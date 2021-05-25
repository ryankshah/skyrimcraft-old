package com.ryankshah.skyrimcraft.character.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DunmerEarModel extends EntityModel<AbstractClientPlayerEntity>
{
    private PlayerModel<AbstractClientPlayerEntity> playerModel;

    private final ModelRenderer Ears;
    private final ModelRenderer Ear_Left;
    private final ModelRenderer Ear_Right;

    public DunmerEarModel(PlayerModel<AbstractClientPlayerEntity> model, ModelRenderer head) {
        this.playerModel = model;
        texWidth = 64;
        texHeight = 64;

        Ears = new ModelRenderer(model, 24, 0);
        Ears.copyFrom(head);
        Ears.setPos(0.0F, 1.0F, 0.0F);


        Ear_Left = new ModelRenderer(this);
        Ear_Left.copyFrom(Ears);
        Ear_Left.setPos(0.0F, -24.0F, 0.0F);
        Ears.addChild(Ear_Left);
        Ear_Left.texOffs(3, 12).addBox(4.0F, 19.0F, -2.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        Ear_Left.texOffs(3, 12).addBox(4.0F, 18.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        Ear_Left.texOffs(4, 13).addBox(5.0F, 18.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        Ear_Right = new ModelRenderer(this);
        Ear_Right.copyFrom(Ears);
        Ear_Right.setPos(-9.0F, -24.0F, 0.0F);
        Ears.addChild(Ear_Right);
        Ear_Right.texOffs(3, 12).addBox(4.0F, 19.0F, -2.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        Ear_Right.texOffs(3, 12).addBox(4.0F, 18.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        Ear_Right.texOffs(4, 13).addBox(3.0F, 18.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void setupAnim(AbstractClientPlayerEntity p_225597_1_, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Ears.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}