package com.ryankshah.skyrimcraft.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class SkyrimTwoHandedWeapon extends SkyrimWeapon
{
    public SkyrimTwoHandedWeapon(Tier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_, String displayName) {
        super(p_i48460_1_, p_i48460_2_, p_i48460_3_, p_i48460_4_, displayName);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return !entity.getOffhandItem().equals(ItemStack.EMPTY);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        return !player.getOffhandItem().equals(ItemStack.EMPTY);
    }

    public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
        if(p_77659_2_.getOffhandItem().equals(ItemStack.EMPTY)) {
            p_77659_2_.startUsingItem(p_77659_3_);
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {
        if(armorType == EquipmentSlot.OFFHAND)
            return false;
        else return super.canEquip(stack, armorType, entity);
    }

    public UseAnim getUseAnimation(ItemStack p_77661_1_) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }
}
