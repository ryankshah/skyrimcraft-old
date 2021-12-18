package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class UndeadFleeEffect extends MobEffect implements IForgeRegistryEntry<MobEffect>
{
    public UndeadFleeEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xAA222222);
    }

    public Component getDisplayName() {
        return new TextComponent("Turn Undead");
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}