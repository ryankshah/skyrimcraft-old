package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.client.gui.CharacterCreationScreen;
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

public class PacketOpenCharacterCreationScreen
{
    private static final Logger LOGGER = LogManager.getLogger();

    private boolean hasSetup;

    public PacketOpenCharacterCreationScreen(PacketBuffer buf) {
        hasSetup = buf.readBoolean();
    }

    public PacketOpenCharacterCreationScreen(boolean hasSetup) {
        this.hasSetup = hasSetup;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(hasSetup);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("PacketOpenCharacterCreationScreen received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketOpenCharacterCreationScreen context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setHasSetup(hasSetup);
            });
            Minecraft.getInstance().setScreen(new CharacterCreationScreen());
        });

        return true;
    }
}