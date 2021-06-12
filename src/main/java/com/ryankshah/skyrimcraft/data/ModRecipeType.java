package com.ryankshah.skyrimcraft.data;

import com.ryankshah.skyrimcraft.util.AlchemyRecipe;
import com.ryankshah.skyrimcraft.util.ForgeRecipe;
import com.ryankshah.skyrimcraft.util.OvenRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Optional;

public class ModRecipeType<T extends IRecipe<?>>
{
    public static IRecipeType<ForgeRecipe> FORGE;
    public static IRecipeType<AlchemyRecipe> ALCHEMY;
    public static IRecipeType<OvenRecipe> OVEN;

    public static void register() {
        FORGE = register("skyrimcraft:forge");
        ALCHEMY = register("skyrimcraft:alchemy");
        OVEN = register("skyrimcraft:oven");
    }

    static <T extends IRecipe<?>> IRecipeType<T> register(final String p_222147_0_) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(p_222147_0_), new IRecipeType<T>() {
            public String toString() {
                return p_222147_0_;
            }
        });
    }

    public <C extends IInventory> Optional<T> tryMatch(IRecipe<C> p_222148_1_, World p_222148_2_, C p_222148_3_) {
        return p_222148_1_.matches(p_222148_3_, p_222148_2_) ? Optional.of((T)p_222148_1_) : Optional.empty();
    }
}