package com.ryankshah.skyrimcraft.network.map;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class PacketUpdatePlayerMapOnServer
{
    private static final Logger LOGGER = LogManager.getLogger();
    private ObjectList<WorldRenderer.LocalRenderInformationContainer> mapChunks = new ObjectArrayList<>(69696);

    public PacketUpdatePlayerMapOnServer(PacketBuffer buf) {
        int size = buf.readInt();
        for(int i = 0; i < size; i++) {

        }
    }

    public PacketUpdatePlayerMapOnServer(ObjectList<WorldRenderer.LocalRenderInformationContainer> mapChunks) {
        this.mapChunks = mapChunks;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(mapChunks.size());
        for(WorldRenderer.LocalRenderInformationContainer chunk : mapChunks) {
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PacketUpdatePlayerMapOnServer received on wrong side:" + sideReceived);
            return false;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that ServerPlayerEntity exists
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final ServerPlayerEntity sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("ServerPlayerEntity was null when PacketUpdatePlayerMapOnServer was received");
            return false;
        }

        context.enqueueWork(() -> {
            sendingPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
//                cap.setCurrentTarget(targetEntity);
//                Networking.sendToClient(new PacketUpdatePlayerTarget(cap.getCurrentTarget()), sendingPlayer);
            });
        });
        return true;
    }
}