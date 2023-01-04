package com.ryankshah.skyrimcraft.data.provider;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.data.loot_table.modifier.ChestLootModifier;
import com.ryankshah.skyrimcraft.data.loot_table.modifier.PassiveEntityLootModifier;
import com.ryankshah.skyrimcraft.item.ModItems;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.NonNullList;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModGlobalLootTableProvider extends GlobalLootModifierProvider
{
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, Skyrimcraft.MODID);
    public static final RegistryObject<PassiveEntityLootModifier.Serializer> PASSIVE_ENTITY = LOOT_MODIFIERS.register("passive_entity", PassiveEntityLootModifier.Serializer::new);
    public static final RegistryObject<ChestLootModifier.Serializer> CHEST_LOOT = LOOT_MODIFIERS.register("chest_loot", ChestLootModifier.Serializer::new);
    public static final RegistryObject<AdditionalDropsForBlocks.Serializer> BLOCK_LOOT = LOOT_MODIFIERS.register("block_loot", AdditionalDropsForBlocks.Serializer::new);

    public ModGlobalLootTableProvider(DataGenerator gen) {
        super(gen, Skyrimcraft.MODID);
    }

    @Override
    protected void start() {
        addMobLootModifiers();
        addChestLootModifiers();
        addBlockLootModifiers();
    }

    protected void addBlockLootModifiers() {
        add("grass_pod_from_small_vegetation", BLOCK_LOOT.get(), new AdditionalDropsForBlocks(
                new LootItemCondition[]{AlternativeLootItemCondition.alternative(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS),
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.LARGE_FERN)
                ).build(), LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.25f).build()
                }, NonNullList.of(new ItemStack(ModItems.GRASS_POD.get())) //NonNullList.of(ItemStack.EMPTY, new ItemStack(ModItems.GRASS_POD.get()))
        ));
    }

    protected void addChestLootModifiers() {
        NonNullList<ChestLootModifier.ChestItem> globalChestDrops = NonNullList.create();
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.FIREBALL_SPELLBOOK.get(), 1, 1, 0.365f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.TURN_UNDEAD_SPELLBOOK.get(), 1, 1, 0.365f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModBlocks.GARLIC.get(), 1, 3, 0.685f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModBlocks.TOMATO_SEEDS.get(), 1, 3, 0.685f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.MINOR_MAGICKA_POTION.get(), 2, 3, 0.625f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.MAGICKA_POTION.get(), 1, 2, 0.5f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.PLENTIFUL_MAGICKA_POTION.get(), 1, 2, 0.425f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.VIGOROUS_MAGICKA_POTION.get(), 1, 1, 0.35f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.EXTREME_MAGICKA_POTION.get(), 1, 1, 0.3f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.ULTIMATE_MAGICKA_POTION.get(), 0, 1, 0.2f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.SALT_PILE.get(), 1, 3, 0.425f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.BRIAR_HEART.get(), 1, 2, 0.425f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.DWARVEN_OIL.get(), 1, 2, 0.3825f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.FIRE_SALTS.get(), 1, 2, 0.325f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.MORA_TAPINELLA.get(), 1, 2, 0.375f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.GIANTS_TOE.get(), 1, 2, 0.2f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.VAMPIRE_DUST.get(), 1, 2, 0.3f));
        globalChestDrops.add(new ChestLootModifier.ChestItem(ModItems.CREEP_CLUSTER.get(), 1, 2, 0.375f));


        for(Map.Entry<String, ResourceLocation> entry : getChestTables().entrySet()) {
            add(entry.getValue().getPath(), CHEST_LOOT.get(), new ChestLootModifier(
                    new LootItemCondition[] {
                            LootTableIdCondition.builder(entry.getValue()).build()
                    },
                    globalChestDrops
            ));
        }

        NonNullList<ChestLootModifier.ChestItem> villageChestDrops = NonNullList.create();
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModBlocks.GARLIC.get(), 1, 3, 0.685f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModBlocks.TOMATO_SEEDS.get(), 1, 3, 0.685f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.MINOR_MAGICKA_POTION.get(), 2, 3, 0.625f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.MAGICKA_POTION.get(), 1, 2, 0.5f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.PLENTIFUL_MAGICKA_POTION.get(), 1, 2, 0.425f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.SALT_PILE.get(), 1, 3, 0.425f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.BRIAR_HEART.get(), 1, 2, 0.425f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.DWARVEN_OIL.get(), 1, 2, 0.3825f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.FIRE_SALTS.get(), 1, 2, 0.325f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.MORA_TAPINELLA.get(), 1, 2, 0.375f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.GIANTS_TOE.get(), 1, 2, 0.2f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.VAMPIRE_DUST.get(), 1, 2, 0.3f));
        villageChestDrops.add(new ChestLootModifier.ChestItem(ModItems.CREEP_CLUSTER.get(), 1, 2, 0.375f));

        for(Map.Entry<String, ResourceLocation> entry : getVillageChestTables().entrySet()) {
            add(entry.getValue().getPath(), CHEST_LOOT.get(), new ChestLootModifier(
                    new LootItemCondition[] {
                            LootTableIdCondition.builder(entry.getValue()).build()
                    },
                    villageChestDrops
            ));
        }
    }

    protected void addMobLootModifiers() {
        dropsForWitchAndEvoker();

//        NonNullList<MobLootModifier.AdditionalItems> sabreCatDrops = NonNullList.create();
//        sabreCatDrops.add(new MobLootModifier.AdditionalItems(ModItems.SABRE_CAT_TOOTH.get(), new RandomValueRange(1, 1), 0.25f, true));
//        sabreCatDrops.add(new MobLootModifier.AdditionalItems(ModItems.EYE_OF_SABRE_CAT.get(), new RandomValueRange(1, 1), 0.25f, true));
//        //sabreCatDrops.add(new MobLootModifier.AdditionalItems(ModItems.SABRE_CAT_PELT.get(), new RandomValueRange(1, 1), 0.45f, true));
//        add("sabre_cat_modifier", PASSIVE_ENTITY.get(), new PassiveEntityLootModifier(
//                new ILootCondition[]{
//                        EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS,
//                                EntityPredicate.Builder.entity().of(ModEntityType.SABRE_CAT.get()).build()).build()
//                },
//                sabreCatDrops
//        ));

        NonNullList<PassiveEntityLootModifier.AdditionalItems> modSeedDrops = NonNullList.create();
        modSeedDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModBlocks.GARLIC.get(), 1, 2, 0.45f, true));
        modSeedDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModBlocks.TOMATO_SEEDS.get(), 1, 2, 0.45f, true));

        add("parrot_modifier", PASSIVE_ENTITY.get(), new PassiveEntityLootModifier(
                new LootItemCondition[]{
                        LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.PARROT).build()).build()
                },
                modSeedDrops
        ));
        add("rabbit_modifier", PASSIVE_ENTITY.get(), new PassiveEntityLootModifier(
                new LootItemCondition[]{
                        LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.RABBIT).build()).build()
                },
                modSeedDrops
        ));

        NonNullList<PassiveEntityLootModifier.AdditionalItems> salmonRoeDrop = NonNullList.create();
        salmonRoeDrop.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.SALMON_ROE.get(), 1, 2, 0.3f, true));
        add("salmon_modifier", PASSIVE_ENTITY.get(), new PassiveEntityLootModifier(
                new LootItemCondition[]{
                        LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.SALMON).build()).build()
                },
                salmonRoeDrop
        ));
    }

    private void dropsForWitchAndEvoker() {
        NonNullList<PassiveEntityLootModifier.AdditionalItems> witch_evoker_drops = NonNullList.create();
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.FIREBALL_SPELLBOOK.get(), 0, 1, 0.1f, false));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.TURN_UNDEAD_SPELLBOOK.get(), 0, 1, 0.1f, false));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.MAGICKA_POTION.get(), 1, 2, 0.475f, true));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.MINOR_MAGICKA_POTION.get(), 2, 3, 0.625f, true));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.PLENTIFUL_MAGICKA_POTION.get(), 1, 2, 0.425f, true));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.VIGOROUS_MAGICKA_POTION.get(), 1, 1, 0.3f, true));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.EXTREME_MAGICKA_POTION.get(), 1, 1, 0.2f, true));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.ULTIMATE_MAGICKA_POTION.get(), 0, 1, 0.1f, true));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.PHILTER_OF_THE_PHANTOM_POTION.get(), 0, 1, 0.2f, true));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.BRIAR_HEART.get(), 1, 1, 0.4f, true));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.GIANTS_TOE.get(), 1, 1, 0.285f, true));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModBlocks.RED_MOUNTAIN_FLOWER_ITEM.get(), 1, 2, 0.475f, true));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.MORA_TAPINELLA.get(), 0, 1, 0.3f, true));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.VAMPIRE_DUST.get(), 0, 1, 0.3f, true));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.FIRE_SALTS.get(), 1, 1, 0.3f, true));
        witch_evoker_drops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.DWARVEN_OIL.get(), 1, 1, 0.3f, true));

        add("witch_modifier", PASSIVE_ENTITY.get(), new PassiveEntityLootModifier(
                new LootItemCondition[]{
                        LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.WITCH).build()).build()
                },
                witch_evoker_drops
        ));
        add("evoker_modifier", PASSIVE_ENTITY.get(), new PassiveEntityLootModifier(
                new LootItemCondition[]{
                        LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.EVOKER).build()).build()
                },
                witch_evoker_drops
        ));
    }

    private Map<String, ResourceLocation> getChestTables() {
        Map<String, ResourceLocation> chestTables = new HashMap<>();
        chestTables.put("buried_treasure", BuiltInLootTables.BURIED_TREASURE);
        chestTables.put("jungle_temple", BuiltInLootTables.JUNGLE_TEMPLE);
        chestTables.put("simple_dungeon", BuiltInLootTables.SIMPLE_DUNGEON);
        chestTables.put("shipwreck_treasure", BuiltInLootTables.SHIPWRECK_TREASURE);
        chestTables.put("stronghold_crossing", BuiltInLootTables.STRONGHOLD_CROSSING);
        chestTables.put("stronghold_corridor", BuiltInLootTables.STRONGHOLD_CORRIDOR);
        chestTables.put("nether_bridge", BuiltInLootTables.NETHER_BRIDGE);
        chestTables.put("bastion_treasure", BuiltInLootTables.BASTION_TREASURE);
        chestTables.put("stronghold_library", BuiltInLootTables.STRONGHOLD_LIBRARY);
        //chestTables.put("spawn_bonus_chest", LootTables.SPAWN_BONUS_CHEST);
        chestTables.put("ruined_portal", BuiltInLootTables.RUINED_PORTAL);
        chestTables.put("desert_pyramid", BuiltInLootTables.DESERT_PYRAMID);
        chestTables.put("pillager_outpost", BuiltInLootTables.PILLAGER_OUTPOST);
        chestTables.put("underwater_ruin_big", BuiltInLootTables.UNDERWATER_RUIN_BIG);
        chestTables.put("underwater_ruin_small", BuiltInLootTables.UNDERWATER_RUIN_SMALL);

        return chestTables;
    }

    private Map<String, ResourceLocation> getVillageChestTables() {
        Map<String, ResourceLocation> chestTables = new HashMap<>();

        chestTables.put("village_desert_house", BuiltInLootTables.VILLAGE_DESERT_HOUSE);
        chestTables.put("village_plains_house", BuiltInLootTables.VILLAGE_PLAINS_HOUSE);
        chestTables.put("village_savanna_house", BuiltInLootTables.VILLAGE_SAVANNA_HOUSE);
        chestTables.put("village_snowy_house", BuiltInLootTables.VILLAGE_SNOWY_HOUSE);
        chestTables.put("village_taiga_house", BuiltInLootTables.VILLAGE_TAIGA_HOUSE);

        return chestTables;
    }

    @Override
    public String getName() {
        return Skyrimcraft.MODID + "_globalLootTables";
    }


    public static class AdditionalDropsForBlocks extends LootModifier {

        private NonNullList<ItemStack> output;

        /**
         * Constructs a LootModifier.
         *
         * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
         */
        public AdditionalDropsForBlocks(LootItemCondition[] conditionsIn, NonNullList<ItemStack> outputs) {
            super(conditionsIn);
            this.output = outputs;
        }

        @Nonnull
        @Override
        protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            generatedLoot.addAll(output);
            return generatedLoot;
        }

        public static class Serializer extends GlobalLootModifierSerializer<AdditionalDropsForBlocks> {

            @Override
            public AdditionalDropsForBlocks read(ResourceLocation location, JsonObject object, LootItemCondition[] condition) {

                JsonArray array = GsonHelper.getAsJsonArray(object, "items");

                NonNullList<ItemStack> list = NonNullList.create();

                for (JsonElement json : array) {
                    JsonObject jsonObject = (JsonObject) json;
                    list.add(new ItemStack(GsonHelper.getAsItem(jsonObject, "item"), GsonHelper.getAsInt(jsonObject, "count")));
                }

                return new AdditionalDropsForBlocks(condition, list);
            }

            @Override
            public JsonObject write(AdditionalDropsForBlocks instance) {
                JsonObject jsonObject = makeConditions(instance.conditions);
                JsonArray array = new JsonArray();
                for (ItemStack stack : instance.output) {
                    JsonObject jo = new JsonObject();
                    jo.addProperty("item", ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
                    jo.addProperty("count", stack.getCount());
                    array.add(jo);
                }
                jsonObject.add("items", array);

                return jsonObject;
            }

        }
    }
}