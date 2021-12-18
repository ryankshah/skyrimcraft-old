package com.ryankshah.skyrimcraft.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.data.loot_table.condition.MatchSkillLevel;
import com.ryankshah.skyrimcraft.data.loot_table.predicate.SkillPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class PickpocketLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>
{
    private final Map<ResourceLocation, LootTable.Builder> map = Maps.newHashMap();

    protected static LootItemCondition.Builder getSkillLevelCondition(int skillID, int level) {
        return MatchSkillLevel.skillMatches(SkillPredicate.Builder.skill().of(skillID, level, 1F));
    }

    protected static LootItemCondition.Builder getSkillLevelConditionWithChance(int skillID, int level, float successChance) {
        return MatchSkillLevel.skillMatches(SkillPredicate.Builder.skill().of(skillID, level, successChance));
    }

    protected static LootTable.Builder createSingleItemTable(ItemLike itemProvider) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(itemProvider)));
    }

    protected static LootTable.Builder createSingleItemTable(ItemLike itemProvider, LootItemCondition.Builder lootConditionBuilder, LootPoolEntryContainer.Builder<?> lootEntryBuilder) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(itemProvider).when(lootConditionBuilder).otherwise(lootEntryBuilder)));
    }

    protected static LootTable.Builder createSingleItemTableWithRange(ItemLike itemProvider, UniformGenerator rolls) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(rolls).add(LootItem.lootTableItem(itemProvider)));
    }

    protected static LootTable.Builder createSingleItemTableWithRange(ItemLike itemProvider, UniformGenerator rolls, LootItemCondition.Builder lootConditionBuilder) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(rolls).add(LootItem.lootTableItem(itemProvider).when(lootConditionBuilder)));//.otherwise(ItemLootEntry.lootTableItem(itemProvider))));
    }

    protected static LootTable.Builder multiplePools(LootItemCondition.Builder lootConditionBuilder, LootPool.Builder... lootPools) {
        LootTable.Builder lootTable = LootTable.lootTable();

        for(LootPool.Builder pool : lootPools) {
            lootTable.withPool(pool.when(lootConditionBuilder));
        }

        return lootTable;
    }

    public static LootTable.Builder noLoot() {
        return LootTable.lootTable();
    }

    protected void add(EntityType<?> entityType, LootTable.Builder builder) {
        this.map.put(new ResourceLocation(entityType.getDefaultLootTable().toString().replace("minecraft", Skyrimcraft.MODID).replace("entities", "pickpocket")), builder);
    }
    protected void add(EntityType<?> entityType, Function<EntityType<?>, LootTable.Builder> builder) {
        this.add(entityType, builder.apply(entityType));
    }

    public static Iterable<EntityType<?>> getPickpocketableEntities() {
        return ImmutableList.of(EntityType.VILLAGER);//, ModEntityType.MERCHANT.get());
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> resourceLocationBuilderBiConsumer) {
        addTables();

        Set<ResourceLocation> set = Sets.newHashSet();

        for(EntityType<?> entityType : getPickpocketableEntities()) {
            ResourceLocation resourcelocation = new ResourceLocation(entityType.getDefaultLootTable().toString().replace("minecraft", Skyrimcraft.MODID).replace("entities", "pickpocket"));
            if (resourcelocation != BuiltInLootTables.EMPTY && set.add(resourcelocation)) {
                LootTable.Builder loottable$builder = this.map.remove(resourcelocation);
                if (loottable$builder == null) {
                    continue;
                    //throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", resourcelocation, ForgeRegistries.ENTITIES.getKey(entityType)));
                }

                resourceLocationBuilderBiConsumer.accept(resourcelocation, loottable$builder);
            }
        }

        if (!this.map.isEmpty()) {
            throw new IllegalStateException("Created pickpocket loot tables for non-pickpocketable entities: " + this.map.keySet());
        }
    }

    public void addTables() {
//        add(EntityType.VILLAGER, multiplePools(
//                getSkillLevelConditionWithChance(SkillRegistry.PICKPOCKET.getID(), 15, 0.4f),
//                LootPool.lootPool().setRolls(RandomValueRange.between(1, 3)).add(ItemLootEntry.lootTableItem(Items.EMERALD)),
//                LootPool.lootPool().setRolls(RandomValueRange.between(0, 1)).add(ItemLootEntry.lootTableItem(ModItems.DWARVEN_OIL.get()))
//        ));
        //add(ModEntityType.MERCHANT.get(), createSingleItemTableWithRange(Items.EMERALD, RandomValueRange.between(1F, 3F), getSkillLevelConditionWithChance(SkillRegistry.PICKPOCKET.getID(), 15, 0.4f)));
        add(EntityType.VILLAGER, createSingleItemTableWithRange(Items.EMERALD, UniformGenerator.between(1F, 3F), getSkillLevelConditionWithChance(SkillRegistry.PICKPOCKET.getID(), 15, 0.4f)));
    }
}
