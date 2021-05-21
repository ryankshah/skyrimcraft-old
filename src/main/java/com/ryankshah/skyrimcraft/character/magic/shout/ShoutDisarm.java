package com.ryankshah.skyrimcraft.character.magic.shout;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.magic.entity.DisarmEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class ShoutDisarm extends ISpell implements IForgeRegistryEntry<ISpell>
{
    public ShoutDisarm(int identifier) {
        super(identifier);
    }

    @Override
    public String getName() {
        return "Disarm";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Shout defies steel, as you");
        desc.add("rip the weapon from an");
        desc.add("opponent's grasp");
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
        return new ResourceLocation(Skyrimcraft.MODID, "spells/disarm.png");
    }

    @Override
    public float getCost() {
        return 0f;
    }

    @Override
    public float getCooldown() {
        return 40.0f;
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
        DisarmEntity entity = new DisarmEntity(getCaster().getCommandSenderWorld(), getCaster(), getCaster().getLookAngle().x * 1, getCaster().getLookAngle().y * 1, getCaster().getLookAngle().z * 1);
        entity.setPos(entity.getX(), getCaster().getY() + getCaster().getEyeHeight(), entity.getZ());
        getCaster().getCommandSenderWorld().addFreshEntity(entity);

        super.onCast();
    }
}