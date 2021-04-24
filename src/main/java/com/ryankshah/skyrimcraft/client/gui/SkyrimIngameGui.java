package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.capability.IMagicka;
import com.ryankshah.skyrimcraft.capability.IMagickaProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;

public class SkyrimIngameGui extends AbstractGui
{
    protected static final ResourceLocation OVERLAY_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/overlay_icons.png");

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

        render();
    }

    protected void render() {
        this.mc.getTextureManager().bindTexture(OVERLAY_ICONS);
        blit(this.matrixStack, this.width / 2 - 110, 10, 0, 37, 221, 14);

        renderHealth();
        renderStamina();
        renderMagicka();

        //drawCenteredString(matrixStack, fontRenderer, "Hello World!", this.width / 2, (this.height / 2) - 4, 0xFFAA00);
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
            float magickaPercentage = magicka.get() / 20.0f;
            System.out.println(magickaPercentage);
            this.blit(this.matrixStack, 20, this.height - 40, 0, 51, 102, 10);
            this.blit(this.matrixStack, 31, this.height - 38, 11, 64, (int)(80 * magickaPercentage), 6);
        });
    }
}
