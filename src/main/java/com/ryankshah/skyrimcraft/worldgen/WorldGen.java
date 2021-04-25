package com.ryankshah.skyrimcraft.worldgen;

import com.ryankshah.skyrimcraft.util.ModBlocks;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class WorldGen
{
    public static final List<ConfiguredFeature<?, ?>> oreList = new ArrayList<>();

    public static void init() {
        oreList.add(Feature.ORE.withConfiguration(
            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                    ModBlocks.EBONY_ORE.get().getDefaultState(), 2)) //vein size
            .range(20).square() //maximum y level where ore can spawn
            .count(2) //how many veins maximum per chunk
        );

        oreList.add(Feature.ORE.withConfiguration(
            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                    ModBlocks.SILVER_ORE.get().getDefaultState(), 5))
            .range(52).square()
            .count(5)
        );
    }

    @SubscribeEvent
    public static void generateOres(final BiomeLoadingEvent event){
        BiomeGenerationSettingsBuilder generation = event.getGeneration();
        for (ConfiguredFeature ore : oreList) {
            generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
        }
    }
}