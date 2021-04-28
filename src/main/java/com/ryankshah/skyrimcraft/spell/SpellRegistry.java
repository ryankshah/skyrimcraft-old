package com.ryankshah.skyrimcraft.spell;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class SpellRegistry
{
    public static final DeferredRegister<ISpell> SPELLS = DeferredRegister.create(ISpell.class, Skyrimcraft.MODID);
    public static final Supplier<IForgeRegistry<ISpell>> SPELLS_REGISTRY = SPELLS.makeRegistry(Skyrimcraft.MODID + "spells", RegistryBuilder::new);

    private static int id = 0;
    /**
     * SHOUTS
     * ======
     * Shouts do not cost mana and cooldowns will be referenced from https://elderscrolls.fandom.com/wiki/Dragon_Shouts
     */
    public static RegistryObject<ISpell> UNRELENTING_FORCE = SPELLS.register("unrelenting_force", () -> new ShoutUnrelentingForce(id++));

    // Spells
    public static RegistryObject<ISpell> FIREBALL = SPELLS.register("fireball", () -> new SpellFireball(id++));
}