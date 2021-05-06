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
        matrixStack.pushPose();
        matrixStack.scale(1.0F, 1.0F, 1.0F);

        IVertexBuilder ivertexbuilder = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
        MatrixStack.Entry matrixstack$entry = matrixStack.last();
        Matrix4f matrix4f = matrixstack$entry.pose();

        long t = System.currentTimeMillis() % 6;

        matrixStack.mulPose(entityRenderDispatcher.cameraOrientation());


        ivertexbuilder.vertex(matrix4f, -1, -1, 0).color(255, 255, 255, 255).uv(0, 0 +  t * (1.0f / 4.0f)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(0, 1, 0).endVertex();
        ivertexbuilder.vertex(matrix4f, -1, 1, 0).color(255, 255, 255, 255).uv(0, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(0, 1, 0).endVertex();
        ivertexbuilder.vertex(matrix4f, 1, 1, 0).color(255, 255, 255, 255).uv(1, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(0, 1, 0).endVertex();
        ivertexbuilder.vertex(matrix4f, 1, -1, 0).color(255, 255, 255, 255).uv(1, 0 +  t * (1.0f / 4.0f)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(0, 1, 0).endVertex();

        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(FireballEntity entity) {
        return SPELL_FIREBALL_TEXTURE;
    }
}