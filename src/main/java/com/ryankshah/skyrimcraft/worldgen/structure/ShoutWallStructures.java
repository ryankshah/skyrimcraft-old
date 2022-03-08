package com.ryankshah.skyrimcraft.worldgen.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ShoutWallStructures extends StructureFeature<JigsawConfiguration> {

    public ShoutWallStructures() {
        // Create the pieces layout of the structure and give it to the game
        super(JigsawConfiguration.CODEC, context -> createPiecesGenerator(context), PostPlacementProcessor.NONE);
    }

    /**
     * : WARNING!!! DO NOT FORGET THIS METHOD!!!! :
     * If you do not override step method, your structure WILL crash the biome as it is being parsed!
     * <p>
     * Generation step for when to generate the structure. there are 10 stages you can pick from!
     * This surface structure stage places the structure before plants and ores are generated.
     */
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.UNDERGROUND_STRUCTURES;
    }

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);
        blockpos = findSuitableLocation(context, blockpos);
        // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces.
        return JigsawPlacement.addPieces(
                context,
                PoolElementStructurePiece::new,
                blockpos,
                false,
                false
        );

//        if (structurePiecesGenerator.isPresent()) {
//            // I use to debug and quickly find out if the structure is spawning or not and where it is.
//            // This is returning the coordinates of the center starting piece.
//            Skyrimcraft.LOGGER.log(Level.DEBUG, "Shout Wall at {}", blockpos);
//        }
//
//        // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces.
//        return structurePiecesGenerator;
    }

    @NotNull
    private static BlockPos findSuitableLocation(PieceGeneratorSupplier.Context<JigsawConfiguration> context, BlockPos blockpos) {
        LevelHeightAccessor heightAccessor = context.heightAccessor();

        // Get the top y location that is solid
        int y = context.chunkGenerator().getBaseHeight(blockpos.getX(), blockpos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, heightAccessor);

        // Create a randomgenerator that depends on the current chunk location. That way if the world is recreated
        // with the same seed the feature will end up at the same spot
        WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(context.seed()));
        worldgenrandom.setLargeFeatureSeed(context.seed(), context.chunkPos().x, context.chunkPos().z);

        // Pick a random y location between a low and a high point
        y = worldgenrandom.nextIntBetweenInclusive(heightAccessor.getMinBuildHeight()+20, y - 10);

        // Go down until we find a spot that has air. Then go down until we find a spot that is solid again
        NoiseColumn baseColumn = context.chunkGenerator().getBaseColumn(blockpos.getX(), blockpos.getZ(), heightAccessor);
        int yy = y; // Remember 'y' because we will just use this if we can't find an air bubble
        int lower = heightAccessor.getMinBuildHeight() + 3; // Lower limit, don't go below this
        while (yy > lower && !baseColumn.getBlock(yy).isAir()) {
            yy--;
        }
        // If we found air we go down until we find a non-air block
        if (yy > lower) {
            while (yy > lower && baseColumn.getBlock(yy).isAir()) {
                yy--;
            }
            if (yy > lower) {
                // We found a possible spawn spot
                y = yy + 1;
            }
        }

        return blockpos.atY(y);
    }
}
