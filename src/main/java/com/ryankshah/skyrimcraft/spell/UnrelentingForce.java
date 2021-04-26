package com.ryankshah.skyrimcraft.spell;


public class UnrelentingForce implements ISpell
{
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public String getName() {
        return "Unrelenting Force";
    }

    @Override
    public float getCost() {
        return 0;
    }

    @Override
    public float getCooldown() {
        return 45.0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.SHOUT;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.NA;
    }

    @Override
    public void onCast() {

    }
}