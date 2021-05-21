package com.ryankshah.skyrimcraft.character.magic.shout;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class ShoutBecomeEthereal extends ISpell implements IForgeRegistryEntry<ISpell>
{
    public ShoutBecomeEthereal(int identifier) {
        super(identifier);
    }

    @Override
    public String getName() {
        return "Become Ethereal";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("The Thu'um reaches out to");
        desc.add("the Void, changing your form");
        desc.add("to one at peace");
        return desc;
    }

    @Override
    public SoundEvent getSound() {
        //return ModSounds.UNRELENTING_FORCE.get();
        return SoundEvents.GENERIC_EXPLODE;
    }

    @Override
    public float getSoundLength() {
        return 2.2f;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return new ResourceLocation(Skyrimcraft.MODID, "spells/become_ethereal.png");
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
        // 360 = 18s (18 * 20 ticks/s)
        getCaster().addEffect(new EffectInstance(ModEffects.ETHEREAL.get(), 360, 0, false, true, true));
        super.onCast();
    }
}