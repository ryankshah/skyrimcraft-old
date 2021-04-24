package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityType
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Skyrimcraft.MODID);

    // Entity Types
//    public static final RegistryObject<EntityType<OrcaEntity>> ORCA = ENTITY_TYPES.register("orca",
//            () -> EntityType.Builder.create(OrcaEntity::new, EntityClassification.WATER_CREATURE)
//                    .size(1.25f, 1.0f) // Hitbox Size
//                    .build(new ResourceLocation(DeepSeaMod.MODID, "orca").toString()));
//
//    public static final RegistryObject<EntityType<SeaSnakeEntity>> SEA_SNAKE = ENTITY_TYPES.register("sea_snake",
//            () -> EntityType.Builder.create(SeaSnakeEntity::new, EntityClassification.WATER_CREATURE)
//                    .size(1f, 1f)
//                    .build(new ResourceLocation(DeepSeaMod.MODID, "sea_snake").toString()));
}