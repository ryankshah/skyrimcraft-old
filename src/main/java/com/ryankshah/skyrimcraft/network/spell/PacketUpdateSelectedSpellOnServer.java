package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
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

public class PacketUpdateSelectedSpellOnServer
{
    private static final Logger LOGGER = LogManager.getLogger();
    private int pos = 0;
    private ISpell spell;

    public PacketUpdateSelectedSpellOnServer(PacketBuffer buf) {
        pos = buf.readInt();
        String s = buf.readUtf();

        if(s.equals("null"))
            this.spell = null;
        else
            this.spell = SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(s));
    }

    public PacketUpdateSelectedSpellOnServer(int pos, ISpell spell) {
        this.pos = pos;
        this.spell = spell;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(pos);
        if(spell == null) {
            buf.writeUtf("null");
        }else {
            String spellRL = SpellRegistry.SPELLS_REGISTRY.get().getKey(spell).toString();
            buf.writeUtf(spellRL);
        }
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

        final ServerPlayerEntity sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("ServerPlayerEntity was null when AddSpellToServer was received");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            ctx.get().getSender().getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                System.out.println(cap.getSelectedSpells());
                cap.setSelectedSpell(pos, spell);
                System.out.println(cap.getSelectedSpells());
                Networking.sendToClient(new PacketUpdateSelectedSpells(cap.getSelectedSpells()), sendingPlayer);
            });
        });
        return true;
    }
}