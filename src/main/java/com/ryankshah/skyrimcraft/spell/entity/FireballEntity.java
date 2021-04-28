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
        this.setLocationAndAngles(shooter.getPosX(), shooter.getPosY(), shooter.getPosZ(), shooter.rotationYaw, shooter.rotationPitch);
        this.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
        this.setMotion(Vector3d.ZERO);

        double d0 = MathHelper.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);

        this.accelerationX = accelX / d0 * 0.1D;
        this.accelerationY = accelY / d0 * 0.1D;
        this.accelerationZ = accelZ / d0 * 0.1D;
    }

    public FireballEntity(FMLPlayMessages.SpawnEntity packet, World world) {
        super(ModEntityType.SPELL_FIREBALL_ENTITY.get(), world);
    }

    public FireballEntity(EntityType<Entity> entityEntityType, World world) {
        super(entityEntityType, world);
    }

    @Override
    protected void registerData() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isInRangeToRenderDist(double distance) {
        double d0 = this.getBoundingBox().getAverageEdgeLength() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }

    @Override
    public void tick() {
        if (this.world.isRemote || (this.shootingEntity == null || !this.shootingEntity.removed) && this.world.isBlockLoaded(new BlockPos(this.getPosX(), this.getPosY(), this.getPosZ()))) {
            super.tick();
            ++this.ticksInAir;

            RayTraceResult raytraceresult = ProjectileHelper.func_234618_a_(this, entity -> entity.isAlive() && entity != this.shootingEntity);
            if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onImpact(raytraceresult);
            }

            Vector3d vec3d = this.getMotion();
            this.setPosition(getPosX() + vec3d.x, getPosY() + (vec3d.y - 0.01), getPosZ() + vec3d.z);
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);

            float f = this.getMotionFactor();

            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    this.world.addParticle(ParticleTypes.BUBBLE, this.getPosX() - vec3d.x * 0.25D, this.getPosY() - vec3d.y * 0.25D, this.getPosZ() - vec3d.z * 0.25D, vec3d.x, vec3d.y, vec3d.z);
                }
                f = 0.8F;
            }
            this.setMotion(vec3d.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale(f));
            this.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
        } else {
            this.remove();
        }
    }

    protected float getMotionFactor() {
        return 0.95F;
    }


    public void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.getType() == RayTraceResult.Type.ENTITY) {
                Entity entity = ((EntityRayTraceResult)result).getEntity();
                ServerWorld world = (ServerWorld) entity.world;
                entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.shootingEntity), 5.5F);

                // TODO: on impact cause player on fire (and set surrounding blocks on fire?)
                entity.setFire(4); // 4 seconds too long/short?
                igniteBlocks(4);
            } else if (result.getType() == RayTraceResult.Type.BLOCK) {
                world.playSound(null, new BlockPos(result.getHitVec().x, result.getHitVec().y, result.getHitVec().z), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.HOSTILE, 1.0F, 1.0F);
            }
            this.remove();
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        Vector3d vec3d = this.getMotion();
        compound.put("direction", this.newDoubleNBTList(vec3d.x, vec3d.y, vec3d.z));
        compound.put("power", this.newDoubleNBTList(this.accelerationX, this.accelerationY, this.accelerationZ));
        compound.putInt("life", this.ticksAlive);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
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
            this.setMotion(listnbt1.getDouble(0), listnbt1.getDouble(1), listnbt1.getDouble(2));
        } else {
            this.remove();
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public float getCollisionBorderSize() {
        return 1.0F;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.markVelocityChanged();
            if (source.getTrueSource() != null) {
                Vector3d vec3d = source.getTrueSource().getLookVec();
                this.setMotion(vec3d);
                this.accelerationX = vec3d.x * 0.1D;
                this.accelerationY = vec3d.y * 0.1D;
                this.accelerationZ = vec3d.z * 0.1D;
                if (source.getTrueSource() instanceof LivingEntity) {
                    this.shootingEntity = (LivingEntity)source.getTrueSource();
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
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void igniteBlocks(int extraIgnitions) {
        if (!this.world.isRemote && this.world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
            BlockPos blockpos = this.getPosition();
            BlockState blockstate = AbstractFireBlock.getFireForPlacement(this.world, blockpos);
            if (this.world.getBlockState(blockpos).isAir() && blockstate.isValidPosition(this.world, blockpos)) {
                this.world.setBlockState(blockpos, blockstate);
            }

            for(int i = 0; i < extraIgnitions; ++i) {
                BlockPos blockpos1 = blockpos.add(this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1);
                blockstate = AbstractFireBlock.getFireForPlacement(this.world, blockpos1);
                if (this.world.getBlockState(blockpos1).isAir() && blockstate.isValidPosition(this.world, blockpos1)) {
                    this.world.setBlockState(blockpos1, blockstate);
                }
            }

        }
    }
}