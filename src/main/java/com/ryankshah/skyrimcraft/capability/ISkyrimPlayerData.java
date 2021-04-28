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
    public void setSelectedSpells(ISpell[] selectedSpells);
    public void setSelectedSpellsForNBT(ISpell[] selectedSpells);
    public List<ISpell> getKnownSpells();
    public ISpell[] getSelectedSpells();
    public float getShoutCooldown();
    public void setShoutCooldown(float cooldown);
    public void setShoutCooldownForNBT(float cooldown);

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