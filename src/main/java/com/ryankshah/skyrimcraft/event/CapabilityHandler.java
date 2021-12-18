package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.character.PacketUpdateCharacter;
import com.ryankshah.skyrimcraft.network.character.PacketUpdateCompassFeatures;
import com.ryankshah.skyrimcraft.network.character.PacketUpdateExtraStats;
import com.ryankshah.skyrimcraft.network.character.PacketUpdatePlayerTarget;
import com.ryankshah.skyrimcraft.network.spell.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
// TODO: Change all individual packets for persistance and syncing to use a single packet for all
//       as this will save bandwidth etc.
public class CapabilityHandler
{
    public static final ResourceLocation SKYRIM_PLAYER_DATA = new ResourceLocation(Skyrimcraft.MODID, "skyrim_player_data");

    @SubscribeEvent
    public static void capabilityRegister(RegisterCapabilitiesEvent event) {
        event.register(ISkyrimPlayerData.class);
    }

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(!(event.getObject() instanceof Player))
            return;
        ISkyrimPlayerDataProvider provider = new ISkyrimPlayerDataProvider((Player)event.getObject());
        event.addCapability(SKYRIM_PLAYER_DATA, provider);
        event.addListener(provider::invalidate);
    }

    @SubscribeEvent
    public static void onLoginPlayer(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getEntity() instanceof ServerPlayer) {
            ISkyrimPlayerData cap = event.getEntity().getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("at logged in event"));
            Networking.sendToClient(new PacketUpdateExtraStats(cap.getExtraMaxMagicka(), cap.getExtraMaxHealth(), cap.getExtraMaxStamina()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateMagicka(cap.getMagicka()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateKnownSpells(cap.getKnownSpells()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateSelectedSpells(cap.getSelectedSpells()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdatePlayerTarget((LivingEntity) null), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateShoutCooldowns(cap.getShoutsAndCooldowns()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateCompassFeatures(cap.getCompassFeatures()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateMagickaRegenModifierOnClient(cap.getMagickaRegenModifier()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateCharacter(cap.getCharacterXp(), cap.getSkills(), cap.getRace()), (ServerPlayer) event.getEntity());
        }
    }

//    @SubscribeEvent
//    public static void onLogoutPlayer(PlayerEvent.PlayerLoggedOutEvent event) {
//    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(event.getEntity() instanceof ServerPlayer) {
            ISkyrimPlayerData cap = event.getEntity().getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("at logged in event"));
            Networking.sendToClient(new PacketUpdateExtraStats(cap.getExtraMaxMagicka(), cap.getExtraMaxHealth(), cap.getExtraMaxStamina()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateMagicka(cap.getMagicka()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateKnownSpells(cap.getKnownSpells()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateSelectedSpells(cap.getSelectedSpells()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdatePlayerTarget((LivingEntity) null), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateShoutCooldowns(cap.getShoutsAndCooldowns()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateCompassFeatures(cap.getCompassFeatures()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateMagickaRegenModifierOnClient(cap.getMagickaRegenModifier()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateCharacter(cap.getCharacterXp(), cap.getSkills(), cap.getRace()), (ServerPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if(event.getPlayer() instanceof ServerPlayer) {
            ISkyrimPlayerData cap = event.getEntity().getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("at logged in event"));
            Networking.sendToClient(new PacketUpdateExtraStats(cap.getExtraMaxMagicka(), cap.getExtraMaxHealth(), cap.getExtraMaxStamina()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateMagicka(cap.getMagicka()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateKnownSpells(cap.getKnownSpells()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateSelectedSpells(cap.getSelectedSpells()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdatePlayerTarget((LivingEntity) null), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateShoutCooldowns(cap.getShoutsAndCooldowns()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateCompassFeatures(cap.getCompassFeatures()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateMagickaRegenModifierOnClient(cap.getMagickaRegenModifier()), (ServerPlayer)event.getEntity());
            Networking.sendToClient(new PacketUpdateCharacter(cap.getCharacterXp(), cap.getSkills(), cap.getRace()), (ServerPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if(event.isWasDeath()) { // check death and not returning from end
            if(event.getOriginal() instanceof ServerPlayer) {
                Player originalPlayer = event.getOriginal();
                ServerPlayer newPlayer = (ServerPlayer)event.getPlayer();
                ISkyrimPlayerData originalPlayerCapability = ((ServerPlayer) originalPlayer).getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("at clone event"));

                // For the new player add the existing cap data
                newPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((ISkyrimPlayerData newCap) -> {
                    newCap.setKnownSpells(originalPlayerCapability.getKnownSpells());
                    newCap.setSelectedSpells(originalPlayerCapability.getSelectedSpells());
                    newCap.setCompassFeatures(originalPlayerCapability.getCompassFeatures());
                    newCap.setMagickaRegenModifier(1.0F); // reset to default magicka regen modifier (TODO: Is this influenced by perks?)
                    newCap.setCharacterXp(originalPlayerCapability.getCharacterXp());
                    newCap.setSkills(originalPlayerCapability.getSkills());
                    newCap.setRace(originalPlayerCapability.getRace());
                });
            }
        }
    }
}