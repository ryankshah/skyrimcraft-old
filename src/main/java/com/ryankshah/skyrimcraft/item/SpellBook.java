package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.spell.PacketAddToKnownSpells;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

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
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);

        // Dont run on server side
        if(!worldIn.isClientSide) {
            return InteractionResultHolder.pass(itemstack);
        }

        ISkyrimPlayerData cap = playerIn.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("spellbook use"));
        if(!cap.getKnownSpells().contains(spell.get())) {
            Networking.sendToServer(new PacketAddToKnownSpells(spell.get()));
            playerIn.displayClientMessage(new TranslatableComponent("spellbook.learn", new TranslatableComponent(spell.get().getName()).withStyle(ChatFormatting.RED)), false);
            playerIn.awardStat(Stats.ITEM_USED.get(this));
            itemstack.shrink(1);
        } else {
            playerIn.displayClientMessage(new TranslatableComponent("spellbook.known"), false);
        }



        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent("spellbook.tooltip", new TranslatableComponent(spell.get().getName()).withStyle(ChatFormatting.RED)));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}