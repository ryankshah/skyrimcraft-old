package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.skill.ISkill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class SkillsScreen extends Screen
{
    // Skill Nebula Cube Map
    public static final RenderSkyboxCube SKILL_NEBULA_CUBE_MAP = new RenderSkyboxCube(new ResourceLocation(Skyrimcraft.MODID, "textures/gui/panorama/nebula"));
    protected static final ResourceLocation OVERLAY_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/overlay_icons.png");

    // 83, 130 (main bar starting pos)
    // 80, 8 (main bar width,height)
    // 89, 145 (xp bar starting pos)
    // 65, 2 (xp bar width,height)
    private final int SKILL_BAR_CONTAINER_U = 83,
                      SKILL_BAR_CONTAINER_V = 130,
                      SKILL_BAR_CONTAINER_WIDTH = 80,
                      SKILL_BAR_CONTAINER_HEIGHT = 8,
                      SKILL_BAR_U = 89,
                      SKILL_BAR_V = 145,
                      SKILL_BAR_WIDTH = 65,
                      SKILL_BAR_HEIGHT = 2;

    private ISkyrimPlayerData playerCap;
    private Map<Integer, ISkill> skillsList;
    private Minecraft minecraft;
    private ClientPlayerEntity player;
    private float cubeMapPosition = 0.0f;

    protected SkillsScreen() {
        super(new TranslationTextComponent(".mapgui.title"));

        this.minecraft = Minecraft.getInstance();
        this.player = Minecraft.getInstance().player;
        this.playerCap = player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalStateException("Skills Screen capability failed"));

        this.skillsList = playerCap.getSkills();
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW_KEY_RIGHT || keyCode == GLFW_KEY_D) {
            cubeMapPosition += 10.0f;
        } else if(keyCode == GLFW_KEY_LEFT || keyCode == GLFW_KEY_A) {
            cubeMapPosition -= 10.0f;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        SKILL_NEBULA_CUBE_MAP.render(this.minecraft, MathHelper.sin(cubeMapPosition * 0.001F) * 5.0F + 25.0F, -cubeMapPosition * 0.1F, 1.0f);

        for(Map.Entry<Integer, ISkill> skillEntry : skillsList.entrySet()) {
            drawSkill(skillEntry.getValue(), matrixStack, ((skillEntry.getKey()+1) * SKILL_BAR_CONTAINER_WIDTH) + 24, height / 2);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    private void drawSkill(ISkill skill, MatrixStack matrixStack, int x, int y) {
        float skillProgress = skill.getXpProgress();
        float skillBarWidth = SKILL_BAR_WIDTH * skillProgress; // xpbar width, height == 65, 2

        minecraft.getTextureManager().bind(OVERLAY_ICONS);

        TextureDrawer.drawGuiTexture(matrixStack, x - (SKILL_BAR_CONTAINER_WIDTH / 2), y - (SKILL_BAR_CONTAINER_HEIGHT / 2), SKILL_BAR_CONTAINER_U, SKILL_BAR_CONTAINER_V, SKILL_BAR_CONTAINER_WIDTH, SKILL_BAR_CONTAINER_HEIGHT);
        TextureDrawer.drawGuiTexture(matrixStack, x - (SKILL_BAR_WIDTH / 2), y - (SKILL_BAR_HEIGHT / 2), SKILL_BAR_U + ((SKILL_BAR_WIDTH - skillBarWidth) / 2.0f), SKILL_BAR_V, SKILL_BAR_WIDTH * skillProgress, SKILL_BAR_HEIGHT);

        drawCenteredString(matrixStack, font, skill.getName() + " " + skill.getLevel(), x, y - 16, 0x00FFFFFF);
    }
}