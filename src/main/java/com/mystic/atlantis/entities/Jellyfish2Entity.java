package com.mystic.atlantis.entities;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class Jellyfish2Entity extends JellyfishEntity {
    private static final AnimationBuilder HOVER_ANIMATION = new AnimationBuilder().addAnimation("animation.jellyfish2.hover", true);
    private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.jellyfish2.idle", true);

    private final AnimationFactory factory = new AnimationFactory(this);

    public Jellyfish2Entity(EntityType<? extends WaterAnimal> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public ItemStack getBucketItemStack() {
        return ItemInit.JELLYFISH_2_BUCKET.get().getDefaultInstance();
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public boolean isMovingSlowly(){
        return this.getDeltaMovement().x() != 0.0f && this.getDeltaMovement().y() != 0.0f && this.getDeltaMovement().z() != 0.0f;
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
