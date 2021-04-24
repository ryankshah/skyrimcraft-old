package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Skyrimcraft.MODID);

//    public static final RegistryObject<Block> DEEP_SEA_GRAVEL = BLOCKS.register("deep_sea_gravel", DeepSeaGravelBlock::new);
//    public static final RegistryObject<Block> AMMONITE_FOSSIL = BLOCKS.register("ammonite_fossil", AmmoniteFossilBlock::new);
}