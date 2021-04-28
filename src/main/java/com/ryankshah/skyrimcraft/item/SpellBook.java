package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.spell.ISpell;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;

public class SpellBook extends SkyrimItem
{
    public SpellBook(Properties properties, String displayName) {
        super(properties, displayName);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.EPIC;
    }

    private RegistryObject<ISpell> spell;

    public SpellBook(Properties properties, String displayName, RegistryObject<ISpell> spell) {
        super(properties, displayName);
        this.spell = spell;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(!worldIn.isRemote) {
            playerIn.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                if(!cap.getKnownSpells().contains(spell.get())) {
                    cap.addToKnownSpells(spell.get());
                    playerIn.sendStatusMessage(new StringTextComponent("You have just learnt " + TextFormatting.RED + spell.get().getName() + TextFormatting.RED + "!"), false);
                    worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.BLOCKS, 1f, 1f);
                }
            });
        }

        if (playerIn != null) {
            playerIn.addStat(Stats.ITEM_USED.get(this));
            if (!playerIn.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }
        }

        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Grants you use of the " + TextFormatting.RED + spell.get().getName() + TextFormatting.RESET + " spell!"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}