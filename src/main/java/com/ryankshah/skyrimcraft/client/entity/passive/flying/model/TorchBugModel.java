package com.ryankshah.skyrimcraft.client.entity.passive.flying.model;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.TorchBug;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TorchBugModel extends AnimatedGeoModel<TorchBug>
{
    @Override
    public ResourceLocation getModelLocation(TorchBug object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "geo/torchbug.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TorchBug object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "textures/entity/torchbug.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TorchBug object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "animations/torchbug.animation.json");
    }
}
