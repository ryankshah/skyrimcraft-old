package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.capability.IMagicka;
import com.ryankshah.skyrimcraft.capability.IMagickaProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.PacketConsumeMagicka;
import com.ryankshah.skyrimcraft.network.PacketReplenishMagicka;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class MagickaPotion extends SkyrimItem
{
    private float replenishValue;

    public MagickaPotion(Properties properties, String displayName, float replenishValue) {
        super(properties, displayName);
        this.replenishValue = replenishValue;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        PlayerEntity playerEntity = entityLiving instanceof PlayerEntity ? (PlayerEntity) entityLiving : null;

        if(playerEntity instanceof ServerPlayerEntity)
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);

        if(!worldIn.isRemote) {
//            playerEntity.getCapability(IMagickaProvider.MAGICKA_CAPABILITY).ifPresent((cap) -> {
//                if(cap.get() < cap.getMaxMagicka()) {
//                    Networking.sendToClient(new PacketConsumeMagicka(replenishValue), (ServerPlayerEntity) playerEntity);
//                    playerEntity.sendStatusMessage(new StringTextComponent("Magicka : " + cap.get() + " / " + cap.getMaxMagicka()), false);
//                }
//            });
            Networking.sendToClient(new PacketConsumeMagicka(replenishValue), (ServerPlayerEntity) playerEntity);
            //Networking.sendToServer(new PacketReplenishMagicka(replenishValue));
        }

        if (playerEntity != null) {
            playerEntity.addStat(Stats.ITEM_USED.get(this));
            if (!playerEntity.abilities.isCreativeMode) {
                stack.shrink(1);
            }
        }

        if (playerEntity == null || !playerEntity.abilities.isCreativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (playerEntity != null) {
                playerEntity.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return stack;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return DrinkHelper.startDrinking(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    @Override
    public boolean isFood() {
        return super.isFood();
    }

    @Override
    public SoundEvent getDrinkSound() {
        return super.getDrinkSound();
    }
}