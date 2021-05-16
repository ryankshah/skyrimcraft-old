package com.ryankshah.skyrimcraft.data;

import com.ryankshah.skyrimcraft.block.GarlicCrop;
import com.ryankshah.skyrimcraft.block.TomatoCrop;
import com.ryankshah.skyrimcraft.util.ModBlocks;
import com.ryankshah.skyrimcraft.util.ModItems;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.fml.RegistryObject;

public class ModBlockLootTables extends BlockLootTables
{
    @Override
    protected void addTables() {
        dropSelf(ModBlocks.CORUNDUM_ORE.get());
        dropSelf(ModBlocks.EBONY_ORE.get());
        dropSelf(ModBlocks.MALACHITE_ORE.get());
        dropSelf(ModBlocks.MOONSTONE_ORE.get());
        dropSelf(ModBlocks.QUICKSILVER_ORE.get());
        dropSelf(ModBlocks.SILVER_ORE.get());
        dropSelf(ModBlocks.ORICHALCUM_ORE.get());

        dropSelf(ModBlocks.OVEN.get());
        dropSelf(ModBlocks.ALCHEMY_TABLE.get());

        dropOther(ModBlocks.RED_MOUNTAIN_FLOWER.get(), ModBlocks.RED_MOUNTAIN_FLOWER_ITEM.get());

        add(ModBlocks.SALT_DEPOSIT.get(), (deposit) -> {
            return createSilkTouchDispatchTable(deposit, applyExplosionDecay(deposit, ItemLootEntry.lootTableItem(ModItems.SALT_PILE.get()).apply(SetCount.setCount(RandomValueRange.between(2.0F, 5.0F))).apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
        });

        ILootCondition.IBuilder ilootcondition$tomatocrop = BlockStateProperty.hasBlockStateProperties(ModBlocks.TOMATO_CROP.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TomatoCrop.AGE, 7));
        add(ModBlocks.TOMATO_CROP.get(), createCropDrops(ModBlocks.TOMATO_CROP.get(), ModItems.TOMATO.get(), ModBlocks.TOMATO_SEEDS.get(), ilootcondition$tomatocrop));
        ILootCondition.IBuilder ilootcondition$garliccrop = BlockStateProperty.hasBlockStateProperties(ModBlocks.GARLIC_CROP.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GarlicCrop.AGE, 7));
        add(ModBlocks.GARLIC_CROP.get(), createCropDrops(ModBlocks.GARLIC_CROP.get(), ModBlocks.GARLIC.get(), ModBlocks.GARLIC.get(), ilootcondition$garliccrop));
    }

    protected static LootTable.Builder createCropDrops(Block p_218541_0_, Item p_218541_1_, Item p_218541_2_, ILootCondition.IBuilder p_218541_3_) {
        return applyExplosionDecay(p_218541_0_, LootTable.lootTable().withPool(LootPool.lootPool().add(ItemLootEntry.lootTableItem(p_218541_1_).when(p_218541_3_).otherwise(ItemLootEntry.lootTableItem(p_218541_2_)))).withPool(LootPool.lootPool().when(p_218541_3_).add(ItemLootEntry.lootTableItem(p_218541_2_).apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}