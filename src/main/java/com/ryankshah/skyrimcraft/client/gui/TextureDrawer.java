package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;

public class TextureDrawer
{
    private static BufferBuilder buffer;

    public static void drawTexture(PoseStack matrix, float x, float y, float w, float h, float minU, float maxU, float minV, float maxV)
    {
        start();
        fillBuffer(matrix, x, y, w, h, minU, maxU, minV, maxV);
        end();
    }

    public static void drawTexture(PoseStack matrix, float x, float y, float w, float h, float minU, float maxU, float minV, float maxV, int color)
    {
        startColored();
        fillBuffer(matrix, x, y, w, h, minU, maxU, minV, maxV, color);
        end();
    }

    public static void drawGuiTexture(PoseStack matrix, float x, float y, float texX, float texY, float w, float h)
    {
        start();
        fillGuiBuffer(matrix, x, y, texX, texY, w, h);
        end();
    }

    public static void drawGuiTexture(PoseStack matrix, float x, float y, float texX, float texY, float w, float h, int color)
    {
        startColored();
        fillGuiBuffer(matrix, x, y, texX, texY, w, h, color);
        end();
    }

    public static void start()
    {
        if (buffer != null) { throw new IllegalStateException("Last drawing operation not finished!"); }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
    }

    public static void startColored()
    {
        if (buffer != null) { throw new IllegalStateException("Last drawing operation not finished!"); }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
    }

    public static void fillBuffer(PoseStack matrix, float x, float y, float w, float h, float minU, float maxU, float minV, float maxV)
    {
        if (buffer == null) { throw new IllegalStateException("Drawing operation not started!"); }

        buffer.vertex(matrix.last().pose(), x,y + h, -90).uv(minU, maxV).endVertex();
        buffer.vertex(matrix.last().pose(), x + w, y + h, -90).uv(maxU, maxV).endVertex();
        buffer.vertex(matrix.last().pose(), x + w, y,     -90).uv(maxU, minV).endVertex();
        buffer.vertex(matrix.last().pose(), x, y,-90).uv(minU, minV).endVertex();
    }

    public static void fillBuffer(PoseStack matrix, float x, float y, float w, float h, float minU, float maxU, float minV, float maxV, int color)
    {
        if (buffer == null) { throw new IllegalStateException("Drawing operation not started!"); }

        int[] colors = getRGBAArrayFromHexColor(color);
        buffer.vertex(matrix.last().pose(), x, y + h, -90).color(colors[0], colors[1], colors[2], colors[3]).uv(minU, maxV).endVertex();
        buffer.vertex(matrix.last().pose(), x + w, y + h, -90).color(colors[0], colors[1], colors[2], colors[3]).uv(maxU, maxV).endVertex();
        buffer.vertex(matrix.last().pose(), x + w, y,     -90).color(colors[0], colors[1], colors[2], colors[3]).uv(maxU, minV).endVertex();
        buffer.vertex(matrix.last().pose(), x,     y,     -90).color(colors[0], colors[1], colors[2], colors[3]).uv(minU, minV).endVertex();
    }

    public static void fillGuiBuffer(PoseStack matrix, float x, float y, float texX, float texY, float w, float h)
    {
        float minU = texX / 256F;
        float maxU = minU + (w / 256F);
        float minV = texY / 256F;
        float maxV = minV + (h / 256F);
        fillBuffer(matrix, x, y, w, h, minU, maxU, minV, maxV);
    }

    public static void fillGuiBuffer(PoseStack matrix, float x, float y, float texX, float texY, float w, float h, int color)
    {
        float minU = texX / 256F;
        float maxU = minU + (w / 256F);
        float minV = texY / 256F;
        float maxV = minV + (h / 256F);
        fillBuffer(matrix, x, y, w, h, minU, maxU, minV, maxV, color);
    }

    public static void end()
    {
        if (buffer == null) { throw new IllegalStateException("Drawing operation not started!"); }

        buffer.end();
        //noinspection deprecation
        //RenderSystem.enableAlphaTest();
        BufferUploader.end(buffer);

        buffer = null;
    }

    public static int[] getRGBAArrayFromHexColor(int color)
    {
        int[] ints = new int[4];
        ints[0] = (color >> 24 & 255);
        ints[1] = (color >> 16 & 255);
        ints[2] = (color >>  8 & 255);
        ints[3] = (color       & 255);
        return ints;
    }
}