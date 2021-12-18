package com.ryankshah.skyrimcraft.data.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.item.ModItems;
import com.ryankshah.skyrimcraft.util.OvenRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.DataProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class ModOvenRecipeProvider implements DataProvider, IConditionBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final List<Consumer<Consumer<OvenRecipe>>> tabs = ImmutableList.of(new ModOvenRecipeProvider.OvenRecipes());
    private DataGenerator generator;

    public static Map<String, OvenRecipe> RECIPES;

    private ResourceLocation namespace = null;

    public ModOvenRecipeProvider(DataGenerator generatorIn) {
        RECIPES = new HashMap<>();
        this.generator = generatorIn;
    }

    @Override
    public void run(HashCache p_200398_1_) throws IOException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<OvenRecipe> consumer = (p_204017_3_) -> {
            if (!set.add(p_204017_3_.getId())) {
                throw new IllegalStateException("Duplicate oven recipe " + p_204017_3_.getId());
            } else {
                Path path1 = createPath(path, p_204017_3_);

                try {
                    DataProvider.save(GSON, p_200398_1_, p_204017_3_.deconstruct().serializeToJson(), path1);
                } catch (IOException ioexception) {
                    LOGGER.error("Couldn't save oven recipe {}", path1, ioexception);
                }

            }
        };

        for (Consumer<Consumer<OvenRecipe>> consumer1 : this.tabs) {
            consumer1.accept(consumer);
        }

    }

    private static Path createPath(Path p_218428_0_, OvenRecipe p_218428_1_) {
        return p_218428_0_.resolve("data/" + p_218428_1_.getId().getNamespace() + "/" + p_218428_1_.getId().getPath() + ".json");
    }

    static class OvenRecipes implements Consumer<Consumer<OvenRecipe>> {
        public void accept(Consumer<OvenRecipe> consumer) {
            RECIPES.put("food", OvenRecipe.Builder.recipe().output(new ItemStack(ModItems.SWEET_ROLL.get(), 1)).level(1).xp(5)
                    .category("food")
                    .addRecipeItem(new ItemStack(ModItems.BUTTER.get(), 1))
                    .addRecipeItem(new ItemStack(Items.EGG, 1))
                    .addRecipeItem(new ItemStack(ModItems.SALT_PILE.get(), 1))
                    .addRecipeItem(new ItemStack(ModItems.FLOUR.get(), 1))
                    .addRecipeItem(new ItemStack(Items.MILK_BUCKET, 1))
                    .save(consumer, Skyrimcraft.MODID + ":recipes/oven/" + ModItems.SWEET_ROLL.getId().getPath()));

            RECIPES.put("food", OvenRecipe.Builder.recipe().output(new ItemStack(ModItems.GARLIC_BREAD.get(), 1)).level(1).xp(5)
                    .category("food")
                    .addRecipeItem(new ItemStack(ModItems.BUTTER.get(), 1))
                    .addRecipeItem(new ItemStack(ModBlocks.GARLIC.get(), 1))
                    .addRecipeItem(new ItemStack(ModItems.FLOUR.get(), 1))
                    .save(consumer, Skyrimcraft.MODID + ":recipes/oven/" + ModItems.GARLIC_BREAD.getId().getPath()));

            RECIPES.put("food", OvenRecipe.Builder.recipe().output(new ItemStack(ModItems.APPLE_PIE.get(), 1)).level(1).xp(5)
                    .category("food")
                    .addRecipeItem(new ItemStack(ModItems.FLOUR.get(), 1))
                    .addRecipeItem(new ItemStack(ModItems.SALT_PILE.get(), 2))
                    .addRecipeItem(new ItemStack(ModItems.BUTTER.get(), 1))
                    .addRecipeItem(new ItemStack(Items.EGG, 1))
                    .addRecipeItem(new ItemStack(Items.APPLE, 2))
                    .save(consumer, Skyrimcraft.MODID + ":recipes/oven/" + ModItems.APPLE_PIE.getId().getPath()));

            RECIPES.put("food", OvenRecipe.Builder.recipe().output(new ItemStack(ModItems.POTATO_BREAD.get(), 1)).level(1).xp(5)
                    .category("food")
                    .addRecipeItem(new ItemStack(ModItems.FLOUR.get(), 1))
                    .addRecipeItem(new ItemStack(ModItems.SALT_PILE.get(), 2))
                    .addRecipeItem(new ItemStack(Items.MILK_BUCKET, 1))
                    .addRecipeItem(new ItemStack(Items.EGG, 1))
                    .addRecipeItem(new ItemStack(Items.POTATO, 1))
                    .save(consumer, Skyrimcraft.MODID + ":recipes/oven/" + ModItems.POTATO_BREAD.getId().getPath()));
        }
    }

    @Override
    public String getName() {
        return Skyrimcraft.MODID + "_ovenRecipes";
    }
}