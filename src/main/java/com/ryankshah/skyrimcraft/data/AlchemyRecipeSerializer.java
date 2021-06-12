package com.ryankshah.skyrimcraft.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.util.AlchemyRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class AlchemyRecipeSerializer<T extends AlchemyRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AlchemyRecipe>
{
    private ResourceLocation registryName;

    public AlchemyRecipeSerializer() {
    }

    @Override
    public void toNetwork(PacketBuffer buf, AlchemyRecipe alchemyRecipe) {
        buf.writeUtf(alchemyRecipe.getCategory());

        if (alchemyRecipe.getResult() != null) {
            buf.writeItemStack(alchemyRecipe.getResult(), false);
        }

        if (alchemyRecipe.getRecipeItems() != null && !alchemyRecipe.getRecipeItems().isEmpty()) {
            buf.writeInt(alchemyRecipe.getRecipeItems().size());
            for (ItemStack stack : alchemyRecipe.getRecipeItems()) {
                buf.writeItemStack(stack, false);
            }
        }

        buf.writeInt(alchemyRecipe.getRequiredLevel());
        buf.writeInt(alchemyRecipe.getXpGained());
    }

    @Override
    public AlchemyRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonobject) {
        ItemStack stackToCreate = ItemStack.EMPTY;
        List<ItemStack> recipeItems = new ArrayList<>();
        String category = "";
        int level = 0, xp = 0;

        if (!jsonobject.has("output") || !jsonobject.has("recipe")) {
            throw new IllegalStateException("Incorrect json for an Alchemy Recipe!");
        } else {
            String type = JSONUtils.getAsString(jsonobject, "type");

            category = JSONUtils.getAsString(jsonobject, "category");

            JsonObject output = jsonobject.getAsJsonObject("output");
            stackToCreate = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getAsString(output, "item"))), JSONUtils.getAsInt(output, "amount"));

            JsonArray recipe = JSONUtils.getAsJsonArray(jsonobject, "recipe");
            for (JsonElement recipeElement : recipe) {
                JsonObject recipeObj = recipeElement.getAsJsonObject();
                ItemStack recipeItem = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getAsString(recipeObj, "item"))), JSONUtils.getAsInt(recipeObj, "amount"));
                recipeItems.add(recipeItem);
            }

            level = JSONUtils.getAsInt(jsonobject, "levelToCreate");
            xp = JSONUtils.getAsInt(jsonobject, "xp");
        }

        return new AlchemyRecipe.Builder(category, stackToCreate, level, xp, recipeItems).build(resourceLocation);
    }

    @Override
    public AlchemyRecipe fromNetwork(ResourceLocation resourceLocation, PacketBuffer buf) {
        List<ItemStack> recipeItems = new ArrayList<>();

        String category = buf.readUtf();

        ItemStack stackToCreate = buf.readItem();

        int length = buf.readInt();
        for (int i = 0; i < length; i++) {
            recipeItems.add(buf.readItem());
        }

        int level = buf.readInt();
        int xp = buf.readInt();

        return new AlchemyRecipe.Builder(category, stackToCreate, xp, level, recipeItems).build(resourceLocation);
    }
}