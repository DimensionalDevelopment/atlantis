package com.mystic.atlantis.entities;

import com.mystic.atlantis.init.ItemInit;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class StarfishEntity extends Animal implements GeoEntity { //TODO make bucketable

    //   private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(com.mystic.atlantis.entities.StarfishEntity.class, EntityDataSerializers.BOOLEAN);

    private static final RawAnimation WALK_ANIMATION = RawAnimation.begin().thenLoop("animation.starfish.walk");
    private static final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenLoop("animation.starfish.idle");
    private static final RawAnimation EAT_ANIMATION = RawAnimation.begin().thenLoop("animation.starfish.eat");
    private static final RawAnimation JUMP_ANIMATION = RawAnimation.begin().thenLoop("animation.starfish.jump");


    private final AnimatableInstanceCache factory = AzureLibUtil.createInstanceCache(this);

    public StarfishEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader world) {
        return world.isUnobstructed(this);
    }

    //  public boolean fromBucket() {
    //      return this.entityData.get(FROM_BUCKET);
    //  }

    //  public void setFromBucket(boolean fromBucket) {
    //      this.entityData.set(FROM_BUCKET, fromBucket);
    //  }

    //   @Override
    //  public void saveToBucketTag(ItemStack stack) {
    //       Bucketable.saveDefaultDataToBucketTag(this, stack);
    //   }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }

    // @Override
    // public void loadFromBucketTag(CompoundTag nbt) {
    //     Bucketable.loadDefaultDataFromBucketTag(this, nbt);
    // }

    //  @Override
    //  public ItemStack getBucketItemStack() {
    //      return ItemInit.STARFISH_BUCKET.get().getDefaultInstance();
    //  }

    //  @Override
    //  public SoundEvent getPickupSound() {
    //     return SoundEvents.BUCKET_FILL_FISH;
    // }

    public static AttributeSupplier.Builder createStarfishAttributes() {
        return createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.6d);
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

//    @Override
//    public boolean requiresCustomPersistence() {
//        return super.requiresCustomPersistence() || this.fromBucket();
//    }

//   @Override
//   public boolean removeWhenFarAway(double distanceSquared) {
//       return !this.fromBucket() && !this.hasCustomName();
//   }

    //   @Override
    //   protected void defineSynchedData() {
    //       super.defineSynchedData();
    //       this.entityData.define(FROM_BUCKET, false);
    //   }

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

        if (!entity.isAlive() && !((entity instanceof ShrimpEntity) || (entity instanceof Player)) && !canRide(entity)) {
            this.stopRiding();
        } else {
            this.setDeltaMovement(0, 0, 0);
            this.tick();
            if(entity instanceof Player player) {
                this.setPos(player.getX(), Math.max(player.getY() + player.getEyeHeight(), player.getY()), player.getZ());
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1, 5));
                if(player.getHealth() > 1.0f) {
                    player.hurt(damageSources().mobAttack(this), 1.0f);
                }

                if (!player.isAlive()) {
                    this.removeVehicle();
                }
            } else if (entity instanceof ShrimpEntity shrimp) {
                this.setPos(shrimp.getX(), Math.max(shrimp.getY() + shrimp.getEyeHeight(), shrimp.getY()), shrimp.getZ());
                shrimp.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1, 5));
                shrimp.hurt(damageSources().mobAttack(this), 1.0f);
                if (!shrimp.isAlive()) {
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

        goalSelector.addGoal(7, new LookAtPlayerGoal(this, LivingEntity.class, 10));
        goalSelector.addGoal(6, new HurtByTargetGoal(this).setAlertOthers(StarfishEntity.class));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(ItemInit.SHRIMP.get()), false));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.6));
        goalSelector.addGoal(1, new TryFindWaterGoal(this));
        goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, ShrimpEntity.class, true));
        //TODO fix
        //goalSelector.addGoal(1, new LatchOntoGoal<>(this, ShrimpEntity.class, true));
        //goalSelector.addGoal(0, new LatchOntoGoal<>(this, Player.class, true));
    }

    public static boolean canSpawn(EntityType<StarfishEntity> starfishEntityType, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return pos.getY() >= 75 && 95 >= pos.getY() && serverWorldAccess.getBlockState(pos).is(Blocks.WATER);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() == ItemInit.SHRIMP.get()) {
            if (player instanceof ServerPlayer) {
                if (this.isFood(ItemInit.SHRIMP.get().getDefaultInstance())) {
                    if (!this.level().isClientSide && this.canFallInLove()) {
                        this.usePlayerItem(player, hand, ItemInit.SHRIMP.get().getDefaultInstance());
                        this.setInLove(player);
                        this.gameEvent(GameEvent.ENTITY_INTERACT, this);
                        getBreedOffspring((ServerLevel) player.getCommandSenderWorld(), this);
                        if (!player.getAbilities().instabuild) {
                            player.getItemInHand(hand).shrink(1);
                        }
                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.FAIL;
                }
                return InteractionResult.FAIL;
            }
            return InteractionResult.FAIL;
        }
        //      } else if (player.getItemInHand(hand).getItem() == Items.WATER_BUCKET) {
        //          return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
        //      }
        return InteractionResult.FAIL;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return isTempting(stack);
    }

    private static boolean isTempting(ItemStack stack) {
        return stack.is(ItemInit.SHRIMP.get());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        StarfishEntity starfishEntity = ((StarfishEntity) entity);
        if (starfishEntity.isInLove() && canMate(starfishEntity)) {
            return (StarfishEntity) starfishEntity.getType().create(world);
        }
        return entity;
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
