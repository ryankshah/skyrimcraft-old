package com.ryankshah.skyrimcraft.client.entity.passive.flying.render;

import com.ryankshah.skyrimcraft.client.entity.passive.flying.OrangeDartwing;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.model.OrangeDartwingModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class OrangeDartwingRenderer extends GeoEntityRenderer<OrangeDartwing>
{
    public OrangeDartwingRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx, new OrangeDartwingModel());
        this.shadowRadius = 0.5f;
    }
}