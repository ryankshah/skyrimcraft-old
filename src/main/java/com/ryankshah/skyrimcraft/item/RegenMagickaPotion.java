package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.util.ModAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

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
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        Player playerEntity = entityLiving instanceof Player ? (Player) entityLiving : null;

        if (!worldIn.isClientSide) {
            if(playerEntity instanceof ServerPlayer) {
                ServerPlayer serverPlayerEntity = (ServerPlayer) playerEntity;
                serverPlayerEntity.getAttribute(ModAttributes.MAGICKA_REGEN.get()).addTransientModifier(new AttributeModifier(ModAttributes.MAGICKA_REGEN_ID, modifierValue, AttributeModifier.Operation.MULTIPLY_BASE));
                serverPlayerEntity.addEffect(new MobEffectInstance(ModEffects.REGEN_MAGICKA.get(), duration, 0, true, true, true));
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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
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

        tooltip.add(new TextComponent("Grants " + duration/20 + "s of " + regenValue + " magicka regen"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}