package com.ryankshah.skyrimcraft.client.entity.merchant;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public interface ISkyrimMerchant
{
    void setTradingPlayer(@Nullable PlayerEntity playerEntity);

    @Nullable
    PlayerEntity getTradingPlayer();

    MerchantOffers getOffers();

    @OnlyIn(Dist.CLIENT)
    void overrideOffers(@Nullable MerchantOffers merchantOffers);

    void notifyTrade(MerchantOffer merchantOffer);

    void notifyTradeUpdated(ItemStack itemStack);

    World getLevel();

//    default void openTradingScreen(PlayerEntity playerEntity, ITextComponent iTextComponent, int id) {
//        if(playerEntity instanceof ClientPlayerEntity) {
//            MerchantOffers merchantOffers = this.getOffers();
//            if (!merchantOffers.isEmpty()) {
//                Minecraft.getInstance().setScreen(new MerchantTradeScreen(merchantOffers));
//            }
//        }
//    }
}
