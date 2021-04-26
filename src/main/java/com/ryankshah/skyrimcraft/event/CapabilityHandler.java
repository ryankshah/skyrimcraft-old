package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.capability.IMagicka;
import com.ryankshah.skyrimcraft.capability.IMagickaProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.PacketUpdateMagicka;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityHandler
{
    public static final ResourceLocation MAGICKA_CAPABILITY = new ResourceLocation(Skyrimcraft.MODID, "magicka");


    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(!(event.getObject() instanceof PlayerEntity))
            return;
        IMagickaProvider provider = new IMagickaProvider((PlayerEntity)event.getObject());
        event.addCapability(MAGICKA_CAPABILITY, provider);
        event.addListener(provider::invalidate);
    }

    @SubscribeEvent
    public static void onloginPlayer(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getEntity() instanceof ServerPlayerEntity) {
            IMagicka cap = ((ServerPlayerEntity)event.getEntity()).getCapability(IMagickaProvider.MAGICKA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("at login"));
            Networking.sendToClient(new PacketUpdateMagicka(cap.getMagicka()), (ServerPlayerEntity)event.getEntity());
        }
    }
}