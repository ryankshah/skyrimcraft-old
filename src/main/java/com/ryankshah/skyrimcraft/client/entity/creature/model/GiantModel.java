package com.ryankshah.skyrimcraft.client.entity.creature.model;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.creature.GiantEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GiantModel extends AnimatedGeoModel<GiantEntity>
{
    @Override
    public ResourceLocation getModelLocation(GiantEntity object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "geo/giant.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(GiantEntity object) {
        return new ResourceLocation(Skyrimcraft.MODID, "textures/entity/giant.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(GiantEntity object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "animations/giant.animation.json");
    }
}
