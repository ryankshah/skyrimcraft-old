package com.ryankshah.skyrimcraft.client.entity.arrow.render;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.arrow.OrcishArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class OrcishArrowRenderer extends ArrowRenderer<OrcishArrowEntity> {
    public static final ResourceLocation ARROW_LOCATION = new ResourceLocation(Skyrimcraft.MODID, "textures/entity/projectiles/orcish_arrow.png");

    public OrcishArrowRenderer(EntityRendererProvider.Context p_i46549_1_) {
        super(p_i46549_1_);
    }

    public ResourceLocation getTextureLocation(OrcishArrowEntity p_110775_1_) {
        return ARROW_LOCATION;
    }
}