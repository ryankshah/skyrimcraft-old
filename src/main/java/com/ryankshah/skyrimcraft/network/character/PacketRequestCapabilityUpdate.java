package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.spell.PacketUpdateKnownSpells;
import com.ryankshah.skyrimcraft.network.spell.PacketUpdateMagicka;
import com.ryankshah.skyrimcraft.network.spell.PacketUpdateSelectedSpells;
import com.ryankshah.skyrimcraft.network.spell.PacketUpdateShoutCooldowns;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class PacketRequestCapabilityUpdate
{
    private static final Logger LOGGER = LogManager.getLogger();

//    private List<ISpell> knownSpells;
//    private Map<Integer, ISpell> selectedSpells = new HashMap<>();
//    private float magicka;
//    private LivingEntity playerTarget;
//    private float shoutCooldown;

    public PacketRequestCapabilityUpdate(FriendlyByteBuf buf) {
//        // Known spells
//        int size = buf.readInt();
//        for(int i = 0; i < size; i++) {
//            ResourceLocation rl = buf.readResourceLocation();
//            this.knownSpells.add(SpellRegistry.SPELLS_REGISTRY.get().getValue(rl));
//        }
//
//        // Selected spells
//        CompoundNBT nbt = buf.readNbt();
//
//        String spell0RL = nbt.getString("selected0");
//        String spell1RL = nbt.getString("selected1");
//
//        this.selectedSpells.put(0, spell0RL.equals("null") ? null : SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(spell0RL)));
//        this.selectedSpells.put(1, spell0RL.equals("null") ? null : SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(spell1RL)));
//
//        int id = buf.readVarInt();
//        if(id != -1) {
//            Entity ent = ClientUtil.getClientWorld().getEntity(id);
//            playerTarget = ent instanceof LivingEntity ? (LivingEntity) ent : null;
//        } else playerTarget = null;
//
//        this.magicka = buf.readFloat();
//        this.shoutCooldown = buf.readFloat();
    }

//    public PacketRequestCapabilityUpdate(List<ISpell> knownSpells, Map<Integer, ISpell> selectedSpells, LivingEntity playerTarget, float magicka, float shoutCooldown) {
//        this.knownSpells = knownSpells;
//        this.selectedSpells = selectedSpells;
//        this.playerTarget = playerTarget;
//        this.magicka = magicka;
//        this.shoutCooldown = shoutCooldown;
//    }

    public PacketRequestCapabilityUpdate() {}

    public void toBytes(FriendlyByteBuf buf) {
//        // Known spells
//        buf.writeInt(knownSpells.size());
//        for(ISpell spell : knownSpells) {
//            buf.writeResourceLocation(SpellRegistry.SPELLS_REGISTRY.get().getKey(spell));
//        }
//
//        // Selected spells
//        CompoundNBT nbt = new CompoundNBT();
//        for (Map.Entry<Integer, ISpell> entry : selectedSpells.entrySet()) {
//            nbt.putString("selected" + entry.getKey(), entry.getValue() == null ? "null" : entry.getValue().getRegistryName().toString());
//        }
//        buf.writeNbt(nbt);
//
//        int id = playerTarget != null ? playerTarget.getId() : -1;
//        buf.writeVarInt(id);
//
//        buf.writeFloat(magicka);
//        buf.writeFloat(shoutCooldown);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PacketRequestCapabilityUpdate received on wrong side:" + sideReceived);
            return false;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that ServerPlayerEntity exists
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final ServerPlayer sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("sendingPlayer was null when PacketRequestCapabilityUpdate was received");
            return false;
        }

        context.enqueueWork(() -> {
            sendingPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                Networking.sendToClient(new PacketUpdateMagicka(cap.getMagicka()), sendingPlayer);
                Networking.sendToClient(new PacketUpdateKnownSpells(cap.getKnownSpells()), sendingPlayer);
                Networking.sendToClient(new PacketUpdateSelectedSpells(cap.getSelectedSpells()), sendingPlayer);
                Networking.sendToClient(new PacketUpdatePlayerTarget((LivingEntity) null), sendingPlayer);
                Networking.sendToClient(new PacketUpdateShoutCooldowns(cap.getShoutsAndCooldowns()), sendingPlayer);
            });
        });
        return true;
    }
}