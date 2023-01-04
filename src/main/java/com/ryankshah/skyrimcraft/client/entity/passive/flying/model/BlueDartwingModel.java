package com.ryankshah.skyrimcraft.client.entity.passive.flying.model;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.BlueDartwing;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlueDartwingModel extends AnimatedGeoModel<BlueDartwing>
{
    @Override
    public ResourceLocation getModelLocation(BlueDartwing object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "geo/bluedartwing.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BlueDartwing object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "textures/entity/bluedartwing.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BlueDartwing object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "animations/bluedartwing.animation.json");
    }
}
