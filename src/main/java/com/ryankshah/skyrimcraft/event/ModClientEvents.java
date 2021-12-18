package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.client.entity.ModEntityType;
import com.ryankshah.skyrimcraft.data.ModRecipes;
import com.ryankshah.skyrimcraft.data.lang.LangGenerator;
import com.ryankshah.skyrimcraft.data.provider.*;
import com.ryankshah.skyrimcraft.item.ModItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEvents
{
    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        if(event.includeServer()) {
            // Recipes
            gen.addProvider(new ModRecipes(gen));
            // Loot Tables
            gen.addProvider(new BaseLootTableProvider(gen));
            // Global Loot Tables
            gen.addProvider(new ModGlobalLootTableProvider(gen));
            // Advancements
            gen.addProvider(new ModAdvancementProvider(gen));
            // Forge Recipes
            gen.addProvider(new ModForgeRecipeProvider(gen));
            // Alchemy Recipes
            gen.addProvider(new ModAlchemyRecipeProvider(gen));
            // Oven Recipes
            gen.addProvider(new ModOvenRecipeProvider(gen));
            // Quests
            gen.addProvider(new ModQuestProvider(gen));
        }
        if(event.includeClient()) {
            // BlockStates
            gen.addProvider(new ModBlocks.BlockStates(gen, event.getExistingFileHelper()));
            // Block Items
            gen.addProvider(new ModBlocks.BlockItems(gen, event.getExistingFileHelper()));
            // Items
            gen.addProvider(new ModItems.Items(gen, event.getExistingFileHelper()));
            // Lang
            gen.addProvider(new LangGenerator(gen, Skyrimcraft.MODID, "en_us"));
        }
    }

    @SubscribeEvent
    public static void onRegisterEntities(EntityRenderersEvent.RegisterRenderers event) {
        ModEntityType.registerRenderers(event);
    }

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(ForgeClientEvents.toggleSkyrimMenu);
        ClientRegistry.registerKeyBinding(ForgeClientEvents.toggleSpellSlot1);
        ClientRegistry.registerKeyBinding(ForgeClientEvents.toggleSpellSlot2);

        //SkyrimIngameGui.register();

        ModBlocks.blockRenders();

        event.enqueueWork(() -> {
            ModItems.registerItemModelProperties();
        });
    }

    public static float noUse(ItemStack sword, ClientLevel clientWorld, LivingEntity entity, int i) {
        return entity != null && entity.getMainHandItem() == sword && entity.getOffhandItem() != ItemStack.EMPTY ? 1.0F : 0.0F;
    }

    public static float blocking(ItemStack itemStack, ClientLevel clientWorld, LivingEntity livingEntity, int i) {
        return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
    }

    public static float pulling(ItemStack bow, ClientLevel clientWorld, LivingEntity livingEntity, int i) {
        return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == bow ? 1.0F : 0.0F;
    }

    public static float pull(ItemStack bow, ClientLevel clientWorld, LivingEntity livingEntity, int i) {
        if (livingEntity == null) {
            return 0.0F;
        } else {
            return livingEntity.getUseItem() != bow ? 0.0F : (float)(bow.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
        }
    }
}
