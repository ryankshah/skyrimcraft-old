package com.ryankshah.skyrimcraft.character.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class KhajiitHeadModel extends EntityModel<AbstractClientPlayerEntity>
{
    private PlayerModel<AbstractClientPlayerEntity> playerModel;

    private final ModelRenderer Ears;
    private final ModelRenderer Ear_Left;
    private final ModelRenderer Ear_Right;
    private final ModelRenderer Whiskers;
    private final ModelRenderer Whiskers_Left;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer Whiskers_Right;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;

    public KhajiitHeadModel(PlayerModel<AbstractClientPlayerEntity> model, ModelRenderer head) {
        this.playerModel = model;
        texWidth = 64;
        texHeight = 64;

        Ears = new ModelRenderer(model);
        Ears.copyFrom(head);
        Ears.setPos(0.0F, 1.0F, 0.0F);


        Ear_Left = new ModelRenderer(this);
        Ear_Left.setPos(5.0F, -24.0F, 4.0F);
        Ears.addChild(Ear_Left);
        setRotationAngle(Ear_Left, 0.0F, 1.5708F, 0.0F);
        Ear_Left.texOffs(3, 12).addBox(4.0F, 19.0F, -2.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        Ear_Left.texOffs(3, 12).addBox(4.0F, 18.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        Ear_Left.texOffs(4, 14).addBox(4.0F, 17.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        Ear_Right = new ModelRenderer(this);
        Ear_Right.setPos(-5.0F, -24.0F, -5.0F);
        Ears.addChild(Ear_Right);
        setRotationAngle(Ear_Right, 0.0F, -1.5708F, 0.0F);
        Ear_Right.texOffs(3, 12).addBox(4.0F, 19.0F, -2.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        Ear_Right.texOffs(3, 12).addBox(4.0F, 18.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        Ear_Right.texOffs(4, 14).addBox(4.0F, 17.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        Whiskers = new ModelRenderer(model);
        Whiskers.copyFrom(head);
        Whiskers.setPos(-5.0F, 0.0F, -5.0F);


        Whiskers_Left = new ModelRenderer(this);
        Whiskers_Left.copyFrom(Whiskers);
        Whiskers_Left.setPos(3.0F, 22.0F, 2.0F);
        Whiskers.addChild(Whiskers_Left);
        setRotationAngle(Whiskers_Left, 0.0F, -0.3054F, 0.0F);
        Whiskers_Left.texOffs(3, 14).addBox(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 0.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.copyFrom(Whiskers_Left);
        cube_r1.setPos(0.0F, 0.0F, 0.0F);
        Whiskers_Left.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, -0.4363F);
        cube_r1.texOffs(3, 14).addBox(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.copyFrom(Whiskers_Left);
        cube_r2.setPos(0.0F, 0.0F, 0.0F);
        Whiskers_Left.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, 0.4363F);
        cube_r2.texOffs(3, 14).addBox(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        Whiskers_Right = new ModelRenderer(this);
        Whiskers_Right.copyFrom(Whiskers);
        Whiskers_Right.setPos(7.0F, 22.0F, 2.0F);
        Whiskers.addChild(Whiskers_Right);
        setRotationAngle(Whiskers_Right, 0.0F, 0.3054F, 0.0F);
        Whiskers_Right.texOffs(3, 14).addBox(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, true);

        cube_r3 = new ModelRenderer(this);
        cube_r3.copyFrom(Whiskers_Right);
        cube_r3.setPos(0.0F, 0.0F, 0.0F);
        Whiskers_Right.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.0F, 0.4363F);
        cube_r3.texOffs(3, 14).addBox(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, true);

        cube_r4 = new ModelRenderer(this);
        cube_r4.copyFrom(Whiskers_Right);
        cube_r4.setPos(0.0F, 0.0F, 0.0F);
        Whiskers_Right.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.0F, -0.4363F);
        cube_r4.texOffs(3, 14).addBox(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, true);
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
