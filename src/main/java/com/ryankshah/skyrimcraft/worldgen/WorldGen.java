package com.ryankshah.skyrimcraft.worldgen;

import com.ryankshah.skyrimcraft.util.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class WorldGen
{
    public static void generate(final BiomeLoadingEvent event) {
        if (!(event.getCategory().equals(Biome.Category.NETHER) || event.getCategory().equals(Biome.Category.THEEND))) {
            generateOre(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    ModBlocks.EBONY_ORE.get().defaultBlockState(), 5, 10, 30, 6);
            generateOre(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    ModBlocks.MALACHITE_ORE.get().defaultBlockState(), 5, 15, 45, 10);
            generateOre(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    ModBlocks.MOONSTONE_ORE.get().defaultBlockState(), 5, 34, 60, 12);
            generateOre(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    ModBlocks.ORICHALCUM_ORE.get().defaultBlockState(), 5, 30, 50, 12);

            if(event.getCategory().equals(Biome.Category.DESERT) || event.getCategory().equals(Biome.Category.MESA) || event.getCategory().equals(Biome.Category.SAVANNA))
                generateSalt(event.getGeneration());
        }
    }

    // TODO: If we want to generate the shout wall underground (only and/or overground) we must add as feature

    private static void generateOre(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
                                    int veinSize, int minHeight, int maxHeight, int amount) {
        settings.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                Feature.ORE.configured(new OreFeatureConfig(fillerType, state, veinSize))
                        .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeight, 0, maxHeight)))
                        .squared().count(amount));
    }

    private static void generateSalt(BiomeGenerationSettingsBuilder settings) {
        settings.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.SALT_DEPOSIT.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(3).build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).count(3).chance(32));
    }
}