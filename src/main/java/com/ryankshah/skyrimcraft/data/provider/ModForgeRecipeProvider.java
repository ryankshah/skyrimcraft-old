package com.ryankshah.skyrimcraft.data.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.item.ModItems;
import com.ryankshah.skyrimcraft.util.ForgeRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
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

public class ModForgeRecipeProvider implements IDataProvider, IConditionBuilder
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final List<Consumer<Consumer<ForgeRecipe>>> tabs = ImmutableList.of(new ModForgeRecipeProvider.ForgeRecipes());
    private DataGenerator generator;

    public static Map<String, ForgeRecipe> RECIPES;

    private ResourceLocation namespace = null;

    public ModForgeRecipeProvider(DataGenerator generatorIn) {
        RECIPES = new HashMap<>();
        this.generator = generatorIn;
    }

    @Override
    public void run(DirectoryCache p_200398_1_) throws IOException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<ForgeRecipe> consumer = (p_204017_3_) -> {
            if (!set.add(p_204017_3_.getId())) {
                throw new IllegalStateException("Duplicate forge recipe " + p_204017_3_.getId());
            } else {
                Path path1 = createPath(path, p_204017_3_);

                try {
                    IDataProvider.save(GSON, p_200398_1_, p_204017_3_.deconstruct().serializeToJson(), path1);
                } catch (IOException ioexception) {
                    LOGGER.error("Couldn't save forge recipe {}", path1, ioexception);
                }

            }
        };

        for(Consumer<Consumer<ForgeRecipe>> consumer1 : this.tabs) {
            consumer1.accept(consumer);
        }

    }

    private static Path createPath(Path p_218428_0_, ForgeRecipe p_218428_1_) {
        return p_218428_0_.resolve("data/" + p_218428_1_.getId().getNamespace() + "/" + p_218428_1_.getId().getPath() + ".json");
    }

    static class ForgeRecipes implements Consumer<Consumer<ForgeRecipe>> {
        public void accept(Consumer<ForgeRecipe> consumer) {
            daedricRecipes(consumer);
            dwarvenRecipes(consumer);
            ebonyRecipes(consumer);
            elvenRecipes(consumer);
            glassRecipes(consumer);
            ironRecipes(consumer);
            orcishRecipes(consumer);
            steelRecipes(consumer);
        }
    }

    @Override
    public String getName() {
        return Skyrimcraft.MODID + "_forgeRecipes";
    }

    private static void daedricRecipes(Consumer<ForgeRecipe> consumer) {
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_ARROW.get(), 24)).level(90).xp(5)
                .category("daedric")
                .addRecipeItem(new ItemStack(Items.STICK, 1))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_ARROW.getId().getPath()));
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_DAGGER.get(), 1)).level(90).xp(5)
                .category("daedric")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_DAGGER.getId().getPath()));
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_SWORD.get(), 1)).level(90).xp(13)
                .category("daedric")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_SWORD.getId().getPath()));
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_BATTLEAXE.get(), 1)).level(90).xp(27)
                .category("daedric")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 5))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_BATTLEAXE.getId().getPath()));
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_BOW.get(), 1)).level(90).xp(25)
                .category("daedric")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_BOW.getId().getPath()));
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_GREATSWORD.get(), 1)).level(90).xp(25)
                .category("daedric")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 5))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_GREATSWORD.getId().getPath()));
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_MACE.get(), 1)).level(90).xp(18)
                .category("daedric")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_MACE.getId().getPath()));
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_WAR_AXE.get(), 1)).level(90).xp(15)
                .category("daedric")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_WAR_AXE.getId().getPath()));
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_WARHAMMER.get(), 1)).level(90).xp(40)
                .category("daedric")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 5))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_WARHAMMER.getId().getPath()));
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_HELMET.get(), 1)).level(90).xp(16)
                .category("daedric")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_HELMET.getId().getPath()));
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_CHESTPLATE.get(), 1)).level(90).xp(32)
                .category("daedric")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 5))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_CHESTPLATE.getId().getPath()));
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_LEGGINGS.get(), 1)).level(90).xp(32)
                .category("daedric")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 5))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_LEGGINGS.getId().getPath()));
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_BOOTS.get(), 1)).level(90).xp(16)
                .category("daedric")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_BOOTS.getId().getPath()));
        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DAEDRIC_SHIELD.get(), 1)).level(90).xp(16)
                .category("daedric")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 4))
                .addRecipeItem(new ItemStack(ModItems.DAEDRA_HEART.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DAEDRIC_SHIELD.getId().getPath()));
    }
    private static void dwarvenRecipes(Consumer<ForgeRecipe> consumer) {
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_ARROW.get(), 24)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(Items.STICK, 1))
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_ARROW.getId().getPath()));
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_DAGGER.get(), 1)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_DAGGER.getId().getPath()));
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_BATTLEAXE.get(), 1)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_BATTLEAXE.getId().getPath()));
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_BOW.get(), 1)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_BOW.getId().getPath()));
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_GREATSWORD.get(), 1)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 2))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_GREATSWORD.getId().getPath()));
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_MACE.get(), 1)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_MACE.getId().getPath()));
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_SWORD.get(), 1)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_SWORD.getId().getPath()));
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_WAR_AXE.get(), 1)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_WAR_AXE.getId().getPath()));
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_WARHAMMER.get(), 1)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_WARHAMMER.getId().getPath()));
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_HELMET.get(), 1)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_HELMET.getId().getPath()));
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_CHESTPLATE.get(), 1)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_CHESTPLATE.getId().getPath()));
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_LEGGINGS.get(), 1)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_LEGGINGS.getId().getPath()));
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_BOOTS.get(), 1)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_BOOTS.getId().getPath()));
        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.DWARVEN_SHIELD.get(), 1)).level(30).xp(5)
                .category("dwarven")
                .addRecipeItem(new ItemStack(ModItems.DWARVEN_METAL_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.DWARVEN_SHIELD.getId().getPath()));
    }
    private static void ebonyRecipes(Consumer<ForgeRecipe> consumer) {
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_ARROW.get(), 24)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(Items.STICK, 1))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_ARROW.getId().getPath()));
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_DAGGER.get(), 1)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_DAGGER.getId().getPath()));
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_SWORD.get(), 1)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_SWORD.getId().getPath()));
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_GREATSWORD.get(), 1)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 5))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_GREATSWORD.getId().getPath()));
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_WAR_AXE.get(), 1)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_WAR_AXE.getId().getPath()));
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_BATTLEAXE.get(), 1)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 5))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_BATTLEAXE.getId().getPath()));
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_MACE.get(), 1)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 3))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_MACE.getId().getPath()));
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_WARHAMMER.get(), 1)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 5))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_WARHAMMER.getId().getPath()));
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_BOW.get(), 1)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 3))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_BOW.getId().getPath()));
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_HELMET.get(), 1)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_HELMET.getId().getPath()));
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_CHESTPLATE.get(), 1)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 5))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_CHESTPLATE.getId().getPath()));
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_LEGGINGS.get(), 1)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 5))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_LEGGINGS.getId().getPath()));
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_BOOTS.get(), 1)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_BOOTS.getId().getPath()));
        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.EBONY_SHIELD.get(), 1)).level(80).xp(5)
                .category("ebony")
                .addRecipeItem(new ItemStack(ModItems.EBONY_INGOT.get(), 4))
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.EBONY_SHIELD.getId().getPath()));
    }
    private static void elvenRecipes(Consumer<ForgeRecipe> consumer) {
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_ARROW.get(), 24)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(Items.STICK, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_ARROW.getId().getPath()));
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_DAGGER.get(), 1)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.QUICKSILVER_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_DAGGER.getId().getPath()));
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_SWORD.get(), 1)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.QUICKSILVER_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_SWORD.getId().getPath()));
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_GREATSWORD.get(), 1)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 2))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.QUICKSILVER_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_GREATSWORD.getId().getPath()));
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_WAR_AXE.get(), 1)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.QUICKSILVER_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_WAR_AXE.getId().getPath()));
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_BATTLEAXE.get(), 1)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 2))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.QUICKSILVER_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_BATTLEAXE.getId().getPath()));
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_MACE.get(), 1)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.QUICKSILVER_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_MACE.getId().getPath()));
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_WARHAMMER.get(), 1)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 2))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.QUICKSILVER_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_WARHAMMER.getId().getPath()));
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_BOW.get(), 1)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.QUICKSILVER_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_BOW.getId().getPath()));
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_HELMET.get(), 1)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.LEATHER, 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_HELMET.getId().getPath()));
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_CHESTPLATE.get(), 1)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.LEATHER, 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_CHESTPLATE.getId().getPath()));
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_LEGGINGS.get(), 1)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.LEATHER, 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_LEGGINGS.getId().getPath()));
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_BOOTS.get(), 1)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.LEATHER, 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_BOOTS.getId().getPath()));
        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ELVEN_SHIELD.get(), 1)).level(30).xp(5)
                .category("elven")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ELVEN_SHIELD.getId().getPath()));
    }
    private static void glassRecipes(Consumer<ForgeRecipe> consumer) {
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_ARROW.get(), 24)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(Items.STICK, 1))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_ARROW.getId().getPath()));
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_DAGGER.get(), 1)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_DAGGER.getId().getPath()));
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_SWORD.get(), 1)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_SWORD.getId().getPath()));
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_GREATSWORD.get(), 1)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_GREATSWORD.getId().getPath()));
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_WAR_AXE.get(), 1)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_WAR_AXE.getId().getPath()));
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_BATTLEAXE.get(), 1)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_BATTLEAXE.getId().getPath()));
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_MACE.get(), 1)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_MACE.getId().getPath()));
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_WARHAMMER.get(), 1)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 3))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_WARHAMMER.getId().getPath()));
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_BOW.get(), 1)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_BOW.getId().getPath()));
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_HELMET.get(), 1)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.LEATHER, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_HELMET.getId().getPath()));
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_CHESTPLATE.get(), 1)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.LEATHER, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_CHESTPLATE.getId().getPath()));
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_LEGGINGS.get(), 1)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.LEATHER, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_LEGGINGS.getId().getPath()));
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_BOOTS.get(), 1)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.LEATHER, 1))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_BOOTS.getId().getPath()));
        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.GLASS_SHIELD.get(), 1)).level(70).xp(5)
                .category("glass")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(ModItems.MOONSTONE_INGOT.get(), 1))
                .addRecipeItem(new ItemStack(ModItems.MALACHITE_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.GLASS_SHIELD.getId().getPath()));
    }
    private static void ironRecipes(Consumer<ForgeRecipe> consumer) {
        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.IRON_ARROW.get(), 24)).level(1).xp(5)
                .category("iron")
                .addRecipeItem(new ItemStack(Items.STICK, 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.IRON_ARROW.getId().getPath()));
        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.IRON_DAGGER.get(), 1)).level(1).xp(5)
                .category("iron")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.IRON_DAGGER.getId().getPath()));
        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.IRON_SWORD.get(), 1)).level(1).xp(5)
                .category("iron")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.IRON_SWORD.getId().getPath()));
        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.IRON_GREATSWORD.get(), 1)).level(1).xp(5)
                .category("iron")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.IRON_GREATSWORD.getId().getPath()));
        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.IRON_WAR_AXE.get(), 1)).level(1).xp(5)
                .category("iron")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.IRON_WAR_AXE.getId().getPath()));
        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.IRON_BATTLEAXE.get(), 1)).level(1).xp(5)
                .category("iron")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.IRON_BATTLEAXE.getId().getPath()));
        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.IRON_MACE.get(), 1)).level(1).xp(5)
                .category("iron")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 3))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.IRON_MACE.getId().getPath()));
        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.IRON_WARHAMMER.get(), 1)).level(1).xp(5)
                .category("iron")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.IRON_WARHAMMER.getId().getPath()));
        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.IRON_HELMET.get(), 1)).level(1).xp(5)
                .category("iron")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 3))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.IRON_HELMET.getId().getPath()));
        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.IRON_CHESTPLATE.get(), 1)).level(1).xp(5)
                .category("iron")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 5))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.IRON_CHESTPLATE.getId().getPath()));
        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.IRON_LEGGINGS.get(), 1)).level(1).xp(5)
                .category("iron")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 5))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.IRON_LEGGINGS.getId().getPath()));
        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.IRON_BOOTS.get(), 1)).level(1).xp(5)
                .category("iron")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 3))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.IRON_BOOTS.getId().getPath()));
        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.IRON_SHIELD.get(), 1)).level(1).xp(5)
                .category("iron")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.IRON_SHIELD.getId().getPath()));
    }
    private static void orcishRecipes(Consumer<ForgeRecipe> consumer) {
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_ARROW.get(), 24)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(Items.STICK, 1))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_ARROW.getId().getPath()));
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_DAGGER.get(), 1)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_DAGGER.getId().getPath()));
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_SWORD.get(), 1)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_SWORD.getId().getPath()));
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_GREATSWORD.get(), 1)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 2))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_GREATSWORD.getId().getPath()));
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_WAR_AXE.get(), 1)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_WAR_AXE.getId().getPath()));
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_BATTLEAXE.get(), 1)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_BATTLEAXE.getId().getPath()));
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_MACE.get(), 1)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 3))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_MACE.getId().getPath()));
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_WARHAMMER.get(), 1)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_WARHAMMER.getId().getPath()));
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_BOW.get(), 1)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_BOW.getId().getPath()));
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_HELMET.get(), 1)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_HELMET.getId().getPath()));
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_CHESTPLATE.get(), 1)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_CHESTPLATE.getId().getPath()));
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_LEGGINGS.get(), 1)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_LEGGINGS.getId().getPath()));
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_BOOTS.get(), 1)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 3))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_BOOTS.getId().getPath()));
        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.ORCISH_SHIELD.get(), 1)).level(50).xp(5)
                .category("orcish")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.ORICHALCUM_INGOT.get(), 3))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.ORCISH_SHIELD.getId().getPath()));
    }
    private static void steelRecipes(Consumer<ForgeRecipe> consumer) {
        RECIPES.put("steel", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.STEEL_ARROW.get(), 24)).level(1).xp(5)
                .category("steel")
                .addRecipeItem(new ItemStack(Items.STICK, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.STEEL_ARROW.getId().getPath()));
        RECIPES.put("steel", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.STEEL_DAGGER.get(), 1)).level(1).xp(5)
                .category("steel")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 1))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.STEEL_DAGGER.getId().getPath()));
        RECIPES.put("steel", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.STEEL_SWORD.get(), 1)).level(1).xp(5)
                .category("steel")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.STEEL_SWORD.getId().getPath()));
        RECIPES.put("steel", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.STEEL_GREATSWORD.get(), 1)).level(1).xp(5)
                .category("steel")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 2))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.STEEL_GREATSWORD.getId().getPath()));
        RECIPES.put("steel", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.STEEL_WAR_AXE.get(), 1)).level(1).xp(5)
                .category("steel")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 2))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.STEEL_WAR_AXE.getId().getPath()));
        RECIPES.put("steel", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.STEEL_BATTLEAXE.get(), 1)).level(1).xp(5)
                .category("steel")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 2))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.STEEL_BATTLEAXE.getId().getPath()));
        RECIPES.put("steel", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.STEEL_MACE.get(), 1)).level(1).xp(5)
                .category("steel")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 3))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.STEEL_MACE.getId().getPath()));
        RECIPES.put("steel", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.STEEL_WARHAMMER.get(), 1)).level(1).xp(5)
                .category("steel")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 3))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 4))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.STEEL_WARHAMMER.getId().getPath()));
        RECIPES.put("steel", ForgeRecipe.Builder.recipe().output(new ItemStack(ModItems.STEEL_SHIELD.get(), 1)).level(1).xp(5)
                .category("steel")
                .addRecipeItem(new ItemStack(ModItems.LEATHER_STRIPS.get(), 1))
                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
                .addRecipeItem(new ItemStack(ModItems.STEEL_INGOT.get(), 3))
                .save(consumer, Skyrimcraft.MODID + ":recipes/forge/" + ModItems.STEEL_SHIELD.getId().getPath()));
    }
}