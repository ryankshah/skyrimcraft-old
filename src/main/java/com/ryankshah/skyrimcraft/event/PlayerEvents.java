package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.PacketUpdatePlayerTarget;
import com.ryankshah.skyrimcraft.network.PacketUpdatePlayerTargetOnServer;
import com.ryankshah.skyrimcraft.network.PacketUpdateShoutCooldownOnServer;
import com.ryankshah.skyrimcraft.spell.ISpell;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

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
                } else Networking.sendToServer(new PacketUpdatePlayerTargetOnServer((LivingEntity) null));
            });

        }
    }

    @SubscribeEvent
    public static void onClientPlayerTick(TickEvent.PlayerTickEvent event) {
        // Check we're only ticking on the client for the cooldown
        if(event.phase == TickEvent.Phase.END) {
            PlayerEntity playerEntity = event.player;
            playerEntity.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                for(Map.Entry<ISpell, Float> entry : cap.getShoutsAndCooldowns().entrySet()) {
                    if(entry.getValue() < 0f)
                        Networking.sendToServer(new PacketUpdateShoutCooldownOnServer(entry.getKey(), 0f));
                    if(entry.getValue() > 0f)
                        Networking.sendToServer(new PacketUpdateShoutCooldownOnServer(entry.getKey(), cap.getShoutCooldown(entry.getKey())-0.05f));
                }
//                if(cap.getShoutCooldown() < 0f)
//                    Networking.sendToServer(new PacketUpdateShoutCooldownOnServer(0f));
//
//                if (cap.getShoutCooldown() > 0f)
//                    Networking.sendToServer(new PacketUpdateShoutCooldownOnServer(cap.getShoutCooldown() - 0.05f)); // subtract 1/20 (20 ticks per second) = 0.05
            });
        }
    }
}