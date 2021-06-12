package com.ryankshah.skyrimcraft.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.data.ModRecipeSerializers;
import com.ryankshah.skyrimcraft.data.ModRecipeType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ForgeRecipe implements IRecipe<IForgeInventory>
{
    private ResourceLocation id;
    private String category;
    private ItemStack stackToCreate;
    private List<ItemStack> recipeItems;
    private int level;
    private int xp;

    public ForgeRecipe(ResourceLocation id, String category, ItemStack stackToCreate, int level, int xp, List<ItemStack> recipeItems) {
        this.id = id;
        this.category = category;
        this.stackToCreate = stackToCreate;
        this.recipeItems = recipeItems;
        this.level = level;
        this.xp = xp;
    }

    public ForgeRecipe(ResourceLocation id, String category, ItemStack stackToCreate, int level, int xp, ItemStack... recipeItems) {
        this(id, category, stackToCreate, level, xp, Arrays.asList(recipeItems));
    }

    public ForgeRecipe(String category, ItemStack itemStack, List<ItemStack> itemStacks, int level, int xp) {
        this(null, category, itemStack, level, xp, itemStacks);
    }

    @Override
    public boolean matches(IForgeInventory p_77569_1_, World p_77569_2_) {
        return false;
    }

    @Override
    public ItemStack assemble(IForgeInventory p_77572_1_) {
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
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.FORGE_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipeType.FORGE;
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

    public ForgeRecipe.Builder deconstruct() {
        return new ForgeRecipe.Builder(this.category, this.stackToCreate, this.level, this.xp, this.recipeItems);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof ForgeRecipe)) {
            return false;
        } else {
            ForgeRecipe recipe = (ForgeRecipe)obj;
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

        public static ForgeRecipe.Builder recipe() {
            return new ForgeRecipe.Builder();
        }

        public ForgeRecipe.Builder output(ItemStack stack) {
            this.stackToCreate = stack;
            return this;
        }

        public ForgeRecipe.Builder level(int level) {
            this.level = level;
            return this;
        }

        public ForgeRecipe.Builder category(String category) {
            this.category = category;
            return this;
        }

        public ForgeRecipe.Builder xp(int xp) {
            this.xp = xp;
            return this;
        }

        public ForgeRecipe.Builder addRecipeItem(ItemStack stack) {
            if(this.recipeItems.contains(stack))
                throw new IllegalArgumentException("Duplicate recipe itemstack" + stack);
            else {
                this.recipeItems.add(stack);
                return this;
            }
        }

        public ForgeRecipe build(ResourceLocation p_192056_1_) {
            if (this.recipeItems == null || this.recipeItems.isEmpty()) {
                throw new IllegalStateException("Tried to build incomplete forge recipe!");
            } else {
                return new ForgeRecipe(p_192056_1_, this.category, this.stackToCreate, this.level, this.xp, this.recipeItems);
            }
        }

        public ForgeRecipe save(Consumer<ForgeRecipe> p_203904_1_, String p_203904_2_) {
            ForgeRecipe recipe = this.build(new ResourceLocation(p_203904_2_));
            p_203904_1_.accept(recipe);
            return recipe;
        }

        public JsonObject serializeToJson() {
            JsonObject jsonobject = new JsonObject();

            jsonobject.addProperty("type", Skyrimcraft.MODID+":forge");

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
            return "ForgeRecipe{stackToCreate=" + this.stackToCreate + ", level=" + this.level + ", xp=" + this.xp + ", recipeItems=" + this.recipeItems + '}';
        }

        public List<ItemStack> getRecipeItems() {
            return this.recipeItems;
        }
    }
}