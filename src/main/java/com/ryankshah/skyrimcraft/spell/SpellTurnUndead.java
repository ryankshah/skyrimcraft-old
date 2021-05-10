package com.ryankshah.skyrimcraft.spell;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class SpellTurnUndead extends ISpell implements IForgeRegistryEntry<ISpell>
{
    public SpellTurnUndead(int identifier) {
        super(identifier);
    }

    @Override
    public String getName() {
        return "Turn Undead";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Undead enemies within 12");
        desc.add("blocks will flee from you for");
        desc.add("30 seconds");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return new ResourceLocation(Skyrimcraft.MODID, "spells/turn_undead.png");
    }

    public SoundEvent getSound() {
        return SoundEvents.GHAST_DEATH;
    }

    @Override
    public float getCost() {
        return 6.0f;
    }

    @Override
    public float getCooldown() {
        return 0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.RESTORATION;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.NA;
    }

    @Override
    public void onCast() {
        // 600 = 30s (30 * 20 ticks/s)
        getCaster().addEffect(new EffectInstance(ModEffects.UNDEAD_FLEE.get(), 600, 0, false, true, true));
        super.onCast();
    }
}
