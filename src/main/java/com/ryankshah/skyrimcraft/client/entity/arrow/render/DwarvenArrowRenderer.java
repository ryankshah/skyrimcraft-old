package com.ryankshah.skyrimcraft.client.entity.arrow.render;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.arrow.DwarvenArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class DwarvenArrowRenderer extends ArrowRenderer<DwarvenArrowEntity> {
    public static final ResourceLocation ARROW_LOCATION = new ResourceLocation(Skyrimcraft.MODID, "textures/entity/projectiles/dwarven_arrow.png");

    public DwarvenArrowRenderer(EntityRendererManager p_i46549_1_) {
        super(p_i46549_1_);
    }

    public ResourceLocation getTextureLocation(DwarvenArrowEntity p_110775_1_) {
        return ARROW_LOCATION;
    }
}