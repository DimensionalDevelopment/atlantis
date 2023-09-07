package com.mystic.atlantis.entities;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AtlanteanBoatEntity extends Boat implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public AtlanteanBoatEntity(EntityType<? extends Boat> arg, Level arg2) {
        super(arg, arg2);
    }

    @Override
    public @NotNull Item getDropItem() {
        return ItemInit.ATLANTEAN_BOAT.get();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
