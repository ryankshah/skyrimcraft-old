package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.item.MagickaPotion;
import com.ryankshah.skyrimcraft.item.SkyrimItem;
import com.ryankshah.skyrimcraft.item.SpellBook;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Skyrimcraft.MODID);

    public static final RegistryObject<Item> EBONY_INGOT = ITEMS.register("ebony_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Ebony Ingot"));
    public static final RegistryObject<Item> MOONSTONE_INGOT = ITEMS.register("moonstone_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Refined Moonstone"));
    public static final RegistryObject<Item> MALACHITE_INGOT = ITEMS.register("malachite_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Refined Malachite"));
    public static final RegistryObject<Item> ORICHALCUM_INGOT = ITEMS.register("orichalcum_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Orichalcum Ingot"));
    public static final RegistryObject<Item> LEATHER_STRIPS = ITEMS.register("leather_strips", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Leather Strips"));
    public static final RegistryObject<Item> DAEDRA_HEART = ITEMS.register("daedra_heart", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Daedra Heart"));

    // Potions
    public static final RegistryObject<Item> MINOR_MAGICKA_POTION = ITEMS.register("minor_magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Minor Magicka Potion", 2.0f));
    public static final RegistryObject<Item> MAGICKA_POTION = ITEMS.register("magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Magicka Potion", 4.0f));
    public static final RegistryObject<Item> PLENTIFUL_MAGICKA_POTION = ITEMS.register("plentiful_magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Plentiful Magicka Potion", 6.0f));
    public static final RegistryObject<Item> VIGOROUS_MAGICKA_POTION = ITEMS.register("vigorous_magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Vigorous Magicka Potion", 8.0f));
    public static final RegistryObject<Item> EXTREME_MAGICKA_POTION = ITEMS.register("extreme_magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Extreme Magicka Potion", 12.0f));
    public static final RegistryObject<Item> ULTIMATE_MAGICKA_POTION = ITEMS.register("ultimate_magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Ultimate Magicka Potion", 20.0f));

    // Food
    public static final RegistryObject<Item> SWEET_ROLL = ITEMS.register("sweet_roll", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new Food.Builder().nutrition(4).saturationMod(0.4F).build()), "Sweet Roll"));
    public static final RegistryObject<Item> GARLIC_BREAD = ITEMS.register("garlic_bread", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new Food.Builder().nutrition(5).saturationMod(0.6F).build()), "Garlic Bread"));
    public static final RegistryObject<Item> POTATO_BREAD = ITEMS.register("potato_bread", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new Food.Builder().nutrition(4).saturationMod(0.4F).build()), "Potato Bread"));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new Food.Builder().nutrition(4).saturationMod(0.4F).build()), "Tomato"));
    public static final RegistryObject<Item> APPLE_PIE = ITEMS.register("apple_pie", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new Food.Builder().nutrition(4).saturationMod(0.4F).build()), "Apple Pie"));

    // Ingredients
    public static final RegistryObject<Item> SALT_PILE = ITEMS.register("salt_pile", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Salt Pile"));
    public static final RegistryObject<Item> FLOUR = ITEMS.register("flour", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Flour"));
    public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Butter"));

    // Spell books
    public static final RegistryObject<Item> FIREBALL_SPELLBOOK = ITEMS.register("fireball_spellbook", () -> new SpellBook(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC).stacksTo(1), "Spellbook", SpellRegistry.FIREBALL));
    public static final RegistryObject<Item> TURN_UNDEAD_SPELLBOOK = ITEMS.register("turn_undead_spellbook", () -> new SpellBook(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC).stacksTo(1), "Spellbook", SpellRegistry.TURN_UNDEAD));

    public static class Items extends ItemModelProvider {
        public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, Skyrimcraft.MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            singleTexture(EBONY_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/ebony_ingot"));
            singleTexture(MOONSTONE_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/moonstone_ingot"));
            singleTexture(MALACHITE_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/malachite_ingot"));
            singleTexture(ORICHALCUM_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/orichalcum_ingot"));
            singleTexture(LEATHER_STRIPS.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/leather_strips"));
            singleTexture(DAEDRA_HEART.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/daedra_heart"));

            // Potions
            singleTexture(MINOR_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/minor_magicka_potion"));
            singleTexture(MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/magicka_potion"));
            singleTexture(PLENTIFUL_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/plentiful_magicka_potion"));
            singleTexture(VIGOROUS_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/vigorous_magicka_potion"));
            singleTexture(EXTREME_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/extreme_magicka_potion"));
            singleTexture(ULTIMATE_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/ultimate_magicka_potion"));

            // Food
            singleTexture(SWEET_ROLL.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/sweet_roll"));
            singleTexture(GARLIC_BREAD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/garlic_bread"));
            singleTexture(POTATO_BREAD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/potato_bread"));
            singleTexture(TOMATO.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/tomato"));
            singleTexture(APPLE_PIE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/apple_pie"));

            // Ingredients
            singleTexture(SALT_PILE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/salt_pile"));
            singleTexture(FLOUR.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/flour"));
            singleTexture(BUTTER.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/butter"));

            singleTexture(FIREBALL_SPELLBOOK.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/spellbook"));
            singleTexture(TURN_UNDEAD_SPELLBOOK.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/spellbook"));
        }
    }
}