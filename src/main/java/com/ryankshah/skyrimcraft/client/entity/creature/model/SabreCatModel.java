package com.ryankshah.skyrimcraft.client.entity.creature.model;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.creature.SabreCatEntity;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import java.util.Arrays;
import java.util.List;

public class SabreCatModel extends AnimatedGeoModel<SabreCatEntity>
{
    @Override
    public ResourceLocation getModelLocation(SabreCatEntity object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "geo/sabre_cat.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SabreCatEntity object) {
//        object.getBiomeType()
//        Optional<RegistryKey<Biome>> biome = object.level.getBiomeName(object.blockPosition());
        List<ResourceKey<Biome>> SNOWY_BIOMES = Arrays.asList(
                Biomes.SNOWY_SLOPES, Biomes.SNOWY_TAIGA, Biomes.SNOWY_BEACH,
                Biomes.SNOWY_PLAINS
        );
        if(SNOWY_BIOMES.contains(object.getBiomeType())) //biome.isPresent() &&
            return new ResourceLocation(Skyrimcraft.MODID, "textures/entity/snowy_sabre_cat.png");
        else
            return new ResourceLocation(Skyrimcraft.MODID, "textures/entity/sabre_cat.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SabreCatEntity object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "animations/sabre_cat.animation.json");
    }
}
