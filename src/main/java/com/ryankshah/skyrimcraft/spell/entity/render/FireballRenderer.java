package com.ryankshah.skyrimcraft.spell.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.spell.entity.FireballEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public class FireballRenderer extends EntityRenderer<FireballEntity>
{
    private static final ResourceLocation SPELL_FIREBALL_TEXTURE = new ResourceLocation(Skyrimcraft.MODID, "textures/effect/fireball2.png");

    public FireballRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(FireballEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLightIn) {
        matrixStack.push();
        matrixStack.scale(1.0F, 1.0F, 1.0F);

        IVertexBuilder ivertexbuilder = buffer.getBuffer(RenderType.getEntityTranslucent(this.getEntityTexture(entity)));
        MatrixStack.Entry matrixstack$entry = matrixStack.getLast();
        Matrix4f matrix4f = matrixstack$entry.getMatrix();

        long t = System.currentTimeMillis() % 6;

        matrixStack.rotate(renderManager.getCameraOrientation());


        ivertexbuilder.pos(matrix4f, -1, -1, 0).color(255, 255, 255, 255).tex(0, 0 +  t * (1.0f / 4.0f)).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLightIn).normal(0, 1, 0).endVertex();
        ivertexbuilder.pos(matrix4f, -1, 1, 0).color(255, 255, 255, 255).tex(0, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLightIn).normal(0, 1, 0).endVertex();
        ivertexbuilder.pos(matrix4f, 1, 1, 0).color(255, 255, 255, 255).tex(1, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLightIn).normal(0, 1, 0).endVertex();
        ivertexbuilder.pos(matrix4f, 1, -1, 0).color(255, 255, 255, 255).tex(1, 0 +  t * (1.0f / 4.0f)).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLightIn).normal(0, 1, 0).endVertex();

        matrixStack.pop();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(FireballEntity entity) {
        return SPELL_FIREBALL_TEXTURE;
    }
}