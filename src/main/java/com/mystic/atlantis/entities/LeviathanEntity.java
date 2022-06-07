package com.mystic.atlantis.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class LeviathanEntity extends WaterAnimal implements IAnimatable {

    private Vec3 moveTargetPoint;
    private BlockPos anchorPoint;
    private LeviathanEntity.AttackPhase attackPhase;
    private static final AnimationBuilder SWIM_IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.leviathan.swim", true);

    private final AnimationFactory factory = new AnimationFactory(this);

    public LeviathanEntity(EntityType<? extends WaterAnimal> arg, Level arg2) {
        super(arg, arg2);
        this.moveTargetPoint = Vec3.ZERO;
        this.anchorPoint = BlockPos.ZERO;
        this.attackPhase = LeviathanEntity.AttackPhase.CIRCLE;
        this.xpReward = 5;
        this.moveControl = new LeviathanEntity.LeviathanEntityMoveControl(this);
        this.lookControl = new LeviathanEntity.LeviathanEntityLookControl(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LeviathanEntity.LeviathanEntityAttackStrategyGoal());
        this.goalSelector.addGoal(2, new LeviathanEntity.LeviathanEntitySweepAttackGoal());
        this.goalSelector.addGoal(3, new LeviathanEntity.LeviathanEntityCircleAroundAnchorGoal());
        this.targetSelector.addGoal(1, new LeviathanEntity.LeviathanEntityAttackPlayerTargetGoal());
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

    // Literally all of this is from the LeviathanEntity class
    class LeviathanEntityMoveControl extends MoveControl {
        private float speed = 0.1F;

        public LeviathanEntityMoveControl(Mob arg2) {
            super(arg2);
        }

        public void tick() {
            if (LeviathanEntity.this.horizontalCollision) {
                LeviathanEntity.this.setYRot(LeviathanEntity.this.getYRot() + 180.0F);
                this.speed = 0.1F;
            }

            float f = (float)(LeviathanEntity.this.moveTargetPoint.x - LeviathanEntity.this.getX());
            float g = (float)(LeviathanEntity.this.moveTargetPoint.y - LeviathanEntity.this.getY());
            float h = (float)(LeviathanEntity.this.moveTargetPoint.z - LeviathanEntity.this.getZ());
            double d = Mth.sqrt(f * f + h * h);
            if (Math.abs(d) > 9.999999747378752E-6D) {
                double e = 1.0D - (double)Mth.abs(g * 0.7F) / d;
                f = (float)((double)f * e);
                h = (float)((double)h * e);
                d = (double)Mth.sqrt(f * f + h * h);
                double i = (double)Mth.sqrt(f * f + h * h + g * g);
                float j = LeviathanEntity.this.getYRot();
                float k = (float)Mth.atan2((double)h, (double)f);
                float l = Mth.wrapDegrees(LeviathanEntity.this.getYRot() + 90.0F);
                float m = Mth.wrapDegrees(k * 57.295776F);
                LeviathanEntity.this.setYRot(Mth.approachDegrees(l, m, 4.0F) - 90.0F);
                LeviathanEntity.this.yBodyRot = LeviathanEntity.this.getYRot();
                if (Mth.degreesDifferenceAbs(j, LeviathanEntity.this.getYRot()) < 3.0F) {
                    this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
                } else {
                    this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
                }
                float n = (float)(-(Mth.atan2(-g, d) * 57.2957763671875D));
                LeviathanEntity.this.setXRot(n);
                float o = LeviathanEntity.this.getYRot() + 90.0F;
                double p = (double)(this.speed * Mth.cos(o * 0.017453292F)) * Math.abs((double)f / i);
                double q = (double)(this.speed * Mth.sin(o * 0.017453292F)) * Math.abs((double)h / i);
                double r = (double)(this.speed * Mth.sin(n * 0.017453292F)) * Math.abs((double)g / i);
                Vec3 vec3 = LeviathanEntity.this.getDeltaMovement();
                LeviathanEntity.this.setDeltaMovement(vec3.add((new Vec3(p, r, q)).subtract(vec3).scale(0.2D)));
            }

        }
    }

    private enum AttackPhase {
        CIRCLE,
        SWOOP;
        AttackPhase() {}
    }

    class LeviathanEntityLookControl extends LookControl {
        public LeviathanEntityLookControl(Mob arg2) {
            super(arg2);
        }
        public void tick() {}
    }

    class LeviathanEntityBodyRotationControl extends BodyRotationControl {
        public LeviathanEntityBodyRotationControl(Mob arg2) {
            super(arg2);
        }

        public void clientTick() {
            LeviathanEntity.this.yHeadRot = LeviathanEntity.this.yBodyRot;
            LeviathanEntity.this.yBodyRot = LeviathanEntity.this.getYRot();
        }
    }

    class LeviathanEntityAttackStrategyGoal extends Goal {
        private int nextSweepTick;

        LeviathanEntityAttackStrategyGoal() {}

        public boolean canUse() {
            LivingEntity livingEntity = LeviathanEntity.this.getTarget();
            return livingEntity != null && LeviathanEntity.this.canAttack(livingEntity, TargetingConditions.DEFAULT);
        }

        public void start() {
            this.nextSweepTick = this.adjustedTickDelay(10);
            LeviathanEntity.this.attackPhase = LeviathanEntity.AttackPhase.CIRCLE;
            this.setAnchorAboveTarget();
        }

        public void stop() {
            LeviathanEntity.this.anchorPoint = LeviathanEntity.this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, LeviathanEntity.this.anchorPoint).above(10 + LeviathanEntity.this.random.nextInt(20));
        }

        public void tick() {
            if (LeviathanEntity.this.attackPhase == AttackPhase.CIRCLE) {
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

    class LeviathanEntitySweepAttackGoal extends LeviathanEntity.LeviathanEntityMoveTargetGoal {
        private static final int CAT_SEARCH_TICK_DELAY = 20;
        private boolean isScaredOfCat;
        private int catSearchTick;

        LeviathanEntitySweepAttackGoal() {
            super();
        }

        public boolean canUse() {
            return LeviathanEntity.this.getTarget() != null && LeviathanEntity.this.attackPhase == LeviathanEntity.AttackPhase.SWOOP;
        }

        public boolean canContinueToUse() {
            LivingEntity livingEntity = LeviathanEntity.this.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else {
                if (livingEntity instanceof Player player) {
                    if (livingEntity.isSpectator() || player.isCreative()) {
                        return false;
                    }
                }

                if (!this.canUse()) {
                    return false;
                } else {
                    if (LeviathanEntity.this.tickCount > this.catSearchTick) {
                        this.catSearchTick = LeviathanEntity.this.tickCount + 20;
                        List<Cat> list = LeviathanEntity.this.level.getEntitiesOfClass(Cat.class, LeviathanEntity.this.getBoundingBox().inflate(16.0D), EntitySelector.ENTITY_STILL_ALIVE);
                        for (Cat cat : list) {
                            cat.hiss();
                        }

                        this.isScaredOfCat = !list.isEmpty();
                    }

                    return !this.isScaredOfCat;
                }
            }
        }

        public void start() {
        }

        public void stop() {
            LeviathanEntity.this.setTarget(null);
            LeviathanEntity.this.attackPhase = LeviathanEntity.AttackPhase.CIRCLE;
        }

        public void tick() {
            LivingEntity livingEntity = LeviathanEntity.this.getTarget();
            if (livingEntity != null) {
                LeviathanEntity.this.moveTargetPoint = new Vec3(livingEntity.getX(), livingEntity.getY(0.5D), livingEntity.getZ());
                if (LeviathanEntity.this.getBoundingBox().inflate(0.20000000298023224D).intersects(livingEntity.getBoundingBox())) {
                    LeviathanEntity.this.doHurtTarget(livingEntity);
                    LeviathanEntity.this.attackPhase = LeviathanEntity.AttackPhase.CIRCLE;
                    if (!LeviathanEntity.this.isSilent()) {
                        LeviathanEntity.this.level.levelEvent(1039, LeviathanEntity.this.blockPosition(), 0);
                    }
                } else if (LeviathanEntity.this.horizontalCollision || LeviathanEntity.this.hurtTime > 0) {
                    LeviathanEntity.this.attackPhase = LeviathanEntity.AttackPhase.CIRCLE;
                }

            }
        }
    }

    class LeviathanEntityCircleAroundAnchorGoal extends LeviathanEntityMoveTargetGoal {
        private float angle;
        private float distance;
        private float height;
        private float clockwise;

        LeviathanEntityCircleAroundAnchorGoal() {
            super();
        }

        public boolean canUse() {
            return LeviathanEntity.this.getTarget() == null || LeviathanEntity.this.attackPhase == LeviathanEntity.AttackPhase.CIRCLE;
        }

        public void start() {
            this.distance = 5.0F + LeviathanEntity.this.random.nextFloat() * 10.0F;
            this.height = -4.0F + LeviathanEntity.this.random.nextFloat() * 9.0F;
            this.clockwise = LeviathanEntity.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.selectNext();
        }

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
                this.angle = LeviathanEntity.this.random.nextFloat() * 2.0F * 3.1415927F;
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

            this.angle += this.clockwise * 15.0F * 0.017453292F;
            LeviathanEntity.this.moveTargetPoint = Vec3.atLowerCornerOf(LeviathanEntity.this.anchorPoint).add(this.distance * Mth.cos(this.angle), -4.0F + this.height, (double)(this.distance * Mth.sin(this.angle)));
        }
    }

    private class LeviathanEntityAttackPlayerTargetGoal extends Goal {
        private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D);
        private int nextScanTick = reducedTickDelay(20);

        LeviathanEntityAttackPlayerTargetGoal() {}

        public boolean canUse() {
            if (this.nextScanTick > 0) {
                --this.nextScanTick;
            }
            else {
                this.nextScanTick = reducedTickDelay(60);
                var list = LeviathanEntity.this.level.getNearbyPlayers(this.attackTargeting, LeviathanEntity.this, LeviathanEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(this::getY).reversed());
                    for (Player player : list) {
                        if (LeviathanEntity.this.canAttack(player, TargetingConditions.DEFAULT)) {
                            LeviathanEntity.this.setTarget(player);
                            return true;
                        }
                    }
                }

            }
            return false;
        }

        private double getY(Player player) {
            return player.getY();
        }

        public boolean canContinueToUse() {
            LivingEntity livingEntity = LeviathanEntity.this.getTarget();
            return livingEntity != null && LeviathanEntity.this.canAttack(livingEntity, TargetingConditions.DEFAULT);
        }
    }

    private abstract class LeviathanEntityMoveTargetGoal extends Goal {
        public LeviathanEntityMoveTargetGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        protected boolean touchingTarget() {
            return LeviathanEntity.this.moveTargetPoint.distanceToSqr(LeviathanEntity.this.getX(), LeviathanEntity.this.getY(), LeviathanEntity.this.getZ()) < 4.0D;
        }
    }
}
