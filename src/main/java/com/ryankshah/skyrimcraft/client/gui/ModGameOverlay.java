package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, value = Dist.CLIENT)
public class ModGameOverlay
{
    private static boolean needsPop = false;

    // TODO: Check for boss health + info and use the render target display similar to other mobs.
    // TODO: Fix subtitle text position (if needed??)
    // TODO: Move position of item text up
    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Pre event) {
        if (ClientUtil.getMinecraft().player == null) { return; }

//        if(event.getType() == RenderGameOverlayEvent.ElementType.BOSSHEALTH) {
//            event.getMatrixStack().translate(0, 1, 0);
//        }

        if (event.getType() == RenderGameOverlayEvent.ElementType.BOSSHEALTH && !ClientUtil.getMinecraft().options.hideGui && !event.isCanceled()) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0,28, 0);
            RenderSystem.popMatrix();
        }

        if(event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.HEALTH
            || event.getType() == RenderGameOverlayEvent.ElementType.AIR || event.getType() == RenderGameOverlayEvent.ElementType.ARMOR
            || event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void renderOverlayPost(RenderGameOverlayEvent.Post event) {
        if(event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            int width = event.getWindow().getGuiScaledWidth();
            int height = event.getWindow().getGuiScaledHeight();

            RenderSystem.enableTexture();
            RenderSystem.enableBlend();
            SkyrimIngameGui.render(event.getMatrixStack(), width, height, event.getPartialTicks());
        }
    }
}