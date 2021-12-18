package com.ryankshah.skyrimcraft.character;

import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.skill.ISkill;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;

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
    public void setMaxMagicka(float maxMagicka);
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
    public void addMapFeature(CompassFeature feature);
    public void setCompassFeatures(List<CompassFeature> compassFeatures);
    public List<CompassFeature> getCompassFeatures();

    /* SKILL FIELDS */
    public void setCharacterLevel(int level);
    public int getCharacterLevel();
    public int getCharacterXp();
    public void setCharacterXp(int xp);
    public void addCharacterXp(int amount, ServerPlayer playerEntity);
    public double getXpNeededForNextCharacterLevel(int level);
    public void setSkills(Map<Integer, ISkill> skills);
    public Map<Integer, ISkill> getSkills();
    //public void addXpToSkill(ISkill skill);
    public void addXpToSkill(int id, int baseXp, ServerPlayer playerEntity);
    public void incrementLevelUpdates();
    public void decrementLevelUpdates();
    public int getLevelUpdates();
    public void setLevelUpdates(int levelUpdates);

    /* CHARACTER UPDATE FIELDS */

    /**
     * Add an extra half heart (0.5f) to player max health
     */
    public void addExtraMaxHealth();
    public void setExtraMaxHealth(float extraHealth);
    public float getExtraMaxHealth();
    public void addExtraMaxStamina();
    public void setExtraMaxStamina(float extraStamina);
    public float getExtraMaxStamina();
    public void addExtraMaxMagicka();
    public void setExtraMaxMagicka(float extraMagicka);
    public float getExtraMaxMagicka();

    /* RACE FIELDS */
    public void setRace(Race race);
    public Race getRace();

    public boolean hasSetup();
    public void setHasSetup(boolean hasSetup);
}