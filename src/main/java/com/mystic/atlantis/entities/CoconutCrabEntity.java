package com.mystic.atlantis.entities;

import mod.azure.azurelib.common.internal.common.core.animatable.GeoAnimatable;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.core.animation.AnimatableManager;
import mod.azure.azurelib.common.internal.common.core.animation.AnimationController;
import mod.azure.azurelib.common.internal.common.core.animation.AnimationState;
import mod.azure.azurelib.common.internal.common.core.animation.RawAnimation;
import mod.azure.azurelib.common.internal.common.core.object.PlayState;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
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

import java.util.UUID;

public class CoconutCrabEntity extends Animal implements NeutralMob, GeoAnimatable {
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private final AnimatableInstanceCache factory = AzureLibUtil.createInstanceCache(this);
    private final AnimationController<CoconutCrabEntity> mainController = new AnimationController<CoconutCrabEntity>(this, "coconutCrabController", 2, new AnimationController.AnimationStateHandler<>() {
        @Override
        public PlayState handle(AnimationState<CoconutCrabEntity> state) {
            if (isMovingSlowly()) {
                mainController.setAnimation(WALK_ANIMATION);
            } else if (!isMovingSlowly()) {
                mainController.setAnimation(IDLE_ANIMATION);
            }
            return PlayState.CONTINUE;
        }
    });
    static final RawAnimation NUTON_ANIMATION = RawAnimation.begin().thenLoop("animation.coconut_crab.nuton");
    static final RawAnimation WALK_ANIMATION = RawAnimation.begin().thenLoop("animation.crab.walk");
    static final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenLoop("animation.crab.idle");

    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;

    public CoconutCrabEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader world) {
        return world.isUnobstructed(this);
    }

    public static boolean canSpawn(EntityType<CoconutCrabEntity> crabEntityType, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return pos.getY() >= 350 && 512 >= pos.getY();
    }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }

    public static AttributeSupplier.Builder createCoconutCrabAttributes() {
        return createMobAttributes().add(Attributes.ATTACK_DAMAGE, 1d).add(Attributes.MOVEMENT_SPEED, 0.6d);
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence();
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return !this.hasCustomName();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
    }

    @Override
    public CompoundTag saveWithoutId(CompoundTag nbt) {
        return super.saveWithoutId(nbt);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return true;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(7, new LookAtPlayerGoal(this, LivingEntity.class, 10));
        goalSelector.addGoal(6, new HurtByTargetGoal(this).setAlertOthers(CoconutCrabEntity.class));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.SEAGRASS), false));
        goalSelector.addGoal(2, new TryFindWaterGoal(this));
        goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
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
                }
            }
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
        CoconutCrabEntity crabEntity = ((CoconutCrabEntity) entity);
        if (crabEntity.isInLove() && canMate(crabEntity)) {
            return (CoconutCrabEntity) crabEntity.getType().create(world);
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
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {
        this.remainingPersistentAngerTime = i;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setPersistentAngerTarget(@javax.annotation.Nullable UUID uUID) {
        this.persistentAngerTarget = uUID;
    }

    @Override
    @javax.annotation.Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(mainController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public double getTick(Object o) {
        return 0;
    }
}