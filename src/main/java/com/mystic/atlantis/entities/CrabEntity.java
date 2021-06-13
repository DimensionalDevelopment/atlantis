package com.mystic.atlantis.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class CrabEntity extends AnimalEntity implements IAnimatable {

    protected static final TrackedData<Integer> VARIANT = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final AnimationBuilder WALK_ANIMATION = new AnimationBuilder().addAnimation("animation.crab.walk", true);
    private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.crab.idle", true);

    private final AnimationFactory factory = new AnimationFactory(this);

    public CrabEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    public static DefaultAttributeContainer.Builder createCrobAttributes() {
        return createMobAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1d).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6d);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    public static boolean canSpawn(EntityType<?> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return pos.getY() >= 40 && 50 >= pos.getY() && world.getBlockState(pos).isOf(Blocks.WATER);
    }

    public int getVariant(){
        return this.dataTracker.get(VARIANT);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.dataTracker.set(VARIANT, this.random.nextInt(100) > 50 ? 1 : 2);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.dataTracker.set(VARIANT, nbt.getInt("Variant"));
        super.readNbt(nbt);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("Variant", dataTracker.get(VARIANT));
        return super.writeNbt(nbt);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(5, new LookAtEntityGoal(this, LivingEntity.class, 10));
        goalSelector.add(4, new RevengeGoal(this).setGroupRevenge(CrabEntity.class));
        goalSelector.add(3, new LookAroundGoal(this));
        goalSelector.add(2, new WanderAroundFarGoal(this, 0.6));
        goalSelector.add(1, new MoveIntoWaterGoal(this));
    }

    protected void createChild(CrabEntity mate){
        Entity child = getType().create(world);
        if(child != null) {
            child.setPos(this.getX(), this.getY(), this.getZ());
            world.spawnEntity(child);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if(player.getStackInHand(hand).getItem() == Blocks.SEAGRASS.asItem()){
            createChild(this);
            if(!player.getAbilities().creativeMode) {
                player.getStackInHand(hand).decrement(1);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        PassiveEntity child = (PassiveEntity) getType().create(world);
        if(child != null) {
            child.setPos(this.getX(), this.getY(), this.getZ());
            world.spawnEntity(child);
        }
        return child;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        setTarget(world.getClosestPlayer(this, 10));
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
}
