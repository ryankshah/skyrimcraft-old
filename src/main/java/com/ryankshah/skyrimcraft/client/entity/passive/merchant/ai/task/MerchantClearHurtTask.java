package com.ryankshah.skyrimcraft.client.entity.passive.merchant.ai.task;

import com.google.common.collect.ImmutableMap;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.MerchantEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class MerchantClearHurtTask extends Task<MerchantEntity>
{
    public MerchantClearHurtTask() {
        super(ImmutableMap.of());
    }

    protected void start(ServerWorld p_212831_1_, MerchantEntity p_212831_2_, long p_212831_3_) {
        boolean flag = PanicTask.isHurt(p_212831_2_) || PanicTask.hasHostile(p_212831_2_) || isCloseToEntityThatHurtMe(p_212831_2_);
        if (!flag) {
            p_212831_2_.getBrain().eraseMemory(MemoryModuleType.HURT_BY);
            p_212831_2_.getBrain().eraseMemory(MemoryModuleType.HURT_BY_ENTITY);
            p_212831_2_.getBrain().updateActivityFromSchedule(p_212831_1_.getDayTime(), p_212831_1_.getGameTime());
        }

    }

    private static boolean isCloseToEntityThatHurtMe(MerchantEntity p_220394_0_) {
        return p_220394_0_.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).filter((p_223523_1_) -> {
            return p_223523_1_.distanceToSqr(p_220394_0_) <= 36.0D;
        }).isPresent();
    }
}