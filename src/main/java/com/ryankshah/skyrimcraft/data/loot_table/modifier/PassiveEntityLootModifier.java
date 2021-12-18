package com.ryankshah.skyrimcraft.data.loot_table.modifier;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
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
            LootItemCondition[] conditionsIn,
            NonNullList<AdditionalItems> additionalIN) {
        super(conditionsIn);
        this.additional = additionalIN;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        for (AdditionalItems add : additional) {
            generatedLoot.removeIf(itemStack -> itemStack.getItem() == add.item);
            float rand = context.getRandom().nextFloat();
            float chance = add.chance + Float.parseFloat("0." + context.getLootingModifier());
            if (chance > 1f) {
                chance = 1f;
            }
            if (rand <= chance) {
                ItemStack item = getItemStackWithLooting(context, UniformGenerator.between(add.min, add.max), add.item);
                generatedLoot.add(item);
            }
        }

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<PassiveEntityLootModifier> {

        @Override
        public PassiveEntityLootModifier read(ResourceLocation location, JsonObject json, LootItemCondition[] ailootcondition) {
            JsonArray additionalJson = GsonHelper.getAsJsonArray(json, "additional");
            NonNullList<AdditionalItems> additional = NonNullList.create();

            for (int i = 0; i < additionalJson.size(); i++) {
                JsonObject itemStack = additionalJson.get(i).getAsJsonObject();
                additional.add(
                        new AdditionalItems(
                                ForgeRegistries.ITEMS.getValue(
                                        new ResourceLocation(
                                                GsonHelper.getAsString(itemStack, "item"))
                                ),
                                GsonHelper.getAsInt(itemStack, "minItem"),
                                GsonHelper.getAsInt(itemStack, "maxItem"),
                                GsonHelper.getAsInt(itemStack, "chance"),
                                GsonHelper.getAsBoolean(itemStack, "useLooting")
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
                itemstack.addProperty("minItem", additionalItem.min);
                itemstack.addProperty("maxItem", additionalItem.max);
                itemstack.addProperty("chance", additionalItem.chance);
                itemstack.addProperty("useLooting", additionalItem.useLootEnchant);
                additional.add(itemstack);
            }

            json.add("additional", additional);

            return json;
        }
    }
}