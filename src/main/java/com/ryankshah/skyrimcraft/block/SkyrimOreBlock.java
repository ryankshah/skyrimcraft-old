package com.ryankshah.skyrimcraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.Random;

public class SkyrimOreBlock extends SkyrimBlock
{
    public SkyrimOreBlock(String displayName) {
        // For now ores will require iron pickaxe to mine.
        this(BlockBehaviour.Properties.of(new Material(MaterialColor.COLOR_GRAY, false, true, true, false, false, false, PushReaction.PUSH_ONLY)).strength(3.0F, 3.0F).requiresCorrectToolForDrops(), displayName); // pick ,2
    }

    public SkyrimOreBlock(Properties properties, String displayName) {
        super(properties, displayName);
    }

    protected int getExperience(Random rand) {
        if (this == ModBlocks.EBONY_ORE.get()) {
            return Mth.nextInt(rand, 0, 2);
        } else if (this == ModBlocks.CORUNDUM_ORE.get()) {
            return Mth.nextInt(rand, 0, 4);
        } else if (this == ModBlocks.MALACHITE_ORE.get()) {
            return Mth.nextInt(rand, 3, 7);
        } else if (this == ModBlocks.MOONSTONE_ORE.get()) {
            return Mth.nextInt(rand, 3, 7);
        } else if (this == ModBlocks.ORICHALCUM_ORE.get()) {
            return Mth.nextInt(rand, 2, 5);
        } else if (this == ModBlocks.QUICKSILVER_ORE.get()) {
            return Mth.nextInt(rand, 2, 4);
        } else if (this == ModBlocks.SILVER_ORE.get()) {
            return Mth.nextInt(rand, 3, 4);
        } else {
            return 0;
        }
    }

    /**
     * Perform side-effects from block dropping, such as creating silverfish
     */
    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel worldIn, BlockPos pos, ItemStack stack) {
        super.spawnAfterBreak(state, worldIn, pos, stack);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }
}