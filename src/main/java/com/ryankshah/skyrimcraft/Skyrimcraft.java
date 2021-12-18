package com.ryankshah.skyrimcraft;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.mojang.serialization.Codec;
import com.ryankshah.skyrimcraft.advancement.BaseTrigger;
import com.ryankshah.skyrimcraft.advancement.TriggerManager;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.client.entity.ModEntityType;
import com.ryankshah.skyrimcraft.client.entity.boss.dragon.SkyrimDragon;
import com.ryankshah.skyrimcraft.client.entity.creature.GiantEntity;
import com.ryankshah.skyrimcraft.client.entity.creature.SabreCatEntity;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.BlueButterfly;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.TorchBug;
import com.ryankshah.skyrimcraft.data.ModRecipeSerializers;
import com.ryankshah.skyrimcraft.data.ModRecipeType;
import com.ryankshah.skyrimcraft.data.loot_table.condition.type.ModLootConditionTypes;
import com.ryankshah.skyrimcraft.data.provider.ModGlobalLootTableProvider;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.item.ModItems;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.util.ModAttributes;
import com.ryankshah.skyrimcraft.util.ModSounds;
import com.ryankshah.skyrimcraft.worldgen.WorldGen;
import com.ryankshah.skyrimcraft.worldgen.structure.ModConfiguredStructures;
import com.ryankshah.skyrimcraft.worldgen.structure.ModStructures;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO:
 *   - Continue working on the shouts and spells system:
 *     - Add more shouts and spells :)
 *   - Continue with adding some items, food, weapons and armour into the game
 *     - Do code + textures for lettuce crop and add lettuce item
 *     - Add some potions
 *       - Wellbeing potions
 *       - Regenerate magicka, and stamina
 *       - etc.
 *   - Merchants
 *     - Fix the merchant entity taking existing vanilla POI types...
 *     - Work on the merchant trading system (uses villager trading, but will have 1 level)
 *     - Work on merchant work times (like skyrim, some can be all day, some can be Xam-Ypm, etc.)
 *   - Work on the alchemy system
 *     - World generation for creep clusters and other ingredients
 *     - Add more ingredients
 *       - Add sabre cat pelt and sabre cat snow pelt -- add these + tooth for sabre cat drops
 *     - Sort out how ingredients will be obtainable (i.e. giants toes and others currently
 *       tradeable by villagers or gen in structures, but we need giants, mobs, etc.)
 *   - Add more oven recipes:
 *     - Apple Dumpling
 *     - Braided Bread (?)
 *     - Chicken Dumpling
 *     - Jazbay Crostata
 *     - Juniper Berry Crostata
 *     - Lavender Dumpling
 *     - Snowberry Crostata
 *   - Continue working on the ingame GUI overlay:
 *     - Render the boss health similarly to target entities?
 *   - Work on the character level system ( https://elderscrolls.fandom.com/wiki/Character_Level )
 *     - "Simply follow the prompts on the screen, select which of the three statistics to raise (Stamina, Health,
 *       or Magicka), then allocate perks to whichever skill tree the player wishes to advance. This can even be done
 *       in combat, and will refill whichever statistic is raised upon level-up, giving the potential to use a
 *       level-up as a means of recovery during a fight".
 *     - the hard limit is actually level 65,535 (Hex number FFFF). Although a level cap in 1.9 was 81.5
 *   - Work on a skills system (https://elderscrolls.fandom.com/wiki/Skills_(Skyrim))
 *     - Skills system is ready, but need to tinker with the xp rates and add in how XP gains for rest of skills.
 *     - Need to work on skills GUI
 *   - Start working on a quest system
 *     - Certain quests require a character level of either 2, 5, 10, 15, 17, 20, 25, 30, or 80 to start them.
 *   - Start working on a faction system
 *   - Get some dragons and implement a dragon souls system for the shoutss
 */
@Mod(Skyrimcraft.MODID)
public class Skyrimcraft
{
    // Directly reference a log4j logger.
    public static final String MODID = "skyrimcraft";
    public static final Logger LOGGER = LogManager.getLogger();

    public Skyrimcraft() {
        GeckoLib.initialize();

        ModLootConditionTypes.register();

        ModAttributes.ATTRIBUTES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntityType.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCK_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModSounds.SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        SpellRegistry.SPELLS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModRecipeType.register();
        ModRecipeSerializers.SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEffects.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModStructures.STRUCTURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModGlobalLootTableProvider.LOOT_MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::createEntityAttributes);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::addEntityAttributes);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, WorldGen::generate); // high for additions to worldgen
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Networking.registerMessages();
            // CapabilityManager.INSTANCE.register(ISkyrimPlayerData.class); // Moved to capabilityRegister (event) in CapabilityHandler

            // Add triggers
            for(RegistryObject<ISpell> spell : SpellRegistry.SPELLS.getEntries()) {
                BaseTrigger spellTrigger = new BaseTrigger("learned_spell_" + spell.get().getName().toLowerCase().replace(" ", "_"));
                TriggerManager.SPELL_TRIGGERS.put(spell.get(), spellTrigger);
            }
            TriggerManager.init();

            //ModDimensions.registerChunkGensAndBiomeSources();

            ModStructures.setupStructures();
            ModConfiguredStructures.registerConfiguredStructures();
        });
    }

    public void createEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityType.BLUE_BUTTERFLY.get(), BlueButterfly.createAttributes().build());
        event.put(ModEntityType.TORCHBUG.get(), TorchBug.createAttributes().build());
        event.put(ModEntityType.SABRE_CAT.get(), SabreCatEntity.createAttributes().build());
        event.put(ModEntityType.GIANT.get(), GiantEntity.createAttributes().build());
        event.put(ModEntityType.DRAGON.get(), SkyrimDragon.createAttributes().build());
    }

    public void addEntityAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.MAGICKA_REGEN.get());
    }

    // Custom ItemGroup TAB
    public static final CreativeModeTab TAB_BLOCKS = new CreativeModeTab("skyrimcraft.blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.EBONY_ORE.get());
        }
    };
    public static final CreativeModeTab TAB_INGREDIENTS = new CreativeModeTab("skyrimcraft.ingredients") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SALT_PILE.get());
        }
    };
    public static final CreativeModeTab TAB_MATERIALS = new CreativeModeTab("skyrimcraft.material") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.EBONY_INGOT.get());
        }
    };
    public static final CreativeModeTab TAB_FOOD = new CreativeModeTab("skyrimcraft.food") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.APPLE_PIE.get());
        }
    };
    public static final CreativeModeTab TAB_COMBAT = new CreativeModeTab("skyrimcraft.combat") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.DAEDRIC_SWORD.get());
        }
    };
    public static final CreativeModeTab TAB_MAGIC = new CreativeModeTab("skyrimcraft.magic") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.FIREBALL_SPELLBOOK.get());
        }
    };

    /**
     * Tells the chunkgenerator which biomes our structure can spawn in.
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
        if(event.getWorld() instanceof ServerLevel serverLevel){
            ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
            // Skip superflat to prevent issues with it. Plus, users don't want structures clogging up their superflat worlds.
            if (chunkGenerator instanceof FlatLevelSource && serverLevel.dimension().equals(Level.OVERWORLD)) {
                return;
            }

            StructureSettings worldStructureConfig = chunkGenerator.getSettings();

            //////////// BIOME BASED STRUCTURE SPAWNING ////////////
            /*
             * NOTE: BiomeLoadingEvent from Forge API does not work with structures anymore.
             * Instead, we will use the below to add our structure to overworld biomes.
             * Remember, this is temporary until Forge API finds a better solution for adding structures to biomes.
             */

            // Create a mutable map we will use for easier adding to biomes
            HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> STStructureToMultiMap = new HashMap<>();

            // Add the resourcekey of all biomes that this Configured Structure can spawn in.
            for(Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : serverLevel.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY).entrySet()) {
                // Skip all ocean, end, nether, and none category biomes.
                // You can do checks for other traits that the biome has.
                Biome.BiomeCategory biomeCategory = biomeEntry.getValue().getBiomeCategory();
                if(biomeCategory != Biome.BiomeCategory.OCEAN && biomeCategory != Biome.BiomeCategory.THEEND && biomeCategory != Biome.BiomeCategory.NETHER && biomeCategory != Biome.BiomeCategory.NONE) {
                    associateBiomeToConfiguredStructure(STStructureToMultiMap, ModConfiguredStructures.CONFIGURED_SHOUT_WALL, biomeEntry.getKey());
                }
            }

            // Alternative way to add our structures to a fixed set of biomes by creating a set of biome resource keys.
            // To create a custom resource key that points to your own biome, do this:
            // ResourceKey.of(Registry.BIOME_REGISTRY, new ResourceLocation("modid", "custom_biome"))
//            ImmutableSet<ResourceKey<Biome>> overworldBiomes = ImmutableSet.<ResourceKey<Biome>>builder()
//                    .add(Biomes.FOREST)
//                    .add(Biomes.MEADOW)
//                    .add(Biomes.PLAINS)
//                    .add(Biomes.SAVANNA)
//                    .add(Biomes.SNOWY_PLAINS)
//                    .add(Biomes.SWAMP)
//                    .add(Biomes.SUNFLOWER_PLAINS)
//                    .add(Biomes.TAIGA)
//                    .build();
//            overworldBiomes.forEach(biomeKey -> associateBiomeToConfiguredStructure(STStructureToMultiMap, STConfiguredStructures.CONFIGURED_RUN_DOWN_HOUSE, biomeKey));

            // Grab the map that holds what ConfigureStructures a structure has and what biomes it can spawn in.
            // Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
            ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap = ImmutableMap.builder();
            worldStructureConfig.configuredStructures.entrySet().stream().filter(entry -> !STStructureToMultiMap.containsKey(entry.getKey())).forEach(tempStructureToMultiMap::put);

            // Add our structures to the structure map/multimap and set the world to use this combined map/multimap.
            STStructureToMultiMap.forEach((key, value) -> tempStructureToMultiMap.put(key, ImmutableMultimap.copyOf(value)));

            // Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
            worldStructureConfig.configuredStructures = tempStructureToMultiMap.build();


            //////////// DIMENSION BASED STRUCTURE SPAWNING (OPTIONAL) ////////////
            /*
             * Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
             * They will handle your structure spacing for your if you add to BuiltinRegistries.NOISE_GENERATOR_SETTINGS in your structure's registration.
             * This here is done with reflection as this tutorial is not about setting up and using Mixins.
             * If you are using mixins, you can call the codec method with an invoker mixin instead of using reflection.
             */
            try {
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(chunkGenerator));
                if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            }
            catch(Exception e){
                Skyrimcraft.LOGGER.error("Was unable to check if " + serverLevel.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            /*
             * Prevent spawning our structure in Vanilla's superflat world as
             * people seem to want their superflat worlds free of modded structures.
             * Also that vanilla superflat is really tricky and buggy to work with in my experience.
             */
            if(chunkGenerator instanceof FlatLevelSource &&
                    serverLevel.dimension().equals(Level.OVERWORLD)){
                return;
            }

            /*
             * putIfAbsent so people can override the spacing with dimension datapacks themselves if they wish to customize spacing more precisely per dimension.
             * Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
             *
             * NOTE: if you add per-dimension spacing configs, you can't use putIfAbsent as BuiltinRegistries.NOISE_GENERATOR_SETTINGS in FMLCommonSetupEvent
             * already added your default structure spacing to some dimensions. You would need to override the spacing with .put(...)
             * And if you want to do dimension blacklisting, you need to remove the spacing entry entirely from the map below to prevent generation safely.
             */
            Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(worldStructureConfig.structureConfig());
            tempMap.putIfAbsent(ModStructures.SHOUT_WALL.get(), StructureSettings.DEFAULTS.get(ModStructures.SHOUT_WALL.get()));
            worldStructureConfig.structureConfig = tempMap;
        }
    }
    /**
     * Helper method that handles setting up the map to multimap relationship to help prevent issues.
     */
    private static void associateBiomeToConfiguredStructure(Map<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> STStructureToMultiMap, ConfiguredStructureFeature<?, ?> configuredStructureFeature, ResourceKey<Biome> biomeRegistryKey) {
        STStructureToMultiMap.putIfAbsent(configuredStructureFeature.feature, HashMultimap.create());
        HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> configuredStructureToBiomeMultiMap = STStructureToMultiMap.get(configuredStructureFeature.feature);
        if(configuredStructureToBiomeMultiMap.containsValue(biomeRegistryKey)) {
            Skyrimcraft.LOGGER.error("""
                    Detected 2 ConfiguredStructureFeatures that share the same base StructureFeature trying to be added to same biome. One will be prevented from spawning.
                    This issue happens with vanilla too and is why a Snowy Village and Plains Village cannot spawn in the same biome because they both use the Village base structure.
                    The two conflicting ConfiguredStructures are: {}, {}
                    The biome that is attempting to be shared: {}
                """,
                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureFeature),
                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureToBiomeMultiMap.entries().stream().filter(e -> e.getValue() == biomeRegistryKey).findFirst().get().getKey()),
                    biomeRegistryKey
            );
        }
        else{
            configuredStructureToBiomeMultiMap.put(configuredStructureFeature, biomeRegistryKey);
        }
    }
}
