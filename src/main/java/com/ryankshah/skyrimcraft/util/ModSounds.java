package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Skyrimcraft.MODID);

    public static final RegistryObject<SoundEvent> UNRELENTING_FORCE = SOUND_EVENTS.register("unrelenting_force", () -> new SoundEvent(new ResourceLocation(Skyrimcraft.MODID, "unrelenting_force")));
}