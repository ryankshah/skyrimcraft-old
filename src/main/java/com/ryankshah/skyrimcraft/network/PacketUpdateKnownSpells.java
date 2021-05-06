package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class PacketUpdateKnownSpells
{
    private List<ISpell> knownSpells = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger();

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
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("PacketUpdateKnownSpells received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketUpdateKnownSpells context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            PlayerEntity player = Minecraft.getInstance().player;
            player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setKnownSpells(this.knownSpells);
            });
        });
        return true;
    }
}