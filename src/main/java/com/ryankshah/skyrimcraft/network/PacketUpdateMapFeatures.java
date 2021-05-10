package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.util.MapFeature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class PacketUpdateMapFeatures
{
    private List<MapFeature> mapFeatures = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger();

    public PacketUpdateMapFeatures(PacketBuffer buf) {
        int size = buf.readInt();
        for(int i = 0; i < size; i++) {
            UUID id = buf.readUUID();
            ResourceLocation structure = buf.readResourceLocation();
            int x = buf.readInt();
            int y = buf.readInt();
            ChunkPos pos = new ChunkPos(x, y);
            mapFeatures.add(new MapFeature(id, structure, pos));
        }
    }

    public PacketUpdateMapFeatures(List<MapFeature> mapFeatures) {
        this.mapFeatures = mapFeatures;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(mapFeatures.size());
        for(MapFeature feature : mapFeatures) {
            buf.writeUUID(feature.getId());
            buf.writeResourceLocation(feature.getFeature());
            buf.writeInt(feature.getChunkPos().x);
            buf.writeInt(feature.getChunkPos().z);
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("PacketUpdateMapFeatures received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketUpdateMapFeatures context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setMapFeatures(this.mapFeatures);
            });
        });
        return true;
    }
}