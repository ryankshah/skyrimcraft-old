package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import com.ryankshah.skyrimcraft.util.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEvents
{
    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        if(event.includeServer()) {
            // Recipes
            gen.addProvider(new ModRecipes(gen));
            // Loot Tables
            gen.addProvider(new ModLootTables(gen));
            // Global Loot Tables
            gen.addProvider(new ModGlobalLootTableProvider(gen));
            // Advancements
            gen.addProvider(new ModAdvancementProvider(gen));
        }
        if(event.includeClient()) {
            // BlockStates
            gen.addProvider(new ModBlocks.BlockStates(gen, event.getExistingFileHelper()));
            // Block Items
            gen.addProvider(new ModBlocks.BlockItems(gen, event.getExistingFileHelper()));
            // Items
            gen.addProvider(new ModItems.Items(gen, event.getExistingFileHelper()));
            // Lang
            gen.addProvider(new LangGenerator(gen, Skyrimcraft.MODID, "en_us"));
        }
    }

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        // ModSpawnEggItem.initSpawnEggs();
    }

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        SpellRegistry.registerRenderers();

        ClientRegistry.registerKeyBinding(ForgeClientEvents.toggleSkyrimMenu);
        ClientRegistry.registerKeyBinding(ForgeClientEvents.toggleSpellSlot1);
        ClientRegistry.registerKeyBinding(ForgeClientEvents.toggleSpellSlot2);

        ModBlocks.blockRenders();
    }
}
