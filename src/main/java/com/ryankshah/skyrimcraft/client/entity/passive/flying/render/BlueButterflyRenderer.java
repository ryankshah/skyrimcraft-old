package com.ryankshah.skyrimcraft.client.entity.passive.flying.render;

import com.ryankshah.skyrimcraft.client.entity.passive.flying.BlueButterfly;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.model.BlueButterflyModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BlueButterflyRenderer extends GeoEntityRenderer<BlueButterfly>
{
    public BlueButterflyRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx, new BlueButterflyModel());
        this.shadowRadius = 0.5f;
    }
}