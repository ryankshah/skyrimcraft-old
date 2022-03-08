package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.worldgen.structure.ModStructures;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

import java.util.AbstractMap;
import java.util.UUID;

public class CompassFeature
{
    private ResourceLocation feature;
    private BlockPos blockPos;
    private UUID id;

    public static final int ICON_WIDTH = 12, ICON_HEIGHT = 16;

    public CompassFeature(UUID id, ResourceLocation feature, BlockPos blockPos) {
        this.feature = feature;
        this.blockPos = blockPos;
        this.id = id;
    }

    public ResourceLocation getFeature() {
        return feature;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public UUID getId() {
        return id;
    }

    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        if(feature.equals(StructureFeature.VILLAGE.getRegistryName())) {
            return new AbstractMap.SimpleEntry<>(0, 124);
        } else if(feature.equals(StructureFeature.FORTRESS.getRegistryName())) {
            return new AbstractMap.SimpleEntry<>(16, 124);
        } else if(feature.equals(ModStructures.SHOUT_WALL.getId())) {
            return new AbstractMap.SimpleEntry<>(29, 124);
        } else if(feature.equals(StructureFeature.MINESHAFT.getRegistryName())) {
            return new AbstractMap.SimpleEntry<>(44, 125);
        } else if(feature.equals(StructureFeature.SHIPWRECK.getRegistryName())) {
            return new AbstractMap.SimpleEntry<>(57, 124);
        }
        return null;
    }

    public CompoundTag serialise() {
        CompoundTag nbt = new CompoundTag();

        nbt.putUUID("uuid", id);
        nbt.putString("resourcelocation", feature.toString());
        nbt.putInt("xPos", blockPos.getX());
        nbt.putInt("yPos", blockPos.getY());
        nbt.putInt("zPos", blockPos.getZ());

        return nbt;
    }

    public static CompassFeature deserialise(CompoundTag nbt) {
        UUID id = nbt.getUUID("uuid");
        ResourceLocation feature = new ResourceLocation(nbt.getString("resourcelocation"));
        int x = nbt.getInt("xPos");
        int y = nbt.getInt("yPos");
        int z = nbt.getInt("zPos");
        BlockPos blockPos = new BlockPos(x, y, z);
        return new CompassFeature(id, feature, blockPos);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof CompassFeature))
            return false;

        CompassFeature featureToCompare = (CompassFeature)obj;
        return this.feature.equals(featureToCompare.feature) && this.blockPos.getX() == featureToCompare.getBlockPos().getX() && this.getBlockPos().getZ() == featureToCompare.getBlockPos().getZ();
    }
}