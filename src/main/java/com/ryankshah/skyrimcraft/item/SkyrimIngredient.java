package com.ryankshah.skyrimcraft.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.Item.Properties;

public class SkyrimIngredient extends SkyrimItem
{
    private IngredientEffect[] ingredientEffects;

    public SkyrimIngredient(Properties properties, String displayName, IngredientEffect... ingredientEffects) {
        super(properties, displayName);
        this.ingredientEffects = ingredientEffects;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Effects: ").withStyle(TextFormatting.DARK_PURPLE));
        for(int i = 0; i < ingredientEffects.length; i++)
            tooltip.add(new StringTextComponent(ingredientEffects[i].toString()));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    public enum IngredientEffect
    {
        REGENERATE_MAGICKA("Regenerate Magicka"),
        REGENERATE_HEALTH("Regenerate Health"),
        REGENERATE_STAMINA("Regenerate Stamina"),
        RESTORE_MAGICKA("Restore Magicka"),
        RESTORE_HEALTH("Restore Health"),
        RESTORE_STAMINA("Restore Stamina"),
        DAMAGE_STAMINA("Damage Stamina"),
        DAMAGE_MAGICKA("Damage Magicka"),
        DAMAGE_HEALTH("Damage Health"),
        DAMAGE_MAGICKA_REGEN("Damage Magicka Regen"),
        DAMAGE_HEALTH_REGEN("Damage Health Regen"),
        DAMAGE_STAMINA_REGEN("Damage Stamina Regen"),
        RAVAGE_HEALTH("Ravage Health"),
        RAVAGE_MAGICKA("Ravage Magicka"),
        RAVAGE_STAMINA("Ravage Stamina"),
        WEAKNESS_TO_MAGIC("Weakness to Magic"),
        WEAKNESS_TO_FROST("Weakness to Frost"),
        WEAKNESS_TO_FIRE("Weakness to Fire"),
        WEAKNESS_TO_SHOCK("Weakness to Shock"),
        WEAKNESS_TO_POISON("Weakness to Poison"),
        RESIST_FIRE("Resist Fire"),
        RESIST_FROST("Resist Frost"),
        RESIST_POISON("Resist Poison"),
        RESIST_SHOCK("Resist Shock"),
        RESIST_MAGIC("Resist Magic"),
        FORTIFY_ILLUSION("Fortify Illusion"),
        FORTIFY_CONJURATION("Fortify Conjuration"),
        FORTIFY_ENCHANTING("Fortify Enchanting"),
        FORTIFY_ALTERATION("Fortify Alteration"),
        FORTIFY_DESTRUCTION("Fortify Destruction"),
        FORTIFY_RESTORATION("Fortify Restoration"),
        FORTIFY_HEAVY_ARMOR("Fortify Heavy Armor"),
        FORTIFY_LIGHT_ARMOR("Fortify Light Armor"),
        FORTIFY_MAGICKA("Fortify Magicka"),
        FORTIFY_HEALTH("Fortify Health"),
        FORTIFY_STAMINA("Fortify Stamina"),
        FORTIFY_BLOCK("Fortify Block"),
        FORTIFY_CARRY_WEIGHT("Fortify Carry Weight"),
        LINGERING_DAMAGE_HEALTH("Lingering Damage Health"),
        SLOW("Slow"),
        FEAR("Fear"),
        FRENZY("Frenzy"),
        PARALYSIS("Paralysis"),
        WATERBREATHING("Waterbreathing"),
        CURE_DISEASE("Cure Disease"),
        INVISIBILITY("Invisibility");

        private String displayName;

        IngredientEffect(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return this.displayName;
        }
    }
}
