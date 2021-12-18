package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class FrozenEffect extends MobEffect implements IForgeRegistryEntry<MobEffect>
{
    public FrozenEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xA5F2F3);
    }

    public Component getDisplayName() {
        return new TextComponent("Frozen");
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        if(livingEntity instanceof PathfinderMob)
            ((PathfinderMob) livingEntity).goalSelector.setControlFlag(Goal.Flag.MOVE, false);
        else if(livingEntity instanceof Player)
            ((Player) livingEntity).setDeltaMovement(0, 0 ,0);
        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}
