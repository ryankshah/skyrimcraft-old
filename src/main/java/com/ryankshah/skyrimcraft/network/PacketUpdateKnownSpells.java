package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PacketUpdateKnownSpells
{
    private List<ISpell> knownSpells = new ArrayList<>();

    public PacketUpdateKnownSpells(PacketBuffer buf) {
        int size = buf.readInt();
        for(int i = 0; i < size; i++) {
            ResourceLocation rl = buf.readResourceLocation();
            this.knownSpells.add(SpellRegistry.SPELLS_REGISTRY.get().getValue(rl));
        }
    }

    public PacketUpdateKnownSpells(List<ISpell> knownSpells) {
        this.knownSpells = knownSpells;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(knownSpells.size());
        for(ISpell spell : knownSpells) {
            buf.writeResourceLocation(SpellRegistry.SPELLS_REGISTRY.get().getKey(spell));
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setKnownSpells(this.knownSpells);
            });
        });
        return true;
    }
}