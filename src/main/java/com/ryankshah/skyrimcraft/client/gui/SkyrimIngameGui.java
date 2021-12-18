package com.ryankshah.skyrimcraft.client.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.client.entity.ModEntityType;
import com.ryankshah.skyrimcraft.event.ForgeClientEvents;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import com.ryankshah.skyrimcraft.util.LevelUpdate;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetKeyName;

// Inspiration and credits for utility methods used in the compass rendering go to Gigaherz <3

/**
 * TODO: work on creating a fade-in-out for the XP bar in skyrim style when the player gains XP and also levels.
 */
public class SkyrimIngameGui extends GuiComponent //implements IIngameOverlay
{
    protected static final ResourceLocation OVERLAY_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/overlay_icons.png");
    public static final int PLAYER_BAR_MAX_WIDTH = 78;

    private static Minecraft mc = Minecraft.getInstance();
    private static Font fontRenderer = mc.font;

    public static List<LevelUpdate> LEVEL_UPDATES = new ArrayList<>();

    public static void render(PoseStack matrixStack, int width, int height, float partialTicks) {
        RenderSystem.setShaderTexture(0, OVERLAY_ICONS);

        renderCrosshair(matrixStack, width, height);
        renderHealth(matrixStack, width, height);
        renderStamina(matrixStack, width, height);
        renderMagicka(matrixStack, width, height);
        renderCompass(matrixStack, width, height);
        renderTargetHealth(matrixStack, width, height);

        // Check there are level updates for character skills, and if so render the update
        if(!LEVEL_UPDATES.isEmpty()) {
            LevelUpdate levelUpdate = LEVEL_UPDATES.get(0);

            if(levelUpdate.getLevelUpRenderTime() <= 0)
                LEVEL_UPDATES.remove(levelUpdate);

            if(levelUpdate.getUpdateName().equals("characterLevel"))
                renderCharacterLevelUpdate(matrixStack, width, height, partialTicks, levelUpdate.getLevel(), levelUpdate.getLevelUpRenderTime());
            else
                renderLevelUpdate(matrixStack, width, height, partialTicks, levelUpdate.getUpdateName(), levelUpdate.getLevel(), levelUpdate.getLevelUpRenderTime());

            levelUpdate.setLevelUpRenderTime(levelUpdate.getLevelUpRenderTime() - 1);
        }

        // Check if player is holding bow and if has arrows in inventory, display arrow counter
        if(mc.player.getMainHandItem().getItem() instanceof ProjectileWeaponItem
                && !mc.player.isCreative() && !mc.player.isSpectator()
                && !mc.player.isUnderWater() && !(mc.player.getAirSupply() < 300)) {
            int top = height - 48;
            if(mc.player.getArmorValue() > 0) top = height - 60;
            ItemStack itemstack = mc.player.getProjectile(mc.player.getMainHandItem());
            if(itemstack != ItemStack.EMPTY) {
                MutableComponent nameAndCount = itemstack.getHoverName().copy().append("(" + itemstack.getCount() + ")");
                drawString(matrixStack, fontRenderer, nameAndCount, width - 18 - fontRenderer.width(nameAndCount), top, 0x00FFFFFF);
            }
        }
        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
//        if(mc.gameMode.hasExperience())
//            renderXP(matrixStack, width, height);
        renderArmor(matrixStack, width, height);

        if(!mc.player.isCreative() && !mc.player.isSpectator())
            renderAir(matrixStack, width, height);

        renderLookVectorRayTrace(matrixStack, width, height);
    }

    private static void renderCompass(PoseStack matrixStack, int width, int height) {
        TextureDrawer.drawGuiTexture(matrixStack, width / 2 - 110, 10, 0, 37, 221, 14);

        float yaw = Mth.lerp(mc.getFrameTime(), mc.player.yHeadRotO, mc.player.yHeadRot) % 360;
        if (yaw < 0) yaw += 360;

        drawCardinalDirection(matrixStack, yaw, 0, width / 2, "S");
        drawCardinalDirection(matrixStack, yaw, 90, width / 2, "W");
        drawCardinalDirection(matrixStack, yaw, 180, width / 2, "N");
        drawCardinalDirection(matrixStack, yaw, 270, width / 2, "E");

        double playerPosX = Mth.lerp(mc.getFrameTime(), mc.player.xo, mc.player.getX());
        double playerPosY = Mth.lerp(mc.getFrameTime(), mc.player.yo, mc.player.getY());
        double playerPosZ = Mth.lerp(mc.getFrameTime(), mc.player.zo, mc.player.getZ());
        final float finalYaw = yaw;

        RenderSystem.setShaderTexture(0, OVERLAY_ICONS); // rebind after drawing strings (mc binds font texture)

        mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
            if(cap.getCompassFeatures().size() > 0) {
                List<CompassFeature> sortedFeatures = Lists.newArrayList(cap.getCompassFeatures());
                sortedFeatures.sort((a,b) -> {
                    Vec3 positionA = new Vec3(a.getBlockPos().getX(), 0, a.getBlockPos().getZ()); //mc.player.getY()
                    Vec3 positionB = new Vec3(b.getBlockPos().getX(), 0, b.getBlockPos().getZ());
                    float angleA = Math.abs(angleDistance(finalYaw, angleFromTarget(positionA, positionB).x));
                    float angleB = Math.abs(angleDistance(finalYaw, angleFromTarget(positionB, positionA).x));
                    return (int)Math.signum(angleB-angleA);
                });

                for (CompassFeature feature : sortedFeatures) {
                    if(mc.player.position().distanceToSqr(feature.getBlockPos().getX(), mc.player.position().y, feature.getBlockPos().getZ()) <= 512 * 16) { // 256 blocks?
                        Vec3 position = new Vec3(feature.getBlockPos().getX(), 0, feature.getBlockPos().getZ());
                        Vec2 angleYd = angleFromTarget(position, new Vec3(playerPosX, playerPosY, playerPosZ));
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

                    Vec3 a = new Vec3(playerPosX, playerPosY, playerPosZ);
                    Vec3 b = targetingEntity.position();

                    Vec2 angleYd = angleFromTarget(b, a);
                    drawTargetIndicator(matrixStack, finalYaw, angleYd.x, width / 2); //ydelta = angleYd.y
                }
            }
            if (cap.getCurrentTarget() != null && cap.getCurrentTarget().isAlive()) {
                // Check player is out of target range
                if(!mc.player.closerThan(cap.getCurrentTarget(), 16.0D))
                    return;

                Vec3 a = new Vec3(playerPosX, playerPosY, playerPosZ);
                Vec3 b = cap.getCurrentTarget().position();

                Vec2 angleYd = angleFromTarget(b, a);
                drawTargetIndicator(matrixStack, finalYaw, angleYd.x, width / 2); //ydelta = angleYd.y
            }
        });
    }

    private static void renderHealth(PoseStack matrixStack, int width, int height) {
        float healthPercentage = mc.player.getHealth() / mc.player.getMaxHealth();
        float healthBarWidth = PLAYER_BAR_MAX_WIDTH * healthPercentage;
        float healthBarStartX;

        if(mc.player.level.getDifficulty() == Difficulty.HARD) {
            healthBarStartX = (width / 2 - 40) + (PLAYER_BAR_MAX_WIDTH - healthBarWidth) / 2.0f;
            TextureDrawer.drawGuiTexture(matrixStack, width / 2 - 51, height - 41, 96, 71, 100, 16);
        } else {
            healthBarStartX = (width / 2 - 39) + (PLAYER_BAR_MAX_WIDTH - healthBarWidth) / 2.0f;
            TextureDrawer.drawGuiTexture(matrixStack, width / 2 - 51, height - 35, 0, 51, 102, 10);
        }
        TextureDrawer.drawGuiTexture(matrixStack, (int)healthBarStartX, height - 33, 12 + ((PLAYER_BAR_MAX_WIDTH - healthBarWidth) / 2.0f), 72, healthBarWidth, 6);
    }

    private static void renderStamina(PoseStack matrixStack, int width, int height) {
        float staminaPercentage = mc.player.getFoodData().getFoodLevel() / 20.0f; // 20.0f is the max food value (this is apparently hardcoded...)
        float staminaBarWidth = PLAYER_BAR_MAX_WIDTH * staminaPercentage;
        float staminaBarStartX = (float)(width - 108) + (PLAYER_BAR_MAX_WIDTH - staminaBarWidth);

        TextureDrawer.drawGuiTexture(matrixStack, width - 120, height - 35, 0, 51, 102, 10);
        TextureDrawer.drawGuiTexture(matrixStack, (int)staminaBarStartX, height - 33, 12 + ((PLAYER_BAR_MAX_WIDTH - staminaBarWidth) / 2.0f), 80, staminaBarWidth, 6);
    }

    private static void renderMagicka(PoseStack matrixStack, int width, int height) {
        mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
            float magickaPercentage = cap.getMagicka() / cap.getMaxMagicka();
            float magickaBarWidth = PLAYER_BAR_MAX_WIDTH * magickaPercentage;

//            System.out.println(cap.getSkills());
//            System.out.println(cap.getCharacterXp());

            TextureDrawer.drawGuiTexture(matrixStack, 20, height - 35, 0, 51, 102, 10);
            TextureDrawer.drawGuiTexture(matrixStack, 32, height - 33, 12 + ((PLAYER_BAR_MAX_WIDTH - magickaBarWidth) / 2.0f), 64, (int)(78 * magickaPercentage), 6);
        });
    }

    private static void renderTargetHealth(PoseStack matrixStack, int width, int height) {
        RenderSystem.setShaderTexture(0, OVERLAY_ICONS);

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
                TextureDrawer.drawGuiTexture(matrixStack, (int)healthBarStartX, 30, 10 + ((142 - healthBarWidth) / 2.0f), 101, (int)healthBarWidth, 3);

                int entityNameWidth = fontRenderer.width(entityName);
                // left banner
                TextureDrawer.drawGuiTexture(matrixStack, (width / 2) - 2 - (41 + entityNameWidth / 2), 38, 25, 107, 41, 12); // width / 2 - 69
                // right banner
                TextureDrawer.drawGuiTexture(matrixStack, (width / 2) + 2 + entityNameWidth / 2, 38, 84, 107, 41, 12); // width / 2 + 28
                drawCenteredString(matrixStack, fontRenderer, entityName , width / 2, 40, 0x00C0C0C0); //0x0083868A
            }
        });

        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
    }

    private static void renderArmor(PoseStack matrixStack, int width, int height) {
        int left = width - 18 - 8;
        int top = height - 48;

        int level = mc.player.getArmorValue();
        for (int i = 1; level > 0 && i < 20; i += 2) {
            if (i < level)
                TextureDrawer.drawGuiTexture(matrixStack, left, top, 34, 9, 9, 9);
            else if (i == level)
                TextureDrawer.drawGuiTexture(matrixStack, left, top, 25, 9, 9, 9);
            else if (i > level)
                TextureDrawer.drawGuiTexture(matrixStack, left, top, 16, 9, 9, 9);

            left -= 8;
        }
    }

    private static void renderAir(PoseStack matrixStack, int width, int height) {
        int left = width - 18;
        int top;
        if(mc.player.getArmorValue() > 0)
            top = height - 60;
        else
            top = height - 48;

        int air = mc.player.getAirSupply();
        if (mc.player.isEyeInFluid(FluidTags.WATER) || air < 300)
        {
            int full = Mth.ceil((double)(air - 2) * 10.0D / 300.0D);
            int partial = Mth.ceil((double)air * 10.0D / 300.0D) - full;

            for (int i = 0; i < full + partial; ++i)
            {
                TextureDrawer.drawGuiTexture(matrixStack, left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
            }
        }
    }

    private static void renderXP(PoseStack matrixStack, int width, int height) {
        int i = mc.player.getXpNeededForNextLevel();
        if (i > 0) {
            int j = 182;
            int k = (int)(mc.player.experienceProgress * 183.0F);
            int l = height - 32 + 3;
            TextureDrawer.drawGuiTexture(matrixStack, width / 2 - 91, l, 0, 64, 182, 5);
            if (k > 0) {
                TextureDrawer.drawGuiTexture(matrixStack, width / 2 - 19, l, 0, 69, k, 5);
            }
        }

        if (mc.player.experienceLevel > 0) {
            String s = "" + mc.player.experienceLevel;
            int i1 = (width - fontRenderer.width(s)) / 2;
            int j1 = height - 31 - 4;
            fontRenderer.draw(matrixStack, s, (float)(i1 + 1), (float)j1, 0);
            fontRenderer.draw(matrixStack, s, (float)(i1 - 1), (float)j1, 0);
            fontRenderer.draw(matrixStack, s, (float)i1, (float)(j1 + 1), 0);
            fontRenderer.draw(matrixStack, s, (float)i1, (float)(j1 - 1), 0);
            fontRenderer.draw(matrixStack, s, (float)i1, (float)j1, 8453920);
        }
    }

    private static void renderCharacterLevelUpdate(PoseStack matrixStack, int width, int height, float elapsed, int level, int levelUpRenderTime) {
        //matrixStack.pushPose();
        String characterLevel = ""+level;
        String levelProgressString = "Progress";

        float hue = (float)levelUpRenderTime - elapsed;
        int opacity = (int)(hue * 255.0F / 20.0F);
        if (opacity > 255) opacity = 255;

        matrixStack.pushPose();
        //RenderSystem.translatef((float)(width / 2), (float)(height - 68), 0.0F);
//        RenderSystem.enableAlphaTest();
//        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        //RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
        RenderSystem.clearColor(1F, 1F, 1f, opacity / 255f);

        RenderSystem.setShaderTexture(0, OVERLAY_ICONS);
        TextureDrawer.drawGuiTexture(matrixStack, width / 2 - 51, height / 2 - 30, 0, 51, 102, 10);
        TextureDrawer.drawGuiTexture(matrixStack, width / 2 - 39, height / 2 - 28, 96, 64, 78, 6);

        if (opacity > 8) {
            drawCenteredString(matrixStack, fontRenderer, "Level Up".toUpperCase(), width / 2, height / 2 - 45, 0x00FFFFFF | (opacity << 24));
            drawString(matrixStack, fontRenderer, levelProgressString, width / 2 - 60 - fontRenderer.width(levelProgressString), height / 2 - 30, 0x00FFFFFF | (opacity << 24));
            drawString(matrixStack, fontRenderer, characterLevel, width / 2 + 60 + 8 - fontRenderer.width(characterLevel), height / 2 - 30, 0x00FFFFFF | (opacity << 24));
        }

        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        //RenderSystem.disableAlphaTest();
        matrixStack.popPose();
    }

    private static void renderLevelUpdate(PoseStack matrixStack, int width, int height, float elapsed, String updateName, int level, int levelUpRenderTime) {
        //matrixStack.pushPose();
        ISkyrimPlayerData cap = mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalStateException("skyrim ingame gui renderLevelUpdate"));
        String nextCharacterLevel = ""+(cap.getCharacterLevel()+1);
        float characterProgress = cap.getCharacterXp() / (float)cap.getXpNeededForNextCharacterLevel(cap.getCharacterLevel()+1);
        float characterProgressBarWidth = PLAYER_BAR_MAX_WIDTH * characterProgress;
        String levelProgressString = "Progress"; //"Level Progress";

        float hue = (float)levelUpRenderTime - elapsed;
        int opacity = (int)(hue * 255.0F / 20.0F);
        if (opacity > 255) opacity = 255;

        matrixStack.pushPose();
        //RenderSystem.translatef((float)(width / 2), (float)(height - 68), 0.0F);
        //RenderSystem.enableAlphaTest();
        //RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        //RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
        RenderSystem.clearColor(1F, 1F, 1f, opacity / 255f);

        RenderSystem.setShaderTexture(0, OVERLAY_ICONS);
        TextureDrawer.drawGuiTexture(matrixStack, width / 2 - 51, height / 2 - 30, 0, 51, 102, 10);
        TextureDrawer.drawGuiTexture(matrixStack, width / 2 - 39, height / 2 - 28, 96 + ((PLAYER_BAR_MAX_WIDTH - characterProgressBarWidth) / 2.0f), 64, (int)(78 * characterProgress), 6);

        if (opacity > 8) {
            drawCenteredString(matrixStack, fontRenderer, (updateName + " Increased To " + level).toUpperCase(), width / 2, height / 2 - 45, 0x00FFFFFF | (opacity << 24));
            drawString(matrixStack, fontRenderer, levelProgressString, width / 2 - 60 - fontRenderer.width(levelProgressString), height / 2 - 30, 0x00FFFFFF | (opacity << 24));
            drawString(matrixStack, fontRenderer, nextCharacterLevel, width / 2 + 60 + 8 - fontRenderer.width(nextCharacterLevel), height / 2 - 30, 0x00FFFFFF | (opacity << 24));
        }

        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        //RenderSystem.disableAlphaTest();
        matrixStack.popPose();
    }

    private static void renderCrosshair(PoseStack matrixStack, int width, int height) {
        int texX = 166;
        int texY = 88;
        if(!mc.player.isSpectator()) { // mc.player.getMainHandItem().getItem() instanceof ShootableItem && // --> Player does not need bow for this iirc
            if (mc.player.isCrouching()) {
                texX += 15;

                if(!ClientUtil.canPlayerBeSeen()) {
                    texX += 15;
                }
            }
        }
        TextureDrawer.drawGuiTexture(matrixStack, (width - 16) / 2, (height - 16) / 2, texX, texY, 15, 15);

        if (mc.options.attackIndicator == AttackIndicatorStatus.CROSSHAIR) {
            float f = mc.player.getAttackStrengthScale(0.0F);
            boolean flag = false;
            if (mc.crosshairPickEntity != null && mc.crosshairPickEntity instanceof LivingEntity && f >= 1.0F) {
                flag = mc.player.getCurrentItemAttackStrengthDelay() > 5.0F;
                flag = flag & mc.crosshairPickEntity.isAlive();
            }

            int j = height / 2 - 7 + 16;
            int k = width / 2 - 8;
            mc.textureManager.bindForSetup(GuiComponent.GUI_ICONS_LOCATION);
            if (flag) {
                TextureDrawer.drawGuiTexture(matrixStack, k, j, 68, 94, 16, 16);
            } else if (f < 1.0F) {
                int l = (int)(f * 17.0F);
                TextureDrawer.drawGuiTexture(matrixStack, k, j, 36, 94, 16, 4);
                TextureDrawer.drawGuiTexture(matrixStack, k, j, 52, 94, l, 4);
            }

            RenderSystem.setShaderTexture(0, OVERLAY_ICONS);
        }
    }

    private static void renderLookVectorRayTrace(PoseStack matrixStack, int width, int height) {
        // Check if player is looking at entity
        if(mc.crosshairPickEntity instanceof LivingEntity && mc.player.isCrouching()) {
            LivingEntity entity = (LivingEntity) mc.crosshairPickEntity;
            if(entity.getTags().contains(ModEntityType.PICKPOCKET_TAG)) {
                drawCenteredString(matrixStack, fontRenderer, "(" + glfwGetKeyName(ForgeClientEvents.pickpocketKey.getKey().getValue(), 0).toUpperCase() + ") Pickpocket", width / 2, height / 2 + 8, 0x00FFFFFF);
            }
        }
    }

    private static void drawCardinalDirection(PoseStack matrixStack, float yaw, float angle, int xPos, String text) {
        float nDist = angleDistance(yaw, angle);
        if (Math.abs(nDist) <= 90) {
            float nPos = xPos + nDist;
            //fill(matrixStack, (int)(nPos-0.5f), 10, (int)(nPos+0.5f), 18, 0x7FFFFFFF);
            drawCenteredString(matrixStack, fontRenderer, text, (int)nPos, 13, 0xFFFFFF);
        }
    }

    private static Vec2 angleFromTarget(Vec3 targetPos, Vec3 playerPos) {
        double xd = targetPos.x - playerPos.x;
        double yd = targetPos.y - playerPos.y;
        double zd = targetPos.z - playerPos.z;
        return new Vec2((float) Math.toDegrees(-Math.atan2(xd, zd)), (float)yd);
    }

    private static float angleDistance(float yaw, float other) {
        float dist = other - yaw;
        if (dist > 0) return dist > 180 ? (dist - 360) : dist;
        else return dist < -180 ? (dist + 360) : dist;
    }

    private static void drawTargetIndicator(PoseStack matrixStack, float yaw, float angle, int xPos) {
        float nDist = angleDistance(yaw, angle);
        if (Math.abs(nDist) <= 90) {
            float nPos = xPos + nDist;
            //fill(matrixStack, (int)(nPos-0.5f), 10, (int)(nPos+0.5f), 18, 0x7FFFFFFF);
            TextureDrawer.drawGuiTexture(matrixStack, (int)nPos-2, 15, 106, 53, 4, 4); // -2 for texture size.
        }
    }

    private static void drawStructureIndicator(PoseStack matrixStack, float yaw, float angle, int xPos, CompassFeature feature) {
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
