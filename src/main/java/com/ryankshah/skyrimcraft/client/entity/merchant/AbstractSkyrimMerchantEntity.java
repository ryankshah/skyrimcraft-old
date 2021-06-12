package com.ryankshah.skyrimcraft.client.entity.merchant;

import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public abstract class AbstractSkyrimMerchantEntity extends CreatureEntity implements INPC, ISkyrimMerchant
{
    @Nullable
    private PlayerEntity tradingPlayer;

    public AbstractSkyrimMerchantEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 1.62F;
    }

    @Override
    public void setTradingPlayer(@Nullable PlayerEntity playerEntity) {
        this.tradingPlayer = playerEntity;
    }

    @Nullable
    @Override
    public PlayerEntity getTradingPlayer() {
        return tradingPlayer;
    }

    public boolean isTrading() {
        return this.tradingPlayer != null;
    }

    @Override
    public MerchantOffers getOffers() {
        return null;
    }

    @Override
    public void overrideOffers(@Nullable MerchantOffers merchantOffers) {

    }

    @Override
    public void notifyTrade(MerchantOffer merchantOffer) {
        merchantOffer.increaseUses();
        this.rewardTradeXp(merchantOffer);
    }

    protected abstract void rewardTradeXp(MerchantOffer p_213713_1_);

    @Override
    public void notifyTradeUpdated(ItemStack itemStack) {

    }

    @Override
    public World getLevel() {
        return this.level;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        MerchantOffers merchantoffers = this.getOffers();
        if (!merchantoffers.isEmpty()) {
            compoundNBT.put("Offers", merchantoffers.createTag());
        }
    }

//    @Override
//    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
//        super.readAdditionalSaveData(compoundNBT);
//        if (compoundNBT.contains("Offers", 10)) {
//            this.offers = new MerchantOffers(compoundNBT.getCompound("Offers"));
//        }
//    }

    @Override
    public boolean canBeLeashed(PlayerEntity p_184652_1_) {
        return false;
    }

    @Nullable
    public Entity changeDimension(ServerWorld level, net.minecraftforge.common.util.ITeleporter teleporter) {
        this.stopTrading();
        return super.changeDimension(level, teleporter);
    }

    @Override
    public Vector3d getRopeHoldPosition(float p_241843_1_) {
        float f = MathHelper.lerp(p_241843_1_, this.yBodyRotO, this.yBodyRot) * ((float)Math.PI / 180F);
        Vector3d vector3d = new Vector3d(0.0D, this.getBoundingBox().getYsize() - 1.0D, 0.2D);
        return this.getPosition(p_241843_1_).add(vector3d.yRot(-f));
    }

    protected void stopTrading() {
        this.setTradingPlayer((PlayerEntity)null);
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        this.stopTrading();
    }
}