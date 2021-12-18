package com.ryankshah.skyrimcraft.character;

import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.character.skill.ISkill;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import net.minecraft.core.Direction;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ISkyrimPlayerDataProvider implements ICapabilitySerializable<CompoundTag>
{
//    @CapabilityInject(ISkyrimPlayerData.class)
//    public static final Capability<ISkyrimPlayerData> SKYRIM_PLAYER_DATA_CAPABILITY = null;

    public static Capability<ISkyrimPlayerData> SKYRIM_PLAYER_DATA_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    private LazyOptional<ISkyrimPlayerData> instance;

    public ISkyrimPlayerDataProvider(Player obj) {
        instance = LazyOptional.of(() -> {
            return new SkyrimPlayerData(obj);
        });
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
    public CompoundTag serializeNBT() {
        if(!SKYRIM_PLAYER_DATA_CAPABILITY.isRegistered())
            return new CompoundTag();
        else {
            CompoundTag tag = new CompoundTag();
            instance.ifPresent(inst -> {
                tag.putBoolean("hasSetup", inst.hasSetup());

                tag.putFloat("magicka", inst.getMagicka());
                tag.putFloat("maxMagicka", inst.getMaxMagicka());
                tag.putFloat("magicka_regen_modifier", inst.getMagickaRegenModifier());

                List<ISpell> knownSpells = inst.getKnownSpells();
                ListTag knownSpellsNBT = new ListTag();
                for (ISpell spell : knownSpells) {
                    knownSpellsNBT.add(StringTag.valueOf(spell.getRegistryName().toString()));
                }
                tag.put("knownSpells", knownSpellsNBT);

                Map<ISpell, Float> shoutsAndCooldowns = inst.getShoutsAndCooldowns();
                CompoundTag shoutsAndCooldownsNBT = new CompoundTag();
                for (Map.Entry<ISpell, Float> entry : shoutsAndCooldowns.entrySet()) {
                    shoutsAndCooldownsNBT.put(entry.getKey().getRegistryName().toString(), FloatTag.valueOf(entry.getValue()));
                }
                tag.put("shoutsAndCooldowns", shoutsAndCooldownsNBT);

                Map<Integer, ISpell> selectedSpells = inst.getSelectedSpells();
                for (Map.Entry<Integer, ISpell> entry : selectedSpells.entrySet()) {
                    tag.put("selected" + entry.getKey(), entry.getValue() == null ? StringTag.valueOf("null") : StringTag.valueOf(entry.getValue().getRegistryName().toString()));
                }

                List<CompassFeature> compassFeatures = inst.getCompassFeatures();
                tag.putInt("mapFeaturesSize", compassFeatures.size());
                int counter = 0;
                for(CompassFeature feature : compassFeatures) {
                    tag.put("feature"+counter++, feature.serialise());
                }

                tag.putInt("totalCharacterXp", inst.getCharacterXp());

                Map<Integer, ISkill> skills = inst.getSkills();
                tag.putInt("skillsSize", skills.size());
                for(Map.Entry<Integer, ISkill> skill : skills.entrySet()) {
                    tag.put("skill"+skill.getKey(), skill.getValue().serialise());
                }

                tag.putInt("levelUpdates", inst.getLevelUpdates());
                tag.putFloat("extraHealth", inst.getExtraMaxHealth());
                tag.putFloat("extraStamina", inst.getExtraMaxStamina());
                tag.putFloat("extraMagicka", inst.getExtraMaxMagicka());

                tag.putInt("raceID", inst.getRace().getId());
                tag.putString("raceName", inst.getRace().getName());

                List<Integer> targetingEntities = inst.getTargetingEntities();
                tag.putInt("targetingEntitiesSize", targetingEntities.size());
                counter = 0;
                for(int entityID : targetingEntities)
                    tag.putInt("targetingEntity"+counter++, entityID);
            });
            return tag;
        }
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (SKYRIM_PLAYER_DATA_CAPABILITY.isRegistered()) {
            instance.ifPresent(inst -> {
                CompoundTag tag = (CompoundTag) nbt;
                List<ISpell> knownSpells = new ArrayList<>();
                Map<Integer, ISpell> selectedSpells = new HashMap<>();
                Map<ISpell, Float> shoutsAndCooldowns = new HashMap<>();
                List<CompassFeature> compassFeatures = new ArrayList<>();
                List<Integer> targetingEntities = new ArrayList<>();
                Map<Integer, ISkill> skills = new HashMap<>();

                boolean hasSetup = tag.getBoolean("hasSetup");

                float magicka = tag.getFloat("magicka");
                float maxMagicka = tag.getFloat("maxMagicka");
                float magicka_regen_modifier = tag.getFloat("magicka_regen_modifier");

                CompoundTag shoutsAndCooldownsNBT = tag.getCompound("shoutsAndCooldowns");
                for(String s : shoutsAndCooldownsNBT.getAllKeys()) {
                    shoutsAndCooldowns.put(SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(s)), shoutsAndCooldownsNBT.getFloat(s));
                }

                ListTag knownSpellsNBT = tag.getList("knownSpells", Tag.TAG_STRING);
                for(Tag inbt : knownSpellsNBT) {
                    String s = inbt.getAsString();
                    ResourceLocation loc = new ResourceLocation(s);
                    knownSpells.add(SpellRegistry.SPELLS_REGISTRY.get().getValue(loc));
                }

                selectedSpells.put(0, tag.getString("selected0").equals("null") ? null : SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(tag.getString("selected0"))));
                selectedSpells.put(1, tag.getString("selected1").equals("null") ? null : SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(tag.getString("selected1"))));

                int mapFeaturesSize = tag.getInt("mapFeaturesSize");
                for(int i = 0; i < mapFeaturesSize; i++) {
                    CompoundTag comp = tag.getCompound("feature"+i);
                    compassFeatures.add(CompassFeature.deserialise(comp));
                }

                int totalCharacterXp = tag.getInt("totalCharacterXp");

                int skillsSize = tag.getInt("skillsSize");
                for(int i = 0; i < skillsSize; i++) {
                    CompoundTag comp = tag.getCompound("skill"+i);
                    skills.put(i, ISkill.deserialise(comp));
                }

                int levelUpdates = tag.getInt("levelUpdates");
                float extraHealth = tag.getFloat("extraHealth");
                float extraStamina = tag.getFloat("extraStamina");
                float extraMagicka = tag.getFloat("extraMagicka");

                int raceID = tag.getInt("raceID");
                String raceName = tag.getString("raceName");

                int targetingEntitiesSize = tag.getInt("targetingEntitiesSize");
                for(int i = 0; i < targetingEntitiesSize; i++) {
                    targetingEntities.add(tag.getInt("targetingEntity"+i));
                }

                inst.setHasSetup(hasSetup);
                inst.setMagicka(magicka);
                inst.setMaxMagicka(maxMagicka);
                inst.setKnownSpells(knownSpells);
                inst.setShoutsWithCooldowns(shoutsAndCooldowns);
                inst.setSelectedSpells(selectedSpells);
                inst.setCompassFeatures(compassFeatures);
                inst.setTargetingEntities(targetingEntities);
                inst.setMagickaRegenModifier(magicka_regen_modifier);
                inst.setCharacterXp(totalCharacterXp);
                inst.setRace(new Race(raceID, raceName, skills));
                inst.setSkills(skills);
                inst.setLevelUpdates(levelUpdates);
                inst.setExtraMaxHealth(extraHealth);
                inst.setExtraMaxStamina(extraStamina);
                inst.setExtraMaxMagicka(extraMagicka);
            });
        }
    }
}