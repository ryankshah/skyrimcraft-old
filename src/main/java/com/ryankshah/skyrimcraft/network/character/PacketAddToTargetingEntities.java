package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class PacketAddToTargetingEntities
{
    private static final Logger LOGGER = LogManager.getLogger();
    private int targetEntity;

    public PacketAddToTargetingEntities(PacketBuffer buf) {
        targetEntity = buf.readVarInt();
    }

    public PacketAddToTargetingEntities(int targetEntity) {
        this.targetEntity = targetEntity;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeVarInt(targetEntity);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PacketAddToTargetingEntities received on wrong side:" + sideReceived);
            return false;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that ServerPlayerEntity exists
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final ServerPlayerEntity sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("ServerPlayerEntity was null when PacketAddToTargetingEntities was received");
            return false;
        }

        context.enqueueWork(() -> {
            sendingPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.addToTargetingEntities(targetEntity);
                Networking.sendToClient(new PacketUpdateTargetingEntities(cap.getTargetingEntities()), sendingPlayer);
            });
        });
        return true;
    }
}