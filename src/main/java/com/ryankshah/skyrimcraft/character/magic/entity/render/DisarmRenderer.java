package com.ryankshah.skyrimcraft.character.magic.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.character.magic.entity.DisarmEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DisarmRenderer extends EntityRenderer<DisarmEntity>
{
    //private static final ResourceLocation DISARM = new ResourceLocation(Skyrimcraft.MODID, "textures/effect/fireball2.png");

    public DisarmRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(DisarmEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource
            buffer, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(DisarmEntity entity) {
        return null;
    }
}