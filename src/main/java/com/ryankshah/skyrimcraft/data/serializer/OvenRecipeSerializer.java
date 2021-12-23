package com.ryankshah.skyrimcraft.data.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.util.OvenRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class OvenRecipeSerializer<T extends OvenRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<OvenRecipe>
{
    private ResourceLocation registryName;

    public OvenRecipeSerializer() {
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, OvenRecipe ovenRecipe) {
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
            String type = GsonHelper.getAsString(jsonobject, "type");

            category = GsonHelper.getAsString(jsonobject, "category");

            JsonObject output = jsonobject.getAsJsonObject("output");
            stackToCreate = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(output, "item"))), GsonHelper.getAsInt(output, "amount"));

            JsonArray recipe = GsonHelper.getAsJsonArray(jsonobject, "recipe");
            for (JsonElement recipeElement : recipe) {
                JsonObject recipeObj = recipeElement.getAsJsonObject();
                ItemStack recipeItem = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(recipeObj, "item"))), GsonHelper.getAsInt(recipeObj, "amount"));
                recipeItems.add(recipeItem);
            }

            level = GsonHelper.getAsInt(jsonobject, "levelToCreate");
            xp = GsonHelper.getAsInt(jsonobject, "xp");
        }

        return new OvenRecipe.Builder(category, stackToCreate, level, xp, recipeItems).build(resourceLocation);
    }

    @Override
    public OvenRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf buf) {
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