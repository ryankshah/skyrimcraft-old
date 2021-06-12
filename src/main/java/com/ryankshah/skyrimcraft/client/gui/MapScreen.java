package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class MapScreen extends Screen
{
    private Minecraft minecraft;
    private ClientPlayerEntity player;
    private World world;
    private Vector3d camera;

    protected MapScreen() {
        super(new TranslationTextComponent(".mapgui.title"));

        this.minecraft = Minecraft.getInstance();
        this.player = Minecraft.getInstance().player;
        this.world = player.level;
        this.camera = minecraft.gameRenderer.getMainCamera().getPosition();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.renderBackground(matrixStack);

        //RenderSystem.enableDepthTest();

        // Set d1 to 200 or max build height...
        double d0 = 0, d1 = 200d, d2 = 0;

        //d0 = 0D;
        //d1 = 0D;
        //d2 = 0D;

        Matrix4f matrix4f1 = matrixStack.last().pose();
        this.resetProjectionMatrix(matrix4f1);

        // getMain
        float width = minecraft.getMainRenderTarget().viewWidth;
        float height = minecraft.getMainRenderTarget().viewHeight;

        Matrix4f matrix4f2 = Matrix4f.orthographic(width / height, 1, 0.1f, 1000.0f);
        //this.resetProjectionMatrix(matrix4f2);
        matrixStack.pushPose();
        matrixStack.last().pose().set(matrix4f2);
        matrixStack.pushPose();
        //        ClippingHelper clippinghelper = new ClippingHelper(matrix4f2, matrix4f1);
//        clippinghelper.prepare(d0, d1, d2);

        //matrixStack.mulPose(new Quaternion(Vector3f.XN.rotationDegrees(-22.5f)));
        matrixStack.mulPose(new Quaternion(Vector3f.XP.rotationDegrees(60f)));
        //matrixStack.mulPose(new Quaternion(Vector3f.YP.rotationDegrees(45f)));

        //matrixStack.translate(0, 0, 0);

        matrixStack.pushPose();
        matrixStack.scale(1/100F, 1/100F, 1/100F);
        //matrixStack.mulPose(new Quaternion(Vector3f.YP.rotationDegrees(-45f)));
        //matrixStack.translate(0, 0, 0);
        minecraft.levelRenderer.renderChunkLayer(RenderType.solid(), matrixStack, d0, d1, d2);
        minecraft.getModelManager().getAtlas(AtlasTexture.LOCATION_BLOCKS).setBlurMipmap(false, this.minecraft.options.mipmapLevels > 0); // FORGE: fix flickering leaves when mods mess up the blurMipmap settings
        minecraft.levelRenderer.renderChunkLayer(RenderType.cutoutMipped(), matrixStack, d0, d1, d2);
        minecraft.getModelManager().getAtlas(AtlasTexture.LOCATION_BLOCKS).restoreLastBlurMipmap();
        minecraft.levelRenderer.renderChunkLayer(RenderType.cutout(), matrixStack, d0, d1, d2);
        //RenderHelper.setupLevel(matrixStack.last().pose());

        matrixStack.popPose();
        matrixStack.popPose();
        matrixStack.popPose();

        //RenderSystem.disableDepthTest();

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void resetProjectionMatrix(Matrix4f p_228379_1_) {
        RenderSystem.matrixMode(5889); // projection view
        RenderSystem.loadIdentity();
        RenderSystem.multMatrix(p_228379_1_);
        RenderSystem.matrixMode(5888); // model view
        RenderSystem.loadIdentity();
        RenderSystem.multMatrix(p_228379_1_);
    }
}