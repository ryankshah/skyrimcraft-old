package com.ryankshah.skyrimcraft.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class UndeadFleeEffect extends Effect implements IForgeRegistryEntry<Effect>
{
    private static final double FLEE_RADIUS = 30D;

    public UndeadFleeEffect() {
        super(EffectType.BENEFICIAL, 0xAA222222);
    }

    public ITextComponent getDisplayName() {
        return new StringTextComponent("Turn Undead");
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}