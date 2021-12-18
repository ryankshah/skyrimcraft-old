package com.ryankshah.skyrimcraft.data.loot_table.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.data.loot_table.condition.type.ModLootConditionTypes;
import com.ryankshah.skyrimcraft.data.loot_table.predicate.SkillPredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class MatchSkillLevel implements LootItemCondition
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
    public LootItemConditionType getType() {
        return ModLootConditionTypes.MATCH_SKILL;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
        if(entity instanceof LivingEntity) {
            float successChance = ((LivingEntity) entity).getRandom().nextFloat();
            ISkyrimPlayerData cap = entity.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("MatchSkillLevel test"));
            return this.skillPredicate.matches(cap.getSkills().get(SkillRegistry.PICKPOCKET.getID()), successChance);
        }
        return false;
    }

    public static LootItemCondition.Builder skillMatches(SkillPredicate.Builder builder) {
        return () -> {
            return new MatchSkillLevel(builder.build());
        };
    }

    public static class MatchSkillLevelSerializer implements Serializer<MatchSkillLevel> {
        public void serialize(JsonObject obj, MatchSkillLevel condition, JsonSerializationContext context) {
            obj.add("predicate", condition.skillPredicate.serializeToJson());
        }

        public MatchSkillLevel deserialize(JsonObject obj, JsonDeserializationContext context) {
            SkillPredicate skillPredicate = SkillPredicate.fromJson(obj.get("predicate"));
            return new MatchSkillLevel(skillPredicate);
        }
    }
}
