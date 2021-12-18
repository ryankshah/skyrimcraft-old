package com.ryankshah.skyrimcraft.data.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.item.IPotion;
import com.ryankshah.skyrimcraft.item.ModItems;
import com.ryankshah.skyrimcraft.item.SkyrimPotion;
import com.ryankshah.skyrimcraft.util.AlchemyRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ModAlchemyRecipeProvider implements DataProvider, IConditionBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final List<Consumer<Consumer<AlchemyRecipe>>> tabs = ImmutableList.of(new ModAlchemyRecipeProvider.AlchemyRecipes());
    private DataGenerator generator;

    public static Map<String, AlchemyRecipe> RECIPES;

    private ResourceLocation namespace = null;

    public ModAlchemyRecipeProvider(DataGenerator generatorIn) {
        RECIPES = new HashMap<>();
        this.generator = generatorIn;
    }

    @Override
    public void run(HashCache p_200398_1_) throws IOException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<AlchemyRecipe> consumer = (p_204017_3_) -> {
            if (!set.add(p_204017_3_.getId())) {
                throw new IllegalStateException("Duplicate alchemy recipe " + p_204017_3_.getId());
            } else {
                Path path1 = createPath(path, p_204017_3_);

                try {
                    DataProvider.save(GSON, p_200398_1_, p_204017_3_.deconstruct().serializeToJson(), path1);
                } catch (IOException ioexception) {
                    LOGGER.error("Couldn't save alchemy recipe {}", path1, ioexception);
                }

            }
        };

        for (Consumer<Consumer<AlchemyRecipe>> consumer1 : this.tabs) {
            consumer1.accept(consumer);
        }

    }

    private static Path createPath(Path p_218428_0_, AlchemyRecipe p_218428_1_) {
        return p_218428_0_.resolve("data/" + p_218428_1_.getId().getNamespace() + "/" + p_218428_1_.getId().getPath() + ".json");
    }

    static class AlchemyRecipes implements Consumer<Consumer<AlchemyRecipe>> {
        public void accept(Consumer<AlchemyRecipe> consumer) {
            List<SkyrimPotion> potions = ModItems.ITEMS.getEntries().stream().map(RegistryObject::get).filter(item -> item instanceof SkyrimPotion).map(item -> (SkyrimPotion)item).collect(Collectors.toList());
            for(SkyrimPotion potion : potions) {
                if(potion.getCategory() == IPotion.PotionCategory.UNIQUE || potion.getIngredients().isEmpty())
                    continue;

                AlchemyRecipe.Builder builder = AlchemyRecipe.Builder.recipe();
                builder = AlchemyRecipe.Builder.recipe().output(new ItemStack(potion.asItem(), 1)).xp(2).level(1).category(potion.getCategory().toString());

                for(ItemStack item : potion.getIngredients())
                    builder = builder.addRecipeItem(item);

                RECIPES.put(potion.getRegistryName().getPath(), builder.save(consumer, Skyrimcraft.MODID + ":recipes/alchemy/" + potion.getRegistryName().getPath()));
            }
        }
    }

    @Override
    public String getName() {
        return Skyrimcraft.MODID + "_alchemyRecipes";
    }
}