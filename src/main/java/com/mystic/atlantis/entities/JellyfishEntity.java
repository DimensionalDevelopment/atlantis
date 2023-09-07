package com.mystic.atlantis.entities;

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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
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

import java.util.concurrent.ThreadLocalRandom;

public class JellyfishEntity extends WaterAnimal implements GeoEntity, Bucketable {

    protected static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(JellyfishEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(JellyfishEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(JellyfishEntity.class, EntityDataSerializers.INT);
    private static final RawAnimation HOVER_ANIMATION = RawAnimation.begin().thenLoop("animation.jellyfish.hover");
    private static final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenLoop("animation.jellyfish.idle");
    private int randomTimer;
    private float tx;
    private float ty;
    private float tz;

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public JellyfishEntity(EntityType<? extends WaterAnimal> entityType, Level world) {
        super(entityType, world);
        this.randomTimer = this.getRandom().nextInt(61);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createJellyfishAttributes() {
        return createMobAttributes().add(Attributes.ATTACK_DAMAGE, 2d).add(Attributes.MOVEMENT_SPEED, 0.5d);
    }

    public static boolean canSpawn(EntityType<JellyfishEntity> jellyfishEntityType, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return pos.getY() >= 75 && 105 >= pos.getY() && serverWorldAccess.getBlockState(pos).is(Blocks.WATER);
    }

    @Override
    public boolean isVisuallySwimming() {
        return true;
    }

    @Override
    protected boolean isAffectedByFluids() {
        return true;
    }

    public int getVariant(){
        return this.entityData.get(VARIANT);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() == ItemInit.CRAB_LEGS.get()) {
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
        } else if (player.getItemInHand(hand).getItem() == Items.HONEY_BOTTLE) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (!player.getAbilities().instabuild) {
                player.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
                ItemStack itemStack2 = ItemUtils.createFilledResult(itemStack, player, ItemInit.JELLY_BOTTLE.get().getDefaultInstance());
                player.setItemInHand(hand, itemStack2);
            } else {
                player.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
                ItemStack itemStack2 = ItemUtils.createFilledResult(itemStack, player, ItemInit.JELLY_BOTTLE.get().getDefaultInstance());
                player.setItemInHand(hand, itemStack2);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new JellyFishRandomMovementGoal(this));
        goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        goalSelector.addGoal(2, new TryFindWaterGoal(this));
        goalSelector.addGoal(3, new TemptGoal(this, 1, Ingredient.of(ItemInit.CRAB_LEGS.get()), false));
    }

    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setColor(int color){
        this.entityData.set(COLOR, color);
    }

    public int getColor(){
        return this.entityData.get(COLOR);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        this.entityData.set(VARIANT, this.random.nextInt(100) > 50 ? 1 : 2);
        this.entityData.set(COLOR, betterNiceColor());
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        this.setFromBucket(nbt.getBoolean("FromBucket"));
        this.entityData.set(VARIANT, nbt.getInt("Variant"));
        this.setColor(nbt.getInt("Color"));
        super.load(nbt);
    }

    @Override
    public CompoundTag saveWithoutId(CompoundTag nbt) {
        nbt.putBoolean("FromBucket", this.fromBucket());
        nbt.putInt("Variant", entityData.get(VARIANT));
        nbt.putInt("Color", this.getColor());
        return super.saveWithoutId(nbt);
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return ItemInit.JELLYFISH_BUCKET.get().getDefaultInstance();
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
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(COLOR, betterNiceColor());
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            if (--this.randomTimer <= 0) {
                this.randomTimer = this.getRandom().nextInt(21);
                this.setDeltaMovement(this.tx * 1.2, this.ty * 1.6, this.tz * 1.2);
            }
        }
        setTarget(level().getNearestPlayer(getX(), getY(), getZ(), 10, true));
    }

    public void createChild(ServerLevel world, JellyfishEntity entity) {
        JellyfishEntity child = (JellyfishEntity) getType().create(world);
        if(child != null) {
            child.setPosRaw(this.getX(), this.getY(), this.getZ());
            world.addFreshEntity(child);
        }
    }

    public static int betterNiceColor() {
        return ThreadLocalRandom.current().nextInt(0x01000000);
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean canBeLeashed(@NotNull Player player) {
        return true;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public boolean isMovingSlowly(){
        return this.getDeltaMovement().x() != 0.0f && this.getDeltaMovement().y() != 0.0f && this.getDeltaMovement().z() != 0.0f;
    }

    private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
        if(isMovingSlowly()) {
            event.getController().setAnimation(HOVER_ANIMATION);
        }
        else {
            event.getController().setAnimation(IDLE_ANIMATION);
        }
        return PlayState.CONTINUE;
    }

    static class JellyFishRandomMovementGoal extends Goal {
        private final JellyfishEntity jellyfishEntity;

        public JellyFishRandomMovementGoal(JellyfishEntity arg2) {
            this.jellyfishEntity = arg2;
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void tick() {
            int i = this.jellyfishEntity.getNoActionTime();
            if (i > 100) {
                this.jellyfishEntity.setMovementVector(0.0f, 0.0f, 0.0f);
            }
            else if (this.jellyfishEntity.getRandom().nextInt(JellyfishEntity.JellyFishRandomMovementGoal.reducedTickDelay(50)) == 0 || !this.jellyfishEntity.wasTouchingWater || !this.jellyfishEntity.hasMovementVector()) {
                float f = this.jellyfishEntity.getRandom().nextFloat() * ((float)Math.PI * 2);
                float g = Mth.cos(f) * 0.2f;
                float h = -0.1f + this.jellyfishEntity.getRandom().nextFloat() * 0.2f;
                float j = Mth.sin(f) * 0.2f;
                this.jellyfishEntity.setMovementVector(g, h, j);
            }
        }
    }

    public void setMovementVector(float randomMotionVecX, float randomMotionVecY, float randomMotionVecZ) {
        this.tx = randomMotionVecX;
        this.ty = randomMotionVecY;
        this.tz = randomMotionVecZ;
    }

    public boolean hasMovementVector() {
        return this.tx != 0.0f || this.ty != 0.0f || this.tz != 0.0f;
    }
}
