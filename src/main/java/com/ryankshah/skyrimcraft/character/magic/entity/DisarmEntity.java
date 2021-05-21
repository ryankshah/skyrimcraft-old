package com.ryankshah.skyrimcraft.character.magic.entity;

import com.ryankshah.skyrimcraft.util.ModEntityType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class DisarmEntity extends Entity
{
    private LivingEntity shootingEntity;
    private int ticksAlive;
    private int ticksInAir;
    private double accelerationX;
    private double accelerationY;
    private double accelerationZ;
    private Vector3d startingPosition;

    public DisarmEntity(World worldIn) {
        super(ModEntityType.SHOUT_DISARM_ENTITY.get(), worldIn);
    }

    public DisarmEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(ModEntityType.SHOUT_DISARM_ENTITY.get(), worldIn);
        this.shootingEntity = shooter;
        this.setPos(shooter.getForward().x, shooter.getForward().y, shooter.getForward().z);
        this.moveTo(shooter.getX(), shooter.getY(), shooter.getZ(), shooter.yRot, shooter.xRot);
        this.setPos(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(Vector3d.ZERO);

        startingPosition = new Vector3d(getX(), getY(), getZ());

        double u = MathHelper.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);

        this.accelerationX = accelX / u * 0.1D;
        this.accelerationY = accelY / u * 0.1D;
        this.accelerationZ = accelZ / u * 0.1D;
    }

    public DisarmEntity(FMLPlayMessages.SpawnEntity packet, World world) {
        super(ModEntityType.SHOUT_DISARM_ENTITY.get(), world);
    }

    public DisarmEntity(EntityType<Entity> entityEntityType, World world) {
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
        if(!this.level.isClientSide)
            if(startingPosition.distanceToSqr(getX(), getY(), getZ()) >= 64D)
                this.remove();

        if (this.level.isClientSide || (this.shootingEntity == null || !this.shootingEntity.removed) && this.level.hasChunkAt(new BlockPos(this.getX(), this.getY(), this.getZ()))) {
            super.tick();
            ++this.ticksInAir;

            RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, entity -> entity.isAlive() && entity != this.shootingEntity);
            if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onImpact(raytraceresult);
            }

            Vector3d vec3d = this.getDeltaMovement();
            this.setPos(getX() + vec3d.x, getY() + vec3d.y, getZ() + vec3d.z);

            float f = this.getMotionFactor();

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
                if(entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity)entity;
                    ItemStack mainHand = livingEntity.getMainHandItem();

                    // Check its not vanishing curse and there is indeed something in the entity's main hand
                    if(!livingEntity.getMainHandItem().isEmpty() && !EnchantmentHelper.hasVanishingCurse(mainHand)) {
                        ItemEntity mainHandItemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), mainHand);
                        this.level.addFreshEntity(mainHandItemEntity);
                        livingEntity.setItemSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
                    }

                    // Stagger entity
                    if(!entity.isInWater() || !entity.isInLava())
                        ((LivingEntity)entity).knockback(1F, (double)MathHelper.sin(this.yRot * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(this.yRot * ((float)Math.PI / 180F))));
                }
//                if(!entity.isInWater() || !entity.isInLava())
//                    ((LivingEntity)entity).knockback(2F, (double)MathHelper.sin(this.yRot * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(this.yRot * ((float)Math.PI / 180F))));
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
}