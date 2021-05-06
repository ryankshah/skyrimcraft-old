package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import javafx.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Supplier;

public class PacketUpdateSelectedSpells
{
    private Map<Integer, ISpell> selectedSpells = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();

    public PacketUpdateSelectedSpells(PacketBuffer buf) {
        CompoundNBT nbt = buf.readNbt();

        String spell0RL = nbt.getString("selected0");
        String spell1RL = nbt.getString("selected1");

        this.selectedSpells.put(0, spell0RL.equals("null") ? null : SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(spell0RL)));
        this.selectedSpells.put(1, spell0RL.equals("null") ? null : SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(spell1RL)));
    }

    public PacketUpdateSelectedSpells(Map<Integer, ISpell> selectedSpells) {
        this.selectedSpells = selectedSpells;
    }

    public void toBytes(PacketBuffer buf) {
        CompoundNBT nbt = new CompoundNBT();
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
        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
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