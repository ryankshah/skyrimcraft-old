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
import com.ryankshah.skyrimcraft.client.entity.boss.dragon.SkyrimDragon;
import com.ryankshah.skyrimcraft.client.entity.boss.dragon.render.SkyrimDragonRenderer;
import com.ryankshah.skyrimcraft.client.entity.creature.GiantEntity;
import com.ryankshah.skyrimcraft.client.entity.creature.SabreCatEntity;
import com.ryankshah.skyrimcraft.client.entity.creature.render.GiantRenderer;
import com.ryankshah.skyrimcraft.client.entity.creature.render.SabreCatRenderer;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.*;
import com.ryankshah.skyrimcraft.client.entity.passive.flying.render.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityType
{
    public static final String PICKPOCKET_TAG = "pickpocketable";
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Skyrimcraft.MODID);

    // Arrow entity types
    public static final RegistryObject<EntityType<? extends AncientNordArrowEntity>> ANCIENT_NORD_ARROW_ENTITY = ENTITY_TYPES.register("ancient_nord_arrow",
            () -> EntityType.Builder.<AncientNordArrowEntity>of(AncientNordArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "ancient_nord_arrow").toString()));

    public static final RegistryObject<EntityType<? extends GlassArrowEntity>> GLASS_ARROW_ENTITY = ENTITY_TYPES.register("glass_arrow",
            () -> EntityType.Builder.<GlassArrowEntity>of(GlassArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "glass_arrow").toString()));

    public static final RegistryObject<EntityType<? extends DaedricArrowEntity>> DAEDRIC_ARROW_ENTITY = ENTITY_TYPES.register("daedric_arrow",
            () -> EntityType.Builder.<DaedricArrowEntity>of(DaedricArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "daedric_arrow").toString()));

    public static final RegistryObject<EntityType<? extends SteelArrowEntity>> STEEL_ARROW_ENTITY = ENTITY_TYPES.register("steel_arrow",
            () -> EntityType.Builder.<SteelArrowEntity>of(SteelArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "steel_arrow").toString()));

    public static final RegistryObject<EntityType<? extends OrcishArrowEntity>> ORCISH_ARROW_ENTITY = ENTITY_TYPES.register("orcish_arrow",
            () -> EntityType.Builder.<OrcishArrowEntity>of(OrcishArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "orcish_arrow").toString()));

    public static final RegistryObject<EntityType<? extends IronArrowEntity>> IRON_ARROW_ENTITY = ENTITY_TYPES.register("iron_arrow",
            () -> EntityType.Builder.<IronArrowEntity>of(IronArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "iron_arrow").toString()));

    public static final RegistryObject<EntityType<? extends FalmerArrowEntity>> FALMER_ARROW_ENTITY = ENTITY_TYPES.register("falmer_arrow",
            () -> EntityType.Builder.<FalmerArrowEntity>of(FalmerArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "falmer_arrow").toString()));

    public static final RegistryObject<EntityType<? extends ElvenArrowEntity>> ELVEN_ARROW_ENTITY = ENTITY_TYPES.register("elven_arrow",
            () -> EntityType.Builder.<ElvenArrowEntity>of(ElvenArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "elven_arrow").toString()));

    public static final RegistryObject<EntityType<? extends EbonyArrowEntity>> EBONY_ARROW_ENTITY = ENTITY_TYPES.register("ebony_arrow",
            () -> EntityType.Builder.<EbonyArrowEntity>of(EbonyArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "ebony_arrow").toString()));

    public static final RegistryObject<EntityType<? extends DwarvenArrowEntity>> DWARVEN_ARROW_ENTITY = ENTITY_TYPES.register("dwarven_arrow",
            () -> EntityType.Builder.<DwarvenArrowEntity>of(DwarvenArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "dwarven_arrow").toString()));

    public static final RegistryObject<EntityType<? extends DragonboneArrowEntity>> DRAGONBONE_ARROW_ENTITY = ENTITY_TYPES.register("dragonbone_arrow",
            () -> EntityType.Builder.<DragonboneArrowEntity>of(DragonboneArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).setTrackingRange(4).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "dragonbone_arrow").toString()));

    // Spell entity types
    public static final RegistryObject<EntityType<FireballEntity>> SPELL_FIREBALL_ENTITY = ENTITY_TYPES.register("spell_fireball",
            () -> EntityType.Builder.<FireballEntity>of(FireballEntity::new, MobCategory.MISC)
                    .sized(1.25f, 1.0f) // Hitbox Size
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(2)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "spell_fireball").toString()));

    // Shout entity types
    public static final RegistryObject<EntityType<UnrelentingForceEntity>> SHOUT_UNRELENTING_FORCE_ENTITY = ENTITY_TYPES.register("shout_unrelenting_force",
            () -> EntityType.Builder.<UnrelentingForceEntity>of(UnrelentingForceEntity::new, MobCategory.MISC)
                    .sized(2f, 2f) // Hitbox Size
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(2)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "shout_unrelenting_force").toString()));
    public static final RegistryObject<EntityType<DisarmEntity>> SHOUT_DISARM_ENTITY = ENTITY_TYPES.register("shout_disarm",
            () -> EntityType.Builder.<DisarmEntity>of(DisarmEntity::new, MobCategory.MISC)
                    .sized(2f, 2f) // Hitbox Size
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(2)
                    .build(new ResourceLocation(Skyrimcraft.MODID, "shout_disarm").toString()));

    // Mobs
    public static final RegistryObject<EntityType<BlueButterfly>> BLUE_BUTTERFLY = ENTITY_TYPES.register("blue_butterfly",
            () -> EntityType.Builder.of(BlueButterfly::new, MobCategory.AMBIENT)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Skyrimcraft.MODID, "blue_butterfly").toString()));
    public static final RegistryObject<EntityType<MonarchButterfly>> MONARCH_BUTTERFLY = ENTITY_TYPES.register("monarch_butterfly",
            () -> EntityType.Builder.of(MonarchButterfly::new, MobCategory.AMBIENT)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Skyrimcraft.MODID, "monarch_butterfly").toString()));
    public static final RegistryObject<EntityType<BlueDartwing>> BLUE_DARTWING = ENTITY_TYPES.register("blue_dartwing",
            () -> EntityType.Builder.of(BlueDartwing::new, MobCategory.AMBIENT)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Skyrimcraft.MODID, "blue_dartwing").toString()));
    public static final RegistryObject<EntityType<OrangeDartwing>> ORANGE_DARTWING = ENTITY_TYPES.register("orange_dartwing",
            () -> EntityType.Builder.of(OrangeDartwing::new, MobCategory.AMBIENT)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Skyrimcraft.MODID, "orange_dartwing").toString()));
    public static final RegistryObject<EntityType<LunarMoth>> LUNAR_MOTH = ENTITY_TYPES.register("lunar_moth",
            () -> EntityType.Builder.of(LunarMoth::new, MobCategory.AMBIENT)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Skyrimcraft.MODID, "lunar_moth").toString()));
    public static final RegistryObject<EntityType<TorchBug>> TORCHBUG = ENTITY_TYPES.register("torchbug",
            () -> EntityType.Builder.of(TorchBug::new, MobCategory.AMBIENT)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Skyrimcraft.MODID, "torchbug").toString()));
    public static final RegistryObject<EntityType<SabreCatEntity>> SABRE_CAT = ENTITY_TYPES.register("sabre_cat",
            () -> EntityType.Builder.of(SabreCatEntity::new, MobCategory.MONSTER)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Skyrimcraft.MODID, "sabre_cat").toString()));
    public static final RegistryObject<EntityType<GiantEntity>> GIANT = ENTITY_TYPES.register("giant",
            () -> EntityType.Builder.of(GiantEntity::new, MobCategory.MONSTER)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Skyrimcraft.MODID, "giant").toString()));
    public static final RegistryObject<EntityType<SkyrimDragon>> DRAGON = ENTITY_TYPES.register("dragon",
            () -> EntityType.Builder.of(SkyrimDragon::new, MobCategory.MONSTER)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Skyrimcraft.MODID, "dragon").toString()));

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType<AncientNordArrowEntity>) ModEntityType.ANCIENT_NORD_ARROW_ENTITY.get(), AncientNordArrowRenderer::new);
        event.registerEntityRenderer((EntityType<GlassArrowEntity>) ModEntityType.GLASS_ARROW_ENTITY.get(), GlassArrowRenderer::new);
        event.registerEntityRenderer((EntityType<DaedricArrowEntity>) ModEntityType.DAEDRIC_ARROW_ENTITY.get(), DaedricArrowRenderer::new);
        event.registerEntityRenderer((EntityType<SteelArrowEntity>) ModEntityType.STEEL_ARROW_ENTITY.get(), SteelArrowRenderer::new);
        event.registerEntityRenderer((EntityType<OrcishArrowEntity>) ModEntityType.ORCISH_ARROW_ENTITY.get(), OrcishArrowRenderer::new);
        event.registerEntityRenderer((EntityType<IronArrowEntity>) ModEntityType.IRON_ARROW_ENTITY.get(), IronArrowRenderer::new);
        event.registerEntityRenderer((EntityType<FalmerArrowEntity>) ModEntityType.FALMER_ARROW_ENTITY.get(), FalmerArrowRenderer::new);
        event.registerEntityRenderer((EntityType<ElvenArrowEntity>) ModEntityType.ELVEN_ARROW_ENTITY.get(), ElvenArrowRenderer::new);
        event.registerEntityRenderer((EntityType<EbonyArrowEntity>) ModEntityType.EBONY_ARROW_ENTITY.get(), EbonyArrowRenderer::new);
        event.registerEntityRenderer((EntityType<DwarvenArrowEntity>) ModEntityType.DWARVEN_ARROW_ENTITY.get(), DwarvenArrowRenderer::new);
        event.registerEntityRenderer((EntityType<DragonboneArrowEntity>) ModEntityType.DRAGONBONE_ARROW_ENTITY.get(), DragonboneArrowRenderer::new);

        event.registerEntityRenderer((EntityType<FireballEntity>) ModEntityType.SPELL_FIREBALL_ENTITY.get(), FireballRenderer::new);
        event.registerEntityRenderer((EntityType<UnrelentingForceEntity>) ModEntityType.SHOUT_UNRELENTING_FORCE_ENTITY.get(), UnrelentingForceRenderer::new);
        event.registerEntityRenderer((EntityType<DisarmEntity>) ModEntityType.SHOUT_DISARM_ENTITY.get(), DisarmRenderer::new);

        // Mobs
        event.registerEntityRenderer(BLUE_BUTTERFLY.get(), BlueButterflyRenderer::new);
        event.registerEntityRenderer(MONARCH_BUTTERFLY.get(), MonarchButterflyRenderer::new);
        event.registerEntityRenderer(LUNAR_MOTH.get(), LunarMothRenderer::new);
        event.registerEntityRenderer(BLUE_DARTWING.get(), BlueDartwingRenderer::new);
        event.registerEntityRenderer(ORANGE_DARTWING.get(), OrangeDartwingRenderer::new);
        event.registerEntityRenderer(TORCHBUG.get(), TorchBugRenderer::new);
        event.registerEntityRenderer(SABRE_CAT.get(), SabreCatRenderer::new);
        event.registerEntityRenderer(GIANT.get(), GiantRenderer::new);
        event.registerEntityRenderer(DRAGON.get(), SkyrimDragonRenderer::new);
    }
}