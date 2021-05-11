package com.ryankshah.skyrimcraft.data;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.data.loot_table.modifier.ChestLootModifier;
import com.ryankshah.skyrimcraft.data.loot_table.modifier.PassiveEntityLootModifier;
import com.ryankshah.skyrimcraft.util.ModBlocks;
import com.ryankshah.skyrimcraft.util.ModItems;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ModGlobalLootTableProvider extends GlobalLootModifierProvider
{
    public static DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, Skyrimcraft.MODID);
    public static final RegistryObject<PassiveEntityLootModifier.Serializer> PASSIVE_ENTITY = LOOT_MODIFIERS.register("passive_entity", PassiveEntityLootModifier.Serializer::new);
    public static final RegistryObject<ChestLootModifier.Serializer> CHEST_LOOT = LOOT_MODIFIERS.register("chest_loot", ChestLootModifier.Serializer::new);

    public ModGlobalLootTableProvider(DataGenerator gen) {
        super(gen, Skyrimcraft.MODID);
    }

    @Override
    protected void start() {
        addMobLootModifiers();
        addChestLootModifiers();
    }

    protected void addChestLootModifiers() {
        NonNullList<ChestLootModifier.ChestItem> globalChestDrops = NonNullList.create();
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.FIREBALL_SPELLBOOK.get(), new RandomValueRange(1, 1), 0.365f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.TURN_UNDEAD_SPELLBOOK.get(), new RandomValueRange(1, 1), 0.365f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModBlocks.GARLIC.get(), new RandomValueRange(1, 3), 0.685f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModBlocks.TOMATO_SEEDS.get(), new RandomValueRange(1, 3), 0.685f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.MINOR_MAGICKA_POTION.get(), new RandomValueRange(2, 3), 0.625f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.MAGICKA_POTION.get(), new RandomValueRange(1, 2), 0.5f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.PLENTIFUL_MAGICKA_POTION.get(), new RandomValueRange(1, 2), 0.425f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.VIGOROUS_MAGICKA_POTION.get(), new RandomValueRange(1, 1), 0.35f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.EXTREME_MAGICKA_POTION.get(), new RandomValueRange(1, 1), 0.3f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.ULTIMATE_MAGICKA_POTION.get(), new RandomValueRange(0, 1), 0.2f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.SALT_PILE.get(), new RandomValueRange(1, 3), 0.425f));


        for(Map.Entry<String, ResourceLocation> entry : getChestTables().entrySet()) {
            add(entry.getValue().getPath(), CHEST_LOOT.get(), new ChestLootModifier(
                    new ILootCondition[] {
                            LootTableIdCondition.builder(entry.getValue()).build()
                    },
                    globalChestDrops
            ));
        }

        NonNullList<ChestLootModifier.ChestItem> villageChestDrops = NonNullList.create();
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModBlocks.GARLIC.get(), new RandomValueRange(1, 3), 0.685f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModBlocks.TOMATO_SEEDS.get(), new RandomValueRange(1, 3), 0.685f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.MINOR_MAGICKA_POTION.get(), new RandomValueRange(2, 3), 0.625f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.MAGICKA_POTION.get(), new RandomValueRange(1, 2), 0.5f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.PLENTIFUL_MAGICKA_POTION.get(), new RandomValueRange(1, 2), 0.425f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.SALT_PILE.get(), new RandomValueRange(1, 3), 0.425f));

        for(Map.Entry<String, ResourceLocation> entry : getVillageChestTables().entrySet()) {
            add(entry.getValue().getPath(), CHEST_LOOT.get(), new ChestLootModifier(
                    new ILootCondition[] {
                            LootTableIdCondition.builder(entry.getValue()).build()
                    },
                    villageChestDrops
            ));
        }
    }

    protected void addMobLootModifiers() {
        NonNullList<PassiveEntityLootModifier.AdditionalItems> spellBookDrops = NonNullList.create();
        spellBookDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.FIREBALL_SPELLBOOK.get(), new RandomValueRange(0, 1), 0.4f, false));
        spellBookDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.TURN_UNDEAD_SPELLBOOK.get(), new RandomValueRange(0, 1), 0.4f, false));
        spellBookDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.MAGICKA_POTION.get(), new RandomValueRange(1, 2), 0.5f, true));
        spellBookDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.MINOR_MAGICKA_POTION.get(), new RandomValueRange(2, 3), 0.625f, true));
        spellBookDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.PLENTIFUL_MAGICKA_POTION.get(), new RandomValueRange(1, 2), 0.425f, true));
        spellBookDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.VIGOROUS_MAGICKA_POTION.get(), new RandomValueRange(1, 1), 0.35f, true));
        spellBookDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.EXTREME_MAGICKA_POTION.get(), new RandomValueRange(1, 1), 0.3f, true));
        spellBookDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.ULTIMATE_MAGICKA_POTION.get(), new RandomValueRange(0, 1), 0.2f, true));

        add("witch_modifier", PASSIVE_ENTITY.get(), new PassiveEntityLootModifier(
                new ILootCondition[]{
                        EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.WITCH).build()).build()
                },
                spellBookDrops
        ));
        add("evoker_modifier", PASSIVE_ENTITY.get(), new PassiveEntityLootModifier(
                new ILootCondition[]{
                        EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.EVOKER).build()).build()
                },
                spellBookDrops
        ));

        NonNullList<PassiveEntityLootModifier.AdditionalItems> modSeedDrops = NonNullList.create();
        modSeedDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModBlocks.GARLIC.get(), new RandomValueRange(1, 2), 0.45f, false));
        modSeedDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModBlocks.TOMATO_SEEDS.get(), new RandomValueRange(1, 2), 0.45f, false));

        add("parrot_modifier", PASSIVE_ENTITY.get(), new PassiveEntityLootModifier(
                new ILootCondition[]{
                        EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.PARROT).build()).build()
                },
                modSeedDrops
        ));
        add("rabbit_modifier", PASSIVE_ENTITY.get(), new PassiveEntityLootModifier(
                new ILootCondition[]{
                        EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.RABBIT).build()).build()
                },
                modSeedDrops
        ));
    }

    private Map<String, ResourceLocation> getChestTables() {
        Map<String, ResourceLocation> chestTables = new HashMap<>();
        chestTables.put("buried_treasure", LootTables.BURIED_TREASURE);
        chestTables.put("jungle_temple", LootTables.JUNGLE_TEMPLE);
        chestTables.put("simple_dungeon", LootTables.SIMPLE_DUNGEON);
        chestTables.put("shipwreck_treasure", LootTables.SHIPWRECK_TREASURE);
        chestTables.put("stronghold_crossing", LootTables.STRONGHOLD_CROSSING);
        chestTables.put("stronghold_corridor", LootTables.STRONGHOLD_CORRIDOR);
        chestTables.put("nether_bridge", LootTables.NETHER_BRIDGE);
        chestTables.put("bastion_treasure", LootTables.BASTION_TREASURE);
        chestTables.put("stronghold_library", LootTables.STRONGHOLD_LIBRARY);
        //chestTables.put("spawn_bonus_chest", LootTables.SPAWN_BONUS_CHEST);
        chestTables.put("ruined_portal", LootTables.RUINED_PORTAL);
        chestTables.put("desert_pyramid", LootTables.DESERT_PYRAMID);
        chestTables.put("pillager_outpost", LootTables.PILLAGER_OUTPOST);
        chestTables.put("underwater_ruin_big", LootTables.UNDERWATER_RUIN_BIG);
        chestTables.put("underwater_ruin_small", LootTables.UNDERWATER_RUIN_SMALL);

        return chestTables;
    }

    private Map<String, ResourceLocation> getVillageChestTables() {
        Map<String, ResourceLocation> chestTables = new HashMap<>();

        chestTables.put("village_desert_house", LootTables.VILLAGE_DESERT_HOUSE);
        chestTables.put("village_plains_house", LootTables.VILLAGE_PLAINS_HOUSE);
        chestTables.put("village_savanna_house", LootTables.VILLAGE_SAVANNA_HOUSE);
        chestTables.put("village_snowy_house", LootTables.VILLAGE_SNOWY_HOUSE);
        chestTables.put("village_taiga_house", LootTables.VILLAGE_TAIGA_HOUSE);

        return chestTables;
    }

    @Override
    public String getName() {
        return Skyrimcraft.MODID + "_globalLootTables";
    }
}