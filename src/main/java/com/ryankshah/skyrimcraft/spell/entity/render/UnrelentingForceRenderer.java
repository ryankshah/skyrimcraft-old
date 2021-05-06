package com.ryankshah.skyrimcraft.spell.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.spell.entity.FireballEntity;
import com.ryankshah.skyrimcraft.spell.entity.UnrelentingForceEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public class UnrelentingForceRenderer extends EntityRenderer<UnrelentingForceEntity>
{
        //private static final ResourceLocation UNRELENTING_FORCE = new ResourceLocation(Skyrimcraft.MODID, "textures/effect/fireball2.png");

        public UnrelentingForceRenderer(EntityRendererManager renderManager) {
            super(renderManager);
        }

        @Override
        public void render(UnrelentingForceEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer
        buffer, int packedLightIn) {
            super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLightIn);
        }

        @Override
        public ResourceLocation getTextureLocation(UnrelentingForceEntity entity) {
            return null;
        }
}