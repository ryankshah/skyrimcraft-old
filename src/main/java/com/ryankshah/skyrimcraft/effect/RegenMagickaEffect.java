package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegenMagickaEffect extends MobEffect implements IForgeRegistryEntry<MobEffect>
{
    public RegenMagickaEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xAA3792CB);
    }

    public Component getDisplayName() {
        return new TextComponent("Regen Magicka");
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}