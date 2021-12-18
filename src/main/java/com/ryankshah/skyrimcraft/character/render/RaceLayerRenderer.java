package com.ryankshah.skyrimcraft.character.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.character.model.DunmerEarModel;
import com.ryankshah.skyrimcraft.character.model.HighElfEarModel;
import com.ryankshah.skyrimcraft.character.model.KhajiitHeadModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;

public class RaceLayerRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>
{
    private HighElfEarModel highElfEarModel;
    private DunmerEarModel dunmerEarModel;
    private KhajiitHeadModel khajiitHeadModel;

    public RaceLayerRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> entityRenderer) {
        super(entityRenderer);

        ModelPart head = entityRenderer.getModel().getHead();
        highElfEarModel = new HighElfEarModel(entityRenderer.getModel(), head);
        dunmerEarModel = new DunmerEarModel(entityRenderer.getModel(), head);
        khajiitHeadModel = new KhajiitHeadModel(entityRenderer.getModel(), head);

//        entityRenderer.getModel().copyPropertiesTo(highElfEarModel);
//        entityRenderer.getModel().copyPropertiesTo(dunmerEarModel);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight, AbstractClientPlayer playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ISkyrimPlayerData cap = playerEntity.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("highelfearslayerrenderer render"));

        if(cap.getRace().getId() == Race.ALTMER.getId() || cap.getRace().getId() == Race.BOSMER.getId())
            renderAltmer(matrixStack, renderBuffer, packedLight, playerEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        else if(cap.getRace().getId() == Race.DUNMER.getId())
            renderDunmer(matrixStack, renderBuffer, packedLight, playerEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        else if(cap.getRace().getId() == Race.KHAJIIT.getId())
            renderKhajiit(matrixStack, renderBuffer, packedLight, playerEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
    }

    private void renderAltmer(PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight, AbstractClientPlayer playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer ivertexbuilder = renderBuffer.getBuffer(RenderType.entitySolid(playerEntity.getSkinTextureLocation()));
        int overlayCoords = LivingEntityRenderer.getOverlayCoords(playerEntity, 0.0F);

        matrixStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(matrixStack);
        //matrixStack.mulPose(YP.rotationDegrees(180F));
        highElfEarModel.renderToBuffer(matrixStack, ivertexbuilder, packedLight, overlayCoords, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }

    private void renderDunmer(PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight, AbstractClientPlayer playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer ivertexbuilder = renderBuffer.getBuffer(RenderType.entitySolid(playerEntity.getSkinTextureLocation()));
        int overlayCoords = LivingEntityRenderer.getOverlayCoords(playerEntity, 0.0F);

        matrixStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(matrixStack);
        //matrixStack.mulPose(YP.rotationDegrees(180F));
        dunmerEarModel.renderToBuffer(matrixStack, ivertexbuilder, packedLight, overlayCoords, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }

    private void renderKhajiit(PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight, AbstractClientPlayer playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer ivertexbuilder = renderBuffer.getBuffer(RenderType.entitySolid(playerEntity.getSkinTextureLocation()));
        int overlayCoords = LivingEntityRenderer.getOverlayCoords(playerEntity, 0.0F);

        matrixStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(matrixStack);
        //matrixStack.mulPose(YP.rotationDegrees(180F));
        khajiitHeadModel.renderToBuffer(matrixStack, ivertexbuilder, packedLight, overlayCoords, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }
}