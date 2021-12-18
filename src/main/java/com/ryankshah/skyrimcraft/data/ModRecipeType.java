package com.ryankshah.skyrimcraft.data;

import com.ryankshah.skyrimcraft.util.AlchemyRecipe;
import com.ryankshah.skyrimcraft.util.ForgeRecipe;
import com.ryankshah.skyrimcraft.util.OvenRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ModRecipeType<T extends Recipe<?>>
{
    public static RecipeType<ForgeRecipe> FORGE;
    public static RecipeType<AlchemyRecipe> ALCHEMY;
    public static RecipeType<OvenRecipe> OVEN;

    public static void register() {
        FORGE = register("skyrimcraft:forge");
        ALCHEMY = register("skyrimcraft:alchemy");
        OVEN = register("skyrimcraft:oven");
    }

    static <T extends Recipe<?>> RecipeType<T> register(final String p_222147_0_) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(p_222147_0_), new RecipeType<T>() {
            public String toString() {
                return p_222147_0_;
            }
        });
    }

    public <C extends Container> Optional<T> tryMatch(Recipe<C> p_222148_1_, Level p_222148_2_, C p_222148_3_) {
        return p_222148_1_.matches(p_222148_3_, p_222148_2_) ? Optional.of((T)p_222148_1_) : Optional.empty();
    }
}