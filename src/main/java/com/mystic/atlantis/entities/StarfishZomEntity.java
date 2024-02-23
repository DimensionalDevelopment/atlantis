package com.mystic.atlantis.entities;

import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
import mod.azure.azurelib.common.internal.common.core.animatable.GeoAnimatable;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.core.animation.AnimatableManager;
import mod.azure.azurelib.common.internal.common.core.animation.AnimationController;
import mod.azure.azurelib.common.internal.common.core.animation.RawAnimation;
import mod.azure.azurelib.common.internal.common.core.object.PlayState;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class StarfishZomEntity extends Monster implements GeoEntity {

    private static final RawAnimation WALK_ANIMATION = RawAnimation.begin().thenLoop("animation.starfish.walk");
    private static final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenLoop("animation.starfish.idle");
    private static final RawAnimation EAT_ANIMATION = RawAnimation.begin().thenLoop("animation.starfish.eat");
    private static final RawAnimation JUMP_ANIMATION = RawAnimation.begin().thenLoop("animation.starfish.jump");


    private final AnimatableInstanceCache factory = AzureLibUtil.createInstanceCache(this);

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

    public static AttributeSupplier.Builder createStarfishAttributes() {
        return createMobAttributes().add(Attributes.MOVEMENT_SPEED, 1.2d);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<GeoAnimatable>(this, "controller", 0, event -> {
            if (isMovingSlowly()) {
                event.getController().setAnimation(WALK_ANIMATION);
            } else {
                event.getController().setAnimation(IDLE_ANIMATION);
            }
            return PlayState.CONTINUE;
        }));
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
        setTarget(level().getNearestPlayer(getX(), getY(), getZ(), 10, true));
    }

    public boolean isMovingSlowly() {
        return this.getDeltaMovement().x() != 0.0f && this.getDeltaMovement().y() != 0.0f && this.getDeltaMovement().z() != 0.0f;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
