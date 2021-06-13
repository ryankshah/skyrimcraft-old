package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapScreen extends Screen
{
    private Minecraft minecraft;
    private ClientPlayerEntity player;
    private World world;
    private Vector3d camera;
    private ObjectList<WorldRenderer.LocalRenderInformationContainer> renderChunks;
    private ObjectList<WorldRenderer.LocalRenderInformationContainer> mapChunks;
    private ISkyrimPlayerData playerCap;
    private List<CompassFeature> features;

    protected MapScreen() {
        super(new TranslationTextComponent(".mapgui.title"));

        this.minecraft = Minecraft.getInstance();

        // minecraft.options.renderDistance = 64;

        this.player = Minecraft.getInstance().player;
        this.world = player.level;
        this.camera = minecraft.gameRenderer.getMainCamera().getPosition();
        this.playerCap = player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalStateException("Skills Screen capability failed"));

        this.renderChunks = new ObjectArrayList<>(69696);
        this.mapChunks = new ObjectArrayList<>(69696);

        this.mapChunks.addAll(Arrays.stream(
                this.minecraft.levelRenderer.viewArea.chunks
        ).map(
                chunkRender -> minecraft.levelRenderer.new LocalRenderInformationContainer(chunkRender, (Direction)null, 0)
        ).collect(Collectors.toList()));

        //this.features = playerCap.getCompassFeatures().stream().filter(feature -> feature.getChunkPos().equals())
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        if(world != null) {
            fill(matrixStack, 0, 0, this.width, this.height, 0xFF000000);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(this, matrixStack));
        }

        this.renderChunks = minecraft.levelRenderer.renderChunks;

        this.minecraft.levelRenderer.renderChunks.clear();
        this.minecraft.levelRenderer.renderChunks.addAll(mapChunks);

        // Set d1 to 200 or max build height...
        double d0 = player.blockPosition().getX(), d1 = 200d, d2 = player.blockPosition().getZ();

        //Matrix4f matrix4f1 = matrixStack.last().pose();
        this.resetProjectionMatrix();
        matrixStack.translate(width / 2, height / 2 + 45f, -1.4143 * minecraft.options.renderDistance*16);

        matrixStack.pushPose();

        matrixStack.mulPose(new Quaternion(Vector3f.XP.rotationDegrees(60f)));
        matrixStack.mulPose(new Quaternion(Vector3f.YP.rotationDegrees(-22.5f)));

        minecraft.levelRenderer.renderChunkLayer(RenderType.solid(), matrixStack, d0, d1, d2);
        minecraft.getModelManager().getAtlas(AtlasTexture.LOCATION_BLOCKS).setBlurMipmap(false, this.minecraft.options.mipmapLevels > 0); // FORGE: fix flickering leaves when mods mess up the blurMipmap settings
        minecraft.levelRenderer.renderChunkLayer(RenderType.cutoutMipped(), matrixStack, d0, d1, d2);
        minecraft.getModelManager().getAtlas(AtlasTexture.LOCATION_BLOCKS).restoreLastBlurMipmap();
        minecraft.levelRenderer.renderChunkLayer(RenderType.cutout(), matrixStack, d0, d1, d2);
        minecraft.levelRenderer.renderChunkLayer(RenderType.translucent(), matrixStack, d0, d1, d2);
        //RenderHelper.setupLevel(matrixStack.last().pose());

        matrixStack.popPose();

        //RenderSystem.disableDepthTest();

        this.minecraft.levelRenderer.renderChunks.clear();
        this.minecraft.levelRenderer.renderChunks.addAll(this.renderChunks);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void resetProjectionMatrix() {
        RenderSystem.matrixMode(5889); // projection view
        RenderSystem.loadIdentity();
        RenderSystem.ortho(0, width, 0, height, 1f, 1000.0f);
        RenderSystem.matrixMode(5888); // model view
        RenderSystem.loadIdentity();
    }
}