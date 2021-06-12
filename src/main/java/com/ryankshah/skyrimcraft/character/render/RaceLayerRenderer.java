package com.ryankshah.skyrimcraft.character.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.character.model.DunmerEarModel;
import com.ryankshah.skyrimcraft.character.model.HighElfEarModel;
import com.ryankshah.skyrimcraft.character.model.KhajiitHeadModel;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class RaceLayerRenderer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>
{
    private HighElfEarModel highElfEarModel;
    private DunmerEarModel dunmerEarModel;
    private KhajiitHeadModel khajiitHeadModel;

    public RaceLayerRenderer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> entityRenderer) {
        super(entityRenderer);

        ModelRenderer head = entityRenderer.getModel().getHead();
        highElfEarModel = new HighElfEarModel(entityRenderer.getModel(), head);
        dunmerEarModel = new DunmerEarModel(entityRenderer.getModel(), head);
        khajiitHeadModel = new KhajiitHeadModel(entityRenderer.getModel(), head);

//        entityRenderer.getModel().copyPropertiesTo(highElfEarModel);
//        entityRenderer.getModel().copyPropertiesTo(dunmerEarModel);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int packedLight, AbstractClientPlayerEntity playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ISkyrimPlayerData cap = playerEntity.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("highelfearslayerrenderer render"));

        if(cap.getRace().getId() == Race.ALTMER.getId() || cap.getRace().getId() == Race.BOSMER.getId())
            renderAltmer(matrixStack, renderBuffer, packedLight, playerEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        else if(cap.getRace().getId() == Race.DUNMER.getId())
            renderDunmer(matrixStack, renderBuffer, packedLight, playerEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        else if(cap.getRace().getId() == Race.KHAJIIT.getId())
            renderKhajiit(matrixStack, renderBuffer, packedLight, playerEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
    }

    private void renderAltmer(MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int packedLight, AbstractClientPlayerEntity playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        IVertexBuilder ivertexbuilder = renderBuffer.getBuffer(RenderType.entitySolid(playerEntity.getSkinTextureLocation()));
        int overlayCoords = LivingRenderer.getOverlayCoords(playerEntity, 0.0F);

        matrixStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(matrixStack);
        //matrixStack.mulPose(YP.rotationDegrees(180F));
        highElfEarModel.renderToBuffer(matrixStack, ivertexbuilder, packedLight, overlayCoords, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }

    private void renderDunmer(MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int packedLight, AbstractClientPlayerEntity playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        IVertexBuilder ivertexbuilder = renderBuffer.getBuffer(RenderType.entitySolid(playerEntity.getSkinTextureLocation()));
        int overlayCoords = LivingRenderer.getOverlayCoords(playerEntity, 0.0F);

        matrixStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(matrixStack);
        //matrixStack.mulPose(YP.rotationDegrees(180F));
        dunmerEarModel.renderToBuffer(matrixStack, ivertexbuilder, packedLight, overlayCoords, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }

    private void renderKhajiit(MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int packedLight, AbstractClientPlayerEntity playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        IVertexBuilder ivertexbuilder = renderBuffer.getBuffer(RenderType.entitySolid(playerEntity.getSkinTextureLocation()));
        int overlayCoords = LivingRenderer.getOverlayCoords(playerEntity, 0.0F);

        matrixStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(matrixStack);
        //matrixStack.mulPose(YP.rotationDegrees(180F));
        khajiitHeadModel.renderToBuffer(matrixStack, ivertexbuilder, packedLight, overlayCoords, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }
}