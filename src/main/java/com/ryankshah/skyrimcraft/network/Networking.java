package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.client.Minecraft;
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

        INSTANCE.messageBuilder(PacketUpdateMagicka.class, nextID())
                .encoder(PacketUpdateMagicka::toBytes)
                .decoder(PacketUpdateMagicka::new)
                .consumer(PacketUpdateMagicka::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdateKnownSpells.class, nextID())
                .encoder(PacketUpdateKnownSpells::toBytes)
                .decoder(PacketUpdateKnownSpells::new) // buf -> new PacketUpdateMagicka(buf)
                .consumer(PacketUpdateKnownSpells::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdateSelectedSpells.class, nextID())
                .encoder(PacketUpdateSelectedSpells::toBytes)
                .decoder(PacketUpdateSelectedSpells::new) // buf -> new PacketUpdateMagicka(buf)
                .consumer(PacketUpdateSelectedSpells::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdatePlayerTarget.class, nextID())
                .encoder(PacketUpdatePlayerTarget::toBytes)
                .decoder(PacketUpdatePlayerTarget::new) // buf -> new PacketUpdateMagicka(buf)
                .consumer(PacketUpdatePlayerTarget::handle)
                .add();

        INSTANCE.messageBuilder(PacketCastSpell.class, nextID())
                .encoder(PacketCastSpell::toBytes)
                .decoder(PacketCastSpell::new) // buf -> new PacketUpdateMagicka(buf)
                .consumer(PacketCastSpell::handle)
                .add();
    }

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
    }

//    public static void serverToClient(AbstractMessage message, PlayerEntity player) {
//        INSTANCE.sendTo(message, ((ServerPlayerEntity) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
//    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}