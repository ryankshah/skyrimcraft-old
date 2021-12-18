package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
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

public class PacketUpdateShoutCooldowns
{
    private Map<ISpell, Float> shoutsAndCooldowns = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();

    public PacketUpdateShoutCooldowns(FriendlyByteBuf buf) {
        CompoundTag shoutsAndCooldownsNBT = buf.readNbt();
        for(String s : shoutsAndCooldownsNBT.getAllKeys()) {
            shoutsAndCooldowns.put(SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(s)), shoutsAndCooldownsNBT.getFloat(s));
        }
    }

    public PacketUpdateShoutCooldowns(Map<ISpell, Float> shoutsAndCooldowns) {
        this.shoutsAndCooldowns = shoutsAndCooldowns;
    }

    public void toBytes(FriendlyByteBuf buf) {
        CompoundTag nbt = new CompoundTag();
        for (Map.Entry<ISpell, Float> entry : shoutsAndCooldowns.entrySet()) {
            nbt.put(entry.getKey().getRegistryName().toString(), FloatTag.valueOf(entry.getValue()));
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
        Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
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