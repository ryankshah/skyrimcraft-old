package com.ryankshah.skyrimcraft;

import com.ryankshah.skyrimcraft.advancement.BaseTrigger;
import com.ryankshah.skyrimcraft.advancement.TriggerManager;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.client.entity.ModEntityType;
import com.ryankshah.skyrimcraft.client.entity.boss.dragon.SkyrimDragon;
import com.ryankshah.skyrimcraft.client.entity.creature.GiantEntity;
import com.ryankshah.skyrimcraft.client.entity.creature.SabreCatEntity;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.BlueButterfly;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.TorchBug;
import com.ryankshah.skyrimcraft.data.ModRecipeType;
import com.ryankshah.skyrimcraft.data.loot_table.condition.type.ModLootConditionTypes;
import com.ryankshah.skyrimcraft.data.provider.ModGlobalLootTableProvider;
import com.ryankshah.skyrimcraft.data.serializer.ModSerializers;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.item.ModItems;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.util.ModAttributes;
import com.ryankshah.skyrimcraft.util.ModSounds;
import com.ryankshah.skyrimcraft.worldgen.WorldGen;
import com.ryankshah.skyrimcraft.worldgen.ore.OreConfig;
import com.ryankshah.skyrimcraft.worldgen.structure.ModStructures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

/**
 * The main class for the Skyrimcraft mod.
 *
 * TODO:
 *   - Continue working on the shouts and spells system:
 *     - Add more shouts and spells :)
 *   - Continue with adding some items, food, weapons and armour into the game
 *     - Do code + textures for lettuce crop and add lettuce item
 *     - Add some potions
 *       - Wellbeing potions
 *       - Regenerate magicka, and stamina
 *       - etc.
 *   - Merchants
 *     - Fix the merchant entity taking existing vanilla POI types...
 *     - Work on the merchant trading system (uses villager trading, but will have 1 level)
 *     - Work on merchant work times (like skyrim, some can be all day, some can be Xam-Ypm, etc.)
 *   - Work on the alchemy system
 *     - World generation for creep clusters and other ingredients
 *     - Add more ingredients
 *       - Add sabre cat pelt and sabre cat snow pelt -- add these + tooth for sabre cat drops
 *     - Sort out how ingredients will be obtainable (i.e. giants toes and others currently
 *       tradeable by villagers or gen in structures, but we need giants and other mobs)
 *   - Add more oven recipes:
 *     - Apple Dumpling
 *     - Braided Bread (?)
 *     - Chicken Dumpling
 *     - Jazbay Crostata
 *     - Juniper Berry Crostata
 *     - Lavender Dumpling
 *     - Snowberry Crostata
 *   - Continue working on the ingame GUI overlay:
 *     - Render the boss health similarly to target entities?
 *   - Work on the character level system ( https://elderscrolls.fandom.com/wiki/Character_Level )
 *     - "Simply follow the prompts on the screen, select which of the three statistics to raise (Stamina, Health,
 *       or Magicka), then allocate perks to whichever skill tree the player wishes to advance. This can even be done
 *       in combat, and will refill whichever statistic is raised upon level-up, giving the potential to use a
 *       level-up as a means of recovery during a fight".
 *     - the hard limit is actually level 65,535 (Hex number FFFF). Although a level cap in 1.9 was 81.5
 *   - Work on a skills system (https://elderscrolls.fandom.com/wiki/Skills_(Skyrim))
 *     - Skills system is ready, but need to tinker with the xp rates and add in how XP gains for rest of skills.
 *     - Need to work on skills GUI
 *   - Start working on a quest system
 *     - Certain quests require a character level of either 2, 5, 10, 15, 17, 20, 25, 30, or 80 to start them
 *       - This is a future consideration...
 *     - Need to write the reload listener to retrieve the quest jsons..
 *   - Start working on a faction system
 *   - We have a dragon! Now to implement its fight and flight mechanics
 *     - Once this is done, add in the dragon souls mechanic to unlock shout stages
 *     - Also.. work on shout stages (haha!)
 */
@Mod(Skyrimcraft.MODID)
public class Skyrimcraft
{
    // Skyrimcraft's mod identifier
    public static final String MODID = "skyrimcraft";
    // Directly reference a Log4J logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public Skyrimcraft() {
        GeckoLib.initialize();

        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        OreConfig.registerCommonConfig(COMMON_BUILDER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_BUILDER.build());

        ModAttributes.ATTRIBUTES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntityType.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCK_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModSounds.SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        SpellRegistry.SPELLS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModSerializers.RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEffects.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, WorldGen::onBiomeLoadingEvent); // high for additions to worldgen
        ModStructures.DEFERRED_REGISTRY_STRUCTURE.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModGlobalLootTableProvider.LOOT_MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::createEntityAttributes);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::addEntityAttributes);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            WorldGen.registerConfiguredFeatures();
            Networking.registerMessages();

            // Add Spell triggers
            for(RegistryObject<ISpell> spell : SpellRegistry.SPELLS.getEntries()) {
                BaseTrigger spellTrigger = new BaseTrigger("learned_spell_" + spell.get().getName().toLowerCase().replace(" ", "_"));
                TriggerManager.SPELL_TRIGGERS.put(spell.get(), spellTrigger);
            }
            TriggerManager.init();
        });
    }

    public void createEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityType.BLUE_BUTTERFLY.get(), BlueButterfly.createAttributes().build());
        event.put(ModEntityType.TORCHBUG.get(), TorchBug.createAttributes().build());
        event.put(ModEntityType.SABRE_CAT.get(), SabreCatEntity.createAttributes().build());
        event.put(ModEntityType.GIANT.get(), GiantEntity.createAttributes().build());
        event.put(ModEntityType.DRAGON.get(), SkyrimDragon.createAttributes().build());

        ModLootConditionTypes.register(); // needed to put this inside any register event to unfreeze register.
        ModRecipeType.register(); // needed to put this inside any register event to unfreeze register.
    }

    public void addEntityAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.MAGICKA_REGEN.get());
    }

    // Custom ItemGroup TAB
    public static final CreativeModeTab TAB_BLOCKS = new CreativeModeTab("skyrimcraft.blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.EBONY_ORE.get());
        }
    };
    public static final CreativeModeTab TAB_INGREDIENTS = new CreativeModeTab("skyrimcraft.ingredients") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SALT_PILE.get());
        }
    };
    public static final CreativeModeTab TAB_MATERIALS = new CreativeModeTab("skyrimcraft.material") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.EBONY_INGOT.get());
        }
    };
    public static final CreativeModeTab TAB_FOOD = new CreativeModeTab("skyrimcraft.food") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.APPLE_PIE.get());
        }
    };
    public static final CreativeModeTab TAB_COMBAT = new CreativeModeTab("skyrimcraft.combat") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.DAEDRIC_SWORD.get());
        }
    };
    public static final CreativeModeTab TAB_MAGIC = new CreativeModeTab("skyrimcraft.magic") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.FIREBALL_SPELLBOOK.get());
        }
    };
}
