package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.item.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.inventory.EquipmentSlotType;
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
    // Magicka potions
    public static final RegistryObject<Item> MINOR_MAGICKA_POTION = ITEMS.register("minor_magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Potion of Minor Magicka", 2.0f));
    public static final RegistryObject<Item> MAGICKA_POTION = ITEMS.register("magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Potion of Magicka", 4.0f));
    public static final RegistryObject<Item> PLENTIFUL_MAGICKA_POTION = ITEMS.register("plentiful_magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Potion of Plentiful Magicka", 6.0f));
    public static final RegistryObject<Item> VIGOROUS_MAGICKA_POTION = ITEMS.register("vigorous_magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Potion of Vigorous Magicka", 8.0f));
    public static final RegistryObject<Item> EXTREME_MAGICKA_POTION = ITEMS.register("extreme_magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Potion of Extreme Magicka", 12.0f));
    public static final RegistryObject<Item> ULTIMATE_MAGICKA_POTION = ITEMS.register("ultimate_magicka_potion", () -> new MagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Potion of Ultimate Magicka", 20.0f));
    // Magicka regen potions
    public static final RegistryObject<Item> LASTING_POTENCY_POTION = ITEMS.register("lasting_potency_potion", () -> new RegenMagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Potion of Lasting Potency",1.5f, 600));
    public static final RegistryObject<Item> DRAUGHT_LASTING_POTENCY_POTION = ITEMS.register("draught_lasting_potency_potion", () -> new RegenMagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Draught of Lasting Potency", 1.6f, 600));
    public static final RegistryObject<Item> SOLUTION_LASTING_POTENCY_POTION = ITEMS.register("solution_lasting_potency_potion", () -> new RegenMagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Solution of Lasting Potency", 1.7f, 600));
    public static final RegistryObject<Item> PHILTER_LASTING_POTENCY_POTION = ITEMS.register("philter_lasting_potency_potion", () -> new RegenMagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Philter of Lasting Potency", 1.8f, 600));
    public static final RegistryObject<Item> ELIXIR_LASTING_POTENCY_POTION = ITEMS.register("elixir_lasting_potency_potion", () -> new RegenMagickaPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Elixir of Lasting Potency", 2f, 600));
    // Unique and non-levelled potions
    public static final RegistryObject<Item> PHILTER_OF_THE_PHANTOM_POTION = ITEMS.register("philter_of_the_phantom_potion", () -> new SpectralPotion(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Philter of the Phantom", 600));

    // Food
    public static final RegistryObject<Item> SWEET_ROLL = ITEMS.register("sweet_roll", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new Food.Builder().nutrition(4).saturationMod(0.4F).build()), "Sweet Roll"));
    public static final RegistryObject<Item> GARLIC_BREAD = ITEMS.register("garlic_bread", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new Food.Builder().nutrition(5).saturationMod(0.6F).build()), "Garlic Bread"));
    public static final RegistryObject<Item> POTATO_BREAD = ITEMS.register("potato_bread", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new Food.Builder().nutrition(4).saturationMod(0.4F).build()), "Potato Bread"));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new Food.Builder().nutrition(4).saturationMod(0.4F).build()), "Tomato"));
    public static final RegistryObject<Item> APPLE_PIE = ITEMS.register("apple_pie", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new Food.Builder().nutrition(4).saturationMod(0.4F).build()), "Apple Pie"));
    public static final RegistryObject<Item> FLOUR = ITEMS.register("flour", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Flour"));
    public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Butter"));

    // Ingredients
    public static final RegistryObject<Item> SALT_PILE = ITEMS.register("salt_pile", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Salt Pile", SkyrimIngredient.IngredientEffect.WEAKNESS_TO_MAGIC, SkyrimIngredient.IngredientEffect.FORTIFY_RESTORATION, SkyrimIngredient.IngredientEffect.SLOW, SkyrimIngredient.IngredientEffect.REGENERATE_MAGICKA));
    public static final RegistryObject<Item> CREEP_CLUSTER = ITEMS.register("creep_cluster", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Creep Cluster", SkyrimIngredient.IngredientEffect.RESTORE_MAGICKA, SkyrimIngredient.IngredientEffect.DAMAGE_STAMINA_REGEN, SkyrimIngredient.IngredientEffect.FORTIFY_CARRY_WEIGHT, SkyrimIngredient.IngredientEffect.WEAKNESS_TO_MAGIC));
    public static final RegistryObject<Item> GRASS_POD = ITEMS.register("grass_pod", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Grass Pod", SkyrimIngredient.IngredientEffect.RESIST_POISON, SkyrimIngredient.IngredientEffect.RAVAGE_MAGICKA, SkyrimIngredient.IngredientEffect.FORTIFY_ALTERATION, SkyrimIngredient.IngredientEffect.RESTORE_MAGICKA));
    public static final RegistryObject<Item> VAMPIRE_DUST = ITEMS.register("vampire_dust", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Vampire Dust", SkyrimIngredient.IngredientEffect.INVISIBILITY, SkyrimIngredient.IngredientEffect.RESTORE_MAGICKA, SkyrimIngredient.IngredientEffect.REGENERATE_HEALTH, SkyrimIngredient.IngredientEffect.CURE_DISEASE));
    public static final RegistryObject<Item> MORA_TAPINELLA = ITEMS.register("mora_tapinella", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Mora Tapinella", SkyrimIngredient.IngredientEffect.RESTORE_MAGICKA, SkyrimIngredient.IngredientEffect.LINGERING_DAMAGE_HEALTH, SkyrimIngredient.IngredientEffect.REGENERATE_STAMINA, SkyrimIngredient.IngredientEffect.FORTIFY_ILLUSION));
    public static final RegistryObject<Item> BRIAR_HEART = ITEMS.register("briar_heart", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Briar Heart", SkyrimIngredient.IngredientEffect.RESTORE_MAGICKA, SkyrimIngredient.IngredientEffect.FORTIFY_BLOCK, SkyrimIngredient.IngredientEffect.PARALYSIS, SkyrimIngredient.IngredientEffect.FORTIFY_MAGICKA));
    public static final RegistryObject<Item> GIANTS_TOE = ITEMS.register("giants_toe", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Giant's Toe", SkyrimIngredient.IngredientEffect.DAMAGE_STAMINA, SkyrimIngredient.IngredientEffect.FORTIFY_HEALTH, SkyrimIngredient.IngredientEffect.FORTIFY_CARRY_WEIGHT, SkyrimIngredient.IngredientEffect.DAMAGE_STAMINA_REGEN));
    public static final RegistryObject<Item> SALMON_ROE = ITEMS.register("salmon_roe", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Salmon Roe", SkyrimIngredient.IngredientEffect.RESTORE_STAMINA, SkyrimIngredient.IngredientEffect.WATERBREATHING, SkyrimIngredient.IngredientEffect.FORTIFY_MAGICKA, SkyrimIngredient.IngredientEffect.REGENERATE_MAGICKA));
    public static final RegistryObject<Item> DWARVEN_OIL = ITEMS.register("dwarven_oil", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Dwarven Oil", SkyrimIngredient.IngredientEffect.WEAKNESS_TO_MAGIC, SkyrimIngredient.IngredientEffect.FORTIFY_ILLUSION, SkyrimIngredient.IngredientEffect.REGENERATE_MAGICKA, SkyrimIngredient.IngredientEffect.RESTORE_MAGICKA));
    public static final RegistryObject<Item> FIRE_SALTS = ITEMS.register("fire_salts", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Fire Salts", SkyrimIngredient.IngredientEffect.WEAKNESS_TO_FROST, SkyrimIngredient.IngredientEffect.RESIST_FIRE, SkyrimIngredient.IngredientEffect.RESTORE_MAGICKA, SkyrimIngredient.IngredientEffect.REGENERATE_MAGICKA));

    // Armors
    // Ancient nord
    public static final RegistryObject<Item> ANCIENT_NORD_HELMET = ITEMS.register("ancient_nord_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.ANCIENT_NORD, EquipmentSlotType.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Ancient Nord Helmet"));
    public static final RegistryObject<Item> ANCIENT_NORD_CHESTPLATE = ITEMS.register("ancient_nord_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.ANCIENT_NORD, EquipmentSlotType.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Ancient Nord Chestplate"));
    public static final RegistryObject<Item> ANCIENT_NORD_LEGGINGS = ITEMS.register("ancient_nord_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.ANCIENT_NORD, EquipmentSlotType.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Ancient Nord Leggings"));
    public static final RegistryObject<Item> ANCIENT_NORD_BOOTS = ITEMS.register("ancient_nord_boots", () -> new SkyrimArmorItem(ModArmorMaterial.ANCIENT_NORD, EquipmentSlotType.FEET, new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Ancient Nord Boots"));
    // Glass
    public static final RegistryObject<Item> GLASS_HELMET = ITEMS.register("glass_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.GLASS, EquipmentSlotType.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Glass Helmet"));
    public static final RegistryObject<Item> GLASS_CHESTPLATE = ITEMS.register("glass_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.GLASS, EquipmentSlotType.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Glass Chestplate"));
    public static final RegistryObject<Item> GLASS_LEGGINGS = ITEMS.register("glass_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.GLASS, EquipmentSlotType.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Glass Leggings"));
    public static final RegistryObject<Item> GLASS_BOOTS = ITEMS.register("glass_boots", () -> new SkyrimArmorItem(ModArmorMaterial.GLASS, EquipmentSlotType.FEET, new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Glass Boots"));
    // Daedric
    public static final RegistryObject<Item> DAEDRIC_HELMET = ITEMS.register("daedric_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.DAEDRIC, EquipmentSlotType.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Daedric Helmet"));
    public static final RegistryObject<Item> DAEDRIC_CHESTPLATE = ITEMS.register("daedric_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.DAEDRIC, EquipmentSlotType.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Daedric Chestplate"));
    public static final RegistryObject<Item> DAEDRIC_LEGGINGS = ITEMS.register("daedric_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.DAEDRIC, EquipmentSlotType.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Daedric Leggings"));
    public static final RegistryObject<Item> DAEDRIC_BOOTS = ITEMS.register("daedric_boots", () -> new SkyrimArmorItem(ModArmorMaterial.DAEDRIC, EquipmentSlotType.FEET, new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Daedric Boots"));

    // Spell books
    public static final RegistryObject<Item> FIREBALL_SPELLBOOK = ITEMS.register("fireball_spellbook", () -> new SpellBook(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC).stacksTo(1), "Spellbook", SpellRegistry.FIREBALL));
    public static final RegistryObject<Item> TURN_UNDEAD_SPELLBOOK = ITEMS.register("turn_undead_spellbook", () -> new SpellBook(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC).stacksTo(1), "Spellbook", SpellRegistry.TURN_UNDEAD));

    public static class Items extends ItemModelProvider {
        public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, Skyrimcraft.MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            singleTexture(EBONY_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/ebony_ingot"));
            singleTexture(MOONSTONE_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/moonstone_ingot"));
            singleTexture(MALACHITE_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/malachite_ingot"));
            singleTexture(ORICHALCUM_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/orichalcum_ingot"));
            singleTexture(LEATHER_STRIPS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/leather_strips"));
            singleTexture(DAEDRA_HEART.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/daedra_heart"));

            // Potions
            // magicka potions
            singleTexture(MINOR_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/potion"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/minor_magicka_potion"));
            singleTexture(MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/potion"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/magicka_potion"));
            singleTexture(PLENTIFUL_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/potion"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/plentiful_magicka_potion"));
            singleTexture(VIGOROUS_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/potion"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/vigorous_magicka_potion"));
            singleTexture(EXTREME_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/potion"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/extreme_magicka_potion"));
            singleTexture(ULTIMATE_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/potion"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/ultimate_magicka_potion"));
            // magicka regen potions
            singleTexture(LASTING_POTENCY_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/potion"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/lasting_potency_potion"));
            singleTexture(DRAUGHT_LASTING_POTENCY_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/potion"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/draught_lasting_potency_potion"));
            singleTexture(SOLUTION_LASTING_POTENCY_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/potion"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/solution_lasting_potency_potion"));
            singleTexture(PHILTER_LASTING_POTENCY_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/potion"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/philter_lasting_potency_potion"));
            singleTexture(ELIXIR_LASTING_POTENCY_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/potion"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/elixir_lasting_potency_potion"));
            // unique potions
            singleTexture(PHILTER_OF_THE_PHANTOM_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/potion"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/philter_of_the_phantom_potion"));

            // Food
            singleTexture(SWEET_ROLL.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/sweet_roll"));
            singleTexture(GARLIC_BREAD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/garlic_bread"));
            singleTexture(POTATO_BREAD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/potato_bread"));
            singleTexture(TOMATO.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/tomato"));
            singleTexture(APPLE_PIE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/apple_pie"));

            // Ingredients
            singleTexture(SALT_PILE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/salt_pile"));
            singleTexture(FLOUR.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/flour"));
            singleTexture(BUTTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/butter"));

            singleTexture(CREEP_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/creep_cluster"));
            singleTexture(GRASS_POD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/grass_pod"));
            singleTexture(VAMPIRE_DUST.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/vampire_dust"));
            singleTexture(MORA_TAPINELLA.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/mora_tapinella"));
            singleTexture(BRIAR_HEART.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/briar_heart"));
            singleTexture(GIANTS_TOE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/giants_toe"));
            singleTexture(SALMON_ROE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/salmon_roe"));
            singleTexture(DWARVEN_OIL.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/dwarven_oil"));
            singleTexture(FIRE_SALTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/fire_salts"));

            // Armors
            // Ancient Nord
            singleTexture(ANCIENT_NORD_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/ancient_nord_helmet"));
            singleTexture(ANCIENT_NORD_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/ancient_nord_chestplate"));
            singleTexture(ANCIENT_NORD_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/ancient_nord_leggings"));
            singleTexture(ANCIENT_NORD_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/ancient_nord_boots"));
            // Glass
            singleTexture(GLASS_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/glass_helmet"));
            singleTexture(GLASS_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/glass_chestplate"));
            singleTexture(GLASS_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/glass_leggings"));
            singleTexture(GLASS_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/glass_boots"));
            // Daedric
            singleTexture(DAEDRIC_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/daedric_helmet"));
            singleTexture(DAEDRIC_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/daedric_chestplate"));
            singleTexture(DAEDRIC_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/daedric_leggings"));
            singleTexture(DAEDRIC_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/daedric_boots"));

            singleTexture(FIREBALL_SPELLBOOK.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/spellbook"));
            singleTexture(TURN_UNDEAD_SPELLBOOK.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", new ResourceLocation(Skyrimcraft.MODID, "item/spellbook"));
        }
    }
}