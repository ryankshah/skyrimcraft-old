package com.ryankshah.skyrimcraft.character.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;

public class SpectralLayerRenderer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>
{
    private int light;

    public SpectralLayerRenderer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> p_i50926_1_, int light) {
        super(p_i50926_1_);
        this.light = light;
    }

    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, AbstractClientPlayerEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if(p_225628_4_.hasEffect(ModEffects.SPECTRAL.get())) {
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.entityTranslucentCull(p_225628_4_.getSkinTextureLocation()));
            getParentModel().renderToBuffer(p_225628_1_,
                    ivertexbuilder,
                    light,
                    LivingRenderer.getOverlayCoords(p_225628_4_, 0.0F),
                    197/255f, 205/255f, 216/255f, 0.4F);
            RenderSystem.disableBlend();
        }
    }
}
