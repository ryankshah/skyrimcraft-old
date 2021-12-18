package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.util.IngredientEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public class SkyrimBlockItem extends BlockItem
{
    private String displayName;
    private IngredientEffect[] ingredientEffects;

    public SkyrimBlockItem(Block blockIn, Properties builder, String displayName, IngredientEffect... ingredientEffects) {
        super(blockIn, builder);
        this.displayName = displayName;

        if(ingredientEffects.length > 0)
            this.ingredientEffects = ingredientEffects;
        else
            this.ingredientEffects = null;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if(ingredientEffects != null) {
            tooltip.add(new TextComponent("Effects: ").withStyle(ChatFormatting.DARK_PURPLE));
            for (int i = 0; i < ingredientEffects.length; i++)
                tooltip.add(new TextComponent(ingredientEffects[i].toString()));
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}