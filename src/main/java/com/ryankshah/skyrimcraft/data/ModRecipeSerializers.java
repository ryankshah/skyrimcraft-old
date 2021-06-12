package com.ryankshah.skyrimcraft.data;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeSerializers
{
    public static final DeferredRegister<IRecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Skyrimcraft.MODID);

    public static final RegistryObject<IRecipeSerializer<?>> FORGE_RECIPE_SERIALIZER = SERIALIZERS.register("forge", ForgeRecipeSerializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> ALCHEMY_RECIPE_SERIALIZER = SERIALIZERS.register("alchemy", AlchemyRecipeSerializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> OVEN_RECIPE_SERIALIZER = SERIALIZERS.register("oven", OvenRecipeSerializer::new);

}