package com.ryankshah.skyrimcraft.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SkyrimShield extends Item
{
    private String displayName;

    public SkyrimShield(Item.Properties p_i48470_1_, String displayName) {
        super(p_i48470_1_);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
        this.displayName = displayName;
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.BLOCK;
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
        p_77659_2_.startUsingItem(p_77659_3_);
        return ActionResult.consume(itemstack);
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
        return true;
    }

    public boolean isValidRepairItem(ItemStack p_82789_1_, ItemStack p_82789_2_) {
        return p_82789_2_.getItem() == ModItems.EBONY_INGOT.get();
    }

    public String getDisplayName() {
        return displayName;
    }
}