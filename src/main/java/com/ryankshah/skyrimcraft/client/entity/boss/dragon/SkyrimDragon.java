package com.ryankshah.skyrimcraft.client.entity.boss.dragon;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SkyrimDragon extends PathfinderMob implements IAnimatable
{
    public static final EntityDataAccessor<Integer> DATA_PHASE = SynchedEntityData.defineId(SkyrimDragon.class, EntityDataSerializers.INT);
    private static final String SKYRIM_DRAGON_PHASE_KEY = "SkyrimDragonPhase";
    private static final float SITTING_ALLOWED_DAMAGE_PERCENTAGE = 0.25F;
    private float sittingDamageReceived;
    private AnimationFactory factory = new AnimationFactory(this);
    private boolean shouldFly, isFlying;

    public SkyrimDragon(EntityType<? extends PathfinderMob> p_i48575_1_, Level p_i48575_2_) {
        super(p_i48575_1_, p_i48575_2_);

        //this.noPhysics = true;
        //this.noCulling = true;
        this.maxUpStep = 1.25f;
        this.shouldFly = false;
        this.isFlying = false;
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.lookControl = new LookControl(this);
//        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }

//    @Override
//    public boolean fireImmune() {
//        return true;
//    }

    public void onFlap() {
        if (this.level.isClientSide && !this.isSilent()) {
            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENDER_DRAGON_FLAP, this.getSoundSource(), 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false);
        }
    }

    @Override
    public void tick() {
        if(!this.isFlying) {
//            this.noPhysics = false;
            this.moveControl = new MoveControl(this);
        } else {
//            this.noPhysics = true;
            this.moveControl = new FlyingMoveControl(this, 10, true);
        }
        super.tick();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_PHASE, EnderDragonPhase.HOVERING.getId());
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(8, new SkyrimDragon.WanderGoal());
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader world) {
        return world.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) ? 10.0F : world.getBrightness(pos) - 0.5F;
    }

    protected boolean canRide(Entity p_31169_) {
        return false;
    }

    public boolean canChangeDimensions() {
        return false;
    }

    public boolean canAttack(LivingEntity p_149576_) {
        return p_149576_.canBeSeenAsEnemy();
    }

    protected boolean shouldDropExperience() {
        return true;
    }

    protected boolean shouldDropLoot() {
        return true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.JUMP_STRENGTH, 0.5D).add(Attributes.FLYING_SPEED, 1.0D).add(Attributes.MOVEMENT_SPEED, 1.0D).add(Attributes.MAX_HEALTH, 200.0D).add(Attributes.ATTACK_DAMAGE, 9.0D).add(Attributes.FOLLOW_RANGE, 18.0D);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(this.isFlying && event.isMoving())
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dragon.flyidle", true));
        else if(!this.isFlying && event.isMoving())
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dragon.walk", true));
        else if(!this.isFlying && !event.isMoving())
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dragon.stand", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        return false;
    }

    @Override
    protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, BlockState p_184231_4_, BlockPos p_184231_5_) {
    }

    protected PathNavigation createNavigation(Level p_27815_) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_27815_) {
            public boolean isStableDestination(BlockPos p_27947_) {
                return !this.level.getBlockState(p_27947_.below()).isAir();
            }

            public void tick() {
                super.tick();
            }
        };
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(false);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    class WanderGoal extends Goal {
        WanderGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            return SkyrimDragon.this.navigation.isDone() && SkyrimDragon.this.random.nextInt(10) == 0;
        }

        public boolean canContinueToUse() {
            return SkyrimDragon.this.navigation.isInProgress();
        }

        public void start() {
            Vec3 vector3d = this.findPos();
            if (vector3d != null) {
                SkyrimDragon.this.navigation.moveTo(SkyrimDragon.this.navigation.createPath(new BlockPos(vector3d), 1), 1.0D);
            }

        }

        @Nullable
        private Vec3 findPos() {
            Vec3 vector3d = SkyrimDragon.this.getViewVector(0.0F);
            int i = 4;
            Vec3 vec32 = HoverRandomPos.getPos(SkyrimDragon.this, 8, 7, vector3d.x, vector3d.z, ((float)Math.PI / 2F), 3, 1);
            return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(SkyrimDragon.this, 8, 4, -2, vector3d.x, vector3d.z, (double)((float)Math.PI / 2F));
        }
    }
}
