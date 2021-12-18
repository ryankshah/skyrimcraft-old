package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.render.RaceLayerRenderer;
import com.ryankshah.skyrimcraft.character.render.SpectralLayerRenderer;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.item.SkyrimTwoHandedWeapon;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.character.PacketAddToCompassFeaturesOnClient;
import com.ryankshah.skyrimcraft.network.character.PacketOpenCharacterCreationScreen;
import com.ryankshah.skyrimcraft.network.spell.PacketUpdateShoutCooldownOnServer;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import com.ryankshah.skyrimcraft.util.ModAttributes;
import com.ryankshah.skyrimcraft.worldgen.structure.ModStructures;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
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

    // TODO: work on resting and waiting mechanic
//    @SubscribeEvent
//    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
//
//    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent event) {
        LocalPlayer player = (LocalPlayer)event.getPlayer();
        if(!hasLayer) {
            event.getRenderer().addLayer(new SpectralLayerRenderer(event.getRenderer(), event.getPackedLight()));
            event.getRenderer().addLayer(new RaceLayerRenderer(event.getRenderer()));
            hasLayer = true;
        }

        if ((player.hasEffect(ModEffects.SPECTRAL.get()) || player.hasEffect(ModEffects.ETHEREAL.get())) && !flag) {
            flag = true;
            player.setInvisible(true);
            flag = false;
        } else {
            player.setInvisible(player.hasEffect(MobEffects.INVISIBILITY));
        }

        if(player.getMainHandItem().getItem() instanceof SkyrimTwoHandedWeapon) {
            event.getRenderer().getModel().rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player playerEntity = event.player;
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

            if(playerEntity instanceof ServerPlayer && event.side == LogicalSide.SERVER) {
                ServerPlayer player = (ServerPlayer) playerEntity;
                ServerLevel world = (ServerLevel) player.level;
                List<CompassFeature> playerCompassFeatures = cap.getCompassFeatures();

                // TODO: see below...
//                if(!PositionTrigger.Instance.located(LocationPredicate.inFeature(Structure.VILLAGE)).matches(world, player.getX(), player.getY(), player.getZ())) {
//                    return;
//                }

                // TODO: We should do this check after we do the player bounds check...
                for (StructureFeature<?> structure : ForgeRegistries.STRUCTURE_FEATURES.getValues()) {
                    if (structure == StructureFeature.VILLAGE || structure == StructureFeature.NETHER_BRIDGE
                            || structure == ModStructures.SHOUT_WALL.get() || structure == StructureFeature.MINESHAFT
                            || structure == StructureFeature.SHIPWRECK) {
                        BlockPos featureStartPos = locateFeatureStartChunkFromPlayerBlockPos(world, player.blockPosition(), structure);
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

    private static BlockPos locateFeatureStartChunkFromPlayerBlockPos(ServerLevel world, BlockPos pos, StructureFeature<?> feature) {
        // use 2 since based on min spacing, or we can use 7 in case user makes village spacing at every chunk..
        BlockPos blockpos1 = world.findNearestMapFeature(feature, pos, 2, true);
        if (blockpos1 != null) {
            return blockpos1;
        } else {
            return null;
        }
    }

    // Open the character creation screen if first login / world created
    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        ISkyrimPlayerData cap = player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("player events logged in event"));
        if(!cap.hasSetup()) {
            Networking.sendToClient(new PacketOpenCharacterCreationScreen(cap.hasSetup()), (ServerPlayer) player);
            cap.setHasSetup(true); // TODO: ensure this always syncs to the client as well..
        }
    }
}