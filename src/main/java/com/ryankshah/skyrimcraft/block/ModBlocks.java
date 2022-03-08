package com.ryankshah.skyrimcraft.block;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.item.SkyrimBlockItem;
import com.ryankshah.skyrimcraft.util.IngredientEffect;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Skyrimcraft.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Skyrimcraft.MODID);

    // Ores
    public static final RegistryObject<Block> EBONY_ORE = BLOCKS.register("ebony_ore", () -> new SkyrimOreBlock("Ebony Ore"));
    public static final RegistryObject<Block> EBONY_ORE_DEEPSLATE = BLOCKS.register("ebony_ore_deepslate", () -> new SkyrimOreBlock("Ebony Ore"));
    public static final RegistryObject<Block> CORUNDUM_ORE = BLOCKS.register("corundum_ore", () -> new SkyrimOreBlock("Corundum Ore"));
    public static final RegistryObject<Block> MALACHITE_ORE = BLOCKS.register("malachite_ore", () -> new SkyrimOreBlock("Malachite Ore"));
    public static final RegistryObject<Block> MOONSTONE_ORE = BLOCKS.register("moonstone_ore", () -> new SkyrimOreBlock("Moonstone Ore"));
    public static final RegistryObject<Block> ORICHALCUM_ORE = BLOCKS.register("orichalcum_ore", () -> new SkyrimOreBlock("Orichalcum Ore"));
    public static final RegistryObject<Block> QUICKSILVER_ORE = BLOCKS.register("quicksilver_ore", () -> new SkyrimOreBlock("Quicksilver Ore"));
    public static final RegistryObject<Block> SILVER_ORE = BLOCKS.register("silver_ore", () -> new SkyrimOreBlock("Silver Ore"));
    // Ore BlockItems
    public static final RegistryObject<Item> EBONY_ORE_ITEM = BLOCK_ITEMS.register("ebony_ore_item", () -> new SkyrimBlockItem(EBONY_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Ebony Ore"));
    public static final RegistryObject<Item> EBONY_ORE_DEEPSLATE_ITEM = BLOCK_ITEMS.register("ebony_ore_deepslate_item", () -> new SkyrimBlockItem(EBONY_ORE_DEEPSLATE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Ebony Ore"));
    public static final RegistryObject<Item> CORUNDUM_ORE_ITEM = BLOCK_ITEMS.register("corundum_ore_item", () -> new SkyrimBlockItem(CORUNDUM_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Corundum Ore"));
    public static final RegistryObject<Item> MALACHITE_ORE_ITEM = BLOCK_ITEMS.register("malachite_ore_item", () -> new SkyrimBlockItem(MALACHITE_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Malachite Ore"));
    public static final RegistryObject<Item> MOONSTONE_ORE_ITEM = BLOCK_ITEMS.register("moonstone_ore_item", () -> new SkyrimBlockItem(MOONSTONE_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Moonstone Ore"));
    public static final RegistryObject<Item> ORICHALCUM_ORE_ITEM = BLOCK_ITEMS.register("orichalcum_ore_item", () -> new SkyrimBlockItem(ORICHALCUM_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Orichalcum Ore"));
    public static final RegistryObject<Item> QUICKSILVER_ORE_ITEM = BLOCK_ITEMS.register("quicksilver_ore_item", () -> new SkyrimBlockItem(QUICKSILVER_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Quicksilver Ore"));
    public static final RegistryObject<Item> SILVER_ORE_ITEM = BLOCK_ITEMS.register("silver_ore_item", () -> new SkyrimBlockItem(SILVER_ORE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Silver Ore"));

    // Misc
    public static final RegistryObject<Block> SALT_DEPOSIT = BLOCKS.register("salt_deposit", () -> new SaltDepositBlock("Salt Deposit"));
    public static final RegistryObject<Item> SALT_DEPOSIT_ITEM = BLOCK_ITEMS.register("salt_deposit_item", () -> new SkyrimBlockItem(SALT_DEPOSIT.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Salt Deposit"));

    // Plants
    public static final RegistryObject<Block> TOMATO_CROP = BLOCKS.register("tomatoes", () -> new TomatoCrop("Tomatoes"));
    public static final RegistryObject<Block> GARLIC_CROP = BLOCKS.register("garlic", () -> new TomatoCrop("Garlic"));
    // Plant seeds
    public static final RegistryObject<Item> TOMATO_SEEDS = BLOCK_ITEMS.register("tomato_seeds", () -> new SkyrimBlockItem(ModBlocks.TOMATO_CROP.get(), new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Tomato Seeds"));
    public static final RegistryObject<Item> GARLIC = BLOCK_ITEMS.register("garlic", () -> new SkyrimBlockItem(ModBlocks.GARLIC_CROP.get(), new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Garlic"));

    // Flowers
    public static final RegistryObject<Block> RED_MOUNTAIN_FLOWER = BLOCKS.register("red_mountain_flower", () -> new SkyrimFlower("Red Mountain Flower"));
    public static final RegistryObject<Item> RED_MOUNTAIN_FLOWER_ITEM = BLOCK_ITEMS.register("red_mountain_flower_item", () -> new SkyrimBlockItem(ModBlocks.RED_MOUNTAIN_FLOWER.get(), new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Red Mountain Flower", IngredientEffect.RESTORE_HEALTH, IngredientEffect.FORTIFY_CONJURATION, IngredientEffect.FORTIFY_HEALTH, IngredientEffect.DAMAGE_MAGICKA_REGEN));
    public static final RegistryObject<Block> BLUE_MOUNTAIN_FLOWER = BLOCKS.register("blue_mountain_flower", () -> new SkyrimFlower("Blue Mountain Flower"));
    public static final RegistryObject<Item> BLUE_MOUNTAIN_FLOWER_ITEM = BLOCK_ITEMS.register("blue_mountain_flower_item", () -> new SkyrimBlockItem(ModBlocks.BLUE_MOUNTAIN_FLOWER.get(), new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Blue Mountain Flower", IngredientEffect.RESTORE_MAGICKA, IngredientEffect.RAVAGE_MAGICKA, IngredientEffect.FORTIFY_MAGICKA, IngredientEffect.DAMAGE_HEALTH));
    public static final RegistryObject<Block> YELLOW_MOUNTAIN_FLOWER = BLOCKS.register("yellow_mountain_flower", () -> new SkyrimFlower("Yellow Mountain Flower"));
    public static final RegistryObject<Item> YELLOW_MOUNTAIN_FLOWER_ITEM = BLOCK_ITEMS.register("yellow_mountain_flower_item", () -> new SkyrimBlockItem(ModBlocks.YELLOW_MOUNTAIN_FLOWER.get(), new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Yellow Mountain Flower", IngredientEffect.RESIST_POISON, IngredientEffect.FORTIFY_RESTORATION, IngredientEffect.FORTIFY_HEALTH, IngredientEffect.DAMAGE_STAMINA_REGEN));
    public static final RegistryObject<Block> PURPLE_MOUNTAIN_FLOWER = BLOCKS.register("purple_mountain_flower", () -> new SkyrimFlower("Purple Mountain Flower"));
    public static final RegistryObject<Item> PURPLE_MOUNTAIN_FLOWER_ITEM = BLOCK_ITEMS.register("purple_mountain_flower_item", () -> new SkyrimBlockItem(ModBlocks.PURPLE_MOUNTAIN_FLOWER.get(), new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Purple Mountain Flower", IngredientEffect.RESTORE_STAMINA, IngredientEffect.FORTIFY_SNEAK, IngredientEffect.LINGERING_DAMAGE_MAGICKA, IngredientEffect.RESIST_FROST));

    public static final RegistryObject<Block> OVEN = BLOCKS.register("oven", () -> new OvenBlock("Oven"));
    public static final RegistryObject<Item> OVEN_BLOCK_ITEM = BLOCK_ITEMS.register("oven_item", () -> new SkyrimBlockItem(OVEN.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Oven"));

    // Shout block
    public static final RegistryObject<Block> SHOUT_BLOCK = BLOCKS.register("shout_block", () -> new ShoutBlock("Shout Block"));
    public static final RegistryObject<Item> SHOUT_BLOCK_ITEM = BLOCK_ITEMS.register("shout_block_item", () -> new SkyrimBlockItem(SHOUT_BLOCK.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Shout Block"));

    // Alchemy Table
    public static final RegistryObject<Block> ALCHEMY_TABLE = BLOCKS.register("alchemy_table", () -> new AlchemyTableBlock("Alchemy Lab"));
    public static final RegistryObject<Item> ALCHEMY_TABLE_ITEM = BLOCK_ITEMS.register("alchemy_table_item", () -> new SkyrimBlockItem(ALCHEMY_TABLE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Alchemy Lab"));

    // Blacksmith Forge
    public static final RegistryObject<Block> BLACKSMITH_FORGE = BLOCKS.register("blacksmith_forge", () -> new BlacksmithForgeBlock("Blacksmith Forge"));
    public static final RegistryObject<Item> BLACKSMITH_FORGE_ITEM = BLOCK_ITEMS.register("blacksmith_forge_item", () -> new SkyrimBlockItem(BLACKSMITH_FORGE.get(), new Item.Properties().tab(Skyrimcraft.TAB_BLOCKS), "Blacksmith Forge"));

    public static void blockRenders() {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SILVER_ORE.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.QUICKSILVER_ORE.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ORICHALCUM_ORE.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MOONSTONE_ORE.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MALACHITE_ORE.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.EBONY_ORE.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.EBONY_ORE_DEEPSLATE.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CORUNDUM_ORE.get(), RenderType.solid());

        // Flowers
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RED_MOUNTAIN_FLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLUE_MOUNTAIN_FLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.YELLOW_MOUNTAIN_FLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PURPLE_MOUNTAIN_FLOWER.get(), RenderType.cutout());

        // Crops
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TOMATO_CROP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GARLIC_CROP.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OVEN.get(), RenderType.cutoutMipped());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHOUT_BLOCK.get(), RenderType.solid());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ALCHEMY_TABLE.get(), RenderType.cutoutMipped());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLACKSMITH_FORGE.get(), RenderType.cutoutMipped());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SALT_DEPOSIT.get(), RenderType.cutoutMipped());
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
            simpleBlock(ModBlocks.EBONY_ORE_DEEPSLATE.get());
            simpleBlock(ModBlocks.MALACHITE_ORE.get());
            simpleBlock(ModBlocks.MOONSTONE_ORE.get());
            simpleBlock(ModBlocks.ORICHALCUM_ORE.get());
            simpleBlock(ModBlocks.QUICKSILVER_ORE.get());
            simpleBlock(ModBlocks.SILVER_ORE.get());
            simpleBlock(ModBlocks.SHOUT_BLOCK.get());
            simpleBlock(ModBlocks.SALT_DEPOSIT.get(), models().getBuilder("salt_deposit").parent(models().getExistingFile(new ResourceLocation("minecraft:block/cube_all"))).texture("0", new ResourceLocation("skyrimcraft:block/salt_deposit")).texture("particle", new ResourceLocation("skyrimcraft:block/salt_deposit")).element().from(1F, 0F, 1F).to(15F, 2F, 13F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(3F, 3F, 6.5F, 3.5F).texture("#0").end().face(Direction.EAST).uvs(0F, 3F, 3F, 3.5F).texture("#0").end().face(Direction.SOUTH).uvs(9.5F, 3F, 13F, 3.5F).texture("#0").end().face(Direction.WEST).uvs(6.5F, 3F, 9.5F, 3.5F).texture("#0").end().face(Direction.UP).uvs(6.5F, 3F, 3F, 0F).texture("#0").end().face(Direction.DOWN).uvs(10F, 0F, 6.5F, 3F).texture("#0").end().end().element().from(0F, 0F, 9F).to(12F, 2F, 15F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(1.5F, 8F, 4.5F, 8.5F).texture("#0").end().face(Direction.EAST).uvs(0F, 8F, 1.5F, 8.5F).texture("#0").end().face(Direction.SOUTH).uvs(6F, 8F, 9F, 8.5F).texture("#0").end().face(Direction.WEST).uvs(4.5F, 8F, 6F, 8.5F).texture("#0").end().face(Direction.UP).uvs(4.5F, 8F, 1.5F, 6.5F).texture("#0").end().face(Direction.DOWN).uvs(7.5F, 6.5F, 4.5F, 8F).texture("#0").end().end().element().from(12F, 0F, 13F).to(13F, 3F, 14F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(0.25F, 0.25F, 0.5F, 1F).texture("#0").end().face(Direction.EAST).uvs(0F, 0.25F, 0.25F, 1F).texture("#0").end().face(Direction.SOUTH).uvs(0.75F, 0.25F, 1F, 1F).texture("#0").end().face(Direction.WEST).uvs(0.5F, 0.25F, 0.75F, 1F).texture("#0").end().face(Direction.UP).uvs(0.5F, 0.25F, 0.25F, 0F).texture("#0").end().face(Direction.DOWN).uvs(0.75F, 0F, 0.5F, 0.25F).texture("#0").end().end().element().from(12F, 0F, 13F).to(15F, 1F, 15F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(0.5F, 9F, 1.25F, 9.25F).texture("#0").end().face(Direction.EAST).uvs(0F, 9F, 0.5F, 9.25F).texture("#0").end().face(Direction.SOUTH).uvs(1.75F, 9F, 2.5F, 9.25F).texture("#0").end().face(Direction.WEST).uvs(1.25F, 9F, 1.75F, 9.25F).texture("#0").end().face(Direction.UP).uvs(1.25F, 9F, 0.5F, 8.5F).texture("#0").end().face(Direction.DOWN).uvs(2F, 8.5F, 1.25F, 9F).texture("#0").end().end().element().from(15F, 0F, 11F).to(16F, 1F, 14F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(0.75F, 4.25F, 1F, 4.5F).texture("#0").end().face(Direction.EAST).uvs(0F, 4.25F, 0.75F, 4.5F).texture("#0").end().face(Direction.SOUTH).uvs(1.75F, 4.25F, 2F, 4.5F).texture("#0").end().face(Direction.WEST).uvs(1F, 4.25F, 1.75F, 4.5F).texture("#0").end().face(Direction.UP).uvs(1F, 4.25F, 0.75F, 3.5F).texture("#0").end().face(Direction.DOWN).uvs(1.25F, 3.5F, 1F, 4.25F).texture("#0").end().end().element().from(2F, 2F, 3F).to(11F, 5F, 12F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(2.25F, 5.75F, 4.5F, 6.5F).texture("#0").end().face(Direction.EAST).uvs(0F, 5.75F, 2.25F, 6.5F).texture("#0").end().face(Direction.SOUTH).uvs(6.75F, 5.75F, 9F, 6.5F).texture("#0").end().face(Direction.WEST).uvs(4.5F, 5.75F, 6.75F, 6.5F).texture("#0").end().face(Direction.UP).uvs(4.5F, 5.75F, 2.25F, 3.5F).texture("#0").end().face(Direction.DOWN).uvs(6.75F, 3.5F, 4.5F, 5.75F).texture("#0").end().end().element().from(11F, 2F, 3F).to(14F, 3F, 10F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(8.5F, 5.25F, 9.25F, 5.5F).texture("#0").end().face(Direction.EAST).uvs(6.75F, 5.25F, 8.5F, 5.5F).texture("#0").end().face(Direction.SOUTH).uvs(11F, 5.25F, 11.75F, 5.5F).texture("#0").end().face(Direction.WEST).uvs(9.25F, 5.25F, 11F, 5.5F).texture("#0").end().face(Direction.UP).uvs(9.25F, 5.25F, 8.5F, 3.5F).texture("#0").end().face(Direction.DOWN).uvs(10F, 3.5F, 9.25F, 5.25F).texture("#0").end().end().element().from(3F, 5F, 4F).to(6F, 6F, 7F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(8.25F, 7.25F, 9F, 7.5F).texture("#0").end().face(Direction.EAST).uvs(7.5F, 7.25F, 8.25F, 7.5F).texture("#0").end().face(Direction.SOUTH).uvs(9.75F, 7.25F, 10.5F, 7.5F).texture("#0").end().face(Direction.WEST).uvs(9F, 7.25F, 9.75F, 7.5F).texture("#0").end().face(Direction.UP).uvs(9F, 7.25F, 8.25F, 6.5F).texture("#0").end().face(Direction.DOWN).uvs(9.75F, 6.5F, 9F, 7.25F).texture("#0").end().end().element().from(7F, 5F, 6F).to(10F, 6F, 9F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(0.75F, 2.25F, 1.5F, 2.5F).texture("#0").end().face(Direction.EAST).uvs(0F, 2.25F, 0.75F, 2.5F).texture("#0").end().face(Direction.SOUTH).uvs(2.25F, 2.25F, 3F, 2.5F).texture("#0").end().face(Direction.WEST).uvs(1.5F, 2.25F, 2.25F, 2.5F).texture("#0").end().face(Direction.UP).uvs(1.5F, 2.25F, 0.75F, 1.5F).texture("#0").end().face(Direction.DOWN).uvs(2.25F, 1.5F, 1.5F, 2.25F).texture("#0").end().end().element().from(6F, 5F, 4F).to(7F, 6F, 9F).rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 0F, 0F).end().face(Direction.NORTH).uvs(1.25F, 1.25F, 1.5F, 1.5F).texture("#0").end().face(Direction.EAST).uvs(0F, 1.25F, 1.25F, 1.5F).texture("#0").end().face(Direction.SOUTH).uvs(2.75F, 1.25F, 3F, 1.5F).texture("#0").end().face(Direction.WEST).uvs(1.5F, 1.25F, 2.75F, 1.5F).texture("#0").end().face(Direction.UP).uvs(1.5F, 1.25F, 1.25F, 0F).texture("#0").end().face(Direction.DOWN).uvs(1.75F, 0F, 1.5F, 1.25F).texture("#0").end().end());

            simpleBlock(ModBlocks.RED_MOUNTAIN_FLOWER.get(), models().getBuilder("red_mountain_flower").parent(models().getExistingFile(new ResourceLocation("minecraft:block/cross"))).texture("cross", new ResourceLocation("skyrimcraft:block/red_mountain_flower")));
            simpleBlock(ModBlocks.BLUE_MOUNTAIN_FLOWER.get(), models().getBuilder("blue_mountain_flower").parent(models().getExistingFile(new ResourceLocation("minecraft:block/cross"))).texture("cross", new ResourceLocation("skyrimcraft:block/blue_mountain_flower")));
            simpleBlock(ModBlocks.YELLOW_MOUNTAIN_FLOWER.get(), models().getBuilder("yellow_mountain_flower").parent(models().getExistingFile(new ResourceLocation("minecraft:block/cross"))).texture("cross", new ResourceLocation("skyrimcraft:block/yellow_mountain_flower")));
            simpleBlock(ModBlocks.PURPLE_MOUNTAIN_FLOWER.get(), models().getBuilder("purple_mountain_flower").parent(models().getExistingFile(new ResourceLocation("minecraft:block/cross"))).texture("cross", new ResourceLocation("skyrimcraft:block/purple_mountain_flower")));

            horizontalBlock(ModBlocks.OVEN.get(), state -> models().getExistingFile(new ResourceLocation(Skyrimcraft.MODID, "block/oven")));

            //BlockModelBuilder alchemyTableBuilder = models().getBuilder("alchemy_table").parent(models().getExistingFile(new ResourceLocation(Skyrimcraft.MODID, "block/alchemy_table")))).save();

            horizontalBlock(ModBlocks.ALCHEMY_TABLE.get(), state -> models().getExistingFile(new ResourceLocation(Skyrimcraft.MODID, "block/alchemy_table")));
            horizontalBlock(ModBlocks.BLACKSMITH_FORGE.get(), state -> models().getExistingFile(new ResourceLocation(Skyrimcraft.MODID, "block/blacksmith_forge")));
        }
    }

    public static class BlockItems extends ItemModelProvider {
        public BlockItems(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, Skyrimcraft.MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            withExistingParent(EBONY_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/ebony_ore"));
            withExistingParent(EBONY_ORE_DEEPSLATE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/ebony_ore_deepslate"));
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
            withExistingParent(BLACKSMITH_FORGE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Skyrimcraft.MODID, "block/blacksmith_forge"));
            singleTexture(TOMATO_SEEDS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/tomato_seeds"));
            singleTexture(GARLIC.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/garlic"));

            // FLowers
            singleTexture(RED_MOUNTAIN_FLOWER_ITEM.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "block/red_mountain_flower"));
            singleTexture(BLUE_MOUNTAIN_FLOWER_ITEM.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "block/blue_mountain_flower"));
            singleTexture(YELLOW_MOUNTAIN_FLOWER_ITEM.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "block/yellow_mountain_flower"));
            singleTexture(PURPLE_MOUNTAIN_FLOWER_ITEM.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "block/purple_mountain_flower"));
        }
    }
}