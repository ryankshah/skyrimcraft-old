package com.ryankshah.skyrimcraft.client.entity.passive.flying.render;

import com.ryankshah.skyrimcraft.client.entity.passive.flying.AbstractButterflyEntity;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.model.BlueButterflyModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BlueButterflyRenderer extends GeoEntityRenderer<AbstractButterflyEntity>
{
    public BlueButterflyRenderer(EntityRendererManager renderManager)
    {
        super(renderManager, new BlueButterflyModel());
        this.shadowRadius = 0.75f;
    }
}