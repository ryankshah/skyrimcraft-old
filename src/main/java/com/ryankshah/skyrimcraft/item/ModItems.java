package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.event.ModClientEvents;
import com.ryankshah.skyrimcraft.util.IngredientEffect;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Skyrimcraft.MODID);

    public static final RegistryObject<Item> CORUNDUM_INGOT = ITEMS.register("corundum_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Corundum Ingot"));
    public static final RegistryObject<Item> QUICKSILVER_INGOT = ITEMS.register("quicksilver_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Quicksilver Ingot"));
    public static final RegistryObject<Item> DWARVEN_METAL_INGOT = ITEMS.register("dwarven_metal_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Dwarven Metal Ingot"));
    public static final RegistryObject<Item> EBONY_INGOT = ITEMS.register("ebony_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Ebony Ingot"));
    public static final RegistryObject<Item> MALACHITE_INGOT = ITEMS.register("malachite_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Refined Malachite"));
    public static final RegistryObject<Item> MOONSTONE_INGOT = ITEMS.register("moonstone_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Refined Moonstone"));
    public static final RegistryObject<Item> ORICHALCUM_INGOT = ITEMS.register("orichalcum_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Orichalcum Ingot"));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Silver Ingot"));
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MATERIALS), "Steel Ingot"));
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
    public static final RegistryObject<Item> SWEET_ROLL = ITEMS.register("sweet_roll", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).build()), "Sweet Roll"));
    public static final RegistryObject<Item> GARLIC_BREAD = ITEMS.register("garlic_bread", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new FoodProperties.Builder().nutrition(5).saturationMod(0.6F).build()), "Garlic Bread"));
    public static final RegistryObject<Item> POTATO_BREAD = ITEMS.register("potato_bread", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).build()), "Potato Bread"));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).build()), "Tomato"));
    public static final RegistryObject<Item> APPLE_PIE = ITEMS.register("apple_pie", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).build()), "Apple Pie"));
    public static final RegistryObject<Item> MAMMOTH_SNOUT = ITEMS.register("mammoth_snout", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).build()), "Mammoth Snout"));
    public static final RegistryObject<Item> MAMMOTH_STEAK = ITEMS.register("mammoth_steak", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).build()), "Mammoth Steak"));
    public static final RegistryObject<Item> VENISON = ITEMS.register("venison", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_FOOD).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).build()), "Venison"));
    public static final RegistryObject<Item> FLOUR = ITEMS.register("flour", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Flour"));
    public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Butter"));

    // Drink
    // TODO: add skooma, ale, ..

    // Ingredients
    public static final RegistryObject<Item> SALT_PILE = ITEMS.register("salt_pile", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Salt Pile", IngredientEffect.WEAKNESS_TO_MAGIC, IngredientEffect.FORTIFY_RESTORATION, IngredientEffect.SLOW, IngredientEffect.REGENERATE_MAGICKA));
    public static final RegistryObject<Item> CREEP_CLUSTER = ITEMS.register("creep_cluster", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Creep Cluster", IngredientEffect.RESTORE_MAGICKA, IngredientEffect.DAMAGE_STAMINA_REGEN, IngredientEffect.FORTIFY_CARRY_WEIGHT, IngredientEffect.WEAKNESS_TO_MAGIC));
    public static final RegistryObject<Item> GRASS_POD = ITEMS.register("grass_pod", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Grass Pod", IngredientEffect.RESIST_POISON, IngredientEffect.RAVAGE_MAGICKA, IngredientEffect.FORTIFY_ALTERATION, IngredientEffect.RESTORE_MAGICKA));
    public static final RegistryObject<Item> VAMPIRE_DUST = ITEMS.register("vampire_dust", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Vampire Dust", IngredientEffect.INVISIBILITY, IngredientEffect.RESTORE_MAGICKA, IngredientEffect.REGENERATE_HEALTH, IngredientEffect.CURE_DISEASE));
    public static final RegistryObject<Item> MORA_TAPINELLA = ITEMS.register("mora_tapinella", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Mora Tapinella", IngredientEffect.RESTORE_MAGICKA, IngredientEffect.LINGERING_DAMAGE_HEALTH, IngredientEffect.REGENERATE_STAMINA, IngredientEffect.FORTIFY_ILLUSION));
    public static final RegistryObject<Item> BRIAR_HEART = ITEMS.register("briar_heart", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Briar Heart", IngredientEffect.RESTORE_MAGICKA, IngredientEffect.FORTIFY_BLOCK, IngredientEffect.PARALYSIS, IngredientEffect.FORTIFY_MAGICKA));
    public static final RegistryObject<Item> GIANTS_TOE = ITEMS.register("giants_toe", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Giant's Toe", IngredientEffect.DAMAGE_STAMINA, IngredientEffect.FORTIFY_HEALTH, IngredientEffect.FORTIFY_CARRY_WEIGHT, IngredientEffect.DAMAGE_STAMINA_REGEN));
    public static final RegistryObject<Item> SALMON_ROE = ITEMS.register("salmon_roe", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Salmon Roe", IngredientEffect.RESTORE_STAMINA, IngredientEffect.WATERBREATHING, IngredientEffect.FORTIFY_MAGICKA, IngredientEffect.REGENERATE_MAGICKA));
    public static final RegistryObject<Item> DWARVEN_OIL = ITEMS.register("dwarven_oil", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Dwarven Oil", IngredientEffect.WEAKNESS_TO_MAGIC, IngredientEffect.FORTIFY_ILLUSION, IngredientEffect.REGENERATE_MAGICKA, IngredientEffect.RESTORE_MAGICKA));
    public static final RegistryObject<Item> FIRE_SALTS = ITEMS.register("fire_salts", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Fire Salts", IngredientEffect.WEAKNESS_TO_FROST, IngredientEffect.RESIST_FIRE, IngredientEffect.RESTORE_MAGICKA, IngredientEffect.REGENERATE_MAGICKA));
    public static final RegistryObject<Item> ABECEAN_LONGFIN = ITEMS.register("abecean_longfin", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Abecean Longfin", IngredientEffect.WEAKNESS_TO_FROST, IngredientEffect.FORTIFY_SNEAK, IngredientEffect.WEAKNESS_TO_POISON, IngredientEffect.FORTIFY_RESTORATION));
    public static final RegistryObject<Item> CYRODILIC_SPADETAIL = ITEMS.register("cyrodilic_spadetail", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Cyrodilic Spadetail", IngredientEffect.DAMAGE_STAMINA, IngredientEffect.FORTIFY_RESTORATION, IngredientEffect.FEAR, IngredientEffect.RAVAGE_HEALTH));
    public static final RegistryObject<Item> SABRE_CAT_TOOTH = ITEMS.register("sabre_cat_tooth", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Sabre Cat Tooth", IngredientEffect.RESTORE_STAMINA, IngredientEffect.FORTIFY_HEAVY_ARMOR, IngredientEffect.FORTIFY_SMITHING, IngredientEffect.WEAKNESS_TO_POISON));
    public static final RegistryObject<Item> BLUE_DARTWING = ITEMS.register("blue_dartwing", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Blue Dartwing", IngredientEffect.RESIST_SHOCK, IngredientEffect.FORTIFY_PICKPOCKET, IngredientEffect.RESTORE_HEALTH, IngredientEffect.FEAR));
    public static final RegistryObject<Item> ORANGE_DARTWING = ITEMS.register("orange_dartwing", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Orange Dartwing", IngredientEffect.RESTORE_STAMINA, IngredientEffect.RAVAGE_MAGICKA, IngredientEffect.FORTIFY_PICKPOCKET, IngredientEffect.LINGERING_DAMAGE_HEALTH));
    public static final RegistryObject<Item> PEARL = ITEMS.register("pearl", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Pearl", IngredientEffect.RESTORE_STAMINA, IngredientEffect.FORTIFY_BLOCK, IngredientEffect.RESTORE_MAGICKA, IngredientEffect.RESIST_SHOCK));
    public static final RegistryObject<Item> SMALL_PEARL = ITEMS.register("small_pearl", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Small Pearl", IngredientEffect.RESTORE_STAMINA, IngredientEffect.FORTIFY_ONE_HANDED, IngredientEffect.FORTIFY_RESTORATION, IngredientEffect.RESIST_FROST));
    public static final RegistryObject<Item> PINE_THRUSH_EGG = ITEMS.register("pine_thrush_egg", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Pine Thrush Egg", IngredientEffect.RESTORE_STAMINA, IngredientEffect.FORTIFY_LOCKPICKING, IngredientEffect.WEAKNESS_TO_POISON, IngredientEffect.RESIST_SHOCK));
    public static final RegistryObject<Item> ROCK_WARBLER_EGG = ITEMS.register("rock_warbler_egg", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Rock Warbler Egg", IngredientEffect.RESTORE_HEALTH, IngredientEffect.FORTIFY_ONE_HANDED, IngredientEffect.DAMAGE_STAMINA, IngredientEffect.WEAKNESS_TO_MAGIC));
    public static final RegistryObject<Item> SLAUGHTERFISH_EGG = ITEMS.register("slaughterfish_egg", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Slaughterfish Egg", IngredientEffect.RESIST_POISON, IngredientEffect.FORTIFY_PICKPOCKET, IngredientEffect.LINGERING_DAMAGE_HEALTH, IngredientEffect.FORTIFY_STAMINA));
    public static final RegistryObject<Item> SLAUGHTERFISH_SCALES = ITEMS.register("slaughterfish_scales", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Slaughterfish Scales", IngredientEffect.RESIST_FROST, IngredientEffect.LINGERING_DAMAGE_HEALTH, IngredientEffect.FORTIFY_HEAVY_ARMOR, IngredientEffect.FORTIFY_BLOCK));
    public static final RegistryObject<Item> SPIDER_EGG = ITEMS.register("spider_egg", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Spider Egg", IngredientEffect.DAMAGE_STAMINA, IngredientEffect.DAMAGE_MAGICKA_REGEN, IngredientEffect.FORTIFY_LOCKPICKING, IngredientEffect.FORTIFY_MARKSMAN));
    public static final RegistryObject<Item> HAWK_EGG = ITEMS.register("hawk_egg", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Hawk's Egg", IngredientEffect.RESIST_MAGIC, IngredientEffect.DAMAGE_MAGICKA_REGEN, IngredientEffect.WATERBREATHING, IngredientEffect.LINGERING_DAMAGE_STAMINA));
    public static final RegistryObject<Item> TROLL_FAT = ITEMS.register("troll_fat", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Troll Fat", IngredientEffect.RESIST_POISON, IngredientEffect.FORTIFY_TWO_HANDED, IngredientEffect.FRENZY, IngredientEffect.DAMAGE_HEALTH));
    public static final RegistryObject<Item> CHAURUS_EGGS = ITEMS.register("chaurus_eggs", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Chaurus Eggs", IngredientEffect.WEAKNESS_TO_POISON, IngredientEffect.FORTIFY_STAMINA, IngredientEffect.DAMAGE_MAGICKA, IngredientEffect.INVISIBILITY));
    public static final RegistryObject<Item> FLY_AMANITA = ITEMS.register("fly_amanita", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Fly Amanita", IngredientEffect.RESIST_FIRE, IngredientEffect.FORTIFY_TWO_HANDED, IngredientEffect.FRENZY, IngredientEffect.REGENERATE_STAMINA));
    public static final RegistryObject<Item> ELVES_EAR = ITEMS.register("elves_ear", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Elves Ear", IngredientEffect.RESTORE_MAGICKA, IngredientEffect.FORTIFY_MARKSMAN, IngredientEffect.WEAKNESS_TO_FROST, IngredientEffect.RESIST_FIRE));
    public static final RegistryObject<Item> TAPROOT = ITEMS.register("taproot", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Taproot", IngredientEffect.WEAKNESS_TO_MAGIC, IngredientEffect.FORTIFY_ILLUSION, IngredientEffect.REGENERATE_MAGICKA, IngredientEffect.RESTORE_MAGICKA));
    public static final RegistryObject<Item> BEE = ITEMS.register("bee", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Bee", IngredientEffect.RESTORE_STAMINA, IngredientEffect.RAVAGE_STAMINA, IngredientEffect.REGENERATE_STAMINA, IngredientEffect.WEAKNESS_TO_SHOCK));
    public static final RegistryObject<Item> EYE_OF_SABRE_CAT = ITEMS.register("eye_of_sabre_cat", () -> new SkyrimIngredient(new Item.Properties().tab(Skyrimcraft.TAB_INGREDIENTS), "Eye of Sabre Cat", IngredientEffect.RESTORE_STAMINA, IngredientEffect.RAVAGE_HEALTH, IngredientEffect.DAMAGE_MAGICKA, IngredientEffect.RESTORE_HEALTH));


    //// COMBAT ////
    // Ancient Nord
    public static final RegistryObject<Item> ANCIENT_NORD_HELMET = ITEMS.register("ancient_nord_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.ANCIENT_NORD, EquipmentSlot.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Ancient Nord Helmet", true));
    public static final RegistryObject<Item> ANCIENT_NORD_CHESTPLATE = ITEMS.register("ancient_nord_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.ANCIENT_NORD, EquipmentSlot.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Ancient Nord Chestplate", true));
    public static final RegistryObject<Item> ANCIENT_NORD_LEGGINGS = ITEMS.register("ancient_nord_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.ANCIENT_NORD, EquipmentSlot.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Ancient Nord Leggings", true));
    public static final RegistryObject<Item> ANCIENT_NORD_BOOTS = ITEMS.register("ancient_nord_boots", () -> new SkyrimArmorItem(ModArmorMaterial.ANCIENT_NORD, EquipmentSlot.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Ancient Nord Boots", true));
    public static final RegistryObject<Item> ANCIENT_NORD_ARROW = ITEMS.register("ancient_nord_arrow", () -> new SkyrimArrow.AncientNordArrow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Ancient Nord Arrow"));
    public static final RegistryObject<Item> ANCIENT_NORD_SWORD = ITEMS.register("ancient_nord_sword", () -> new SkyrimWeapon(ModItemTier.ANCIENT_NORD, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT), "Ancient Nord Sword"));
    public static final RegistryObject<Item> ANCIENT_NORD_BATTLEAXE = ITEMS.register("ancient_nord_battleaxe", () -> new SkyrimWeapon(ModItemTier.ANCIENT_NORD, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT), "Ancient Nord Battleaxe"));
    public static final RegistryObject<Item> ANCIENT_NORD_BOW = ITEMS.register("ancient_nord_bow", () -> new SkyrimBow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Ancient Nord Bow", ANCIENT_NORD_ARROW.get()));
    public static final RegistryObject<Item> ANCIENT_NORD_GREATSWORD = ITEMS.register("ancient_nord_greatsword", () -> new SkyrimTwoHandedWeapon(ModItemTier.ANCIENT_NORD, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT), "Ancient Nord Greatsword"));
    public static final RegistryObject<Item> ANCIENT_NORD_WAR_AXE = ITEMS.register("ancient_nord_war_axe", () -> new SkyrimWeapon(ModItemTier.ANCIENT_NORD, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT), "Ancient Nord War Axe"));
    // Daedric
    public static final RegistryObject<Item> DAEDRIC_HELMET = ITEMS.register("daedric_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.DAEDRIC, EquipmentSlot.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Daedric Helmet", true));
    public static final RegistryObject<Item> DAEDRIC_CHESTPLATE = ITEMS.register("daedric_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.DAEDRIC, EquipmentSlot.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Daedric Chestplate", true));
    public static final RegistryObject<Item> DAEDRIC_LEGGINGS = ITEMS.register("daedric_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.DAEDRIC, EquipmentSlot.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Daedric Leggings", true));
    public static final RegistryObject<Item> DAEDRIC_BOOTS = ITEMS.register("daedric_boots", () -> new SkyrimArmorItem(ModArmorMaterial.DAEDRIC, EquipmentSlot.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Daedric Boots", true));
    public static final RegistryObject<Item> DAEDRIC_SHIELD = ITEMS.register("daedric_shield", () -> new SkyrimShield(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Daedric Shield"));
    public static final RegistryObject<Item> DAEDRIC_ARROW = ITEMS.register("daedric_arrow", () -> new SkyrimArrow.DaedricArrow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Daedric Arrow"));
    public static final RegistryObject<Item> DAEDRIC_DAGGER = ITEMS.register("daedric_dagger", () -> new SkyrimWeapon(ModItemTier.DAEDRIC, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Daedric Dagger"));
    public static final RegistryObject<Item> DAEDRIC_SWORD = ITEMS.register("daedric_sword", () -> new SkyrimWeapon(ModItemTier.DAEDRIC, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Daedric Sword"));
    public static final RegistryObject<Item> DAEDRIC_BATTLEAXE = ITEMS.register("daedric_battleaxe", () -> new SkyrimWeapon(ModItemTier.DAEDRIC, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Daedric Battleaxe"));
    public static final RegistryObject<Item> DAEDRIC_BOW = ITEMS.register("daedric_bow", () -> new SkyrimBow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Daedric Bow", DAEDRIC_ARROW.get()));
    public static final RegistryObject<Item> DAEDRIC_GREATSWORD = ITEMS.register("daedric_greatsword", () -> new SkyrimTwoHandedWeapon(ModItemTier.DAEDRIC, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Daedric Greatsword"));
    public static final RegistryObject<Item> DAEDRIC_MACE = ITEMS.register("daedric_mace", () -> new SkyrimWeapon(ModItemTier.DAEDRIC, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Daedric Mace"));
    public static final RegistryObject<Item> DAEDRIC_WAR_AXE = ITEMS.register("daedric_war_axe", () -> new SkyrimWeapon(ModItemTier.DAEDRIC, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Daedric War Axe"));
    public static final RegistryObject<Item> DAEDRIC_WARHAMMER = ITEMS.register("daedric_warhammer", () -> new SkyrimTwoHandedWeapon(ModItemTier.DAEDRIC, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Daedric Warhammer"));
    // Dragonbone
    public static final RegistryObject<Item> DRAGONBONE_ARROW = ITEMS.register("dragonbone_arrow", () -> new SkyrimArrow.DragonboneArrow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dragonbone Arrow"));
    public static final RegistryObject<Item> DRAGONBONE_DAGGER = ITEMS.register("dragonbone_dagger", () -> new SkyrimWeapon(ModItemTier.DRAGONBONE, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dragonbone Dagger"));
    public static final RegistryObject<Item> DRAGONBONE_SWORD = ITEMS.register("dragonbone_sword", () -> new SkyrimWeapon(ModItemTier.DRAGONBONE, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dragonbone Sword"));
    public static final RegistryObject<Item> DRAGONBONE_BATTLEAXE = ITEMS.register("dragonbone_battleaxe", () -> new SkyrimWeapon(ModItemTier.DRAGONBONE, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dragonbone Battleaxe"));
    public static final RegistryObject<Item> DRAGONBONE_BOW = ITEMS.register("dragonbone_bow", () -> new SkyrimBow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Dragonbone Bow", DRAGONBONE_ARROW.get()));
    public static final RegistryObject<Item> DRAGONBONE_GREATSWORD = ITEMS.register("dragonbone_greatsword", () -> new SkyrimTwoHandedWeapon(ModItemTier.DRAGONBONE, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dragonbone Greatsword"));
    public static final RegistryObject<Item> DRAGONBONE_MACE = ITEMS.register("dragonbone_mace", () -> new SkyrimWeapon(ModItemTier.DRAGONBONE, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dragonbone Mace"));
    public static final RegistryObject<Item> DRAGONBONE_WAR_AXE = ITEMS.register("dragonbone_war_axe", () -> new SkyrimWeapon(ModItemTier.DRAGONBONE, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dragonbone War Axe"));
    public static final RegistryObject<Item> DRAGONBONE_WARHAMMER = ITEMS.register("dragonbone_warhammer", () -> new SkyrimTwoHandedWeapon(ModItemTier.DRAGONBONE, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dragonbone Warhammer"));
    // Dwarven
    public static final RegistryObject<Item> DWARVEN_HELMET = ITEMS.register("dwarven_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.DWARVEN, EquipmentSlot.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Dwarven Helmet", true));
    public static final RegistryObject<Item> DWARVEN_CHESTPLATE = ITEMS.register("dwarven_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.DWARVEN, EquipmentSlot.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Dwarven Chestplate", true));
    public static final RegistryObject<Item> DWARVEN_LEGGINGS = ITEMS.register("dwarven_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.DWARVEN, EquipmentSlot.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Dwarven Leggings", true));
    public static final RegistryObject<Item> DWARVEN_BOOTS = ITEMS.register("dwarven_boots", () -> new SkyrimArmorItem(ModArmorMaterial.DWARVEN, EquipmentSlot.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Dwarven Boots", true));
    public static final RegistryObject<Item> DWARVEN_SHIELD = ITEMS.register("dwarven_shield", () -> new SkyrimShield(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Dwarven Shield"));
    public static final RegistryObject<Item> DWARVEN_ARROW = ITEMS.register("dwarven_arrow", () -> new SkyrimArrow.DwarvenArrow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dwarven Arrow"));
    public static final RegistryObject<Item> DWARVEN_DAGGER = ITEMS.register("dwarven_dagger", () -> new SkyrimWeapon(ModItemTier.DWARVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dwarven Dagger"));
    public static final RegistryObject<Item> DWARVEN_SWORD = ITEMS.register("dwarven_sword", () -> new SkyrimWeapon(ModItemTier.DWARVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dwarven Sword"));
    public static final RegistryObject<Item> DWARVEN_BATTLEAXE = ITEMS.register("dwarven_battleaxe", () -> new SkyrimWeapon(ModItemTier.DWARVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dwarven Battleaxe"));
    public static final RegistryObject<Item> DWARVEN_BOW = ITEMS.register("dwarven_bow", () -> new SkyrimBow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Dwarven Bow", DWARVEN_ARROW.get()));
    public static final RegistryObject<Item> DWARVEN_GREATSWORD = ITEMS.register("dwarven_greatsword", () -> new SkyrimTwoHandedWeapon(ModItemTier.DWARVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dwarven Greatsword"));
    public static final RegistryObject<Item> DWARVEN_MACE = ITEMS.register("dwarven_mace", () -> new SkyrimWeapon(ModItemTier.DWARVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dwarven Mace"));
    public static final RegistryObject<Item> DWARVEN_WAR_AXE = ITEMS.register("dwarven_war_axe", () -> new SkyrimWeapon(ModItemTier.DWARVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dwarven War Axe"));
    public static final RegistryObject<Item> DWARVEN_WARHAMMER = ITEMS.register("dwarven_warhammer", () -> new SkyrimTwoHandedWeapon(ModItemTier.DWARVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Dwarven Warhammer"));
    // Ebony
    public static final RegistryObject<Item> EBONY_HELMET = ITEMS.register("ebony_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.EBONY, EquipmentSlot.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Ebony Helmet", true));
    public static final RegistryObject<Item> EBONY_CHESTPLATE = ITEMS.register("ebony_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.EBONY, EquipmentSlot.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Ebony Chestplate", true));
    public static final RegistryObject<Item> EBONY_LEGGINGS = ITEMS.register("ebony_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.EBONY, EquipmentSlot.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Ebony Leggings", true));
    public static final RegistryObject<Item> EBONY_BOOTS = ITEMS.register("ebony_boots", () -> new SkyrimArmorItem(ModArmorMaterial.EBONY, EquipmentSlot.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Ebony Boots", true));
    public static final RegistryObject<Item> EBONY_SHIELD = ITEMS.register("ebony_shield", () -> new SkyrimShield(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Ebony Shield"));
    public static final RegistryObject<Item> EBONY_ARROW = ITEMS.register("ebony_arrow", () -> new SkyrimArrow.EbonyArrow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Ebony Arrow"));
    public static final RegistryObject<Item> EBONY_DAGGER = ITEMS.register("ebony_dagger", () -> new SkyrimWeapon(ModItemTier.EBONY, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Ebony Dagger"));
    public static final RegistryObject<Item> EBONY_SWORD = ITEMS.register("ebony_sword", () -> new SkyrimWeapon(ModItemTier.EBONY, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Ebony Sword"));
    public static final RegistryObject<Item> EBONY_BATTLEAXE = ITEMS.register("ebony_battleaxe", () -> new SkyrimWeapon(ModItemTier.EBONY, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Ebony Battleaxe"));
    public static final RegistryObject<Item> EBONY_BOW = ITEMS.register("ebony_bow", () -> new SkyrimBow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Ebony Bow", EBONY_ARROW.get()));
    public static final RegistryObject<Item> EBONY_GREATSWORD = ITEMS.register("ebony_greatsword", () -> new SkyrimTwoHandedWeapon(ModItemTier.EBONY, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Ebony Greatsword"));
    public static final RegistryObject<Item> EBONY_MACE = ITEMS.register("ebony_mace", () -> new SkyrimWeapon(ModItemTier.EBONY, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Ebony Mace"));
    public static final RegistryObject<Item> EBONY_WAR_AXE = ITEMS.register("ebony_war_axe", () -> new SkyrimWeapon(ModItemTier.EBONY, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Ebony War Axe"));
    public static final RegistryObject<Item> EBONY_WARHAMMER = ITEMS.register("ebony_warhammer", () -> new SkyrimTwoHandedWeapon(ModItemTier.EBONY, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Ebony Warhammer"));
    // Elven
    public static final RegistryObject<Item> ELVEN_HELMET = ITEMS.register("elven_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.ELVEN, EquipmentSlot.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Elven Helmet", false));
    public static final RegistryObject<Item> ELVEN_CHESTPLATE = ITEMS.register("elven_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.ELVEN, EquipmentSlot.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Elven Chestplate", false));
    public static final RegistryObject<Item> ELVEN_LEGGINGS = ITEMS.register("elven_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.ELVEN, EquipmentSlot.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Elven Leggings", false));
    public static final RegistryObject<Item> ELVEN_BOOTS = ITEMS.register("elven_boots", () -> new SkyrimArmorItem(ModArmorMaterial.ELVEN, EquipmentSlot.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Elven Boots", false));
    public static final RegistryObject<Item> ELVEN_SHIELD = ITEMS.register("elven_shield", () -> new SkyrimShield(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Elven Shield"));
    public static final RegistryObject<Item> ELVEN_ARROW = ITEMS.register("elven_arrow", () -> new SkyrimArrow.ElvenArrow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Elven Arrow"));
    public static final RegistryObject<Item> ELVEN_DAGGER = ITEMS.register("elven_dagger", () -> new SkyrimWeapon(ModItemTier.ELVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Elven Dagger"));
    public static final RegistryObject<Item> ELVEN_SWORD = ITEMS.register("elven_sword", () -> new SkyrimWeapon(ModItemTier.ELVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Elven Sword"));
    public static final RegistryObject<Item> ELVEN_BATTLEAXE = ITEMS.register("elven_battleaxe", () -> new SkyrimWeapon(ModItemTier.ELVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Elven Battleaxe"));
    public static final RegistryObject<Item> ELVEN_BOW = ITEMS.register("elven_bow", () -> new SkyrimBow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Elven Bow", ELVEN_ARROW.get()));
    public static final RegistryObject<Item> ELVEN_GREATSWORD = ITEMS.register("elven_greatsword", () -> new SkyrimTwoHandedWeapon(ModItemTier.ELVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Elven Greatsword"));
    public static final RegistryObject<Item> ELVEN_MACE = ITEMS.register("elven_mace", () -> new SkyrimWeapon(ModItemTier.ELVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Elven Mace"));
    public static final RegistryObject<Item> ELVEN_WAR_AXE = ITEMS.register("elven_war_axe", () -> new SkyrimWeapon(ModItemTier.ELVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Elven War Axe"));
    public static final RegistryObject<Item> ELVEN_WARHAMMER = ITEMS.register("elven_warhammer", () -> new SkyrimTwoHandedWeapon(ModItemTier.ELVEN, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Elven Warhammer"));
    // Falmer
    public static final RegistryObject<Item> FALMER_HELMET = ITEMS.register("falmer_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.FALMER, EquipmentSlot.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Falmer Helmet", true));
    public static final RegistryObject<Item> FALMER_CHESTPLATE = ITEMS.register("falmer_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.FALMER, EquipmentSlot.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Falmer Chestplate", true));
    public static final RegistryObject<Item> FALMER_LEGGINGS = ITEMS.register("falmer_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.FALMER, EquipmentSlot.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Falmer Leggings", true));
    public static final RegistryObject<Item> FALMER_BOOTS = ITEMS.register("falmer_boots", () -> new SkyrimArmorItem(ModArmorMaterial.FALMER, EquipmentSlot.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Falmer Boots", true));
    public static final RegistryObject<Item> FALMER_ARROW = ITEMS.register("falmer_arrow", () -> new SkyrimArrow.FalmerArrow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Falmer Arrow"));
    public static final RegistryObject<Item> FALMER_SWORD = ITEMS.register("falmer_sword", () -> new SkyrimWeapon(ModItemTier.FALMER, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Falmer Sword"));
    public static final RegistryObject<Item> FALMER_BOW = ITEMS.register("falmer_bow", () -> new SkyrimBow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Falmer Bow", FALMER_ARROW.get()));
    public static final RegistryObject<Item> FALMER_WAR_AXE = ITEMS.register("falmer_war_axe", () -> new SkyrimWeapon(ModItemTier.FALMER, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Falmer War Axe"));
    // Glass
    public static final RegistryObject<Item> GLASS_HELMET = ITEMS.register("glass_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.GLASS, EquipmentSlot.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Glass Helmet", false));
    public static final RegistryObject<Item> GLASS_CHESTPLATE = ITEMS.register("glass_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.GLASS, EquipmentSlot.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Glass Chestplate", false));
    public static final RegistryObject<Item> GLASS_LEGGINGS = ITEMS.register("glass_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.GLASS, EquipmentSlot.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Glass Leggings", false));
    public static final RegistryObject<Item> GLASS_BOOTS = ITEMS.register("glass_boots", () -> new SkyrimArmorItem(ModArmorMaterial.GLASS, EquipmentSlot.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Glass Boots", false));
    public static final RegistryObject<Item> GLASS_SHIELD = ITEMS.register("glass_shield", () -> new SkyrimShield(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Glass Shield"));
    public static final RegistryObject<Item> GLASS_ARROW = ITEMS.register("glass_arrow", () -> new SkyrimArrow.GlassArrow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Glass Arrow"));
    public static final RegistryObject<Item> GLASS_DAGGER = ITEMS.register("glass_dagger", () -> new SkyrimWeapon(ModItemTier.GLASS, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT), "Glass Dagger"));
    public static final RegistryObject<Item> GLASS_SWORD = ITEMS.register("glass_sword", () -> new SkyrimWeapon(ModItemTier.GLASS, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT), "Glass Sword"));
    public static final RegistryObject<Item> GLASS_BATTLEAXE = ITEMS.register("glass_battleaxe", () -> new SkyrimWeapon(ModItemTier.GLASS, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT), "Glass Battleaxe"));
    public static final RegistryObject<Item> GLASS_BOW = ITEMS.register("glass_bow", () -> new SkyrimBow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Glass Bow", GLASS_ARROW.get()));
    public static final RegistryObject<Item> GLASS_GREATSWORD = ITEMS.register("glass_greatsword", () -> new SkyrimTwoHandedWeapon(ModItemTier.GLASS, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT), "Glass Greatsword"));
    public static final RegistryObject<Item> GLASS_MACE = ITEMS.register("glass_mace", () -> new SkyrimWeapon(ModItemTier.GLASS, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT), "Glass Mace"));
    public static final RegistryObject<Item> GLASS_WAR_AXE = ITEMS.register("glass_war_axe", () -> new SkyrimWeapon(ModItemTier.GLASS, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT), "Glass War Axe"));
    public static final RegistryObject<Item> GLASS_WARHAMMER = ITEMS.register("glass_warhammer", () -> new SkyrimTwoHandedWeapon(ModItemTier.GLASS, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT), "Glass Warhammer"));
    // Imperial
    public static final RegistryObject<Item> IMPERIAL_HELMET = ITEMS.register("imperial_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.IMPERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Imperial Helmet", true));
    public static final RegistryObject<Item> IMPERIAL_CHESTPLATE = ITEMS.register("imperial_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.IMPERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Imperial Chestplate", true));
    public static final RegistryObject<Item> IMPERIAL_LEGGINGS = ITEMS.register("imperial_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.IMPERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Imperial Leggings", true));
    public static final RegistryObject<Item> IMPERIAL_BOOTS = ITEMS.register("imperial_boots", () -> new SkyrimArmorItem(ModArmorMaterial.IMPERIAL, EquipmentSlot.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Imperial Boots", true));
    public static final RegistryObject<Item> IMPERIAL_SWORD = ITEMS.register("imperial_sword", () -> new SkyrimWeapon(Tiers.IRON, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Imperial Sword"));
    // Iron (Skyrim)
    public static final RegistryObject<Item> IRON_HELMET = ITEMS.register("iron_helmet", () -> new SkyrimArmorItem(ArmorMaterials.IRON, EquipmentSlot.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Iron Helmet", true));
    public static final RegistryObject<Item> IRON_CHESTPLATE = ITEMS.register("iron_chestplate", () -> new SkyrimArmorItem(ArmorMaterials.IRON, EquipmentSlot.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Iron Chestplate", true));
    public static final RegistryObject<Item> IRON_LEGGINGS = ITEMS.register("iron_leggings", () -> new SkyrimArmorItem(ArmorMaterials.IRON, EquipmentSlot.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Iron Leggings", true));
    public static final RegistryObject<Item> IRON_BOOTS = ITEMS.register("iron_boots", () -> new SkyrimArmorItem(ArmorMaterials.IRON, EquipmentSlot.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Iron Boots", true));
    public static final RegistryObject<Item> IRON_SHIELD = ITEMS.register("iron_shield", () -> new SkyrimShield(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Iron Shield"));
    public static final RegistryObject<Item> IRON_ARROW = ITEMS.register("iron_arrow", () -> new SkyrimArrow.IronArrow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Iron Arrow"));
    public static final RegistryObject<Item> IRON_DAGGER = ITEMS.register("iron_dagger", () -> new SkyrimWeapon(Tiers.IRON, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Iron Dagger"));
    public static final RegistryObject<Item> IRON_SWORD = ITEMS.register("iron_sword", () -> new SkyrimWeapon(Tiers.IRON, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Iron Sword"));
    public static final RegistryObject<Item> IRON_BATTLEAXE = ITEMS.register("iron_battleaxe", () -> new SkyrimWeapon(Tiers.IRON, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Iron Battleaxe"));
    public static final RegistryObject<Item> IRON_GREATSWORD = ITEMS.register("iron_greatsword", () -> new SkyrimTwoHandedWeapon(Tiers.IRON, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Iron Greatsword"));
    public static final RegistryObject<Item> IRON_MACE = ITEMS.register("iron_mace", () -> new SkyrimWeapon(Tiers.IRON, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Iron Mace"));
    public static final RegistryObject<Item> IRON_WAR_AXE = ITEMS.register("iron_war_axe", () -> new SkyrimWeapon(Tiers.IRON, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Iron War Axe"));
    public static final RegistryObject<Item> IRON_WARHAMMER = ITEMS.register("iron_warhammer", () -> new SkyrimTwoHandedWeapon(Tiers.IRON, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Iron Warhammer"));
    // Orcish
    public static final RegistryObject<Item> ORCISH_HELMET = ITEMS.register("orcish_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.ORCISH, EquipmentSlot.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Orcish Helmet", true));
    public static final RegistryObject<Item> ORCISH_CHESTPLATE = ITEMS.register("orcish_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.ORCISH, EquipmentSlot.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Orcish Chestplate", true));
    public static final RegistryObject<Item> ORCISH_LEGGINGS = ITEMS.register("orcish_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.ORCISH, EquipmentSlot.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Orcish Leggings", true));
    public static final RegistryObject<Item> ORCISH_BOOTS = ITEMS.register("orcish_boots", () -> new SkyrimArmorItem(ModArmorMaterial.ORCISH, EquipmentSlot.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Orcish Boots", true));
    public static final RegistryObject<Item> ORCISH_SHIELD = ITEMS.register("orcish_shield", () -> new SkyrimShield(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Orcish Shield"));
    public static final RegistryObject<Item> ORCISH_ARROW = ITEMS.register("orcish_arrow", () -> new SkyrimArrow.OrcishArrow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Orcish Arrow"));
    public static final RegistryObject<Item> ORCISH_DAGGER = ITEMS.register("orcish_dagger", () -> new SkyrimWeapon(ModItemTier.ORCISH, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Orcish Dagger"));
    public static final RegistryObject<Item> ORCISH_SWORD = ITEMS.register("orcish_sword", () -> new SkyrimWeapon(ModItemTier.ORCISH, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Orcish Sword"));
    public static final RegistryObject<Item> ORCISH_BATTLEAXE = ITEMS.register("orcish_battleaxe", () -> new SkyrimWeapon(ModItemTier.ORCISH, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Orcish Battleaxe"));
    public static final RegistryObject<Item> ORCISH_BOW = ITEMS.register("orcish_bow", () -> new SkyrimBow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Orcish Bow", ORCISH_ARROW.get()));
    public static final RegistryObject<Item> ORCISH_GREATSWORD = ITEMS.register("orcish_greatsword", () -> new SkyrimTwoHandedWeapon(ModItemTier.ORCISH, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Orcish Greatsword"));
    public static final RegistryObject<Item> ORCISH_MACE = ITEMS.register("orcish_mace", () -> new SkyrimWeapon(ModItemTier.ORCISH, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Orcish Mace"));
    public static final RegistryObject<Item> ORCISH_WAR_AXE = ITEMS.register("orcish_war_axe", () -> new SkyrimWeapon(ModItemTier.ORCISH, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Orcish War Axe"));
    public static final RegistryObject<Item> ORCISH_WARHAMMER = ITEMS.register("orcish_warhammer", () -> new SkyrimTwoHandedWeapon(ModItemTier.ORCISH, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Orcish Warhammer"));
    // Steel
//    public static final RegistryObject<Item> STEEL_HELMET = ITEMS.register("steel_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.STEEL, EquipmentSlotType.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Steel Helmet", true));
//    public static final RegistryObject<Item> STEEL_CHESTPLATE = ITEMS.register("steel_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.STEEL, EquipmentSlotType.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Steel Chestplate", true));
//    public static final RegistryObject<Item> STEEL_LEGGINGS = ITEMS.register("steel_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.STEEL, EquipmentSlotType.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Steel Leggings", true));
//    public static final RegistryObject<Item> STEEL_BOOTS = ITEMS.register("steel_boots", () -> new SkyrimArmorItem(ModArmorMaterial.STEEL, EquipmentSlotType.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Steel Boots", true));
    public static final RegistryObject<Item> STEEL_SHIELD = ITEMS.register("steel_shield", () -> new SkyrimShield(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Steel Shield"));
    public static final RegistryObject<Item> STEEL_ARROW = ITEMS.register("steel_arrow", () -> new SkyrimArrow.SteelArrow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Steel Arrow"));
    public static final RegistryObject<Item> STEEL_DAGGER = ITEMS.register("steel_dagger", () -> new SkyrimWeapon(ModItemTier.STEEL, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Steel Dagger"));
    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword", () -> new SkyrimWeapon(ModItemTier.STEEL, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Steel Sword"));
    public static final RegistryObject<Item> STEEL_BATTLEAXE = ITEMS.register("steel_battleaxe", () -> new SkyrimWeapon(ModItemTier.STEEL, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Steel Battleaxe"));
    public static final RegistryObject<Item> STEEL_GREATSWORD = ITEMS.register("steel_greatsword", () -> new SkyrimTwoHandedWeapon(ModItemTier.STEEL, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Steel Greatsword"));
    public static final RegistryObject<Item> STEEL_MACE = ITEMS.register("steel_mace", () -> new SkyrimWeapon(ModItemTier.STEEL, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Steel Mace"));
    public static final RegistryObject<Item> STEEL_WAR_AXE = ITEMS.register("steel_war_axe", () -> new SkyrimWeapon(ModItemTier.STEEL, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Steel War Axe"));
    public static final RegistryObject<Item> STEEL_WARHAMMER = ITEMS.register("steel_warhammer", () -> new SkyrimTwoHandedWeapon(ModItemTier.STEEL, 3, -2.4F, (new Item.Properties()).tab(Skyrimcraft.TAB_COMBAT).fireResistant(), "Steel Warhammer"));
    // Stormcloak + Stormcloak Officer Armor
    public static final RegistryObject<Item> STORMCLOAK_OFFICER_HELMET = ITEMS.register("stormcloak_officer_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.STORMCLOAK, EquipmentSlot.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Stormcloak Officer Helmet", true));
    public static final RegistryObject<Item> STORMCLOAK_OFFICER_CHESTPLATE = ITEMS.register("stormcloak_officer_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.STORMCLOAK, EquipmentSlot.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Stormcloak Officer Chestplate", true));
    public static final RegistryObject<Item> STORMCLOAK_OFFICER_LEGGINGS = ITEMS.register("stormcloak_officer_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.STORMCLOAK, EquipmentSlot.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Stormcloak Officer Leggings", true));
    public static final RegistryObject<Item> STORMCLOAK_OFFICER_BOOTS = ITEMS.register("stormcloak_officer_boots", () -> new SkyrimArmorItem(ModArmorMaterial.STORMCLOAK, EquipmentSlot.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Stormcloak Officer Boots", true));

    //// MISC ////
    // Hunting bow
    public static final RegistryObject<Item> HUNTING_BOW = ITEMS.register("hunting_bow", () -> new SkyrimBow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Hunting Bow", net.minecraft.world.item.Items.ARROW, IRON_ARROW.get(), STEEL_ARROW.get()));
    // Longbow
    public static final RegistryObject<Item> LONGBOW = ITEMS.register("longbow", () -> new SkyrimBow(new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Long Bow", net.minecraft.world.item.Items.ARROW, IRON_ARROW.get(), STEEL_ARROW.get()));
    // Scaled armor
    public static final RegistryObject<Item> SCALED_HELMET = ITEMS.register("scaled_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.SCALED, EquipmentSlot.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Scaled Helmet", false));
    public static final RegistryObject<Item> SCALED_CHESTPLATE = ITEMS.register("scaled_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.SCALED, EquipmentSlot.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Scaled Chestplate", false));
    public static final RegistryObject<Item> SCALED_LEGGINGS = ITEMS.register("scaled_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.SCALED, EquipmentSlot.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Scaled Leggings", false));
    public static final RegistryObject<Item> SCALED_BOOTS = ITEMS.register("scaled_boots", () -> new SkyrimArmorItem(ModArmorMaterial.SCALED, EquipmentSlot.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Scaled Boots", false));
    // Hide armor
    public static final RegistryObject<Item> HIDE_HELMET = ITEMS.register("hide_helmet", () -> new SkyrimArmorItem(ModArmorMaterial.HIDE, EquipmentSlot.HEAD, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Hide Helmet", false));
    public static final RegistryObject<Item> HIDE_CHESTPLATE = ITEMS.register("hide_chestplate", () -> new SkyrimArmorItem(ModArmorMaterial.HIDE, EquipmentSlot.CHEST, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Hide Chestplate", false));
    public static final RegistryObject<Item> HIDE_LEGGINGS = ITEMS.register("hide_leggings", () -> new SkyrimArmorItem(ModArmorMaterial.HIDE, EquipmentSlot.LEGS, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Hide Leggings", false));
    public static final RegistryObject<Item> HIDE_BOOTS = ITEMS.register("hide_boots", () -> new SkyrimArmorItem(ModArmorMaterial.HIDE, EquipmentSlot.FEET, new Item.Properties().tab(Skyrimcraft.TAB_COMBAT), "Hide Boots", false));

    //// MAGIC ////
    // Staff
    public static final RegistryObject<Item> STAFF = ITEMS.register("staff", () -> new SkyrimItem(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC), "Staff"));
    // Spell books
    public static final RegistryObject<Item> FIREBALL_SPELLBOOK = ITEMS.register("fireball_spellbook", () -> new SpellBook(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC).stacksTo(1), "Spellbook", SpellRegistry.FIREBALL));
    public static final RegistryObject<Item> TURN_UNDEAD_SPELLBOOK = ITEMS.register("turn_undead_spellbook", () -> new SpellBook(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC).stacksTo(1), "Spellbook", SpellRegistry.TURN_UNDEAD));
    public static final RegistryObject<Item> CONJURE_FAMILIAR_SPELLBOOK = ITEMS.register("conjure_familiar_spellbook", () -> new SpellBook(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC).stacksTo(1), "Spellbook", SpellRegistry.CONJURE_FAMILIAR));
    public static final RegistryObject<Item> HEALING_SPELLBOOK = ITEMS.register("healing_spellbook", () -> new SpellBook(new Item.Properties().tab(Skyrimcraft.TAB_MAGIC).stacksTo(1), "Spellbook", SpellRegistry.HEALING));

    public static void registerItemModelProperties() {
        registerTwoHandedProperties(ANCIENT_NORD_GREATSWORD.get());
        registerBowProperties(ANCIENT_NORD_BOW.get());

        registerTwoHandedProperties(DAEDRIC_GREATSWORD.get());
        registerTwoHandedProperties(DAEDRIC_WARHAMMER.get());
        registerBowProperties(DAEDRIC_BOW.get());
        registerShield(DAEDRIC_SHIELD.get());

        registerTwoHandedProperties(DRAGONBONE_GREATSWORD.get());
        registerTwoHandedProperties(DRAGONBONE_WARHAMMER.get());
        registerBowProperties(DRAGONBONE_BOW.get());

        registerTwoHandedProperties(DWARVEN_GREATSWORD.get());
        registerTwoHandedProperties(DWARVEN_WARHAMMER.get());
        registerBowProperties(DWARVEN_BOW.get());
        registerShield(DWARVEN_SHIELD.get());

        registerTwoHandedProperties(EBONY_GREATSWORD.get());
        registerTwoHandedProperties(EBONY_WARHAMMER.get());
        registerBowProperties(EBONY_BOW.get());
        registerShield(EBONY_SHIELD.get());

        registerTwoHandedProperties(ELVEN_GREATSWORD.get());
        registerTwoHandedProperties(ELVEN_WARHAMMER.get());
        registerBowProperties(ELVEN_BOW.get());
        registerShield(ELVEN_SHIELD.get());

        registerTwoHandedProperties(GLASS_GREATSWORD.get());
        registerTwoHandedProperties(GLASS_WARHAMMER.get());
        registerBowProperties(GLASS_BOW.get());
        registerShield(GLASS_SHIELD.get());

        registerTwoHandedProperties(IRON_GREATSWORD.get());
        registerTwoHandedProperties(IRON_WARHAMMER.get());
        registerShield(IRON_SHIELD.get());

        registerTwoHandedProperties(ORCISH_GREATSWORD.get());
        registerTwoHandedProperties(ORCISH_WARHAMMER.get());
        registerBowProperties(ORCISH_BOW.get());
        registerShield(ORCISH_SHIELD.get());

        registerTwoHandedProperties(STEEL_GREATSWORD.get());
        registerTwoHandedProperties(STEEL_WARHAMMER.get());
        registerShield(STEEL_SHIELD.get());

        // misc
        registerBowProperties(HUNTING_BOW.get());
        registerBowProperties(LONGBOW.get());
    }

    private static void registerTwoHandedProperties(Item item) {
        ItemProperties.register(item, new ResourceLocation(Skyrimcraft.MODID, "no_use"), ModClientEvents::noUse);
        ItemProperties.register(item, new ResourceLocation(Skyrimcraft.MODID, "blocking"), ModClientEvents::blocking);
    }

    private static void registerBowProperties(Item item) {
        ItemProperties.register(item, new ResourceLocation(Skyrimcraft.MODID, "pulling"), ModClientEvents::pulling);
        ItemProperties.register(item, new ResourceLocation(Skyrimcraft.MODID, "pull"), ModClientEvents::pull);
    }

    private static void registerShield(Item item) {
        ItemProperties.register(item, new ResourceLocation(Skyrimcraft.MODID, "blocking"), ModClientEvents::blocking);
    }


    public static class Items extends ItemModelProvider {
        public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, Skyrimcraft.MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            singleTexture(DWARVEN_METAL_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/dwarven_metal_ingot"));
            singleTexture(CORUNDUM_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/corundum_ingot"));
            singleTexture(QUICKSILVER_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/quicksilver_ingot"));
            singleTexture(SILVER_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/silver_ingot"));
            singleTexture(STEEL_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/steel_ingot"));
            singleTexture(EBONY_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/ebony_ingot"));
            singleTexture(MOONSTONE_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/moonstone_ingot"));
            singleTexture(MALACHITE_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/malachite_ingot"));
            singleTexture(ORICHALCUM_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/orichalcum_ingot"));
            singleTexture(LEATHER_STRIPS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/leather_strips"));
            singleTexture(DAEDRA_HEART.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/daedra_heart"));

            // Potions
            // magicka potions
            singleTexture(MINOR_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/minor_magicka_potion"));
            singleTexture(MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/magicka_potion"));
            singleTexture(PLENTIFUL_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/plentiful_magicka_potion"));
            singleTexture(VIGOROUS_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/vigorous_magicka_potion"));
            singleTexture(EXTREME_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/extreme_magicka_potion"));
            singleTexture(ULTIMATE_MAGICKA_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/ultimate_magicka_potion"));
            // magicka regen potions
            singleTexture(LASTING_POTENCY_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/lasting_potency_potion"));
            singleTexture(DRAUGHT_LASTING_POTENCY_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/draught_lasting_potency_potion"));
            singleTexture(SOLUTION_LASTING_POTENCY_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/solution_lasting_potency_potion"));
            singleTexture(PHILTER_LASTING_POTENCY_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/philter_lasting_potency_potion"));
            singleTexture(ELIXIR_LASTING_POTENCY_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/elixir_lasting_potency_potion"));
            // unique potions
            singleTexture(PHILTER_OF_THE_PHANTOM_POTION.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/philter_of_the_phantom_potion"));

            // Food
            singleTexture(SWEET_ROLL.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/sweet_roll"));
            singleTexture(GARLIC_BREAD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/garlic_bread"));
            singleTexture(POTATO_BREAD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/potato_bread"));
            singleTexture(MAMMOTH_SNOUT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/mammoth_snout"));
            singleTexture(MAMMOTH_STEAK.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/mammoth_steak"));
            singleTexture(VENISON.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/venison"));
            singleTexture(TOMATO.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/tomato"));
            singleTexture(APPLE_PIE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/apple_pie"));

            // Ingredients
            singleTexture(SALT_PILE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/salt_pile"));
            singleTexture(FLOUR.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/flour"));
            singleTexture(BUTTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/butter"));
            singleTexture(CREEP_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/creep_cluster"));
            singleTexture(GRASS_POD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/grass_pod"));
            singleTexture(VAMPIRE_DUST.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/vampire_dust"));
            singleTexture(MORA_TAPINELLA.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/mora_tapinella"));
            singleTexture(BRIAR_HEART.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/briar_heart"));
            singleTexture(GIANTS_TOE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/giants_toe"));
            singleTexture(SALMON_ROE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/salmon_roe"));
            singleTexture(DWARVEN_OIL.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/dwarven_oil"));
            singleTexture(FIRE_SALTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/fire_salts"));
            singleTexture(ABECEAN_LONGFIN.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/abecean_longfin"));
            singleTexture(CYRODILIC_SPADETAIL.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/cyrodilic_spadetail"));
            singleTexture(SABRE_CAT_TOOTH.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/sabre_cat_tooth"));
            singleTexture(BLUE_DARTWING.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/blue_dartwing"));
            singleTexture(ORANGE_DARTWING.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/orange_dartwing"));
            singleTexture(PEARL.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/pearl"));
            singleTexture(SMALL_PEARL.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/small_pearl"));
            singleTexture(PINE_THRUSH_EGG.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/pine_thrush_egg"));
            singleTexture(ROCK_WARBLER_EGG.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/rock_warbler_egg"));
            singleTexture(SLAUGHTERFISH_EGG.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/slaughterfish_egg"));
            singleTexture(SLAUGHTERFISH_SCALES.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/slaughterfish_scales"));
            singleTexture(SPIDER_EGG.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/spider_egg"));
            singleTexture(HAWK_EGG.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/hawk_egg"));
            singleTexture(TROLL_FAT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/troll_fat"));
            singleTexture(CHAURUS_EGGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/chaurus_eggs"));
            singleTexture(FLY_AMANITA.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/fly_amanita"));
            singleTexture(ELVES_EAR.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/elves_ear"));
            singleTexture(TAPROOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/taproot"));
            singleTexture(BEE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/bee"));
            singleTexture(EYE_OF_SABRE_CAT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/eye_of_sabre_cat"));

            // Weapons
            // Ancient Nord
            singleTexture(ANCIENT_NORD_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/ancient_nord_helmet"));
            singleTexture(ANCIENT_NORD_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/ancient_nord_chestplate"));
            singleTexture(ANCIENT_NORD_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/ancient_nord_leggings"));
            singleTexture(ANCIENT_NORD_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/ancient_nord_boots"));
            singleTexture(ANCIENT_NORD_SWORD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/ancient_nord_sword"));
            singleTexture(ANCIENT_NORD_BATTLEAXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/ancient_nord_battleaxe"));
            bow(ANCIENT_NORD_BOW.get().getRegistryName().getPath(), "ancient_nord_bow");
            twoHanded(ANCIENT_NORD_GREATSWORD.get().getRegistryName().getPath(), "ancient_nord_greatsword");
            singleTexture(ANCIENT_NORD_WAR_AXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/ancient_nord_war_axe"));
            singleTexture(ANCIENT_NORD_ARROW.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/ancient_nord_arrow"));
            // Daedric
            singleTexture(DAEDRIC_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/daedric_helmet"));
            singleTexture(DAEDRIC_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/daedric_chestplate"));
            singleTexture(DAEDRIC_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/daedric_leggings"));
            singleTexture(DAEDRIC_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/daedric_boots"));
            singleTexture(DAEDRIC_DAGGER.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/daedric_dagger"));
            singleTexture(DAEDRIC_SWORD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/daedric_sword"));
            singleTexture(DAEDRIC_BATTLEAXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/daedric_battleaxe"));
            bow(DAEDRIC_BOW.get().getRegistryName().getPath(), "daedric_bow");
            twoHanded(DAEDRIC_GREATSWORD.get().getRegistryName().getPath(), "daedric_greatsword");
            singleTexture(DAEDRIC_MACE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/daedric_mace"));
            singleTexture(DAEDRIC_WAR_AXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/daedric_war_axe"));
            twoHanded(DAEDRIC_WARHAMMER.get().getRegistryName().getPath(), "daedric_warhammer");
            singleTexture(DAEDRIC_ARROW.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/daedric_arrow"));
            // Dragonbone
            singleTexture(DRAGONBONE_DAGGER.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/dragonbone_dagger"));
            singleTexture(DRAGONBONE_SWORD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/dragonbone_sword"));
            singleTexture(DRAGONBONE_BATTLEAXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/dragonbone_battleaxe"));
            bow(DRAGONBONE_BOW.get().getRegistryName().getPath(), "dragonbone_bow");
            twoHanded(DRAGONBONE_GREATSWORD.get().getRegistryName().getPath(), "dragonbone_greatsword");
            singleTexture(DRAGONBONE_MACE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/dragonbone_mace"));
            singleTexture(DRAGONBONE_WAR_AXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/dragonbone_war_axe"));
            twoHanded(DRAGONBONE_WARHAMMER.get().getRegistryName().getPath(), "dragonbone_warhammer");
            singleTexture(DRAGONBONE_ARROW.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/dragonbone_arrow"));
            // Dwarven
            singleTexture(DWARVEN_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/dwarven_helmet"));
            singleTexture(DWARVEN_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/dwarven_chestplate"));
            singleTexture(DWARVEN_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/dwarven_leggings"));
            singleTexture(DWARVEN_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/dwarven_boots"));
            singleTexture(DWARVEN_DAGGER.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/dwarven_dagger"));
            singleTexture(DWARVEN_SWORD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/dwarven_sword"));
            singleTexture(DWARVEN_BATTLEAXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/dwarven_battleaxe"));
            bow(DWARVEN_BOW.get().getRegistryName().getPath(), "dwarven_bow");
            twoHanded(DWARVEN_GREATSWORD.get().getRegistryName().getPath(), "dwarven_greatsword");
            singleTexture(DWARVEN_MACE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/dwarven_mace"));
            singleTexture(DWARVEN_WAR_AXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/dwarven_war_axe"));
            twoHanded(DWARVEN_WARHAMMER.get().getRegistryName().getPath(), "dwarven_warhammer");
            singleTexture(DWARVEN_ARROW.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/dwarven_arrow"));
            // Ebony
            singleTexture(EBONY_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/ebony_helmet"));
            singleTexture(EBONY_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/ebony_chestplate"));
            singleTexture(EBONY_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/ebony_leggings"));
            singleTexture(EBONY_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/ebony_boots"));
            singleTexture(EBONY_DAGGER.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/ebony_dagger"));
            singleTexture(EBONY_SWORD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/ebony_sword"));
            singleTexture(EBONY_BATTLEAXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/ebony_battleaxe"));
            bow(EBONY_BOW.get().getRegistryName().getPath(), "ebony_bow");
            twoHanded(EBONY_GREATSWORD.get().getRegistryName().getPath(), "ebony_greatsword");
            singleTexture(EBONY_MACE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/ebony_mace"));
            singleTexture(EBONY_WAR_AXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/ebony_war_axe"));
            twoHanded(EBONY_WARHAMMER.get().getRegistryName().getPath(), "ebony_warhammer");
            singleTexture(EBONY_ARROW.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/ebony_arrow"));
            // Elven
            singleTexture(ELVEN_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/elven_helmet"));
            singleTexture(ELVEN_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/elven_chestplate"));
            singleTexture(ELVEN_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/elven_leggings"));
            singleTexture(ELVEN_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/elven_boots"));
            singleTexture(ELVEN_DAGGER.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/elven_dagger"));
            singleTexture(ELVEN_SWORD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/elven_sword"));
            singleTexture(ELVEN_BATTLEAXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/elven_battleaxe"));
            bow(ELVEN_BOW.get().getRegistryName().getPath(), "elven_bow");
            twoHanded(ELVEN_GREATSWORD.get().getRegistryName().getPath(), "elven_greatsword");
            singleTexture(ELVEN_MACE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/elven_mace"));
            singleTexture(ELVEN_WAR_AXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/elven_war_axe"));
            twoHanded(ELVEN_WARHAMMER.get().getRegistryName().getPath(), "elven_warhammer");
            singleTexture(ELVEN_ARROW.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/elven_arrow"));
            // Falmer
            singleTexture(FALMER_ARROW.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/falmer_arrow"));
            singleTexture(FALMER_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/falmer_helmet"));
            singleTexture(FALMER_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/falmer_chestplate"));
            singleTexture(FALMER_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/falmer_leggings"));
            singleTexture(FALMER_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/falmer_boots"));
            singleTexture(FALMER_SWORD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/falmer_sword"));
            bow(FALMER_BOW.get().getRegistryName().getPath(), "falmer_bow");
            singleTexture(FALMER_WAR_AXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/falmer_war_axe"));
            // Glass
            singleTexture(GLASS_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/glass_helmet"));
            singleTexture(GLASS_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/glass_chestplate"));
            singleTexture(GLASS_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/glass_leggings"));
            singleTexture(GLASS_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/glass_boots"));
            singleTexture(GLASS_DAGGER.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/glass_dagger"));
            singleTexture(GLASS_SWORD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/glass_sword"));
            singleTexture(GLASS_BATTLEAXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/glass_battleaxe"));
            bow(GLASS_BOW.get().getRegistryName().getPath(), "glass_bow");
            twoHanded(GLASS_GREATSWORD.get().getRegistryName().getPath(), "glass_greatsword");
            singleTexture(GLASS_MACE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/glass_mace"));
            singleTexture(GLASS_WAR_AXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/glass_war_axe"));
            twoHanded(GLASS_WARHAMMER.get().getRegistryName().getPath(), "glass_warhammer");
            singleTexture(GLASS_ARROW.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/glass_arrow"));
            // Imperial
            singleTexture(IMPERIAL_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/imperial_helmet"));
            singleTexture(IMPERIAL_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/imperial_chestplate"));
            singleTexture(IMPERIAL_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/imperial_leggings"));
            singleTexture(IMPERIAL_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/imperial_boots"));
            singleTexture(IMPERIAL_SWORD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/imperial_sword"));
            // Iron
            singleTexture(IRON_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/iron_helmet"));
            singleTexture(IRON_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/iron_chestplate"));
            singleTexture(IRON_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/iron_leggings"));
            singleTexture(IRON_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/iron_boots"));
            singleTexture(IRON_DAGGER.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/iron_dagger"));
            singleTexture(IRON_SWORD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/iron_sword"));
            singleTexture(IRON_BATTLEAXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/iron_battleaxe"));
            twoHanded(IRON_GREATSWORD.get().getRegistryName().getPath(), "iron_greatsword");
            singleTexture(IRON_MACE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/iron_mace"));
            singleTexture(IRON_WAR_AXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/iron_war_axe"));
            twoHanded(IRON_WARHAMMER.get().getRegistryName().getPath(), "iron_warhammer");
            singleTexture(IRON_ARROW.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/iron_arrow"));
            // Orcish
            singleTexture(ORCISH_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/orcish_helmet"));
            singleTexture(ORCISH_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/orcish_chestplate"));
            singleTexture(ORCISH_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/orcish_leggings"));
            singleTexture(ORCISH_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/orcish_boots"));
            singleTexture(ORCISH_DAGGER.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/orcish_dagger"));
            singleTexture(ORCISH_SWORD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/orcish_sword"));
            singleTexture(ORCISH_BATTLEAXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/orcish_battleaxe"));
            bow(ORCISH_BOW.get().getRegistryName().getPath(), "orcish_bow");
            twoHanded(ORCISH_GREATSWORD.get().getRegistryName().getPath(), "orcish_greatsword");
            singleTexture(ORCISH_MACE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/orcish_mace"));
            singleTexture(ORCISH_WAR_AXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/orcish_war_axe"));
            twoHanded(ORCISH_WARHAMMER.get().getRegistryName().getPath(), "orcish_warhammer");
            singleTexture(ORCISH_ARROW.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/orcish_arrow"));
            // Steel
//            singleTexture(STEEL_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
//                    "layer0", modLoc("item/steel_helmet"));
//            singleTexture(STEEL_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
//                    "layer0", modLoc("item/steel_chestplate"));
//            singleTexture(STEEL_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
//                    "layer0", modLoc("item/steel_leggings"));
//            singleTexture(STEEL_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
//                    "layer0", modLoc("item/steel_boots"));
            singleTexture(STEEL_DAGGER.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/steel_dagger"));
            singleTexture(STEEL_SWORD.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/steel_sword"));
            singleTexture(STEEL_BATTLEAXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/steel_battleaxe"));
            twoHanded(STEEL_GREATSWORD.get().getRegistryName().getPath(), "steel_greatsword");
            singleTexture(STEEL_MACE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/steel_mace"));
            singleTexture(STEEL_WAR_AXE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                    "layer0", modLoc("item/steel_war_axe"));
            twoHanded(STEEL_WARHAMMER.get().getRegistryName().getPath(), "steel_warhammer");
            singleTexture(STEEL_ARROW.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/steel_arrow"));
            // Stormcloak
            singleTexture(STORMCLOAK_OFFICER_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/stormcloak_officer_helmet"));
            singleTexture(STORMCLOAK_OFFICER_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/stormcloak_officer_chestplate"));
            singleTexture(STORMCLOAK_OFFICER_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/stormcloak_officer_leggings"));
            singleTexture(STORMCLOAK_OFFICER_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/stormcloak_officer_boots"));

            // Misc
            // Hide armor
            singleTexture(HIDE_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/hide_helmet"));
            singleTexture(HIDE_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/hide_chestplate"));
            singleTexture(HIDE_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/hide_leggings"));
            singleTexture(HIDE_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/hide_boots"));
            // Bows
            bow(HUNTING_BOW.get().getRegistryName().getPath(), "hunting_bow");
            bow(LONGBOW.get().getRegistryName().getPath(), "longbow");
            // Scaled armor
            singleTexture(SCALED_HELMET.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/scaled_helmet"));
            singleTexture(SCALED_CHESTPLATE.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/scaled_chestplate"));
            singleTexture(SCALED_LEGGINGS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/scaled_leggings"));
            singleTexture(SCALED_BOOTS.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/scaled_boots"));
            // Staff
            singleTexture(STAFF.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/staff"));

            singleTexture(FIREBALL_SPELLBOOK.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/spellbook"));
            singleTexture(TURN_UNDEAD_SPELLBOOK.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/spellbook"));
            singleTexture(CONJURE_FAMILIAR_SPELLBOOK.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/spellbook"));
            singleTexture(HEALING_SPELLBOOK.get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/spellbook"));

        }

        protected void twoHanded(String name, String itemName) {
            singleTexture(name+"_no_use", modLoc("item/greatsword_no_use"), //new ResourceLocation("item/generated"),
                    "layer0", modLoc("item/" + itemName)).texture("layer1", mcLoc("item/barrier"));

            singleTexture(name+"_blocking", modLoc("item/greatsword_blocking"),
                    "layer0", modLoc("item/" + itemName));

            singleTexture(name, modLoc("item/greatsword"),
                    "layer0", modLoc("item/" + itemName))
                    .override().predicate(new ResourceLocation(Skyrimcraft.MODID, "no_use"), 1.0f)
                        .model(getExistingFile(modLoc("item/" + itemName + "_no_use"))).end()
                    .override().predicate(modLoc("blocking"), 1.0f)
                        .model(getExistingFile(modLoc("item/" + itemName + "_blocking"))).end();
        }

        protected void bow(String name, String itemName) {
            singleTexture(name+"_pulling_0", mcLoc("item/bow"),
                    "layer0", modLoc("item/" + itemName + "_pulling_0"));
            singleTexture(name+"_pulling_1", mcLoc("item/bow"),
                    "layer0", modLoc("item/" + itemName + "_pulling_1"));
            singleTexture(name+"_pulling_2", mcLoc("item/bow"),
                    "layer0", modLoc("item/" + itemName + "_pulling_2"));

            singleTexture(name, mcLoc("item/bow"),
                    "layer0", modLoc("item/" + itemName))
                    .override().predicate(modLoc("pulling"), 1.0f)
                    .model(getExistingFile(modLoc("item/" + itemName + "_pulling_0"))).end()
                    .override().predicate(modLoc("pulling"), 1.0f).predicate(mcLoc("pull"), 0.65f)
                    .model(getExistingFile(modLoc("item/" + itemName + "_pulling_1"))).end()
                    .override().predicate(modLoc("pulling"), 1.0f).predicate(mcLoc("pull"), 0.9f)
                    .model(getExistingFile(modLoc("item/" + itemName + "_pulling_2"))).end();
        }
    }
}