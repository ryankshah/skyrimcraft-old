package com.ryankshah.skyrimcraft.character.magic.spell;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class SpellConjureFamiliar extends ISpell implements IForgeRegistryEntry<ISpell> {
    public SpellConjureFamiliar(int identifier) {
        super(identifier);
    }

    @Override
    public String getName() {
        return "Conjure Familiar";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Summons a familiar for 60");
        desc.add("seconds wherever the");
        desc.add("caster is pointing");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return new ResourceLocation(Skyrimcraft.MODID, "spells/fireball.png");
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.WOLF_GROWL;
    }

    @Override
    public float getCost() {
        return 12.0f;
    }

    @Override
    public float getCooldown() {
        return 0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.CONJURATION;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.NOVICE;
    }

    @Override
    public int getBaseXp() {
        return 4;
    }

    @Override
    public void onCast() {
        if(getCaster() instanceof ServerPlayer && !getCaster().level.isClientSide) {
            Wolf wolf = getCaster().level.getNearestEntity(Wolf.class, TargetingConditions.DEFAULT, getCaster(), getCaster().getX(), getCaster().getY(), getCaster().getZ(),
                    new AABB(getCaster().getX() - 10f, getCaster().getY() - 10f, getCaster().getZ() - 10f, getCaster().getX() + 10f, getCaster().getY() + 10f, getCaster().getZ() + 10f));
            if(wolf != null && wolf.getPersistentData().contains(Skyrimcraft.MODID+"_timeToKill")) {
                getCaster().displayClientMessage(new TranslatableComponent(Skyrimcraft.MODID + ".conjuredfamiliar.exists"), false);
            } else {
                Wolf wolfEntity = new Wolf(EntityType.WOLF, getCaster().level);
                wolfEntity.setPos(getCaster().getX(), getCaster().getY() + 0.2f, getCaster().getZ());
                wolfEntity.setTame(true);
                wolfEntity.setHealth(40f);
                wolfEntity.addEffect(new MobEffectInstance(ModEffects.SPECTRAL.get(), 60 * 20, 0, false, true, true));
                wolfEntity.setOwnerUUID(getCaster().getUUID());
                wolfEntity.getPersistentData().putLong(Skyrimcraft.MODID + "_timeToKill", getCaster().level.getGameTime() + (60L * 20L));
                getCaster().level.addFreshEntity(wolfEntity);

                super.onCast();
            }
        }
    }
}