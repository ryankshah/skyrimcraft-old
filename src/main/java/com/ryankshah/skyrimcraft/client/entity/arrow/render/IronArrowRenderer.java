package com.ryankshah.skyrimcraft.client.entity.arrow.render;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.arrow.IronArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class IronArrowRenderer extends ArrowRenderer<IronArrowEntity> {
    public static final ResourceLocation ARROW_LOCATION = new ResourceLocation(Skyrimcraft.MODID, "textures/entity/projectiles/iron_arrow.png");

    public IronArrowRenderer(EntityRendererManager p_i46549_1_) {
        super(p_i46549_1_);
    }

    public ResourceLocation getTextureLocation(IronArrowEntity p_110775_1_) {
        return ARROW_LOCATION;
    }
}