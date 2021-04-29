package com.ryankshah.skyrimcraft.spell;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.spell.entity.FireballEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class ShoutUnrelentingForce extends ISpell implements IForgeRegistryEntry<ISpell>
{
    public ShoutUnrelentingForce(int identifier) {
        super(identifier);
    }

    @Override
    public String getName() {
        return "Unrelenting Force";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Your voice is raw power,");
        desc.add("pushing aside anything");
        desc.add("that stands in your path");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return new ResourceLocation(Skyrimcraft.MODID, "spells/fireball2.png");
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
        FireballEntity fireball = new FireballEntity(getCaster().getEntityWorld(), getCaster(), getCaster().getLookVec().x * 1, getCaster().getLookVec().y * 1, getCaster().getLookVec().z * 1);
        fireball.setPosition(fireball.getPosX(), getCaster().getPosY() + getCaster().getEyeHeight(), getCaster().getPosZ());
        getCaster().getEntityWorld().addEntity(fireball);

        super.onCast();
    }
}