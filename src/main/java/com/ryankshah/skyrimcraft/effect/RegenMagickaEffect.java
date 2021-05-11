package com.ryankshah.skyrimcraft.effect;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegenMagickaEffect extends Effect implements IForgeRegistryEntry<Effect>
{
    public RegenMagickaEffect() {
        super(EffectType.BENEFICIAL, 0xAA3792CB);
    }

    public ITextComponent getDisplayName() {
        return new StringTextComponent("Regen Magicka");
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        if(livingEntity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) livingEntity;
            ISkyrimPlayerData cap = player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalStateException("Getting capability at regen magicka effect"));
        }
        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}