package com.mystic.atlantis.entities;

import java.util.Random;

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

import net.minecraft.block.Blocks;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MoveIntoWaterGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class CrabEntity extends AnimalEntity implements IAnimatable, Bucketable {

    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Integer> VARIANT = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final AnimationBuilder WALK_ANIMATION = new AnimationBuilder().addAnimation("animation.crab.walk", true);
    private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.crab.idle", true);

    private final AnimationFactory factory = new AnimationFactory(this);

    public CrabEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canSpawn(WorldView world) {
        return world.doesNotIntersectEntities(this);
    }

    public boolean isFromBucket() {
        return this.dataTracker.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean fromBucket) {
        this.dataTracker.set(FROM_BUCKET, fromBucket);
    }

    @Override
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }

    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
    }

    @Override
    public ItemStack getBucketItem() {
        return ItemInit.CRAB_BUCKET.getDefaultStack();
    }

    @Override
    public SoundEvent getBucketedSound() {
        return SoundEvents.ITEM_BUCKET_FILL_FISH;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    public static DefaultAttributeContainer.Builder createCrabAttributes() {
        return createMobAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1d).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6d);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    public int getVariant(){
        return this.dataTracker.get(VARIANT);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
        this.dataTracker.startTracking(FROM_BUCKET, false);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.dataTracker.set(VARIANT, this.random.nextInt(100) > 50 ? 1 : 2);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.setFromBucket(nbt.getBoolean("FromBucket"));
        this.dataTracker.set(VARIANT, nbt.getInt("Variant"));
        super.readNbt(nbt);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean("FromBucket", this.isFromBucket());
        nbt.putInt("Variant", dataTracker.get(VARIANT));
        return super.writeNbt(nbt);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }

    @Override
    protected void initGoals() {

        goalSelector.add(8, new LookAtEntityGoal(this, LivingEntity.class, 10));
        goalSelector.add(7, new RevengeGoal(this).setGroupRevenge(CrabEntity.class));
        goalSelector.add(6, new LookAroundGoal(this));
        goalSelector.add(5, new AnimalMateGoal(this, 1.0D));
        goalSelector.add(4, new EscapeDangerGoal(this, 0.8));
        goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.SEAGRASS), false));
        goalSelector.add(2, new WanderAroundFarGoal(this, 0.6));
        goalSelector.add(1, new MoveIntoWaterGoal(this));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.getStackInHand(hand).getItem() == Blocks.SEAGRASS.asItem()) {
            if (player instanceof ServerPlayerEntity) {
                if (this.isBreedingItem(Blocks.SEAGRASS.asItem().getDefaultStack())) {
                    if (!this.world.isClient && this.canEat()) {
                        this.eat(player, hand, Blocks.SEAGRASS.asItem().getDefaultStack());
                        this.lovePlayer(player);
                        this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
                        createChild((ServerWorld) player.getEntityWorld(), this);
                        if (!player.getAbilities().creativeMode) {
                            player.getStackInHand(hand).decrement(1);
                        }
                        return ActionResult.SUCCESS;
                    }
                    return ActionResult.FAIL;
                }
                return ActionResult.FAIL;
            }
            return ActionResult.FAIL;
        } else if (player.getStackInHand(hand).getItem() == Items.WATER_BUCKET) {
            return Bucketable.tryBucket(player, hand, this).orElse(super.interactMob(player, hand));
        }
        return ActionResult.FAIL;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return isTempting(stack);
    }

    private static boolean isTempting(ItemStack stack) {
        return stack.isOf(Blocks.SEAGRASS.asItem());
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        CrabEntity crabEntity = ((CrabEntity) entity);
        if (crabEntity.isInLove() && canBreedWith(crabEntity) && crabEntity.getVariant() == 1) {
            return (CrabEntity) crabEntity.getType().create(world);
        } else if ((crabEntity.isInLove() && canBreedWith(crabEntity) && crabEntity.getVariant() == 2)){
            return (CrabEntity) crabEntity.getType().create(world);
        }
        return entity;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        setTarget(world.getClosestPlayer(getX(), getY(), getZ(), 10, true));
    }

    public boolean isMovingSlowly(){
        return this.getVelocity().getX() != 0.0f && this.getVelocity().getY() != 0.0f && this.getVelocity().getZ() != 0.0f;
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

    public static boolean canSpawn(EntityType<CrabEntity> crabEntityType, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos pos, Random random) {
        return pos.getY() >= AtlantisConfig.get().minCrabSpawnHeight && AtlantisConfig.get().maxCrabSpawnHeight >= pos.getY() && serverWorldAccess.getBlockState(pos).isOf(Blocks.WATER);
    }
}
