package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.spell.ISpell;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.lwjgl.glfw.GLFW.*;

public class SkyrimMenuScreen extends Screen
{
    protected static final ResourceLocation MENU_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/cross.png");

    private Direction currentDirection;

    TranslationTextComponent SKILLS = new TranslationTextComponent("skyrimcraft.menu.skills");
    TranslationTextComponent MAP = new TranslationTextComponent("skyrimcraft.menu.map");
    TranslationTextComponent ITEMS = new TranslationTextComponent("skyrimcraft.menu.items");
    TranslationTextComponent MAGIC = new TranslationTextComponent("skyrimcraft.menu.magic");

    public SkyrimMenuScreen() {
        super(new TranslationTextComponent(Skyrimcraft.MODID + ".menugui.title"));

        currentDirection = Direction.NONE;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        minecraft.getTextureManager().bind(MENU_ICONS);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBackground(matrixStack);

        blit(matrixStack, this.width / 2 - 103, this.height / 2 - 50, 24, 81, 207, 100);

        drawCenteredString(matrixStack, font, SKILLS, this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
        drawCenteredString(matrixStack, font, MAP, this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
        drawCenteredString(matrixStack, font, ITEMS, this.width / 2 + 103 + font.width(ITEMS), this.height / 2 - 4, 0x00FFFFFF);
        drawCenteredString(matrixStack, font, MAGIC, this.width / 2 - 103 - font.width(MAGIC), this.height / 2 - 4, 0x00FFFFFF);

        minecraft.getTextureManager().bind(MENU_ICONS);

        if(currentDirection == Direction.NORTH) {
            blit(matrixStack, this.width / 2 - 9, this.height / 2 - 90 - 9, 0, 0, 18, 17);
        } else if(currentDirection == Direction.SOUTH) {
            blit(matrixStack, this.width / 2 - 9, this.height / 2 + 70 + 9, 18, 0, 18, 17);
        } else if(currentDirection == Direction.EAST) {
            blit(matrixStack, this.width / 2 - 170 - 9, this.height / 2 - 9, 53, 0, 18, 17);
        } else if(currentDirection == Direction.WEST) {
            blit(matrixStack, this.width / 2 + 170 + 9, this.height / 2 - 9, 36, 0, 18, 17);
        }

        minecraft.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == GLFW_KEY_RIGHT || keyCode == GLFW_KEY_D)
            currentDirection = Direction.WEST;
        else if(keyCode == GLFW_KEY_LEFT || keyCode == GLFW_KEY_A)
            currentDirection = Direction.EAST;
        else if(keyCode == GLFW_KEY_UP || keyCode == GLFW_KEY_W)
            currentDirection = Direction.NORTH;
        else if(keyCode == GLFW_KEY_DOWN || keyCode == GLFW_KEY_S)
            currentDirection = Direction.SOUTH;
        else if(keyCode == GLFW_KEY_ENTER) {
            if(currentDirection == Direction.NORTH) {
                minecraft.setScreen(null);
                minecraft.player.displayClientMessage(new TranslationTextComponent("skyrimcraft.menu.option.unavailable"), false);
            } else if(currentDirection == Direction.SOUTH) {
                minecraft.setScreen(null);
                minecraft.player.displayClientMessage(new TranslationTextComponent("skyrimcraft.menu.option.unavailable"), false);
            } else if(currentDirection == Direction.WEST) {
                minecraft.setScreen(null);
                minecraft.setScreen(new InventoryScreen(minecraft.player));
            } else if(currentDirection == Direction.EAST) {
                AtomicReference<List<ISpell>> knownSpells = new AtomicReference<List<ISpell>>();
                minecraft.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
                    knownSpells.set(cap.getKnownSpells());
                });

                if(knownSpells.get().isEmpty()) {
                    minecraft.setScreen(null);
                    minecraft.player.displayClientMessage(new TranslationTextComponent("skyrimcraft.menu.option.magic.none"), false);
                } else {
                    minecraft.setScreen(null);
                    minecraft.setScreen(new SkyrimMagicGui(knownSpells.get()));
                }
            }  else
                minecraft.player.displayClientMessage(new TranslationTextComponent("skyrimcraft.menu.option.invalid"), false);
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
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