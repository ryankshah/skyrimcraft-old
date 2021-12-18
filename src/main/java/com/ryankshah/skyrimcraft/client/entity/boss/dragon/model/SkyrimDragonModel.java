package com.ryankshah.skyrimcraft.client.entity.boss.dragon.model;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.boss.dragon.SkyrimDragon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SkyrimDragonModel extends AnimatedGeoModel<SkyrimDragon>
{
    @Override
    public ResourceLocation getModelLocation(SkyrimDragon object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "geo/dragon.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SkyrimDragon object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "textures/entity/normal_dragon.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SkyrimDragon object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "animations/dragon.animation.json");
    }
}
