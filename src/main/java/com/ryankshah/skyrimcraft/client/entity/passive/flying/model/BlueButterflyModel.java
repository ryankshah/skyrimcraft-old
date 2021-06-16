package com.ryankshah.skyrimcraft.client.entity.passive.flying.model;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.AbstractButterflyEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlueButterflyModel extends AnimatedGeoModel<AbstractButterflyEntity>
{
    @Override
    public ResourceLocation getModelLocation(AbstractButterflyEntity object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "geo/butterfly.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractButterflyEntity object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "textures/entity/blue_butterfly.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AbstractButterflyEntity object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "animations/butterfly.animation.json");
    }
}
