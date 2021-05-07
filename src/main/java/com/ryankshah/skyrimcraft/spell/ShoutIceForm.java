package com.ryankshah.skyrimcraft.spell;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.util.RayTraceUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class ShoutIceForm extends ISpell implements IForgeRegistryEntry<ISpell>
{
    public ShoutIceForm(int identifier) {
        super(identifier);
    }

    @Override
    public String getName() {
        return "Ice Form";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Your Thu'um freezes an");
        desc.add("opponent solid");
        return desc;
    }

    @Override
    public SoundEvent getSound() {
        //return ModSounds.UNRELENTING_FORCE.get();
        return SoundEvents.GLASS_BREAK;
    }

    @Override
    public float getSoundLength() {
        return 0f;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return new ResourceLocation(Skyrimcraft.MODID, "spells/ice_form.png");
    }

    @Override
    public float getCost() {
        return 0f;
    }

    @Override
    public float getCooldown() {
        return 20.0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.SHOUT;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.NA;
    }

    @Override
    public void onCast() {
        World world = getCaster().level;
        Entity rayTracedEntity = RayTraceUtil.rayTrace(world, getCaster(), 20D);
        if(rayTracedEntity instanceof LivingEntity) {
            double x = rayTracedEntity.getX();
            double y = rayTracedEntity.getY();
            double z = rayTracedEntity.getZ();
            // freeze player for 4s (80 ticks = 4 * 20)
            ((LivingEntity) rayTracedEntity).addEffect(new EffectInstance(ModEffects.FROZEN.get(), 80, 0, false, true, true));
            super.onCast();
        } else {
            getCaster().displayClientMessage(new StringTextComponent("There is nothing there to cast this shout on!"), false);
        }
    }
}