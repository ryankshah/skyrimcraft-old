package com.ryankshah.skyrimcraft.client.entity.creature;

import com.ryankshah.skyrimcraft.client.entity.creature.ai.goal.SprintToNearestAttackableTargetGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SabreCatEntity extends PathfinderMob implements IAnimatable
{
    private static final EntityDataAccessor<String> BIOME_TYPE = SynchedEntityData.defineId(SabreCatEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> PREV_ANIMATION_STATE = SynchedEntityData.defineId(SabreCatEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(SabreCatEntity.class, EntityDataSerializers.INT);

    private static final List<ResourceKey<Biome>> SNOWY_BIOMES = Arrays.asList(
            Biomes.SNOWY_SLOPES, Biomes.SNOWY_TAIGA, Biomes.SNOWY_BEACH,
            Biomes.SNOWY_PLAINS
    );

    private MeleeAttackGoal meleeGoal;
    private WaterAvoidingRandomStrollGoal walkingGoal;
    private NearestAttackableTargetGoal<? extends LivingEntity> sprintToNearestPlayerGoal;
    private NearestAttackableTargetGoal<? extends LivingEntity> sprintToNearestAnimalGoal;
    private AnimationFactory factory = new AnimationFactory(this);

    public SabreCatEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.noCulling = true;
        this.xpReward = 5;
        this.maxUpStep = 1.25f; // 1.5 works.. but does 1.25f? if so then this comment may still be here xox

        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
        this.setBiomeType(Biomes.PLAINS);
    }

    protected void registerGoals() {
        meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
            @Override
            public void stop() {
                super.stop();
                SabreCatEntity.this.setAnimationState(0);
            }

            @Override
            protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_) {
                double d0 = this.getAttackReachSqr(p_190102_1_);
                if (p_190102_2_ <= d0 && getTicksUntilNextAttack() <= 0) {
                    if(SabreCatEntity.this.getAnimationState() != 3)
                        SabreCatEntity.this.setAnimationState(3);
                    this.resetAttackCooldown();
                    //this.mob.swing(Hand.MAIN_HAND);
                    this.mob.doHurtTarget(p_190102_1_);
                }
            }
        };

        walkingGoal = new WaterAvoidingRandomStrollGoal(this, 1.0D) {
            @Override
            public void stop() {
                super.stop();
                SabreCatEntity.this.setAnimationState(SabreCatEntity.this.getPrevAnimationState());
            }

            @Override
            public void tick() {
                super.tick();
                if(SabreCatEntity.this.getAnimationState() != 1)
                    SabreCatEntity.this.setAnimationState(1);
            }
        };

        sprintToNearestPlayerGoal = new NearestAttackableTargetGoal<Player>(this, Player.class, true) {
            @Override
            public void stop() {
                super.stop();
                SabreCatEntity.this.setAnimationState(SabreCatEntity.this.getPrevAnimationState());
            }

            @Override
            public void tick() {
                super.tick();
                if(SabreCatEntity.this.getAnimationState() != 2)
                    SabreCatEntity.this.setAnimationState(2);
            }
        };

        this.goalSelector.addGoal(1, new FloatGoal(this));
        //this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, meleeGoal); //new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, walkingGoal);
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new SprintToNearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new SprintToNearestAttackableTargetGoal<>(this, Animal.class, true));
    }

    @Nullable
    @Override
    public Component getCustomName() {
        if(SNOWY_BIOMES.contains(this.getBiomeType()))
            return new TranslatableComponent("entity.skyrimcraft.snowy_sabre_cat");
        return super.getCustomName();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.JUMP_STRENGTH, 0.5D).add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.FOLLOW_RANGE, 18.0D);
    }

    @Override
    protected float getBlockJumpFactor() {
        return super.getBlockJumpFactor();
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader world) {
        return world.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) ? 10.0F : world.getBrightness(pos) - 0.5F;
    }

    protected boolean shouldDropExperience() {
        return true;
    }

    protected boolean shouldDropLoot() {
        return true;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BIOME_TYPE, Biomes.PLAINS.location().toString());
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(PREV_ANIMATION_STATE, 0);
    }

    public void addAdditionalSaveData(CompoundTag p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.putString("BiomeType", this.getBiomeType().location().toString());
        p_213281_1_.putInt("AnimationState", this.getAnimationState());
        p_213281_1_.putInt("PrevAnimationState", this.getPrevAnimationState());
    }

    public void readAdditionalSaveData(CompoundTag p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        if (p_70037_1_.contains("BiomeType")) {
            Optional<Map.Entry<ResourceKey<Biome>, Biome>> dataresult = ForgeRegistries.BIOMES.getEntries().stream().filter(
                    registryKeyBiomeEntry -> registryKeyBiomeEntry.getKey().location().equals(new ResourceLocation(p_70037_1_.getString("BiomeType"))
            )).findFirst();

            dataresult.ifPresent(r -> this.setBiomeType(r.getKey()));
        }
        this.setAnimationState(p_70037_1_.getInt("AnimationState"));
        this.setPrevAnimationState(p_70037_1_.getInt("PrevAnimationState"));
    }

    public void setBiomeType(ResourceKey<Biome> biomeType) {
        this.entityData.set(BIOME_TYPE, biomeType.location().toString());
    }

    public ResourceKey<Biome> getBiomeType() {
        Optional<Map.Entry<ResourceKey<Biome>, Biome>> opt = ForgeRegistries.BIOMES.getEntries().stream().filter(entry -> entry.getKey().location().toString().equals(this.entityData.get(BIOME_TYPE))).findFirst();
        return opt.map(Map.Entry::getKey).orElse(null);
    }

    public void setAnimationState(int animationState) {
        setPrevAnimationState(this.getAnimationState());
        //this.entityData.set(PREV_ANIMATION_STATE, this.getAnimationState());
        this.entityData.set(ANIMATION_STATE, animationState);
    }
    public int getAnimationState() {
        return this.entityData.get(ANIMATION_STATE);
    }

    public void setPrevAnimationState(int prevAnimationState) {
        this.entityData.set(PREV_ANIMATION_STATE, prevAnimationState);
    }
    public int getPrevAnimationState() { return this.entityData.get(PREV_ANIMATION_STATE); }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance p_213386_2_, MobSpawnType p_213386_3_, @Nullable SpawnGroupData p_213386_4_, @Nullable CompoundTag p_213386_5_) {
        p_213386_1_.getBiomeName(this.blockPosition()).ifPresent(this::setBiomeType);
        this.setAnimationState(0);
        this.setPrevAnimationState(0);
        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<SabreCatEntity> event) {
        AnimationController controller = event.getController();
        controller.transitionLengthTicks = 0;

        if(this.getAnimationState() == 0) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.sabre_cat.idle", true));
        } else if (this.getAnimationState() == 1 && event.isMoving()) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.sabre_cat.walk", true));
        } else if(this.getAnimationState() == 2 && event.isMoving()) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.sabre_cat.run", true));
        } else if(this.getAnimationState() == 3) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.sabre_cat.claw", false));
        } else {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.sabre_cat.idle", true));
        }

        //event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sabre_cat.walk", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "sabre_cat_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}