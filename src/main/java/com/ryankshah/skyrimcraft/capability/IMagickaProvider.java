package com.ryankshah.skyrimcraft.capability;

import net.minecraft.nbt.FloatNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IMagickaProvider implements ICapabilitySerializable<FloatNBT>
{
    @CapabilityInject(IMagicka.class)
    public static final Capability<IMagicka> MAGICKA_CAPABILITY = null;

    private LazyOptional<IMagicka> instance = LazyOptional.of(MAGICKA_CAPABILITY::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return MAGICKA_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public FloatNBT serializeNBT() {
        return (FloatNBT) MAGICKA_CAPABILITY.getStorage().writeNBT(MAGICKA_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("at serialise")), null);
    }

    @Override
    public void deserializeNBT(FloatNBT nbt) {
        MAGICKA_CAPABILITY.getStorage().readNBT(MAGICKA_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("at deserialise")), null, nbt);
    }
}