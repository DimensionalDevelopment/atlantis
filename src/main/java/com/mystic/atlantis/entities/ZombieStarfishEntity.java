package com.mystic.atlantis.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ZombieStarfishEntity extends Monster implements GeoEntity {

    private static final RawAnimation WALK_ANIMATION = RawAnimation.begin().thenLoop("animation.starfish.walk");
    private static final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenLoop("animation.starfish.idle");
    private static final RawAnimation EAT_ANIMATION = RawAnimation.begin().thenLoop("animation.starfish.eat");
    private static final RawAnimation JUMP_ANIMATION = RawAnimation.begin().thenLoop("animation.starfish.jump");


    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public ZombieStarfishEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader world) {
        return world.isUnobstructed(this);
    }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public static AttributeSupplier.Builder createStarfishAttributes() {
        return createMobAttributes().add(Attributes.MOVEMENT_SPEED, 1.2d);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    public static boolean canSpawn(EntityType<?> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return pos.getY() >= 65 && 70 >= pos.getY() && world.getBlockState(pos).is(Blocks.WATER);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected boolean canRide(Entity entity) {
        return !entity.isVehicle() && !entity.hasPassenger(this);
    }

    public void rideTick() {
        final Entity entity = this.getVehicle();
        if(entity instanceof Player player) {
            if(player.isShiftKeyDown()) {
                this.stopRiding();
            }
        }

        if (!entity.isAlive() && !((entity instanceof Drowned) || (entity instanceof Player)) && !canRide(entity)) {
            this.stopRiding();
        } else {
            this.setDeltaMovement(0, 0, 0);
            this.tick();
            if(entity instanceof Drowned drowned) {
                this.setPos(drowned.getX(), Math.max(drowned.getY() + drowned.getEyeHeight() * 0.25F, drowned.getY()), drowned.getZ());
                drowned.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1, 5));
                if(drowned.getHealth() > 1.0f) {
                    drowned.hurt(damageSources().mobAttack(this), 1.0f);
                }

                if (!drowned.isAlive()) {
                    this.removeVehicle();
                }
            } else if (entity instanceof Player player) {
                this.setPos(player.getX(), Math.max(player.getY() + player.getEyeHeight(), player.getY()), player.getZ());
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1, 5));
                player.hurt(damageSources().mobAttack(this), 1.0f);

                if (!player.isAlive() || player.isCreative()) {
                    this.removeVehicle();
                }
            }
        }
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return true;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 10));
        goalSelector.addGoal(5, new HurtByTargetGoal(this).setAlertOthers(ZombieStarfishEntity.class));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        goalSelector.addGoal(3, new RandomSwimmingGoal(this, 0.6, 1));
        goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        //TODO fix
        //goalSelector.addGoal(1, new LatchOntoGoal<>(this, Drowned.class, true));
        //goalSelector.addGoal(0, new LatchOntoGoal<>(this, Player.class, true));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        // Flop when on land (not in water and on ground)
        if (!this.isInWater() && this.onGround() && !this.level().isClientSide) {
            this.setDeltaMovement(this.getDeltaMovement().x(), 0.0, this.getDeltaMovement().z());
            if (this.tickCount % 20 == 0) {
                this.jumpFromGround();
                this.playSound(SoundEvents.COD_FLOP, 1.0f, 1.0f);
            }
        }

        setTarget(level().getNearestPlayer(getX(), getY(), getZ(), 10, true));
    }

    @Override
    protected void jumpFromGround() {
        this.setDeltaMovement(this.getDeltaMovement().x(), 0.4, this.getDeltaMovement().z());
        this.hasImpulse = true;
    }

    public boolean isMovingSlowly() {
        return this.getDeltaMovement().x() != 0.0f && this.getDeltaMovement().y() != 0.0f && this.getDeltaMovement().z() != 0.0f;
    }

    private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
        if (this.isPassenger()) {
            event.getController().setAnimation(EAT_ANIMATION);
        } else if (isMovingSlowly()) {
            event.getController().setAnimation(WALK_ANIMATION);
        } else if (this.isSwimming()) {
            event.getController().setAnimation(JUMP_ANIMATION);
        } else {
            event.getController().setAnimation(IDLE_ANIMATION);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
