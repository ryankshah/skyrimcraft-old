package com.ryankshah.skyrimcraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class SkyrimBlock extends Block
{
    protected String displayName;

    public SkyrimBlock(Properties properties, String displayName) {
        super(properties);
        this.displayName = displayName;
    }

    public SkyrimBlock(String displayName) {
        this(AbstractBlock.Properties.create(new Material(MaterialColor.GRAY, false, true, true, false, false, false, PushReaction.PUSH_ONLY)), displayName);
    }

    public String getDisplayName() {
        return this.displayName;
    }
}