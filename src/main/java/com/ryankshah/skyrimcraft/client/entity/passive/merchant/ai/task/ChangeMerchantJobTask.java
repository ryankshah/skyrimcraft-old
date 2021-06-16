package com.ryankshah.skyrimcraft.client.entity.passive.merchant.ai.task;

import com.google.common.collect.ImmutableMap;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.MerchantEntity;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.MerchantHandler;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.world.server.ServerWorld;

public class ChangeMerchantJobTask extends Task<MerchantEntity>
{
    public ChangeMerchantJobTask() {
        super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_ABSENT));
    }

    protected boolean checkExtraStartConditions(ServerWorld p_212832_1_, MerchantEntity p_212832_2_) {
        VillagerData villagerdata = p_212832_2_.getVillagerData();
        return villagerdata.getProfession() != MerchantHandler.NONE.get() && p_212832_2_.getVillagerXp() == 0 && villagerdata.getLevel() <= 1;
    }

    protected void start(ServerWorld p_212831_1_, MerchantEntity p_212831_2_, long p_212831_3_) {
        p_212831_2_.setVillagerData(p_212831_2_.getVillagerData().setProfession(MerchantHandler.NONE.get()));
        p_212831_2_.refreshBrain(p_212831_1_);
    }
}