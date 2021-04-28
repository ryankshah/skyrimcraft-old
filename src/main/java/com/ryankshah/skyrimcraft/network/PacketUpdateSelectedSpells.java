package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class PacketUpdateSelectedSpells
{
    private ISpell[] selectedSpells = new ISpell[2];

    public PacketUpdateSelectedSpells(PacketBuffer buf) {
        int size = buf.readInt();
        for(int i = 0; i < size; i++) {
            this.selectedSpells[i] = SpellRegistry.SPELLS_REGISTRY.get().getValue(buf.readResourceLocation());
        }
    }

    public PacketUpdateSelectedSpells(ISpell[] selectedSpells) {
        this.selectedSpells = selectedSpells;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(selectedSpells.length);
        for(ISpell spell : selectedSpells) {
            buf.writeResourceLocation(SpellRegistry.SPELLS_REGISTRY.get().getKey(spell));
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setSelectedSpells(this.selectedSpells);
            });
        });
        return true;
    }
}