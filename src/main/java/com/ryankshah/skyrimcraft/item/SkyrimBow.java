package com.ryankshah.skyrimcraft.item;

import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class SkyrimBow extends BowItem
{
    private String displayName;
    private List<Item> supportedArrows;

    public SkyrimBow(Properties p_i48522_1_, String displayName, Item... supportedArrows) {
        super(p_i48522_1_);
        this.displayName = displayName;
        this.supportedArrows = Arrays.asList(supportedArrows);
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return is -> supportedArrows.contains(is.getItem());
    }
}
