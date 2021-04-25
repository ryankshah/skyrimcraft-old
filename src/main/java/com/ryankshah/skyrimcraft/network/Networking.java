package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking
{
    private static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Skyrimcraft.MODID, "skyrimcraft_channel"),
                () -> "1.0",
                s -> true,
                s -> true);

        INSTANCE.messageBuilder(PacketReplenishMagicka.class, nextID())
                .encoder(PacketReplenishMagicka::toBytes)
                .decoder(PacketReplenishMagicka::new) // buf -> new PacketReplenishMagicka(buf)
                .consumer(PacketReplenishMagicka::handle)
                .add();

        INSTANCE.messageBuilder(PacketConsumeMagicka.class, nextID())
                .encoder(PacketConsumeMagicka::toBytes)
                .decoder(PacketConsumeMagicka::new) // buf -> new PacketConsumeMagicka(buf)
                .consumer(PacketConsumeMagicka::handle)
                .add();
    }

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}