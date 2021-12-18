package com.ryankshah.skyrimcraft.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class SkyrimWeapon extends SwordItem
{
    private String displayName;

    public SkyrimWeapon(Tier itemTier, int baseDamage, float attackSpeedModifier, Properties itemProperties, String displayName) {
        super(itemTier, baseDamage, attackSpeedModifier, itemProperties);
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
