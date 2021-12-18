package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class PacketUpdateTargetingEntities
{
    private List<Integer> targetEntities = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger();

    public PacketUpdateTargetingEntities(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        for(int i = 0; i < size; i++)
            targetEntities.add(buf.readVarInt());
    }

    public PacketUpdateTargetingEntities(List<Integer> targetEntities) {
        this.targetEntities = targetEntities;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarInt(targetEntities.size());
        for(Integer i : targetEntities)
            buf.writeVarInt(i);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("PacketUpdateTargetingEntities received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketUpdateTargetingEntities context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setTargetingEntities(targetEntities);
            });
        });
        return true;
    }
}