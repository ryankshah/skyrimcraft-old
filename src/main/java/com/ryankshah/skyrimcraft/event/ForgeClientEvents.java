package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.client.gui.SkyrimMenuScreen;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.spell.PacketCastSpell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static org.lwjgl.glfw.GLFW.*;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE) //, value = Dist.CLIENT
public class ForgeClientEvents
{
    public static final String CATEGORY = "key.categories." + Skyrimcraft.MODID;

    public static final KeyBinding toggleSkyrimMenu = new KeyBinding("key.togglemenu", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW_KEY_M, CATEGORY);
    public static final KeyBinding toggleSpellSlot1 = new KeyBinding("key.togglespellslot1", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW_KEY_V, CATEGORY);
    public static final KeyBinding toggleSpellSlot2 = new KeyBinding("key.togglespellslot2", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW_KEY_B, CATEGORY);

    // public static final Map<BlockPos, Float> positions = Collections.synchronizedMap(new HashMap<>());

    // TODO: Perhaps we make use of this for ice form shout..?
//    @SubscribeEvent
//    public static void postRenderWorld(RenderWorldLastEvent event) {
//        if(!positions.isEmpty()) {
//            for (Map.Entry<BlockPos, Float> entry : positions.entrySet()) {
//                if (entry == null)
//                    continue;
//
//                if (entry.getValue() <= 0)
//                    positions.remove(entry.getKey());
//                else {
//                    ClientUtil.drawCloudAtPos(event, entry.getKey(), new Vector3d(3, 1, 3), 0x007E8796); // storm cloud color
//                    positions.put(entry.getKey(), entry.getValue() - 0.05f);
//                }
//            }
//        }
//    }

    @SubscribeEvent
    public static void onClientTickEvent(TickEvent.ClientTickEvent event) {
        if(event.phase != TickEvent.Phase.END) return;

        final Minecraft mc = Minecraft.getInstance();

        if(toggleSkyrimMenu.consumeClick()) {
            mc.setScreen(new SkyrimMenuScreen());
            return;
        }

        if(toggleSpellSlot1.consumeClick()) {
            mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
                if(cap.getSelectedSpells().size() > 0 && cap.getSelectedSpells().get(0) != null)
                    Networking.sendToServer(new PacketCastSpell(cap.getSelectedSpells().get(0)));
                else
                    mc.player.displayClientMessage(new TranslationTextComponent("spell.noselect"), false);
            });
            return;
        }

        if(toggleSpellSlot2.consumeClick()) {
            mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
                if(cap.getSelectedSpells().size() > 1 && cap.getSelectedSpells().get(1) != null)
                    Networking.sendToServer(new PacketCastSpell(cap.getSelectedSpells().get(1)));
                else
                    mc.player.displayClientMessage(new TranslationTextComponent("spell.noselect"), false);
            });
        }
    }
}
