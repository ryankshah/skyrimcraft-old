package com.ryankshah.skyrimcraft.client.entity.passive.merchant.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.MerchantEntity;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.model.MerchantModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

public class MerchantRenderer extends MobRenderer<MerchantEntity, MerchantModel>
{
    private static final ResourceLocation MERCHANT_BASE_SKIN = new ResourceLocation(Skyrimcraft.MODID, "textures/entity/merchant/merchant.png");

    public MerchantRenderer(EntityRendererManager manager) {
        this(manager, (IReloadableResourceManager) Minecraft.getInstance().getResourceManager());
    }

    public MerchantRenderer(EntityRendererManager p_i50954_1_, IReloadableResourceManager p_i50954_2_) {
        super(p_i50954_1_, new MerchantModel(0.0F), 0.5F);
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new MerchantLevelPendantLayer<>(this, p_i50954_2_, "merchant"));
    }

    public ResourceLocation getTextureLocation(MerchantEntity p_110775_1_) {
        return MERCHANT_BASE_SKIN;
    }

    protected void scale(MerchantEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
        float f = 0.9375F;
        if (p_225620_1_.isBaby()) {
            f = (float)((double)f * 0.5D);
            this.shadowRadius = 0.25F;
        } else {
            this.shadowRadius = 0.5F;
        }

        p_225620_2_.scale(f, f, f);
    }
}