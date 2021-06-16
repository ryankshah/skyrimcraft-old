package com.ryankshah.skyrimcraft.client.entity.passive.merchant;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.ryankshah.skyrimcraft.client.entity.passive.merchant.ai.MerchantTasks;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.IReputationTracking;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.village.GossipManager;
import net.minecraft.village.GossipType;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiPredicate;

// TODO: Add merchant races
public class MerchantEntity extends CreatureEntity implements IReputationTracking, IVillagerDataHolder, INPC, IMerchant
{
    private static final DataParameter<Integer> DATA_UNHAPPY_COUNTER = EntityDataManager.defineId(MerchantEntity.class, DataSerializers.INT);
    private static final DataParameter<VillagerData> MERCHANT_DATA = EntityDataManager.defineId(MerchantEntity.class, DataSerializers.VILLAGER_DATA);
    @Nullable
    private PlayerEntity tradingPlayer;
    @Nullable
    protected MerchantOffers offers;
    private final Inventory inventory = new Inventory(8);
    @Nullable
    private PlayerEntity lastTradedPlayer;
    private final GossipManager gossips = new GossipManager();
    private long lastGossipTime;
    private long lastGossipDecayTime;
    private int villagerXp;
    private long lastRestockGameTime;
    private int numberOfRestocksToday;
    private long lastRestockCheckDayTime;
    private boolean assignProfessionWhenSpawned;

    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleType.MEETING_POINT, MemoryModuleType.LIVING_ENTITIES, MemoryModuleType.VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.NEAREST_BED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleType.HIDING_PLACE, MemoryModuleType.HEARD_BELL_TIME, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LAST_SLEPT, MemoryModuleType.LAST_WOKEN, MemoryModuleType.LAST_WORKED_AT_POI);
    private static final ImmutableList<SensorType<? extends Sensor<? super MerchantEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_BED, SensorType.HURT_BY, SensorType.VILLAGER_HOSTILES, MerchantHandler.SECONDARY_POIS.get());
    public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<MerchantEntity, PointOfInterestType>> POI_MEMORIES = ImmutableMap.of(MemoryModuleType.HOME, (p_213769_0_, p_213769_1_) -> {
        return p_213769_1_ == PointOfInterestType.HOME;
    }, MemoryModuleType.JOB_SITE, (p_213771_0_, p_213771_1_) -> {
        return p_213771_0_.getVillagerData().getProfession().getJobPoiType() == p_213771_1_;
    }, MemoryModuleType.POTENTIAL_JOB_SITE, (p_213772_0_, p_213772_1_) -> {
        return MerchantHandler.MERCHANT_JOBS.test(p_213772_1_);
    }, MemoryModuleType.MEETING_POINT, (p_234546_0_, p_234546_1_) -> {
        return p_234546_1_ == PointOfInterestType.MEETING;
    });

    public MerchantEntity(EntityType<? extends MerchantEntity> p_i48575_1_, World p_i48575_2_) {
        this(p_i48575_1_, p_i48575_2_, VillagerType.PLAINS);
    }

    public MerchantEntity(EntityType<? extends MerchantEntity> p_i50183_1_, World p_i50183_2_, VillagerType p_i50183_3_) {
        super(p_i50183_1_, p_i50183_2_);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
        ((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(true);
        this.getNavigation().setCanFloat(true);
        this.setCanPickUpLoot(true);
        this.setVillagerData(this.getVillagerData().setType(p_i50183_3_).setProfession(MerchantHandler.NONE.get()));
    }

    public Brain<MerchantEntity> getBrain() {
        return (Brain<MerchantEntity>)super.getBrain();
    }

    protected Brain.BrainCodec<MerchantEntity> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> p_213364_1_) {
        Brain<MerchantEntity> brain = this.brainProvider().makeBrain(p_213364_1_);
        this.registerBrainGoals(brain);
        return brain;
    }

    public void refreshBrain(ServerWorld p_213770_1_) {
        Brain<MerchantEntity> brain = this.getBrain();
        brain.stopAll(p_213770_1_, this);
        this.brain = brain.copyWithoutBehaviors();
        this.registerBrainGoals(this.getBrain());
    }

    private void registerBrainGoals(Brain<MerchantEntity> p_213744_1_) {
        VillagerProfession villagerprofession = this.getVillagerData().getProfession();
        p_213744_1_.setSchedule(Schedule.VILLAGER_DEFAULT);
        p_213744_1_.addActivityWithConditions(Activity.WORK, MerchantTasks.getWorkPackage(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT)));

        p_213744_1_.addActivity(Activity.CORE, MerchantTasks.getCorePackage(villagerprofession, 0.5F));
        p_213744_1_.addActivityWithConditions(Activity.MEET, MerchantTasks.getMeetPackage(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryModuleStatus.VALUE_PRESENT)));
        p_213744_1_.addActivity(Activity.REST, MerchantTasks.getRestPackage(villagerprofession, 0.5F));
        p_213744_1_.addActivity(Activity.IDLE, MerchantTasks.getIdlePackage(villagerprofession, 0.5F));
        p_213744_1_.addActivity(Activity.PANIC, MerchantTasks.getPanicPackage(villagerprofession, 0.5F));
        //p_213744_1_.addActivity(Activity.HIDE, VillagerTasks.getHidePackage(villagerprofession, 0.5F));
        p_213744_1_.setCoreActivities(ImmutableSet.of(Activity.CORE));
        p_213744_1_.setDefaultActivity(Activity.IDLE);
        p_213744_1_.setActiveActivityIfPossible(Activity.IDLE);
        p_213744_1_.updateActivityFromSchedule(this.level.getDayTime(), this.level.getGameTime());
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.5D).add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    public void onReputationEventFrom(IReputationType p_213739_1_, Entity p_213739_2_) {
        if (p_213739_1_ == IReputationType.TRADE) {
            this.gossips.add(p_213739_2_.getUUID(), GossipType.TRADING, 2);
        } else if (p_213739_1_ == IReputationType.VILLAGER_HURT) {
            this.gossips.add(p_213739_2_.getUUID(), GossipType.MINOR_NEGATIVE, 25);
        } else if (p_213739_1_ == IReputationType.VILLAGER_KILLED) {
            this.gossips.add(p_213739_2_.getUUID(), GossipType.MAJOR_NEGATIVE, 25);
        }
    }

    public boolean assignProfessionWhenSpawned() {
        return this.assignProfessionWhenSpawned;
    }

    protected void customServerAiStep() {
        this.level.getProfiler().push("merchantBrain");
        this.getBrain().tick((ServerWorld)this.level, this);
        this.level.getProfiler().pop();
        if (this.assignProfessionWhenSpawned) {
            this.assignProfessionWhenSpawned = false;
        }

        if (this.lastTradedPlayer != null && this.level instanceof ServerWorld) {
            ((ServerWorld)this.level).onReputationEvent(IReputationType.TRADE, this.lastTradedPlayer, this);
            this.level.broadcastEntityEvent(this, (byte)14);
            this.lastTradedPlayer = null;
        }

        if (this.getVillagerData().getProfession() == MerchantHandler.NONE.get() && this.isTrading()) {
            this.stopTrading();
        }

        super.customServerAiStep();
    }

    public void tick() {
        super.tick();
        if (this.getUnhappyCounter() > 0) {
            this.setUnhappyCounter(this.getUnhappyCounter() - 1);
        }

        this.maybeDecayGossip();
    }

    public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        if (itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.isTrading() && !this.isSleeping() && !p_230254_1_.isSecondaryUseActive()) {
            boolean flag = this.getOffers().isEmpty();
            if (p_230254_2_ == Hand.MAIN_HAND) {
                if (flag && !this.level.isClientSide) {
                    this.setUnhappy();
                }

                p_230254_1_.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (flag) {
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            } else {
                if (!this.level.isClientSide && !this.offers.isEmpty()) {
                    this.startTrading(p_230254_1_);
                }

                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }
        } else {
            return super.mobInteract(p_230254_1_, p_230254_2_);
        }
    }

    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return 1.62F;
    }

    public int getUnhappyCounter() {
        return this.entityData.get(DATA_UNHAPPY_COUNTER);
    }

    public void setUnhappyCounter(int p_213720_1_) {
        this.entityData.set(DATA_UNHAPPY_COUNTER, p_213720_1_);
    }

    private void setUnhappy() {
        this.setUnhappyCounter(40);
        if (!this.level.isClientSide()) {
            this.playSound(SoundEvents.VILLAGER_NO, this.getSoundVolume(), this.getVoicePitch());
        }
    }

    private void startTrading(PlayerEntity p_213740_1_) {
        this.updateSpecialPrices(p_213740_1_);
        this.setTradingPlayer(p_213740_1_);
        this.openTradingScreen(p_213740_1_, this.getDisplayName(), this.getVillagerData().getLevel());
    }

    @Override
    public void openTradingScreen(PlayerEntity p_213707_1_, ITextComponent p_213707_2_, int p_213707_3_) {
        OptionalInt optionalint = p_213707_1_.openMenu(new SimpleNamedContainerProvider((p_213701_1_, p_213701_2_, p_213701_3_) -> {
            return new MerchantContainer(p_213701_1_, p_213701_2_, this);
        }, p_213707_2_));
        if (optionalint.isPresent()) {
            MerchantOffers merchantoffers = this.getOffers();
            if (!merchantoffers.isEmpty()) {
                p_213707_1_.sendMerchantOffers(optionalint.getAsInt(), merchantoffers, p_213707_3_, this.getVillagerXp(), this.showProgressBar(), this.canRestock());
            }
        }

    }

    public void setTradingPlayer(@Nullable PlayerEntity p_70932_1_) {
        boolean flag = this.getTradingPlayer() != null && p_70932_1_ == null;
        if (flag) {
            this.stopTrading();
        }

    }

    public boolean isTrading() {
        return this.tradingPlayer != null;
    }

    public MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = new MerchantOffers();
            this.updateTrades();
        }

        return this.offers;
    }

    @OnlyIn(Dist.CLIENT)
    public void overrideOffers(@Nullable MerchantOffers p_213703_1_) {
    }

    public void overrideXp(int p_213702_1_) {
    }

    public void notifyTrade(MerchantOffer p_213704_1_) {
        p_213704_1_.increaseUses();
        this.ambientSoundTime = -this.getAmbientSoundInterval();
        this.rewardTradeXp(p_213704_1_);
        // TODO: do we need this?
//        if (this.tradingPlayer instanceof ServerPlayerEntity) {
//            CriteriaTriggers.TRADE.trigger((ServerPlayerEntity)this.tradingPlayer, this, p_213704_1_.getResult());
//        }

    }

    public boolean showProgressBar() {
        return false;
    }

    public void notifyTradeUpdated(ItemStack p_110297_1_) {
        if (!this.level.isClientSide && this.ambientSoundTime > -this.getAmbientSoundInterval() + 20) {
            this.ambientSoundTime = -this.getAmbientSoundInterval();
            this.playSound(this.getTradeUpdatedSound(!p_110297_1_.isEmpty()), this.getSoundVolume(), this.getVoicePitch());
        }

    }

    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.VILLAGER_YES;
    }

    protected SoundEvent getTradeUpdatedSound(boolean p_213721_1_) {
        return p_213721_1_ ? SoundEvents.VILLAGER_YES : SoundEvents.VILLAGER_NO;
    }

    public void playCelebrateSound() {
        this.playSound(SoundEvents.VILLAGER_CELEBRATE, this.getSoundVolume(), this.getVoicePitch());
    }

    protected void stopTrading() {
        this.setTradingPlayer((PlayerEntity)null);
        this.resetSpecialPrices();
    }

    private void resetSpecialPrices() {
        for(MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetSpecialPriceDiff();
        }

    }

    @OnlyIn(Dist.CLIENT)
    protected void addParticlesAroundSelf(IParticleData p_213718_1_) {
        for(int i = 0; i < 5; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(p_213718_1_, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
        }

    }

    public boolean setSlot(int p_174820_1_, ItemStack p_174820_2_) {
        if (super.setSlot(p_174820_1_, p_174820_2_)) {
            return true;
        } else {
            int i = p_174820_1_ - 300;
            if (i >= 0 && i < this.inventory.getContainerSize()) {
                this.inventory.setItem(i, p_174820_2_);
                return true;
            } else {
                return false;
            }
        }
    }

    public World getLevel() {
        return this.level;
    }

    public boolean canBeLeashed(PlayerEntity p_184652_1_) {
        return false;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public boolean canRestock() {
        return true;
    }

    public void restock() {
        this.updateDemand();

        for(MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetUses();
        }

        this.lastRestockGameTime = this.level.getGameTime();
        ++this.numberOfRestocksToday;
    }

    private boolean needsToRestock() {
        for(MerchantOffer merchantoffer : this.getOffers()) {
            if (merchantoffer.needsRestock()) {
                return true;
            }
        }

        return false;
    }

    private boolean allowedToRestock() {
        return this.numberOfRestocksToday == 0 || this.numberOfRestocksToday < 2 && this.level.getGameTime() > this.lastRestockGameTime + 2400L;
    }

    public boolean shouldRestock() {
        long i = this.lastRestockGameTime + 12000L;
        long j = this.level.getGameTime();
        boolean flag = j > i;
        long k = this.level.getDayTime();
        if (this.lastRestockCheckDayTime > 0L) {
            long l = this.lastRestockCheckDayTime / 24000L;
            long i1 = k / 24000L;
            flag |= i1 > l;
        }

        this.lastRestockCheckDayTime = k;
        if (flag) {
            this.lastRestockGameTime = j;
            this.resetNumberOfRestocks();
        }

        return this.allowedToRestock() && this.needsToRestock();
    }

    private void catchUpDemand() {
        int i = 2 - this.numberOfRestocksToday;
        if (i > 0) {
            for(MerchantOffer merchantoffer : this.getOffers()) {
                merchantoffer.resetUses();
            }
        }

        for(int j = 0; j < i; ++j) {
            this.updateDemand();
        }

    }

    private void updateDemand() {
        for(MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.updateDemand();
        }

    }

    private void updateSpecialPrices(PlayerEntity p_213762_1_) {
        int i = this.getPlayerReputation(p_213762_1_);
        if (i != 0) {
            for(MerchantOffer merchantoffer : this.getOffers()) {
                merchantoffer.addToSpecialPriceDiff(-MathHelper.floor((float)i * merchantoffer.getPriceMultiplier()));
            }
        }

        if (p_213762_1_.hasEffect(Effects.HERO_OF_THE_VILLAGE)) {
            EffectInstance effectinstance = p_213762_1_.getEffect(Effects.HERO_OF_THE_VILLAGE);
            int k = effectinstance.getAmplifier();

            for(MerchantOffer merchantoffer1 : this.getOffers()) {
                double d0 = 0.3D + 0.0625D * (double)k;
                int j = (int)Math.floor(d0 * (double)merchantoffer1.getBaseCostA().getCount());
                merchantoffer1.addToSpecialPriceDiff(-Math.max(j, 1));
            }
        }

    }

    @Nullable
    public PlayerEntity getTradingPlayer() {
        return this.tradingPlayer;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_UNHAPPY_COUNTER, 0);
        this.entityData.define(MERCHANT_DATA, new VillagerData(VillagerType.PLAINS, MerchantHandler.NONE.get(), 1));
    }

    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        VillagerData.CODEC.encodeStart(NBTDynamicOps.INSTANCE, this.getVillagerData()).resultOrPartial(LOGGER::error).ifPresent((p_234547_1_) -> {
            p_213281_1_.put("VillagerData", p_234547_1_);
        });
        p_213281_1_.put("Gossips", this.gossips.store(NBTDynamicOps.INSTANCE).getValue());
        p_213281_1_.putInt("Xp", this.villagerXp);
        p_213281_1_.putLong("LastRestock", this.lastRestockGameTime);
        p_213281_1_.putLong("LastGossipDecay", this.lastGossipDecayTime);
        p_213281_1_.putInt("RestocksToday", this.numberOfRestocksToday);
        if (this.assignProfessionWhenSpawned) {
            p_213281_1_.putBoolean("AssignProfessionWhenSpawned", true);
        }
        MerchantOffers merchantoffers = this.getOffers();
        if (!merchantoffers.isEmpty()) {
            p_213281_1_.put("Offers", merchantoffers.createTag());
        }

        p_213281_1_.put("Inventory", this.inventory.createTag());
    }

    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        if (p_70037_1_.contains("VillagerData", 10)) {
            DataResult<VillagerData> dataresult = VillagerData.CODEC.parse(new Dynamic<>(NBTDynamicOps.INSTANCE, p_70037_1_.get("VillagerData")));
            dataresult.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
        }

        if (p_70037_1_.contains("Offers", 10)) {
            this.offers = new MerchantOffers(p_70037_1_.getCompound("Offers"));
        }

        ListNBT listnbt = p_70037_1_.getList("Gossips", 10);
        this.gossips.update(new Dynamic<>(NBTDynamicOps.INSTANCE, listnbt));
        if (p_70037_1_.contains("Xp", 3)) {
            this.villagerXp = p_70037_1_.getInt("Xp");
        }

        this.lastRestockGameTime = p_70037_1_.getLong("LastRestock");
        this.lastGossipDecayTime = p_70037_1_.getLong("LastGossipDecay");
        //this.setCanPickUpLoot(true);
        if (this.level instanceof ServerWorld) {
            this.refreshBrain((ServerWorld)this.level);
        }

        this.numberOfRestocksToday = p_70037_1_.getInt("RestocksToday");
        if (p_70037_1_.contains("AssignProfessionWhenSpawned")) {
            this.assignProfessionWhenSpawned = p_70037_1_.getBoolean("AssignProfessionWhenSpawned");
        }

        if (p_70037_1_.contains("Offers", 10)) {
            this.offers = new MerchantOffers(p_70037_1_.getCompound("Offers"));
        }

        this.inventory.fromTag(p_70037_1_.getList("Inventory", 10));
    }

    public boolean removeWhenFarAway(double p_213397_1_) {
        return false;
    }

    @Nullable
    public Entity changeDimension(ServerWorld p_241206_1_, net.minecraftforge.common.util.ITeleporter teleporter) {
        this.stopTrading();
        return super.changeDimension(p_241206_1_, teleporter);
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        if (this.isSleeping()) {
            return null;
        } else {
            return this.isTrading() ? SoundEvents.VILLAGER_TRADE : SoundEvents.VILLAGER_AMBIENT;
        }
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.VILLAGER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    public void playWorkSound() {
        SoundEvent soundevent = this.getVillagerData().getProfession().getWorkSound();
        if (soundevent != null) {
            this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
        }

    }

    public void setVillagerData(VillagerData p_213753_1_) {
        VillagerData villagerdata = this.getVillagerData();
        if (villagerdata.getProfession() != p_213753_1_.getProfession()) {
            this.offers = null;
        }

        this.entityData.set(MERCHANT_DATA, p_213753_1_);
    }

    @Override
    public VillagerData getVillagerData() {
        return this.entityData.get(MERCHANT_DATA);
    }

    protected void rewardTradeXp(MerchantOffer p_213713_1_) {
        int i = 3 + this.random.nextInt(4);
        this.villagerXp += p_213713_1_.getXp();
        this.lastTradedPlayer = this.getTradingPlayer();

        if (p_213713_1_.shouldRewardExp()) {
            this.level.addFreshEntity(new ExperienceOrbEntity(this.level, this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }

    }

    public void setLastHurtByMob(@Nullable LivingEntity p_70604_1_) {
        if (p_70604_1_ != null && this.level instanceof ServerWorld) {
            ((ServerWorld)this.level).onReputationEvent(IReputationType.VILLAGER_HURT, p_70604_1_, this);
            if (this.isAlive() && p_70604_1_ instanceof PlayerEntity) {
                this.level.broadcastEntityEvent(this, (byte)13);
            }
        }

        super.setLastHurtByMob(p_70604_1_);
    }

    public void die(DamageSource p_70645_1_) {
        LOGGER.info("Villager {} died, message: '{}'", this, p_70645_1_.getLocalizedDeathMessage(this).getString());
        Entity entity = p_70645_1_.getEntity();
        if (entity != null) {
            this.tellWitnessesThatIWasMurdered(entity);
        }

        this.stopTrading();
        this.releaseAllPois();
        super.die(p_70645_1_);
    }

    private void releaseAllPois() {
        this.releasePoi(MemoryModuleType.HOME);
        this.releasePoi(MemoryModuleType.JOB_SITE);
        this.releasePoi(MemoryModuleType.POTENTIAL_JOB_SITE);
        this.releasePoi(MemoryModuleType.MEETING_POINT);
    }

    private void tellWitnessesThatIWasMurdered(Entity p_223361_1_) {
        if (this.level instanceof ServerWorld) {
            Optional<List<LivingEntity>> optional = this.brain.getMemory(MemoryModuleType.VISIBLE_LIVING_ENTITIES);
            if (optional.isPresent()) {
                ServerWorld serverworld = (ServerWorld)this.level;
                optional.get().stream().filter((p_223349_0_) -> {
                    return p_223349_0_ instanceof IReputationTracking;
                }).forEach((p_223342_2_) -> {
                    serverworld.onReputationEvent(IReputationType.VILLAGER_KILLED, p_223361_1_, (IReputationTracking)p_223342_2_);
                });
            }
        }
    }

    public void releasePoi(MemoryModuleType<GlobalPos> p_213742_1_) {
        if (this.level instanceof ServerWorld) {
            MinecraftServer minecraftserver = ((ServerWorld)this.level).getServer();
            this.brain.getMemory(p_213742_1_).ifPresent((p_213752_3_) -> {
                ServerWorld serverworld = minecraftserver.getLevel(p_213752_3_.dimension());
                if (serverworld != null) {
                    PointOfInterestManager pointofinterestmanager = serverworld.getPoiManager();
                    Optional<PointOfInterestType> optional = pointofinterestmanager.getType(p_213752_3_.pos());
                    BiPredicate<MerchantEntity, PointOfInterestType> bipredicate = POI_MEMORIES.get(p_213742_1_);
                    if (optional.isPresent() && bipredicate.test(this, optional.get())) {
                        pointofinterestmanager.release(p_213752_3_.pos());
                        DebugPacketSender.sendPoiTicketCountPacket(serverworld, p_213752_3_.pos());
                    }

                }
            });
        }
    }

    public boolean canBreed() { return false; }

    public int getPlayerReputation(PlayerEntity p_223107_1_) {
        return this.gossips.getReputation(p_223107_1_.getUUID(), (p_223103_0_) -> {
            return true;
        });
    }

    public void setOffers(MerchantOffers p_213768_1_) {
        this.offers = p_213768_1_;
    }

    protected ITextComponent getTypeName() {
        net.minecraft.util.ResourceLocation profName = this.getVillagerData().getProfession().getRegistryName();
        return new TranslationTextComponent(this.getType().getDescriptionId() + '.' + (!"minecraft".equals(profName.getNamespace()) ? profName.getNamespace() + '.' : "") + profName.getPath());
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte p_70103_1_) {
        if (p_70103_1_ == 12) {
            this.addParticlesAroundSelf(ParticleTypes.HEART);
        } else if (p_70103_1_ == 13) {
            this.addParticlesAroundSelf(ParticleTypes.ANGRY_VILLAGER);
        } else if (p_70103_1_ == 14) {
            this.addParticlesAroundSelf(ParticleTypes.HAPPY_VILLAGER);
        } else if (p_70103_1_ == 42) {
            this.addParticlesAroundSelf(ParticleTypes.SPLASH);
        } else {
            super.handleEntityEvent(p_70103_1_);
        }

    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
        if (p_213386_3_ == SpawnReason.BREEDING) {
            this.setVillagerData(this.getVillagerData().setProfession(MerchantHandler.NONE.get()));
        }

        if (p_213386_3_ == SpawnReason.COMMAND || p_213386_3_ == SpawnReason.SPAWN_EGG || p_213386_3_ == SpawnReason.SPAWNER || p_213386_3_ == SpawnReason.DISPENSER) {
            this.setVillagerData(this.getVillagerData().setType(VillagerType.byBiome(p_213386_1_.getBiomeName(this.blockPosition()))));
        }

        if (p_213386_3_ == SpawnReason.STRUCTURE) {
            this.assignProfessionWhenSpawned = true;
        }

        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
    }

    protected void updateTrades() {
        VillagerData villagerdata = this.getVillagerData();
        Int2ObjectMap<VillagerTrades.ITrade[]> int2objectmap = VillagerTrades.TRADES.get(villagerdata.getProfession());
        if (int2objectmap != null && !int2objectmap.isEmpty()) {
            VillagerTrades.ITrade[] avillagertrades$itrade = int2objectmap.get(villagerdata.getLevel());
            if (avillagertrades$itrade != null) {
                MerchantOffers merchantoffers = this.getOffers();
                this.addOffersFromItemListings(merchantoffers, avillagertrades$itrade, 2);
            }
        }
    }

    protected void addOffersFromItemListings(MerchantOffers p_213717_1_, VillagerTrades.ITrade[] p_213717_2_, int p_213717_3_) {
        Set<Integer> set = Sets.newHashSet();
        if (p_213717_2_.length > p_213717_3_) {
            while(set.size() < p_213717_3_) {
                set.add(this.random.nextInt(p_213717_2_.length));
            }
        } else {
            for(int i = 0; i < p_213717_2_.length; ++i) {
                set.add(i);
            }
        }

        for(Integer integer : set) {
            VillagerTrades.ITrade villagertrades$itrade = p_213717_2_[integer];
            MerchantOffer merchantoffer = villagertrades$itrade.getOffer(this, this.random);
            if (merchantoffer != null) {
                p_213717_1_.add(merchantoffer);
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d getRopeHoldPosition(float p_241843_1_) {
        float f = MathHelper.lerp(p_241843_1_, this.yBodyRotO, this.yBodyRot) * ((float)Math.PI / 180F);
        Vector3d vector3d = new Vector3d(0.0D, this.getBoundingBox().getYsize() - 1.0D, 0.2D);
        return this.getPosition(p_241843_1_).add(vector3d.yRot(-f));
    }

    public void gossip(ServerWorld p_242368_1_, MerchantEntity p_242368_2_, long p_242368_3_) {
        if ((p_242368_3_ < this.lastGossipTime || p_242368_3_ >= this.lastGossipTime + 1200L) && (p_242368_3_ < p_242368_2_.lastGossipTime || p_242368_3_ >= p_242368_2_.lastGossipTime + 1200L)) {
            this.gossips.transferFrom(p_242368_2_.gossips, this.random, 10);
            this.lastGossipTime = p_242368_3_;
            p_242368_2_.lastGossipTime = p_242368_3_;
        }
    }

    private void maybeDecayGossip() {
        long i = this.level.getGameTime();
        if (this.lastGossipDecayTime == 0L) {
            this.lastGossipDecayTime = i;
        } else if (i >= this.lastGossipDecayTime + 24000L) {
            this.gossips.decay();
            this.lastGossipDecayTime = i;
        }
    }

    public int getVillagerXp() {
        return this.villagerXp;
    }

    public void setVillagerXp(int p_213761_1_) {
        this.villagerXp = p_213761_1_;
    }

    private void resetNumberOfRestocks() {
        this.catchUpDemand();
        this.numberOfRestocksToday = 0;
    }

    public GossipManager getGossips() {
        return this.gossips;
    }

    public void setGossips(INBT p_223716_1_) {
        this.gossips.update(new Dynamic<>(NBTDynamicOps.INSTANCE, p_223716_1_));
    }

    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPacketSender.sendEntityBrain(this);
    }

    public void startSleeping(BlockPos p_213342_1_) {
        super.startSleeping(p_213342_1_);
        this.brain.setMemory(MemoryModuleType.LAST_SLEPT, this.level.getGameTime());
        this.brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        this.brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    public void stopSleeping() {
        super.stopSleeping();
        this.brain.setMemory(MemoryModuleType.LAST_WOKEN, this.level.getGameTime());
    }
}