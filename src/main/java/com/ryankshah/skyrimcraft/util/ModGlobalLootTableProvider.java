package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.util.modifier.MobLootModifier;
import com.ryankshah.skyrimcraft.util.modifier.PassiveEntityLootModifier;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModGlobalLootTableProvider extends GlobalLootModifierProvider
{
    public static DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, Skyrimcraft.MODID);
    public static final RegistryObject<PassiveEntityLootModifier.Serializer> PASSIVE_ENTITY = LOOT_MODIFIERS.register("passive_entity", PassiveEntityLootModifier.Serializer::new);

    public ModGlobalLootTableProvider(DataGenerator gen) {
        super(gen, Skyrimcraft.MODID);
    }

    @Override
    protected void start() {
        NonNullList<PassiveEntityLootModifier.AdditionalItems> witchBookDrops = NonNullList.create();
        witchBookDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.FIREBALL_SPELLBOOK.get(), new RandomValueRange(0, 1), 0.25f, false));
        witchBookDrops.add(new PassiveEntityLootModifier.AdditionalItems(ModItems.TURN_UNDEAD_SPELLBOOK.get(), new RandomValueRange(0, 1), 0.25f, false));

        add("witch_modifier", PASSIVE_ENTITY.get(), new PassiveEntityLootModifier(
                new ILootCondition[]{
                        EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.WITCH).build()).build()
                },
                witchBookDrops
        ));
    }

    @Override
    public String getName() {
        return Skyrimcraft.MODID+"_globalLootTables";
    }
}