package com.ryankshah.skyrimcraft.client.entity.creature;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class GiantEntity extends PathfinderMob implements IAnimatable
{
    private static final EntityDataAccessor<Integer> PREV_ANIMATION_STATE = SynchedEntityData.defineId(GiantEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(GiantEntity.class, EntityDataSerializers.INT);

    private MeleeAttackGoal meleeGoal;
    private WaterAvoidingRandomStrollGoal walkingGoal;
    private NearestAttackableTargetGoal<? extends LivingEntity> sprintToNearestPlayerGoal;
    private NearestAttackableTargetGoal<? extends LivingEntity> sprintToNearestAnimalGoal;
    private AnimationFactory factory = new AnimationFactory(this);

    public GiantEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.noCulling = true;
        this.xpReward = 5;
        this.maxUpStep = 1.25f;

        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
    }

    protected void registerGoals() {
//        meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
//            @Override
//            public void stop() {
//                super.stop();
//                SabreCatEntity.this.setAnimationState(0);
//            }
//
//            @Override
//            protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_) {
//                double d0 = this.getAttackReachSqr(p_190102_1_);
//                if (p_190102_2_ <= d0 && getTicksUntilNextAttack() <= 0) {
//                    if (SabreCatEntity.this.getAnimationState() != 3)
//                        SabreCatEntity.this.setAnimationState(3);
//                    this.resetAttackCooldown();
//                    //this.mob.swing(Hand.MAIN_HAND);
//                    this.mob.doHurtTarget(p_190102_1_);
//                }
//            }
//        };
//
        walkingGoal = new WaterAvoidingRandomStrollGoal(this, 1.0D) {
            @Override
            public void stop() {
                super.stop();
                GiantEntity.this.setAnimationState(GiantEntity.this.getPrevAnimationState());
            }

            @Override
            public void tick() {
                super.tick();
                if (GiantEntity.this.getAnimationState() != 1)
                    GiantEntity.this.setAnimationState(3);
            }
        };

        this.goalSelector.addGoal(1, new FloatGoal(this));
        //this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        //this.goalSelector.addGoal(5, meleeGoal); //new MeleeAttackGoal(this, 1.0D, true));
        //this.goalSelector.addGoal(5, walkingGoal);
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        //this.targetSelector.addGoal(2, new SprintToNearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        //this.targetSelector.addGoal(2, new SprintToNearestAttackableTargetGoal<>(this, AnimalEntity.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.JUMP_STRENGTH, 0.5D).add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.ATTACK_DAMAGE, 10.0D).add(Attributes.FOLLOW_RANGE, 20.0D);
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
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(PREV_ANIMATION_STATE, 0);
    }

    public void addAdditionalSaveData(CompoundTag p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.putInt("AnimationState", this.getAnimationState());
        p_213281_1_.putInt("PrevAnimationState", this.getPrevAnimationState());
    }

    public void readAdditionalSaveData(CompoundTag p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        this.setAnimationState(p_70037_1_.getInt("AnimationState"));
        this.setPrevAnimationState(p_70037_1_.getInt("PrevAnimationState"));
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

    public int getPrevAnimationState() {
        return this.entityData.get(PREV_ANIMATION_STATE);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance p_213386_2_, MobSpawnType p_213386_3_, @Nullable SpawnGroupData p_213386_4_, @Nullable CompoundTag p_213386_5_) {
        this.setAnimationState(0);
        this.setPrevAnimationState(0);
        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<SabreCatEntity> event) {
        AnimationController controller = event.getController();
        controller.transitionLengthTicks = 0;

        if (this.getAnimationState() == 0) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.giant.idle", true));
        } else if (this.getAnimationState() == 1) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.giant.lift_club", false));
        } else if (this.getAnimationState() == 2) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.giant.lower_club", false));
        } else if (this.getAnimationState() == 3 && event.isMoving()) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.giant.walk_club", true));
        } else if (this.getAnimationState() == 4) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.giant.guard", true));
        } else if (this.getAnimationState() == 5 && event.isMoving()) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.giant.run", true));
        } else if (this.getAnimationState() == 6) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.giant.idle_aggressive", true));
        } else {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.giant.idle", true));
        }

        //event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sabre_cat.walk", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "giant_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}