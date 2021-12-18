package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class EtherealEffect extends MobEffect implements IForgeRegistryEntry<MobEffect>
{
    public EtherealEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xA5F2F3);
    }

    public Component getDisplayName() {
        return new TextComponent("Ethereal");
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        if(!livingEntity.isInvulnerable())
            livingEntity.setInvulnerable(true);
        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}
