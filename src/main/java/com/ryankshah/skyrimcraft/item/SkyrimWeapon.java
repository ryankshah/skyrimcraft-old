package com.ryankshah.skyrimcraft.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;

public class SkyrimWeapon extends SwordItem
{
    private String displayName;

    public SkyrimWeapon(IItemTier itemTier, int baseDamage, float attackSpeedModifier, Properties itemProperties, String displayName) {
        super(itemTier, baseDamage, attackSpeedModifier, itemProperties);
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
