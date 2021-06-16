package com.ryankshah.skyrimcraft.client.entity.passive.merchant.model;

import com.ryankshah.skyrimcraft.client.entity.passive.merchant.MerchantEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.IHeadToggle;

public class MerchantModel extends BipedModel<MerchantEntity> implements IHeadToggle
{
    public MerchantModel(float p_i1148_1_) {
        super(p_i1148_1_);
    }

    @Override
    public void hatVisible(boolean p_217146_1_) {
        this.head.visible = p_217146_1_;
        this.hat.visible = p_217146_1_;
    }
}
