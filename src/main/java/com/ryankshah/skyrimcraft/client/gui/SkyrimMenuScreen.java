package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import static org.lwjgl.glfw.GLFW.*;

public class SkyrimMenuScreen extends Screen
{
    protected static final ResourceLocation MENU_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/cross.png");

    private Direction currentDirection;

    public SkyrimMenuScreen() {
        super(new TranslationTextComponent(Skyrimcraft.MODID + ".menugui.title"));

        currentDirection = Direction.NONE;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        minecraft.getTextureManager().bindTexture(MENU_ICONS);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBackground(matrixStack);

        blit(matrixStack, this.width / 2 - 115, this.height / 2 - 50, 0, 81, 230, 101);

        if(currentDirection == Direction.NORTH) {
            blit(matrixStack, this.width / 2 - 2, this.height / 2 - 90, 0, 0, 17, 18);
            drawSizedText(matrixStack, font, "Skills", this.width / 2, this.height / 2 - 65, 1, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, "Map", this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, "Items", this.width / 2 + 118, this.height / 2 - 4, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, "Magic", this.width / 2 - 135, this.height / 2 - 4, 0x00FFFFFF);
        } else if(currentDirection == Direction.SOUTH) {
            blit(matrixStack, this.width / 2 - 3, this.height / 2 + 70, 18, 0, 17, 18);
            drawCenteredString(matrixStack, font, "Skills", this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
            drawSizedText(matrixStack, font, "Map", this.width / 2, this.height / 2 + 55, 1, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, "Items", this.width / 2 + 118, this.height / 2 - 4, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, "Magic", this.width / 2 - 135, this.height / 2 - 4, 0x00FFFFFF);
        } else if(currentDirection == Direction.EAST) {
            blit(matrixStack, this.width / 2 - 170, this.height / 2 - 10, 53, 0, 17, 18);
            drawCenteredString(matrixStack, font, "Skills", this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, "Map", this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, "Items", this.width / 2 + 118, this.height / 2 - 4, 0x00FFFFFF);
            drawSizedText(matrixStack, font, "Magic", this.width / 2 - 144, this.height / 2 - 4, 1, 0x00FFFFFF);
        } else if(currentDirection == Direction.WEST) {
                blit(matrixStack, this.width / 2 + 165, this.height / 2 - 10, 36, 0, 17, 18);
                drawCenteredString(matrixStack, font, "Skills", this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
                drawCenteredString(matrixStack, font, "Map", this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
                drawSizedText(matrixStack, font, "Items", this.width / 2 + 115, this.height / 2 - 4, 1, 0x00FFFFFF);
                drawCenteredString(matrixStack, font, "Magic", this.width / 2 - 135, this.height / 2 - 4, 0x00FFFFFF);
        } else if(currentDirection == Direction.NONE){
            drawCenteredString(matrixStack, font, "Skills", this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, "Map", this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, "Items", this.width / 2 + 118, this.height / 2 - 4, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, "Magic", this.width / 2 - 135, this.height / 2 - 4, 0x00FFFFFF);
        }

        minecraft.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == GLFW_KEY_RIGHT)
            currentDirection = Direction.WEST;
        else if(keyCode == GLFW_KEY_LEFT)
            currentDirection = Direction.EAST;
        else if(keyCode == GLFW_KEY_UP)
            currentDirection = Direction.NORTH;
        else if(keyCode == GLFW_KEY_DOWN)
            currentDirection = Direction.SOUTH;
        else if(keyCode == GLFW_KEY_ENTER) {
            if(currentDirection == Direction.NORTH)
                minecraft.player.sendChatMessage("[Skyrimcraft] - Skills Unavailable!");
            else if(currentDirection == Direction.SOUTH)
                minecraft.player.sendChatMessage("[Skyrimcraft] - Map Unavailable!");
            else if(currentDirection == Direction.EAST) {
                minecraft.displayGuiScreen(null);
                minecraft.player.inventory.openInventory(minecraft.player);
            } else if(currentDirection == Direction.WEST)
                minecraft.player.sendChatMessage("[Skyrimcraft] - Magic Unavailable!");
            else
                minecraft.player.sendChatMessage("[Skyrimcraft] - Invalid Option!");
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void drawSizedText(MatrixStack matrixStack, FontRenderer fr, String string, int x, int y, int big_small, int color) {
        if (big_small == 0) {
            matrixStack.push();
            matrixStack.scale(0.5F, 0.5F, 0.5F);
            drawCenteredString(matrixStack, fr, string, x*2, y*2, color);
            matrixStack.pop();
        } else if (big_small == 1) {
            matrixStack.push();
            matrixStack.scale(1.25F, 1.25F, 1.25F);
            drawCenteredString(matrixStack, fr, string, (x - 5) / 2, (y - 5) / 2, color);
            matrixStack.pop();
        }

    }

    enum Direction {
        NONE(),
        NORTH(),
        SOUTH(),
        EAST(),
        WEST();

        Direction() {
        }
    }

}