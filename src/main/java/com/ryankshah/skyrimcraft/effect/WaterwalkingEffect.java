package com.ryankshah.skyrimcraft.effect;

import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class WaterwalkingEffect extends Effect implements IForgeRegistryEntry<Effect>
{
    private Vector3d vec = new Vector3d(1.02d, 0d, 1.02d);

    public WaterwalkingEffect() {
        super(EffectType.BENEFICIAL, 0xA5F2F3);
    }

    public ITextComponent getDisplayName() {
        return new StringTextComponent("Water Walking");
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        if (livingEntity.level.getBlockState(livingEntity.blockPosition().below()).getMaterial() == Material.WATER) {
            Vector3d motion = livingEntity.getDeltaMovement().multiply(vec);
            livingEntity.setDeltaMovement(motion);
        }

        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}
