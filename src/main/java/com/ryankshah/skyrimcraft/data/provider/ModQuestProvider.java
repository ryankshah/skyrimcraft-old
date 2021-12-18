package com.ryankshah.skyrimcraft.data.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.quest.Quest;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.DataProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class ModQuestProvider implements DataProvider
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final List<Consumer<Consumer<Quest>>> tabs = ImmutableList.of(new ModQuestProvider.Quests());
    public static Map<String, Quest> QUESTS;
    private DataGenerator generator;

    public ModQuestProvider(DataGenerator generator) {
        QUESTS = new HashMap<>();
        this.generator = generator;
    }

    @Override
    public void run(HashCache p_200398_1_) throws IOException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Quest> consumer = (p_204017_3_) -> {
            if (!set.add(p_204017_3_.getId())) {
                throw new IllegalStateException("Duplicate quest " + p_204017_3_.getId());
            } else {
                Path path1 = createPath(path, p_204017_3_);

                try {
                    DataProvider.save(GSON, p_200398_1_, p_204017_3_.deconstruct().serialiseToJson(), path1);
                } catch (IOException ioexception) {
                    LOGGER.error("Couldn't save quest {}", path1, ioexception);
                }

            }
        };

        for (Consumer<Consumer<Quest>> consumer1 : this.tabs) {
            consumer1.accept(consumer);
        }
    }

    private static Path createPath(Path p_218428_0_, Quest p_218428_1_) {
        return p_218428_0_.resolve("data/" + p_218428_1_.getId().getNamespace() + "/" + p_218428_1_.getId().getPath() + ".json");
    }

    @Override
    public String getName() {
        return Skyrimcraft.MODID + "_quests";
    }

    static class Quests implements Consumer<Consumer<Quest>> {
        public void accept(Consumer<Quest> consumer) {
            QUESTS.put("kill_spider", Quest.Builder.quest().name("Kill Spider").description("Kill a spider!").category(Quest.QuestCategory.ACTIVITY)
                    .step(Quest.QuestStep.Builder.step().priority(1).description("Kill a Spider").criteria(KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityType.SPIDER))).build())
                    .step(Quest.QuestStep.Builder.step().priority(2).description("Kill a Spider").criteria(KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityType.SPIDER))).build())
                    .save(consumer, Skyrimcraft.MODID + ":quests/kill_spider"));
        }
    }
}
