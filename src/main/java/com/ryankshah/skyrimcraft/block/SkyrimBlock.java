package com.ryankshah.skyrimcraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

import net.minecraft.block.AbstractBlock.Properties;

public class SkyrimBlock extends Block
{
    protected String displayName;

    public SkyrimBlock(Properties properties, String displayName) {
        super(properties);
        this.displayName = displayName;
    }

    public SkyrimBlock(String displayName) {
        this(AbstractBlock.Properties.of(Material.STONE), displayName);
    }

    public String getDisplayName() {
        return this.displayName;
    }
}