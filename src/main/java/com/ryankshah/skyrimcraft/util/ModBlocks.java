package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.block.ShoutBlock;
import com.ryankshah.skyrimcraft.block.SkyrimOreBlock;
import com.ryankshah.skyrimcraft.item.SkyrimBlockItem;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Skyrimcraft.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Skyrimcraft.MODID);

    // Ores
    public static final RegistryObject<Block> EBONY_ORE = BLOCKS.register("ebony_ore", () -> new SkyrimOreBlock("Ebony Ore"));
    public static final RegistryObject<Block> CORUNDUM_ORE = BLOCKS.register("corundum_ore", () -> new SkyrimOreBlock("Corundum Ore"));
    public static final RegistryObject<Block> MALACHITE_ORE = BLOCKS.register("malachite_ore", () -> new SkyrimOreBlock("Malachite Ore"));
    public static final RegistryObject<Block> MOONSTONE_ORE = BLOCKS.register("moonstone_ore", () -> new SkyrimOreBlock("Moonstone Ore"));
    public static final RegistryObject<Block> ORICHALCUM_ORE = BLOCKS.register("orichalcum_ore", () -> new SkyrimOreBlock("Orichalcum Ore"));
    public static final RegistryObject<Block> QUICKSILVER_ORE = BLOCKS.register("quicksilver_ore", () -> new SkyrimOreBlock("Quicksilver Ore"));
    public static final RegistryObject<Block> SILVER_ORE = BLOCKS.register("silver_ore", () -> new SkyrimOreBlock("Silver Ore"));
    // Ore BlockItems
    public static final RegistryObject<Item> EBONY_ORE_ITEM = BLOCK_ITEMS.register("ebony_ore", () -> new SkyrimBlockItem(EBONY_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB), "Ebony Ore"));
    public static final RegistryObject<Item> CORUNDUM_ORE_ITEM = BLOCK_ITEMS.register("corundum_ore", () -> new SkyrimBlockItem(CORUNDUM_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB), "Corundum Ore"));
    public static final RegistryObject<Item> MALACHITE_ORE_ITEM = BLOCK_ITEMS.register("malachite_ore", () -> new SkyrimBlockItem(MALACHITE_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB), "Malachite Ore"));
    public static final RegistryObject<Item> MOONSTONE_ORE_ITEM = BLOCK_ITEMS.register("moonstone_ore", () -> new SkyrimBlockItem(MOONSTONE_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB), "Moonstone Ore"));
    public static final RegistryObject<Item> ORICHALCUM_ORE_ITEM = BLOCK_ITEMS.register("orichalcum_ore", () -> new SkyrimBlockItem(ORICHALCUM_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB), "Orichalcum Ore"));
    public static final RegistryObject<Item> QUICKSILVER_ORE_ITEM = BLOCK_ITEMS.register("quicksilver_ore", () -> new SkyrimBlockItem(QUICKSILVER_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB), "Quicksilver Ore"));
    public static final RegistryObject<Item> SILVER_ORE_ITEM = BLOCK_ITEMS.register("silver_ore", () -> new SkyrimBlockItem(SILVER_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB), "Silver Ore"));

    // Shout block
    public static final RegistryObject<Block> SHOUT_BLOCK = BLOCKS.register("shout_block", () -> new ShoutBlock("Shout Block"));
    public static final RegistryObject<Item> SHOUT_BLOCK_ITEM = BLOCK_ITEMS.register("shout_block", () -> new SkyrimBlockItem(SHOUT_BLOCK.get(), new Item.Properties().tab(Skyrimcraft.TAB), "Shout Block"));

    public static void blockRenders() {
        RenderTypeLookup.setRenderLayer(ModBlocks.SILVER_ORE.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.QUICKSILVER_ORE.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.ORICHALCUM_ORE.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.MOONSTONE_ORE.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.MALACHITE_ORE.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.EBONY_ORE.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.CORUNDUM_ORE.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.SHOUT_BLOCK.get(), RenderType.solid());
    }

    public static class BlockStates extends BlockStateProvider {
        public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
            super(gen, Skyrimcraft.MODID, exFileHelper);
        }

        @Override
        protected void registerStatesAndModels() {
            simpleBlock(ModBlocks.CORUNDUM_ORE.get());
            simpleBlock(ModBlocks.EBONY_ORE.get());
            simpleBlock(ModBlocks.MALACHITE_ORE.get());
            simpleBlock(ModBlocks.MOONSTONE_ORE.get());
            simpleBlock(ModBlocks.ORICHALCUM_ORE.get());
            simpleBlock(ModBlocks.QUICKSILVER_ORE.get());
            simpleBlock(ModBlocks.SILVER_ORE.get());
            simpleBlock(ModBlocks.SHOUT_BLOCK.get());
        }
    }

    public static class BlockItems extends ItemModelProvider {
        public BlockItems(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, Skyrimcraft.MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            withExistingParent(EBONY_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/ebony_ore"));
            withExistingParent(CORUNDUM_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/corundum_ore"));
            withExistingParent(MALACHITE_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/malachite_ore"));
            withExistingParent(MOONSTONE_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/moonstone_ore"));
            withExistingParent(ORICHALCUM_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/orichalcum_ore"));
            withExistingParent(QUICKSILVER_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/quicksilver_ore"));
            withExistingParent(SILVER_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/silver_ore"));
            withExistingParent(SHOUT_BLOCK_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/shout_block"));
        }
    }

//    public static final RegistryObject<Block> DEEP_SEA_GRAVEL = BLOCKS.register("deep_sea_gravel", DeepSeaGravelBlock::new);
//    public static final RegistryObject<Block> AMMONITE_FOSSIL = BLOCKS.register("ammonite_fossil", AmmoniteFossilBlock::new);
}