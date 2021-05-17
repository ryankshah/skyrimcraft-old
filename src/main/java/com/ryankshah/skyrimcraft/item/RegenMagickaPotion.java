package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.util.ModAttributes;
import com.ryankshah.skyrimcraft.util.ModBlocks;
import com.ryankshah.skyrimcraft.util.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RegenMagickaPotion extends SkyrimPotion {
    private float modifierValue;
    private int duration;

    public RegenMagickaPotion(Properties properties, String displayName, float modifierValue, int duration) {
        super(properties, displayName);
        this.modifierValue = modifierValue;
        this.duration = duration;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        PlayerEntity playerEntity = entityLiving instanceof PlayerEntity ? (PlayerEntity) entityLiving : null;

        if (!worldIn.isClientSide) {
            if(playerEntity instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) playerEntity;
                serverPlayerEntity.getAttribute(ModAttributes.MAGICKA_REGEN.get()).addTransientModifier(new AttributeModifier(ModAttributes.MAGICKA_REGEN_ID, modifierValue, AttributeModifier.Operation.MULTIPLY_BASE));
                serverPlayerEntity.addEffect(new EffectInstance(ModEffects.REGEN_MAGICKA.get(), duration, 0, true, true, true));
            }
        }

        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    @Override
    public List<ItemStack> getIngredients() {
        List<ItemStack> ingredients = new ArrayList<>();
        if (this == ModItems.LASTING_POTENCY_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.SALMON_ROE.get(), 1));
            ingredients.add(new ItemStack(ModBlocks.GARLIC.get(), 1));
        } else if(this == ModItems.DRAUGHT_LASTING_POTENCY_POTION.get()) {
            ingredients.add(new ItemStack(ModBlocks.GARLIC.get(), 1));
            ingredients.add(new ItemStack(ModItems.SALT_PILE.get(), 1));
        } else if (this == ModItems.SOLUTION_LASTING_POTENCY_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.DWARVEN_OIL.get(), 1));
            ingredients.add(new ItemStack(ModItems.SALMON_ROE.get(), 1));
        } else if (this == ModItems.PHILTER_LASTING_POTENCY_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.SALT_PILE.get(), 1));
            ingredients.add(new ItemStack(ModItems.DWARVEN_OIL.get(), 1));
        } else if (this == ModItems.ELIXIR_LASTING_POTENCY_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.DWARVEN_OIL.get(), 1));
            ingredients.add(new ItemStack(ModItems.FIRE_SALTS.get(), 1));
        }
        return ingredients;
    }

    @Override
    public PotionCategory getCategory() {
        return PotionCategory.REGENERATE_HEALTH;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        String regenValue = "";

        Item item = stack.getItem();
        if (ModItems.LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "50%";
        } else if (ModItems.DRAUGHT_LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "60%";
        } else if (ModItems.SOLUTION_LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "70%";
        } else if (ModItems.PHILTER_LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "80%";
        } else if (ModItems.ELIXIR_LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "100%";
        }

        tooltip.add(new StringTextComponent("Grants " + duration/20 + "s of " + regenValue + " magicka regen"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}