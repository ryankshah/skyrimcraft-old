package com.ryankshah.skyrimcraft.client.entity.creature.render;

import com.ryankshah.skyrimcraft.client.entity.creature.GiantEntity;
import com.ryankshah.skyrimcraft.client.entity.creature.model.GiantModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GiantRenderer extends GeoEntityRenderer<GiantEntity>
{
    public GiantRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx, new GiantModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }
}