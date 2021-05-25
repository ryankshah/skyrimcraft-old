package com.ryankshah.skyrimcraft.data.loot_table.condition.type;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.data.loot_table.condition.MatchSkillLevel;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ModLootConditionTypes
{
    public static LootConditionType MATCH_SKILL;

    public static void register() {
        MATCH_SKILL = add("match_skill", new MatchSkillLevel.Serializer());
    }

    public static LootConditionType add(String name, ILootSerializer<? extends ILootCondition> lootSerializer) {
        return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(Skyrimcraft.MODID, name), new LootConditionType(lootSerializer));
    }
}