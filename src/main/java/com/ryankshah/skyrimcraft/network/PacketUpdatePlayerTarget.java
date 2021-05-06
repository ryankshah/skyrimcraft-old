package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

public class PacketUpdatePlayerTarget
{
    private LivingEntity targetEntity;
    private static final Logger LOGGER = LogManager.getLogger();

    public PacketUpdatePlayerTarget(PacketBuffer buf) {
        int id = buf.readVarInt();
        if(id != -1) {
            Entity ent = ClientUtil.getClientWorld().getEntity(id);
            targetEntity = ent instanceof LivingEntity ? (LivingEntity) ent : null;
        } else targetEntity = null;
    }

    public PacketUpdatePlayerTarget(LivingEntity targetEntity) {
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

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("PacketUpdatePlayerTarget received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketUpdatePlayerTarget context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setCurrentTarget(targetEntity);
            });
        });
        return true;
    }
}