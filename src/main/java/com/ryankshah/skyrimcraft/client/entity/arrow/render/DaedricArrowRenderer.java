package com.ryankshah.skyrimcraft.client.entity.arrow.render;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.arrow.DaedricArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DaedricArrowRenderer  extends ArrowRenderer<DaedricArrowEntity> {
    public static final ResourceLocation ARROW_LOCATION = new ResourceLocation(Skyrimcraft.MODID, "textures/entity/projectiles/daedric_arrow.png");

    public DaedricArrowRenderer(EntityRendererProvider.Context p_i46549_1_) {
        super(p_i46549_1_);
    }

    public ResourceLocation getTextureLocation(DaedricArrowEntity p_110775_1_) {
        return ARROW_LOCATION;
    }
}