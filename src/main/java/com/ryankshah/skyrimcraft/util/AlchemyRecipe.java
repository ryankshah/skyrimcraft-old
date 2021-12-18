package com.ryankshah.skyrimcraft.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.data.ModRecipeSerializers;
import com.ryankshah.skyrimcraft.data.ModRecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class AlchemyRecipe implements Recipe<IAlchemyInventory>
{
    private ResourceLocation id;
    private String category;
    private ItemStack stackToCreate;
    private List<ItemStack> recipeItems;
    private int level;
    private int xp;

    public AlchemyRecipe(ResourceLocation id, String category, ItemStack stackToCreate, int level, int xp, List<ItemStack> recipeItems) {
        this.id = id;
        this.category = category;
        this.stackToCreate = stackToCreate;
        this.recipeItems = recipeItems;
        this.level = level;
        this.xp = xp;
    }

    public AlchemyRecipe(ResourceLocation id, String category, ItemStack stackToCreate, int level, int xp, ItemStack... recipeItems) {
        this(id, category, stackToCreate, level, xp, Arrays.asList(recipeItems));
    }

    public AlchemyRecipe(String category, ItemStack itemStack, List<ItemStack> itemStacks, int level, int xp) {
        this(null, category, itemStack, level, xp, itemStacks);
    }

    @Override
    public boolean matches(IAlchemyInventory p_77569_1_, Level p_77569_2_) {
        return false;
    }

    @Override
    public ItemStack assemble(IAlchemyInventory p_77572_1_) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.stackToCreate;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.ALCHEMY_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeType.ALCHEMY;
    }

    public ItemStack getResult() {
        return this.stackToCreate;
    }

    public String getCategory() {
        return this.category;
    }

    public List<ItemStack> getRecipeItems() {
        return this.recipeItems;
    }

    public int getRequiredLevel() {
        return this.level;
    }

    public int getXpGained() {
        return this.xp;
    }

    public AlchemyRecipe.Builder deconstruct() {
        return new AlchemyRecipe.Builder(this.category, this.stackToCreate, this.level, this.xp, this.recipeItems);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof AlchemyRecipe)) {
            return false;
        } else {
            AlchemyRecipe recipe = (AlchemyRecipe)obj;
            return this.id.equals(recipe.id);
        }
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public static class Builder {
        private String category;
        private ItemStack stackToCreate;
        private List<ItemStack> recipeItems = new ArrayList<>();
        private int level;
        private int xp;

        public Builder(String category, ItemStack stackToCreate, int level, int xp, List<ItemStack> recipeItems) {
            this.stackToCreate = stackToCreate;
            this.recipeItems = recipeItems;
            this.level = level;
            this.xp = xp;
            this.category = category;
        }

        public Builder() {
        }

        public static AlchemyRecipe.Builder recipe() {
            return new AlchemyRecipe.Builder();
        }

        public AlchemyRecipe.Builder output(ItemStack stack) {
            this.stackToCreate = stack;
            return this;
        }

        public AlchemyRecipe.Builder level(int level) {
            this.level = level;
            return this;
        }

        public AlchemyRecipe.Builder category(String category) {
            this.category = category;
            return this;
        }

        public AlchemyRecipe.Builder xp(int xp) {
            this.xp = xp;
            return this;
        }

        public AlchemyRecipe.Builder addRecipeItem(ItemStack stack) {
            if(this.recipeItems.contains(stack))
                throw new IllegalArgumentException("Duplicate recipe itemstack" + stack);
            else {
                this.recipeItems.add(stack);
                return this;
            }
        }

        public AlchemyRecipe build(ResourceLocation p_192056_1_) {
            if (this.recipeItems == null || this.recipeItems.isEmpty()) {
                throw new IllegalStateException("Tried to build incomplete alchemy recipe!");
            } else {
                return new AlchemyRecipe(p_192056_1_, this.category, this.stackToCreate, this.level, this.xp, this.recipeItems);
            }
        }

        public AlchemyRecipe save(Consumer<AlchemyRecipe> p_203904_1_, String p_203904_2_) {
            AlchemyRecipe recipe = this.build(new ResourceLocation(p_203904_2_));
            p_203904_1_.accept(recipe);
            return recipe;
        }

        public JsonObject serializeToJson() {
            JsonObject jsonobject = new JsonObject();

            jsonobject.addProperty("type", Skyrimcraft.MODID+":alchemy");

            jsonobject.addProperty("category", category);

            JsonObject output = new JsonObject();
            output.addProperty("item", stackToCreate.getItem().getRegistryName().toString());
            output.addProperty("amount", stackToCreate.getCount());
            jsonobject.add("output", output);

            JsonArray recipeItems = new JsonArray();
            for(ItemStack stack : this.recipeItems) {
                JsonObject stackObj = new JsonObject();
                stackObj.addProperty("item", stack.getItem().getRegistryName().toString());
                stackObj.addProperty("amount", stack.getCount());
                recipeItems.add(stackObj);
            }
            jsonobject.add("recipe", recipeItems);

            jsonobject.addProperty("levelToCreate", level);
            jsonobject.addProperty("xp", xp);
            return jsonobject;
        }

        public String toString() {
            return "AlchemyRecipe{stackToCreate=" + this.stackToCreate + ", level=" + this.level + ", xp=" + this.xp + ", recipeItems=" + this.recipeItems + '}';
        }

        public List<ItemStack> getRecipeItems() {
            return this.recipeItems;
        }
    }
}