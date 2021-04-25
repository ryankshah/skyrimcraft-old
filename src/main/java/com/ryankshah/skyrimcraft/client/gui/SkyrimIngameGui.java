package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.capability.IMagicka;
import com.ryankshah.skyrimcraft.capability.IMagickaProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;

public class SkyrimIngameGui extends AbstractGui
{
    protected static final ResourceLocation OVERLAY_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/overlay_icons_current.png");

    private Minecraft mc;
    private MatrixStack matrixStack;
    private FontRenderer fontRenderer;
    private int width, height;

    @CapabilityInject(IMagicka.class)
    private Capability<IMagicka> MAGICKA_CAPABILITY = null;

    public SkyrimIngameGui(MatrixStack ms, int width, int height) {
        this.width = width;
        this.height = height;

        this.mc = Minecraft.getInstance();
        this.matrixStack = ms;
        this.fontRenderer = mc.fontRenderer;

        render();
    }

    protected void render() {
        this.mc.getTextureManager().bindTexture(OVERLAY_ICONS);

        renderHealth();
        renderStamina();
        renderMagicka();
        renderCompass();

        this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
    }

    private void renderCompass() {
        blit(this.matrixStack, this.width / 2 - 110, 10, 0, 37, 221, 14);

        int rot;
        boolean f0 = mc.player.rotationYaw < 0;

        if(f0) rot = -MathHelper.floor(this.mc.player.rotationYaw % 360);
        else rot = MathHelper.floor(this.mc.player.rotationYaw % 360);

        boolean f1 = rot > 0 && rot < 180;
        boolean f2 = rot <= 270 && rot >= 90;
        boolean f3 = rot <= 180 && rot >= 0;
        boolean f4 = rot <= 1 && rot >= 0;

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

        blit(this.matrixStack, this.width / 2 - 50, this.height - 40, 0, 51, 102, 10);
        blit(this.matrixStack, this.width / 2 - (int)(39 * healthPercentage), this.height - 38, 11, 72, (int)(80 * healthPercentage), 6);
    }

    private void renderStamina() {
        float staminaPercentage = mc.player.getFoodStats().getFoodLevel() / 20.0f; // 20.0f is the max food value (this is apparently hardcoded...)
        this.blit(this.matrixStack, this.width - 120, this.height - 40, 0, 51, 102, 10);
        this.blit(this.matrixStack, this.width - 109, this.height - 38, 11, 80, (int)(80 * staminaPercentage), 6);
    }

    private void renderMagicka() {
        LazyOptional<IMagicka> magickaLazyOptional = mc.player.getCapability(IMagickaProvider.MAGICKA_CAPABILITY);
        magickaLazyOptional.ifPresent((magicka) -> {
            float magickaPercentage = magicka.get() / magicka.getMaxMagicka();
            this.blit(this.matrixStack, 20, this.height - 40, 0, 51, 102, 10);
            this.blit(this.matrixStack, 31, this.height - 38, 11, 64, (int)(80 * magickaPercentage), 6);
        });
    }
}
