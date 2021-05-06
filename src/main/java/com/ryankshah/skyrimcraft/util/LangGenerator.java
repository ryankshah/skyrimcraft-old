package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.block.SkyrimBlock;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.item.SkyrimBlockItem;
import com.ryankshah.skyrimcraft.item.SkyrimItem;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

public class LangGenerator extends LanguageProvider
{
    public LangGenerator(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
//        for(RegistryObject<Block> block : ModBlocks.BLOCKS.getEntries()) {
//            SkyrimBlock b = (SkyrimBlock) block.get();
//            add(b, b.getDisplayName());
//        }

        add("itemGroup.skyrimcraftTab", "Skyrimcraft");

        for(RegistryObject<Item> blockItem : ModBlocks.BLOCK_ITEMS.getEntries()) {
            SkyrimBlockItem bi = (SkyrimBlockItem) blockItem.get();
            add(bi, bi.getDisplayName());
        }

        for(RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
            SkyrimItem i = (SkyrimItem) item.get();
            add(i, i.getDisplayName());
        }

        for(RegistryObject<Effect> effect : ModEffects.EFFECTS.getEntries()) {
            addEffect(effect, effect.get().getDisplayName().getString());
        }
    }
}