package com.ryankshah.skyrimcraft.client.entity.passive.merchant;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.ai.sensor.MerchantSecondaryPositionSensor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MerchantHandler
{
    public static final DeferredRegister<PointOfInterestType> POINT_OF_INTEREST_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Skyrimcraft.MODID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, Skyrimcraft.MODID);
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, Skyrimcraft.MODID);

    private static final Supplier<Set<PointOfInterestType>> ALL_JOB_POI_TYPES = Suppliers.memoize(() -> {
        return PROFESSIONS.getEntries().stream().map(RegistryObject::get).map(VillagerProfession::getJobPoiType).collect(Collectors.toSet());
    });
    public static final Predicate<PointOfInterestType> ALL_JOBS = (p_221049_0_) -> {
        return ALL_JOB_POI_TYPES.get().contains(p_221049_0_);
    };

    // Merchant POI types
    public static final RegistryObject<PointOfInterestType> UNEMPLOYED = POINT_OF_INTEREST_TYPES.register("unemployed", () -> new PointOfInterestType("merchant_unemployed", ImmutableSet.of(), 1, ALL_JOBS, 1));
    public static final RegistryObject<PointOfInterestType> OVEN = POINT_OF_INTEREST_TYPES.register("oven", () -> new PointOfInterestType("oven", getBlockStates(ModBlocks.OVEN.get()), 1, 1));
    public static final RegistryObject<PointOfInterestType> ALCHEMY_TABLE = POINT_OF_INTEREST_TYPES.register("alchemy_table", () -> new PointOfInterestType("alchemy_table", getBlockStates(ModBlocks.ALCHEMY_TABLE.get()), 1, 1));
    public static final RegistryObject<PointOfInterestType> BLACKSMITH_FORGE = POINT_OF_INTEREST_TYPES.register("blacksmith_forge", () -> new PointOfInterestType("blacksmith_forge", getBlockStates(ModBlocks.BLACKSMITH_FORGE.get()), 1, 1));

    // Merchant Professions
    public static final RegistryObject<VillagerProfession> NONE = PROFESSIONS.register("none", () -> new VillagerProfession("none", PointOfInterestType.UNEMPLOYED, ImmutableSet.of(), ImmutableSet.of(), (SoundEvent) null));
    public static final RegistryObject<VillagerProfession> FOOD_MERCHANT = PROFESSIONS.register("food_merchant", () -> new VillagerProfession("food_merchant", OVEN.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_BUTCHER));
    public static final RegistryObject<VillagerProfession> ALCHEMIST = PROFESSIONS.register("alchemist", () -> new VillagerProfession("alchemist", ALCHEMY_TABLE.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CLERIC));
    public static final RegistryObject<VillagerProfession> BLACKSMITH = PROFESSIONS.register("blacksmith", () -> new VillagerProfession("blacksmith", BLACKSMITH_FORGE.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));

    // Merchant Sensor Types
    public static final RegistryObject<SensorType<MerchantSecondaryPositionSensor>> SECONDARY_POIS = SENSOR_TYPES.register("merchant_secondary_pois", () -> new SensorType<>(MerchantSecondaryPositionSensor::new));

    private static final Supplier<Set<PointOfInterestType>> MERCHANT_JOB_POI_TYPES = Suppliers.memoize(() -> {
        return PROFESSIONS.getEntries().stream().map(RegistryObject::get).map(VillagerProfession::getJobPoiType).collect(Collectors.toSet());
    });
    private static final Supplier<Set<VillagerProfession>> MERCHANT_PROFESSION_TYPES = Suppliers.memoize(() -> {
        return PROFESSIONS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toSet());
    });

    public static final Predicate<PointOfInterestType> MERCHANT_JOBS = (p_221049_0_) -> {
        return MERCHANT_JOB_POI_TYPES.get().contains(p_221049_0_);
    };
    public static final Predicate<VillagerProfession> MERCHANT_PROFESSIONS = (p_221049_0_) -> {
        return MERCHANT_PROFESSION_TYPES.get().contains(p_221049_0_);
    };

    public static Set<BlockState> getBlockStates(Block p_221042_0_) {
        return ImmutableSet.copyOf(p_221042_0_.getStateDefinition().getPossibleStates());
    }

    public static Stream<MerchantEntity> getNearbyMerchantsWithCondition(MerchantEntity p_233872_0_, Predicate<MerchantEntity> p_233872_1_) {
        return p_233872_0_.getBrain().getMemory(MemoryModuleType.LIVING_ENTITIES).map((p_233873_2_) -> {
            return p_233873_2_.stream().filter((p_233871_1_) -> {
                return p_233871_1_ instanceof MerchantEntity && p_233871_1_ != p_233872_0_;
            }).map((p_233859_0_) -> {
                return (MerchantEntity)p_233859_0_;
            }).filter(LivingEntity::isAlive).filter(p_233872_1_);
        }).orElseGet(Stream::empty);
    }
}