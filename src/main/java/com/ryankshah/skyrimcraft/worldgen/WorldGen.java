package com.ryankshah.skyrimcraft.worldgen;

import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.client.entity.ModEntityType;
import com.ryankshah.skyrimcraft.worldgen.ore.OreConfig;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class WorldGen
{
    public static Holder<PlacedFeature> EBONY_ORE_OVERWORLD_OREGEN;
    public static Holder<PlacedFeature> EBONY_ORE_DEEPSLATE_OREGEN;

    public static void registerConfiguredFeatures() {
        OreConfiguration ebonyOreOverworldConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.EBONY_ORE.get().defaultBlockState(), OreConfig.EBONY_ORE_OVERWORLD_VEINSIZE.get());
        EBONY_ORE_OVERWORLD_OREGEN = registerPlacedFeature("ebony_ore_feature", new ConfiguredFeature<>(Feature.ORE, ebonyOreOverworldConfig),
                CountPlacement.of(OreConfig.EBONY_ORE_OVERWORLD_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(), // new DimensionBiomeFilter(key -> !Dimensions.MYSTERIOUS.equals(key)),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90)));

        OreConfiguration ebonyOreDeepslateConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.EBONY_ORE_DEEPSLATE.get().defaultBlockState(), OreConfig.EBONY_ORE_DEEPSLATE_VEINSIZE.get());
        EBONY_ORE_DEEPSLATE_OREGEN = registerPlacedFeature("ebony_ore_deepslate_feature", new ConfiguredFeature<>(Feature.ORE, ebonyOreDeepslateConfig),
                CountPlacement.of(OreConfig.EBONY_ORE_DEEPSLATE_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(64)));

    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<PlacedFeature> registerPlacedFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        return PlacementUtils.register(registryName, Holder.direct(feature), placementModifiers);
    }

    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.BiomeCategory.NETHER) {
        } else if (event.getCategory() == Biome.BiomeCategory.THEEND) {
        } else if(event.getName().equals(Biomes.FLOWER_FOREST.location()) || event.getName().equals(Biomes.SUNFLOWER_PLAINS.location())) {
            System.out.println(event.getName());
            event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(ModEntityType.BLUE_BUTTERFLY.get(), 100, 2, 4));
        } else {
            event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(ModEntityType.TORCHBUG.get(), 6, 3, 6));
        //} else {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EBONY_ORE_OVERWORLD_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EBONY_ORE_DEEPSLATE_OREGEN);

            // Handle mob spawns
            event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(ModEntityType.SABRE_CAT.get(), 2, 1, 2));
        }
    }

//    private static List<PlacedFeature> orePlacedFeatures = new ArrayList<>();
//    private static List<PlacedFeature> vegetalPlacedFeatures = new ArrayList<>();
//
//    public static void registerPlacements() {
//        generateOre("ebony_ore",
//                ModBlocks.EBONY_ORE.get().defaultBlockState(), 2, 6, 10, 30);
//        generateOre("malachite_ore",
//                ModBlocks.MALACHITE_ORE.get().defaultBlockState(), 3, 10, 15, 45);
//        generateOre("moonstone_ore",
//                ModBlocks.MOONSTONE_ORE.get().defaultBlockState(), 4, 12, 34, 60);
//        generateOre("orichalcum_ore",
//                ModBlocks.ORICHALCUM_ORE.get().defaultBlockState(), 3, 12, 30, 50);
//        generateOre("corundum_ore",
//                ModBlocks.CORUNDUM_ORE.get().defaultBlockState(), 3, 12, 30, 50);
//        generateOre("quicksilver_ore",
//                ModBlocks.QUICKSILVER_ORE.get().defaultBlockState(), 3, 12, 30, 50);
//        generateOre("silver_ore",
//                ModBlocks.SILVER_ORE.get().defaultBlockState(), 3, 12, 30, 50);
//
//        // Salt pile
//        //if (event.getCategory().equals(Biome.BiomeCategory.DESERT) || event.getCategory().equals(Biome.BiomeCategory.MESA) || event.getCategory().equals(Biome.BiomeCategory.SAVANNA))
//        generateSalt();
//
//        vegetalPlacedFeatures.add(PlacementUtils.register("mountain_flowers", FeatureUtils.register("mountain_flower_default", Feature.FLOWER.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ModBlocks.RED_MOUNTAIN_FLOWER.get().defaultBlockState(), 1).add(ModBlocks.BLUE_MOUNTAIN_FLOWER.get().defaultBlockState(), 1).add(ModBlocks.PURPLE_MOUNTAIN_FLOWER.get().defaultBlockState(), 1).add(ModBlocks.YELLOW_MOUNTAIN_FLOWER.get().defaultBlockState(), 1)), 64))).placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()))); // onAverageOnceEvery(16)
//    }
//
//    public static void generate(final BiomeLoadingEvent event) {
//        if (!(event.getCategory().equals(Biome.BiomeCategory.NETHER) || event.getCategory().equals(Biome.BiomeCategory.THEEND))) {
//            // Mobs
//            for(ResourceKey<Biome> biomeKey : SABRE_CAT_BIOMES) {
//                ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
//                if(biomeKey.equals(key))
//                    event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.SABRE_CAT.get(), 4, 1, 3));
//            }
//
//            for(PlacedFeature f : orePlacedFeatures)
//                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, f.feature());
//
//            for(PlacedFeature f : vegetalPlacedFeatures)
//                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, f);
//
//            // Flowers
//            //event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
//        }
//    }
//
//    // TODO: Register world gen entries (configured features and placement utils) in variables for each feature in
//    //       `FMLCommonSetupEvent#enqueueWork` and then add feature vars to generation in `BiomeLoadingEvent`
//
//    private static void generateOre(String name, BlockState state, int veinSize, int veinsPerChunk, int minHeight, int maxHeight) {
//        List<OreConfiguration.TargetBlockState> targetStates = List.of(OreConfiguration.target(OreFeatures.NATURAL_STONE, state));
//        ConfiguredFeature<?, ?> configuredFeature = FeatureUtils.register(Skyrimcraft.MODID + ":block/" + name, Feature.ORE.configured(new OreConfiguration(targetStates, veinSize)));
//        PlacedFeature placedFeature = PlacementUtils.register(Skyrimcraft.MODID + ":ore/" + name, configuredFeature.place(List.of(
//                CountPlacement.of(veinsPerChunk),
//                InSquarePlacement.spread(),
//                HeightRangePlacement.triangle(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)),
//                BiomeFilter.biome()
//        )));
//        orePlacedFeatures.add(placedFeature);
//        //settings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, placedFeature);
//    }
//
//    private static void generateSalt() {
//        vegetalPlacedFeatures.add(PlacementUtils.register("salt_patch", FeatureUtils.register("salt_patch_default", Feature.RANDOM_PATCH.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ModBlocks.SALT_DEPOSIT.get().defaultBlockState(), 1)), 64))).placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()))); // onAverageOnceEvery(16)
//    }
//
//    private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
//        return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(p_195203_)).onlyWhenEmpty());
//    }
}