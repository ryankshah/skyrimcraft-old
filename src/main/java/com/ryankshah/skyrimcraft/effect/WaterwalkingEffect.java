package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class WaterwalkingEffect extends MobEffect implements IForgeRegistryEntry<MobEffect>
{
    private Vec3 vec = new Vec3(1.02d, 0d, 1.02d);

    public WaterwalkingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xA5F2F3);
    }

    public Component getDisplayName() {
        return new TextComponent("Water Walking");
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        if (livingEntity.level.getBlockState(livingEntity.blockPosition().below()).getMaterial() == Material.WATER) {
            Vec3 motion = livingEntity.getDeltaMovement().multiply(vec);
            livingEntity.setDeltaMovement(motion);
        }

        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}
