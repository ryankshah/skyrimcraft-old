package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Skyrimcraft.MODID);

    // Items

    // Block items
    // public static final RegistryObject<Item> DEEP_SEA_GRAVEL_BLOCK_ITEM = ITEMS.register("deep_sea_gravel", () -> new BlockItem(ModBlocks.DEEP_SEA_GRAVEL.get(), new Item.Properties().group(DeepSeaMod.TAB)));
}