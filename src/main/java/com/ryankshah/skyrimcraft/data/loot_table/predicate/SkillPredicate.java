package com.ryankshah.skyrimcraft.data.loot_table.predicate;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.ryankshah.skyrimcraft.character.skill.ISkill;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class SkillPredicate
{
    private static final Map<ResourceLocation, Function<JsonObject, SkillPredicate>> custom_predicates = new java.util.HashMap<>();
    private static final Map<ResourceLocation, java.util.function.Function<JsonObject, SkillPredicate>> unmod_predicates = java.util.Collections.unmodifiableMap(custom_predicates);
    public static final SkillPredicate ANY = new SkillPredicate();

    private final ISkill skill;
    private final float successChance;
    //private final NBTPredicate nbt;
    private Random random = new Random();

    public SkillPredicate() {
        this.skill = null;
        this.successChance = 1.0f;
        //this.nbt = NBTPredicate.ANY;
    }

    public SkillPredicate(ISkill skill, float successChance) { //NBTPredicate nbt
        this.skill = skill;
        this.successChance = successChance;
        //this.nbt = nbt;
    }

    public boolean matches(ISkill skill, float successChance) {
        if(this == ANY)
            return true;
        else if(this.skill != null && skill.getID() != this.skill.getID())
            return false;
        else if(this.skill != null && skill.getLevel() > this.skill.getLevel())
            return false;
        else {
            float rand = random.nextFloat();
            if (successChance > 1f) {
                successChance = 1f;
            }
            if (rand <= successChance)
                return true;
        }
        return false;
    }

    public static SkillPredicate fromJson(@Nullable JsonElement p_192492_0_) {
        if (p_192492_0_ != null && !p_192492_0_.isJsonNull()) {
            JsonObject jsonobject = GsonHelper.convertToJsonObject(p_192492_0_, "item");
            if (jsonobject.has("type")) {
                final ResourceLocation rl = new ResourceLocation(GsonHelper.getAsString(jsonobject, "type"));
                if (custom_predicates.containsKey(rl)) return custom_predicates.get(rl).apply(jsonobject);
                else throw new JsonSyntaxException("There is no SkillPredicate of type "+rl);
            }

            ISkill skill = null;
            if(!jsonobject.has("skill"))
                throw new JsonSyntaxException("There is no skill specified!");
            else {
                JsonObject skillObj = GsonHelper.getAsJsonObject(jsonobject, "skill");
                int id = GsonHelper.getAsInt(skillObj, "id");
                if(!SkillRegistry.getKnownSkillIds().contains(id))
                    throw new JsonSyntaxException("There is no skill that exists with id: " + id);
                int level = GsonHelper.getAsInt(skillObj, "level");

                skill = new ISkill(id, level);
            }

            float successChance = GsonHelper.getAsFloat(jsonobject, "successChance");

            return new SkillPredicate(skill, successChance);
        } else {
            return ANY;
        }
    }

    public JsonElement serializeToJson() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        } else {
            JsonObject jsonobject = new JsonObject();
            JsonObject skillObject = new JsonObject();
            if (this.skill != null) {
                skillObject.addProperty("id", this.skill.getID());
                skillObject.addProperty("level", this.skill.getLevel());
                jsonobject.add("skill", skillObject);
            }
            jsonobject.addProperty("successChance", this.successChance);

            return jsonobject;
        }
    }

    public static void register(ResourceLocation name, java.util.function.Function<JsonObject, SkillPredicate> deserializer) {
        custom_predicates.put(name, deserializer);
    }

    public static Map<ResourceLocation, java.util.function.Function<JsonObject, SkillPredicate>> getPredicates() {
        return unmod_predicates;
    }

    public static class Builder {
        private ISkill skill;
        private float successChance;

        private Builder() {
        }

        public static SkillPredicate.Builder skill() {
            return new SkillPredicate.Builder();
        }

        public SkillPredicate.Builder of(int level, int id, float successChance) {
            this.skill = new ISkill(level, id);
            this.successChance = successChance;
            return this;
        }

        public SkillPredicate build() {
            return new SkillPredicate(skill, successChance);
        }
    }
}