package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.spell.ISpell;

import java.util.List;
import java.util.Map;

public interface ISkyrimPlayerData
{
    /* SPELL FIELDS */
    public void addToKnownSpells(ISpell spell);
    public void setSelectedSpell(int pos, ISpell spell);
    public void setKnownSpells(List<ISpell> knownSpells);
    public void setKnownSpellsForNBT(List<ISpell> knownSpells);
    public void setSelectedSpells(Map<Integer, ISpell> selectedSpells);
    public void setSelectedSpellsForNBT(Map<Integer, ISpell> selectedSpells);
    public List<ISpell> getKnownSpells();
    public Map<Integer, ISpell> getSelectedSpells();

    /* MAGICKA FIELDS */
    public void consumeMagicka(float amount);
    public void replenishMagicka(float amount);
    public void setMagicka(float amount);
    public void setMagickaForNBT(float amount);
    public float getMagicka();
    public float getMaxMagicka();
}