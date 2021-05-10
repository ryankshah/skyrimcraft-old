package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityHandler
{
    public static final ResourceLocation SKYRIM_PLAYER_DATA = new ResourceLocation(Skyrimcraft.MODID, "skyrim_player_data");


    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(!(event.getObject() instanceof PlayerEntity))
            return;
        ISkyrimPlayerDataProvider provider = new ISkyrimPlayerDataProvider((PlayerEntity)event.getObject());
        event.addCapability(SKYRIM_PLAYER_DATA, provider);
        event.addListener(provider::invalidate);
    }

    @SubscribeEvent
    public static void onLoginPlayer(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getEntity() instanceof ServerPlayerEntity) {
            ISkyrimPlayerData cap = event.getEntity().getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("at logged in event"));
            //Networking.sendToServer(new PacketRequestCapabilityUpdate());
            Networking.sendToClient(new PacketUpdateMagicka(cap.getMagicka()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateKnownSpells(cap.getKnownSpells()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateSelectedSpells(cap.getSelectedSpells()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdatePlayerTarget((LivingEntity) null), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateShoutCooldowns(cap.getShoutsAndCooldowns()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateMapFeatures(cap.getMapFeatures()), (ServerPlayerEntity)event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onLogoutPlayer(PlayerEvent.PlayerLoggedOutEvent event) {
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(event.getEntity() instanceof ServerPlayerEntity) {
            ISkyrimPlayerData cap = event.getEntity().getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("at logged in event"));
            //Networking.sendToServer(new PacketRequestCapabilityUpdate());
            Networking.sendToClient(new PacketUpdateMagicka(cap.getMagicka()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateKnownSpells(cap.getKnownSpells()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateSelectedSpells(cap.getSelectedSpells()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdatePlayerTarget((LivingEntity) null), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateShoutCooldowns(cap.getShoutsAndCooldowns()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateMapFeatures(cap.getMapFeatures()), (ServerPlayerEntity)event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if(event.getPlayer() instanceof ServerPlayerEntity) {
            //Networking.sendToServer(new PacketRequestCapabilityUpdate());
            ISkyrimPlayerData cap = event.getEntity().getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("at logged in event"));
            //Networking.sendToServer(new PacketRequestCapabilityUpdate());
            Networking.sendToClient(new PacketUpdateMagicka(cap.getMagicka()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateKnownSpells(cap.getKnownSpells()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateSelectedSpells(cap.getSelectedSpells()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdatePlayerTarget((LivingEntity) null), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateShoutCooldowns(cap.getShoutsAndCooldowns()), (ServerPlayerEntity)event.getEntity());
        }
    }

    // TODO: On clone event we want to keep the known and selected spells -- eek!
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            if(event.getOriginal() instanceof ServerPlayerEntity) {
                PlayerEntity originalPlayer = event.getOriginal();
                ServerPlayerEntity newPlayer = (ServerPlayerEntity)event.getPlayer();
                ISkyrimPlayerData originalPlayerCapability = ((ServerPlayerEntity) originalPlayer).getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("at clone event"));

                // For the new player add the existing cap data
                newPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((ISkyrimPlayerData newCap) -> {
                    newCap.setKnownSpells(originalPlayerCapability.getKnownSpells());
                    newCap.setSelectedSpells(originalPlayerCapability.getSelectedSpells());
                });
            }
        }
    }
}