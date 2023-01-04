package com.ryankshah.skyrimcraft.client.entity.passive.flying.model;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.MonarchButterfly;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MonarchButterflyModel extends AnimatedGeoModel<MonarchButterfly>
{
    @Override
    public ResourceLocation getModelLocation(MonarchButterfly object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "geo/monarchbutterfly.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MonarchButterfly object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "textures/entity/monarchbutterfly.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MonarchButterfly object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "animations/monarchbutterfly.animation.json");
    }
}
