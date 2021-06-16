package com.ryankshah.skyrimcraft.client.entity.passive.merchant.ai.sensor;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.MerchantEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Set;

public class MerchantSecondaryPositionSensor extends Sensor<MerchantEntity>
{
    public MerchantSecondaryPositionSensor() {
        super(40);
    }

    protected void doTick(ServerWorld p_212872_1_, MerchantEntity p_212872_2_) {
        RegistryKey<World> registrykey = p_212872_1_.dimension();
        BlockPos blockpos = p_212872_2_.blockPosition();
        List<GlobalPos> list = Lists.newArrayList();
        int i = 4;

        for(int j = -4; j <= 4; ++j) {
            for(int k = -2; k <= 2; ++k) {
                for(int l = -4; l <= 4; ++l) {
                    BlockPos blockpos1 = blockpos.offset(j, k, l);
                    if (p_212872_2_.getVillagerData().getProfession().getSecondaryPoi().contains(p_212872_1_.getBlockState(blockpos1).getBlock())) {
                        list.add(GlobalPos.of(registrykey, blockpos1));
                    }
                }
            }
        }

        Brain<?> brain = p_212872_2_.getBrain();
        if (!list.isEmpty()) {
            brain.setMemory(MemoryModuleType.SECONDARY_JOB_SITE, list);
        } else {
            brain.eraseMemory(MemoryModuleType.SECONDARY_JOB_SITE);
        }

    }

    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(MemoryModuleType.SECONDARY_JOB_SITE);
    }
}