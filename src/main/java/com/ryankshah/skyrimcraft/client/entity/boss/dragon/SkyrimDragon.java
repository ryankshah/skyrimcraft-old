package com.ryankshah.skyrimcraft.client.entity.boss.dragon;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
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
    private AnimationFactory factory = new AnimationFactory(this);
    private boolean shouldFly, isFlying;

    public SkyrimDragon(EntityType<? extends PathfinderMob> p_i48575_1_, Level p_i48575_2_) {
        super(p_i48575_1_, p_i48575_2_);

        this.noPhysics = true;
        this.noCulling = true;
        this.maxUpStep = 1.25f; // 1.5 works.. but does 1.25f? if so then this comment may still be here xox

        this.shouldFly = true;
        this.isFlying = true;
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.lookControl = new LookControl(this);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }

    @Override
    public void tick() {
        if(!this.isFlying) {
            this.noPhysics = false;
            this.moveControl = new MoveControl(this);
        } else {
            this.noPhysics = true;
            this.moveControl = new FlyingMoveControl(this, 10, true);
        }
        super.tick();
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
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dragon.flyidle", true));
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
