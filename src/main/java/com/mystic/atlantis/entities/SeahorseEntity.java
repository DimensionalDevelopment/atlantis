package com.mystic.atlantis.entities;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nullable;

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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SeahorseEntity extends WaterAnimal implements IAnimatable, Bucketable {
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(SeahorseEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(SeahorseEntity.class, EntityDataSerializers.INT);
    private static final AnimationBuilder CORAL_IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.seahorse.coral-idle", true);
    private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.seahorse.idle", true);
    private static final AnimationBuilder SWIM_ANIMATION = new AnimationBuilder().addAnimation("animation.seahorse.swim", true);

    private final AnimationFactory factory = new AnimationFactory(this);

    public SeahorseEntity(EntityType<? extends WaterAnimal> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createSeahorseAttributes() {
        return createMobAttributes().add(Attributes.MOVEMENT_SPEED, 2.5d);
    }

    public static boolean canSpawn(EntityType<SeahorseEntity> seahorseEntityType, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return pos.getY() >= 75 && 95 >= pos.getY() && serverWorldAccess.getBlockState(pos).is(Blocks.WATER);
    }

    @Override
    public float getScale() {
        return 0.7f;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }



    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        this.entityData.set(COLOR, betterNiceColor());
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        this.setColor(nbt.getInt("Color"));
        this.setFromBucket(nbt.getBoolean("FromBucket"));
        super.load(nbt);
    }

    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void loadFromBucketTag(CompoundTag nbt) {
        Bucketable.loadDefaultDataFromBucketTag(this, nbt);
    }

    @Override
    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
    }

    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override
    public CompoundTag saveWithoutId(CompoundTag nbt) {
        nbt.putInt("Color", this.getColor());
        nbt.putBoolean("FromBucket", this.fromBucket());
        return super.saveWithoutId(nbt);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(COLOR, betterNiceColor());
    }

    public void createChild(ServerLevel world, SeahorseEntity entity) {
        SeahorseEntity child = (SeahorseEntity) getType().create(world);
        if(child != null) {
            child.setPosRaw(this.getX(), this.getY(), this.getZ());
            world.addFreshEntity(child);
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.6875f;
    }

    @Override
    public boolean isVisuallySwimming() {
        return super.isVisuallySwimming();
    }

    @Override
    protected boolean isAffectedByFluids() {
        return true;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() == Items.SEAGRASS) {
            if (player instanceof ServerPlayer) {
                this.gameEvent(GameEvent.ENTITY_INTERACT, this);
                createChild((ServerLevel) player.getCommandSenderWorld(), this);
                if (!player.getAbilities().instabuild) {
                    player.getItemInHand(hand).shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        } else if (player.getItemInHand(hand).getItem() == Items.WATER_BUCKET) {
            return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
        }
        return InteractionResult.FAIL;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new RandomLookAroundGoal(this));
        goalSelector.addGoal(1, new RandomSwimmingGoal(this, 0.5, 1));
        goalSelector.addGoal(2, new TryFindWaterGoal(this));
        goalSelector.addGoal(3, new TemptGoal(this, 1, Ingredient.of(Items.SEAGRASS), false));
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.TROPICAL_FISH_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.TROPICAL_FISH_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return ItemInit.SEAHORSE_BUCKET.get().getDefaultInstance();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return true;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public void setColor(int color){
        this.entityData.set(COLOR, color);
    }

    public int getColor(){
        return this.entityData.get(COLOR);
    }

    public static int betterNiceColor() {
        return ThreadLocalRandom.current().nextInt(0x01000000);
    }
    private final List<Block> CORALS = List.of(Blocks.BUBBLE_CORAL, Blocks.BUBBLE_CORAL_BLOCK,
            Blocks.BUBBLE_CORAL_FAN, Blocks.BUBBLE_CORAL_WALL_FAN, Blocks.BRAIN_CORAL,
            Blocks.BRAIN_CORAL_BLOCK, Blocks.BRAIN_CORAL_FAN, Blocks.BRAIN_CORAL_WALL_FAN, Blocks.FIRE_CORAL,
            Blocks.FIRE_CORAL_BLOCK, Blocks.FIRE_CORAL_FAN, Blocks.FIRE_CORAL_WALL_FAN,
            Blocks.HORN_CORAL, Blocks.HORN_CORAL_BLOCK, Blocks.HORN_CORAL_FAN, Blocks.HORN_CORAL_WALL_FAN,
            Blocks.TUBE_CORAL, Blocks.TUBE_CORAL_BLOCK, Blocks.TUBE_CORAL_FAN, Blocks.TUBE_CORAL_WALL_FAN);

    private final List<BlockPos> HORIZONAL_DIRECTIONS = List.of(this.blockPosition().north(), this.blockPosition().south(),
        this.blockPosition().west(), this.blockPosition().east());

    public boolean isMovingSlowly(){
        return this.getDeltaMovement().x() != 0.0f && this.getDeltaMovement().y() != 0.0f && this.getDeltaMovement().z() != 0.0f;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (isMovingSlowly()) {
            event.getController().setAnimation(SWIM_ANIMATION);
        } else {
            for(BlockPos pos : HORIZONAL_DIRECTIONS) {
                for (Block block : CORALS) {
                    if (this.getLevel().getBlockState(new BlockPos(this.getX(), this.getY(), this.getZ()).offset(pos)).equals(block.defaultBlockState())) {
                        event.getController().setAnimation(IDLE_ANIMATION); //TODO Coral Animation is borked atm so regular animation is used for now!
                    } else {
                        event.getController().setAnimation(IDLE_ANIMATION);
                    }
                }
            }
        }
        return PlayState.CONTINUE;
    }
}
