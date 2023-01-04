package com.ryankshah.skyrimcraft.worldgen;

import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.client.entity.ModEntityType;
import com.ryankshah.skyrimcraft.worldgen.ore.OreConfig;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class WorldGen
{
    public static Holder<PlacedFeature> EBONY_ORE_OVERWORLD_OREGEN;
    public static Holder<PlacedFeature> EBONY_ORE_DEEPSLATE_OREGEN;

    public static Holder<PlacedFeature> MALACHITE_ORE_OVERWORLD_OREGEN;
    public static Holder<PlacedFeature> MALACHITE_ORE_DEEPSLATE_OREGEN;

    public static Holder<PlacedFeature> MOONSTONE_ORE_OVERWORLD_OREGEN;
    public static Holder<PlacedFeature> MOONSTONE_ORE_DEEPSLATE_OREGEN;

    public static Holder<PlacedFeature> ORICHALCUM_ORE_OVERWORLD_OREGEN;
    public static Holder<PlacedFeature> ORICHALCUM_ORE_DEEPSLATE_OREGEN;

    public static Holder<PlacedFeature> CORUNDUM_ORE_OVERWORLD_OREGEN;
    public static Holder<PlacedFeature> CORUNDUM_ORE_DEEPSLATE_OREGEN;

    public static Holder<PlacedFeature> QUICKSILVER_ORE_OVERWORLD_OREGEN;
    public static Holder<PlacedFeature> QUICKSILVER_ORE_DEEPSLATE_OREGEN;

    public static Holder<PlacedFeature> SILVER_ORE_OVERWORLD_OREGEN;
    public static Holder<PlacedFeature> SILVER_ORE_DEEPSLATE_OREGEN;

    public static Holder<PlacedFeature> MOUNTAIN_FLOWER_DEFAULT;
    public static Holder<PlacedFeature> SALT_PATCH;

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

        OreConfiguration malachiteOreOverworldConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.MALACHITE_ORE.get().defaultBlockState(), OreConfig.MALACHITE_ORE_OVERWORLD_VEINSIZE.get());
        MALACHITE_ORE_OVERWORLD_OREGEN = registerPlacedFeature("malachite_ore_feature", new ConfiguredFeature<>(Feature.ORE, malachiteOreOverworldConfig),
                CountPlacement.of(OreConfig.MALACHITE_ORE_OVERWORLD_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90)));
        OreConfiguration malachiteOreDeepslateConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.MALACHITE_ORE_DEEPSLATE.get().defaultBlockState(), OreConfig.MALACHITE_ORE_DEEPSLATE_VEINSIZE.get());
        MALACHITE_ORE_DEEPSLATE_OREGEN = registerPlacedFeature("malachite_ore_deepslate_feature", new ConfiguredFeature<>(Feature.ORE, malachiteOreDeepslateConfig),
                CountPlacement.of(OreConfig.MALACHITE_ORE_DEEPSLATE_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(64)));

        OreConfiguration moonstoneOreOverworldConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.MOONSTONE_ORE.get().defaultBlockState(), OreConfig.MOONSTONE_ORE_OVERWORLD_VEINSIZE.get());
        MOONSTONE_ORE_OVERWORLD_OREGEN = registerPlacedFeature("moonstone_ore_feature", new ConfiguredFeature<>(Feature.ORE, moonstoneOreOverworldConfig),
                CountPlacement.of(OreConfig.MOONSTONE_ORE_OVERWORLD_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90)));
        OreConfiguration moonstoneOreDeepslateConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.MOONSTONE_ORE_DEEPSLATE.get().defaultBlockState(), OreConfig.MOONSTONE_ORE_DEEPSLATE_VEINSIZE.get());
        MOONSTONE_ORE_DEEPSLATE_OREGEN = registerPlacedFeature("moonstone_ore_deepslate_feature", new ConfiguredFeature<>(Feature.ORE, moonstoneOreDeepslateConfig),
                CountPlacement.of(OreConfig.MOONSTONE_ORE_DEEPSLATE_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(64)));

        OreConfiguration orichalcumOreOverworldConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.ORICHALCUM_ORE.get().defaultBlockState(), OreConfig.ORICHALCUM_ORE_OVERWORLD_VEINSIZE.get());
        ORICHALCUM_ORE_OVERWORLD_OREGEN = registerPlacedFeature("orichalcum_ore_feature", new ConfiguredFeature<>(Feature.ORE, orichalcumOreOverworldConfig),
                CountPlacement.of(OreConfig.ORICHALCUM_ORE_OVERWORLD_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90)));
        OreConfiguration orichalcumOreDeepslateConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.ORICHALCUM_ORE_DEEPSLATE.get().defaultBlockState(), OreConfig.ORICHALCUM_ORE_DEEPSLATE_VEINSIZE.get());
        ORICHALCUM_ORE_DEEPSLATE_OREGEN = registerPlacedFeature("orichalcum_ore_deepslate_feature", new ConfiguredFeature<>(Feature.ORE, orichalcumOreDeepslateConfig),
                CountPlacement.of(OreConfig.ORICHALCUM_ORE_DEEPSLATE_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(64)));

        OreConfiguration corundumOreOverworldConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.CORUNDUM_ORE.get().defaultBlockState(), OreConfig.CORUNDUM_ORE_OVERWORLD_VEINSIZE.get());
        CORUNDUM_ORE_OVERWORLD_OREGEN = registerPlacedFeature("corundum_ore_feature", new ConfiguredFeature<>(Feature.ORE, corundumOreOverworldConfig),
                CountPlacement.of(OreConfig.CORUNDUM_ORE_OVERWORLD_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90)));
        OreConfiguration corundumOreDeepslateConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.CORUNDUM_ORE_DEEPSLATE.get().defaultBlockState(), OreConfig.CORUNDUM_ORE_DEEPSLATE_VEINSIZE.get());
        CORUNDUM_ORE_DEEPSLATE_OREGEN = registerPlacedFeature("corundum_ore_deepslate_feature", new ConfiguredFeature<>(Feature.ORE, corundumOreDeepslateConfig),
                CountPlacement.of(OreConfig.CORUNDUM_ORE_DEEPSLATE_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(64)));

        OreConfiguration quicksilverOreOverworldConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.QUICKSILVER_ORE.get().defaultBlockState(), OreConfig.QUICKSILVER_ORE_OVERWORLD_VEINSIZE.get());
        QUICKSILVER_ORE_OVERWORLD_OREGEN = registerPlacedFeature("quicksilver_ore_feature", new ConfiguredFeature<>(Feature.ORE, quicksilverOreOverworldConfig),
                CountPlacement.of(OreConfig.QUICKSILVER_ORE_OVERWORLD_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90)));
        OreConfiguration quicksilverOreDeepslateConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.QUICKSILVER_ORE_DEEPSLATE.get().defaultBlockState(), OreConfig.QUICKSILVER_ORE_DEEPSLATE_VEINSIZE.get());
        QUICKSILVER_ORE_DEEPSLATE_OREGEN = registerPlacedFeature("quicksilver_ore_deepslate_feature", new ConfiguredFeature<>(Feature.ORE, quicksilverOreDeepslateConfig),
                CountPlacement.of(OreConfig.QUICKSILVER_ORE_DEEPSLATE_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(64)));

        OreConfiguration silverOreOverworldConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.SILVER_ORE.get().defaultBlockState(), OreConfig.SILVER_ORE_OVERWORLD_VEINSIZE.get());
        SILVER_ORE_OVERWORLD_OREGEN = registerPlacedFeature("silver_ore_feature", new ConfiguredFeature<>(Feature.ORE, silverOreOverworldConfig),
                CountPlacement.of(OreConfig.SILVER_ORE_OVERWORLD_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90)));
        OreConfiguration silverOreDeepslateConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.SILVER_ORE_DEEPSLATE.get().defaultBlockState(), OreConfig.SILVER_ORE_DEEPSLATE_VEINSIZE.get());
        SILVER_ORE_DEEPSLATE_OREGEN = registerPlacedFeature("silver_ore_deepslate_feature", new ConfiguredFeature<>(Feature.ORE, silverOreDeepslateConfig),
                CountPlacement.of(OreConfig.SILVER_ORE_DEEPSLATE_AMOUNT.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(64)));

        MOUNTAIN_FLOWER_DEFAULT = PlacementUtils.register("mountain_flower_default_placement",
                FeatureUtils.register("mountain_flower_default_feature", Feature.FLOWER, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ModBlocks.PURPLE_MOUNTAIN_FLOWER.get().defaultBlockState(), 2).add(ModBlocks.RED_MOUNTAIN_FLOWER.get().defaultBlockState(), 1).add(ModBlocks.BLUE_MOUNTAIN_FLOWER.get().defaultBlockState(), 1).add(ModBlocks.YELLOW_MOUNTAIN_FLOWER.get().defaultBlockState(), 1)), 64)),
                RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

        SALT_PATCH = PlacementUtils.register("salt_patch_placement",
                FeatureUtils.register("salt_patch_feature", Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ModBlocks.SALT_DEPOSIT.get().defaultBlockState(), 2)), 32)),
                RarityFilter.onAverageOnceEvery(64), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<PlacedFeature> registerPlacedFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        return PlacementUtils.register(registryName, Holder.direct(feature), placementModifiers);
    }

    private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
        return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
    }

    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.BiomeCategory.NETHER) {
        } else if (event.getCategory() == Biome.BiomeCategory.THEEND) {
        } else {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EBONY_ORE_OVERWORLD_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EBONY_ORE_DEEPSLATE_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MALACHITE_ORE_OVERWORLD_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MALACHITE_ORE_DEEPSLATE_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MOONSTONE_ORE_OVERWORLD_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MOONSTONE_ORE_DEEPSLATE_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORICHALCUM_ORE_OVERWORLD_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORICHALCUM_ORE_DEEPSLATE_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CORUNDUM_ORE_OVERWORLD_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CORUNDUM_ORE_DEEPSLATE_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, QUICKSILVER_ORE_OVERWORLD_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, QUICKSILVER_ORE_DEEPSLATE_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, SILVER_ORE_OVERWORLD_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, SILVER_ORE_DEEPSLATE_OREGEN);
        }

        if(event.getName().equals(Biomes.MEADOW.location()))
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MOUNTAIN_FLOWER_DEFAULT);

        if(event.getName().equals(Biomes.DESERT.location()))
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, SALT_PATCH);

        if(event.getName().equals(Biomes.FLOWER_FOREST.location()) || event.getName().equals(Biomes.SUNFLOWER_PLAINS.location()) ||  event.getName().equals(Biomes.MEADOW.location())) {
            event.getSpawns().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(ModEntityType.BLUE_BUTTERFLY.get(), 100, 3, 5));
            event.getSpawns().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(ModEntityType.MONARCH_BUTTERFLY.get(), 100, 3, 5));
            event.getSpawns().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(ModEntityType.BLUE_DARTWING.get(), 100, 3, 5));
            event.getSpawns().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(ModEntityType.ORANGE_DARTWING.get(), 100, 3, 5));
            event.getSpawns().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(ModEntityType.LUNAR_MOTH.get(), 100, 3, 5));
        } else if(event.getName().equals(Biomes.LUSH_CAVES.location()) || event.getName().equals(Biomes.DRIPSTONE_CAVES.location())) {
            event.getSpawns().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(ModEntityType.TORCHBUG.get(), 200, 4, 6));
        } else if(event.getName().equals(Biomes.SNOWY_SLOPES.location()) ||
                event.getName().equals(Biomes.SNOWY_TAIGA.location()) || event.getName().equals(Biomes.SNOWY_BEACH.location()) ||
                event.getName().equals(Biomes.SNOWY_PLAINS.location()) || event.getName().equals(Biomes.FROZEN_PEAKS.location()) ||
                event.getName().equals(Biomes.JAGGED_PEAKS.location()) || event.getName().equals(Biomes.GROVE.location()) ||
                event.getName().equals(Biomes.ICE_SPIKES.location()) || event.getName().equals(Biomes.STONY_PEAKS) ||
                event.getName().equals(Biomes.SAVANNA.location()) || event.getName().equals(Biomes.PLAINS.location()) ||
                event.getName().equals(Biomes.TAIGA)) {
            event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(ModEntityType.SABRE_CAT.get(), 2, 1, 2));
            event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(ModEntityType.GIANT.get(), 1, 2, 4));
        }
    }
}