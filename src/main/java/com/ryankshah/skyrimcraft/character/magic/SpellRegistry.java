package com.ryankshah.skyrimcraft.character.magic;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.magic.shout.*;
import com.ryankshah.skyrimcraft.character.magic.spell.SpellFireball;
import com.ryankshah.skyrimcraft.character.magic.spell.SpellTurnUndead;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class SpellRegistry
{
    public static final DeferredRegister<ISpell> SPELLS = DeferredRegister.create(ISpell.class, Skyrimcraft.MODID);
    public static final Supplier<IForgeRegistry<ISpell>> SPELLS_REGISTRY = SPELLS.makeRegistry(Skyrimcraft.MODID + "spells", RegistryBuilder::new);

    // Shouts
    public static RegistryObject<ISpell> UNRELENTING_FORCE = SPELLS.register("unrelenting_force", () -> new ShoutUnrelentingForce(1));
    public static RegistryObject<ISpell> STORM_CALL = SPELLS.register("storm_call", () -> new ShoutStormCall(2));
    public static RegistryObject<ISpell> WHIRLWIND_SPRINT = SPELLS.register("whirlwind_sprint", () -> new ShoutWhirlwindSprint(3));
    public static RegistryObject<ISpell> ICE_FORM = SPELLS.register("ice_form", () -> new ShoutIceForm(4));
    public static RegistryObject<ISpell> DISARM = SPELLS.register("disarm", () -> new ShoutDisarm(5));
    public static RegistryObject<ISpell> BECOME_ETHEREAL = SPELLS.register("become_ethereal", () -> new ShoutBecomeEthereal(6));
    public static RegistryObject<ISpell> DRAGONREND = SPELLS.register("dragonrend", () -> new ShoutDragonrend(7));

    // Spells
    public static RegistryObject<ISpell> FIREBALL = SPELLS.register("fireball", () -> new SpellFireball(20));
    public static RegistryObject<ISpell> TURN_UNDEAD = SPELLS.register("turn_undead", () -> new SpellTurnUndead(21));
}