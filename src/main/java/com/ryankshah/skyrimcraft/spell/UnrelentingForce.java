package com.ryankshah.skyrimcraft.spell;


import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class UnrelentingForce extends ISpell implements IForgeRegistryEntry<ISpell> {
    public UnrelentingForce(int identifier) {
        super(identifier);
    }

    @Override
    public String getName() {
        return "Unrelenting Force";
    }

    @Override
    public float getCost() {
        return 0;
    }

    @Override
    public float getCooldown() {
        return 45.0f;
    }

    @Override
    public SpellType getType() {
        return ISpell.SpellType.SHOUT;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.NA;
    }

    @Override
    public void onCast() {
        // Do stuff...
        super.onCast();
    }
}