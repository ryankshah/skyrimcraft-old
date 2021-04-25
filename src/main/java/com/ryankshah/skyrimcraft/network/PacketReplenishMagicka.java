package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.IMagickaProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketReplenishMagicka
{
    private final float magicka;

    public PacketReplenishMagicka(PacketBuffer buf) {
        this.magicka = buf.readFloat();
    }

    public PacketReplenishMagicka(float magicka) {
        this.magicka = magicka;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(magicka);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();
            sender.getCapability(IMagickaProvider.MAGICKA_CAPABILITY).ifPresent((cap) -> {
                cap.replenish(this.magicka);
            });
            sender.sendStatusMessage(new StringTextComponent("WOOOHOOOO"), false);
        });
        ctx.get().setPacketHandled(true);
        return true;
    }
}