package com.ryankshah.skyrimcraft;

import com.mojang.serialization.Codec;
import com.ryankshah.skyrimcraft.advancement.BaseTrigger;
import com.ryankshah.skyrimcraft.advancement.TriggerManager;
import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.capability.SkyrimPlayerData;
import com.ryankshah.skyrimcraft.capability.SkyrimPlayerDataStorage;
import com.ryankshah.skyrimcraft.data.modifier.ModGlobalLootTableProvider;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import com.ryankshah.skyrimcraft.util.*;
import com.ryankshah.skyrimcraft.worldgen.WorldGen;
import com.ryankshah.skyrimcraft.worldgen.structure.ModConfiguredStructures;
import com.ryankshah.skyrimcraft.worldgen.structure.ModStructures;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO:
 *   - Continue working on the shouts and spells system:
 *     - Add more shouts and spells :)
 *   - Continue with adding some items, food, weapons and armour into the game
 *     - Add butter item (crafted by magma cream + bucket of milk) - possibly add churn block later or something..
 *     - Do code + textures for lettuce crop and add lettuce item
 *   - Add oven recipes:
 *     - Apple Dumpling
 *     - Apple Pie
 *     - Braided Bread (?)
 *     - Chicken Dumpling
 *     - Jazbay Crostata
 *     - Juniper Berry Crostata
 *     - Lavender Dumpling
 *     - Snowberry Crostata
 *   - Continue working on the ingame GUI overlay:
 *     - Mob indicators and *known* structures indicators in compass
 *       - Keep track of entities targeting the player and display these indicators in the compass
 *   - Fix the positioning of the text + icons in SkyrimMenuScreen
 */
@Mod(Skyrimcraft.MODID)
public class Skyrimcraft
{
    // Directly reference a log4j logger.
    public static final String MODID = "skyrimcraft";
    public static final Logger LOGGER = LogManager.getLogger();

    public Skyrimcraft() {
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCK_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModSounds.SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        SpellRegistry.SPELLS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntityType.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEffects.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModStructures.STRUCTURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModGlobalLootTableProvider.LOOT_MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(Skyrimcraft::commonSetup);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, WorldGen::generate); // high for additions to worldgen
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, this::biomeModification);


        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void commonSetup(final FMLCommonSetupEvent event) {
        Networking.registerMessages();
        CapabilityManager.INSTANCE.register(ISkyrimPlayerData.class, new SkyrimPlayerDataStorage(), SkyrimPlayerData::new);

        // Add triggers
        for(RegistryObject<ISpell> spell : SpellRegistry.SPELLS.getEntries()) {
            BaseTrigger spellTrigger = new BaseTrigger("learned_spell_" + spell.get().getName().toLowerCase().replace(" ", "_"));
            TriggerManager.TRIGGERS.put(spell.get(), spellTrigger);
        }
        TriggerManager.init();

        ModStructures.setupStructures();
        ModConfiguredStructures.registerConfiguredStructures();
    }

//    @SubscribeEvent
//    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
//        event.put(ModEntityTypes.EARTH_GOD.get(), EarthGodEntity.createAttributes().build());
//    }

    // Custom ItemGroup TAB
    public static final ItemGroup TAB = new ItemGroup("skyrimcraft") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.EBONY_ORE.get());
        }
    };

    public void biomeModification(final BiomeLoadingEvent event) {
        /*
         * Add our structure to all biomes including other modded biomes.
         * You can skip or add only to certain biomes based on stuff like biome category,
         * temperature, scale, precipitation, mod id, etc. All kinds of options!
         *
         * You can even use the BiomeDictionary as well! To use BiomeDictionary, do
         * RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName()) to get the biome's
         * registrykey. Then that can be fed into the dictionary to get the biome's types.
         */
//        if(event.getName().equals(Biomes.MOUNTAINS) || event.getName().equals(Biomes.MOUNTAIN_EDGE)
//            || event.getName().equals(Biomes.SNOWY_MOUNTAINS) || event.getName().equals(Biomes.SNOWY_TAIGA_MOUNTAINS)
//            || event.getName().equals(Biomes.TAIGA_MOUNTAINS) || event.getName().equals(Biomes.WOODED_MOUNTAINS)
//            || event.getName().equals(Biomes.GRAVELLY_MOUNTAINS) || event.getName().equals(Biomes.MODIFIED_GRAVELLY_MOUNTAINS))
        event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_SHOUT_WALL);
    }

    /**
     * Will go into the world's chunkgenerator and manually add our structure spacing.
     * If the spacing is not added, the structure doesn't spawn.
     *
     * Use this for dimension blacklists for your structure.
     * (Don't forget to attempt to remove your structure too from the map if you are blacklisting that dimension!)
     * (It might have your structure in it already.)
     *
     * Basically use this to make absolutely sure the chunkgenerator can or cannot spawn your structure.
     */
    private static Method GETCODEC_METHOD;
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            /*
             * Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
             * They will handle your structure spacing for your if you add to WorldGenRegistries.NOISE_GENERATOR_SETTINGS in your structure's registration.
             * This here is done with reflection as this tutorial is not about setting up and using Mixins.
             * If you are using mixins, you can call the codec method with an invoker mixin instead of using reflection.
             */
            try {
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            }
            catch(Exception e){
                Skyrimcraft.LOGGER.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            /*
             * Prevent spawning our structure in Vanilla's superflat world as
             * people seem to want their superflat worlds free of modded structures.
             * Also that vanilla superflat is really tricky and buggy to work with in my experience.
             */
            if(serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.dimension().equals(World.OVERWORLD)){
                return;
            }

            /*
             * putIfAbsent so people can override the spacing with dimension datapacks themselves if they wish to customize spacing more precisely per dimension.
             * Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
             *
             * NOTE: if you add per-dimension spacing configs, you can't use putIfAbsent as WorldGenRegistries.NOISE_GENERATOR_SETTINGS in FMLCommonSetupEvent
             * already added your default structure spacing to some dimensions. You would need to override the spacing with .put(...)
             * And if you want to do dimension blacklisting, you need to remove the spacing entry entirely from the map below to prevent generation safely.
             */
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(ModStructures.SHOUT_WALL.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.SHOUT_WALL.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }
}
