package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.concurrent.atomic.AtomicInteger;

public class SkyrimIngameGui extends AbstractGui
{
    protected static final ResourceLocation OVERLAY_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/overlay_icons.png");

    public static final int PLAYER_BAR_MAX_WIDTH = 80;

    private static Minecraft mc = Minecraft.getInstance();
    private static FontRenderer fontRenderer = mc.font;

    public static void render(MatrixStack matrixStack, int width, int height) {
        mc.getTextureManager().bind(OVERLAY_ICONS);

        renderHealth(matrixStack, width, height);
        renderStamina(matrixStack, width, height);
        renderMagicka(matrixStack, width, height);
        renderCompass(matrixStack, width, height);
        renderTargetHealth(matrixStack, width, height);

        mc.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
    }

    private static void renderCompass(MatrixStack matrixStack, int width, int height) {
        TextureDrawer.drawGuiTexture(matrixStack, width / 2 - 110, 10, 0, 37, 221, 14);

        int rot;
        boolean f0 = mc.player.yRot < 0.0f;

        if(f0) rot = -MathHelper.floor(mc.player.yRot % 360);
        else rot = MathHelper.floor(mc.player.yRot % 360);

        boolean f1 = rot > 0 && rot < 180;
        boolean f2 = rot <= 270 && rot >= 90;
        boolean f3 = rot <= 180 && rot >= 0;

        AtomicInteger targetAngle = new AtomicInteger(-1);
        mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
            if(cap.getCurrentTarget() != null && cap.getCurrentTarget().isAlive()) {
                Vector3d playerPos = mc.player.getLookAngle();
                Vector3d targetPos = cap.getCurrentTarget().position();
                Vector3d norm = targetPos.subtract(playerPos);

                double angleDir = (Math.atan2(norm.z, norm.x) / 2 / Math.PI * 360 + 360) % 360;
                double angleLook = (Math.atan2(playerPos.z, playerPos.x) / 2 / Math.PI * 360 + 360) % 360;
                targetAngle.set((int)(angleDir - angleLook + 360) % 360);
            } else targetAngle.set(-1);
        });
        int targetEntityAngle = targetAngle.get();

        if (rot == 0) {
            drawCenteredString(matrixStack, fontRenderer, "S", width / 2, 13, 16777215);
            drawCenteredString(matrixStack, fontRenderer, "E", (width / 2) - 90, 13, 16777215);
            drawCenteredString(matrixStack, fontRenderer, "W", (width / 2) + 90, 13, 16777215);
        } else if (!f0) {
            drawCenteredString(matrixStack, fontRenderer, f2 ? "N" : "", (width / 2 - rot) + 180, 13, 16777215);
            if (!f1) rot -= 360;
            drawCenteredString(matrixStack, fontRenderer, !f2 ? "S" : "", width / 2 - rot, 13, 16777215);
            drawCenteredString(matrixStack, fontRenderer, !f3 ? "E" : "", (width / 2 - rot) - 90, 13, 16777215);
            drawCenteredString(matrixStack, fontRenderer, f3 ? "W" : "", (width / 2 - rot) + 90, 13, 16777215);
        } else if(f0) {
            drawCenteredString(matrixStack, fontRenderer, f2 ? "N" : "", (width / 2 + rot) - 180, 13, 16777215);
            if (!f1) rot -= 360;
            drawCenteredString(matrixStack, fontRenderer, !f2 ? "S" : "", width / 2 + rot, 13, 16777215);
            drawCenteredString(matrixStack, fontRenderer, !f3 ? "W" : "", (width / 2 + rot) + 90, 13, 16777215);
            drawCenteredString(matrixStack, fontRenderer, f3 ? "E" : "", (width / 2 + rot) - 90, 13, 16777215);
        }

        mc.getTextureManager().bind(OVERLAY_ICONS); // rebind after drawing strings (mc binds font texture)

//        System.out.println("playerRot: " + rot + " --- targetRot: " + targetEntityAngle);
//        System.out.println(targetEntityAngle);
        if(targetEntityAngle != -1) {
            if(rot / mc.player.getFieldOfViewModifier() < 1)
                TextureDrawer.drawGuiTexture(matrixStack, width / 2 - targetEntityAngle, 14, 106, 53, 4, 4);
            else
                TextureDrawer.drawGuiTexture(matrixStack, width / 2 + targetEntityAngle, 14, 106, 53, 4, 4);
        }
    }

    private static void renderHealth(MatrixStack matrixStack, int width, int height) {
        float healthPercentage = mc.player.getHealth() / mc.player.getMaxHealth();
        float healthBarWidth = PLAYER_BAR_MAX_WIDTH * healthPercentage;
        float healthBarStartX = (width / 2 - 39) + (PLAYER_BAR_MAX_WIDTH - healthBarWidth) / 2.0f;

        TextureDrawer.drawGuiTexture(matrixStack, width / 2 - 50, height - 40, 0, 51, 102, 10);
        TextureDrawer.drawGuiTexture(matrixStack, (int)healthBarStartX, height - 38, 11 + ((PLAYER_BAR_MAX_WIDTH - healthBarWidth) / 2.0f), 72, healthBarWidth, 6);
    }

    private static void renderStamina(MatrixStack matrixStack, int width, int height) {
        float staminaPercentage = mc.player.getFoodData().getFoodLevel() / 20.0f; // 20.0f is the max food value (this is apparently hardcoded...)
        float staminaBarWidth = PLAYER_BAR_MAX_WIDTH * staminaPercentage;
        float staminaBarStartX = (float)(width - 109) + (PLAYER_BAR_MAX_WIDTH - staminaBarWidth);

        TextureDrawer.drawGuiTexture(matrixStack, width - 120, height - 40, 0, 51, 102, 10);
        TextureDrawer.drawGuiTexture(matrixStack, (int)staminaBarStartX, height - 38, 11 + ((PLAYER_BAR_MAX_WIDTH - staminaBarWidth) / 2.0f), 80, (int)staminaBarWidth, 6);
    }

    private static void renderMagicka(MatrixStack matrixStack, int width, int height) {
        mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
            float magickaPercentage = cap.getMagicka() / cap.getMaxMagicka();
            float magickaBarWidth = PLAYER_BAR_MAX_WIDTH * magickaPercentage;

            TextureDrawer.drawGuiTexture(matrixStack, 20, height - 40, 0, 51, 102, 10);
            TextureDrawer.drawGuiTexture(matrixStack, 31, height - 38, 11 + ((PLAYER_BAR_MAX_WIDTH - magickaBarWidth) / 2.0f), 64, (int)(80 * magickaPercentage), 6);
        });
    }

    private static void renderTargetHealth(MatrixStack matrixStack, int width, int height) {
        mc.getTextureManager().bind(OVERLAY_ICONS);

        mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
            if(cap.getCurrentTarget() != null && cap.getCurrentTarget().isAlive()) {
                LivingEntity target = cap.getCurrentTarget();
                String entityName = target.getDisplayName().getString();

                if(!mc.player.closerThan(target, 16.0D))
                    return;

                float healthPercentage = target.getHealth() / target.getMaxHealth();
                float healthBarWidth = 142 * healthPercentage;
                float healthBarStartX = (width / 2 - 69) + (142 - healthBarWidth) / 2.0f;

                TextureDrawer.drawGuiTexture(matrixStack, (width / 2) - 78, 28, 3, 88, 156, 8);
                TextureDrawer.drawGuiTexture(matrixStack, (int)healthBarStartX, 31, 10 + ((142 - healthBarWidth) / 2.0f), 102, (int)healthBarWidth, 2);

                int entityNameWidth = fontRenderer.width(entityName);
                // left banner
                TextureDrawer.drawGuiTexture(matrixStack, (width / 2) - (41 + entityNameWidth), 38, 25, 107, 41, 12); // width / 2 - 69
                // right banner
                TextureDrawer.drawGuiTexture(matrixStack, (width / 2) + entityNameWidth, 38, 84, 107, 41, 12); // width / 2 + 28
                drawCenteredString(matrixStack, fontRenderer, entityName , width / 2, 40, 0x00FFFFFF);
            }
        });

        mc.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
    }
}
