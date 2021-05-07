package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider implements IConditionBuilder
{
    public ModRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.SWEET_ROLL.get()).define('e', Items.EGG).define('m', Items.MILK_BUCKET).define('w', ModItems.FLOUR.get()).define('s', ModItems.SALT_PILE.get()).pattern(" s ").pattern(" e ").pattern("wmw").unlockedBy("has_salt", has(ModItems.SALT_PILE.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.LEATHER_STRIPS.get()).requires(Items.LEATHER).requires(Items.LEATHER).unlockedBy("has_leather", has(Items.LEATHER)).save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.FLOUR.get()).requires(Items.WHEAT).requires(Items.BOWL).unlockedBy("has_wheat", has(Items.WHEAT)).save(consumer);

        CookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.EBONY_ORE_ITEM.get()), ModItems.EBONY_INGOT.get(), 1.0F, 100).unlockedBy("has_ebony_ore", has(ModBlocks.EBONY_ORE_ITEM.get())).save(consumer, "ebony_ingot_from_blasting");
        CookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.MALACHITE_ORE_ITEM.get()), ModItems.MALACHITE_INGOT.get(), 0.7F, 100).unlockedBy("has_malachite_ore", has(ModBlocks.MALACHITE_ORE_ITEM.get())).save(consumer, "malachite_ingot_from_blasting");
        CookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.MOONSTONE_ORE_ITEM.get()), ModItems.MOONSTONE_INGOT.get(), 0.7F, 100).unlockedBy("has_moonstone_ore", has(ModBlocks.MOONSTONE_ORE_ITEM.get())).save(consumer, "moonstone_ingot_from_blasting");
        CookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.ORICHALCUM_ORE_ITEM.get()), ModItems.ORICHALCUM_INGOT.get(), 0.7F, 100).unlockedBy("has_orichalcum_ore", has(ModBlocks.ORICHALCUM_ORE_ITEM.get())).save(consumer, "orichalcum_ingot_from_blasting");
    }

    @Override
    public String getName() {
        return Skyrimcraft.MODID+"_recipes";
    }
}