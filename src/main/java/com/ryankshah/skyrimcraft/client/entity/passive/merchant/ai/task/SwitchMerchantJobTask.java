package com.ryankshah.skyrimcraft.client.entity.passive.merchant.ai.task;

import com.google.common.collect.ImmutableMap;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.MerchantEntity;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.MerchantHandler;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;

public class SwitchMerchantJobTask extends Task<MerchantEntity>
{
    final VillagerProfession profession;

    public SwitchMerchantJobTask(VillagerProfession p_i231525_1_) {
        super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.LIVING_ENTITIES, MemoryModuleStatus.VALUE_PRESENT));
        this.profession = p_i231525_1_;
    }

    protected void start(ServerWorld p_212831_1_, MerchantEntity p_212831_2_, long p_212831_3_) {
        GlobalPos globalpos = p_212831_2_.getBrain().getMemory(MemoryModuleType.JOB_SITE).get();
        p_212831_1_.getPoiManager().getType(globalpos.pos()).ifPresent((p_233933_3_) -> {
            MerchantHandler.getNearbyMerchantsWithCondition(p_212831_2_, (p_233935_3_) -> {
                return this.competesForSameJobsite(globalpos, p_233933_3_, p_233935_3_);
            }).reduce(p_212831_2_, SwitchMerchantJobTask::selectWinner);
        });
    }

    private static MerchantEntity selectWinner(MerchantEntity p_233932_0_, MerchantEntity p_233932_1_) {
        MerchantEntity merchantEntity1;
        MerchantEntity merchantEntity2;
        if (p_233932_0_.getVillagerXp() > p_233932_1_.getVillagerXp()) {
            merchantEntity1 = p_233932_0_;
            merchantEntity2 = p_233932_1_;
        } else {
            merchantEntity1 = p_233932_1_;
            merchantEntity2 = p_233932_0_;
        }

        merchantEntity2.getBrain().eraseMemory(MemoryModuleType.JOB_SITE);
        return merchantEntity1;
    }

    private boolean competesForSameJobsite(GlobalPos p_233934_1_, PointOfInterestType p_233934_2_, MerchantEntity p_233934_3_) {
        return this.hasJobSite(p_233934_3_) && p_233934_1_.equals(p_233934_3_.getBrain().getMemory(MemoryModuleType.JOB_SITE).get()) && this.hasMatchingProfession(p_233934_2_, p_233934_3_.getVillagerData().getProfession());
    }

    private boolean hasMatchingProfession(PointOfInterestType p_233930_1_, VillagerProfession p_233930_2_) {
        return MerchantHandler.MERCHANT_PROFESSIONS.test(p_233930_2_);
    }

    private boolean hasJobSite(MerchantEntity p_233931_1_) {
        return p_233931_1_.getBrain().getMemory(MemoryModuleType.JOB_SITE).isPresent();
    }
}