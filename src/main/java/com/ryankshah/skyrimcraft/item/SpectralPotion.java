package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.effect.ModEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SpectralPotion extends SkyrimItem
{
    private int duration;

    public SpectralPotion(Properties properties, String displayName, int duration) {
        super(properties, displayName);
        this.duration = duration;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.EPIC;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        PlayerEntity playerEntity = entityLiving instanceof PlayerEntity ? (PlayerEntity) entityLiving : null;

        if(playerEntity instanceof ServerPlayerEntity)
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);

        if(!worldIn.isClientSide) {
            if(playerEntity instanceof ServerPlayerEntity) {
                playerEntity.addEffect(new EffectInstance(ModEffects.SPECTRAL.get(), duration, 0, true, true, true));
            }
        }

        if (playerEntity != null) {
            playerEntity.awardStat(Stats.ITEM_USED.get(this));
            if (!playerEntity.abilities.instabuild) {
                stack.shrink(1);
            }
        }

        if (playerEntity == null || !playerEntity.abilities.instabuild) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (playerEntity != null) {
                playerEntity.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return stack;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return DrinkHelper.useDrink(worldIn, playerIn, handIn);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Appear spectral for " + duration/20 + " seconds"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        super.releaseUsing(stack, worldIn, entityLiving, timeLeft);
    }

    @Override
    public boolean isEdible() {
        return super.isEdible();
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return super.getDrinkingSound();
    }
}
