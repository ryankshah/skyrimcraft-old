package com.ryankshah.skyrimcraft.effect;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects
{
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Skyrimcraft.MODID);

    public static final RegistryObject<MobEffect> UNDEAD_FLEE = EFFECTS.register("undead_flee", UndeadFleeEffect::new);
    public static final RegistryObject<MobEffect> FROZEN = EFFECTS.register("frozen", FrozenEffect::new);
    public static final RegistryObject<MobEffect> REGEN_MAGICKA = EFFECTS.register("regen_magicka", RegenMagickaEffect::new);
    public static final RegistryObject<MobEffect> SPECTRAL = EFFECTS.register("spectral", SpectralEffect::new);
    public static final RegistryObject<MobEffect> ETHEREAL = EFFECTS.register("ethereal", EtherealEffect::new);
    public static final RegistryObject<MobEffect> WATER_WALKING = EFFECTS.register("waterwalking", WaterwalkingEffect::new);
}