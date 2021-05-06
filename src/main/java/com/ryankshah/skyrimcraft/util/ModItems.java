package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.item.MagickaPotion;
import com.ryankshah.skyrimcraft.item.SkyrimItem;
import com.ryankshah.skyrimcraft.item.SpellBook;
import com.ryankshah.skyrimcraft.spell.SpellFireball;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
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

    public static final RegistryObject<Item> EBONY_INGOT = ITEMS.register("ebony_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB), "Ebony Ingot"));
    public static final RegistryObject<Item> MOONSTONE_INGOT = ITEMS.register("moonstone_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB), "Moonstone Ingot"));
    public static final RegistryObject<Item> MALACHITE_INGOT = ITEMS.register("malachite_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB), "Malachite Ingot"));
    public static final RegistryObject<Item> ORICHALCUM_INGOT = ITEMS.register("orichalcum_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB), "Orichalcum Ingot"));
    public static final RegistryObject<Item> LEATHER_STRIPS = ITEMS.register("leather_strips", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB), "Leather Strips"));
    public static final RegistryObject<Item> DAEDRA_HEART = ITEMS.register("daedra_heart", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB), "Daedra Heart"));
    public static final RegistryObject<Item> MAGICKA_POTION = ITEMS.register("magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB), "Magicka Potion", 6.0f));
    public static final RegistryObject<Item> MINOR_MAGICKA_POTION = ITEMS.register("minor_magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB), "Minor Magicka Potion", 4.0f));

    // Food
    public static final RegistryObject<Item> SWEET_ROLL = ITEMS.register("sweet_roll", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB).food(new Food.Builder().nutrition(4).saturationMod(0.4F).build()), "Sweet Roll"));

    // Spell books
    public static final RegistryObject<Item> FIREBALL_SPELLBOOK = ITEMS.register("fireball_spellbook", () -> new SpellBook(new Item.Properties().tab(Skyrimcraft.TAB).stacksTo(1), "Spellbook", SpellRegistry.FIREBALL));
    public static final RegistryObject<Item> TURN_UNDEAD_SPELLBOOK = ITEMS.register("turn_undead_spellbook", () -> new SpellBook(new Item.Properties().tab(Skyrimcraft.TAB).stacksTo(1), "Spellbook", SpellRegistry.TURN_UNDEAD));

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
            singleTexture(MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/magicka_potion"));
            singleTexture(MINOR_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/minor_magicka_potion"));

            singleTexture(SWEET_ROLL.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/sweet_roll"));

            singleTexture(FIREBALL_SPELLBOOK.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/spellbook"));
            singleTexture(TURN_UNDEAD_SPELLBOOK.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/spellbook"));
        }
    }
}