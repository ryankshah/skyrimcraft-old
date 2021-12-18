package com.ryankshah.skyrimcraft.character.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;

public class DunmerEarModel extends EntityModel<AbstractClientPlayer>
{
    private PlayerModel<AbstractClientPlayer> playerModel;

//    private final ModelPart Ears;
//    private final ModelPart Ear_Left;
//    private final ModelPart Ear_Right;

    public DunmerEarModel(PlayerModel<AbstractClientPlayer> model, ModelPart head) {
        this.playerModel = model;
//        texWidth = 64;
//        texHeight = 64;
//
//        Ears = new ModelPart(model, 24, 0);
//        Ears.copyFrom(head);
//        Ears.setPos(0.0F, 1.0F, 0.0F);
//
//
//        Ear_Left = new ModelPart(this);
//        Ear_Left.copyFrom(Ears);
//        Ear_Left.setPos(0.0F, -24.0F, 0.0F);
//        Ears.addChild(Ear_Left);
//        Ear_Left.texOffs(3, 12).addBox(4.0F, 19.0F, -2.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
//        Ear_Left.texOffs(3, 12).addBox(4.0F, 18.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
//        Ear_Left.texOffs(4, 13).addBox(5.0F, 18.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
//
//        Ear_Right = new ModelPart(this);
//        Ear_Right.copyFrom(Ears);
//        Ear_Right.setPos(-9.0F, -24.0F, 0.0F);
//        Ears.addChild(Ear_Right);
//        Ear_Right.texOffs(3, 12).addBox(4.0F, 19.0F, -2.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
//        Ear_Right.texOffs(3, 12).addBox(4.0F, 18.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
//        Ear_Right.texOffs(4, 13).addBox(3.0F, 18.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void setupAnim(AbstractClientPlayer p_225597_1_, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        // Ears.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}