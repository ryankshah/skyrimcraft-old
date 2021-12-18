package com.ryankshah.skyrimcraft.character.magic.shout;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class ShoutClearSkies extends ISpell implements IForgeRegistryEntry<ISpell>
{
    public ShoutClearSkies(int identifier) {
        super(identifier);
    }

    @Override
    public String getName() {
        return "Clear Skies";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Minecraft yields before your");
        desc.add("Thu'um, as you clear away fog");
        desc.add("and inclement weather");
        return desc;
    }

    @Override
    public SoundEvent getSound() {
        //return ModSounds.UNRELENTING_FORCE.get();
        return SoundEvents.LIGHTNING_BOLT_THUNDER;
    }

    @Override
    public float getSoundLength() {
        return 2.2f;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return new ResourceLocation(Skyrimcraft.MODID, "spells/clear_skies.png");
    }

    @Override
    public float getCost() {
        return 0f;
    }

    @Override
    public float getCooldown() {
        return 60.0f;
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
        if(getCaster().level instanceof ServerLevel) {
            ServerLevel world = (ServerLevel) getCaster().level;
            world.setWeatherParameters(6000, 0, false, false);
        }
        super.onCast();
    }
}