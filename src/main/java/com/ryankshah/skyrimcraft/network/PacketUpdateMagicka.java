package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.IMagickaProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
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
            Minecraft.getInstance().player.getCapability(IMagickaProvider.MAGICKA_CAPABILITY).ifPresent((cap) -> {
                cap.set(this.magicka);
            });
        });
        return true;
    }
}