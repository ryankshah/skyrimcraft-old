package com.ryankshah.skyrimcraft.item;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class SkyrimItem extends Item
{
    private String displayName;
    private List<String> description;

    public SkyrimItem(Properties properties, String displayName) {
        super(properties);
        this.displayName = displayName;
        this.description = new ArrayList<>();
    }

    public SkyrimItem(Properties properties, String displayName, List<String> description) {
        super(properties);
        this.displayName = displayName;
        this.description = description;
    }

    public List<String> getDisplayDescription() {
        return description;
    }

    public String getDisplayName() {
        return displayName;
    }
}
