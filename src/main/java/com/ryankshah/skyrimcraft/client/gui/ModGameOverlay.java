package com.ryankshah.skyrimcraft.client.gui;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, value = Dist.CLIENT)
public class ModGameOverlay
{
    // TODO: Check for boss health + info and use the render target display similar to other mobs.
    // TODO: Fix subtitle text position (if needed??)
    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.PreLayer event) {
        if (ClientUtil.getMinecraft().player == null) { return; }

//        if(event.getType() == RenderGameOverlayEvent.ElementType.BOSSHEALTH) {
//            event.getMatrixStack().translate(0, 1, 0);
//        }

        if (event.getOverlay() == ForgeIngameGui.BOSS_HEALTH_ELEMENT && !ClientUtil.getMinecraft().options.hideGui && !event.isCanceled()) {
            event.getMatrixStack().pushPose();
            event.getMatrixStack().translate(0, 28, 0);
            event.getMatrixStack().popPose();
        }

        if(event.getOverlay() == ForgeIngameGui.PLAYER_HEALTH_ELEMENT || event.getOverlay() == ForgeIngameGui.ARMOR_LEVEL_ELEMENT
            || event.getOverlay() == ForgeIngameGui.FOOD_LEVEL_ELEMENT || event.getOverlay() == ForgeIngameGui.AIR_LEVEL_ELEMENT
            || event.getOverlay() == ForgeIngameGui.EXPERIENCE_BAR_ELEMENT || event.getOverlay() == ForgeIngameGui.CROSSHAIR_ELEMENT)
            event.setCanceled(true);

//        int width = event.getWindow().getGuiScaledWidth();
//        int height = event.getWindow().getGuiScaledHeight();
//
//        // draw in pre
//        SkyrimIngameGui.render(event.getMatrixStack(), width, height, event.getPartialTicks());
    }

    @SubscribeEvent
    public static void renderOverlayPre(RenderGameOverlayEvent.Pre event) {
        //if (ClientUtil.getMinecraft().player == null) { return; }

        int width = event.getWindow().getGuiScaledWidth();
        int height = event.getWindow().getGuiScaledHeight();

        // draw in pre
        SkyrimIngameGui.render(event.getMatrixStack(), width, height, event.getPartialTicks());
    }

//    @SubscribeEvent
//    public static void renderOverlayPost(RenderGameOverlayEvent.Post event) {
//        int width = event.getWindow().getGuiScaledWidth();
//        int height = event.getWindow().getGuiScaledHeight();
//
//        RenderSystem.enableTexture();
//        RenderSystem.enableBlend();
//
//        if(event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
//            SkyrimIngameGui.render(event.getMatrixStack(), width, height, event.getPartialTicks());
//        }
//    }
}