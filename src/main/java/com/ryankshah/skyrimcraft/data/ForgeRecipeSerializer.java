package com.ryankshah.skyrimcraft.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.util.ForgeRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ForgeRecipeSerializer<T extends ForgeRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ForgeRecipe>
{
    private ResourceLocation registryName;

    public ForgeRecipeSerializer() {
    }

//    public JsonObject serializeToJson(ForgeRecipe recipe) {
//        JsonObject jsonobject = new JsonObject();
//
//        jsonobject.addProperty("type", Skyrimcraft.MODID+":forge_recipe");
//
//        JsonObject output = new JsonObject();
//        output.addProperty("item", stackToCreate.getItem().getRegistryName().toString());
//        output.addProperty("amount", stackToCreate.getCount());
//        jsonobject.add("output", output);
//
//        JsonArray recipeItems = new JsonArray();
//        for(ItemStack stack : this.recipeItems) {
//            JsonObject stackObj = new JsonObject();
//            stackObj.addProperty("item", stack.getItem().getRegistryName().toString());
//            stackObj.addProperty("amount", stack.getCount());
//            recipeItems.add(stackObj);
//        }
//        jsonobject.add("recipe", recipeItems);
//
//        jsonobject.addProperty("levelToCreate", level);
//        jsonobject.addProperty("xp", xp);
//        return jsonobject;
//    }


    @Override
    public void toNetwork(FriendlyByteBuf buf, ForgeRecipe forgeRecipe) {
        buf.writeUtf(forgeRecipe.getCategory());

        if (forgeRecipe.getResult() != null) {
            buf.writeItemStack(forgeRecipe.getResult(), false);
        }

        if (forgeRecipe.getRecipeItems() != null && !forgeRecipe.getRecipeItems().isEmpty()) {
            buf.writeInt(forgeRecipe.getRecipeItems().size());
            for (ItemStack stack : forgeRecipe.getRecipeItems()) {
                buf.writeItemStack(stack, false);
            }
        }

        buf.writeInt(forgeRecipe.getRequiredLevel());
        buf.writeInt(forgeRecipe.getXpGained());
    }

    @Override
    public ForgeRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonobject) {
        ItemStack stackToCreate = ItemStack.EMPTY;
        List<ItemStack> recipeItems = new ArrayList<>();
        String category = "";
        int level = 0, xp = 0;

        if (!jsonobject.has("output") || !jsonobject.has("recipe")) {
            throw new IllegalStateException("Incorrect json for a Forge Recipe!");
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

        return new ForgeRecipe.Builder(category, stackToCreate, level, xp, recipeItems).build(resourceLocation);
    }

    @Override
    public ForgeRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf buf) {
        List<ItemStack> recipeItems = new ArrayList<>();

        String category = buf.readUtf();

        ItemStack stackToCreate = buf.readItem();

        int length = buf.readInt();
        for (int i = 0; i < length; i++) {
            recipeItems.add(buf.readItem());
        }

        int level = buf.readInt();
        int xp = buf.readInt();

        return new ForgeRecipe.Builder(category, stackToCreate, xp, level, recipeItems).build(resourceLocation);
    }
}