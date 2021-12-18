package com.ryankshah.skyrimcraft.client.entity.passive.flying.model;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.BlueButterfly;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlueButterflyModel extends AnimatedGeoModel<BlueButterfly>
{
    @Override
    public ResourceLocation getModelLocation(BlueButterfly object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "geo/bluebutterfly.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BlueButterfly object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "textures/entity/bluebutterfly.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BlueButterfly object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "animations/bluebutterfly.animation.json");
    }
}
