package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.client.gui.screen.SkyrimMenuScreen;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.skill.PacketHandlePickpocketOnServer;
import com.ryankshah.skyrimcraft.network.spell.PacketCastSpell;
import com.ryankshah.skyrimcraft.client.entity.ModEntityType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static org.lwjgl.glfw.GLFW.*;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE) //, value = Dist.CLIENT
public class ForgeClientEvents
{
    public static final String CATEGORY = "key.categories." + Skyrimcraft.MODID;

    public static final KeyMapping toggleSkyrimMenu = new KeyMapping("key.togglemenu", KeyConflictContext.UNIVERSAL, InputConstants.Type.KEYSYM, GLFW_KEY_M, CATEGORY);
    public static final KeyMapping toggleSpellSlot1 = new KeyMapping("key.togglespellslot1", KeyConflictContext.UNIVERSAL, InputConstants.Type.KEYSYM, GLFW_KEY_V, CATEGORY);
    public static final KeyMapping toggleSpellSlot2 = new KeyMapping("key.togglespellslot2", KeyConflictContext.UNIVERSAL, InputConstants.Type.KEYSYM, GLFW_KEY_B, CATEGORY);
    public static final KeyMapping pickpocketKey = new KeyMapping("key.pickpocket", KeyConflictContext.UNIVERSAL, InputConstants.Type.KEYSYM, GLFW_KEY_G, CATEGORY);

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
                    mc.player.displayClientMessage(new TranslatableComponent("spell.noselect"), false);
            });
            return;
        }

        if(toggleSpellSlot2.consumeClick()) {
            mc.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
                if(cap.getSelectedSpells().size() > 1 && cap.getSelectedSpells().get(1) != null)
                    Networking.sendToServer(new PacketCastSpell(cap.getSelectedSpells().get(1)));
                else
                    mc.player.displayClientMessage(new TranslatableComponent("spell.noselect"), false);
            });
        }

        if(pickpocketKey.consumeClick()) {
            if (mc.crosshairPickEntity instanceof LivingEntity && mc.player.isCrouching()) {
                LivingEntity entity = (LivingEntity) mc.crosshairPickEntity;

                if(entity.getTags().contains(ModEntityType.PICKPOCKET_TAG)) {
                    Networking.sendToServer(new PacketHandlePickpocketOnServer(entity));
                }
            }
        }
    }
}
