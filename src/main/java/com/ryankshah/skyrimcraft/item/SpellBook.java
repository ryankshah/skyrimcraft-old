package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.PacketAddToKnownSpells;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.spell.ISpell;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
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
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);

        // Dont run on server side
        if(!worldIn.isClientSide) {
            return ActionResult.pass(itemstack);
        }

        ISkyrimPlayerData cap = playerIn.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("spellbook use"));
        if(!cap.getKnownSpells().contains(spell.get())) {
            Networking.sendToServer(new PacketAddToKnownSpells(spell.get()));
            // Replace with something like: new TranslationTextComponent("the.thing", new TranslationTextComponent(spell.get().getName()).withStyle(TextFormatting.RED))
            // where the.thing in the lang file (i.e. en_us.json) is You have just learnt %s!
            playerIn.displayClientMessage(new StringTextComponent("You have just learnt " + TextFormatting.RED + spell.get().getName() + TextFormatting.RESET + "!"), false);
            //worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundCategory.BLOCKS, 1f, 1f);
            playerIn.awardStat(Stats.ITEM_USED.get(this));
            //if (!playerIn.abilities.instabuild) {
            itemstack.shrink(1);
            //}
        } else {
            playerIn.displayClientMessage(new StringTextComponent("You already know this spell!"), false);
        }



        return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Grants you use of the " + TextFormatting.RED + spell.get().getName() + TextFormatting.RESET + " spell!"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}