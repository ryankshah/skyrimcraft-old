package com.ryankshah.skyrimcraft.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class EtherealEffect extends Effect implements IForgeRegistryEntry<Effect>
{
    public EtherealEffect() {
        super(EffectType.BENEFICIAL, 0xA5F2F3);
    }

    public ITextComponent getDisplayName() {
        return new StringTextComponent("Ethereal");
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
