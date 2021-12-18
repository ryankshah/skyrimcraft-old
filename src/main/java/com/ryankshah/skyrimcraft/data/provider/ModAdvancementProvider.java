package com.ryankshah.skyrimcraft.data.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.advancement.BaseTrigger;
import com.ryankshah.skyrimcraft.advancement.TriggerManager;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.LocationTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public class ModAdvancementProvider implements DataProvider, IConditionBuilder
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final List<Consumer<Consumer<Advancement>>> tabs = ImmutableList.of(new SpellAdvancements());
    private DataGenerator generator;

    public static Map<String, Advancement> ADVANCEMENTS;

    private ResourceLocation namespace = null;

    public ModAdvancementProvider(DataGenerator generatorIn) {
        for(RegistryObject<ISpell> spell : SpellRegistry.SPELLS.getEntries()) {
            BaseTrigger spellTrigger = new BaseTrigger("learned_spell_" + spell.get().getName().toLowerCase().replace(" ", "_"));
            TriggerManager.SPELL_TRIGGERS.put(spell.get(), spellTrigger);
        }
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

    static class SpellAdvancements implements Consumer<Consumer<Advancement>> {
        public void accept(Consumer<Advancement> p_accept_1_) {
            Advancement skyrimcraft = Advancement.Builder.advancement().display(ModItems.SWEET_ROLL.get(), new TextComponent("Skyrimcraft"), new TextComponent("Your adventure begins here..."), new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, false, false, false).addCriterion("skyrimcraft_login", LocationTrigger.TriggerInstance.located(LocationPredicate.ANY)).save(p_accept_1_, Skyrimcraft.MODID+":root");
            Advancement spells = Advancement.Builder.advancement().parent(skyrimcraft).display(ModItems.FIREBALL_SPELLBOOK.get(), new TextComponent("Spells"), new TextComponent("Learned spells"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).addCriterion("spells_list", new ImpossibleTrigger.TriggerInstance()).save(p_accept_1_, Skyrimcraft.MODID+":spell/root");
            Advancement shouts = Advancement.Builder.advancement().parent(skyrimcraft).display(ModBlocks.SHOUT_BLOCK.get(), new TextComponent("Shouts"), new TextComponent("Learnt shouts"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).addCriterion("shouts_list", new ImpossibleTrigger.TriggerInstance()).save(p_accept_1_, Skyrimcraft.MODID+":shout/root");
            ADVANCEMENTS.put("root", skyrimcraft);
            ADVANCEMENTS.put("spells", spells);
            ADVANCEMENTS.put("shouts", shouts);

            for(RegistryObject<ISpell> spell : SpellRegistry.SPELLS.getEntries()) {
                BaseTrigger.Instance trigger = BaseTrigger.Instance.get(TriggerManager.SPELL_TRIGGERS.get(spell.get()).getId());

                ItemLike provider = spell.get().getType() == ISpell.SpellType.SHOUT ? ModBlocks.SHOUT_BLOCK.get() : ModItems.FIREBALL_SPELLBOOK.get();

                Advancement adv = Advancement.Builder.advancement().parent(spell.get().getType() == ISpell.SpellType.SHOUT ? shouts : spells).display(provider, new TextComponent(spell.get().getName()), new TextComponent("Learn the " + spell.get().getName() + " " + (spell.get().getType() == ISpell.SpellType.SHOUT ? "shout" : "spell")), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).addCriterion("spell_learned_" + spell.get().getName().toLowerCase(Locale.ENGLISH).replace(" ", "_"), trigger).save(p_accept_1_, Skyrimcraft.MODID + ":" + (spell.get().getType() == ISpell.SpellType.SHOUT ? "shout" : "spell") + "/" + spell.get().getName().toLowerCase(Locale.ENGLISH).replace(" ", "_"));
                ADVANCEMENTS.put(spell.get().getName().toLowerCase(Locale.ENGLISH).replace(" ", "_"), adv);
            }
        }
    }

    @Override
    public String getName() {
        return Skyrimcraft.MODID+"_advancements";
    }
}