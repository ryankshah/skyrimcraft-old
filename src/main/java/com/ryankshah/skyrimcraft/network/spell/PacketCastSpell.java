package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class PacketCastSpell
{

    private static final Logger LOGGER = LogManager.getLogger();
    private ISpell spell;

    public PacketCastSpell(FriendlyByteBuf buf) {
        ResourceLocation rl = buf.readResourceLocation();
        this.spell = SpellRegistry.SPELLS_REGISTRY.get().getValue(rl);
    }

    public PacketCastSpell(ISpell spell) {
        this.spell = spell;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(SpellRegistry.SPELLS_REGISTRY.get().getKey(spell));
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("AddSpellToServer received on wrong side:" + sideReceived);
            return false;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that ServerPlayerEntity exists
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final ServerPlayer sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("ServerPlayerEntity was null when AddSpellToServer was received");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            ctx.get().getSender().getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                spell.setCaster(ctx.get().getSender());
                spell.cast();
            });
        });
        return true;
    }
}