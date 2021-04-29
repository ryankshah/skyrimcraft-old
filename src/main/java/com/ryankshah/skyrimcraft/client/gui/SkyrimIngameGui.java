package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

public class SkyrimIngameGui extends AbstractGui
{
    protected static final ResourceLocation OVERLAY_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/overlay_icons.png");

    private final int PLAYER_BAR_MAX_WIDTH = 80;

    private Minecraft mc;
    private MatrixStack matrixStack;
    private FontRenderer fontRenderer;
    private int width, height;

    public SkyrimIngameGui(MatrixStack ms, int width, int height) {
        this.width = width;
        this.height = height;

        this.mc = Minecraft.getInstance();
        this.matrixStack = ms;
        this.fontRenderer = mc.fontRenderer;

        render();
    }

    protected void render() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        this.mc.getTextureManager().bindTexture(OVERLAY_ICONS);

        renderHealth();
        renderStamina();
        renderMagicka();
        renderCompass();

        this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);

        renderTargetHealth();

        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
    }

    private void renderCompass() {
        blit(this.matrixStack, this.width / 2 - 110, 10, 0, 37, 221, 14);

        int rot;
        boolean f0 = mc.player.rotationYaw < 0.0f;

        if(f0) rot = -MathHelper.floor(this.mc.player.rotationYaw % 360);
        else rot = MathHelper.floor(this.mc.player.rotationYaw % 360);

        boolean f1 = rot > 0 && rot < 180;
        boolean f2 = rot <= 270 && rot >= 90;
        boolean f3 = rot <= 180 && rot >= 0;

        if (rot == 0) {
            drawCenteredString(matrixStack, fontRenderer, "S", this.width / 2, 13, 16777215);
            drawCenteredString(matrixStack, fontRenderer, "E", (this.width / 2) - 90, 13, 16777215);
            drawCenteredString(matrixStack, fontRenderer, "W", (this.width / 2) + 90, 13, 16777215);
        } else if (!f0) {
            drawCenteredString(matrixStack, fontRenderer, f2 ? "N" : "", (this.width / 2 - rot) + 180, 13, 16777215);
            if (!f1) rot -= 360;
            drawCenteredString(matrixStack, fontRenderer, !f2 ? "S" : "", this.width / 2 - rot, 13, 16777215);
            drawCenteredString(matrixStack, fontRenderer, !f3 ? "E" : "", (this.width / 2 - rot) - 90, 13, 16777215);
            drawCenteredString(matrixStack, fontRenderer, f3 ? "W" : "", (this.width / 2 - rot) + 90, 13, 16777215);
        } else if(f0) {
            drawCenteredString(matrixStack, fontRenderer, f2 ? "N" : "", (this.width / 2 + rot) - 180, 13, 16777215);
            if (!f1) rot -= 360;
            drawCenteredString(matrixStack, fontRenderer, !f2 ? "S" : "", this.width / 2 + rot, 13, 16777215);
            drawCenteredString(matrixStack, fontRenderer, !f3 ? "W" : "", (this.width / 2 + rot) + 90, 13, 16777215);
            drawCenteredString(matrixStack, fontRenderer, f3 ? "E" : "", (this.width / 2 + rot) - 90, 13, 16777215);
        }
    }

    private void renderHealth() {
        float healthPercentage = mc.player.getHealth() / mc.player.getMaxHealth();
        float healthBarWidth = PLAYER_BAR_MAX_WIDTH * healthPercentage;
        float healthBarStartX = (this.width / 2 - 39) + (PLAYER_BAR_MAX_WIDTH - healthBarWidth) / 2.0f;

        blit(this.matrixStack, this.width / 2 - 50, this.height - 40, 0, 51, 102, 10);
        blit(this.matrixStack, (int)healthBarStartX, this.height - 38, 11, 72, (int)healthBarWidth, 6);
    }

    private void renderStamina() {
        float staminaPercentage = mc.player.getFoodStats().getFoodLevel() / 20.0f; // 20.0f is the max food value (this is apparently hardcoded...)
        float staminaBarWidth = PLAYER_BAR_MAX_WIDTH * staminaPercentage;
        float staminaBarStartX = (float)(width - 109) + (PLAYER_BAR_MAX_WIDTH - staminaBarWidth);
        this.blit(this.matrixStack, this.width - 120, this.height - 40, 0, 51, 102, 10);
        this.blit(this.matrixStack, (int)staminaBarStartX, this.height - 38, 11, 80, (int)staminaBarWidth, 6);
    }

    private void renderMagicka() {
        mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
            float magickaPercentage = cap.getMagicka() / cap.getMaxMagicka();
            this.blit(this.matrixStack, 20, this.height - 40, 0, 51, 102, 10);
            this.blit(this.matrixStack, 31, this.height - 38, 11, 64, (int)(80 * magickaPercentage), 6);
        });
    }

    private void renderTargetHealth() {
        this.mc.getTextureManager().bindTexture(OVERLAY_ICONS);

        mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
            if(cap.getCurrentTarget() != null && cap.getCurrentTarget().isAlive()) {
                LivingEntity target = cap.getCurrentTarget();
                String entityName = target.getDisplayName().getString();

                if(!mc.player.isEntityInRange(target, 16.0D))
                    return;

                float healthPercentage = target.getHealth() / target.getMaxHealth();
                float healthBarWidth = 142 * healthPercentage;
                float healthBarStartX = (this.width / 2 - 87) + (142 - healthBarWidth) / 2.0f;

                this.blit(this.matrixStack, (this.width / 2) - 78, 28, 3, 88, 156, 8);
                this.blit(this.matrixStack, (int)healthBarStartX, 31, 10, 102, (int)healthBarWidth, 2);

                // left banner
                this.blit(this.matrixStack, (this.width / 2) - 69, 38, 25, 107, 41, 12);
                // right banner
                this.blit(this.matrixStack, (this.width / 2) + 28, 38, 84, 107, 41, 12);
                drawCenteredString(matrixStack, fontRenderer, entityName , this.width / 2, 40, 0x00FFFFFF);
            }
        });

        this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
    }
}
