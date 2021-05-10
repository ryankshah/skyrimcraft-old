package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.util.MapFeature;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class PacketAddToMapFeaturesOnClient
{
    private static final Logger LOGGER = LogManager.getLogger();
    private MapFeature mapFeature;

    public PacketAddToMapFeaturesOnClient(PacketBuffer buf) {
        UUID id = buf.readUUID();
        ResourceLocation structure = buf.readResourceLocation();
        int x = buf.readInt();
        int y = buf.readInt();
        ChunkPos pos = new ChunkPos(x, y);
        mapFeature = new MapFeature(id, structure, pos);
        //ResourceLocation rl = buf.readResourceLocation();
        //this.spell = SpellRegistry.SPELLS_REGISTRY.get().getValue(rl);
    }

    public PacketAddToMapFeaturesOnClient(MapFeature mapFeature) {
        this.mapFeature = mapFeature;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUUID(mapFeature.getId());
        buf.writeResourceLocation(mapFeature.getFeature());
        buf.writeInt(mapFeature.getChunkPos().x);
        buf.writeInt(mapFeature.getChunkPos().z);
        //buf.writeResourceLocation(SpellRegistry.SPELLS_REGISTRY.get().getKey(spell));
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
            Networking.sendToServer(new PacketAddToMapFeatures(mapFeature));
        });

        return true;
    }
}