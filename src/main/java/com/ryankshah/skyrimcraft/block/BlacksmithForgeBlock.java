package com.ryankshah.skyrimcraft.block;

import com.ryankshah.skyrimcraft.client.gui.BlacksmithForgeScreen;
import com.ryankshah.skyrimcraft.data.ModRecipeType;
import com.ryankshah.skyrimcraft.util.ForgeRecipe;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.List;
import java.util.Random;

public class BlacksmithForgeBlock extends SkyrimBlock
{
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private VoxelShape shape = VoxelShapes.or(
            Block.box(0, 0, 0, 16, 9, 16)
    );

    public BlacksmithForgeBlock(String displayName) {
        super(AbstractBlock.Properties.of(Material.STONE).strength(2.0f).lightLevel((value) -> 15).requiresCorrectToolForDrops().noOcclusion().harvestTool(ToolType.PICKAXE).harvestLevel(2), displayName);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        List<ForgeRecipe> recipes = p_225533_2_.getRecipeManager().getAllRecipesFor(ModRecipeType.FORGE);
        Minecraft.getInstance().setScreen(new BlacksmithForgeScreen(recipes));

        return ActionResultType.SUCCESS;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        return this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return shape;
    }

    @Override
    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.rotate(p_185471_2_.getRotation(p_185471_1_.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(FACING);
    }

    public void animateTick(BlockState state, World world, BlockPos blockPos, Random random) {
        if (random.nextInt(24) == 0) {
            world.playLocalSound((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
        }
        for(int i = 0; i < 3; ++i) {
            double d0 = (double)blockPos.getX() + (random.nextDouble() * (0.4D - 0.2D) + 0.4D);
            double d1 = (double)blockPos.getY() + 0.25D;
            double d2 = (double)blockPos.getZ() + (random.nextDouble() * (0.4D - 0.2D) + 0.4D);
            world.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.LARGE_SMOKE, -d0, d1, -d2, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, -d0, d1, -d2, 0.0D, 0.0D, 0.0D);
        }
    }
}