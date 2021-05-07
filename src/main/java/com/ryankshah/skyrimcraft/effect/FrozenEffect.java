package com.ryankshah.skyrimcraft.effect;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class FrozenEffect extends Effect implements IForgeRegistryEntry<Effect>
{
    public FrozenEffect() {
        super(EffectType.BENEFICIAL, 0xA5F2F3);
    }

    public ITextComponent getDisplayName() {
        return new StringTextComponent("Frozen");
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        if(livingEntity instanceof CreatureEntity)
            ((CreatureEntity) livingEntity).goalSelector.setControlFlag(Goal.Flag.MOVE, false);
        else if(livingEntity instanceof PlayerEntity)
            ((PlayerEntity) livingEntity).setDeltaMovement(0, 0 ,0);
        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}
