package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.spell.entity.FireballEntity;
import com.ryankshah.skyrimcraft.spell.entity.render.FireballRenderer;
import com.ryankshah.skyrimcraft.util.LangGenerator;
import com.ryankshah.skyrimcraft.util.ModBlocks;
import com.ryankshah.skyrimcraft.util.ModEntityType;
import com.ryankshah.skyrimcraft.util.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents
{
    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        if(event.includeServer()) {
            // Recipes
            // LootTables
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

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler((EntityType<FireballEntity>) ModEntityType.SPELL_FIREBALL_ENTITY.get(), FireballRenderer::new);
        // RenderingRegistry.registerEntityRenderingHandler(ModEntityType.ORCA.get(), manager -> new OrcaRenderer(manager));
        // RenderingRegistry.registerEntityRenderingHandler(ModEntityType.SEA_SNAKE.get(), manager -> new SeaSnakeRenderer(manager));
    }
}
