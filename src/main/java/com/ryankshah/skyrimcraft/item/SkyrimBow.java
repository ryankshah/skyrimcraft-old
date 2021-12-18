package com.ryankshah.skyrimcraft.item;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class SkyrimBow extends BowItem
{
    private String displayName;
    private List<Item> supportedArrows;

    public SkyrimBow(Properties p_i48522_1_, String displayName, Item... supportedArrows) {
        super(p_i48522_1_.stacksTo(1));
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
