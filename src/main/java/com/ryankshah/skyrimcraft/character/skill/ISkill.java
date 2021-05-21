package com.ryankshah.skyrimcraft.character.skill;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ISkill extends ForgeRegistryEntry<ISkill>
{
    private PlayerEntity player;
    private int identifier;
    private String name;
    private int level;
    private int totalXp;

    private float skillUseMultiplier;
    public int skillUseOffset;
    public float skillImproveMultiplier;
    public int skillImproveOffset;

    // Main constructor to use
    public ISkill(int identifier, String name, int baseLevel, float skillUseMultiplier, int skillUseOffset, float skillImproveMultiplier, int skillImproveOffset) {
        this.identifier = identifier;
        this.name = name;
        this.level = baseLevel;
        this.totalXp = 0;
        this.skillUseMultiplier = skillUseMultiplier;
        this.skillUseOffset = skillUseOffset;
        this.skillImproveMultiplier = skillImproveMultiplier;
        this.skillImproveOffset = skillImproveOffset;
    }

    public ISkill(int identifier, String name, int level, int totalXp, float skillUseMultiplier, int skillUseOffset, float skillImproveMultiplier, int skillImproveOffset) {
        this.identifier = identifier;
        this.name = name;
        this.level = level;
        this.totalXp = totalXp;
        this.skillUseMultiplier = skillUseMultiplier;
        this.skillUseOffset = skillUseOffset;
        this.skillImproveMultiplier = skillImproveMultiplier;
        this.skillImproveOffset = skillImproveOffset;
    }

    // Dummy constructor
    public ISkill(ISkill skill) {
        this(skill.identifier, skill.name, skill.level, skill.totalXp, skill.skillUseMultiplier, skill.skillUseOffset, skill.skillImproveMultiplier, skill.skillImproveOffset);
    }

    /**
     * Get the ID of the skill
     *
     * @return skill ID
     */
    public int getID() {
        return identifier;
    }

    /**
     * Get the skill instance
     *
     * @return ISkill instance
     */
    public ISkill getSkill() {
        return this;
    }

    /**
     * Get the name of the skill
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the player who the skill instance belongs to
     *
     * @param playerEntity
     */
    public void setPlayer(PlayerEntity playerEntity) {
        this.player = playerEntity;
    }

    /**
     * Get the player entity who has the skill
     * @return {@link PlayerEntity}
     */
    public PlayerEntity getPlayer() {
        return this.player;
    }

    public float getSkillImproveMultiplier() {
        return skillImproveMultiplier;
    }

    public float getSkillUseMultiplier() {
        return skillUseMultiplier;
    }

    public int getIdentifier() {
        return identifier;
    }

    public int getSkillImproveOffset() {
        return skillImproveOffset;
    }

    public int getSkillUseOffset() {
        return skillUseOffset;
    }

    ///
    /// EXPERIENCE-RELATED FIELDS
    ///

    public int getLevel() { return this.level; }

    public void setLevel(int level) { this.level = level; }

    public int getTotalXp() {
        return this.totalXp;
    }

    private void giveXpLevels(int levels) {
        this.level += levels;
        if (this.level < 0) {
            this.level = 0;
            this.totalXp = 0;
        }
    }

    // xp progress calculation taken from https://en.uesp.net/wiki/Skyrim:Leveling
    public void giveExperiencePoints(int baseXp) {
        // full calculation: `Skill Use Mult * (base XP * skill specific multipliers) + Skill Use Offset` -- TODO: add in skill specific multipliers
        // minecraft progress calc : (float)amount / (float)this.getXpNeededForNextLevel();
        this.totalXp += skillUseMultiplier * (baseXp) + skillUseOffset;
        float xpProgress = totalXp / (float)getXpNeededForNextLevel();

        if(xpProgress < 0.0F) {
            float f = xpProgress * (float)this.getXpNeededForNextLevel();
            if (level > 0) {
                this.giveXpLevels(-1);
                xpProgress = 1.0F + f / (float)this.getXpNeededForNextLevel();
            } else {
                giveXpLevels(-1);
                xpProgress = 0.0F;
            }
        }

        if(xpProgress >= 1.0F) {
            xpProgress = (xpProgress - 1.0F) * (float)this.getXpNeededForNextLevel();
            this.giveXpLevels(1);
            xpProgress /= (float)this.getXpNeededForNextLevel();
        }
    }

    // Taken from https://en.uesp.net/wiki/Skyrim:Leveling
    public double getXpNeededForNextLevel() {
        return level == 0 ? 0 : skillImproveMultiplier * Math.pow((level-1), 1.95) + skillImproveOffset;
    }

    public CompoundNBT serialise() {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putInt("id", identifier);
        nbt.putString("name", name);
        nbt.putInt("level", level);
        nbt.putInt("totalXp", totalXp);
        nbt.putFloat("skillUseMultiplier", skillUseMultiplier);
        nbt.putInt("skillUseOffset", skillUseOffset);
        nbt.putFloat("skillImproveMultiplier", skillImproveMultiplier);
        nbt.putInt("skillImproveOffset", skillImproveOffset);

        return nbt;
    }

    public static ISkill deserialise(CompoundNBT nbt) {
        int p1 = nbt.getInt("id");
        String p2 = nbt.getString("name");
        int p3 = nbt.getInt("level");
        int p4 = nbt.getInt("totalXp");
        float p6 = nbt.getFloat("skillUseMultiplier");
        int p7 = nbt.getInt("skillUseOffset");
        float p8 = nbt.getFloat("skillImproveMultiplier");
        int p9 = nbt.getInt("skillImproveOffset");
        return new ISkill(p1, p2, p3, p4, p6, p7, p8, p9);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("id: ").append(identifier).append(", ");
        sb.append("name: ").append(name).append(", ");
        sb.append("level: ").append(level).append(", ");
        sb.append("totalXp: ").append(totalXp).append(", ");
        sb.append("skillUseMultiplier: ").append(skillUseMultiplier).append(", ");
        sb.append("skillUseOffset: ").append(skillUseOffset).append(", ");
        sb.append("skillImproveMultiplier: ").append(skillImproveMultiplier).append(", ");
        sb.append("skillImproveOffset: ").append(skillImproveOffset);
        sb.append("]");
        return sb.toString();
    }
}