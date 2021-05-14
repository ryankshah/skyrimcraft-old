package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class PacketUpdateMagickaRegenModifierOnServer
{
    private static final Logger LOGGER = LogManager.getLogger();
    private float modifier;

    public PacketUpdateMagickaRegenModifierOnServer(PacketBuffer buf) {
        this.modifier = buf.readFloat();
    }

    public PacketUpdateMagickaRegenModifierOnServer(float modifier) {
        this.modifier = modifier;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(modifier);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PacketUpdateMagickaRegenModifier received on wrong side:" + sideReceived);
            return false;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that ServerPlayerEntity exists
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final ServerPlayerEntity sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("ServerPlayerEntity was null when PacketUpdateMagickaRegenModifier was received");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            ctx.get().getSender().getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                if(cap.getMagickaRegenModifier() != 1.0f) {
                    cap.setMagickaRegenModifier(modifier);
                    Networking.sendToClient(new PacketUpdateMagickaRegenModifierOnClient(cap.getMagickaRegenModifier()), sendingPlayer);
                }
            });
        });
        return true;
    }
}