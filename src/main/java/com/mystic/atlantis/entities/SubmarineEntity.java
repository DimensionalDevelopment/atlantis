package com.mystic.atlantis.entities;

import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.mixin.BoatEntityAccessor;
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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SubmarineEntity extends Boat implements IAnimatable {
    public boolean pressingForward;
    public float prevRoll = 0;
    public float rotorAngle;
    private AnimationFactory factory = new AnimationFactory(this);

    public SubmarineEntity(EntityType<? extends Boat> entityType, Level world) {
        super(entityType, world);
        this.maxUpStep = 1.0F;
    }
    @Override
    public Item getDropItem() {
        return ItemInit.SUBMARINE;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
    }
    @Override
    public void tick() {
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
    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        if (this.hasPassenger(passenger)) {
            if (passenger instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,2,1,true,false,false));
            }
        }
    }
    public void setInput(boolean pressingLeft, boolean pressingRight, boolean pressingForward, boolean pressingBack) {
        super.setInput(pressingLeft,pressingRight,pressingForward,pressingBack);
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
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this,"main", 0f, event -> {
            if(event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
                return PlayState.CONTINUE;
            } else {
                return PlayState.STOP;
            }
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}

