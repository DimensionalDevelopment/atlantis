package com.mystic.atlantis.entities;

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
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CrabEntity extends Animal implements GeoEntity, Bucketable {
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.INT);
    protected static final Ingredient TEMPT_ITEMS = Ingredient.of(Items.SEAGRASS);
    private static final RawAnimation WALK_ANIMATION = RawAnimation.begin().thenLoop("animation.crab.walk");
    private static final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenLoop("animation.crab.idle");
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private final AnimationController<CrabEntity> mainController = new AnimationController<CrabEntity>(this, "crabController", 2, this::mainPredicate);

    public CrabEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader world) {
        return world.isUnobstructed(this);
    }
    
    public static AttributeSupplier.Builder createCrabAttributes() {
        return createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D);
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
        return ItemInit.CRAB_BUCKET.get().getDefaultInstance();
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(mainController);
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
    	goalSelector.addGoal(0, new TryFindWaterGoal(this));
        goalSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers(CrabEntity.class));
        goalSelector.addGoal(1, new PanicGoal(this, 1.35));
        goalSelector.addGoal(1, new TemptGoal(this, 1.05D, TEMPT_ITEMS, false));
        goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 0.6));
        goalSelector.addGoal(1, new LookAtPlayerGoal(this, LivingEntity.class, 10));
        goalSelector.addGoal(2, new RandomLookAroundGoal(this));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() == Blocks.SEAGRASS.asItem()) {
            if (player instanceof ServerPlayer) {
                if (this.isFood(Blocks.SEAGRASS.asItem().getDefaultInstance())) {
                    if (!this.level().isClientSide && this.canFallInLove()) {
                        this.usePlayerItem(player, hand, Blocks.SEAGRASS.asItem().getDefaultInstance());
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
    	return TEMPT_ITEMS.test(stack);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        CrabEntity crab = ((CrabEntity) entity);
        if (crab.isInLove() && canMate(crab)) return (CrabEntity) crab.getType().create(world);
        return crab;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        setTarget(level().getNearestPlayer(getX(), getY(), getZ(), 10, true));
    }

    public boolean isMovingSlowly(){
        return this.getDeltaMovement().x() != 0.0f && this.getDeltaMovement().y() != 0.0f && this.getDeltaMovement().z() != 0.0f;
    }

    private <P extends GeoAnimatable> PlayState mainPredicate(AnimationState<P> event) {
        if(isMovingSlowly()) {
            event.getController().setAnimation(WALK_ANIMATION);
            return PlayState.CONTINUE;
        } else if (!isMovingSlowly()) {
            event.getController().setAnimation(IDLE_ANIMATION);
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public static boolean canSpawn(EntityType<CrabEntity> crabEntityType, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return pos.getY() >= AtlantisConfig.INSTANCE.minCrabSpawnHeight.get() && AtlantisConfig.INSTANCE.maxCrabSpawnHeight.get() >= pos.getY() && serverWorldAccess.getBlockState(pos).is(Blocks.WATER);
    }
}
