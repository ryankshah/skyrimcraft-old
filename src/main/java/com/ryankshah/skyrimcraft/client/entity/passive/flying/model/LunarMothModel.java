package com.ryankshah.skyrimcraft.client.entity.passive.flying.model;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.LunarMoth;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LunarMothModel extends AnimatedGeoModel<LunarMoth>
{
    @Override
    public ResourceLocation getModelLocation(LunarMoth object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "geo/lunarmoth.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(LunarMoth object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "textures/entity/lunarmoth.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(LunarMoth object)
    {
        return new ResourceLocation(Skyrimcraft.MODID, "animations/lunarmoth.animation.json");
    }
}
