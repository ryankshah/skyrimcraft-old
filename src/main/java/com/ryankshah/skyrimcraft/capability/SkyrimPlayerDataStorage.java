package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SkyrimPlayerDataStorage implements Capability.IStorage<ISkyrimPlayerData>
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<ISkyrimPlayerData> capability, ISkyrimPlayerData instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();

        tag.putFloat("magicka", instance.getMagicka());
        tag.putFloat("cooldown", instance.getShoutCooldown());

        List<ISpell> knownSpells = instance.getKnownSpells();
        int counter = 0;
        tag.putInt("size", knownSpells.size());
        for (ISpell spell : knownSpells) {
            tag.put("" + counter++, StringNBT.valueOf(spell.getRegistryName().toString()));
        }

        return tag;
    }

    @Override
    public void readNBT(Capability<ISkyrimPlayerData> capability, ISkyrimPlayerData instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        List<ISpell> knownSpells = new ArrayList<>();

        float magicka = tag.getFloat("magicka");
        float cooldown = tag.getFloat("cooldown");

        int size = ((CompoundNBT) nbt).getInt("size");
        int counter = 0;
        for(int i = 0; i < size; i++) {
            ResourceLocation loc = new ResourceLocation(tag.getString("" + counter++));
            knownSpells.add(SpellRegistry.SPELLS_REGISTRY.get().getValue(loc));
        }

        instance.setMagickaForNBT(magicka);
        instance.setKnownSpellsForNBT(knownSpells);
        instance.setShoutCooldownForNBT(cooldown);
    }
}