package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.spell.PacketReplenishMagicka;
import com.ryankshah.skyrimcraft.util.ModBlocks;
import com.ryankshah.skyrimcraft.util.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

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
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        PlayerEntity playerEntity = entityLiving instanceof PlayerEntity ? (PlayerEntity) entityLiving : null;

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
        if (this == ModItems.MINOR_MAGICKA_POTION.get() || this == ModItems.MAGICKA_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.CREEP_CLUSTER.get(), 1));
            ingredients.add(new ItemStack(ModBlocks.RED_MOUNTAIN_FLOWER_ITEM.get(), 1));
        } else if (this == ModItems.PLENTIFUL_MAGICKA_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.VAMPIRE_DUST.get(), 1));
            ingredients.add(new ItemStack(ModItems.BRIAR_HEART.get(), 1));
        } else if (this == ModItems.VIGOROUS_MAGICKA_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.GRASS_POD.get(), 1));
            ingredients.add(new ItemStack(ModItems.MORA_TAPINELLA.get(), 1));
        } else if (this == ModItems.EXTREME_MAGICKA_POTION.get()) {
            ingredients.add(new ItemStack(ModItems.GRASS_POD.get(), 1));
            ingredients.add(new ItemStack(ModBlocks.RED_MOUNTAIN_FLOWER_ITEM.get(), 1));
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
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Replenishes " + (int)replenishValue + " magicka"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}