package com.ryankshah.skyrimcraft.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class SkyrimBlock extends Block
{
    protected String displayName;

    public SkyrimBlock(Properties properties, String displayName) {
        super(properties);
        this.displayName = displayName;
    }

    public SkyrimBlock(String displayName) {
        this(BlockBehaviour.Properties.of(Material.STONE), displayName);
    }

    public String getDisplayName() {
        return this.displayName;
    }
}