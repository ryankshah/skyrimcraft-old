package com.ryankshah.skyrimcraft.client.entity.passive.flying.model;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.OrangeDartwing;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OrangeDartwingModel extends AnimatedGeoModel<OrangeDartwing>
{
    @Override
    public ResourceLocation getModelLocation(OrangeDartwing object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "geo/orangedartwing.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(OrangeDartwing object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "textures/entity/orangedartwing.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(OrangeDartwing object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "animations/orangedartwing.animation.json");
    }
}
