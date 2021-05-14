package com.ryankshah.skyrimcraft.character;

import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.util.MapFeature;
import net.minecraft.entity.LivingEntity;

import java.util.List;
import java.util.Map;

public interface ISkyrimPlayerData
{
    /* SPELL FIELDS */
    public void addToKnownSpells(ISpell spell);
    public void setSelectedSpell(int pos, ISpell spell);
    public void setKnownSpells(List<ISpell> knownSpells);
    public void setSelectedSpells(Map<Integer, ISpell>selectedSpells);
    public List<ISpell> getKnownSpells();
    public Map<Integer, ISpell> getSelectedSpells();
    public Map<ISpell, Float> getShoutsAndCooldowns();
    public float getShoutCooldown(ISpell shout);
    public void setShoutCooldown(ISpell shout, float cooldown);
    public void setShoutsWithCooldowns(Map<ISpell, Float> shoutsWithCooldowns);

    /* MAGICKA FIELDS */
    public void consumeMagicka(float amount);
    public void replenishMagicka(float amount);
    public void setMagicka(float amount);
    public float getMagicka();
    public float getMaxMagicka();
    public float getMagickaRegenModifier();
    public void setMagickaRegenModifier(float modifier);

    /* CURRENT TARGET FIELDS */
    public void setCurrentTarget(LivingEntity entity);
    public LivingEntity getCurrentTarget();
    /* OTHER TARGET FIELDS */
    public void addToTargetingEntities(Integer entityId);
    public List<Integer> getTargetingEntities();
    public void setTargetingEntities(List<Integer> targetingEntities);
    public void removeTargetingEntity(Integer entityId);

    /* FEATURE FIELDS */
    public void addMapFeature(MapFeature feature);
    public void setMapFeatures(List<MapFeature> mapFeatures);
    public List<MapFeature> getMapFeatures();
}