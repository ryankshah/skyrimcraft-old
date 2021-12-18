package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class PacketUpdateExtraStatOnServer
{
    private static final Logger LOGGER = LogManager.getLogger();
    private int stat;

    public PacketUpdateExtraStatOnServer(FriendlyByteBuf buf) {
        stat = buf.readInt();
    }

    public PacketUpdateExtraStatOnServer(int stat) {
        this.stat = stat;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(stat);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PacketCreateCharacterOnServer received on wrong side:" + sideReceived);
            return false;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that ServerPlayerEntity exists
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final ServerPlayer sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("Server Player was null when PacketCreateCharacterOnServer was received");
            return false;
        }

        context.enqueueWork(() -> {
            sendingPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                switch(stat) {
                    case 0:
                        cap.setExtraMaxMagicka(0.5f);
                        break;
                    case 1:
                        cap.setExtraMaxHealth(0.5f);
                        break;
                    case 2:
                        cap.setExtraMaxStamina(0.5f);
                        break;
                }
                cap.setMaxMagicka(cap.getMaxMagicka() + cap.getExtraMaxMagicka());
                sendingPlayer.getAttribute(Attributes.MAX_HEALTH).setBaseValue(sendingPlayer.getMaxHealth() + cap.getExtraMaxHealth());
                sendingPlayer.getAttribute(Attributes.MAX_HEALTH).setBaseValue(sendingPlayer.getMaxHealth() + cap.getExtraMaxHealth());
                //sendingPlayer.getFoodData().
                Networking.sendToClient(new PacketUpdateExtraStats(cap.getExtraMaxMagicka(), cap.getExtraMaxHealth(), cap.getExtraMaxStamina()), sendingPlayer);
            });
        });
        return true;
    }
}