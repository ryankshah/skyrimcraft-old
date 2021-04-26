package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.event.CapabilityHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IMagickaProvider implements ICapabilitySerializable<CompoundNBT>
{
    @CapabilityInject(IMagicka.class)
    public static final Capability<IMagicka> MAGICKA_CAPABILITY = null;
    private static PlayerEntity playerEntity;

    private LazyOptional<IMagicka> instance;

    public IMagickaProvider(PlayerEntity obj) {
        instance = LazyOptional.of(() -> {
            return new Magicka(obj);
        });
    }

    public static PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap != MAGICKA_CAPABILITY) return LazyOptional.empty();
        return instance.cast();
    }

    public void invalidate() {
        instance.invalidate();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if(MAGICKA_CAPABILITY == null)
            return new CompoundNBT();
        else
            return (CompoundNBT)MAGICKA_CAPABILITY.writeNBT(instance.orElseThrow(() -> new IllegalArgumentException("at serialise")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (MAGICKA_CAPABILITY != null) {
            MAGICKA_CAPABILITY.readNBT(instance.orElseThrow(() -> new IllegalArgumentException("at deserialise")), null, nbt);
        }
    }
}