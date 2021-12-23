package com.ryankshah.skyrimcraft.data.provider;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider
{
    public ModBlockTagsProvider(DataGenerator p_126511_, String modid,  @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126511_, modid, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.EBONY_ORE.get(), ModBlocks.ORICHALCUM_ORE.get(),
                ModBlocks.CORUNDUM_ORE.get(), ModBlocks.MALACHITE_ORE.get(), ModBlocks.MOONSTONE_ORE.get(),
                ModBlocks.SILVER_ORE.get(), ModBlocks.QUICKSILVER_ORE.get(), ModBlocks.BLACKSMITH_FORGE.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.EBONY_ORE.get(), ModBlocks.ORICHALCUM_ORE.get(),
                ModBlocks.CORUNDUM_ORE.get(), ModBlocks.MALACHITE_ORE.get(), ModBlocks.MOONSTONE_ORE.get(),
                ModBlocks.SILVER_ORE.get(), ModBlocks.QUICKSILVER_ORE.get(), ModBlocks.BLACKSMITH_FORGE.get());
    }

    @Override
    public String getName() {
        return Skyrimcraft.MODID+"_blockTags";
    }
}