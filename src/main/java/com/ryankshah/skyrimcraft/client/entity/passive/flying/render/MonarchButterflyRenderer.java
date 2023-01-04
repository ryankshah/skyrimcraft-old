package com.ryankshah.skyrimcraft.client.entity.passive.flying.render;

import com.ryankshah.skyrimcraft.client.entity.passive.flying.MonarchButterfly;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.model.MonarchButterflyModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MonarchButterflyRenderer extends GeoEntityRenderer<MonarchButterfly>
{
    public MonarchButterflyRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx, new MonarchButterflyModel());
        this.shadowRadius = 0.5f;
    }
}