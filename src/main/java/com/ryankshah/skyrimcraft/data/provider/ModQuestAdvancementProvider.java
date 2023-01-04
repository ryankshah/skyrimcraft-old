package com.ryankshah.skyrimcraft.data.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.ModEntityType;
import com.ryankshah.skyrimcraft.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class ModQuestAdvancementProvider implements DataProvider, IConditionBuilder
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final List<Consumer<Consumer<Advancement>>> tabs = ImmutableList.of(new QuestAdvancements());
    private DataGenerator generator;

    public static Map<String, Advancement> ADVANCEMENTS;

    private ResourceLocation namespace = null;

    public ModQuestAdvancementProvider(DataGenerator generatorIn) {
        ADVANCEMENTS = new HashMap<>();
        this.generator = generatorIn;
    }

    @Override
    public void run(HashCache p_200398_1_) throws IOException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (p_204017_3_) -> {
            if (!set.add(p_204017_3_.getId())) {
                throw new IllegalStateException("Duplicate advancement " + p_204017_3_.getId());
            } else {
                Path path1 = createPath(path, p_204017_3_);

                try {
                    DataProvider.save(GSON, p_200398_1_, p_204017_3_.deconstruct().serializeToJson(), path1);
                } catch (IOException ioexception) {
                    LOGGER.error("Couldn't save advancement {}", path1, ioexception);
                }

            }
        };

        for(Consumer<Consumer<Advancement>> consumer1 : this.tabs) {
            consumer1.accept(consumer);
        }

    }

    private static Path createPath(Path p_218428_0_, Advancement p_218428_1_) {
        return p_218428_0_.resolve("data/" + p_218428_1_.getId().getNamespace() + "/advancements/" + p_218428_1_.getId().getPath() + ".json");
    }

    static class QuestAdvancements implements Consumer<Consumer<Advancement>> {
        public void accept(Consumer<Advancement> p_accept_1_) {
            Advancement skyrimcraft = Advancement.Builder.advancement().display(ModItems.SWEET_ROLL.get(), new TextComponent("Skyrimcraft"), new TextComponent("Your adventure begins here..."), new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, false, false, false).addCriterion("skyrimcraft_login", LocationTrigger.TriggerInstance.located(LocationPredicate.ANY)).save(p_accept_1_, Skyrimcraft.MODID+":root");
            Advancement quests = Advancement.Builder.advancement().parent(skyrimcraft).display(ModItems.IRON_HELMET.get(), new TextComponent("Quests"), new TextComponent("Skyrimcraft Quests"), (ResourceLocation)null, FrameType.CHALLENGE, false, false, true).addCriterion("quests_list", new ImpossibleTrigger.TriggerInstance()).save(p_accept_1_, Skyrimcraft.MODID+":quests/root");
            ADVANCEMENTS.put("root", skyrimcraft);
            ADVANCEMENTS.put("quests", quests);

            Advancement ebony_dreams = Advancement.Builder.advancement().parent(quests).display(ModItems.EBONY_INGOT.get(), new TextComponent("Ebony Dreams"), new TextComponent("Smelt your first Ebony Ingot"), (ResourceLocation) null, FrameType.TASK, true, false, true).addCriterion("smelt_ebony_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EBONY_INGOT.get())).save(p_accept_1_, Skyrimcraft.MODID + ":quests/ebony_dreams");
            Advancement goodbye_webs = Advancement.Builder.advancement().parent(quests).display(Items.STRING, new TextComponent("Goodbye, Webs"), new TextComponent("Kill a Spider"), (ResourceLocation) null, FrameType.TASK, true, false, true).addCriterion("kill_spider", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityType.SPIDER)).build())).save(p_accept_1_, Skyrimcraft.MODID + ":quests/goodbye_webs");
            Advancement dragon_slayer = Advancement.Builder.advancement().parent(quests).display(ModItems.DRAGONBONE_SWORD.get(), new TextComponent("Dragon Slayer"), new TextComponent("Kill your first Skyrim dragon!"), (ResourceLocation) null, FrameType.CHALLENGE, true, false, true).addCriterion("kill_dragon", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(ModEntityType.DRAGON.get())).build())).save(p_accept_1_, Skyrimcraft.MODID + ":quests/dragon_slayer");
            ADVANCEMENTS.put("ebony_dreams", ebony_dreams);
        }
    }

    @Override
    public String getName() {
        return Skyrimcraft.MODID+"_quests";
    }
}