package com.ryankshah.skyrimcraft.character.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.character.model.DunmerEarModel;
import com.ryankshah.skyrimcraft.character.model.HighElfEarModel;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.util.math.vector.Vector3f.XP;
import static net.minecraft.util.math.vector.Vector3f.YP;

public class RaceLayerRenderer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>
{
    private HighElfEarModel highElfEarModel;
    private DunmerEarModel dunmerEarModel;

    public RaceLayerRenderer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> entityRenderer) {
        super(entityRenderer);

        ModelRenderer head = entityRenderer.getModel().getHead();
        highElfEarModel = new HighElfEarModel(entityRenderer.getModel(), head);
        dunmerEarModel = new DunmerEarModel(entityRenderer.getModel(), head);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int packedLight, AbstractClientPlayerEntity playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ISkyrimPlayerData cap = playerEntity.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("highelfearslayerrenderer render"));

        if(cap.getRace().getId() == Race.ALTMER.getId() || cap.getRace().getId() == Race.BOSMER.getId())
            renderAltmer(matrixStack, renderBuffer, packedLight, playerEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        else if(cap.getRace().getId() == Race.DUNMER.getId())
            renderDunmer(matrixStack, renderBuffer, packedLight, playerEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
    }

    private void renderAltmer(MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int packedLight, AbstractClientPlayerEntity playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        IVertexBuilder ivertexbuilder = renderBuffer.getBuffer(RenderType.entitySolid(playerEntity.getSkinTextureLocation()));
        int overlayCoords = LivingRenderer.getOverlayCoords(playerEntity, 0.0F);

        float f = MathHelper.lerp(partialTicks, playerEntity.yRotO, playerEntity.yRot) - MathHelper.lerp(partialTicks, playerEntity.yBodyRotO, playerEntity.yBodyRot);
        float f1 = MathHelper.lerp(partialTicks, playerEntity.xRotO, playerEntity.xRot);
        matrixStack.pushPose();
        matrixStack.mulPose(YP.rotationDegrees(f));
        matrixStack.mulPose(XP.rotationDegrees(f1));
        highElfEarModel.renderToBuffer(matrixStack, ivertexbuilder, packedLight, overlayCoords, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }

    private void renderDunmer(MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int packedLight, AbstractClientPlayerEntity playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        IVertexBuilder ivertexbuilder = renderBuffer.getBuffer(RenderType.entitySolid(playerEntity.getSkinTextureLocation()));
        int overlayCoords = LivingRenderer.getOverlayCoords(playerEntity, 0.0F);

        float f = MathHelper.lerp(partialTicks, playerEntity.yRotO, playerEntity.yRot) - MathHelper.lerp(partialTicks, playerEntity.yBodyRotO, playerEntity.yBodyRot);
        float f1 = MathHelper.lerp(partialTicks, playerEntity.xRotO, playerEntity.xRot);
        matrixStack.pushPose();
        matrixStack.mulPose(YP.rotationDegrees(f));
        matrixStack.mulPose(XP.rotationDegrees(f1));
        dunmerEarModel.renderToBuffer(matrixStack, ivertexbuilder, packedLight, overlayCoords, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }
}