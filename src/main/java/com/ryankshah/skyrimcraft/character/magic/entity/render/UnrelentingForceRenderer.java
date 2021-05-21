package com.ryankshah.skyrimcraft.character.magic.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.ryankshah.skyrimcraft.character.magic.entity.UnrelentingForceEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

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