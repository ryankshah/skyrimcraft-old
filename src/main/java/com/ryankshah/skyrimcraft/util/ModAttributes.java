package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModAttributes
{
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Skyrimcraft.MODID);

    public static final RegistryObject<Attribute> MAGICKA_REGEN = ATTRIBUTES.register("magicka_regen_attribute", () -> new RangedAttribute("skyrimcraft.character.attribute.magicka_regen", 1F, 1F, 2F).setSyncable(true));

    public static final String MAGICKA_REGEN_ID = "26fcb349-bc96-4593-9b29-5ace7bdee19f";
}