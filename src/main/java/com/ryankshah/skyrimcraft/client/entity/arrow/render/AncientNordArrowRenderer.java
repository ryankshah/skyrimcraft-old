package com.ryankshah.skyrimcraft.client.entity.arrow.render;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.arrow.AncientNordArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class AncientNordArrowRenderer extends ArrowRenderer<AncientNordArrowEntity> {
    public static final ResourceLocation ARROW_LOCATION = new ResourceLocation(Skyrimcraft.MODID, "textures/entity/projectiles/ancient_nord_arrow.png");

    public AncientNordArrowRenderer(EntityRendererManager p_i46549_1_) {
        super(p_i46549_1_);
    }

    public ResourceLocation getTextureLocation(AncientNordArrowEntity p_110775_1_) {
        return ARROW_LOCATION;
    }
}