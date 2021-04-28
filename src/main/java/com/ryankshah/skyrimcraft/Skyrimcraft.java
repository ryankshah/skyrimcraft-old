package com.ryankshah.skyrimcraft;

import com.ryankshah.skyrimcraft.capability.*;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import com.ryankshah.skyrimcraft.util.ModBlocks;
import com.ryankshah.skyrimcraft.util.ModEntityType;
import com.ryankshah.skyrimcraft.util.ModItems;
import com.ryankshah.skyrimcraft.worldgen.WorldGen;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

/**
 * TODO:
 *   - Continue working on the shouts and spells system:
 *     - Work on spell casting and what happens on spell cast
 *   - Get started with adding some items, food, weapons and armour into the game
 *   - Continue working on the ingame GUI overlay:
 *     - Mob indicators and *known* structures indicators in compass
 *     - Fix positioning of target health bar
 *     - Fix positioning etc. of the health, magicka and stamina bars (the glow in bar should show on value decrease)
 *   - Fix the positioning of the text + icons in SkyrimMenuScreen
 *   - Set XP rates for SkyrimOreBlock and tool types for mining them
 *   - Set selected spells when keybind in pressed in SkyrimMagicGui
 *     - Check if the player has already selected the same spell before and switch keybinds etc. (if needed)
 *     - Add the selected icon (same as those used in SkyrimMenuScreen) for selected spell(s).
 */
@Mod(Skyrimcraft.MODID)
public class Skyrimcraft
{
    // Directly reference a log4j logger.
    public static final String MODID = "skyrimcraft";
    private static final Logger LOGGER = LogManager.getLogger();

    public Skyrimcraft() {
        GeckoLib.initialize();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(Skyrimcraft::setup);

        SpellRegistry.SPELLS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCK_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntityType.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, WorldGen::generateOres);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void setup(final FMLCommonSetupEvent event) {
        Networking.registerMessages();
        CapabilityManager.INSTANCE.register(ISkyrimPlayerData.class, new SkyrimPlayerDataStorage(), SkyrimPlayerData::new);
    }

//    @SubscribeEvent
//    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
//        event.put(ModEntityTypes.EARTH_GOD.get(), EarthGodEntity.createAttributes().build());
//    }

    // Custom ItemGroup TAB
    public static final ItemGroup TAB = new ItemGroup("skyrimcraftTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.EBONY_ORE.get());
        }
    };
}
