package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.spell.PacketReplenishMagicka;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MagickaPotion extends SkyrimPotion
{
    private float replenishValue;

    public MagickaPotion(Properties properties, String displayName, float replenishValue) {
        super(properties, displayName);
        this.replenishValue = replenishValue;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        Player playerEntity = entityLiving instanceof Player ? (Player) entityLiving : null;

        if(!worldIn.isClientSide) {
            playerEntity.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                if(cap.getMagicka() != cap.getMaxMagicka())
                    Networking.sendToServer(new PacketReplenishMagicka(replenishValue));
            });
        }

        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    @Override
    public List<ItemStack> getIngredients() {
        List<ItemStack> ingredients = new ArrayList<>();
        if (this == ModItems.MINOR_MAGICKA_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.GRASS_POD.get(), 1));
            ingredients.add(new ItemStack(ModBlocks.RED_MOUNTAIN_FLOWER_ITEM.get(), 1));
        } else if(this == ModItems.MAGICKA_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.GRASS_POD.get(), 1));
            ingredients.add(new ItemStack(ModItems.BRIAR_HEART.get(), 1));
        } else if (this == ModItems.PLENTIFUL_MAGICKA_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.VAMPIRE_DUST.get(), 1));
            ingredients.add(new ItemStack(ModItems.BRIAR_HEART.get(), 1));
        } else if (this == ModItems.VIGOROUS_MAGICKA_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.CREEP_CLUSTER.get(), 1));
            ingredients.add(new ItemStack(ModBlocks.RED_MOUNTAIN_FLOWER_ITEM.get(), 1));
        } else if (this == ModItems.EXTREME_MAGICKA_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.CREEP_CLUSTER.get(), 1));
            ingredients.add(new ItemStack(ModItems.MORA_TAPINELLA.get(), 1));
        } else if (this == ModItems.ULTIMATE_MAGICKA_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.MORA_TAPINELLA.get(), 1));
            ingredients.add(new ItemStack(ModBlocks.RED_MOUNTAIN_FLOWER_ITEM.get(), 1));
        }
        return ingredients;
    }

    @Override
    public PotionCategory getCategory() {
        return PotionCategory.RESTORE_MAGICKA;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TextComponent((int)replenishValue == 20 ? "Completely replenishes your magicka" : "Replenishes " + (int)replenishValue + " magicka"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}