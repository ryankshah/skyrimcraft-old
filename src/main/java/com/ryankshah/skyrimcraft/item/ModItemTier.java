package com.ryankshah.skyrimcraft.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum ModItemTier implements IItemTier {
    ANCIENT_NORD(2, 250, 6.0F, 2.0F, 14, () -> {
        return Ingredient.EMPTY;
    }),
    DRAGONBONE(2, 250, 6.0F, 2.0F, 14, () -> {
        return Ingredient.EMPTY;
    }),
    STEEL(2, 250, 6.0F, 2.0F, 14, () -> {
        return Ingredient.of(ModItems.STEEL_INGOT.get());
    }),
    FALMER(2, 250, 6.0F, 2.0F, 14, () -> {
        return Ingredient.EMPTY;
    }),
    GLASS(0, 32, 12.0F, 0.0F, 22, () -> {
        return Ingredient.of(ModItems.MALACHITE_INGOT.get());
    }),
    ELVEN(0, 32, 12.0F, 0.0F, 22, () -> {
        return Ingredient.of(ModItems.MOONSTONE_INGOT.get());
    }),
    ORCISH(2, 250, 6.0F, 2.0F, 22, () -> {
        return Ingredient.of(ModItems.ORICHALCUM_INGOT.get());
    }),
    DWARVEN(3, 450, 6.5F, 2.25F, 22, () -> {
        return Ingredient.of(ModItems.DWARVEN_METAL_INGOT.get());
    }),
    DAEDRIC(4,2031,9.0F,4.0F,12,() -> {
        return Ingredient.of(ModItems.EBONY_INGOT.get());
    }),
    EBONY(4,1743,7.0F,2.25F,15,() -> {
        return Ingredient.of(ModItems.EBONY_INGOT.get());
    });

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyValue<Ingredient> repairIngredient;

    private ModItemTier(int p_i48458_3_, int p_i48458_4_, float p_i48458_5_, float p_i48458_6_, int p_i48458_7_, Supplier<Ingredient> p_i48458_8_) {
        this.level = p_i48458_3_;
        this.uses = p_i48458_4_;
        this.speed = p_i48458_5_;
        this.damage = p_i48458_6_;
        this.enchantmentValue = p_i48458_7_;
        this.repairIngredient = new LazyValue<>(p_i48458_8_);
    }

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public int getLevel() {
        return this.level;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}