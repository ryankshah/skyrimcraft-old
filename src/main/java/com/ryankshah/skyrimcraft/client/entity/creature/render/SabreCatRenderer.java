package com.ryankshah.skyrimcraft.client.entity.creature.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.creature.SabreCatEntity;
import com.ryankshah.skyrimcraft.client.entity.creature.model.SabreCatModel;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.util.Arrays;
import java.util.List;

public class SabreCatRenderer extends GeoEntityRenderer<SabreCatEntity>
{
    public SabreCatRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx, new SabreCatModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
        this.addLayer(new SabreCatEyesLayer(this));
    }

    class SabreCatEyesLayer extends GeoLayerRenderer<SabreCatEntity>
    {
        private final Int2ObjectMap<ResourceLocation> EYES_MAP = Util.make(new Int2ObjectOpenHashMap<>(), (p_215348_0_) -> {
            p_215348_0_.put(1, new ResourceLocation(Skyrimcraft.MODID, "textures/entity/sabre_cat_e.png"));
            p_215348_0_.put(2, new ResourceLocation(Skyrimcraft.MODID, "textures/entity/snowy_sabre_cat_e.png"));
        });
        private final List<ResourceKey<Biome>> SNOWY_BIOMES = Arrays.asList(
                Biomes.SNOWY_SLOPES, Biomes.SNOWY_TAIGA, Biomes.SNOWY_BEACH,
                Biomes.SNOWY_PLAINS
        );
        private IGeoRenderer<SabreCatEntity> entityIGeoRenderer;

        public SabreCatEyesLayer(IGeoRenderer<SabreCatEntity> entityRendererIn) {
            super(entityRendererIn);
            this.entityIGeoRenderer = entityRendererIn;
        }

        @Override
        public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, SabreCatEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            ResourceLocation eyes = EYES_MAP.getOrDefault(SNOWY_BIOMES.contains(entitylivingbaseIn.getBiomeType()) ? 2 : 1, new ResourceLocation(Skyrimcraft.MODID, "textures/entity/sabre_cat_e.png"));

            if(entitylivingbaseIn.level.getDayTime() > 12542) {
                VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.eyes(eyes));
                this.entityIGeoRenderer.render(getEntityModel().getModel(getGeoModelProvider().getModelLocation(entitylivingbaseIn)), entitylivingbaseIn, partialTicks, RenderType.eyes(eyes), matrixStackIn, bufferIn, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }
}