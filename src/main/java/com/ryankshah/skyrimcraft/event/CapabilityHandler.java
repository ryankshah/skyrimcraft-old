package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.character.PacketUpdateCharacter;
import com.ryankshah.skyrimcraft.network.character.PacketUpdateCompassFeatures;
import com.ryankshah.skyrimcraft.network.character.PacketUpdatePlayerTarget;
import com.ryankshah.skyrimcraft.network.spell.*;
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
            Networking.sendToClient(new PacketUpdateMagicka(cap.getMagicka()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateKnownSpells(cap.getKnownSpells()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateSelectedSpells(cap.getSelectedSpells()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdatePlayerTarget((LivingEntity) null), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateShoutCooldowns(cap.getShoutsAndCooldowns()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateCompassFeatures(cap.getCompassFeatures()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateMagickaRegenModifierOnClient(cap.getMagickaRegenModifier()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateCharacter(cap.getCharacterXp(), cap.getSkills()), (ServerPlayerEntity) event.getEntity());
        }
    }

//    @SubscribeEvent
//    public static void onLogoutPlayer(PlayerEvent.PlayerLoggedOutEvent event) {
//    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(event.getEntity() instanceof ServerPlayerEntity) {
            ISkyrimPlayerData cap = event.getEntity().getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("at logged in event"));
            Networking.sendToClient(new PacketUpdateMagicka(cap.getMagicka()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateKnownSpells(cap.getKnownSpells()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateSelectedSpells(cap.getSelectedSpells()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdatePlayerTarget((LivingEntity) null), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateShoutCooldowns(cap.getShoutsAndCooldowns()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateCompassFeatures(cap.getCompassFeatures()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateMagickaRegenModifierOnClient(cap.getMagickaRegenModifier()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateCharacter(cap.getCharacterXp(), cap.getSkills()), (ServerPlayerEntity) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if(event.getPlayer() instanceof ServerPlayerEntity) {
            ISkyrimPlayerData cap = event.getEntity().getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("at logged in event"));
            Networking.sendToClient(new PacketUpdateMagicka(cap.getMagicka()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateKnownSpells(cap.getKnownSpells()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateSelectedSpells(cap.getSelectedSpells()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdatePlayerTarget((LivingEntity) null), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateShoutCooldowns(cap.getShoutsAndCooldowns()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateCompassFeatures(cap.getCompassFeatures()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateMagickaRegenModifierOnClient(cap.getMagickaRegenModifier()), (ServerPlayerEntity)event.getEntity());
            Networking.sendToClient(new PacketUpdateCharacter(cap.getCharacterXp(), cap.getSkills()), (ServerPlayerEntity) event.getEntity());
        }
    }

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
                    newCap.setCompassFeatures(originalPlayerCapability.getCompassFeatures());
                    newCap.setMagickaRegenModifier(1.0F); // reset to default magicka regen modifier
                    newCap.setCharacterXp(originalPlayerCapability.getCharacterXp());
                    newCap.setSkills(originalPlayerCapability.getSkills());
                });
            }
        }
    }
}