package com.ryankshah.skyrimcraft.capability;

import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class MagickaStorage implements Capability.IStorage<IMagicka>
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IMagicka> capability, IMagicka instance, Direction side) {
        return FloatNBT.valueOf(instance.getMagicka());
    }

    @Override
    public void readNBT(Capability<IMagicka> capability, IMagicka instance, Direction side, INBT nbt) {
        instance.set(((FloatNBT) nbt).getFloat());
    }
}