package com.mystic.atlantis.entities.blockbenchentities;

import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Objects;

public class StarfishEntity extends Animal implements IAnimatable { //TODO make bucketable

 //   private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(com.mystic.atlantis.entities.blockbenchentities.StarfishEntity.class, EntityDataSerializers.BOOLEAN);

    private static final AnimationBuilder WALK_ANIMATION = new AnimationBuilder().addAnimation("animation.starfish.walk", true);
    private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.starfish.idle", true);
    private static final AnimationBuilder EAT_ANIMATION = new AnimationBuilder().addAnimation("animation.starfish.eat", true);
    private static final AnimationBuilder JUMP_ANIMATION = new AnimationBuilder().addAnimation("animation.starfish.jump", true);


    private final AnimationFactory factory = new AnimationFactory(this);

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

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public static AttributeSupplier.Builder createStarfishAttributes() {
        return createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.6d);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
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

 //   @Override
 //   public void load(CompoundTag nbt) {
 //       this.setFromBucket(nbt.getBoolean("FromBucket"));
 //       super.load(nbt);
 //   }

 //   @Override
 //   public CompoundTag saveWithoutId(CompoundTag nbt) {
 //       nbt.putBoolean("FromBucket", this.fromBucket());
 //       return super.saveWithoutId(nbt);
 //   }

    public void rideTick() {
        final Entity entity = this.getVehicle();
        if (this.isPassenger() && !entity.isAlive() && !((entity instanceof ShrimpEntity) || (entity instanceof Player))) {
            this.stopRiding();
        } else {
            this.setDeltaMovement(0, 0, 0);
            this.tick();
            if (this.isPassenger()) {
                final Entity mount = this.getVehicle();
                if (mount instanceof final LivingEntity livingEntity) {
                    this.yBodyRot = livingEntity.yBodyRot;
                    this.setYRot( livingEntity.getYRot());
                    this.yHeadRot = livingEntity.yHeadRot;
                    this.yRotO = livingEntity.yHeadRot;
                    final float radius = 1F;
                    final float angle = (0.0174532925F * livingEntity.yBodyRot);
                    final double extraX = radius * Mth.sin((float) (Math.PI + angle));
                    final double extraZ = radius * Mth.cos(angle);
                    this.setPos(mount.getX() + extraX, Math.max(mount.getY() + mount.getEyeHeight() * 0.25F, mount.getY()), mount.getZ() + extraZ);
                    if(mount instanceof Player) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS));
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON));
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS));
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON));
                        if(livingEntity.getHealth() < 0.5f) {
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.HARM));
                        }
                    }
                    if (!mount.isAlive() || mount instanceof Player && ((Player) mount).isCreative()) {
                        this.removeVehicle();
                    }
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

        goalSelector.addGoal(8, new LookAtPlayerGoal(this, LivingEntity.class, 10));
        goalSelector.addGoal(7, new HurtByTargetGoal(this).setAlertOthers(com.mystic.atlantis.entities.blockbenchentities.StarfishEntity.class));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        goalSelector.addGoal(5, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(ItemInit.SHRIMP.get()), false));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.6));
        goalSelector.addGoal(1, new TryFindWaterGoal(this));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() == ItemInit.SHRIMP.get()) {
            if (player instanceof ServerPlayer) {
                if (this.isFood(ItemInit.SHRIMP.get().getDefaultInstance())) {
                    if (!this.level.isClientSide && this.canFallInLove()) {
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
        com.mystic.atlantis.entities.blockbenchentities.StarfishEntity starfishEntity = ((com.mystic.atlantis.entities.blockbenchentities.StarfishEntity) entity);
        if (starfishEntity.isInLove() && canMate(starfishEntity)) {
            return (com.mystic.atlantis.entities.blockbenchentities.StarfishEntity) starfishEntity.getType().create(world);
        }
        return entity;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        setTarget(level.getNearestPlayer(getX(), getY(), getZ(), 10, true));
    }

    public boolean isMovingSlowly(){
        return this.getDeltaMovement().x() != 0.0f && this.getDeltaMovement().y() != 0.0f && this.getDeltaMovement().z() != 0.0f;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (this.isPassenger()) {
            event.getController().setAnimation(EAT_ANIMATION);
        } else if(isMovingSlowly()) {
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
