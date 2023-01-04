package com.ryankshah.skyrimcraft.client.entity.passive.flying.render;

import com.ryankshah.skyrimcraft.client.entity.passive.flying.LunarMoth;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.model.LunarMothModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LunarMothRenderer extends GeoEntityRenderer<LunarMoth>
{
    public LunarMothRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx, new LunarMothModel());
        this.shadowRadius = 0.5f;
    }
}