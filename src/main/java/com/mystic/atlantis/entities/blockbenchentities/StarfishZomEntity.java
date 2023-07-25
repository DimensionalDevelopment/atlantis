package com.mystic.atlantis.entities.blockbenchentities;

import com.mystic.atlantis.config.AtlantisConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import com.mystic.atlantis.entities.goal.LatchOntoGoal;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class StarfishZomEntity extends Monster implements IAnimatable {

    private static final AnimationBuilder WALK_ANIMATION = new AnimationBuilder().addAnimation("animation.starfish.walk", true);
    private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.starfish.idle", true);
    private static final AnimationBuilder EAT_ANIMATION = new AnimationBuilder().addAnimation("animation.starfish.eat", true);
    private static final AnimationBuilder JUMP_ANIMATION = new AnimationBuilder().addAnimation("animation.starfish.jump", true);


    private final AnimationFactory factory = new AnimationFactory(this);

    public StarfishZomEntity(EntityType<? extends Monster> entityType, Level world) {
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
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
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
                    drowned.hurt(DamageSource.mobAttack(this), 1.0f);
                }

                if (!drowned.isAlive()) {
                    this.removeVehicle();
                }
            } else if (entity instanceof Player player) {
                this.setPos(player.getX(), Math.max(player.getY() + player.getEyeHeight(), player.getY()), player.getZ());
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1, 5));
                player.hurt(DamageSource.mobAttack(this), 1.0f);

                if (!player.isAlive() || player.isCreative()) {
                    this.removeVehicle();
                }
            }
        }
    }

    public static boolean canSpawn(EntityType<StarfishZomEntity> starfishZomEntityType, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return pos.getY() >= 75 && 95 >= pos.getY() && serverWorldAccess.getBlockState(pos).is(Blocks.WATER);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return true;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 10));
        goalSelector.addGoal(5, new HurtByTargetGoal(this).setAlertOthers(StarfishZomEntity.class));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.6));
        goalSelector.addGoal(2, new TryFindWaterGoal(this));
        goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        //TODO fix
        //goalSelector.addGoal(1, new LatchOntoGoal<>(this, Drowned.class, true));
        //goalSelector.addGoal(0, new LatchOntoGoal<>(this, Player.class, true));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        setTarget(level.getNearestPlayer(getX(), getY(), getZ(), 10, true));
    }

    public boolean isMovingSlowly() {
        return this.getDeltaMovement().x() != 0.0f && this.getDeltaMovement().y() != 0.0f && this.getDeltaMovement().z() != 0.0f;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
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
    public AnimationFactory getFactory() {
        return factory;
    }
}
