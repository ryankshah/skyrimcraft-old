package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class PacketUpdatePlayerTarget
{
    private LivingEntity targetEntity;

    public PacketUpdatePlayerTarget(PacketBuffer buf) {
        int id = buf.readVarInt();
        if(id != -1) {
            Entity ent = ClientUtil.getClientWorld().getEntityByID(id);
            targetEntity = ent instanceof LivingEntity ? (LivingEntity) ent : null;
        } else targetEntity = null;
    }

    public PacketUpdatePlayerTarget(LivingEntity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public void toBytes(PacketBuffer buf) {
        int id = targetEntity != null ? targetEntity.getEntityId() : -1;
        buf.writeVarInt(id);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setCurrentTarget(targetEntity);
            });
        });
        return true;
    }
}