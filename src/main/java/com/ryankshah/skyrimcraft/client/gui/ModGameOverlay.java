package com.ryankshah.skyrimcraft.client.gui;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, value = Dist.CLIENT)
public class ModGameOverlay
{
    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Pre event) {
        if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH)
            event.setCanceled(true);
        if(event.getType() == RenderGameOverlayEvent.ElementType.FOOD)
            event.setCanceled(true);

        int width = event.getWindow().getScaledWidth();
        int height = event.getWindow().getScaledHeight();

        new SkyrimIngameGui(event.getMatrixStack(), width, height);
    }
}