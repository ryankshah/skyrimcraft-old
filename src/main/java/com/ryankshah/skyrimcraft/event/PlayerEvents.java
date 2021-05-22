package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.render.SpectralLayerRenderer;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.character.PacketAddToCompassFeaturesOnClient;
import com.ryankshah.skyrimcraft.network.character.PacketOpenCharacterCreationScreen;
import com.ryankshah.skyrimcraft.network.spell.PacketUpdateShoutCooldownOnServer;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import com.ryankshah.skyrimcraft.util.ModAttributes;
import com.ryankshah.skyrimcraft.worldgen.structure.ModStructures;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents
{
    public static boolean hasLayer = false, flag = false;

//    @SubscribeEvent
//    public static void playerLevelChangeEvent(PlayerXpEvent.LevelChange event) {
//        SkyrimIngameGui.newLevel = event.getPlayer().experienceLevel;
//        SkyrimIngameGui.levelUpRenderTime = 200;
//    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent event) {
        ClientPlayerEntity player = (ClientPlayerEntity)event.getPlayer();
        if(!hasLayer) {
            event.getRenderer().addLayer(new SpectralLayerRenderer(event.getRenderer(), event.getLight()));
            hasLayer = true;
        }

        if ((player.hasEffect(ModEffects.SPECTRAL.get()) || player.hasEffect(ModEffects.ETHEREAL.get())) && !flag) {
            flag = true;
            player.setInvisible(true);
            flag = false;
        } else {
            player.setInvisible(player.hasEffect(Effects.INVISIBILITY));
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity playerEntity = event.player;
        if(!playerEntity.isAlive())
            return;

        ISkyrimPlayerData cap = playerEntity.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("playerevents playertick"));
        // Check we're only doing the updates at the end of the tick phase
        if(event.phase == TickEvent.Phase.END) {
            if(!cap.getShoutsAndCooldowns().isEmpty()) {
                for (Map.Entry<ISpell, Float> entry : cap.getShoutsAndCooldowns().entrySet()) {
                    if (entry.getValue() <= 0f)
                        Networking.sendToServer(new PacketUpdateShoutCooldownOnServer(entry.getKey(), 0f));
                    if (entry.getValue() > 0f)
                        Networking.sendToServer(new PacketUpdateShoutCooldownOnServer(entry.getKey(), cap.getShoutCooldown(entry.getKey()) - 0.05f));
                }
            }

            if(!playerEntity.hasEffect(ModEffects.REGEN_MAGICKA.get()))
                playerEntity.getAttribute(ModAttributes.MAGICKA_REGEN.get()).removeModifiers();

            if(cap.getMagicka() < cap.getMaxMagicka())
                cap.replenishMagicka(0.005f * (float)playerEntity.getAttributeValue(ModAttributes.MAGICKA_REGEN.get()));

            if(playerEntity instanceof ServerPlayerEntity && event.side == LogicalSide.SERVER) {
                ServerPlayerEntity player = (ServerPlayerEntity) playerEntity;
                ServerWorld world = (ServerWorld) player.level;
                List<CompassFeature> playerCompassFeatures = cap.getCompassFeatures();

                // TODO: see below...
//                if(!PositionTrigger.Instance.located(LocationPredicate.inFeature(Structure.VILLAGE)).matches(world, player.getX(), player.getY(), player.getZ())) {
//                    return;
//                }

                // TODO: We should do this check after we do the player bounds check...
                for (Structure<?> structure : ForgeRegistries.STRUCTURE_FEATURES.getValues()) {
                    if (structure == Structure.VILLAGE || structure == Structure.NETHER_BRIDGE
                            || structure == ModStructures.SHOUT_WALL.get() || structure == Structure.MINESHAFT
                            || structure == Structure.SHIPWRECK) {
                        ChunkPos featureStartPos = locateFeatureStartChunkFromPlayerBlockPos(world, player.blockPosition(), structure);
                        if (featureStartPos != null) {
                            CompassFeature compassFeature = new CompassFeature(UUID.randomUUID(), structure.getRegistryName(), featureStartPos);
                            if (playerCompassFeatures.stream().noneMatch(feature -> feature.equals(compassFeature))) {
                                Networking.sendToClient(new PacketAddToCompassFeaturesOnClient(compassFeature), player);
                            }
                        }
                    }
                }

                // check ethereal
                if(!player.hasEffect(ModEffects.ETHEREAL.get())) {
                    if(player.isInvulnerable() && (!player.isCreative() || !player.isSpectator()))
                        player.setInvulnerable(false);
                }
            }
        }
    }

    private static ChunkPos locateFeatureStartChunkFromPlayerBlockPos(ServerWorld world, BlockPos pos, Structure<?> feature) {
        // use 2 since based on min spacing, or we can use 7 in case user makes village spacing at every chunk..
        BlockPos blockpos1 = world.findNearestMapFeature(feature, pos, 2, true);
        if (blockpos1 != null) {
            return new ChunkPos(blockpos1.getX(), blockpos1.getZ());
        } else {
            return null;
        }
    }

    // Open the character creation screen if first login / world created
    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ISkyrimPlayerData cap = event.getPlayer().getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("player events logged in event"));
        if(!cap.hasSetup()) {
            cap.setHasSetup(true);
            Networking.sendToClient(new PacketOpenCharacterCreationScreen(cap.hasSetup()), (ServerPlayerEntity) event.getPlayer());
        }
    }
}