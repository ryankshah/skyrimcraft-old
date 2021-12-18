package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GuiUtil extends Screen
{
    protected GuiUtil(Component titleIn) {
        super(titleIn);
    }

    public static void drawCenteredSizedText(PoseStack matrixStack, Font fr, Component string, int x, int y, int big_small, int color) {
        if (big_small == 0) {
            matrixStack.pushPose();
            matrixStack.scale(0.5F, 0.5F, 0.5F);
            drawCenteredString(matrixStack, fr, string, x*2, y*2, color);
            matrixStack.popPose();
        } else if (big_small == 1) {
            matrixStack.pushPose();
            matrixStack.scale(1.25f, 1.25f, 1.25f);
            drawCenteredString(matrixStack, fr, string, (int)(x/1.25f), (int)(y/1.25f), color);
            matrixStack.popPose();
        }

    }

    public static void drawCenteredSizedText(PoseStack matrixStack, Font fr, String string, int x, int y, int big_small, int color) {
        if (big_small == 0) {
            matrixStack.pushPose();
            matrixStack.scale(0.5F, 0.5F, 0.5F);
            drawCenteredString(matrixStack, fr, string, x*2, y*2, color);
            matrixStack.popPose();
        } else if (big_small == 1) {
            matrixStack.pushPose();
            matrixStack.scale(1.25f, 1.25f, 1.25f);
            drawCenteredString(matrixStack, fr, string, (int)(x/1.25f), (int)(y/1.25f), color);
            matrixStack.popPose();
        }

    }

    public static void drawSizedText(PoseStack matrixStack, Font fr, Component string, int x, int y, int big_small, int color) {
        matrixStack.pushPose();
        if (big_small == 0) {
            matrixStack.scale(0.5F, 0.5F, 0.5F);
            drawString(matrixStack, fr, string, x*2, y*2, color);
        } else if (big_small == 1) {
            matrixStack.scale(1.25f, 1.25f, 1.25f);
            drawString(matrixStack, fr, string, (int)(x/1.25f), (int)(y/1.25f), color);
        }
        matrixStack.popPose();
    }

    public static void drawSizedText(PoseStack matrixStack, Font fr, String string, int x, int y, int big_small, int color) {
        matrixStack.pushPose();
        if (big_small == 0) {
            matrixStack.scale(0.5F, 0.5F, 0.5F);
            drawString(matrixStack, fr, string, x*2, y*2, color);
        } else if (big_small == 1) {
            matrixStack.scale(1.25f, 1.25f, 1.25f);
            drawString(matrixStack, fr, string, (int)(x/1.25f), (int)(y/1.25f), color);
        }
        matrixStack.popPose();
    }
}