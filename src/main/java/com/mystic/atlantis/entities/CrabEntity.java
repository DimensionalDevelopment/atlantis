package com.mystic.atlantis.entities;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
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
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.init.ItemInit;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CrabEntity extends Animal implements IAnimatable, Bucketable {

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.INT);
    private static final AnimationBuilder WALK_ANIMATION = new AnimationBuilder().addAnimation("animation.crab.walk", true);
    private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.crab.idle", true);

    private final AnimationFactory factory = new AnimationFactory(this);

    public CrabEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader world) {
        return world.isUnobstructed(this);
    }

    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override
    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
    }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    public void loadFromBucketTag(CompoundTag nbt) {
        Bucketable.loadDefaultDataFromBucketTag(this, nbt);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return ItemInit.CRAB_BUCKET.getDefaultInstance();
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public static AttributeSupplier.Builder createCrabAttributes() {
        return createMobAttributes().add(Attributes.ATTACK_DAMAGE, 1d).add(Attributes.MOVEMENT_SPEED, 0.6d);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    public int getVariant(){
        return this.entityData.get(VARIANT);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(FROM_BUCKET, false);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        this.entityData.set(VARIANT, this.random.nextInt(100) > 50 ? 1 : 2);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        this.setFromBucket(nbt.getBoolean("FromBucket"));
        this.entityData.set(VARIANT, nbt.getInt("Variant"));
        super.load(nbt);
    }

    @Override
    public CompoundTag saveWithoutId(CompoundTag nbt) {
        nbt.putBoolean("FromBucket", this.fromBucket());
        nbt.putInt("Variant", entityData.get(VARIANT));
        return super.saveWithoutId(nbt);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return true;
    }

    @Override
    protected void registerGoals() {

        goalSelector.addGoal(8, new LookAtPlayerGoal(this, LivingEntity.class, 10));
        goalSelector.addGoal(7, new HurtByTargetGoal(this).setAlertOthers(CrabEntity.class));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        goalSelector.addGoal(5, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(4, new PanicGoal(this, 0.8));
        goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.SEAGRASS), false));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.6));
        goalSelector.addGoal(1, new TryFindWaterGoal(this));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() == Blocks.SEAGRASS.asItem()) {
            if (player instanceof ServerPlayer) {
                if (this.isFood(Blocks.SEAGRASS.asItem().getDefaultInstance())) {
                    if (!this.level.isClientSide && this.canFallInLove()) {
                        this.usePlayerItem(player, hand, Blocks.SEAGRASS.asItem().getDefaultInstance());
                        this.setInLove(player);
                        this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
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
        } else if (player.getItemInHand(hand).getItem() == Items.WATER_BUCKET) {
            return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
        }
        return InteractionResult.FAIL;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return isTempting(stack);
    }

    private static boolean isTempting(ItemStack stack) {
        return stack.is(Blocks.SEAGRASS.asItem());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        CrabEntity crabEntity = ((CrabEntity) entity);
        if (crabEntity.isInLove() && canMate(crabEntity) && crabEntity.getVariant() == 1) {
            return (CrabEntity) crabEntity.getType().create(world);
        } else if ((crabEntity.isInLove() && canMate(crabEntity) && crabEntity.getVariant() == 2)){
            return (CrabEntity) crabEntity.getType().create(world);
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
        if(isMovingSlowly()) {
            event.getController().setAnimation(WALK_ANIMATION);
        } else {
            event.getController().setAnimation(IDLE_ANIMATION);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public static boolean canSpawn(EntityType<CrabEntity> crabEntityType, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos pos, Random random) {
        return pos.getY() >= AtlantisConfig.get().minCrabSpawnHeight && AtlantisConfig.get().maxCrabSpawnHeight >= pos.getY() && serverWorldAccess.getBlockState(pos).is(Blocks.WATER);
    }
}
