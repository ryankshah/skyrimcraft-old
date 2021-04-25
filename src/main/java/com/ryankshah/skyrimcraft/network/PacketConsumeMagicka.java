package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.IMagickaProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketConsumeMagicka
{
    private float magicka;

    public PacketConsumeMagicka(PacketBuffer buf) {
        this.magicka = buf.readFloat();
    }

    public PacketConsumeMagicka(float magicka) {
        this.magicka = magicka;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(magicka);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();
            assert IMagickaProvider.MAGICKA_CAPABILITY != null;
            sender.getCapability(IMagickaProvider.MAGICKA_CAPABILITY).ifPresent((cap) -> {
                cap.consume(this.magicka);
            });
        });
        return true;
    }
}