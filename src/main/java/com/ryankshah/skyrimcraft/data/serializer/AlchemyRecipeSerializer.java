package com.ryankshah.skyrimcraft.data.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.util.AlchemyRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class AlchemyRecipeSerializer<T extends AlchemyRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<AlchemyRecipe>
{
    private ResourceLocation registryName;

    public AlchemyRecipeSerializer() {
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, AlchemyRecipe alchemyRecipe) {
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

        return new AlchemyRecipe.Builder(category, stackToCreate, level, xp, recipeItems).build(resourceLocation);
    }

    @Override
    public AlchemyRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf buf) {
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