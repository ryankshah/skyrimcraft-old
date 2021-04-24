package com.ryankshah.skyrimcraft;

import com.ryankshah.skyrimcraft.capability.*;
import com.ryankshah.skyrimcraft.util.ModBlocks;
import com.ryankshah.skyrimcraft.util.ModEntityType;
import com.ryankshah.skyrimcraft.util.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("skyrimcraft")
public class Skyrimcraft
{
    // Directly reference a log4j logger.
    public static final String MODID = "skyrimcraft";
    private static final Logger LOGGER = LogManager.getLogger();

    public Skyrimcraft() {
        GeckoLib.initialize();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(Skyrimcraft::setup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntityType.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IMagicka.class, new MagickaStorage(), Magicka::new);

//        DeferredWorkQueue.runLater(() -> {
//            GlobalEntityTypeAttributes.put(ModEntityType.ORCA.get(), OrcaEntity.func_234190_eK_().create());
//            GlobalEntityTypeAttributes.put(ModEntityType.SEA_SNAKE.get(), SeaSnakeEntity.func_234190_eK_().create());
//        });
    }

    //private void doClientStuff(final FMLClientSetupEvent event) {
//
    //}

    // Custom ItemGroup TAB
    public static final ItemGroup TAB = new ItemGroup("skyrimcraftTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Blocks.ACACIA_DOOR.getBlock());
        }
    };
}
