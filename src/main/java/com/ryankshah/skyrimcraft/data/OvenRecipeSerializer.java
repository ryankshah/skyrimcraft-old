package com.ryankshah.skyrimcraft.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.util.OvenRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class OvenRecipeSerializer<T extends OvenRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<OvenRecipe>
{
    private ResourceLocation registryName;

    public OvenRecipeSerializer() {
    }

    @Override
    public void toNetwork(PacketBuffer buf, OvenRecipe ovenRecipe) {
        buf.writeUtf(ovenRecipe.getCategory());

        if (ovenRecipe.getResult() != null) {
            buf.writeItemStack(ovenRecipe.getResult(), false);
        }

        if (ovenRecipe.getRecipeItems() != null && !ovenRecipe.getRecipeItems().isEmpty()) {
            buf.writeInt(ovenRecipe.getRecipeItems().size());
            for (ItemStack stack : ovenRecipe.getRecipeItems()) {
                buf.writeItemStack(stack, false);
            }
        }

        buf.writeInt(ovenRecipe.getRequiredLevel());
        buf.writeInt(ovenRecipe.getXpGained());
    }

    @Override
    public OvenRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonobject) {
        ItemStack stackToCreate = ItemStack.EMPTY;
        List<ItemStack> recipeItems = new ArrayList<>();
        String category = "";
        int level = 0, xp = 0;

        if (!jsonobject.has("output") || !jsonobject.has("recipe")) {
            throw new IllegalStateException("Incorrect json for an Oven Recipe!");
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

        return new OvenRecipe.Builder(category, stackToCreate, level, xp, recipeItems).build(resourceLocation);
    }

    @Override
    public OvenRecipe fromNetwork(ResourceLocation resourceLocation, PacketBuffer buf) {
        List<ItemStack> recipeItems = new ArrayList<>();

        String category = buf.readUtf();

        ItemStack stackToCreate = buf.readItem();

        int length = buf.readInt();
        for (int i = 0; i < length; i++) {
            recipeItems.add(buf.readItem());
        }

        int level = buf.readInt();
        int xp = buf.readInt();

        return new OvenRecipe.Builder(category, stackToCreate, xp, level, recipeItems).build(resourceLocation);
    }
}