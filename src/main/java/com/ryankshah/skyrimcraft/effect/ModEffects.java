package com.ryankshah.skyrimcraft.effect;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects
{
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Skyrimcraft.MODID);

    public static final RegistryObject<Effect> UNDEAD_FLEE = EFFECTS.register("undead_flee", UndeadFleeEffect::new);
}