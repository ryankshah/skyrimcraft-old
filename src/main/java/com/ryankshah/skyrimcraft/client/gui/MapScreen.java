package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.DyeColor;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapScreen extends Screen
{
    public static final ResourceLocation BEAM_LOCATION = new ResourceLocation("textures/entity/beacon_beam.png");

    private Minecraft minecraft;
    private ClientPlayerEntity player;
    private World world;
    private Vector3d camera;
    private ObjectList<WorldRenderer.LocalRenderInformationContainer> renderChunks;
    private ChunkRenderDispatcher.ChunkRender[] renderChunksChunkers;
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
        renderChunksChunkers = this.minecraft.levelRenderer.viewArea.chunks;
        this.mapChunks = new ObjectArrayList<>(69696);

        this.mapChunks.addAll(Arrays.stream(
                this.minecraft.levelRenderer.viewArea.chunks
        ).map(
                chunkRender -> minecraft.levelRenderer.new LocalRenderInformationContainer(chunkRender, (Direction)null, 0)
        ).collect(Collectors.toList()));

        this.features = playerCap.getCompassFeatures();

        // Filter for features found within the map's chunks, and only for features that appear on the surface
        this.features = features.stream().filter(feature -> Arrays.stream(renderChunksChunkers).anyMatch(chunkRender -> chunkRender.getOrigin().getX() == feature.getBlockPos().getX() && chunkRender.getOrigin().getZ() == feature.getBlockPos().getZ()) && !feature.equals(Structure.MINESHAFT.getRegistryName())).collect(Collectors.toList());
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

        // TODO: Fix this...
        long i = world.getGameTime();
        for(CompassFeature feature : features) {
            matrixStack.pushPose();
            matrixStack.translate(feature.getBlockPos().getX() - d0, feature.getBlockPos().getY() - d1, feature.getBlockPos().getZ() - d2);
            // TODO: Render the map marker which we can click on for fast travel to and show info about the marked CompassFeature
            renderBeaconBeam(matrixStack, minecraft.renderBuffers().bufferSource(), partialTicks, i, 40, 1024, DyeColor.WHITE.getTextureDiffuseColors());
            matrixStack.popPose();
        }

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

    private static void renderBeaconBeam(MatrixStack p_228841_0_, IRenderTypeBuffer p_228841_1_, float p_228841_2_, long p_228841_3_, int p_228841_5_, int p_228841_6_, float[] p_228841_7_) {
        renderBeaconBeam(p_228841_0_, p_228841_1_, BEAM_LOCATION, p_228841_2_, 1.0F, p_228841_3_, p_228841_5_, p_228841_6_, p_228841_7_, 1f, 0.25f);
    }

    public static void renderBeaconBeam(MatrixStack p_228842_0_, IRenderTypeBuffer p_228842_1_, ResourceLocation p_228842_2_, float p_228842_3_, float p_228842_4_, long p_228842_5_, int p_228842_7_, int p_228842_8_, float[] p_228842_9_, float p_228842_10_, float p_228842_11_) {
        int i = p_228842_7_ + p_228842_8_;
        p_228842_0_.pushPose();
        p_228842_0_.translate(0.5D, 0.0D, 0.5D);
        float f = (float)Math.floorMod(p_228842_5_, 40L) + p_228842_3_;
        float f1 = p_228842_8_ < 0 ? f : -f;
        float f2 = MathHelper.frac(f1 * 0.2F - (float)MathHelper.floor(f1 * 0.1F));
        float f3 = p_228842_9_[0];
        float f4 = p_228842_9_[1];
        float f5 = p_228842_9_[2];
        p_228842_0_.pushPose();
        p_228842_0_.mulPose(Vector3f.YP.rotationDegrees(f * 2.25F - 45.0F));
        float f6 = 0.0F;
        float f8 = 0.0F;
        float f9 = -p_228842_10_;
        float f10 = 0.0F;
        float f11 = 0.0F;
        float f12 = -p_228842_10_;
        float f13 = 0.0F;
        float f14 = 1.0F;
        float f15 = -1.0F + f2;
        float f16 = (float)p_228842_8_ * p_228842_4_ * (0.5F / p_228842_10_) + f15;
        renderPart(p_228842_0_, p_228842_1_.getBuffer(RenderType.beaconBeam(p_228842_2_, false)), f3, f4, f5, 1.0F, p_228842_7_, i, 0.0F, p_228842_10_, p_228842_10_, 0.0F, f9, 0.0F, 0.0F, f12, 0.0F, 1.0F, f16, f15);
        p_228842_0_.popPose();
        f6 = -p_228842_11_;
        float f7 = -p_228842_11_;
        f8 = -p_228842_11_;
        f9 = -p_228842_11_;
        f13 = 0.0F;
        f14 = 1.0F;
        f15 = -1.0F + f2;
        f16 = (float)p_228842_8_ * p_228842_4_ + f15;
        renderPart(p_228842_0_, p_228842_1_.getBuffer(RenderType.beaconBeam(p_228842_2_, true)), f3, f4, f5, 0.125F, p_228842_7_, i, f6, f7, p_228842_11_, f8, f9, p_228842_11_, p_228842_11_, p_228842_11_, 0.0F, 1.0F, f16, f15);
        p_228842_0_.popPose();
    }

    private static void renderPart(MatrixStack p_228840_0_, IVertexBuilder p_228840_1_, float p_228840_2_, float p_228840_3_, float p_228840_4_, float p_228840_5_, int p_228840_6_, int p_228840_7_, float p_228840_8_, float p_228840_9_, float p_228840_10_, float p_228840_11_, float p_228840_12_, float p_228840_13_, float p_228840_14_, float p_228840_15_, float p_228840_16_, float p_228840_17_, float p_228840_18_, float p_228840_19_) {
        MatrixStack.Entry matrixstack$entry = p_228840_0_.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        renderQuad(matrix4f, matrix3f, p_228840_1_, p_228840_2_, p_228840_3_, p_228840_4_, p_228840_5_, p_228840_6_, p_228840_7_, p_228840_8_, p_228840_9_, p_228840_10_, p_228840_11_, p_228840_16_, p_228840_17_, p_228840_18_, p_228840_19_);
        renderQuad(matrix4f, matrix3f, p_228840_1_, p_228840_2_, p_228840_3_, p_228840_4_, p_228840_5_, p_228840_6_, p_228840_7_, p_228840_14_, p_228840_15_, p_228840_12_, p_228840_13_, p_228840_16_, p_228840_17_, p_228840_18_, p_228840_19_);
        renderQuad(matrix4f, matrix3f, p_228840_1_, p_228840_2_, p_228840_3_, p_228840_4_, p_228840_5_, p_228840_6_, p_228840_7_, p_228840_10_, p_228840_11_, p_228840_14_, p_228840_15_, p_228840_16_, p_228840_17_, p_228840_18_, p_228840_19_);
        renderQuad(matrix4f, matrix3f, p_228840_1_, p_228840_2_, p_228840_3_, p_228840_4_, p_228840_5_, p_228840_6_, p_228840_7_, p_228840_12_, p_228840_13_, p_228840_8_, p_228840_9_, p_228840_16_, p_228840_17_, p_228840_18_, p_228840_19_);
    }

    private static void renderQuad(Matrix4f p_228839_0_, Matrix3f p_228839_1_, IVertexBuilder p_228839_2_, float p_228839_3_, float p_228839_4_, float p_228839_5_, float p_228839_6_, int p_228839_7_, int p_228839_8_, float p_228839_9_, float p_228839_10_, float p_228839_11_, float p_228839_12_, float p_228839_13_, float p_228839_14_, float p_228839_15_, float p_228839_16_) {
        addVertex(p_228839_0_, p_228839_1_, p_228839_2_, p_228839_3_, p_228839_4_, p_228839_5_, p_228839_6_, p_228839_8_, p_228839_9_, p_228839_10_, p_228839_14_, p_228839_15_);
        addVertex(p_228839_0_, p_228839_1_, p_228839_2_, p_228839_3_, p_228839_4_, p_228839_5_, p_228839_6_, p_228839_7_, p_228839_9_, p_228839_10_, p_228839_14_, p_228839_16_);
        addVertex(p_228839_0_, p_228839_1_, p_228839_2_, p_228839_3_, p_228839_4_, p_228839_5_, p_228839_6_, p_228839_7_, p_228839_11_, p_228839_12_, p_228839_13_, p_228839_16_);
        addVertex(p_228839_0_, p_228839_1_, p_228839_2_, p_228839_3_, p_228839_4_, p_228839_5_, p_228839_6_, p_228839_8_, p_228839_11_, p_228839_12_, p_228839_13_, p_228839_15_);
    }

    private static void addVertex(Matrix4f p_228838_0_, Matrix3f p_228838_1_, IVertexBuilder p_228838_2_, float p_228838_3_, float p_228838_4_, float p_228838_5_, float p_228838_6_, int p_228838_7_, float p_228838_8_, float p_228838_9_, float p_228838_10_, float p_228838_11_) {
        p_228838_2_.vertex(p_228838_0_, p_228838_8_, (float)p_228838_7_, p_228838_9_).color(p_228838_3_, p_228838_4_, p_228838_5_, p_228838_6_).uv(p_228838_10_, p_228838_11_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_228838_1_, 0.0F, 1.0F, 0.0F).endVertex();
    }
}