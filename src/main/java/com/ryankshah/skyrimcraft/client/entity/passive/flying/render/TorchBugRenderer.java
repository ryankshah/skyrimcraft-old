package com.ryankshah.skyrimcraft.client.entity.passive.flying.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.TorchBug;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.model.TorchBugModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class TorchBugRenderer extends GeoEntityRenderer<TorchBug>
{
    public TorchBugRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new TorchBugModel());
        this.shadowRadius = 0.5f;
        this.addLayer(new TorchBugButtLayer(this));
    }

    class TorchBugButtLayer extends GeoLayerRenderer<TorchBug>
    {
        private IGeoRenderer<TorchBug> entityIGeoRenderer;

        public TorchBugButtLayer(IGeoRenderer<TorchBug> entityRendererIn) {
            super(entityRendererIn);
            this.entityIGeoRenderer = entityRendererIn;
        }

        @Override
        public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, TorchBug entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            ResourceLocation eyes = new ResourceLocation(Skyrimcraft.MODID, "textures/entity/torchbug_e.png");

            if(entitylivingbaseIn.level.getMaxLocalRawBrightness(entitylivingbaseIn.blockPosition()) < 10) { // test: < 3  |  original: .getDayTime() > 12542) {
                VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.eyes(eyes));
                this.entityIGeoRenderer.render(getEntityModel().getModel(getGeoModelProvider().getModelLocation(entitylivingbaseIn)), entitylivingbaseIn, partialTicks, RenderType.eyes(eyes), matrixStackIn, bufferIn, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }
}
