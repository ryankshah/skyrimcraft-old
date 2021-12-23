package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Supplier;

public class PacketUpdateMagicka
{
    private float magicka;
    private static final Logger LOGGER = LogManager.getLogger();

    public PacketUpdateMagicka(FriendlyByteBuf buf) {
        this.magicka = buf.readFloat();
    }

    public PacketUpdateMagicka(float magicka) {
        this.magicka = magicka;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(magicka);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("PacketUpdateMagicka received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketUpdateMagicka context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setMagicka(magicka);
            });
        });
        return true;
    }
}