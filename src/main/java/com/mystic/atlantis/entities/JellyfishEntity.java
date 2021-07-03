package com.mystic.atlantis.entities;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
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
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class JellyfishEntity extends WaterCreatureEntity implements IAnimatable, Bucketable {

    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(JellyfishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> COLOR = DataTracker.registerData(JellyfishEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final AnimationBuilder HOVER_ANIMATION = new AnimationBuilder().addAnimation("animation.jellyfish.hover", true);
    private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.jellyfish.idle", true);

    private final AnimationFactory factory = new AnimationFactory(this);

    public JellyfishEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createJellyfishAttributes() {
        return createMobAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2d).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5d);
    }

    @Override
    public boolean isInSwimmingPose() {
        return true;
    }

    @Override
    protected boolean shouldSwimInFluids() {
        return true;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.getStackInHand(hand).getItem() == ItemInit.CRAB_LEGS) {
            if (player instanceof ServerPlayerEntity) {
                this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
                createChild((ServerWorld) player.getEntityWorld(), this);
                if (!player.getAbilities().creativeMode) {
                    player.getStackInHand(hand).decrement(1);
                }
                return ActionResult.SUCCESS;
            }
            return ActionResult.FAIL;
        } else if (player.getStackInHand(hand).getItem() == Items.WATER_BUCKET) {
            return Bucketable.tryBucket(player, hand, this).orElse(super.interactMob(player, hand));
        }
        return ActionResult.FAIL;
    }

    @Override
    protected void initGoals() {
        goalSelector.add(6, new TemptGoal(this, 1, Ingredient.ofItems(ItemInit.CRAB_LEGS), false));
        goalSelector.add(5, new MoveIntoWaterGoal(this));
        goalSelector.add(4, new LookAroundGoal(this));
        goalSelector.add(3, new SwimGoal(this));
        goalSelector.add(2, new SwimAroundGoal(this, 0.5, 1));
        goalSelector.add(1, new AttackGoal(this));
    }

    public boolean isFromBucket() {
        return this.dataTracker.get(FROM_BUCKET);
    }

    public void setColor(int color){
        this.dataTracker.set(COLOR, color);
    }

    public int getColor(){
        return this.dataTracker.get(COLOR);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.dataTracker.set(COLOR, betterNiceColor());
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.setColor(nbt.getInt("Color"));
        this.setFromBucket(nbt.getBoolean("FromBucket"));
        super.readNbt(nbt);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("Color", this.getColor());
        nbt.putBoolean("FromBucket", this.isFromBucket());
        return super.writeNbt(nbt);
    }

    @Override
    public SoundEvent getBucketedSound() {
        return SoundEvents.ITEM_BUCKET_FILL_FISH;
    }

    @Override
    public ItemStack getBucketItem() {
        return ItemInit.JELLYFISH_BUCKET.getDefaultStack();
    }

    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
    }

    @Override
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
    }

    public void setFromBucket(boolean fromBucket) {
        this.dataTracker.set(FROM_BUCKET, fromBucket);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROM_BUCKET, false);
        this.dataTracker.startTracking(COLOR, betterNiceColor());
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        setTarget(world.getClosestPlayer(getX(), getY(), getZ(), 10, true));
    }

    public void createChild(ServerWorld world, JellyfishEntity entity) {
        JellyfishEntity child = (JellyfishEntity) getType().create(world);
        if(child != null) {
            child.setPos(this.getX(), this.getY(), this.getZ());
            world.spawnEntity(child);
        }
    }

    public static int betterNiceColor() {
        return ThreadLocalRandom.current().nextInt(0x01000000);
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public boolean isMovingSlowly(){
        return this.getVelocity().getX() != 0.0f && this.getVelocity().getY() != 0.0f && this.getVelocity().getZ() != 0.0f;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if(isMovingSlowly()) {
            event.getController().setAnimation(HOVER_ANIMATION);
        } else {
            event.getController().setAnimation(IDLE_ANIMATION);
        }
        return PlayState.CONTINUE;
    }
}
