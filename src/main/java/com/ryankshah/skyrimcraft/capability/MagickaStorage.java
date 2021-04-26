package com.ryankshah.skyrimcraft.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class MagickaStorage implements Capability.IStorage<IMagicka>
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IMagicka> capability, IMagicka instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putFloat("magicka", instance.getMagicka());
        return tag;
    }

    @Override
    public void readNBT(Capability<IMagicka> capability, IMagicka instance, Direction side, INBT nbt) {
        float magicka = ((CompoundNBT) nbt).getFloat("magicka");
        instance.setForNBT(magicka);
    }
}