package com.ryankshah.skyrimcraft.util.modifier;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraftforge.common.loot.LootModifier;
import org.antlr.v4.runtime.misc.NotNull;

public abstract class MobLootModifier extends LootModifier
{
    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected MobLootModifier(ILootCondition[] conditionsIn) {
            super(conditionsIn);
    }


    @NotNull
    protected static ItemStack getItemStackWithLooting(LootContext context, RandomValueRange hideDropRange, Item loot) {
        LootingEnchantBonus leb = (LootingEnchantBonus) new LootingEnchantBonus.Builder(hideDropRange).setLimit((int) hideDropRange.getMax()).build();
        ItemStack item = leb.apply(new ItemStack(loot), context);
        return item;
    }

    public static class AdditionalItems {
        public Item item;
        public RandomValueRange range;
        public float change;
        public boolean useLootEnchant;

        public AdditionalItems(Item itemIN, RandomValueRange rangeIn, float changeIn, boolean useLootEnchantIn) {
            this.item = itemIN;
            this.range = rangeIn;
            this.change = changeIn;
            this.useLootEnchant = useLootEnchantIn;
        }
    }
}