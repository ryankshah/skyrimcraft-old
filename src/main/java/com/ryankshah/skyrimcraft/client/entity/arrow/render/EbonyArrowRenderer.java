package com.ryankshah.skyrimcraft.client.entity.arrow.render;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.arrow.EbonyArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class EbonyArrowRenderer extends ArrowRenderer<EbonyArrowEntity> {
    public static final ResourceLocation ARROW_LOCATION = new ResourceLocation(Skyrimcraft.MODID, "textures/entity/projectiles/ebony_arrow.png");

    public EbonyArrowRenderer(EntityRendererProvider.Context p_i46549_1_) {
        super(p_i46549_1_);
    }

    public ResourceLocation getTextureLocation(EbonyArrowEntity p_110775_1_) {
        return ARROW_LOCATION;
    }
}