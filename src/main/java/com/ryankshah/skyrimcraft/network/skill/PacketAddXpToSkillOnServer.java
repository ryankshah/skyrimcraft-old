package com.ryankshah.skyrimcraft.network.skill;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.character.PacketUpdateCharacter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class PacketAddXpToSkillOnServer
{
    private static final Logger LOGGER = LogManager.getLogger();
    private int id, baseXp;

    public PacketAddXpToSkillOnServer(FriendlyByteBuf buf) {
        id = buf.readInt();
        baseXp = buf.readInt();
    }

    public PacketAddXpToSkillOnServer(int id, int baseXp) {
        this.id = id;
        this.baseXp = baseXp;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeInt(baseXp);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PacketAddXpToSkillOnServer received on wrong side:" + sideReceived);
            return false;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that ServerPlayerEntity exists
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final ServerPlayer sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("ServerPlayerEntity was null when PacketAddXpToSkillOnServer was received");
            return false;
        }

        context.enqueueWork(() -> {
            sendingPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.addXpToSkill(id, baseXp, sendingPlayer);
                Networking.sendToClient(new PacketUpdateCharacter(cap.getCharacterXp(), cap.getSkills(), cap.getRace()), sendingPlayer);
            });
        });
        return true;
    }
}
