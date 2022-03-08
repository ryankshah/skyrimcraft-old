package com.ryankshah.skyrimcraft.data;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider implements IConditionBuilder
{
    public ModRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(ModItems.LEATHER_STRIPS.get()).requires(Items.LEATHER).requires(Items.LEATHER).unlockedBy("has_leather", has(Items.LEATHER)).save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.FLOUR.get()).requires(Items.WHEAT).requires(Items.BOWL).unlockedBy("has_wheat", has(Items.WHEAT)).save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.BUTTER.get()).requires(Items.MILK_BUCKET).requires(Items.MAGMA_CREAM).unlockedBy("has_milk_bucket", has(Items.MILK_BUCKET)).save(consumer);

        // blocks
        ShapedRecipeBuilder.shaped(ModBlocks.OVEN_BLOCK_ITEM.get()).define('s', Blocks.STONE).define('b', Blocks.STONE_BRICKS).pattern(" b ").pattern("b b").pattern("sss").unlockedBy("has_stone_brick", has(Blocks.STONE_BRICKS)).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.ALCHEMY_TABLE_ITEM.get()).define('p', ItemTags.PLANKS).define('g', Items.GLASS_BOTTLE).pattern(" g ").pattern("ppp").pattern("p p").unlockedBy("has_planks", has(Items.OAK_PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.BLACKSMITH_FORGE.get()).define('l', Items.LAVA_BUCKET).define('s', Blocks.STONE).define('c', Blocks.COBBLESTONE).pattern("c c").pattern("clc").pattern("sss").unlockedBy("has_cobble", has(Blocks.COBBLESTONE)).save(consumer);

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.EBONY_ORE_ITEM.get()), ModItems.EBONY_INGOT.get(), 1.0F, 100).unlockedBy("has_ebony_ore", has(ModBlocks.EBONY_ORE_ITEM.get())).save(consumer, Skyrimcraft.MODID+":blasting/ebony_ingot_from_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.MALACHITE_ORE_ITEM.get()), ModItems.MALACHITE_INGOT.get(), 0.7F, 100).unlockedBy("has_malachite_ore", has(ModBlocks.MALACHITE_ORE_ITEM.get())).save(consumer, Skyrimcraft.MODID+":blasting/malachite_ingot_from_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.MOONSTONE_ORE_ITEM.get()), ModItems.MOONSTONE_INGOT.get(), 0.7F, 100).unlockedBy("has_moonstone_ore", has(ModBlocks.MOONSTONE_ORE_ITEM.get())).save(consumer, Skyrimcraft.MODID+":blasting/moonstone_ingot_from_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.ORICHALCUM_ORE_ITEM.get()), ModItems.ORICHALCUM_INGOT.get(), 0.7F, 100).unlockedBy("has_orichalcum_ore", has(ModBlocks.ORICHALCUM_ORE_ITEM.get())).save(consumer, Skyrimcraft.MODID+":blasting/orichalcum_ingot_from_blasting");
    }

    @Override
    public String getName() {
        return Skyrimcraft.MODID+"_recipes";
    }
}