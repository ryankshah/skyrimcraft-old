package com.ryankshah.skyrimcraft.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SkyrimTwoHandedWeapon extends SkyrimWeapon
{
    public SkyrimTwoHandedWeapon(IItemTier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_, String displayName) {
        super(p_i48460_1_, p_i48460_2_, p_i48460_3_, p_i48460_4_, displayName);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return !entity.getOffhandItem().equals(ItemStack.EMPTY);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        return !player.getOffhandItem().equals(ItemStack.EMPTY);
    }

    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
        if(p_77659_2_.getOffhandItem().equals(ItemStack.EMPTY)) {
            p_77659_2_.startUsingItem(p_77659_3_);
            return ActionResult.consume(itemstack);
        } else {
            return ActionResult.fail(itemstack);
        }
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
        return true;
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
        if(armorType == EquipmentSlotType.OFFHAND)
            return false;
        else return super.canEquip(stack, armorType, entity);
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.BLOCK;
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }
}
