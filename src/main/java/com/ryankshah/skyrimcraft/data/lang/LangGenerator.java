package com.ryankshah.skyrimcraft.data.lang;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.item.*;
import com.ryankshah.skyrimcraft.item.SkyrimArrow;
import net.minecraft.data.DataGenerator;
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
        add("itemGroup.skyrimcraft.blocks", "Skyrimcraft Blocks");
        add("itemGroup.skyrimcraft.ingredients", "Skyrimcraft Ingredients");
        add("itemGroup.skyrimcraft.material", "Skyrimcraft Materials");
        add("itemGroup.skyrimcraft.food", "Skyrimcraft Food");
        add("itemGroup.skyrimcraft.combat", "Skyrimcraft Combat");
        add("itemGroup.skyrimcraft.magic", "Skyrimcraft Magic");

        for(RegistryObject<Item> blockItem : ModBlocks.BLOCK_ITEMS.getEntries()) {
            SkyrimBlockItem bi = (SkyrimBlockItem) blockItem.get();
            add(bi, bi.getDisplayName());
        }

        for(RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
            if (item.get() instanceof SkyrimItem) {
                SkyrimItem i = (SkyrimItem) item.get();
                add(i, i.getDisplayName());
            } else if (item.get() instanceof SkyrimArmorItem) {
                SkyrimArmorItem i = (SkyrimArmorItem) item.get();
                add(i, i.getDisplayName());
            } else if (item.get() instanceof SkyrimShield) {
                SkyrimShield i = (SkyrimShield) item.get();
                add(i, i.getDisplayName());
            } else if(item.get() instanceof SkyrimWeapon) {
                SkyrimWeapon i = (SkyrimWeapon) item.get();
                add(i, i.getDisplayName());
            } else if(item.get() instanceof SkyrimBow) {
                SkyrimBow i = (SkyrimBow) item.get();
                add(i, i.getDisplayName());
            } else if(item.get() instanceof SkyrimArrow) {
                SkyrimArrow i = (SkyrimArrow) item.get();
                add(i, i.getDisplayName());
            }
        }

        for(RegistryObject<Effect> effect : ModEffects.EFFECTS.getEntries()) {
            addEffect(effect, effect.get().getDisplayName().getString());
        }

        // Spellbook
        add("spellbook.tooltip", "Grants you use of the %s spell!");
        add("spellbook.learn", "You have just learnt %s!");
        add("spellbook.known", "You already know this spell!");

        // Shout block
        add("shoutblock.allshoutsknown", "You have no more shouts to learn!");
        add("shoutblock.used", "The power which once resonated within this wall has since departed...");

        // Info
        add("spell.noselect", "No spell/shout selected");
        add("skyrimcraft.menu.skills", "Skills");
        add("skyrimcraft.menu.map", "Map");
        add("skyrimcraft.menu.items", "Items");
        add("skyrimcraft.menu.magic", "Magic");
        add("skyrimcraft.menu.option.unavailable", "This option is currently unavailable!");
        add("skyrimcraft.menu.option.invalid", "Invalid Option!");
        add("skyrimcraft.menu.option.magic.none", "You have not yet learned any spells/shouts!");

        // Skills
        add("skill.pickpocket.fail", "You fail to pick the %s's pockets!");
        add("skill.pickpocket.success", "You successfully pick the %s's pockets and get some loot!");

        // Mobs
        add("entity.skyrimcraft.merchant", "Merchant");
        add("entity.skyrimcraft.merchant.none", "Merchant");
        add("entity.skyrimcraft.merchant.skyrimcraft.alchemist", "Alchemist");
        add("entity.skyrimcraft.merchant.skyrimcraft.food_merchant", "Food Merchant");
        add("entity.skyrimcraft.merchant.skyrimcraft.blacksmith", "Blacksmith");
    }

    @Override
    public String getName() {
        return Skyrimcraft.MODID+"_lang";
    }
}