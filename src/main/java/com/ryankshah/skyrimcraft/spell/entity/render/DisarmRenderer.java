package com.ryankshah.skyrimcraft.spell.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.ryankshah.skyrimcraft.spell.entity.DisarmEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class DisarmRenderer extends EntityRenderer<DisarmEntity>
{
    //private static final ResourceLocation DISARM = new ResourceLocation(Skyrimcraft.MODID, "textures/effect/fireball2.png");

    public DisarmRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(DisarmEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer
            buffer, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(DisarmEntity entity) {
        return null;
    }
}