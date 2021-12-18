package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Supplier;

public class PacketUpdatePlayerTarget
{
    private LivingEntity targetEntity;
    private static final Logger LOGGER = LogManager.getLogger();

    public PacketUpdatePlayerTarget(FriendlyByteBuf buf) {
        int id = buf.readVarInt();
        if(id != -1) {
            Entity ent = ClientUtil.getClientWorld().getEntity(id);
            targetEntity = ent instanceof LivingEntity ? (LivingEntity) ent : null;
        } else targetEntity = null;
    }

    public PacketUpdatePlayerTarget(LivingEntity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public void toBytes(FriendlyByteBuf buf) {
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
        Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
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