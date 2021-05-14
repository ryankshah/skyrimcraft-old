package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Supplier;

public class PacketUpdateMagickaRegenModifierOnClient
{
    private float modifier;
    private static final Logger LOGGER = LogManager.getLogger();

    public PacketUpdateMagickaRegenModifierOnClient(PacketBuffer buf) {
        this.modifier = buf.readFloat();
    }

    public PacketUpdateMagickaRegenModifierOnClient(float modifier) {
        this.modifier = modifier;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(modifier);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("PacketUpdateMagickaRegenModifierOnClient received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketUpdateMagickaRegenModifierOnClient context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setMagickaRegenModifier(modifier);
            });
        });
        return true;
    }
}