package com.ryankshah.skyrimcraft.client.entity.passive.merchant.ai.task;

import com.google.common.collect.ImmutableMap;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.MerchantEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.world.server.ServerWorld;

public class PanicTask extends Task<MerchantEntity>
{
    public PanicTask() {
        super(ImmutableMap.of());
    }

    protected boolean canStillUse(ServerWorld p_212834_1_, VillagerEntity p_212834_2_, long p_212834_3_) {
        return isHurt(p_212834_2_) || hasHostile(p_212834_2_);
    }

    protected void start(ServerWorld p_212831_1_, VillagerEntity p_212831_2_, long p_212831_3_) {
        if (isHurt(p_212831_2_) || hasHostile(p_212831_2_)) {
            Brain<?> brain = p_212831_2_.getBrain();
            if (!brain.isActive(Activity.PANIC)) {
                brain.eraseMemory(MemoryModuleType.PATH);
                brain.eraseMemory(MemoryModuleType.WALK_TARGET);
                brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
                brain.eraseMemory(MemoryModuleType.BREED_TARGET);
                brain.eraseMemory(MemoryModuleType.INTERACTION_TARGET);
            }

            brain.setActiveActivityIfPossible(Activity.PANIC);
        }

    }

    public static boolean hasHostile(LivingEntity p_220513_0_) {
        return p_220513_0_.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_HOSTILE);
    }

    public static boolean isHurt(LivingEntity p_220512_0_) {
        return p_220512_0_.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY);
    }
}