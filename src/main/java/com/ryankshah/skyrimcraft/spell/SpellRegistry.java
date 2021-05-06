package com.ryankshah.skyrimcraft.spell;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.spell.entity.FireballEntity;
import com.ryankshah.skyrimcraft.spell.entity.UnrelentingForceEntity;
import com.ryankshah.skyrimcraft.spell.entity.render.FireballRenderer;
import com.ryankshah.skyrimcraft.spell.entity.render.UnrelentingForceRenderer;
import com.ryankshah.skyrimcraft.util.ModEntityType;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class SpellRegistry
{
    public static final DeferredRegister<ISpell> SPELLS = DeferredRegister.create(ISpell.class, Skyrimcraft.MODID);
    public static final Supplier<IForgeRegistry<ISpell>> SPELLS_REGISTRY = SPELLS.makeRegistry(Skyrimcraft.MODID + "spells", RegistryBuilder::new);

    private static int id = 0;

    // Shouts
    public static RegistryObject<ISpell> UNRELENTING_FORCE = SPELLS.register("unrelenting_force", () -> new ShoutUnrelentingForce(id++));
    public static RegistryObject<ISpell> STORM_CALL = SPELLS.register("storm_call", () -> new ShoutStormCall(id++));
    public static RegistryObject<ISpell> WHIRLWIND_SPRINT = SPELLS.register("whirlwind_sprint", () -> new ShoutWhirlwindSprint(id++));
    public static RegistryObject<ISpell> ICE_FORM = SPELLS.register("ice_form", () -> new ShoutIceForm(id++));
    // Spells
    public static RegistryObject<ISpell> FIREBALL = SPELLS.register("fireball", () -> new SpellFireball(id++));
    public static RegistryObject<ISpell> TURN_UNDEAD = SPELLS.register("turn_undead", () -> new SpellTurnUndead(id++));

    public static void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler((EntityType<FireballEntity>) ModEntityType.SPELL_FIREBALL_ENTITY.get(), FireballRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<UnrelentingForceEntity>) ModEntityType.SHOUT_UNRELENTING_FORCE_ENTITY.get(), UnrelentingForceRenderer::new);
    }
}