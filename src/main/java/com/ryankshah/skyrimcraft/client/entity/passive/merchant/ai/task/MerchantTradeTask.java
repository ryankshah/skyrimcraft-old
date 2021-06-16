package com.ryankshah.skyrimcraft.client.entity.passive.merchant.ai.task;

import com.google.common.collect.ImmutableMap;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.MerchantEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class MerchantTradeTask extends Task<MerchantEntity>
{
    private final float speedModifier;

    public MerchantTradeTask(float p_i50359_1_) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED), Integer.MAX_VALUE);
        this.speedModifier = p_i50359_1_;
    }

    protected boolean checkExtraStartConditions(ServerWorld p_212832_1_, MerchantEntity p_212832_2_) {
        PlayerEntity playerentity = p_212832_2_.getTradingPlayer();
        return p_212832_2_.isAlive() && playerentity != null && !p_212832_2_.isInWater() && !p_212832_2_.hurtMarked && p_212832_2_.distanceToSqr(playerentity) <= 16.0D && playerentity.containerMenu != null;
    }

    protected boolean canStillUse(ServerWorld p_212834_1_, MerchantEntity p_212834_2_, long p_212834_3_) {
        return this.checkExtraStartConditions(p_212834_1_, p_212834_2_);
    }

    protected void start(ServerWorld p_212831_1_, MerchantEntity p_212831_2_, long p_212831_3_) {
        this.followPlayer(p_212831_2_);
    }

    protected void stop(ServerWorld p_212835_1_, MerchantEntity p_212835_2_, long p_212835_3_) {
        Brain<?> brain = p_212835_2_.getBrain();
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
    }

    protected void tick(ServerWorld p_212833_1_, MerchantEntity p_212833_2_, long p_212833_3_) {
        this.followPlayer(p_212833_2_);
    }

    protected boolean timedOut(long p_220383_1_) {
        return false;
    }

    private void followPlayer(MerchantEntity p_220475_1_) {
        Brain<?> brain = p_220475_1_.getBrain();
        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityPosWrapper(p_220475_1_.getTradingPlayer(), false), this.speedModifier, 2));
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(p_220475_1_.getTradingPlayer(), true));
    }
}
