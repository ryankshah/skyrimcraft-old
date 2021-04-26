package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.client.gui.SkyrimMenuScreen;
import com.ryankshah.skyrimcraft.util.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static org.lwjgl.glfw.GLFW.*;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents
{
    private static final String CATEGORY = "key.categories." + Skyrimcraft.MODID;

    private static final KeyBinding toggleSkyrimMenu = new KeyBinding("key.toggleMenu", GLFW_KEY_M, CATEGORY);
    private static final KeyBinding toggleSpellSlot1 = new KeyBinding("key.toggleSpellSlot1", GLFW_KEY_X, CATEGORY);
    private static final KeyBinding toggleSpellSlot2 = new KeyBinding("key.toggleSpellSlot2", GLFW_KEY_Z, CATEGORY);

    @SubscribeEvent
    public static void onClientTickEvent(TickEvent.ClientTickEvent event) {
        if(event.phase != TickEvent.Phase.END) return;

        final Minecraft mc = Minecraft.getInstance();

        // key presses
        {
            if(toggleSkyrimMenu.isPressed()) {
                mc.displayGuiScreen(new SkyrimMenuScreen());
            }
        }
    }

    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(toggleSkyrimMenu);
        ClientRegistry.registerKeyBinding(toggleSpellSlot1);
        ClientRegistry.registerKeyBinding(toggleSpellSlot2);

        ModBlocks.blockRenders();
    }
}
