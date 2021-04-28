package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
                    cap.setCurrentTarget(targetEntity);
                } else cap.setCurrentTarget(null);
            });

        }
    }
}