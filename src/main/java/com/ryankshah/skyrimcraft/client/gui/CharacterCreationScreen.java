package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.character.PacketCreateCharacterOnServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class CharacterCreationScreen extends Screen
{
    //protected static final ResourceLocation OVERLAY_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/character_creation_icons.png");

    public static final RenderSkyboxCube CUBE_MAP = new RenderSkyboxCube(new ResourceLocation(Skyrimcraft.MODID, "textures/gui/panorama/panorama"));
    private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");

    private final RenderSkybox panorama = new RenderSkybox(CUBE_MAP);
    private final boolean fading = true;
    private long fadeInStart;

    private List<Race> races;
    private int currentRace;
    private Race currentRaceObject;

    public CharacterCreationScreen() {
        super(new TranslationTextComponent(Skyrimcraft.MODID + ".charactercreationgui.title"));
        races = Race.getRaces();
        currentRace = 0;
        currentRaceObject = races.get(currentRace);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.fadeInStart == 0L && this.fading) {
            this.fadeInStart = Util.getMillis();
        }

        float f = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
        fill(matrixStack, 0, 0, this.width, this.height, -1);
        this.panorama.render(partialTicks, MathHelper.clamp(f, 0.0F, 1.0F));
        int i = 274;
        int j = this.width / 2 + 40;
        this.minecraft.getTextureManager().bind(PANORAMA_OVERLAY);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.fading ? (float)MathHelper.ceil(MathHelper.clamp(f, 0.0F, 1.0F)) : 1.0F);
        blit(matrixStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        float f1 = this.fading ? MathHelper.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
        int l = MathHelper.ceil(f1 * 255.0F) << 24;

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, l);
        RenderSystem.enableDepthTest();

        // Render stuff...
        renderPlayer(i + 51, j + 75, 120, i + 51, j - 25 - 150, minecraft.player, currentRaceObject);

        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();

        // Render race selection
        renderSelectionGui(matrixStack);

        fillGradient(matrixStack, 0, this.height * 3 / 4 + 20, this.width, this.height, 0xAA000000, 0xAA000000);
        fillGradient(matrixStack, 0, this.height * 3 / 4 + 22, this.width, this.height * 3 / 4 + 23, 0xAAFFFFFF, 0xAAFFFFFF);

        // Draw "buttons" for keys for selecting spells
        drawBorderedGradientRect(matrixStack, 17, this.height - 29, 32, this.height - 14, 0xAA000000, 0xAA000000, 0xAAFFFFFF);
        drawBorderedGradientRect(matrixStack, 37, this.height - 29, 52, this.height - 14, 0xAA000000, 0xAA000000, 0xAAFFFFFF);
        drawBorderedGradientRect(matrixStack, 17, this.height - 29, 32 + font.width("Enter"), this.height - 14, 0xAA000000, 0xAA000000, 0xAAFFFFFF);
        drawString(matrixStack, font, "Enter", 25, this.height - 25, 0x00FFFFFF);
        drawString(matrixStack, font, "Confirm Race", 32 + font.width("Enter") + 6, this.height - 25, 0x00FFFFFF);

        drawString(matrixStack, font, "Name", this.width - 140 + font.width("Name"), this.height - 25, 0x00C0C0C0);
        drawString(matrixStack, font, minecraft.player.getDisplayName(), this.width - 120 + font.width("Name") + 8, this.height - 25, 0x00FFFFFF);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW_KEY_DOWN || keyCode == GLFW_KEY_S) {
            if (this.currentRace < this.races.size() - 1)
                ++this.currentRace;
            else
                this.currentRace = this.races.size() - 1;

            currentRaceObject = races.get(currentRace);
            Networking.sendToServer(new PacketCreateCharacterOnServer(currentRaceObject));
        }

        if (keyCode == GLFW_KEY_UP || keyCode == GLFW_KEY_W) {
            if (this.currentRace > 0)
                --this.currentRace;
            else
                this.currentRace = 0;

            currentRaceObject = races.get(currentRace);
            Networking.sendToServer(new PacketCreateCharacterOnServer(currentRaceObject));
        }

        if (keyCode == GLFW_KEY_ENTER) {
            Networking.sendToServer(new PacketCreateCharacterOnServer(currentRaceObject));
            minecraft.setScreen(null);
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public static void renderPlayer(int p_228187_0_, int p_228187_1_, int p_228187_2_, float p_228187_3_, float p_228187_4_, LivingEntity p_228187_5_, Race currentRaceObject) {
        float f = (float)Math.atan((double)(p_228187_3_ / 40.0F));
        float f1 = (float)Math.atan((double)(p_228187_4_ / 40.0F));
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)p_228187_0_, (float)p_228187_1_, 300F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.translate(0.0D, 0.0D, 1000.0D);
        matrixstack.scale((float)p_228187_2_, (float)p_228187_2_, (float)p_228187_2_);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1); // f1 * 20.0F
        quaternion.mul(quaternion1);
        matrixstack.mulPose(quaternion);
        float f2 = p_228187_5_.yBodyRot;
        float f3 = p_228187_5_.yRot;
        float f4 = p_228187_5_.xRot;
        float f5 = p_228187_5_.yHeadRotO;
        float f6 = p_228187_5_.yHeadRot;
        p_228187_5_.yBodyRot = 180.0F + f * 20.0F;
        p_228187_5_.yRot = 180.0F + f * 20.0F;
        p_228187_5_.xRot = -f1 * 20.0F;
        p_228187_5_.yHeadRot = 225f + f;
        p_228187_5_.yHeadRotO = 225f + f;
        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conj();
        entityrenderermanager.overrideCameraOrientation(quaternion1);
        entityrenderermanager.setRenderShadow(false);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderermanager.render(p_228187_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);
        });
        irendertypebuffer$impl.endBatch();
        entityrenderermanager.setRenderShadow(true);
        p_228187_5_.yBodyRot = f2;
        p_228187_5_.yRot = f3;
        p_228187_5_.xRot = f4;
        p_228187_5_.yHeadRotO = f5;
        p_228187_5_.yHeadRot = f6;
        RenderSystem.popMatrix();
    }

    public void renderSelectionGui(MatrixStack matrixStack) {
        fillGradient(matrixStack, this.width / 4 - 40, this.height / 2 - 60, this.width / 4 + 60, this.height / 2 + 60, 0xAA000000, 0xAA000000);

        int MIN_Y = this.height / 2 - 50;
        int MAX_Y = this.height / 2 + 50;

        for(int i = 0; i < races.size(); i++) {
            int y = this.height / 2 + 14 * i - this.currentRace * 7 - 20;
            if(y <= MIN_Y || y >= MAX_Y)
                continue;

            String raceName = races.get(i).getName();
            if (raceName.length() >= 14) {
                raceName = raceName.substring(0, 8) + "..";
            }
            drawString(matrixStack, font, raceName, this.width / 4 - 20, y, i == this.currentRace ? 0x00FFFFFF : 0x00C0C0C0);
        }
    }

    private void drawBorderedGradientRect(MatrixStack matrixStack, int startX, int startY, int endX, int endY, int colorStart, int colorEnd, int borderColor) {
        matrixStack.pushPose();
        // Draw background
        fillGradient(matrixStack, startX, startY, endX, endY, colorStart, colorEnd);
        // Draw borders
        fill(matrixStack, startX, startY, endX, startY+1, borderColor); // top
        fill(matrixStack, startX, endY-1, endX, endY, borderColor); // bottom
        fill(matrixStack, startX, startY+1, startX+1, endY-1, borderColor); // left
        fill(matrixStack, endX-1, startY+1, endX, endY-1, borderColor); // right
        matrixStack.popPose();
    }
}