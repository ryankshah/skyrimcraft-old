package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.util.IngredientEffect;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

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
}
