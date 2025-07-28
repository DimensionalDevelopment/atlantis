package com.mystic.atlantis.entities;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PalmBoatEntity extends Boat implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public PalmBoatEntity(EntityType<? extends Boat> arg, Level arg2) {
        super(arg, arg2);
    }

    @Override
    public @NotNull Item getDropItem() {
        return ItemInit.PALM_BOAT.get();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}