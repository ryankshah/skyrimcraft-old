package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.util.IngredientEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TextComponent("Effects: ").withStyle(ChatFormatting.DARK_PURPLE));
        for(int i = 0; i < ingredientEffects.length; i++)
            tooltip.add(new TextComponent(ingredientEffects[i].toString()));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
