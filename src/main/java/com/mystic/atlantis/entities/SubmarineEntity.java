package com.mystic.atlantis.entities;

import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.mixin.BoatEntityAccessor;
import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
import mod.azure.azurelib.common.internal.common.core.animatable.GeoAnimatable;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.core.animation.AnimatableManager;
import mod.azure.azurelib.common.internal.common.core.animation.AnimationController;
import mod.azure.azurelib.common.internal.common.core.animation.RawAnimation;
import mod.azure.azurelib.common.internal.common.core.object.PlayState;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SubmarineEntity extends Boat implements GeoEntity {
    public boolean pressingForward;

    private AnimatableInstanceCache factory = AzureLibUtil.createInstanceCache(this);

    public SubmarineEntity(EntityType<? extends Boat> entityType, Level world) {
        super(entityType, world);
        this.setMaxUpStep(1.0F);
    }

    @Override
    public Item getDropItem() {
        return ItemInit.SUBMARINE.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
    }

    @Override
    public void tick() {
        ((BoatEntityAccessor) this).setStatusField(Status.UNDER_WATER);
        super.tick();
        ((BoatEntityAccessor) this).setOutOfControlTicks(0);
        floatBoat();
        this.move(MoverType.SELF, this.getDeltaMovement());
        if (this.getFirstPassenger() != null && this.pressingForward) {
            Entity passenger = this.getFirstPassenger();
            this.setXRot(passenger.getXRot() * 0.5F);
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < 2;
    }

    protected void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
        super.positionRider(pPassenger, pCallback);

        if (this.hasPassenger(pPassenger)) {
            if (pPassenger instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 2, 1, true, false, false));
            }
        }
    }


    public void setInput(boolean pressingLeft, boolean pressingRight, boolean pressingForward, boolean pressingBack) {
        super.setInput(pressingLeft, pressingRight, pressingForward, pressingBack);
        this.pressingForward = pressingForward;
    }

    private void floatBoat() {
        Vec3 vec3d = this.getDeltaMovement();
        if (((BoatEntityAccessor) this).getStatus() == Boat.Status.UNDER_WATER && this.getFirstPassenger() != null && this.pressingForward) {
            this.setDeltaMovement(vec3d.x * 1.5, vec3d.y - (this.getXRot()) * 0.001, vec3d.z * 1.5);
        }
        Vec3 velocity = this.getDeltaMovement();
        if (wasTouchingWater) {
            this.setDeltaMovement(velocity.x, velocity.y, velocity.z);
        } else {
            this.setDeltaMovement(velocity.x * 0.4, -0.75, velocity.z * 0.4);
        }
    }

    public boolean isUnderWater() {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "main", 0, event -> {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}

