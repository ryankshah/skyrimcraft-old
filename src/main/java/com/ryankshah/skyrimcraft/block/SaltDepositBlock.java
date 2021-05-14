package com.ryankshah.skyrimcraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class SaltDepositBlock extends SkyrimBlock
{
    private VoxelShape shape = VoxelShapes.or(
            Block.box(1, 0, 1, 15, 2, 13),
            Block.box(0, 0, 9, 12, 2, 15),
            Block.box(12, 0, 13, 13, 3, 14),
            Block.box(12, 0, 13, 15, 1, 15),
            Block.box(11, 2, 3, 14, 3, 10),
            Block.box(2, 2, 3, 11, 5, 12),
            Block.box(7, 5, 6, 10, 6, 9),
            Block.box(6, 5, 4, 7, 6, 9),
            Block.box(3, 5, 4, 6, 6, 7),
            Block.box(15, 0, 11, 16, 1, 14)
    );

    public SaltDepositBlock(String displayName) {
        super(AbstractBlock.Properties.of(Material.SAND).strength(2.0f).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1), displayName);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return shape;
    }

    @Override
    public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        BlockPos blockpos = p_196260_3_.below();
        if (p_196260_1_.getBlock() == this) //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return this.mayPlaceOn(p_196260_2_.getBlockState(blockpos), p_196260_2_, blockpos);
        return this.mayPlaceOn(p_196260_2_.getBlockState(blockpos), p_196260_2_, blockpos);
    }

    protected boolean mayPlaceOn(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
        Block block = p_200014_1_.getBlock();
        return block == Blocks.SAND || block == Blocks.RED_SAND || block == Blocks.TERRACOTTA || block == Blocks.WHITE_TERRACOTTA || block == Blocks.ORANGE_TERRACOTTA || block == Blocks.MAGENTA_TERRACOTTA || block == Blocks.LIGHT_BLUE_TERRACOTTA || block == Blocks.YELLOW_TERRACOTTA || block == Blocks.LIME_TERRACOTTA || block == Blocks.PINK_TERRACOTTA || block == Blocks.GRAY_TERRACOTTA || block == Blocks.LIGHT_GRAY_TERRACOTTA || block == Blocks.CYAN_TERRACOTTA || block == Blocks.PURPLE_TERRACOTTA || block == Blocks.BLUE_TERRACOTTA || block == Blocks.BROWN_TERRACOTTA || block == Blocks.GREEN_TERRACOTTA || block == Blocks.RED_TERRACOTTA || block == Blocks.BLACK_TERRACOTTA || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL;
    }
}