package com.ryankshah.skyrimcraft.data.loot_table.modifier;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.loot.LootModifier;
import org.antlr.v4.runtime.misc.NotNull;

public abstract class MobLootModifier extends LootModifier
{
    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected MobLootModifier(LootItemCondition[] conditionsIn) {
            super(conditionsIn);
    }


    @NotNull
    protected static ItemStack getItemStackWithLooting(LootContext context, UniformGenerator hideDropRange, Item loot) {
        LootingEnchantFunction leb = (LootingEnchantFunction) new LootingEnchantFunction.Builder(hideDropRange).build();
        ItemStack item = leb.apply(new ItemStack(loot), context);
        return item;
    }

    public static class AdditionalItems {
        public Item item;
        // public RandomValueBounds range;
        public int min, max;
        public float chance;
        public boolean useLootEnchant;

        public AdditionalItems(Item itemIN, int min, int max, float chanceIn, boolean useLootEnchantIn) {
            this.item = itemIN;
            this.min = min;
            this.max = max;
            this.chance = chanceIn;
            this.useLootEnchant = useLootEnchantIn;
        }
    }
}