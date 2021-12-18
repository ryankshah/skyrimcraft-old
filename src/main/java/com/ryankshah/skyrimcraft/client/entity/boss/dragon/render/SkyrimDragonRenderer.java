package com.ryankshah.skyrimcraft.client.entity.boss.dragon.render;

import com.ryankshah.skyrimcraft.client.entity.boss.dragon.SkyrimDragon;
import com.ryankshah.skyrimcraft.client.entity.boss.dragon.model.SkyrimDragonModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SkyrimDragonRenderer extends GeoEntityRenderer<SkyrimDragon>
{
    public SkyrimDragonRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SkyrimDragonModel());
        this.shadowRadius = 1.0f;
    }
}
