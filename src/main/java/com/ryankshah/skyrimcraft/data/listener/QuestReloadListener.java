package com.ryankshah.skyrimcraft.data.listener;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;
import com.google.gson.*;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.quest.Quest;
import com.ryankshah.skyrimcraft.data.serializer.ModSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class QuestReloadListener extends SimpleJsonResourceReloadListener
{
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<Quest.QuestCategory, Map<ResourceLocation, Quest>> quests = ImmutableMap.of();
    private Map<ResourceLocation, Quest> byName = ImmutableMap.of();
    private boolean hasErrors;

    public QuestReloadListener() {
        super(GSON, Skyrimcraft.MODID + "_quests");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> p_10793_, ResourceManager p_10794_, ProfilerFiller p_10795_) {
        this.hasErrors = false;
        Map<Quest.QuestCategory, Builder<ResourceLocation, Quest>> map = Maps.newHashMap();
        Builder<ResourceLocation, Quest> builder = ImmutableMap.builder();

        for(Entry<ResourceLocation, JsonElement> entry : p_10793_.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            if (resourceLocation.getPath().startsWith("_")) continue; //Forge: filter anything beginning with "_" as it's used for metadata.

            try {
                Quest quest = fromJson(resourceLocation, GsonHelper.convertToJsonObject(entry.getValue(), "top element"));
                if(quest == null) {
                    LOGGER.info("Skipping loading quest {} as it's serializer returned null", resourceLocation);
                    continue;
                }
                map.computeIfAbsent(quest.getQuestCategory(), (p_44075_) -> {
                    return ImmutableMap.builder();
                }).put(resourceLocation, quest);
                builder.put(resourceLocation, quest);
            } catch(IllegalArgumentException | JsonParseException exception) {
                LOGGER.error("Parsing error loading quest {}", resourceLocation, exception);
            }
        }

        this.quests = map.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_44033_) -> {
            return p_44033_.getValue().build();
        }));
        this.byName = builder.build();
        LOGGER.info("Loaded {} quests", (int)map.size());
    }

    public boolean hadErrorsLoading() { return this.hasErrors; }

    public Optional<? extends Quest> byKey(ResourceLocation p_44044_) {
        return Optional.ofNullable(this.byName.get(p_44044_));
    }

    public Collection<Quest> getQuests() {
        return this.quests.values().stream().flatMap((p_199910_) -> {
            return p_199910_.values().stream();
        }).collect(Collectors.toSet());
    }

    public Stream<ResourceLocation> getQuestIds() {
        return this.quests.values().stream().flatMap((p_199904_) -> {
            return p_199904_.keySet().stream();
        });
    }

    // TODO: Finish this based off of @see{RecipeManager}

    public static Quest fromJson(ResourceLocation p_44046_, JsonObject p_44047_) {
        String s = GsonHelper.getAsString(p_44047_, "category");
        return ModSerializers.QUEST_SERIALIZER.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Invalid or unsupported quest category '" + s + "'");
        }).fromJson(p_44046_, p_44047_);
    }
}
