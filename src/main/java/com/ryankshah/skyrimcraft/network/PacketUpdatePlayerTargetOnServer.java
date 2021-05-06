package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class PacketUpdatePlayerTargetOnServer
{
    private static final Logger LOGGER = LogManager.getLogger();
    private LivingEntity targetEntity;

    public PacketUpdatePlayerTargetOnServer(PacketBuffer buf) {
        int id = buf.readVarInt();
        if(id != -1) {
            Entity ent = ClientUtil.getClientWorld().getEntity(id);
            targetEntity = ent instanceof LivingEntity ? (LivingEntity) ent : null;
        } else targetEntity = null;
    }

    public PacketUpdatePlayerTargetOnServer(LivingEntity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public void toBytes(PacketBuffer buf) {
        int id = targetEntity != null ? targetEntity.getId() : -1;
        buf.writeVarInt(id);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PacketUpdatePlayerTargetOnServer received on wrong side:" + sideReceived);
            return false;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that ServerPlayerEntity exists
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final ServerPlayerEntity sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("ServerPlayerEntity was null when PacketUpdatePlayerTargetOnServer was received");
            return false;
        }

        context.enqueueWork(() -> {
            sendingPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setCurrentTarget(targetEntity);
                Networking.sendToClient(new PacketUpdatePlayerTarget(cap.getCurrentTarget()), sendingPlayer);
            });
        });
        return true;
    }
}