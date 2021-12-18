package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Skyrimcraft.MODID);

    public static final RegistryObject<SoundEvent> UNRELENTING_FORCE = SOUND_EVENTS.register("unrelenting_force", () -> new SoundEvent(new ResourceLocation(Skyrimcraft.MODID, "unrelenting_force")));
}