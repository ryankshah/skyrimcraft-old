package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.function.Supplier;

public class PacketAddToCompassFeatures
{
    private static final Logger LOGGER = LogManager.getLogger();
    private CompassFeature compassFeature;

    public PacketAddToCompassFeatures(FriendlyByteBuf buf) {
        UUID id = buf.readUUID();
        ResourceLocation structure = buf.readResourceLocation();
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        BlockPos pos = new BlockPos(x, y, z);
        compassFeature = new CompassFeature(id, structure, pos);
        //ResourceLocation rl = buf.readResourceLocation();
        //this.spell = SpellRegistry.SPELLS_REGISTRY.get().getValue(rl);
    }

    public PacketAddToCompassFeatures(CompassFeature compassFeature) {
        this.compassFeature = compassFeature;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(compassFeature.getId());
        buf.writeResourceLocation(compassFeature.getFeature());
        buf.writeInt(compassFeature.getBlockPos().getX());
        buf.writeInt(compassFeature.getBlockPos().getY());
        buf.writeInt(compassFeature.getBlockPos().getZ());
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

        final ServerPlayer sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("Server Player was null when PacketAddToMapFeatures was received");
            return false;
        }

        context.enqueueWork(() -> {
            sendingPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.addMapFeature(compassFeature);
                //TriggerManager.TRIGGERS.get(spell).trigger(sendingPlayer);
                Networking.sendToClient(new PacketUpdateCompassFeatures(cap.getCompassFeatures()), sendingPlayer);
            });
        });
        return true;
    }
}
