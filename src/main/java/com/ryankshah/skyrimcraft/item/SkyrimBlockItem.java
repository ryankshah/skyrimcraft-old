package com.ryankshah.skyrimcraft.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

import net.minecraft.item.Item.Properties;

public class SkyrimBlockItem extends BlockItem
{
    private String displayName;

    public SkyrimBlockItem(Block blockIn, Properties builder, String displayName) {
        super(blockIn, builder);
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}