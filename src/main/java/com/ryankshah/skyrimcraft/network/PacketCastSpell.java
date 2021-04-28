package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketCastSpell
{
    private ISpell spell;

    public PacketCastSpell(PacketBuffer buf) {
        ResourceLocation rl = buf.readResourceLocation();
        this.spell = SpellRegistry.SPELLS_REGISTRY.get().getValue(rl);
    }

    public PacketCastSpell(ISpell spell) {
        this.spell = spell;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeResourceLocation(SpellRegistry.SPELLS_REGISTRY.get().getKey(spell));
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            spell.setCaster(ctx.get().getSender());
            spell.cast();
        });
        return true;
    }
}