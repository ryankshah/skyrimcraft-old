package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.advancement.TriggerManager;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.network.Networking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class PacketAddToKnownSpells
{
    private static final Logger LOGGER = LogManager.getLogger();
    private ISpell spell;

    public PacketAddToKnownSpells(FriendlyByteBuf buf) {
        ResourceLocation rl = buf.readResourceLocation();
        this.spell = SpellRegistry.SPELLS_REGISTRY.get().getValue(rl);
    }

    public PacketAddToKnownSpells(ISpell spellToAdd) {
        spell = spellToAdd;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(SpellRegistry.SPELLS_REGISTRY.get().getKey(spell));
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PacketAddToKnownSpells received on wrong side:" + sideReceived);
            return false;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that ServerPlayerEntity exists
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final ServerPlayer sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("PacketAddToKnownSpells was null when AddSpellToServer was received");
            return false;
        }

        context.enqueueWork(() -> {
            sendingPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.addToKnownSpells(spell);
                TriggerManager.SPELL_TRIGGERS.get(spell).trigger(sendingPlayer);
                sendingPlayer.getCommandSenderWorld().playSound(null, sendingPlayer.getX(), sendingPlayer.getY(), sendingPlayer.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1f, 1f);
                Networking.sendToClient(new PacketUpdateKnownSpells(cap.getKnownSpells()), sendingPlayer);
            });
        });
        return true;
    }
}