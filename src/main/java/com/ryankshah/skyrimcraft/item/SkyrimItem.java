package com.ryankshah.skyrimcraft.item;

import net.minecraft.world.item.Item;

public class SkyrimItem extends Item
{
    private String displayName;

    public SkyrimItem(Properties properties, String displayName) {
        super(properties);
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
