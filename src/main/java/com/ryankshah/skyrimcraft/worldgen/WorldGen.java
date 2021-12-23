package com.ryankshah.skyrimcraft.worldgen;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.client.entity.ModEntityType;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorldGen
{
    private static final List<ResourceKey<Biome>> SABRE_CAT_BIOMES = Arrays.asList(
            Biomes.SNOWY_SLOPES, Biomes.SNOWY_TAIGA, Biomes.SNOWY_BEACH,
            Biomes.SNOWY_PLAINS, Biomes.PLAINS, Biomes.TAIGA
    );

    private static List<PlacedFeature> orePlacedFeatures = new ArrayList<>();
    private static List<PlacedFeature> vegetalPlacedFeatures = new ArrayList<>();

    public static void registerPlacements() {
        generateOre("ebony_ore",
                ModBlocks.EBONY_ORE.get().defaultBlockState(), 2, 6, 10, 30);
        generateOre("malachite_ore",
                ModBlocks.MALACHITE_ORE.get().defaultBlockState(), 3, 10, 15, 45);
        generateOre("moonstone_ore",
                ModBlocks.MOONSTONE_ORE.get().defaultBlockState(), 4, 12, 34, 60);
        generateOre("orichalcum_ore",
                ModBlocks.ORICHALCUM_ORE.get().defaultBlockState(), 3, 12, 30, 50);
        generateOre("corundum_ore",
                ModBlocks.CORUNDUM_ORE.get().defaultBlockState(), 3, 12, 30, 50);
        generateOre("quicksilver_ore",
                ModBlocks.QUICKSILVER_ORE.get().defaultBlockState(), 3, 12, 30, 50);
        generateOre("silver_ore",
                ModBlocks.SILVER_ORE.get().defaultBlockState(), 3, 12, 30, 50);

        // Salt pile
        //if (event.getCategory().equals(Biome.BiomeCategory.DESERT) || event.getCategory().equals(Biome.BiomeCategory.MESA) || event.getCategory().equals(Biome.BiomeCategory.SAVANNA))
        generateSalt();

        vegetalPlacedFeatures.add(PlacementUtils.register("mountain_flowers", FeatureUtils.register("mountain_flower_default", Feature.FLOWER.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ModBlocks.RED_MOUNTAIN_FLOWER.get().defaultBlockState(), 1).add(ModBlocks.BLUE_MOUNTAIN_FLOWER.get().defaultBlockState(), 1).add(ModBlocks.PURPLE_MOUNTAIN_FLOWER.get().defaultBlockState(), 1).add(ModBlocks.YELLOW_MOUNTAIN_FLOWER.get().defaultBlockState(), 1)), 64))).placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()))); // onAverageOnceEvery(16)
    }

    public static void generate(final BiomeLoadingEvent event) {
        if (!(event.getCategory().equals(Biome.BiomeCategory.NETHER) || event.getCategory().equals(Biome.BiomeCategory.THEEND))) {
            // Mobs
            for(ResourceKey<Biome> biomeKey : SABRE_CAT_BIOMES) {
                ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
                if(biomeKey.equals(key))
                    event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityType.SABRE_CAT.get(), 4, 1, 3));
            }

            for(PlacedFeature f : orePlacedFeatures)
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, f);

            for(PlacedFeature f : vegetalPlacedFeatures)
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, f);

            // Flowers
            //event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
        }
    }

    // TODO: Register world gen entries (configured features and placement utils) in variables for each feature in
    //       `FMLCommonSetupEvent#enqueueWork` and then add feature vars to generation in `BiomeLoadingEvent`

    private static void generateOre(String name, BlockState state, int veinSize, int veinsPerChunk, int minHeight, int maxHeight) {
        List<OreConfiguration.TargetBlockState> targetStates = List.of(OreConfiguration.target(OreFeatures.NATURAL_STONE, state));
        ConfiguredFeature<?, ?> configuredFeature = FeatureUtils.register(Skyrimcraft.MODID + ":block/" + name, Feature.ORE.configured(new OreConfiguration(targetStates, veinSize)));
        PlacedFeature placedFeature = PlacementUtils.register(Skyrimcraft.MODID + ":ore/" + name, configuredFeature.placed(List.of(
                CountPlacement.of(veinsPerChunk),
                InSquarePlacement.spread(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)),
                BiomeFilter.biome()
        )));
        orePlacedFeatures.add(placedFeature);
        //settings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, placedFeature);
    }

    private static void generateSalt() {
        vegetalPlacedFeatures.add(PlacementUtils.register("salt_patch", FeatureUtils.register("salt_patch_default", Feature.RANDOM_PATCH.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ModBlocks.SALT_DEPOSIT.get().defaultBlockState(), 1)), 64))).placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()))); // onAverageOnceEvery(16)
    }

    private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
        return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(p_195203_)).onlyWhenEmpty());
    }
}