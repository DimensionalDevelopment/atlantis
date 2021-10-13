package com.mystic.atlantis.entities;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.entity.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ShrimpEntity extends SchoolingFishEntity implements IAnimatable, Bucketable {
    private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.shrimp.idle", true);

    private final AnimationFactory factory = new AnimationFactory(this);

    public ShrimpEntity(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createShrimpAttributes() {
        return createFishAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 2d);
    }

    @Override
    public float getScaleFactor() {
        return 0.5f;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.6875f;
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
        if (player.getStackInHand(hand).getItem() == Items.SEAGRASS) {
            if (player instanceof ServerPlayerEntity) {
                this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
                createChild((ServerWorld) player.getEntityWorld());
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
        super.initGoals();
        goalSelector.add(6, new TemptGoal(this, 1, Ingredient.ofItems(Items.SEAGRASS), false));
    }

    @Override
    public SoundEvent getBucketedSound() {
        return SoundEvents.ITEM_BUCKET_FILL_FISH;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_FLOP;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_TROPICAL_FISH_HURT;
    }

    @Override
    public ItemStack getBucketItem() {
        return ItemInit.SHRIMP_BUCKET.getDefaultStack();
    }

    public void createChild(ServerWorld world) {
        ShrimpEntity child = (ShrimpEntity) getType().create(world);
        if(child != null) {
            child.setPos(this.getX(), this.getY(), this.getZ());
            world.spawnEntity(child);
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(IDLE_ANIMATION);

        return PlayState.CONTINUE;
    }
}
