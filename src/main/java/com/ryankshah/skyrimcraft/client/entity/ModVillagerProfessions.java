package com.ryankshah.skyrimcraft.client.entity;

import com.google.common.collect.ImmutableSet;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagerProfessions
{
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, Skyrimcraft.MODID);
    public static final DeferredRegister<PoiType> POIS = DeferredRegister.create(ForgeRegistries.POI_TYPES, Skyrimcraft.MODID);

    public static final RegistryObject<PoiType> ALCHEMY_TABLE = POIS.register("alchemy_table_poi", () ->
        new PoiType("alchemy_table_poi", ImmutableSet.copyOf(ModBlocks.ALCHEMY_TABLE.get().getStateDefinition().getPossibleStates()), 1, 1)
    );
    public static final RegistryObject<PoiType> OVEN = POIS.register("oven_poi", () ->
            new PoiType("oven_poi", ImmutableSet.copyOf(ModBlocks.OVEN.get().getStateDefinition().getPossibleStates()), 1, 1)
    );
    public static final RegistryObject<PoiType> FORGE = POIS.register("forge_poi", () ->
            new PoiType("forge_poi", ImmutableSet.copyOf(ModBlocks.BLACKSMITH_FORGE.get().getStateDefinition().getPossibleStates()), 1, 1)
    );

    public static final RegistryObject<VillagerProfession> ALCHEMIST = PROFESSIONS.register("alchemist", () ->
            new VillagerProfession("alchemist", ALCHEMY_TABLE.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LIBRARIAN)
    );
    public static final RegistryObject<VillagerProfession> SKYRIM_BLACKSMITH = PROFESSIONS.register("skyrim_blacksmith", () ->
            new VillagerProfession("skyrim_blacksmith", FORGE.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_MASON)
    );
    public static final RegistryObject<VillagerProfession> COOK = PROFESSIONS.register("cook", () ->
            new VillagerProfession("cook", OVEN.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_SHEPHERD)
    );
}
