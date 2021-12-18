package com.ryankshah.skyrimcraft.network.character;

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

public class PacketUpdateExtraStats
{
    private static final Logger LOGGER = LogManager.getLogger();
    private float magicka, health, stamina;

    public PacketUpdateExtraStats(FriendlyByteBuf buf) {
        magicka = buf.readFloat();
        health = buf.readFloat();
        stamina = buf.readFloat();
    }

    public PacketUpdateExtraStats(float magicka, float health, float stamina) {
        this.magicka = magicka;
        this.health = health;
        this.stamina = stamina;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(magicka);
        buf.writeFloat(health);
        buf.writeFloat(stamina);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("PacketUpdateExtraStats received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketUpdateExtraStats context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setExtraMaxMagicka(magicka);
                cap.setExtraMaxHealth(health);
                cap.setExtraMaxStamina(stamina);
            });
        });
        return true;
    }
}