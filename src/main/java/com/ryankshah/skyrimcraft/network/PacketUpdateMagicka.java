package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateMagicka
{
    private float magicka;

    public PacketUpdateMagicka(PacketBuffer buf) {
        this.magicka = buf.readFloat();
    }

    public PacketUpdateMagicka(float magicka) {
        this.magicka = magicka;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(magicka);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setMagicka(this.magicka);
            });
        });
        return true;
    }
}