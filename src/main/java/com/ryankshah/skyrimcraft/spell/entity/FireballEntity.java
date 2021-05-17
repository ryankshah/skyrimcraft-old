package com.ryankshah.skyrimcraft.spell.entity;

import com.ryankshah.skyrimcraft.util.ModEntityType;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class FireballEntity extends Entity
{

    private LivingEntity shootingEntity;
    private int ticksAlive;
    private int ticksInAir;
    private double accelerationX;
    private double accelerationY;
    private double accelerationZ;

    public FireballEntity(World worldIn) {
        super(ModEntityType.SPELL_FIREBALL_ENTITY.get(), worldIn);
    }

    public FireballEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(ModEntityType.SPELL_FIREBALL_ENTITY.get(), worldIn);
        this.shootingEntity = shooter;
        this.moveTo(shooter.getX(), shooter.getY(), shooter.getZ(), shooter.yRot, shooter.xRot);
        this.setPos(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(Vector3d.ZERO);

        double u = MathHelper.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);

        this.accelerationX = accelX / u * 0.1D;
        this.accelerationY = accelY / u * 0.1D;
        this.accelerationZ = accelZ / u * 0.1D;
    }

    public FireballEntity(FMLPlayMessages.SpawnEntity packet, World world) {
        super(ModEntityType.SPELL_FIREBALL_ENTITY.get(), world);
    }

    public FireballEntity(EntityType<Entity> entityEntityType, World world) {
        super(entityEntityType, world);
    }

    @Override
    protected void defineSynchedData() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double u = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(u)) {
            u = 4.0D;
        }

        u = u * 64.0D;
        return distance < u * u;
    }

    @Override
    public void tick() {
        if (this.level.isClientSide || (this.shootingEntity == null || !this.shootingEntity.removed) && this.level.hasChunkAt(new BlockPos(this.getX(), this.getY(), this.getZ()))) {
            super.tick();
            ++this.ticksInAir;

            RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, entity -> entity.isAlive() && entity != this.shootingEntity);
            if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onImpact(raytraceresult);
            }

            Vector3d vec3d = this.getDeltaMovement();
            this.setPos(getX() + vec3d.x, getY() + vec3d.y, getZ() + vec3d.z);
            ProjectileHelper.rotateTowardsMovement(this, 0.2f);
            //ProjectileHelper.rotateTowardsMovement(this, 0.2f);

            float f = this.getMotionFactor();

            float radius = 2f;
            // Get origins
            Vector3d origin = new Vector3d(0, 0, 0);
            Vector3d facing = getForward();

            for(int i = 0; i < 6; i++) {
                this.level.addParticle(ParticleTypes.SMOKE, this.getX() - vec3d.x * 0.25D, this.getY() - vec3d.y * 0.25D, this.getZ() - vec3d.z * 0.25D, vec3d.x, vec3d.y, vec3d.z);
            }

            this.setDeltaMovement(vec3d.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale(f));
            this.setPos(this.getX(), this.getY(), this.getZ());
        } else {
            this.remove();
        }
    }

    protected float getMotionFactor() {
        return 1f;
    }


    public void onImpact(RayTraceResult result) {
        if (!this.level.isClientSide) {
            if (result.getType() == RayTraceResult.Type.ENTITY) {
                Entity entity = ((EntityRayTraceResult)result).getEntity();
                ServerWorld world = (ServerWorld) entity.level;
                entity.hurt(DamageSource.indirectMagic(this, this.shootingEntity), 5.5F);
                entity.setSecondsOnFire(4); // 4 seconds too long/short?
                //igniteBlocks(4); // needed ?
            } else if (result.getType() == RayTraceResult.Type.BLOCK) {
                level.playSound(null, new BlockPos(result.getLocation().x, result.getLocation().y, result.getLocation().z), SoundEvents.GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.0F, 1.0F);
            }
            this.remove();
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        Vector3d vec3d = this.getDeltaMovement();
        compound.put("direction", this.newDoubleList(vec3d.x, vec3d.y, vec3d.z));
        compound.put("power", this.newDoubleList(this.accelerationX, this.accelerationY, this.accelerationZ));
        compound.putInt("life", this.ticksAlive);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        if (compound.contains("power", 9)) {
            ListNBT listnbt = compound.getList("power", 6);
            if (listnbt.size() == 3) {
                this.accelerationX = listnbt.getDouble(0);
                this.accelerationY = listnbt.getDouble(1);
                this.accelerationZ = listnbt.getDouble(2);
            }
        }

        this.ticksAlive = compound.getInt("life");
        if (compound.contains("direction", 9) && compound.getList("direction", 6).size() == 3) {
            ListNBT listnbt1 = compound.getList("direction", 6);
            this.setDeltaMovement(listnbt1.getDouble(0), listnbt1.getDouble(1), listnbt1.getDouble(2));
        } else {
            this.remove();
        }
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 1.0F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.markHurt();
            if (source.getEntity() != null) {
                Vector3d vec3d = source.getEntity().getLookAngle();
                this.setDeltaMovement(vec3d);
                this.accelerationX = vec3d.x * 0.1D;
                this.accelerationY = vec3d.y * 0.1D;
                this.accelerationZ = vec3d.z * 0.1D;
                if (source.getEntity() instanceof LivingEntity) {
                    this.shootingEntity = (LivingEntity)source.getEntity();
                }

                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public float getBrightness() {
        return 1.0F;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void igniteBlocks(int extraIgnitions) {
        if (!this.level.isClientSide && this.level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            BlockPos blockpos = this.blockPosition();
            BlockState blockstate = AbstractFireBlock.getState(this.level, blockpos);
            if (this.level.getBlockState(blockpos).isAir() && blockstate.canSurvive(this.level, blockpos)) {
                this.level.setBlockAndUpdate(blockpos, blockstate);
            }

            for(int i = 0; i < extraIgnitions; ++i) {
                BlockPos blockpos1 = blockpos.offset(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
                blockstate = AbstractFireBlock.getState(this.level, blockpos1);
                if (this.level.getBlockState(blockpos1).isAir() && blockstate.canSurvive(this.level, blockpos1)) {
                    this.level.setBlockAndUpdate(blockpos1, blockstate);
                }
            }

        }
    }

    private Vector3d rotate(Vector3d origin, Vector3d facing, double angle) {
        double c = (origin.x * facing.x) + (origin.y * facing.y) + (origin.z * facing.z); // constant
        double vx = origin.x * c * (1d - MathHelper.cos((float)angle)) + facing.x * MathHelper.cos((float)angle) + (-origin.z * facing.y + origin.y*facing.z) * MathHelper.sin((float)angle);
        double vy = origin.y * c * (1d - MathHelper.cos((float)angle)) + facing.y * MathHelper.cos((float)angle) + (origin.z * facing.x - origin.x*facing.z) * MathHelper.sin((float)angle);
        double vz = origin.z * c * (1d - MathHelper.cos((float)angle)) + facing.z * MathHelper.cos((float)angle) + (-origin.y * facing.x + origin.x*facing.y) * MathHelper.sin((float)angle);

        return new Vector3d(vx, vy, vz);
    }
}