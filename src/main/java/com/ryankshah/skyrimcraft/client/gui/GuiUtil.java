package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiUtil extends Screen
{
    protected GuiUtil(ITextComponent titleIn) {
        super(titleIn);
    }

    public static void drawSizedText(MatrixStack matrixStack, FontRenderer fr, TranslationTextComponent string, int x, int y, int big_small, int color) {
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
}