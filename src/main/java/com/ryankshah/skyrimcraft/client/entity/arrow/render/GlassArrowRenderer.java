package com.ryankshah.skyrimcraft.client.entity.arrow.render;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.arrow.GlassArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class GlassArrowRenderer extends ArrowRenderer<GlassArrowEntity> {
    public static final ResourceLocation ARROW_LOCATION = new ResourceLocation(Skyrimcraft.MODID, "textures/entity/projectiles/glass_arrow.png");

    public GlassArrowRenderer(EntityRendererManager p_i46549_1_) {
        super(p_i46549_1_);
    }

    public ResourceLocation getTextureLocation(GlassArrowEntity p_110775_1_) {
        return ARROW_LOCATION;
    }
}