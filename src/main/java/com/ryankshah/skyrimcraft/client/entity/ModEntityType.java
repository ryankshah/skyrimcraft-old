package com.ryankshah.skyrimcraft.client.entity;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.magic.entity.DisarmEntity;
import com.ryankshah.skyrimcraft.character.magic.entity.FireballEntity;
import com.ryankshah.skyrimcraft.character.magic.entity.UnrelentingForceEntity;
import com.ryankshah.skyrimcraft.character.magic.entity.render.DisarmRenderer;
import com.ryankshah.skyrimcraft.character.magic.entity.render.FireballRenderer;
import com.ryankshah.skyrimcraft.character.magic.entity.render.UnrelentingForceRenderer;
import com.ryankshah.skyrimcraft.client.entity.arrow.*;
import com.ryankshah.skyrimcraft.client.entity.arrow.render.*;
import com.ryankshah.skyrimcraft.client.entity.passive.AbstractButterflyEntity;
import com.ryankshah.skyrimcraft.client.entity.passive.render.BlueButterflyRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityType
{
    public static final String PICKPOCKET_TAG = "pickpocketable";
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Skyrimcraft.MODID);

    // Arrow entity types
    public static final RegistryObject<EntityType<? extends AncientNordArrowEntity>> ANCIENT_NORD_ARROW_ENTITY = ENTITY_TYPES.register("ancient_nord_arrow",
            () -> EntityType.Builder.<AncientNordArrowEntity>of(AncientNordArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "ancient_nord_arrow").toString()));

    public static final RegistryObject<EntityType<? extends GlassArrowEntity>> GLASS_ARROW_ENTITY = ENTITY_TYPES.register("glass_arrow",
            () -> EntityType.Builder.<GlassArrowEntity>of(GlassArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "glass_arrow").toString()));

    public static final RegistryObject<EntityType<? extends DaedricArrowEntity>> DAEDRIC_ARROW_ENTITY = ENTITY_TYPES.register("daedric_arrow",
            () -> EntityType.Builder.<DaedricArrowEntity>of(DaedricArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "daedric_arrow").toString()));

    public static final RegistryObject<EntityType<? extends SteelArrowEntity>> STEEL_ARROW_ENTITY = ENTITY_TYPES.register("steel_arrow",
            () -> EntityType.Builder.<SteelArrowEntity>of(SteelArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "steel_arrow").toString()));

    public static final RegistryObject<EntityType<? extends OrcishArrowEntity>> ORCISH_ARROW_ENTITY = ENTITY_TYPES.register("orcish_arrow",
            () -> EntityType.Builder.<OrcishArrowEntity>of(OrcishArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "orcish_arrow").toString()));

    public static final RegistryObject<EntityType<? extends IronArrowEntity>> IRON_ARROW_ENTITY = ENTITY_TYPES.register("iron_arrow",
            () -> EntityType.Builder.<IronArrowEntity>of(IronArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "iron_arrow").toString()));

    public static final RegistryObject<EntityType<? extends FalmerArrowEntity>> FALMER_ARROW_ENTITY = ENTITY_TYPES.register("falmer_arrow",
            () -> EntityType.Builder.<FalmerArrowEntity>of(FalmerArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "falmer_arrow").toString()));

    public static final RegistryObject<EntityType<? extends ElvenArrowEntity>> ELVEN_ARROW_ENTITY = ENTITY_TYPES.register("elven_arrow",
            () -> EntityType.Builder.<ElvenArrowEntity>of(ElvenArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "elven_arrow").toString()));

    public static final RegistryObject<EntityType<? extends EbonyArrowEntity>> EBONY_ARROW_ENTITY = ENTITY_TYPES.register("ebony_arrow",
            () -> EntityType.Builder.<EbonyArrowEntity>of(EbonyArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "ebony_arrow").toString()));

    public static final RegistryObject<EntityType<? extends DwarvenArrowEntity>> DWARVEN_ARROW_ENTITY = ENTITY_TYPES.register("dwarven_arrow",
            () -> EntityType.Builder.<DwarvenArrowEntity>of(DwarvenArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "dwarven_arrow").toString()));

    public static final RegistryObject<EntityType<? extends DragonboneArrowEntity>> DRAGONBONE_ARROW_ENTITY = ENTITY_TYPES.register("dragonbone_arrow",
            () -> EntityType.Builder.<DragonboneArrowEntity>of(DragonboneArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "dragonbone_arrow").toString()));

    // Spell entity types
    public static final RegistryObject<EntityType<?>> SPELL_FIREBALL_ENTITY = ENTITY_TYPES.register("spell_fireball",
            () -> EntityType.Builder.of(FireballEntity::new, EntityClassification.MISC)
                    .sized(1.25f, 1.0f) // Hitbox Size
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(2)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "spell_fireball").toString()));

    // Shout entity types
    public static final RegistryObject<EntityType<?>> SHOUT_UNRELENTING_FORCE_ENTITY = ENTITY_TYPES.register("shout_unrelenting_force",
            () -> EntityType.Builder.of(UnrelentingForceEntity::new, EntityClassification.MISC)
                    .sized(2f, 2f) // Hitbox Size
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(2)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "shout_unrelenting_force").toString()));
    public static final RegistryObject<EntityType<?>> SHOUT_DISARM_ENTITY = ENTITY_TYPES.register("shout_disarm",
            () -> EntityType.Builder.of(DisarmEntity::new, EntityClassification.MISC)
                    .sized(2f, 2f) // Hitbox Size
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(2)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "shout_disarm").toString()));

    // Mobs
    public static final RegistryObject<EntityType<AbstractButterflyEntity>> BLUE_BUTTERFLY = ENTITY_TYPES.register("blue_butterfly",
            () -> EntityType.Builder.of(AbstractButterflyEntity::new, EntityClassification.CREATURE)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Skyrimcraft.MODID, "blue_butterfly").toString()));

    public static void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler((EntityType<AncientNordArrowEntity>) ModEntityType.ANCIENT_NORD_ARROW_ENTITY.get(), AncientNordArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<GlassArrowEntity>) ModEntityType.GLASS_ARROW_ENTITY.get(), GlassArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<DaedricArrowEntity>) ModEntityType.DAEDRIC_ARROW_ENTITY.get(), DaedricArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<SteelArrowEntity>) ModEntityType.STEEL_ARROW_ENTITY.get(), SteelArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<OrcishArrowEntity>) ModEntityType.ORCISH_ARROW_ENTITY.get(), OrcishArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<IronArrowEntity>) ModEntityType.IRON_ARROW_ENTITY.get(), IronArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<FalmerArrowEntity>) ModEntityType.FALMER_ARROW_ENTITY.get(), FalmerArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<ElvenArrowEntity>) ModEntityType.ELVEN_ARROW_ENTITY.get(), ElvenArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<EbonyArrowEntity>) ModEntityType.EBONY_ARROW_ENTITY.get(), EbonyArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<DwarvenArrowEntity>) ModEntityType.DWARVEN_ARROW_ENTITY.get(), DwarvenArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<DragonboneArrowEntity>) ModEntityType.DRAGONBONE_ARROW_ENTITY.get(), DragonboneArrowRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler((EntityType<FireballEntity>) ModEntityType.SPELL_FIREBALL_ENTITY.get(), FireballRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<UnrelentingForceEntity>) ModEntityType.SHOUT_UNRELENTING_FORCE_ENTITY.get(), UnrelentingForceRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<DisarmEntity>) ModEntityType.SHOUT_DISARM_ENTITY.get(), DisarmRenderer::new);

        // Mobs
        RenderingRegistry.registerEntityRenderingHandler(BLUE_BUTTERFLY.get(), BlueButterflyRenderer::new);
    }

    public static void registerAttributes() {
        GlobalEntityTypeAttributes.put(BLUE_BUTTERFLY.get(), AbstractButterflyEntity.createAttributes().build());
    }

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