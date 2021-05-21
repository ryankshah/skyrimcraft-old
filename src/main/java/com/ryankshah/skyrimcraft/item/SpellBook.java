package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.spell.PacketAddToKnownSpells;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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
            playerIn.displayClientMessage(new TranslationTextComponent("spellbook.learn", new TranslationTextComponent(spell.get().getName()).withStyle(TextFormatting.RED)), false);
            playerIn.awardStat(Stats.ITEM_USED.get(this));
            itemstack.shrink(1);
        } else {
            playerIn.displayClientMessage(new TranslationTextComponent("spellbook.known"), false);
        }



        return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("spellbook.tooltip", new TranslationTextComponent(spell.get().getName()).withStyle(TextFormatting.RED)));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}