package com.ryankshah.skyrimcraft.data.modifier;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class PassiveEntityLootModifier extends MobLootModifier
{
    public NonNullList<AdditionalItems> additional;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    public PassiveEntityLootModifier(
            ILootCondition[] conditionsIn,
            NonNullList<AdditionalItems> additionalIN) {
        super(conditionsIn);
        this.additional = additionalIN;
    }

    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        for (AdditionalItems add : additional) {
            generatedLoot.removeIf(itemStack -> itemStack.getItem() == add.item);
            float rand = context.getRandom().nextFloat();
            float change = add.change + Float.parseFloat("0." + context.getLootingModifier());
            if (change > 1f) {
                change = 1f;
            }
            if (rand <= change) {
                ItemStack item = getItemStackWithLooting(context, add.range, add.item);
                generatedLoot.add(item);
            }
        }

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<PassiveEntityLootModifier> {

        @Override
        public PassiveEntityLootModifier read(ResourceLocation location, JsonObject json, ILootCondition[] ailootcondition) {
            JsonArray additionalJson = JSONUtils.getAsJsonArray(json, "additional");
            NonNullList<AdditionalItems> additional = NonNullList.create();

            for (int i = 0; i < additionalJson.size(); i++) {
                JsonObject itemStack = additionalJson.get(i).getAsJsonObject();
                RandomValueRange range = new RandomValueRange(JSONUtils.getAsInt(itemStack, "minItem"), JSONUtils.getAsInt(itemStack, "maxItem"));
                additional.add(
                        new AdditionalItems(
                                ForgeRegistries.ITEMS.getValue(
                                        new ResourceLocation(
                                                JSONUtils.getAsString(itemStack, "item"))
                                ),
                                range,
                                JSONUtils.getAsInt(itemStack, "change"),
                                JSONUtils.getAsBoolean(itemStack, "useLooting")
                        )
                );
            }

            return new PassiveEntityLootModifier(ailootcondition, additional);
        }

        @Override
        public JsonObject write(PassiveEntityLootModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            JsonArray additional = new JsonArray();

            for (AdditionalItems additionalItem : instance.additional) {
                JsonObject itemstack = new JsonObject();
                itemstack.addProperty("item", ForgeRegistries.ITEMS.getKey(additionalItem.item).toString());
                itemstack.addProperty("minItem", additionalItem.range.getMin());
                itemstack.addProperty("maxItem", additionalItem.range.getMax());
                itemstack.addProperty("change", additionalItem.change);
                itemstack.addProperty("useLooting", additionalItem.useLootEnchant);
                additional.add(itemstack);
            }

            json.add("additional", additional);

            return json;
        }
    }
}