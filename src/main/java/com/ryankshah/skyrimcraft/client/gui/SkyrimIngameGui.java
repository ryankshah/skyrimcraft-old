package com.ryankshah.skyrimcraft.client.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

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

        float yaw = MathHelper.lerp(mc.getFrameTime(), mc.player.yHeadRotO, mc.player.yHeadRot) % 360;
        if (yaw < 0) yaw += 360;

        drawCardinalDirection(matrixStack, yaw, 0, width / 2, "S");
        drawCardinalDirection(matrixStack, yaw, 90, width / 2, "W");
        drawCardinalDirection(matrixStack, yaw, 180, width / 2, "N");
        drawCardinalDirection(matrixStack, yaw, 270, width / 2, "E");

        double playerPosX = MathHelper.lerp(mc.getFrameTime(), mc.player.xo, mc.player.getX());
        double playerPosY = MathHelper.lerp(mc.getFrameTime(), mc.player.yo, mc.player.getY());
        double playerPosZ = MathHelper.lerp(mc.getFrameTime(), mc.player.zo, mc.player.getZ());
        final float finalYaw = yaw;

        mc.getTextureManager().bind(OVERLAY_ICONS); // rebind after drawing strings (mc binds font texture)

        mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
            if(cap.getCompassFeatures().size() > 0) {
                List<CompassFeature> sortedFeatures = Lists.newArrayList(cap.getCompassFeatures());
                sortedFeatures.sort((a,b) -> {
                    Vector3d positionA = new Vector3d(a.getChunkPos().x, 0, a.getChunkPos().z); //mc.player.getY()
                    Vector3d positionB = new Vector3d(b.getChunkPos().x, 0, b.getChunkPos().z);
                    float angleA = Math.abs(angleDistance(finalYaw, angleFromTarget(positionA, positionB).x));
                    float angleB = Math.abs(angleDistance(finalYaw, angleFromTarget(positionB, positionA).x));
                    return (int)Math.signum(angleB-angleA);
                });

                for (CompassFeature feature : sortedFeatures) {
                    if(mc.player.position().distanceToSqr(feature.getChunkPos().x, mc.player.position().y, feature.getChunkPos().z) <= 512 * 16) { // 256 blocks?
                        Vector3d position = new Vector3d(feature.getChunkPos().x, 0, feature.getChunkPos().z);
                        Vector2f angleYd = angleFromTarget(position, new Vector3d(playerPosX, playerPosY, playerPosZ));
                        drawStructureIndicator(matrixStack, finalYaw, angleYd.x, width / 2, feature);
                    }
                }
            }
            if (cap.getTargetingEntities() != null) {
                for(int entityID : cap.getTargetingEntities()) {
                    if(mc.player.level.getEntity(entityID) == null)
                        return;

                    LivingEntity targetingEntity = (LivingEntity) mc.player.level.getEntity(entityID);
                    // Check player is out of target range
                    if(!mc.player.closerThan(targetingEntity, 16.0D))
                        return;

                    Vector3d a = new Vector3d(playerPosX, playerPosY, playerPosZ);
                    Vector3d b = targetingEntity.position();

                    Vector2f angleYd = angleFromTarget(b, a);
                    drawTargetIndicator(matrixStack, finalYaw, angleYd.x, width / 2); //ydelta = angleYd.y
                }
            }
            if (cap.getCurrentTarget() != null && cap.getCurrentTarget().isAlive()) {
                // Check player is out of target range
                if(!mc.player.closerThan(cap.getCurrentTarget(), 16.0D))
                    return;

                Vector3d a = new Vector3d(playerPosX, playerPosY, playerPosZ);
                Vector3d b = cap.getCurrentTarget().position();

                Vector2f angleYd = angleFromTarget(b, a);
                drawTargetIndicator(matrixStack, finalYaw, angleYd.x, width / 2); //ydelta = angleYd.y
            }
        });
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
                TextureDrawer.drawGuiTexture(matrixStack, (width / 2) - 2 - (41 + entityNameWidth / 2), 38, 25, 107, 41, 12); // width / 2 - 69
                // right banner
                TextureDrawer.drawGuiTexture(matrixStack, (width / 2) + 2 + entityNameWidth / 2, 38, 84, 107, 41, 12); // width / 2 + 28
                drawCenteredString(matrixStack, fontRenderer, entityName , width / 2, 40, 0x00FFFFFF);
            }
        });

        mc.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
    }

    private static void drawCardinalDirection(MatrixStack matrixStack, float yaw, float angle, int xPos, String text) {
        float nDist = angleDistance(yaw, angle);
        if (Math.abs(nDist) <= 90)
        {
            float nPos = xPos + nDist;
            //fill(matrixStack, (int)(nPos-0.5f), 10, (int)(nPos+0.5f), 18, 0x7FFFFFFF);
            drawCenteredString(matrixStack, fontRenderer, text, (int)nPos, 13, 0xFFFFFF);
        }
    }

    private static Vector2f angleFromTarget(Vector3d targetPos, Vector3d playerPos) {
        double xd = targetPos.x - playerPos.x;
        double yd = targetPos.y - playerPos.y;
        double zd = targetPos.z - playerPos.z;
        return new Vector2f((float) Math.toDegrees(-Math.atan2(xd, zd)), (float)yd);
    }

    private static float angleDistance(float yaw, float other) {
        float dist = other - yaw;
        if (dist > 0) return dist > 180 ? (dist - 360) : dist;
        else return dist < -180 ? (dist + 360) : dist;
    }

    private static void drawTargetIndicator(MatrixStack matrixStack, float yaw, float angle, int xPos) {
        float nDist = angleDistance(yaw, angle);
        if (Math.abs(nDist) <= 90)
        {
            float nPos = xPos + nDist;
            //fill(matrixStack, (int)(nPos-0.5f), 10, (int)(nPos+0.5f), 18, 0x7FFFFFFF);
            TextureDrawer.drawGuiTexture(matrixStack, (int)nPos-2, 15, 106, 53, 4, 4); // -2 for texture size.
        }
    }

    private static void drawStructureIndicator(MatrixStack matrixStack, float yaw, float angle, int xPos, CompassFeature feature) {
        if(feature.getIconUV() == null)
            return;

        float nDist = angleDistance(yaw, angle);
        if (Math.abs(nDist) <= 90)
        {
            float nPos = xPos + nDist;
            int u = feature.getIconUV().getKey(), v = feature.getIconUV().getValue();
            //fill(matrixStack, (int)(nPos-0.5f), 10, (int)(nPos+0.5f), 18, 0x7FFFFFFF);
            TextureDrawer.drawGuiTexture(matrixStack, (int)nPos-2, 17 - (CompassFeature.ICON_HEIGHT / 2), u, v, CompassFeature.ICON_WIDTH, CompassFeature.ICON_HEIGHT);
        }
    }
}
