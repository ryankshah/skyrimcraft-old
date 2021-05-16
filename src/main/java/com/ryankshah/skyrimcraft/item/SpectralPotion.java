package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.effect.ModEffects;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item.Properties;

public class SpectralPotion extends SkyrimPotion
{
    private int duration;

    public SpectralPotion(Properties properties, String displayName, int duration) {
        super(properties, displayName);
        this.duration = duration;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        PlayerEntity playerEntity = entityLiving instanceof PlayerEntity ? (PlayerEntity) entityLiving : null;

        if(!worldIn.isClientSide) {
            if(playerEntity instanceof ServerPlayerEntity) {
                playerEntity.addEffect(new EffectInstance(ModEffects.SPECTRAL.get(), duration, 0, true, true, true));
            }
        }

        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    @Override
    public List<ItemStack> getIngredients() {
        List<ItemStack> ingredients = new ArrayList<>();
        return ingredients;
    }

    @Override
    public PotionCategory getCategory() {
        return PotionCategory.UNIQUE;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Appear spectral for " + duration/20 + " seconds"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
