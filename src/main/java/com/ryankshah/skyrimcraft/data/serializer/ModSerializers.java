package com.ryankshah.skyrimcraft.data.serializer;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSerializers
{
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Skyrimcraft.MODID);

    public static final RegistryObject<RecipeSerializer<?>> FORGE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("forge", ForgeRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> ALCHEMY_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("alchemy", AlchemyRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> OVEN_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("oven", OvenRecipeSerializer::new);

}