package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class PacketUpdateShoutCooldowns
{
    private Map<ISpell, Float> shoutsAndCooldowns = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();

    public PacketUpdateShoutCooldowns(PacketBuffer buf) {
        CompoundNBT shoutsAndCooldownsNBT = buf.readNbt();
        for(String s : shoutsAndCooldownsNBT.getAllKeys()) {
            shoutsAndCooldowns.put(SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(s)), shoutsAndCooldownsNBT.getFloat(s));
        }
    }

    public PacketUpdateShoutCooldowns(Map<ISpell, Float> shoutsAndCooldowns) {
        this.shoutsAndCooldowns = shoutsAndCooldowns;
    }

    public void toBytes(PacketBuffer buf) {
        CompoundNBT nbt = new CompoundNBT();
        for (Map.Entry<ISpell, Float> entry : shoutsAndCooldowns.entrySet()) {
            nbt.put(entry.getKey().getRegistryName().toString(), FloatNBT.valueOf(entry.getValue()));
        }
        buf.writeNbt(nbt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("PacketUpdateShoutCooldowns received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketUpdateShoutCooldowns context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setShoutsWithCooldowns(shoutsAndCooldowns);
            });
        });
        return true;
    }
}