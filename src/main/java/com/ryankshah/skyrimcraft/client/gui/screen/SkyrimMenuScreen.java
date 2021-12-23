package com.ryankshah.skyrimcraft.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.lwjgl.glfw.GLFW.*;

public class SkyrimMenuScreen extends Screen
{
    protected static final ResourceLocation MENU_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/cross.png");

    private Direction currentDirection;

    TranslatableComponent SKILLS = new TranslatableComponent("skyrimcraft.menu.skills");
    TranslatableComponent MAP = new TranslatableComponent("skyrimcraft.menu.map");
    TranslatableComponent QUESTS = new TranslatableComponent("skyrimcraft.menu.quests");
    TranslatableComponent MAGIC = new TranslatableComponent("skyrimcraft.menu.magic");

    public SkyrimMenuScreen() {
        super(new TranslatableComponent(Skyrimcraft.MODID + ".menuscreen.title"));

        currentDirection = Direction.NONE;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShaderTexture(0, MENU_ICONS);
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBackground(matrixStack);

        blit(matrixStack, this.width / 2 - 103, this.height / 2 - 50, 24, 81, 207, 100);

        // RenderSystem.setShaderTexture(0, MENU_ICONS);

        if(currentDirection == Direction.NORTH) {
            blit(matrixStack, this.width / 2 - 9, this.height / 2 - 90 - 9, 0, 0, 18, 17);
            drawCenteredString(matrixStack, font, SKILLS, this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, MAP, this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, QUESTS, this.width / 2 + 103 + font.width(QUESTS), this.height / 2 - 4, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, MAGIC, this.width / 2 - 103 - font.width(MAGIC), this.height / 2 - 4, 0x00FFFFFF);
        } else if(currentDirection == Direction.SOUTH) {
            blit(matrixStack, this.width / 2 - 9, this.height / 2 + 70 + 9, 18, 0, 18, 17);
            drawCenteredString(matrixStack, font, SKILLS, this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, MAP, this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, QUESTS, this.width / 2 + 103 + font.width(QUESTS), this.height / 2 - 4, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, MAGIC, this.width / 2 - 103 - font.width(MAGIC), this.height / 2 - 4, 0x00FFFFFF);
        } else if(currentDirection == Direction.EAST) {
            blit(matrixStack, this.width / 2 - 170 - 9, this.height / 2 - 9, 53, 0, 18, 17);
            drawCenteredString(matrixStack, font, SKILLS, this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, MAP, this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, QUESTS, this.width / 2 + 103 + font.width(QUESTS), this.height / 2 - 4, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, MAGIC, this.width / 2 - 103 - font.width(MAGIC), this.height / 2 - 4, 0x00FFFFFF);
        } else if(currentDirection == Direction.WEST) {
            blit(matrixStack, this.width / 2 + 170 + 9, this.height / 2 - 9, 36, 0, 18, 17);
            drawCenteredString(matrixStack, font, SKILLS, this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, MAP, this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, QUESTS, this.width / 2 + 103 + font.width(QUESTS), this.height / 2 - 4, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, MAGIC, this.width / 2 - 103 - font.width(MAGIC), this.height / 2 - 4, 0x00FFFFFF);
        } else {
            drawCenteredString(matrixStack, font, SKILLS, this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, MAP, this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, QUESTS, this.width / 2 + 103 + font.width(QUESTS), this.height / 2 - 4, 0x00FFFFFF);
            drawCenteredString(matrixStack, font, MAGIC, this.width / 2 - 103 - font.width(MAGIC), this.height / 2 - 4, 0x00FFFFFF);
        }

        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
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
                minecraft.setScreen(new SkillsScreen());
                // minecraft.setScreen(null);
                // minecraft.player.displayClientMessage(new TranslationTextComponent("skyrimcraft.menu.option.unavailable"), false);
            } else if(currentDirection == Direction.SOUTH) {
                // minecraft.setScreen(null);
                // minecraft.setScreen(new MapScreen());
                // minecraft.setScreen(null);
                minecraft.player.displayClientMessage(new TranslatableComponent("skyrimcraft.menu.option.unavailable"), false);
            } else if(currentDirection == Direction.WEST) {
                minecraft.setScreen(null);
                minecraft.setScreen(new QuestScreen()); // We have moved this to be quests. Used to be: `InventoryScreen(minecraft.player)`
            } else if(currentDirection == Direction.EAST) {
                AtomicReference<List<ISpell>> knownSpells = new AtomicReference<List<ISpell>>();
                minecraft.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
                    knownSpells.set(cap.getKnownSpells());
                });

                if(knownSpells.get().isEmpty()) {
                    minecraft.setScreen(null);
                    minecraft.player.displayClientMessage(new TranslatableComponent("skyrimcraft.menu.option.magic.none"), false);
                } else {
                    minecraft.setScreen(null);
                    minecraft.setScreen(new MagicScreen(knownSpells.get()));
                }
            } else minecraft.player.displayClientMessage(new TranslatableComponent("skyrimcraft.menu.option.invalid"), false);
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