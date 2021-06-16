package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.advancement.TriggerManager;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class PacketAddToKnownSpells
{
    private static final Logger LOGGER = LogManager.getLogger();
    private ISpell spell;

    public PacketAddToKnownSpells(PacketBuffer buf) {
        ResourceLocation rl = buf.readResourceLocation();
        this.spell = SpellRegistry.SPELLS_REGISTRY.get().getValue(rl);
    }

    public PacketAddToKnownSpells(ISpell spellToAdd) {
        spell = spellToAdd;
    }

    public void toBytes(PacketBuffer buf) {
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

        final ServerPlayerEntity sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("PacketAddToKnownSpells was null when AddSpellToServer was received");
            return false;
        }

        context.enqueueWork(() -> {
            sendingPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.addToKnownSpells(spell);
                TriggerManager.SPELL_TRIGGERS.get(spell).trigger(sendingPlayer);
                sendingPlayer.getCommandSenderWorld().playSound(null, sendingPlayer.getX(), sendingPlayer.getY(), sendingPlayer.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundCategory.BLOCKS, 1f, 1f);
                Networking.sendToClient(new PacketUpdateKnownSpells(cap.getKnownSpells()), sendingPlayer);
            });
        });
        return true;
    }
}