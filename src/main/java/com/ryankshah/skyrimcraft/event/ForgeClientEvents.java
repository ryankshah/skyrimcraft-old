package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.client.gui.SkyrimMenuScreen;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.PacketCastSpell;
import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.util.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents
{
    public static final String CATEGORY = "key.categories." + Skyrimcraft.MODID;

    public static final KeyBinding toggleSkyrimMenu = new KeyBinding("key.togglemenu", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW_KEY_M, CATEGORY);
    public static final KeyBinding toggleSpellSlot1 = new KeyBinding("key.togglespellslot1", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW_KEY_V, CATEGORY);
    public static final KeyBinding toggleSpellSlot2 = new KeyBinding("key.togglespellslot2", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW_KEY_B, CATEGORY);

    @SubscribeEvent
    public static void onClientTickEvent(TickEvent.ClientTickEvent event) {
        if(event.phase != TickEvent.Phase.END) return;

        final Minecraft mc = Minecraft.getInstance();

        // key presses
        if(toggleSkyrimMenu.isPressed()) {
            mc.displayGuiScreen(new SkyrimMenuScreen());
            return;
        }

        if(toggleSpellSlot1.isPressed()) {
            mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
                if(cap.getSelectedSpells()[0] != null)
                    Networking.sendToServer(new PacketCastSpell(cap.getSelectedSpells()[0]));
            });
            return;
        }

        if(toggleSpellSlot2.isPressed()) {
            mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
                if(cap.getSelectedSpells()[1] != null)
                    Networking.sendToServer(new PacketCastSpell(cap.getSelectedSpells()[1]));
            });
        }
    }
}
