package com.ryankshah.skyrimcraft.spell;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.spell.entity.FireballEntity;
import net.minecraft.util.ResourceLocation;
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
    public float getCost() {
        return 6.0f;
    }

    @Override
    public float getCooldown() {
        return 10.0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.DESTRUCTION;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.APPRENTICE;
    }

    @Override
    public void onCast() {
        FireballEntity fireball = new FireballEntity(getCaster().getEntityWorld(), getCaster(), getCaster().getLookVec().x * 1, getCaster().getLookVec().y * 1, getCaster().getLookVec().z * 1);
        fireball.setPosition(fireball.getPosX(), getCaster().getPosY() + getCaster().getEyeHeight(), getCaster().getPosZ());
        getCaster().getEntityWorld().addEntity(fireball);

        super.onCast();
    }
}