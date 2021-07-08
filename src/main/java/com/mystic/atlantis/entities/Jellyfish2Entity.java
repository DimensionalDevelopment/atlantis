package com.mystic.atlantis.entities;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class Jellyfish2Entity extends JellyfishEntity{
    private static final AnimationBuilder HOVER_ANIMATION = new AnimationBuilder().addAnimation("animation.jellyfish2.hover", true);
    private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.jellyfish2.idle", true);

    private final AnimationFactory factory = new AnimationFactory(this);

    public Jellyfish2Entity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public ItemStack getBucketItem() {
        return ItemInit.JELLYFISH_2_BUCKET.getDefaultStack();
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
