package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.worldgen.structure.ModStructures;
import javafx.util.Pair;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.structure.Structure;

import java.util.UUID;

public class MapFeature
{
    private ResourceLocation feature;
    private ChunkPos chunkPos;
    private UUID id;

    public static final int ICON_WIDTH = 12, ICON_HEIGHT = 16;

    public MapFeature(UUID id, ResourceLocation feature, ChunkPos chunkPos) {
        this.feature = feature;
        this.chunkPos = chunkPos;
        this.id = id;
    }

    public ResourceLocation getFeature() {
        return feature;
    }

    public ChunkPos getChunkPos() {
        return chunkPos;
    }

    public UUID getId() {
        return id;
    }

    public Pair<Integer, Integer> getIconUV() {
        if(feature.equals(Structure.VILLAGE.getRegistryName())) {
            return new Pair<>(0, 124);
        } else if(feature.equals(Structure.NETHER_BRIDGE.getRegistryName())) {
            return new Pair<>(16, 124);
        } else if(feature.equals(ModStructures.SHOUT_WALL.getId())) {
            return new Pair<>(29, 124);
        } else if(feature.equals(Structure.MINESHAFT.getRegistryName())) {
            return new Pair<>(44, 125);
        }
        return null;
    }

    public CompoundNBT serialise() {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putUUID("uuid", id);
        nbt.putString("resourcelocation", feature.toString());
        nbt.putInt("xPos", chunkPos.x);
        nbt.putInt("zPos", chunkPos.z);

        return nbt;
    }

    public static MapFeature deserialise(CompoundNBT nbt) {
        UUID id = nbt.getUUID("uuid");
        ResourceLocation feature = new ResourceLocation(nbt.getString("resourcelocation"));
        int x = nbt.getInt("xPos");
        int z = nbt.getInt("zPos");
        ChunkPos chunkPos = new ChunkPos(x, z);
        return new MapFeature(id, feature, chunkPos);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MapFeature))
            return false;

        MapFeature featureToCompare = (MapFeature)obj;
        return this.feature.equals(featureToCompare.feature) && this.chunkPos.x == featureToCompare.getChunkPos().x && this.chunkPos.z == featureToCompare.getChunkPos().z;
    }
}