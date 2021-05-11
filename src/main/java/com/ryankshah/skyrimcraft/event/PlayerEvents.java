package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.character.PacketAddToMapFeaturesOnClient;
import com.ryankshah.skyrimcraft.network.character.PacketUpdatePlayerTargetOnServer;
import com.ryankshah.skyrimcraft.network.spell.PacketUpdateShoutCooldownOnServer;
import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.util.MapFeature;
import com.ryankshah.skyrimcraft.worldgen.structure.ModStructures;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
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
    @SubscribeEvent
    public static void onEntityHit(AttackEntityEvent event) {
        PlayerEntity playerEntity = event.getPlayer();

        if(event.getTarget() != null && event.getTarget() instanceof LivingEntity) {
            LivingEntity targetEntity = (LivingEntity) event.getTarget();

            playerEntity.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                if(targetEntity.isAlive()) {
                    Networking.sendToServer(new PacketUpdatePlayerTargetOnServer(targetEntity));
                } else {
//                    if(cap.getTargetingEntities().contains(targetEntity.getId()))
//                        Networking.sendToServer(new PacketRemoveTargetingEntity(targetEntity));
                    Networking.sendToServer(new PacketUpdatePlayerTargetOnServer((LivingEntity) null));
                }
            });

        }
    }

    public static float MANA_REGEN_RATE = 0.005f;

    @SubscribeEvent
    public static void onClientPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity playerEntity = event.player;
        ISkyrimPlayerData cap = playerEntity.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("playerevents playertick"));
        // Check we're only doing the updates at the end of the tick phase
        if(event.phase == TickEvent.Phase.END) {
            for(Map.Entry<ISpell, Float> entry : cap.getShoutsAndCooldowns().entrySet()) {
                if(entry.getValue() < 0f)
                    Networking.sendToServer(new PacketUpdateShoutCooldownOnServer(entry.getKey(), 0f));
                if(entry.getValue() > 0f)
                    Networking.sendToServer(new PacketUpdateShoutCooldownOnServer(entry.getKey(), cap.getShoutCooldown(entry.getKey())-0.05f));
            }

            if(cap.getMagicka() < cap.getMaxMagicka())
                cap.replenishMagicka(MANA_REGEN_RATE);

            if(playerEntity instanceof ServerPlayerEntity && event.side == LogicalSide.SERVER) {
                ServerPlayerEntity player = (ServerPlayerEntity) playerEntity;
                ServerWorld world = (ServerWorld) player.level;
                List<MapFeature> playerMapFeatures = cap.getMapFeatures();

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
                            MapFeature mapFeature = new MapFeature(UUID.randomUUID(), structure.getRegistryName(), featureStartPos);
                            if (playerMapFeatures.stream().noneMatch(feature -> feature.equals(mapFeature))) {
                                Networking.sendToClient(new PacketAddToMapFeaturesOnClient(mapFeature), player);
                            }
                        }
                    }
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
}