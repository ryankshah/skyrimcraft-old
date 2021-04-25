package com.ryankshah.skyrimcraft.block;

import com.ryankshah.skyrimcraft.util.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class SkyrimOreBlock extends SkyrimBlock
{
    public SkyrimOreBlock(String displayName) {
        this(AbstractBlock.Properties.create(new Material(MaterialColor.GRAY, false, true, true, false, false, false, PushReaction.PUSH_ONLY)), displayName);
    }

    public SkyrimOreBlock(Properties properties, String displayName) {
        super(properties, displayName);
    }

    protected int getExperience(Random rand) {
        if (this == ModBlocks.EBONY_ORE.get()) {
            return MathHelper.nextInt(rand, 0, 2);
        } else if (this == ModBlocks.CORUNDUM_ORE.get()) {
            return MathHelper.nextInt(rand, 0, 4);
        } else if (this == ModBlocks.MALACHITE_ORE.get()) {
            return MathHelper.nextInt(rand, 3, 7);
        } else if (this == ModBlocks.MOONSTONE_ORE.get()) {
            return MathHelper.nextInt(rand, 3, 7);
        } else if (this == ModBlocks.ORICHALCUM_ORE.get()) {
            return MathHelper.nextInt(rand, 2, 5);
        } else if (this == ModBlocks.QUICKSILVER_ORE.get()) {
            return MathHelper.nextInt(rand, 2, 4);
        } else if (this == ModBlocks.SILVER_ORE.get()) {
            return MathHelper.nextInt(rand, 3, 4);
        } else {
            return 0;
        }
    }

    /**
     * Perform side-effects from block dropping, such as creating silverfish
     */
    @Override
    public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack) {
        super.spawnAdditionalDrops(state, worldIn, pos, stack);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }
}