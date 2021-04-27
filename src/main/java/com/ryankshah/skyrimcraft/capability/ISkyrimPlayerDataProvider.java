package com.ryankshah.skyrimcraft.capability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ISkyrimPlayerDataProvider implements ICapabilitySerializable<CompoundNBT>
{
    @CapabilityInject(ISkyrimPlayerData.class)
    public static final Capability<ISkyrimPlayerData> SKYRIM_PLAYER_DATA_CAPABILITY = null;
    private static PlayerEntity playerEntity;

    private LazyOptional<ISkyrimPlayerData> instance;

    public ISkyrimPlayerDataProvider(PlayerEntity obj) {
        instance = LazyOptional.of(() -> {
            return new SkyrimPlayerData(obj);
        });
    }

    public static PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap != SKYRIM_PLAYER_DATA_CAPABILITY) return LazyOptional.empty();
        return instance.cast();
    }

    public void invalidate() {
        instance.invalidate();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if(SKYRIM_PLAYER_DATA_CAPABILITY == null)
            return new CompoundNBT();
        else
            return (CompoundNBT)SKYRIM_PLAYER_DATA_CAPABILITY.writeNBT(instance.orElseThrow(() -> new IllegalArgumentException("at serialise")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (SKYRIM_PLAYER_DATA_CAPABILITY != null) {
            SKYRIM_PLAYER_DATA_CAPABILITY.readNBT(instance.orElseThrow(() -> new IllegalArgumentException("at deserialise")), null, nbt);
        }
    }
}