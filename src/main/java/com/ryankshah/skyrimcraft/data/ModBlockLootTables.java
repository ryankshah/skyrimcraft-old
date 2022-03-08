package com.ryankshah.skyrimcraft.data;

import com.ryankshah.skyrimcraft.block.GarlicCrop;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.block.TomatoCrop;
import com.ryankshah.skyrimcraft.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot
{
    @Override
    protected void addTables() {
        dropSelf(ModBlocks.CORUNDUM_ORE.get());
        dropSelf(ModBlocks.EBONY_ORE.get());
        dropSelf(ModBlocks.EBONY_ORE_DEEPSLATE.get());
        dropSelf(ModBlocks.MALACHITE_ORE.get());
        dropSelf(ModBlocks.MOONSTONE_ORE.get());
        dropSelf(ModBlocks.QUICKSILVER_ORE.get());
        dropSelf(ModBlocks.SILVER_ORE.get());
        dropSelf(ModBlocks.ORICHALCUM_ORE.get());

        dropSelf(ModBlocks.OVEN.get());
        dropSelf(ModBlocks.ALCHEMY_TABLE.get());
        dropSelf(ModBlocks.BLACKSMITH_FORGE.get());

        dropOther(ModBlocks.RED_MOUNTAIN_FLOWER.get(), ModBlocks.RED_MOUNTAIN_FLOWER_ITEM.get());
        dropOther(ModBlocks.BLUE_MOUNTAIN_FLOWER.get(), ModBlocks.BLUE_MOUNTAIN_FLOWER_ITEM.get());
        dropOther(ModBlocks.YELLOW_MOUNTAIN_FLOWER.get(), ModBlocks.YELLOW_MOUNTAIN_FLOWER_ITEM.get());
        dropOther(ModBlocks.PURPLE_MOUNTAIN_FLOWER.get(), ModBlocks.PURPLE_MOUNTAIN_FLOWER_ITEM.get());

        add(ModBlocks.SALT_DEPOSIT.get(), (deposit) -> {
            return createSilkTouchDispatchTable(deposit, applyExplosionDecay(deposit, LootItem.lootTableItem(ModItems.SALT_PILE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
        });

        LootItemCondition.Builder ilootcondition$tomatocrop = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.TOMATO_CROP.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TomatoCrop.AGE, 7));
        add(ModBlocks.TOMATO_CROP.get(), createCropDrops(ModBlocks.TOMATO_CROP.get(), ModItems.TOMATO.get(), ModBlocks.TOMATO_SEEDS.get(), ilootcondition$tomatocrop));
        LootItemCondition.Builder ilootcondition$garliccrop = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.GARLIC_CROP.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GarlicCrop.AGE, 7));
        add(ModBlocks.GARLIC_CROP.get(), createCropDrops(ModBlocks.GARLIC_CROP.get(), ModBlocks.GARLIC.get(), ModBlocks.GARLIC.get(), ilootcondition$garliccrop));
    }

    protected static LootTable.Builder createCropDrops(Block p_218541_0_, Item p_218541_1_, Item p_218541_2_, LootItemCondition.Builder p_218541_3_) {
        return applyExplosionDecay(p_218541_0_, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(p_218541_1_).when(p_218541_3_).otherwise(LootItem.lootTableItem(p_218541_2_)))).withPool(LootPool.lootPool().when(p_218541_3_).add(LootItem.lootTableItem(p_218541_2_).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}