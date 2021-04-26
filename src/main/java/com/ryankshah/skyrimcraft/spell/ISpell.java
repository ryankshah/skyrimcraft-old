package com.ryankshah.skyrimcraft.spell;

/**
 * TODO:
 *   - Implement spell "stages" - specifically for shouts so that each
 *     individual part of the shout is learnable
 */
public interface ISpell
{
    /**
     * Get the ID of the spell
     * @return spell ID
     */
    int getID();

    /**
     * Get the name of the spell
     * @return name
     */
    String getName();

    /**
     * Get the magicka cost of the spell
     * @return magicka cost
     */
    float getCost();

    /**
     * Get the cooldown (seconds) of the spell
     * @return cooldown
     */
    float getCooldown();

    /**
     * Get the type of spell {@link SpellType}
     * @return type
     */
    SpellType getType();

    /**
     * Get the spell difficulty {@link SpellDifficulty}
     * @return difficulty
     */
    SpellDifficulty getDifficulty();

    /**
     * Specifies what happens on spell cast
     */
    void onCast();

    enum SpellType {
        ALTERATION(0),
        DESTRUCTION(1),
        RESTORATION(2),
        SHOUT(3),
        POWERS(4);

        private int typeID;

        SpellType(int typeID) {
            this.typeID = typeID;
        }

        public int getTypeID() {
            return this.typeID;
        }
    }
    enum SpellDifficulty {
        NA(0),
        NOVICE(1),
        APPRENTICE(2),
        ADEPT(3),
        EXPERT(4),
        MASTER(5);

        private int difficulty;

        SpellDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public int getDifficulty() {
            return this.difficulty;
        }
    }
}