package com.ryankshah.skyrimcraft.client.entity.passive.merchant.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.ryankshah.skyrimcraft.client.entity.ModEntityType;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.MerchantEntity;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.ai.task.PanicTask;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.ai.task.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.village.PointOfInterestType;

import java.util.Optional;

public class MerchantTasks
{
    public static ImmutableList<Pair<Integer, ? extends Task<? super MerchantEntity>>> getCorePackage(VillagerProfession p_220638_0_, float p_220638_1_) {
        return ImmutableList.of(Pair.of(0, new SwimTask(0.8F)), Pair.of(0, new InteractWithDoorTask()), Pair.of(0, new LookTask(45, 90)), Pair.of(0, new PanicTask()), Pair.of(0, new WakeUpTask()), Pair.of(0, new ExpirePOITask(p_220638_0_.getJobPoiType(), MemoryModuleType.JOB_SITE)), Pair.of(0, new ExpirePOITask(p_220638_0_.getJobPoiType(), MemoryModuleType.POTENTIAL_JOB_SITE)), Pair.of(1, new WalkToTargetTask()), Pair.of(2, new SwitchMerchantJobTask(p_220638_0_)), Pair.of(3, new MerchantTradeTask(p_220638_1_)), Pair.of(6, new GatherPOITask(p_220638_0_.getJobPoiType(), MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, true, Optional.empty())), Pair.of(7, new FindPotentialMerchantJobTask(p_220638_1_)), Pair.of(8, new FindMerchantJobTask(p_220638_1_)), Pair.of(10, new GatherPOITask(PointOfInterestType.HOME, MemoryModuleType.HOME, false, Optional.of((byte)14))), Pair.of(10, new GatherPOITask(PointOfInterestType.MEETING, MemoryModuleType.MEETING_POINT, true, Optional.of((byte)14))), Pair.of(10, new AssignMerchantProfessionTask()), Pair.of(10, new ChangeMerchantJobTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super MerchantEntity>>> getWorkPackage(VillagerProfession p_220639_0_, float p_220639_1_) {
        return ImmutableList.of(getMinimalLookBehavior(), Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.JOB_SITE, 0.4F, 4), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.JOB_SITE, 0.4F, 1, 10), 5), Pair.of(new MerchantWalkTowardsRandomSecondaryPosTask(MemoryModuleType.SECONDARY_JOB_SITE, p_220639_1_, 1, 6, MemoryModuleType.JOB_SITE), 5)))), Pair.of(10, new FindInteractionAndLookTargetTask(EntityType.PLAYER, 4)), Pair.of(2, new MerchantStayNearPointTask(MemoryModuleType.JOB_SITE, p_220639_1_, 9, 100, 1200)));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super MerchantEntity>>> getIdlePackage(VillagerProfession p_220641_0_, float p_220641_1_) {
        return ImmutableList.of(Pair.of(2, new FirstShuffledTask<>(ImmutableList.of(Pair.of(InteractWithEntityTask.of(ModEntityType.MERCHANT.get(), 8, MemoryModuleType.INTERACTION_TARGET, p_220641_1_, 2), 2), Pair.of(InteractWithEntityTask.of(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, p_220641_1_, 2), 1), Pair.of(new FindWalkTargetTask(p_220641_1_), 1), Pair.of(new WalkTowardsLookTargetTask(p_220641_1_, 2), 1), Pair.of(new JumpOnBedTask(p_220641_1_), 1), Pair.of(new DummyTask(30, 60), 1)))), Pair.of(3, new FindInteractionAndLookTargetTask(EntityType.PLAYER, 4)), getFullLookBehavior(), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super MerchantEntity>>> getPanicPackage(VillagerProfession p_220636_0_, float p_220636_1_) {
        float f = p_220636_1_ * 1.5F;
        return ImmutableList.of(Pair.of(0, new MerchantClearHurtTask()), Pair.of(1, RunAwayTask.entity(MemoryModuleType.NEAREST_HOSTILE, f, 6, false)), Pair.of(1, RunAwayTask.entity(MemoryModuleType.HURT_BY_ENTITY, f, 6, false)), Pair.of(3, new FindWalkTargetTask(f, 2, 2)), getMinimalLookBehavior());
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super MerchantEntity>>> getRestPackage(VillagerProfession p_220635_0_, float p_220635_1_) {
        return ImmutableList.of(Pair.of(2, new MerchantStayNearPointTask(MemoryModuleType.HOME, p_220635_1_, 1, 150, 1200)), Pair.of(3, new ExpirePOITask(PointOfInterestType.HOME, MemoryModuleType.HOME)), Pair.of(3, new SleepAtHomeTask()), Pair.of(5, new FirstShuffledTask<>(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleStatus.VALUE_ABSENT), ImmutableList.of(Pair.of(new WalkToHouseTask(p_220635_1_), 1), Pair.of(new WalkRandomlyInsideTask(p_220635_1_), 4), Pair.of(new MerchantWalkToPoiTask(p_220635_1_, 4), 2), Pair.of(new DummyTask(20, 40), 2)))), getMinimalLookBehavior(), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super MerchantEntity>>> getMeetPackage(VillagerProfession p_220637_0_, float p_220637_1_) {
        return ImmutableList.of(Pair.of(2, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.MEETING_POINT, 0.4F, 40), 2), Pair.of(new CongregateTask(), 2)))), Pair.of(10, new FindInteractionAndLookTargetTask(EntityType.PLAYER, 4)), Pair.of(2, new MerchantStayNearPointTask(MemoryModuleType.MEETING_POINT, p_220637_1_, 6, 100, 200)), Pair.of(3, new ExpirePOITask(PointOfInterestType.MEETING, MemoryModuleType.MEETING_POINT)), getFullLookBehavior(), Pair.of(99, new UpdateActivityTask()));
    }

    private static Pair<Integer, Task<LivingEntity>> getFullLookBehavior() {
        return Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new LookAtEntityTask(EntityType.CAT, 8.0F), 8), Pair.of(new LookAtEntityTask(ModEntityType.MERCHANT.get(), 8.0F), 2), Pair.of(new LookAtEntityTask(EntityType.PLAYER, 8.0F), 2), Pair.of(new LookAtEntityTask(EntityClassification.CREATURE, 8.0F), 1), Pair.of(new LookAtEntityTask(EntityClassification.WATER_CREATURE, 8.0F), 1), Pair.of(new LookAtEntityTask(EntityClassification.WATER_AMBIENT, 8.0F), 1), Pair.of(new LookAtEntityTask(EntityClassification.MONSTER, 8.0F), 1), Pair.of(new DummyTask(30, 60), 2))));
    }

    private static Pair<Integer, Task<LivingEntity>> getMinimalLookBehavior() {
        return Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new LookAtEntityTask(ModEntityType.MERCHANT.get(), 8.0F), 2), Pair.of(new LookAtEntityTask(EntityType.PLAYER, 8.0F), 2), Pair.of(new DummyTask(30, 60), 8))));
    }
}