package com.ryankshah.skyrimcraft.character.quest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.function.Consumer;

public class Quest extends ForgeRegistryEntry<Quest>
{
    private ResourceLocation id;
    private Player assignedPlayer;
    private QuestCategory questCategory;
    private PriorityQueue<QuestStep> questSteps;
    private boolean singleUse;
    private int timesCompleted;
    private String name;
    private String description;
    // private List<QuestRequirements> questRequirements;

    public Quest(ResourceLocation id, String name, String description, boolean singleUse, QuestCategory category, PriorityQueue<QuestStep> steps) {
        this.id = id;
        this.assignedPlayer = null;
        this.timesCompleted = 0;
        this.name = name;
        this.description = description;
        this.singleUse = singleUse;
        this.questCategory = category;
        this.questSteps = steps;
    }

    public ResourceLocation getId() { return id; }

    public String getName() { return this.name; }

    public String getDescription() { return this.description; }

    /**
     * Set the player who the skill instance belongs to
     *
     * @param playerEntity
     */
    public void setAssignedPlayer(Player playerEntity) {
        this.assignedPlayer = playerEntity;
    }

    /**
     * Get the player entity who has the skill
     * @return {@link Player}
     */
    public Player getAssignedPlayer() {
        return this.assignedPlayer;
    }

    public QuestCategory getQuestCategory() { return this.questCategory; }

    public PriorityQueue<QuestStep> getSteps() { return this.questSteps; }

    public Quest getQuest() { return this; }

    public boolean isComplete() { return this.questSteps.isEmpty() || this.questSteps.stream().allMatch(QuestStep::isComplete); }

    public boolean isSingleUse() { return this.singleUse; }

    public int getTimesCompleted() { return this.timesCompleted; }

    public CompoundTag serialiseToNBT() {
        CompoundTag nbt = new CompoundTag();
        return nbt;
    }

    public static class QuestStep implements Comparable<QuestStep>
    {
        private int priority;
        private String description;
        private CriterionTriggerInstance criteria;
        private boolean complete;

        public QuestStep(int priority, String description, CriterionTriggerInstance criteria) {
            this(priority, description, criteria, false);
        }

        public QuestStep(int priority, String description, CriterionTriggerInstance criteria, boolean complete) {
            this.priority = priority;
            this.description = description;
            this.criteria = criteria;
            this.complete = false;
        }

        public boolean isComplete() { return this.complete; }

        public int getPriority() { return this.priority; }

        public String getDescription() { return this.description; }

        public CriterionTriggerInstance getCriteria() { return this.criteria; }

        public QuestStep.Builder deconstruct() {
            return new QuestStep.Builder(this.priority, this.description, this.criteria);
        }

        public static class Builder {
            private int priority;
            private String description;
            private CriterionTriggerInstance criteria;

            public Builder(int priority, String description, CriterionTriggerInstance criteria) {
                this.priority = priority;
                this.description = description;
                this.criteria = criteria;
                //this.conditions = ??;
            }

            public Builder() {}

            public static QuestStep.Builder step() { return new QuestStep.Builder(); }

            public QuestStep.Builder priority(int priority) { this.priority = priority; return this; }

            public QuestStep.Builder description(String description) { this.description = description; return this; }

            public QuestStep.Builder criteria(CriterionTriggerInstance criteria) { this.criteria = criteria; return this; }

            public QuestStep build() {
                if (this.priority < 0) {
                    throw new IllegalStateException("QuestStep priority must be > 1!");
                } else if(this.criteria == null) {
                    throw new IllegalStateException("QuestStep must have a criteria!");
                } else {
                    return new QuestStep(priority, description, criteria);
                }
            }

            public JsonObject serialiseToJson() {
                JsonObject object = new JsonObject();

                object.addProperty("priority", this.priority);
                object.addProperty("description", this.description);
                object.add("criteria", this.criteria.serializeToJson(SerializationContext.INSTANCE));

                return object;
            }
        }

        @Override
        public int compareTo(QuestStep other) {
            return Integer.compare(other.priority, this.priority);
        }
    }

    public enum QuestCategory
    {
        ACTIVITY(0, "ACTIVITY"),
        ADVENTURE(1, "ADVENTURE"),
        BOUNTY(2, "BOUNTY"),
        LEGENDARY(3, "LEGENDARY");

        private int id;
        private String displayName;

        QuestCategory(int id, String displayName) {
            this.id = id;
            this.displayName = displayName;
        }

        public int getId() { return this.id; }

        public String getDisplayName() { return this.displayName; }

        @Override
        public String toString() { return this.displayName; }
    }

    public Quest.Builder deconstruct() {
        return new Quest.Builder(this.name, this.description, this.singleUse, this.questCategory, this.questSteps);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Quest)) {
            return false;
        } else {
            Quest quest = (Quest)obj;
            return this.id.equals(quest.id);
        }
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public static class Builder {
        private QuestCategory category;
        private PriorityQueue<QuestStep> steps = new PriorityQueue<>();
        private boolean singleUse = false;
        private String name;
        private String description;

        public Builder(String name, String description, boolean singleUse, QuestCategory category, PriorityQueue<QuestStep> steps) {
            this.name = name;
            this.description = description;
            this.singleUse = singleUse;
            this.category = category;
            this.steps = steps;
        }

        public Builder() {
        }

        public static Quest.Builder quest() {
            return new Quest.Builder();
        }

        public Quest.Builder name(String name) { this.name = name; return this; }

        public Quest.Builder description(String description) { this.description = description; return this; }

        public Quest.Builder singleUse() { this.singleUse = true; return this; }

        public Quest.Builder category(QuestCategory category) { this.category = category; return this; }

        public JsonObject serialiseToJson() {
            JsonObject jsonobject = new JsonObject();

            jsonobject.addProperty("name", name);
            jsonobject.addProperty("description", description);
            jsonobject.addProperty("single_use", singleUse);
            jsonobject.addProperty("category", category.id);

            JsonArray jsonSteps = new JsonArray();
            Arrays.sort(this.steps.toArray());
            for(QuestStep step : this.steps) {
                jsonSteps.add(step.deconstruct().serialiseToJson());
            }
            jsonobject.add("steps", jsonSteps);

            return jsonobject;
        }

        public Quest.Builder step(QuestStep step) {
            if(this.steps.contains(step)) {
                throw new IllegalArgumentException("Duplicate quest step: " + step);
            } else if(this.steps.stream().anyMatch(questStep -> questStep.priority == step.priority)) {
                throw new IllegalArgumentException("Duplicate quest step order: " + step.priority);
            } else {
                this.steps.add(step);
                return this;
            }
        }

        public Quest build(ResourceLocation location) {
            if (this.steps == null || this.steps.isEmpty()) {
                throw new IllegalStateException("Tried to build quest with no steps!");
            } else {
                return new Quest(location, this.name, this.description, this.singleUse, this.category, this.steps);
            }
        }

        public Quest save(Consumer<Quest> consumer, String location) {
            Quest quest = this.build(new ResourceLocation(location));
            consumer.accept(quest);
            return quest;
        }
    }
}