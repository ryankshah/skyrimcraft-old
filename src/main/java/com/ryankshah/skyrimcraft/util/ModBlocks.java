package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.block.*;
import com.ryankshah.skyrimcraft.block.tileentity.OvenTileEntity;
import com.ryankshah.skyrimcraft.item.SkyrimBlockItem;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
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
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Skyrimcraft.MODID);

    // Ores
    public static final RegistryObject<Block> EBONY_ORE = BLOCKS.register("ebony_ore", () -> new SkyrimOreBlock("Ebony Ore"));
    public static final RegistryObject<Block> CORUNDUM_ORE = BLOCKS.register("corundum_ore", () -> new SkyrimOreBlock("Corundum Ore"));
    public static final RegistryObject<Block> MALACHITE_ORE = BLOCKS.register("malachite_ore", () -> new SkyrimOreBlock("Malachite Ore"));
    public static final RegistryObject<Block> MOONSTONE_ORE = BLOCKS.register("moonstone_ore", () -> new SkyrimOreBlock("Moonstone Ore"));
    public static final RegistryObject<Block> ORICHALCUM_ORE = BLOCKS.register("orichalcum_ore", () -> new SkyrimOreBlock("Orichalcum Ore"));
    public static final RegistryObject<Block> QUICKSILVER_ORE = BLOCKS.register("quicksilver_ore", () -> new SkyrimOreBlock("Quicksilver Ore"));
    public static final RegistryObject<Block> SILVER_ORE = BLOCKS.register("silver_ore", () -> new SkyrimOreBlock("Silver Ore"));
    // Ore BlockItems
    public static final RegistryObject<Item> EBONY_ORE_ITEM = BLOCK_ITEMS.register("ebony_ore", () -> new SkyrimBlockItem(EBONY_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Ebony Ore"));
    public static final RegistryObject<Item> CORUNDUM_ORE_ITEM = BLOCK_ITEMS.register("corundum_ore", () -> new SkyrimBlockItem(CORUNDUM_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Corundum Ore"));
    public static final RegistryObject<Item> MALACHITE_ORE_ITEM = BLOCK_ITEMS.register("malachite_ore", () -> new SkyrimBlockItem(MALACHITE_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Malachite Ore"));
    public static final RegistryObject<Item> MOONSTONE_ORE_ITEM = BLOCK_ITEMS.register("moonstone_ore", () -> new SkyrimBlockItem(MOONSTONE_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Moonstone Ore"));
    public static final RegistryObject<Item> ORICHALCUM_ORE_ITEM = BLOCK_ITEMS.register("orichalcum_ore", () -> new SkyrimBlockItem(ORICHALCUM_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Orichalcum Ore"));
    public static final RegistryObject<Item> QUICKSILVER_ORE_ITEM = BLOCK_ITEMS.register("quicksilver_ore", () -> new SkyrimBlockItem(QUICKSILVER_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Quicksilver Ore"));
    public static final RegistryObject<Item> SILVER_ORE_ITEM = BLOCK_ITEMS.register("silver_ore", () -> new SkyrimBlockItem(SILVER_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Silver Ore"));

    // Misc
    public static final RegistryObject<Block> SALT_DEPOSIT = BLOCKS.register("salt_deposit", () -> new SaltDepositBlock("Salt Deposit"));
    public static final RegistryObject<Item> SALT_DEPOSIT_ITEM = BLOCK_ITEMS.register("salt_deposit", () -> new SkyrimBlockItem(SALT_DEPOSIT.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Salt Deposit"));

    // Plants
    public static final RegistryObject<Block> TOMATO_CROP = BLOCKS.register("tomatoes", () -> new TomatoCrop("Tomatoes"));
    public static final RegistryObject<Block> GARLIC_CROP = BLOCKS.register("garlic", () -> new TomatoCrop("Garlic"));
    // Plant seeds
    public static final RegistryObject<Item> TOMATO_SEEDS = BLOCK_ITEMS.register("tomato_seeds", () -> new SkyrimBlockItem(ModBlocks.TOMATO_CROP.get(), new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Tomato Seeds"));
    public static final RegistryObject<Item> GARLIC = BLOCK_ITEMS.register("garlic", () -> new SkyrimBlockItem(ModBlocks.GARLIC_CROP.get(), new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Garlic"));

    public static final RegistryObject<Block> OVEN = BLOCKS.register("oven", () -> new OvenBlock("Oven"));
    public static final RegistryObject<Item> OVEN_BLOCK_ITEM = BLOCK_ITEMS.register("oven", () -> new SkyrimBlockItem(OVEN.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Oven"));
    public static final RegistryObject<TileEntityType<?>> OVEN_TILE_ENTITY = TILE_ENTITIES.register("oven", () -> TileEntityType.Builder.of(OvenTileEntity::new, OVEN.get()).build(null));

    // Shout block
    public static final RegistryObject<Block> SHOUT_BLOCK = BLOCKS.register("shout_block", () -> new ShoutBlock("Shout Block"));
    public static final RegistryObject<Item> SHOUT_BLOCK_ITEM = BLOCK_ITEMS.register("shout_block", () -> new SkyrimBlockItem(SHOUT_BLOCK.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Shout Block"));

    // Alchemy Table
    public static final RegistryObject<Block> ALCHEMY_TABLE = BLOCKS.register("alchemy_table", () -> new AlchemyTableBlock("Alchemy Lab"));
    public static final RegistryObject<Item> ALCHEMY_TABLE_ITEM = BLOCK_ITEMS.register("alchemy_table", () -> new SkyrimBlockItem(ALCHEMY_TABLE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Alchemy Lab"));


    public static void blockRenders() {
        RenderTypeLookup.setRenderLayer(ModBlocks.SILVER_ORE.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.QUICKSILVER_ORE.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.ORICHALCUM_ORE.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.MOONSTONE_ORE.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.MALACHITE_ORE.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.EBONY_ORE.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.CORUNDUM_ORE.get(), RenderType.solid());

        RenderTypeLookup.setRenderLayer(ModBlocks.TOMATO_CROP.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GARLIC_CROP.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(ModBlocks.OVEN.get(), RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(ModBlocks.SHOUT_BLOCK.get(), RenderType.solid());

        RenderTypeLookup.setRenderLayer(ModBlocks.ALCHEMY_TABLE.get(), RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(ModBlocks.SALT_DEPOSIT.get(), RenderType.cutoutMipped());
    }

    public static class BlockStates extends BlockStateProvider {
        private ExistingFileHelper fileHelper;
        public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
            super(gen, Skyrimcraft.MODID, exFileHelper);
            this.fileHelper = exFileHelper;
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
            simpleBlock(ModBlocks.SALT_DEPOSIT.get(), models().getBuilder("salt_deposit").parent(models().getExistingFile(new ResourceLocation("minecraft:block/cube_all"))).texture("0", new ResourceLocation("skyrimcraft:block/salt_deposit")).texture("particle", new ResourceLocation("skyrimcraft:block/salt_deposit")).element().from(1F, 0F, 1F).to(15F, 2F, 13F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(3F, 3F, 6.5F, 3.5F).texture("#0").end().face(Direction.EAST).uvs(0F, 3F, 3F, 3.5F).texture("#0").end().face(Direction.SOUTH).uvs(9.5F, 3F, 13F, 3.5F).texture("#0").end().face(Direction.WEST).uvs(6.5F, 3F, 9.5F, 3.5F).texture("#0").end().face(Direction.UP).uvs(6.5F, 3F, 3F, 0F).texture("#0").end().face(Direction.DOWN).uvs(10F, 0F, 6.5F, 3F).texture("#0").end().end().element().from(0F, 0F, 9F).to(12F, 2F, 15F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(1.5F, 8F, 4.5F, 8.5F).texture("#0").end().face(Direction.EAST).uvs(0F, 8F, 1.5F, 8.5F).texture("#0").end().face(Direction.SOUTH).uvs(6F, 8F, 9F, 8.5F).texture("#0").end().face(Direction.WEST).uvs(4.5F, 8F, 6F, 8.5F).texture("#0").end().face(Direction.UP).uvs(4.5F, 8F, 1.5F, 6.5F).texture("#0").end().face(Direction.DOWN).uvs(7.5F, 6.5F, 4.5F, 8F).texture("#0").end().end().element().from(12F, 0F, 13F).to(13F, 3F, 14F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(0.25F, 0.25F, 0.5F, 1F).texture("#0").end().face(Direction.EAST).uvs(0F, 0.25F, 0.25F, 1F).texture("#0").end().face(Direction.SOUTH).uvs(0.75F, 0.25F, 1F, 1F).texture("#0").end().face(Direction.WEST).uvs(0.5F, 0.25F, 0.75F, 1F).texture("#0").end().face(Direction.UP).uvs(0.5F, 0.25F, 0.25F, 0F).texture("#0").end().face(Direction.DOWN).uvs(0.75F, 0F, 0.5F, 0.25F).texture("#0").end().end().element().from(12F, 0F, 13F).to(15F, 1F, 15F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(0.5F, 9F, 1.25F, 9.25F).texture("#0").end().face(Direction.EAST).uvs(0F, 9F, 0.5F, 9.25F).texture("#0").end().face(Direction.SOUTH).uvs(1.75F, 9F, 2.5F, 9.25F).texture("#0").end().face(Direction.WEST).uvs(1.25F, 9F, 1.75F, 9.25F).texture("#0").end().face(Direction.UP).uvs(1.25F, 9F, 0.5F, 8.5F).texture("#0").end().face(Direction.DOWN).uvs(2F, 8.5F, 1.25F, 9F).texture("#0").end().end().element().from(15F, 0F, 11F).to(16F, 1F, 14F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(0.75F, 4.25F, 1F, 4.5F).texture("#0").end().face(Direction.EAST).uvs(0F, 4.25F, 0.75F, 4.5F).texture("#0").end().face(Direction.SOUTH).uvs(1.75F, 4.25F, 2F, 4.5F).texture("#0").end().face(Direction.WEST).uvs(1F, 4.25F, 1.75F, 4.5F).texture("#0").end().face(Direction.UP).uvs(1F, 4.25F, 0.75F, 3.5F).texture("#0").end().face(Direction.DOWN).uvs(1.25F, 3.5F, 1F, 4.25F).texture("#0").end().end().element().from(2F, 2F, 3F).to(11F, 5F, 12F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(2.25F, 5.75F, 4.5F, 6.5F).texture("#0").end().face(Direction.EAST).uvs(0F, 5.75F, 2.25F, 6.5F).texture("#0").end().face(Direction.SOUTH).uvs(6.75F, 5.75F, 9F, 6.5F).texture("#0").end().face(Direction.WEST).uvs(4.5F, 5.75F, 6.75F, 6.5F).texture("#0").end().face(Direction.UP).uvs(4.5F, 5.75F, 2.25F, 3.5F).texture("#0").end().face(Direction.DOWN).uvs(6.75F, 3.5F, 4.5F, 5.75F).texture("#0").end().end().element().from(11F, 2F, 3F).to(14F, 3F, 10F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(8.5F, 5.25F, 9.25F, 5.5F).texture("#0").end().face(Direction.EAST).uvs(6.75F, 5.25F, 8.5F, 5.5F).texture("#0").end().face(Direction.SOUTH).uvs(11F, 5.25F, 11.75F, 5.5F).texture("#0").end().face(Direction.WEST).uvs(9.25F, 5.25F, 11F, 5.5F).texture("#0").end().face(Direction.UP).uvs(9.25F, 5.25F, 8.5F, 3.5F).texture("#0").end().face(Direction.DOWN).uvs(10F, 3.5F, 9.25F, 5.25F).texture("#0").end().end().element().from(3F, 5F, 4F).to(6F, 6F, 7F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(8.25F, 7.25F, 9F, 7.5F).texture("#0").end().face(Direction.EAST).uvs(7.5F, 7.25F, 8.25F, 7.5F).texture("#0").end().face(Direction.SOUTH).uvs(9.75F, 7.25F, 10.5F, 7.5F).texture("#0").end().face(Direction.WEST).uvs(9F, 7.25F, 9.75F, 7.5F).texture("#0").end().face(Direction.UP).uvs(9F, 7.25F, 8.25F, 6.5F).texture("#0").end().face(Direction.DOWN).uvs(9.75F, 6.5F, 9F, 7.25F).texture("#0").end().end().element().from(7F, 5F, 6F).to(10F, 6F, 9F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(0.75F, 2.25F, 1.5F, 2.5F).texture("#0").end().face(Direction.EAST).uvs(0F, 2.25F, 0.75F, 2.5F).texture("#0").end().face(Direction.SOUTH).uvs(2.25F, 2.25F, 3F, 2.5F).texture("#0").end().face(Direction.WEST).uvs(1.5F, 2.25F, 2.25F, 2.5F).texture("#0").end().face(Direction.UP).uvs(1.5F, 2.25F, 0.75F, 1.5F).texture("#0").end().face(Direction.DOWN).uvs(2.25F, 1.5F, 1.5F, 2.25F).texture("#0").end().end().element().from(6F, 5F, 4F).to(7F, 6F, 9F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(1.25F, 1.25F, 1.5F, 1.5F).texture("#0").end().face(Direction.EAST).uvs(0F, 1.25F, 1.25F, 1.5F).texture("#0").end().face(Direction.SOUTH).uvs(2.75F, 1.25F, 3F, 1.5F).texture("#0").end().face(Direction.WEST).uvs(1.5F, 1.25F, 2.75F, 1.5F).texture("#0").end().face(Direction.UP).uvs(1.5F, 1.25F, 1.25F, 0F).texture("#0").end().face(Direction.DOWN).uvs(1.75F, 0F, 1.5F, 1.25F).texture("#0").end().end());

            BlockModelBuilder ovenBuilder = models().getBuilder("oven").parent(models().getExistingFile(new ResourceLocation("minecraft:block/cube_all"))).texture("0", new ResourceLocation("skyrimcraft:block/oven")).texture("particle", new ResourceLocation("skyrimcraft:block/oven")).element().from(0F, 0F, 0F).to(16F, 4F, 16F).rotation().angle(0F).axis(Direction.Axis.Y).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(2F, 4.5F, 4F, 5F).texture("#0").cullface(Direction.NORTH).end().face(Direction.EAST).uvs(0F, 4.5F, 2F, 5F).texture("#0").cullface(Direction.EAST).end().face(Direction.SOUTH).uvs(6F, 4.5F, 8F, 5F).texture("#0").cullface(Direction.SOUTH).end().face(Direction.WEST).uvs(4F, 4.5F, 6F, 5F).texture("#0").cullface(Direction.WEST).end().face(Direction.UP).uvs(4F, 4.5F, 2F, 2.5F).texture("#0").end().face(Direction.DOWN).uvs(6F, 2.5F, 4F, 4.5F).texture("#0").cullface(Direction.DOWN).end().end().element().from(0F, 6F, 0F).to(16F, 10F, 16F).rotation().angle(0F).axis(Direction.Axis.Y).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(2F, 2F, 4F, 2.5F).texture("#0").cullface(Direction.NORTH).end().face(Direction.EAST).uvs(0F, 2F, 2F, 2.5F).texture("#0").cullface(Direction.EAST).end().face(Direction.SOUTH).uvs(6F, 2F, 8F, 2.5F).texture("#0").cullface(Direction.SOUTH).end().face(Direction.WEST).uvs(4F, 2F, 6F, 2.5F).texture("#0").cullface(Direction.WEST).end().face(Direction.UP).uvs(4F, 2F, 2F, 0F).texture("#0").end().face(Direction.DOWN).uvs(6F, 0F, 4F, 2F).texture("#0").end().end().element().from(0F, 4F, 2F).to(16F, 6F, 16F).rotation().angle(0F).axis(Direction.Axis.Y).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(1.75F, 6.75F, 3.75F, 7F).texture("#0").end().face(Direction.EAST).uvs(0F, 6.75F, 1.75F, 7F).texture("#0").cullface(Direction.EAST).end().face(Direction.SOUTH).uvs(5.5F, 6.75F, 7.5F, 7F).texture("#0").cullface(Direction.SOUTH).end().face(Direction.WEST).uvs(3.75F, 6.75F, 5.5F, 7F).texture("#0").cullface(Direction.WEST).end().end().element().from(6.9289321881345245F, 7.414213562373096F, 7F).to(11.899494936611664F, 11.414213562373096F, 15F).rotation().angle(45F).axis(Direction.Axis.Z).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(9F, 2F, 9.5F, 2.5F).texture("#0").end().face(Direction.SOUTH).uvs(10.5F, 2F, 11F, 2.5F).texture("#0").end().end().element().from(5.5147186257614305F, 8F, 7F).to(10.48528137423857F, 16F, 15F).rotation().angle(0F).axis(Direction.Axis.Y).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(7F, 1F, 7.5F, 2F).texture("#0").end().face(Direction.SOUTH).uvs(8.5F, 1F, 9F, 2F).texture("#0").end().end().element().from(6.9289321881345245F, 15.414213562373096F, 7F).to(11.899494936611664F, 19.414213562373096F, 15F).rotation().angle(45F).axis(Direction.Axis.Z).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(8.75F, 5.25F, 9.25F, 5.75F).texture("#0").end().face(Direction.SOUTH).uvs(10.25F, 5.25F, 10.75F, 5.75F).texture("#0").end().face(Direction.UP).uvs(9.25F, 5.25F, 8.75F, 4.25F).texture("#0").cullface(Direction.UP).end().end().element().from(5.5147186257614305F, 16F, 7F).to(10.48528137423857F, 20F, 15F).rotation().angle(0F).axis(Direction.Axis.Y).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(4F, 8F, 4.5F, 8.5F).texture("#0").cullface(Direction.UP).end().face(Direction.SOUTH).uvs(5.5F, 8F, 6F, 8.5F).texture("#0").cullface(Direction.UP).end().face(Direction.UP).uvs(4.5F, 8F, 4F, 7F).texture("#0").cullface(Direction.UP).end().end().element().from(3.414213562373095F, 10.928932188134526F, 7F).to(7.414213562373095F, 15.899494936611664F, 15F).rotation().angle(45F).axis(Direction.Axis.Z).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(1F, 8F, 1.5F, 8.5F).texture("#0").end().face(Direction.SOUTH).uvs(2.5F, 8F, 3F, 8.5F).texture("#0").end().end().element().from(2F, 9.51471862576143F, 7F).to(6F, 16.485281374238568F, 15F).rotation().angle(0F).axis(Direction.Axis.Y).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(7F, 3.5F, 7.5F, 4.25F).texture("#0").end().face(Direction.SOUTH).uvs(8.5F, 3.5F, 9F, 4.25F).texture("#0").end().face(Direction.WEST).uvs(7.5F, 3.5F, 8.5F, 4.25F).texture("#0").end().end().element().from(11.414213562373096F, 10.928932188134526F, 7F).to(15.414213562373096F, 15.899494936611664F, 15F).rotation().angle(45F).axis(Direction.Axis.Z).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(7.5F, 7.75F, 8F, 8.25F).texture("#0").end().face(Direction.EAST).uvs(6.5F, 7.75F, 7.5F, 8.25F).texture("#0").cullface(Direction.UP).end().face(Direction.SOUTH).uvs(9F, 7.75F, 9.5F, 8.25F).texture("#0").end().end().element().from(10F, 9.51471862576143F, 7F).to(14F, 16.485281374238568F, 15F).rotation().angle(0F).axis(Direction.Axis.Y).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(6.75F, 6F, 7.25F, 6.75F).texture("#0").end().face(Direction.EAST).uvs(5.75F, 6F, 6.75F, 6.75F).texture("#0").end().face(Direction.SOUTH).uvs(8.25F, 6F, 8.75F, 6.75F).texture("#0").end().end().element().from(5.5147186257614305F, 6F, 1F).to(10.48528137423857F, 8F, 7F).rotation().angle(45F).axis(Direction.Axis.Z).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(8.5F, 9F, 9F, 9.25F).texture("#0").end().face(Direction.SOUTH).uvs(9.75F, 9F, 10.25F, 9.25F).texture("#0").end().face(Direction.UP).uvs(9F, 9F, 8.5F, 8.25F).texture("#0").end().end().element().from(5.5147186257614305F, 16F, 1F).to(10.48528137423857F, 18F, 7F).rotation().angle(45F).axis(Direction.Axis.Z).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(8.75F, 3.25F, 9.25F, 3.5F).texture("#0").end().face(Direction.SOUTH).uvs(10F, 3.25F, 10.5F, 3.5F).texture("#0").end().face(Direction.UP).uvs(9.25F, 3.25F, 8.75F, 2.5F).texture("#0").end().face(Direction.DOWN).uvs(9.75F, 2.5F, 9.25F, 3.25F).texture("#0").end().end().element().from(5.5147186257614305F, 16F, 1F).to(10.48528137423857F, 18F, 7F).rotation().angle(0F).axis(Direction.Axis.Y).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(8.75F, 0.75F, 9.25F, 1F).texture("#0").cullface(Direction.UP).end().face(Direction.UP).uvs(9.25F, 0.75F, 8.75F, 0F).texture("#0").cullface(Direction.UP).end().face(Direction.DOWN).uvs(9.75F, 0F, 9.25F, 0.75F).texture("#0").cullface(Direction.UP).end().end().element().from(2F, 9.51471862576143F, 1F).to(4F, 14.485281374238568F, 7F).rotation().angle(45F).axis(Direction.Axis.Z).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(2.75F, 9.25F, 3F, 9.75F).texture("#0").end().face(Direction.EAST).uvs(2F, 9.25F, 2.75F, 9.75F).texture("#0").end().face(Direction.SOUTH).uvs(3.75F, 9.25F, 4F, 9.75F).texture("#0").end().end().element().from(2F, 9.51471862576143F, 1F).to(4F, 14.485281374238568F, 7F).rotation().angle(0F).axis(Direction.Axis.Y).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(0.75F, 9.25F, 1F, 9.75F).texture("#0").end().face(Direction.EAST).uvs(0F, 9.25F, 0.75F, 9.75F).texture("#0").end().face(Direction.WEST).uvs(1F, 9.25F, 1.75F, 9.75F).texture("#0").end().end().element().from(12F, 9.51471862576143F, 1F).to(14F, 14.485281374238568F, 7F).rotation().angle(45F).axis(Direction.Axis.Z).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(0.75F, 3.25F, 1F, 3.75F).texture("#0").end().face(Direction.EAST).uvs(0F, 3.25F, 0.75F, 3.75F).texture("#0").end().face(Direction.SOUTH).uvs(1.75F, 3.25F, 2F, 3.75F).texture("#0").end().face(Direction.WEST).uvs(1F, 3.25F, 1.75F, 3.75F).texture("#0").end().end().element().from(12F, 9.51471862576143F, 1F).to(14F, 14.485281374238568F, 7F).rotation().angle(0F).axis(Direction.Axis.Y).origin(8F, 12F, 7.833333333333333F).end().face(Direction.NORTH).uvs(0.75F, 0.75F, 1F, 1.25F).texture("#0").end().face(Direction.EAST).uvs(0F, 0.75F, 0.75F, 1.25F).texture("#0").end().face(Direction.WEST).uvs(1F, 0.75F, 1.75F, 1.25F).texture("#0").end().end().element().from(5.5147186257614305F, 6F, 1F).to(10.48528137423857F, 8F, 7F).rotation().angle(0F).axis(Direction.Axis.Y).origin(8F, 12F, 4F).end().face(Direction.NORTH).uvs(6F, 9F, 6.5F, 9.25F).texture("#0").end().face(Direction.EAST).uvs(5.25F, 9F, 6F, 9.25F).texture("#0").end().face(Direction.SOUTH).uvs(7.25F, 9F, 7.75F, 9.25F).texture("#0").end().face(Direction.WEST).uvs(6.5F, 9F, 7.25F, 9.25F).texture("#0").end().face(Direction.UP).uvs(6.5F, 9F, 6F, 8.25F).texture("#0").end().face(Direction.DOWN).uvs(7F, 8.25F, 6.5F, 9F).texture("#0").end().end();
            horizontalBlock(ModBlocks.OVEN.get(), state -> ovenBuilder);

            //BlockModelBuilder alchemyTableBuilder = models().getBuilder("alchemy_table").parent(models().getExistingFile(new ResourceLocation(Skyrimcraft.MODID, "block/alchemy_table")))).save();

            horizontalBlock(ModBlocks.ALCHEMY_TABLE.get(), state -> models().getExistingFile(new ResourceLocation(Skyrimcraft.MODID, "block/alchemy_table")));
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
            withExistingParent(SALT_DEPOSIT_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/salt_deposit"));
            withExistingParent(OVEN_BLOCK_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/oven"));
            withExistingParent(ALCHEMY_TABLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/alchemy_table"));
            singleTexture(TOMATO_SEEDS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/tomato_seeds"));
            singleTexture(GARLIC.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/garlic"));
        }
    }
}