package com.ryankshah.skyrimcraft.character.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;

public class KhajiitHeadModel extends EntityModel<AbstractClientPlayer>
{
    private PlayerModel<AbstractClientPlayer> playerModel;

//    private final ModelPart Ears;
//    private final ModelPart Ear_Left;
//    private final ModelPart Ear_Right;
//    private final ModelPart Whiskers;
//    private final ModelPart Whiskers_Left;
//    private final ModelPart cube_r1;
//    private final ModelPart cube_r2;
//    private final ModelPart Whiskers_Right;
//    private final ModelPart cube_r3;
//    private final ModelPart cube_r4;

    public KhajiitHeadModel(PlayerModel<AbstractClientPlayer> model, ModelPart head) {
        this.playerModel = model;
//        texWidth = 64;
//        texHeight = 64;
//
//        Ears = new ModelPart(model);
//        Ears.copyFrom(head);
//        Ears.setPos(0.0F, 1.0F, 0.0F);
//
//
//        Ear_Left = new ModelPart(this);
//        Ear_Left.setPos(5.0F, -24.0F, 4.0F);
//        Ears.addChild(Ear_Left);
//        setRotationAngle(Ear_Left, 0.0F, 1.5708F, 0.0F);
//        Ear_Left.texOffs(3, 12).addBox(4.0F, 19.0F, -2.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
//        Ear_Left.texOffs(3, 12).addBox(4.0F, 18.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
//        Ear_Left.texOffs(4, 14).addBox(4.0F, 17.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
//
//        Ear_Right = new ModelPart(this);
//        Ear_Right.setPos(-5.0F, -24.0F, -5.0F);
//        Ears.addChild(Ear_Right);
//        setRotationAngle(Ear_Right, 0.0F, -1.5708F, 0.0F);
//        Ear_Right.texOffs(3, 12).addBox(4.0F, 19.0F, -2.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
//        Ear_Right.texOffs(3, 12).addBox(4.0F, 18.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
//        Ear_Right.texOffs(4, 14).addBox(4.0F, 17.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
//
//        Whiskers = new ModelPart(model);
//        Whiskers.copyFrom(head);
//        Whiskers.setPos(-5.0F, 0.0F, -5.0F);
//
//
//        Whiskers_Left = new ModelPart(this);
//        Whiskers_Left.copyFrom(Whiskers);
//        Whiskers_Left.setPos(3.0F, 22.0F, 2.0F);
//        Whiskers.addChild(Whiskers_Left);
//        setRotationAngle(Whiskers_Left, 0.0F, -0.3054F, 0.0F);
//        Whiskers_Left.texOffs(3, 14).addBox(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 0.0F, 0.0F, false);
//
//        cube_r1 = new ModelPart(this);
//        cube_r1.copyFrom(Whiskers_Left);
//        cube_r1.setPos(0.0F, 0.0F, 0.0F);
//        Whiskers_Left.addChild(cube_r1);
//        setRotationAngle(cube_r1, 0.0F, 0.0F, -0.4363F);
//        cube_r1.texOffs(3, 14).addBox(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
//
//        cube_r2 = new ModelPart(this);
//        cube_r2.copyFrom(Whiskers_Left);
//        cube_r2.setPos(0.0F, 0.0F, 0.0F);
//        Whiskers_Left.addChild(cube_r2);
//        setRotationAngle(cube_r2, 0.0F, 0.0F, 0.4363F);
//        cube_r2.texOffs(3, 14).addBox(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
//
//        Whiskers_Right = new ModelPart(this);
//        Whiskers_Right.copyFrom(Whiskers);
//        Whiskers_Right.setPos(7.0F, 22.0F, 2.0F);
//        Whiskers.addChild(Whiskers_Right);
//        setRotationAngle(Whiskers_Right, 0.0F, 0.3054F, 0.0F);
//        Whiskers_Right.texOffs(3, 14).addBox(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, true);
//
//        cube_r3 = new ModelPart(this);
//        cube_r3.copyFrom(Whiskers_Right);
//        cube_r3.setPos(0.0F, 0.0F, 0.0F);
//        Whiskers_Right.addChild(cube_r3);
//        setRotationAngle(cube_r3, 0.0F, 0.0F, 0.4363F);
//        cube_r3.texOffs(3, 14).addBox(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, true);
//
//        cube_r4 = new ModelPart(this);
//        cube_r4.copyFrom(Whiskers_Right);
//        cube_r4.setPos(0.0F, 0.0F, 0.0F);
//        Whiskers_Right.addChild(cube_r4);
//        setRotationAngle(cube_r4, 0.0F, 0.0F, -0.4363F);
//        cube_r4.texOffs(3, 14).addBox(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, true);
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
        //Ears.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
