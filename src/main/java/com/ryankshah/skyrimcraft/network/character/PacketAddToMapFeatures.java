package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.util.MapFeature;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.function.Supplier;

public class PacketAddToMapFeatures
{
    private static final Logger LOGGER = LogManager.getLogger();
    private MapFeature mapFeature;

    public PacketAddToMapFeatures(PacketBuffer buf) {
        UUID id = buf.readUUID();
        ResourceLocation structure = buf.readResourceLocation();
        int x = buf.readInt();
        int y = buf.readInt();
        ChunkPos pos = new ChunkPos(x, y);
        mapFeature = new MapFeature(id, structure, pos);
        //ResourceLocation rl = buf.readResourceLocation();
        //this.spell = SpellRegistry.SPELLS_REGISTRY.get().getValue(rl);
    }

    public PacketAddToMapFeatures(MapFeature mapFeature) {
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

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PacketAddToMapFeatures received on wrong side:" + sideReceived);
            return false;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that ServerPlayerEntity exists
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final ServerPlayerEntity sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("Server Player was null when PacketAddToMapFeatures was received");
            return false;
        }

        context.enqueueWork(() -> {
            sendingPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.addMapFeature(mapFeature);
                //TriggerManager.TRIGGERS.get(spell).trigger(sendingPlayer);
                Networking.sendToClient(new PacketUpdateMapFeatures(cap.getMapFeatures()), sendingPlayer);
            });
        });
        return true;
    }
}
