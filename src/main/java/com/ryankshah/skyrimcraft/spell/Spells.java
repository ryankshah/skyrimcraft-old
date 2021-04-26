package com.ryankshah.skyrimcraft.spell;

public class Spells
{
    /**
     * SHOUTS
     * ======
     * Shouts do not cost mana and cooldowns will be referenced from https://elderscrolls.fandom.com/wiki/Dragon_Shouts
     */
    public static ISpell UNRELENTING_FORCE;

    public Spells() {}

    public static void createSpells() {
        UNRELENTING_FORCE = new UnrelentingForce();
    }
}