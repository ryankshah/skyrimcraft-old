package com.ryankshah.skyrimcraft.spell;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.spell.entity.FireballEntity;
import com.ryankshah.skyrimcraft.util.ModSounds;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class SpellFireball extends ISpell implements IForgeRegistryEntry<ISpell>
{
    public SpellFireball(int identifier) {
        super(identifier);
    }

    @Override
    public String getName() {
        return "Fireball";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("A fiery explosion for 4");
        desc.add("points of damage in a 15");
        desc.add("block radius");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return new ResourceLocation(Skyrimcraft.MODID, "spells/fireball.png");
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.ENDER_DRAGON_SHOOT;
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
        return SpellType.DESTRUCTION;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.NA;
    }

    @Override
    public void onCast() {
        FireballEntity fireball = new FireballEntity(getCaster().getCommandSenderWorld(), getCaster(), getCaster().getLookAngle().x * 1, getCaster().getLookAngle().y * 1, getCaster().getLookAngle().z * 1);
        fireball.setPos(fireball.getX(), getCaster().getY() + getCaster().getEyeHeight(), getCaster().getZ());
        getCaster().getCommandSenderWorld().addFreshEntity(fireball);

        super.onCast();
    }
}