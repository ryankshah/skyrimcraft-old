package com.ryankshah.skyrimcraft.data.loot_table.condition.type;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.data.loot_table.condition.MatchSkillLevel;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class ModLootConditionTypes
{
    public static LootItemConditionType MATCH_SKILL;

    public static void register() {
        MATCH_SKILL = add("match_skill", new MatchSkillLevel.MatchSkillLevelSerializer());
    }

    public static LootItemConditionType add(String name, Serializer<? extends LootItemCondition> lootSerializer) {
        return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(Skyrimcraft.MODID, name), new LootItemConditionType(lootSerializer));
    }
}