package com.ryankshah.skyrimcraft.character;

import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import com.ryankshah.skyrimcraft.util.MapFeature;
import net.minecraft.nbt.*;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkyrimPlayerDataStorage implements Capability.IStorage<ISkyrimPlayerData>
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<ISkyrimPlayerData> capability, ISkyrimPlayerData instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();

        tag.putFloat("magicka", instance.getMagicka());

        List<ISpell> knownSpells = instance.getKnownSpells();
        ListNBT knownSpellsNBT = new ListNBT();
        for (ISpell spell : knownSpells) {
            knownSpellsNBT.add(StringNBT.valueOf(spell.getRegistryName().toString()));
        }
        tag.put("knownSpells", knownSpellsNBT);

        Map<ISpell, Float> shoutsAndCooldowns = instance.getShoutsAndCooldowns();
        CompoundNBT shoutsAndCooldownsNBT = new CompoundNBT();
        for (Map.Entry<ISpell, Float> entry : shoutsAndCooldowns.entrySet()) {
            shoutsAndCooldownsNBT.put(entry.getKey().getRegistryName().toString(), FloatNBT.valueOf(entry.getValue()));
        }
        tag.put("shoutsAndCooldowns", shoutsAndCooldownsNBT);

        Map<Integer, ISpell> selectedSpells = instance.getSelectedSpells();
        for (Map.Entry<Integer, ISpell> entry : selectedSpells.entrySet()) {
            tag.put("selected" + entry.getKey(), entry.getValue() == null ? StringNBT.valueOf("null") : StringNBT.valueOf(entry.getValue().getRegistryName().toString()));
        }

        List<MapFeature> mapFeatures = instance.getMapFeatures();
        tag.putInt("mapFeaturesSize", mapFeatures.size());
        int counter = 0;
        for(MapFeature feature : mapFeatures) {
            tag.put("feature"+counter++, feature.serialise());
        }

        List<Integer> targetingEntities = instance.getTargetingEntities();
        tag.putInt("targetingEntitiesSize", targetingEntities.size());
        counter = 0;
        for(int entityID : targetingEntities)
            tag.putInt("targetingEntity"+counter++, entityID);

        return tag;
    }

    @Override
    public void readNBT(Capability<ISkyrimPlayerData> capability, ISkyrimPlayerData instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        List<ISpell> knownSpells = new ArrayList<>();
        Map<Integer, ISpell> selectedSpells = new HashMap<>();
        Map<ISpell, Float> shoutsAndCooldowns = new HashMap<>();
        List<MapFeature> mapFeatures = new ArrayList<>();
        List<Integer> targetingEntities = new ArrayList<>();

        float magicka = tag.getFloat("magicka");

        CompoundNBT shoutsAndCooldownsNBT = tag.getCompound("shoutsAndCooldowns");
        for(String s : shoutsAndCooldownsNBT.getAllKeys()) {
            shoutsAndCooldowns.put(SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(s)), shoutsAndCooldownsNBT.getFloat(s));
        }

        ListNBT knownSpellsNBT = tag.getList("knownSpells", Constants.NBT.TAG_STRING);
        for(INBT inbt : knownSpellsNBT) {
            String s = inbt.getAsString();
            ResourceLocation loc = new ResourceLocation(s);
            knownSpells.add(SpellRegistry.SPELLS_REGISTRY.get().getValue(loc));
        }

        selectedSpells.put(0, tag.getString("selected0").equals("null") ? null : SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(tag.getString("selected0"))));
        selectedSpells.put(1, tag.getString("selected1").equals("null") ? null : SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(tag.getString("selected1"))));

        int mapFeaturesSize = tag.getInt("mapFeaturesSize");
        for(int i = 0; i < mapFeaturesSize; i++) {
            CompoundNBT comp = tag.getCompound("feature"+i);
            mapFeatures.add(MapFeature.deserialise(comp));
        }

        int targetingEntitiesSize = tag.getInt("targetingEntitiesSize");
        for(int i = 0; i < targetingEntitiesSize; i++) {
            targetingEntities.add(tag.getInt("targetingEntity"+i));
        }

        instance.setMagicka(magicka);
        instance.setKnownSpells(knownSpells);
        instance.setShoutsWithCooldowns(shoutsAndCooldowns);
        instance.setSelectedSpells(selectedSpells);
        instance.setMapFeatures(mapFeatures);
        instance.setTargetingEntities(targetingEntities);
    }
}