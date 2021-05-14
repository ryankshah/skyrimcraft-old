package com.ryankshah.skyrimcraft.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegenMagickaEffect extends Effect implements IForgeRegistryEntry<Effect>
{
    public RegenMagickaEffect() {
        super(EffectType.BENEFICIAL, 0xAA3792CB);
    }

    public ITextComponent getDisplayName() {
        return new StringTextComponent("Regen Magicka");
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}