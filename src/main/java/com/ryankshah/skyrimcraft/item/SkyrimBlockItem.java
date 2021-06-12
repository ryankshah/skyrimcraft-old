package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.util.IngredientEffect;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

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
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(ingredientEffects != null) {
            tooltip.add(new StringTextComponent("Effects: ").withStyle(TextFormatting.DARK_PURPLE));
            for (int i = 0; i < ingredientEffects.length; i++)
                tooltip.add(new StringTextComponent(ingredientEffects[i].toString()));
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}