package com.ryankshah.skyrimcraft.character;

import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.skill.ISkill;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.character.PacketAddToLevelUpdates;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import com.ryankshah.skyrimcraft.util.LevelUpdate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SkyrimPlayerData implements ISkyrimPlayerData
{
    private PlayerEntity playerEntity;

    private List<ISpell> knownSpells;
    private Map<Integer, ISpell> selectedSpells;
    private LivingEntity targetEntity;
    private List<Integer> targetingEntities;

    private float magicka = 20.0f;
    private final float maxMagicka = 20.0f;
    private float magicka_regen_modifier;
    private Map<ISpell, Float> shoutsOnCooldown;

    private List<CompassFeature> compassFeatures;

    private int characterLevel;
    private int characterTotalXp;
    private float characterXpProgress;
    private Map<Integer, ISkill> skills;

    public SkyrimPlayerData() {
        knownSpells = new ArrayList<>();
        compassFeatures = new ArrayList<>();
        selectedSpells = new HashMap<>();
        shoutsOnCooldown = new HashMap<>();
        targetEntity = null;
        targetingEntities = new ArrayList<>();
        skills = new HashMap<>();
        skills.putAll(SkillRegistry.SKILLS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toMap(ISkill::getID, ISkill::getSkill)));//SkillRegistry.SKILLS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList()); //new ArrayList<>(SkillRegistry.SKILLS_REGISTRY.get().getValues());

        magicka_regen_modifier = 1.0f;

        selectedSpells.put(0, null);
        selectedSpells.put(1, null);
    }

    public SkyrimPlayerData(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
        knownSpells = new ArrayList<>();
        compassFeatures = new ArrayList<>();
        selectedSpells = new HashMap<>();
        shoutsOnCooldown = new HashMap<>();
        targetEntity = null;
        targetingEntities = new ArrayList<>();
        skills = new HashMap<>();
        skills.putAll(SkillRegistry.SKILLS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toMap(ISkill::getID, ISkill::getSkill)));//SkillRegistry.SKILLS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList()); //new ArrayList<>(SkillRegistry.SKILLS_REGISTRY.get().getValues());

        magicka_regen_modifier = 1.0f;

        selectedSpells.put(0, null);
        selectedSpells.put(1, null);
    }

    @Override
    public void addToKnownSpells(ISpell spell) {
        if(!knownSpells.contains(spell))
            knownSpells.add(spell);
    }

    @Override
    public void setSelectedSpell(int pos, ISpell spell) {
        // only allow 2 selected spells at a time.
        if(pos >= 0 && pos < 2) {
            selectedSpells.put(pos, spell);
        }
    }

    @Override
    public void setKnownSpells(List<ISpell> knownSpells) {
        this.knownSpells = knownSpells;
    }

    @Override
    public void setSelectedSpells(Map<Integer, ISpell> selectedSpells) {
        this.selectedSpells = selectedSpells;
    }

    @Override
    public List<ISpell> getKnownSpells() {
        return this.knownSpells;
    }

    @Override
    public Map<Integer, ISpell> getSelectedSpells() {
        return this.selectedSpells;
    }

    @Override
    public float getShoutCooldown(ISpell shout) {
        return shoutsOnCooldown.get(shout) != null ? shoutsOnCooldown.get(shout) : 0f;
    }

    @Override
    public void setShoutCooldown(ISpell shout, float cooldown) {
        this.shoutsOnCooldown.put(shout, cooldown);
    }

    @Override
    public Map<ISpell, Float> getShoutsAndCooldowns() {
        return shoutsOnCooldown;
    }

    @Override
    public void setShoutsWithCooldowns(Map<ISpell, Float> shoutsOnCooldown) {
        this.shoutsOnCooldown = shoutsOnCooldown;
    }

    @Override
    public void consumeMagicka(float amount) {
        setMagicka(magicka - amount);
    }

    @Override
    public void replenishMagicka(float amount) {
        setMagicka(magicka + amount);
    }

    @Override
    public void setMagicka(float amount) {
        this.magicka = amount;

        if(this.magicka <= 0.0f)
            this.magicka = 0.0f;
        if(this.magicka >= maxMagicka)
            this.magicka = maxMagicka;
    }

    @Override
    public float getMagicka() {
        return this.magicka;
    }

    @Override
    public float getMaxMagicka() {
        return this.maxMagicka;
    }

    @Override
    public float getMagickaRegenModifier() {
        return magicka_regen_modifier;
    }

    @Override
    public void setMagickaRegenModifier(float modifier) {
        this.magicka_regen_modifier = modifier;
    }

    @Override
    public void setCurrentTarget(LivingEntity entity) {
        this.targetEntity = entity;
    }

    @Override
    public LivingEntity getCurrentTarget() {
        return this.targetEntity;
    }

    @Override
    public void addToTargetingEntities(Integer entity) {
        if(!targetingEntities.contains(entity))
            targetingEntities.add(entity);
    }

    @Override
    public void removeTargetingEntity(Integer entity) {
        if(targetingEntities.contains(entity))
            targetingEntities.remove(entity);
    }

    @Override
    public List<Integer> getTargetingEntities() {
        return targetingEntities;
    }

    @Override
    public void setTargetingEntities(List<Integer> targetingEntities) {
        this.targetingEntities = targetingEntities;
    }

    @Override
    public void addMapFeature(CompassFeature compassFeature) {
        if(this.compassFeatures.stream().noneMatch(feature -> feature.equals(compassFeature)))
            compassFeatures.add(compassFeature);
    }

    @Override
    public void setCompassFeatures(List<CompassFeature> compassFeatures) {
        this.compassFeatures = compassFeatures;
    }

    @Override
    public List<CompassFeature> getCompassFeatures() {
        return compassFeatures;
    }

    @Override
    public void setCharacterLevel(int level) {
        this.characterLevel = level;
    }

    @Override
    public int getCharacterLevel() {
        return calculateCharacterLevelFromXp();
    }

    @Override
    public int getCharacterXp() {
        return this.characterTotalXp;
    }

    @Override
    public void addCharacterXp(int amount, ServerPlayerEntity playerEntity) {
        int level = getCharacterLevel();
        this.characterTotalXp += amount;
        if(getCharacterLevel() > level)
            Networking.sendToClient(new PacketAddToLevelUpdates(new LevelUpdate("characterLevel", getCharacterLevel(), 200)), playerEntity);
    }

    @Override
    public void setCharacterXp(int totalXp) {
        this.characterTotalXp = totalXp;
    }

    @Override
    public Map<Integer, ISkill> getSkills() {
        return skills;
    }

    @Override
    public void addXpToSkill(int id, int baseXp, ServerPlayerEntity playerEntity) {
        ISkill skill = skills.get(id);
        ISkill skillOld = new ISkill(skill);

        System.out.println(skill);
        System.out.println(characterTotalXp);

        int oldSkillLevel = skillOld.getLevel();
        skill.giveExperiencePoints(baseXp);

        if(skill.getLevel() > oldSkillLevel) {
            // The skill has leveled up, so send packet to client to add to the skyrim ingame gui levelUpdates list.
            Networking.sendToClient(new PacketAddToLevelUpdates(new LevelUpdate(skill.getName(), skill.getLevel(), 200)), playerEntity);
            addCharacterXp(skill.getLevel(), playerEntity);
        }

        //skills.put(skill.getID(), skill);
    }

    @Override
    public void setSkills(Map<Integer, ISkill> skills) {
        this.skills = skills;
    }

    private int calculateCharacterLevelFromXp() {
        return (int)Math.floor(-2.5 + Math.sqrt(8 * characterTotalXp + 1225) / 10);
    }

    // from https://elderscrolls.fandom.com/wiki/User:Documentalist/Character_level_calculation_(Skyrim)
    @Override
    public double getXpNeededForNextCharacterLevel(int level) {
        return 12.5*Math.pow(level+1, 2) + 62.5*level - 75;
    }
}