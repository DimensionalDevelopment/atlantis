package com.mystic.atlantis.entities.blockbenchentities;

import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.LOOP;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class LeviathanEntity extends WaterAnimal implements IAnimatable {
    public static final int TICKS_PER_FLAP = Mth.ceil(24.166098F);
    private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(LeviathanEntity.class, EntityDataSerializers.INT);
    private Vec3 moveTargetPoint;
    private BlockPos anchorPoint;
    private LeviathanEntity.AttackPhase attackPhase;
    private static final AnimationBuilder SWIM_IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.leviathan.swim", LOOP);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public static AttributeSupplier.Builder createLeviathanAttributes() {
        return Mob.createMobAttributes()
        		.add(Attributes.ATTACK_DAMAGE, 2D);
    }

    public LeviathanEntity(EntityType<? extends WaterAnimal> arg, Level arg2) {
        super(arg, arg2);
        this.moveTargetPoint = Vec3.ZERO;
        this.anchorPoint = BlockPos.ZERO;
        this.attackPhase = LeviathanEntity.AttackPhase.CIRCLE;
        this.xpReward = 5;
        this.moveControl = new LeviathanEntity.LeviathanEntityMoveControl(this);
        this.lookControl = new LeviathanEntityLookControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LeviathanEntity.LeviathanEntityAttackStrategyGoal());
        this.goalSelector.addGoal(2, new LeviathanEntity.LeviathanEntitySweepAttackGoal());
        this.goalSelector.addGoal(3, new LeviathanEntity.LeviathanEntityCircleAroundAnchorGoal());
        this.targetSelector.addGoal(0, new LeviathanEntity.LeviathanEntityAttackJellyfishGoal());
        this.targetSelector.addGoal(1, new LeviathanEntity.LeviathanEntityAttackPlayerTargetGoal());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            float f = Mth.cos((float)(this.getUniqueFlapTickOffset() + this.tickCount) * 7.448451F * ((float)Math.PI / 180F) + (float)Math.PI);
            float f1 = Mth.cos((float)(this.getUniqueFlapTickOffset() + this.tickCount + 1) * 7.448451F * ((float)Math.PI / 180F) + (float)Math.PI);
            if (f > 0.0F && f1 <= 0.0F) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.PHANTOM_FLAP, this.getSoundSource(), 0.95F + this.random.nextFloat() * 0.05F, 0.95F + this.random.nextFloat() * 0.05F, false);
            }
            int i = this.getLeviathanEntitySize();
            float f2 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * (1.3F + 0.21F * (float)i);
            float f3 = Mth.sin(this.getYRot() * ((float)Math.PI / 180F)) * (1.3F + 0.21F * (float)i);
            float f4 = (0.3F + f * 0.45F) * ((float)i * 0.2F + 1.0F);
            this.level.addParticle(ParticleTypes.MYCELIUM, this.getX() + (double)f2, this.getY() + (double)f4, this.getZ() + (double)f3, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.MYCELIUM, this.getX() - (double)f2, this.getY() + (double)f4, this.getZ() - (double)f3, 0.0D, 0.0D, 0.0D);
        }
    }

    public void setLeviathanEntitySize(int pSize) {
        this.entityData.set(ID_SIZE, Mth.clamp(pSize, 0, 64));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_SIZE, 0);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor pLevel, @NotNull DifficultyInstance pDifficulty, @NotNull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.anchorPoint = this.blockPosition().above(5);
        this.setLeviathanEntitySize(0);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("AX")) {
            this.anchorPoint = new BlockPos(pCompound.getInt("AX"), pCompound.getInt("AY"), pCompound.getInt("AZ"));
        }
        this.setLeviathanEntitySize(pCompound.getInt("Size"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("AX", this.anchorPoint.getX());
        pCompound.putInt("AY", this.anchorPoint.getY());
        pCompound.putInt("AZ", this.anchorPoint.getZ());
        pCompound.putInt("Size", this.getLeviathanEntitySize());
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        return true;
    }

    @Override
    public @NotNull SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PHANTOM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return SoundEvents.PHANTOM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PHANTOM_DEATH;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    protected float getSoundVolume() {
        return 1.0F;
    }

    @Override
    public boolean canAttackType(@NotNull EntityType<?> pType) {
        return true;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
        int i = this.getLeviathanEntitySize();
        EntityDimensions entitydimensions = super.getDimensions(pPose);
        float f = (entitydimensions.width + 0.2F * (float)i) / entitydimensions.width;
        return entitydimensions.scale(f);
    }

    public int getLeviathanEntitySize() {
        return this.entityData.get(ID_SIZE);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.35F;
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> pKey) {
        if (ID_SIZE.equals(pKey)) {
            this.updateLeviathanEntitySizeInfo();
        }
        super.onSyncedDataUpdated(pKey);
    }

    private void updateLeviathanEntitySizeInfo() {
        this.refreshDimensions();
        Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(6 + this.getLeviathanEntitySize());
    }

    public int getUniqueFlapTickOffset() {
        return this.getId() * 3;
    }

    @Override
    public boolean isFlapping() {
        return (this.getUniqueFlapTickOffset() + this.tickCount) % TICKS_PER_FLAP == 0;
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new LeviathanEntity.LeviathanEntityBodyRotationControl(this);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(SWIM_IDLE_ANIMATION);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
    
     enum AttackPhase {
        CIRCLE,
        SWOOP
    }

    class LeviathanEntityAttackJellyfishGoal extends Goal {
        private int nextScanTick = reducedTickDelay(20);
        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        @Override
        public boolean canUse() {
            if (LeviathanEntity.this.getTarget() != null) {
                return false;
            }
            if (this.nextScanTick > 0) {
                --this.nextScanTick;
            }
            else {
                this.nextScanTick = reducedTickDelay(60);
                List<JellyfishEntity> list = LeviathanEntity.this.level.getEntitiesOfClass(JellyfishEntity.class, LeviathanEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                List<Jellyfish2Entity> list2 = LeviathanEntity.this.level.getEntitiesOfClass(Jellyfish2Entity.class, LeviathanEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());
                    for(JellyfishEntity jellyFish : list) {
                        if (LeviathanEntity.this.canAttack(jellyFish, TargetingConditions.DEFAULT)) {
                            LeviathanEntity.this.setTarget(jellyFish);
                            return true;
                        }
                    }
                }
                else if (!list2.isEmpty()) {
                    list2.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());
                    for(Jellyfish2Entity jellyfish2 : list2) {
                        if (LeviathanEntity.this.canAttack(jellyfish2, TargetingConditions.DEFAULT)) {
                            LeviathanEntity.this.setTarget(jellyfish2);
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean canContinueToUse() {
            LivingEntity livingentity = LeviathanEntity.this.getTarget();
            return livingentity != null && LeviathanEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT);
        }

        @Override
        public void start() {
            super.start();
            LeviathanEntity.this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, Integer.MAX_VALUE - 1));
        }

        @Override
        public void stop() {
            super.stop();
            LeviathanEntity.this.removeEffect(MobEffects.INVISIBILITY);
        }
    }

    class LeviathanEntityAttackPlayerTargetGoal extends Goal {
        private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D);
        private int nextScanTick = reducedTickDelay(20);
        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        @Override
        public boolean canUse() {
            if (LeviathanEntity.this.getTarget() != null) {
                return false;
            }
            if (this.nextScanTick > 0) {
                --this.nextScanTick;
            }
            else {
                this.nextScanTick = reducedTickDelay(60);
                List<Player> list = LeviathanEntity.this.level.getNearbyPlayers(this.attackTargeting, LeviathanEntity.this, LeviathanEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());
                    for(Player player : list) {
                        if (LeviathanEntity.this.canAttack(player, TargetingConditions.DEFAULT)) {
                            if (LeviathanEntity.this.getLastHurtByMob() instanceof Player) {
                                LeviathanEntity.this.setTarget(player);
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean canContinueToUse() {
            LivingEntity livingentity = LeviathanEntity.this.getTarget();
            return livingentity != null && LeviathanEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT);
        }

        @Override
        public void start() {
            super.start();
            LeviathanEntity.this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, Integer.MAX_VALUE - 1));
        }

        @Override
        public void stop() {
            super.stop();
            LeviathanEntity.this.removeEffect(MobEffects.INVISIBILITY);
        }
    }

    class LeviathanEntityAttackStrategyGoal extends Goal {
        private int nextSweepTick;

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = LeviathanEntity.this.getTarget();
            return livingentity != null && LeviathanEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.nextSweepTick = this.adjustedTickDelay(10);
            LeviathanEntity.this.attackPhase = LeviathanEntity.AttackPhase.CIRCLE;
            this.setAnchorAboveTarget();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            LeviathanEntity.this.anchorPoint = LeviathanEntity.this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, LeviathanEntity.this.anchorPoint).above(10 + LeviathanEntity.this.random.nextInt(20));
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (LeviathanEntity.this.attackPhase == LeviathanEntity.AttackPhase.CIRCLE) {
                --this.nextSweepTick;
                if (this.nextSweepTick <= 0) {
                    LeviathanEntity.this.attackPhase = LeviathanEntity.AttackPhase.SWOOP;
                    this.setAnchorAboveTarget();
                    this.nextSweepTick = this.adjustedTickDelay((8 + LeviathanEntity.this.random.nextInt(4)) * 20);
                    LeviathanEntity.this.playSound(SoundEvents.PHANTOM_SWOOP, 10.0F, 0.95F + LeviathanEntity.this.random.nextFloat() * 0.1F);
                }
            }

        }

        private void setAnchorAboveTarget() {
            if (LeviathanEntity.this.getTarget() != null) {
                LeviathanEntity.this.anchorPoint = LeviathanEntity.this.getTarget().blockPosition().above(20 + LeviathanEntity.this.random.nextInt(20));
                if (LeviathanEntity.this.anchorPoint.getY() < LeviathanEntity.this.level.getSeaLevel()) {
                    LeviathanEntity.this.anchorPoint = new BlockPos(LeviathanEntity.this.anchorPoint.getX(), LeviathanEntity.this.level.getSeaLevel() + 1, LeviathanEntity.this.anchorPoint.getZ());
                }
            }
        }
    }

    class LeviathanEntityBodyRotationControl extends BodyRotationControl {
        public LeviathanEntityBodyRotationControl(Mob p_33216_) {
            super(p_33216_);
        }

        /**
         * Update the Head and Body rendenring angles
         */
        public void clientTick() {
            LeviathanEntity.this.yHeadRot = LeviathanEntity.this.yBodyRot;
            LeviathanEntity.this.yBodyRot = LeviathanEntity.this.getYRot();
        }
    }

    class LeviathanEntityCircleAroundAnchorGoal extends LeviathanEntity.LeviathanEntityMoveTargetGoal {
        private float angle;
        private float distance;
        private float height;
        private float clockwise;

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return LeviathanEntity.this.getTarget() == null || LeviathanEntity.this.attackPhase == LeviathanEntity.AttackPhase.CIRCLE;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.distance = 5.0F + LeviathanEntity.this.random.nextFloat() * 10.0F;
            this.height = -4.0F + LeviathanEntity.this.random.nextFloat() * 9.0F;
            this.clockwise = LeviathanEntity.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.selectNext();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (LeviathanEntity.this.random.nextInt(this.adjustedTickDelay(350)) == 0) {
                this.height = -4.0F + LeviathanEntity.this.random.nextFloat() * 9.0F;
            }

            if (LeviathanEntity.this.random.nextInt(this.adjustedTickDelay(250)) == 0) {
                ++this.distance;
                if (this.distance > 15.0F) {
                    this.distance = 5.0F;
                    this.clockwise = -this.clockwise;
                }
            }

            if (LeviathanEntity.this.random.nextInt(this.adjustedTickDelay(450)) == 0) {
                this.angle = LeviathanEntity.this.random.nextFloat() * 2.0F * (float)Math.PI;
                this.selectNext();
            }

            if (this.touchingTarget()) {
                this.selectNext();
            }

            if (LeviathanEntity.this.moveTargetPoint.y < LeviathanEntity.this.getY() && !LeviathanEntity.this.level.isEmptyBlock(LeviathanEntity.this.blockPosition().below(1))) {
                this.height = Math.max(1.0F, this.height);
                this.selectNext();
            }

            if (LeviathanEntity.this.moveTargetPoint.y > LeviathanEntity.this.getY() && !LeviathanEntity.this.level.isEmptyBlock(LeviathanEntity.this.blockPosition().above(1))) {
                this.height = Math.min(-1.0F, this.height);
                this.selectNext();
            }

        }

        private void selectNext() {
            if (BlockPos.ZERO.equals(LeviathanEntity.this.anchorPoint)) {
                LeviathanEntity.this.anchorPoint = LeviathanEntity.this.blockPosition();
            }

            this.angle += this.clockwise * 15.0F * ((float)Math.PI / 180F);
            LeviathanEntity.this.moveTargetPoint = Vec3.atLowerCornerOf(LeviathanEntity.this.anchorPoint).add(this.distance * Mth.cos(this.angle), -4.0F + this.height, this.distance * Mth.sin(this.angle));
        }
    }

    static class LeviathanEntityLookControl extends LookControl {
        public LeviathanEntityLookControl(Mob p_33235_) {
            super(p_33235_);
        }
        /**
         * Updates look
         */
        public void tick() {}
    }

    class LeviathanEntityMoveControl extends MoveControl {
        private float speed = 0.1F;

        public LeviathanEntityMoveControl(Mob p_33241_) {
            super(p_33241_);
        }

        public void tick() {
            if (LeviathanEntity.this.horizontalCollision) {
                LeviathanEntity.this.setYRot(LeviathanEntity.this.getYRot() + 180.0f);
                this.speed = 0.1f;
            }
            float f = (float)(LeviathanEntity.this.moveTargetPoint.x - LeviathanEntity.this.getX());
            float g = (float)(LeviathanEntity.this.moveTargetPoint.y - LeviathanEntity.this.getY());
            float h = (float)(LeviathanEntity.this.moveTargetPoint.z - LeviathanEntity.this.getZ());
            double d = Mth.sqrt(f * f + h * h);
            if (Math.abs(d) > (double)1.0E-5f) {
                double e = 1.0 - (double)Mth.abs(g * 0.7f) / d;
                f = (float)((double)f * e);
                h = (float)((double)h * e);
                d = Mth.sqrt(f * f + h * h);
                double i = Mth.sqrt(f * f + h * h + g * g);
                float j = LeviathanEntity.this.getYRot();
                float k = (float)Mth.atan2(h, f);
                float l = Mth.wrapDegrees(LeviathanEntity.this.getYRot() + 90.0f);
                float m = Mth.wrapDegrees(k * 57.295776f);
                LeviathanEntity.this.setYRot(Mth.approachDegrees(l, m, 4.0f) - 90.0f);
                LeviathanEntity.this.yBodyRot = LeviathanEntity.this.getYRot();
                this.speed = Mth.degreesDifferenceAbs(j, LeviathanEntity.this.getYRot()) < 3.0f ? Mth.approach(this.speed, 1.8f, 0.005f * (1.8f / this.speed)) : Mth.approach(this.speed, 0.2f, 0.025f);
                float n = (float)(-(Mth.atan2(-g, d) * 57.2957763671875));
                LeviathanEntity.this.setXRot(n);
                float o = LeviathanEntity.this.getYRot() + 90.0f;
                double p = (double)(this.speed * Mth.cos(o * ((float)Math.PI / 180))) * Math.abs((double)f / i);
                double q = (double)(this.speed * Mth.sin(o * ((float)Math.PI / 180))) * Math.abs((double)h / i);
                double r = (double)(this.speed * Mth.sin(n * ((float)Math.PI / 180))) * Math.abs((double)g / i);
                Vec3 vec3 = LeviathanEntity.this.getDeltaMovement();
                LeviathanEntity.this.setDeltaMovement(vec3.add(new Vec3(p, r, q).subtract(vec3).scale(0.2)));
            }
        }
    }

    abstract class LeviathanEntityMoveTargetGoal extends Goal {
        public LeviathanEntityMoveTargetGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        protected boolean touchingTarget() {
            return LeviathanEntity.this.moveTargetPoint.distanceToSqr(LeviathanEntity.this.getX(), LeviathanEntity.this.getY(), LeviathanEntity.this.getZ()) < 4.0D;
        }
    }

    class LeviathanEntitySweepAttackGoal extends LeviathanEntity.LeviathanEntityMoveTargetGoal {
        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return LeviathanEntity.this.getTarget() != null && LeviathanEntity.this.attackPhase == LeviathanEntity.AttackPhase.SWOOP;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = LeviathanEntity.this.getTarget();
            if (livingentity == null) {
                return false;
            }
            else if (!livingentity.isAlive()) {
                return false;
            }
            else {
                if (livingentity instanceof Player player) {
                    if (livingentity.isSpectator() || player.isCreative()) {
                        return false;
                    }
                }
                return this.canUse();
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {}

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            LeviathanEntity.this.setTarget(null);
            LeviathanEntity.this.attackPhase = LeviathanEntity.AttackPhase.CIRCLE;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = LeviathanEntity.this.getTarget();
            if (livingentity != null) {
                LeviathanEntity.this.moveTargetPoint = new Vec3(livingentity.getX(), livingentity.getY(0.5D), livingentity.getZ());
                if (LeviathanEntity.this.getBoundingBox().inflate(0.2F).intersects(livingentity.getBoundingBox())) {
                    LeviathanEntity.this.doHurtTarget(livingentity);
                    LeviathanEntity.this.attackPhase = LeviathanEntity.AttackPhase.CIRCLE;
                    if (!LeviathanEntity.this.isSilent()) {
                        LeviathanEntity.this.level.levelEvent(1039, LeviathanEntity.this.blockPosition(), 0);
                    }
                }
                else if (LeviathanEntity.this.horizontalCollision || LeviathanEntity.this.hurtTime > 0) {
                    LeviathanEntity.this.attackPhase = LeviathanEntity.AttackPhase.CIRCLE;
                }
            }
        }
    }
}
