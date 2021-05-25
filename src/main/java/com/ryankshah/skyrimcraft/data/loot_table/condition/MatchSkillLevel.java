package com.ryankshah.skyrimcraft.data.loot_table.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.data.loot_table.condition.type.ModLootConditionTypes;
import com.ryankshah.skyrimcraft.data.loot_table.predicate.SkillPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;

public class MatchSkillLevel implements ILootCondition
{
    private final SkillPredicate skillPredicate;

    public MatchSkillLevel(SkillPredicate skillPredicate) {
        this.skillPredicate = skillPredicate;
    }

//    @Override
//    public Set<LootParameter<?>> getReferencedContextParams() {
//        return ImmutableSet.of(LootParameters.THIS_ENTITY);
//    }

    @Override
    public LootConditionType getType() {
        return ModLootConditionTypes.MATCH_SKILL;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getParamOrNull(LootParameters.THIS_ENTITY);
        if(entity instanceof LivingEntity) {
            float successChance = ((LivingEntity) entity).getRandom().nextFloat();
            ISkyrimPlayerData cap = entity.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("MatchSkillLevel test"));
            return this.skillPredicate.matches(cap.getSkills().get(SkillRegistry.PICKPOCKET.getID()), successChance);
        }
        return false;
    }

    public static ILootCondition.IBuilder skillMatches(SkillPredicate.Builder builder) {
        return () -> {
            return new MatchSkillLevel(builder.build());
        };
    }

    public static class Serializer implements ILootSerializer<MatchSkillLevel> {
        public void serialize(JsonObject obj, MatchSkillLevel condition, JsonSerializationContext context) {
            obj.add("predicate", condition.skillPredicate.serializeToJson());
        }

        public MatchSkillLevel deserialize(JsonObject obj, JsonDeserializationContext context) {
            SkillPredicate skillPredicate = SkillPredicate.fromJson(obj.get("predicate"));
            return new MatchSkillLevel(skillPredicate);
        }
    }
}
