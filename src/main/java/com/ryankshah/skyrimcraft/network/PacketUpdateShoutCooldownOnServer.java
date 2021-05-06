package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class PacketUpdateShoutCooldownOnServer
{
    private ISpell shout;
    private float cooldown;
    private static final Logger LOGGER = LogManager.getLogger();

    public PacketUpdateShoutCooldownOnServer(PacketBuffer buf) {
        ResourceLocation rl = buf.readResourceLocation();
        this.shout = SpellRegistry.SPELLS_REGISTRY.get().getValue(rl);
        this.cooldown = buf.readFloat();
    }

    public PacketUpdateShoutCooldownOnServer(ISpell shout, float cooldown) {
        this.shout = shout;
        this.cooldown = cooldown;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeResourceLocation(SpellRegistry.SPELLS_REGISTRY.get().getKey(shout));
        buf.writeFloat(cooldown);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PacketUpdateShoutCooldownOnServer received on wrong side:" + sideReceived);
            return false;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that ServerPlayerEntity exists
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final ServerPlayerEntity sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("ServerPlayerEntity was null when PacketUpdateShoutCooldownOnServer was received");
            return false;
        }

        context.enqueueWork(() -> {
            sendingPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setShoutCooldown(shout, cooldown);
                Networking.sendToClient(new PacketUpdateShoutCooldowns(cap.getShoutsAndCooldowns()), sendingPlayer);
            });
        });
        return true;
    }
}
