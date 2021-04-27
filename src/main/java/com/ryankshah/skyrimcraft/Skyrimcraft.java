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
 *   - Have the player's magicka capability persist over login (PlayerLoggedInEvent and changed
 *     dimensions (PlayerChangedDimensionEvent)
 *   - Sync the player's magicka between client and server whenever it changes (i.e. when a spell/shout
 *     is cast, on login, on changed dimension, on respawn (PlayerRespawnEvent))
 *   - Work on a shouts and spells system
 *   - Get started with adding some items, food, weapons and armour into the game
 *   - Continue working on the ingame GUI overlay (mob indicators in compass + current target/enemy health bar)
 *   - Fix the positioning of the text + icons in SkyrimMenuScreen
 *   - Set XP rates for SkyrimOreBlock and tool types for mining them
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
