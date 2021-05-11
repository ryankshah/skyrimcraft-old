package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;
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

    public PacketUpdateTargetingEntities(PacketBuffer buf) {
        int size = buf.readVarInt();
        for(int i = 0; i < size; i++)
            targetEntities.add(buf.readVarInt());
    }

    public PacketUpdateTargetingEntities(List<Integer> targetEntities) {
        this.targetEntities = targetEntities;
    }

    public void toBytes(PacketBuffer buf) {
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
        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
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