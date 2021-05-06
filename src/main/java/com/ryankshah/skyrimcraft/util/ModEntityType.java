package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.spell.entity.FireballEntity;
import com.ryankshah.skyrimcraft.spell.entity.UnrelentingForceEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityType
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Skyrimcraft.MODID);

    // Spell entity types
    public static final RegistryObject<EntityType<?>> SPELL_FIREBALL_ENTITY = ENTITY_TYPES.register("spell_fireball",
            () -> EntityType.Builder.of(FireballEntity::new, EntityClassification.MISC)
                    .sized(1.25f, 1.0f) // Hitbox Size
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "spell_fireball").toString()));

    // Shout entity types
    public static final RegistryObject<EntityType<?>> SHOUT_UNRELENTING_FORCE_ENTITY = ENTITY_TYPES.register("shout_unrelenting_force",
            () -> EntityType.Builder.of(UnrelentingForceEntity::new, EntityClassification.MISC)
                    .sized(1.25f, 1.0f) // Hitbox Size
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(3)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "shout_unrelenting_force").toString()));

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