package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateShoutCooldown
{
    private float cooldown;

    public PacketUpdateShoutCooldown(PacketBuffer buf) {
        this.cooldown = buf.readFloat();
    }

    public PacketUpdateShoutCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(cooldown);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setShoutCooldown(this.cooldown);
            });
        });
        return true;
    }
}