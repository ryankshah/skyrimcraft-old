package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.spell.ISpell;
import net.minecraft.entity.LivingEntity;

import java.util.List;
import java.util.Map;

public interface ISkyrimPlayerData
{
    /* SPELL FIELDS */
    public void addToKnownSpells(ISpell spell);
    public void setSelectedSpell(int pos, ISpell spell);
    public void setKnownSpells(List<ISpell> knownSpells);
    public void setKnownSpellsForNBT(List<ISpell> knownSpells);
    public void setSelectedSpells(Map<Integer, ISpell>selectedSpells);
    public void setSelectedSpellsForNBT(Map<Integer, ISpell> selectedSpells);
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
    public void setMagickaForNBT(float amount);
    public float getMagicka();
    public float getMaxMagicka();

    /* TARGET FIELDS */
    public void setCurrentTarget(LivingEntity entity);
    public void setCurrentTargetForNBT(LivingEntity entity);
    public LivingEntity getCurrentTarget();
}