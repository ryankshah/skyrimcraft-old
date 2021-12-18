package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class PacketUpdateSelectedSpells
{
    private Map<Integer, ISpell> selectedSpells = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();

    public PacketUpdateSelectedSpells(FriendlyByteBuf buf) {
        CompoundTag nbt = buf.readNbt();

        String spell0RL = nbt.getString("selected0");
        String spell1RL = nbt.getString("selected1");

        this.selectedSpells.put(0, spell0RL.equals("null") ? null : SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(spell0RL)));
        this.selectedSpells.put(1, spell0RL.equals("null") ? null : SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(spell1RL)));
    }

    public PacketUpdateSelectedSpells(Map<Integer, ISpell> selectedSpells) {
        this.selectedSpells = selectedSpells;
    }

    public void toBytes(FriendlyByteBuf buf) {
        CompoundTag nbt = new CompoundTag();
        for (Map.Entry<Integer, ISpell> entry : selectedSpells.entrySet()) {
            nbt.putString("selected" + entry.getKey(), entry.getValue() == null ? "null" : entry.getValue().getRegistryName().toString());
        }
        buf.writeNbt(nbt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("PacketUpdateSelectedSpells received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketUpdateSelectedSpells context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setSelectedSpells(this.selectedSpells);
            });
        });
        return true;
    }
}