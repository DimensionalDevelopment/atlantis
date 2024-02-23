package com.mystic.atlantis.entities;

import com.mystic.atlantis.init.ItemInit;
import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.core.animation.AnimatableManager;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class AtlanteanBoatEntity extends Boat implements GeoEntity {

    private AnimatableInstanceCache factory = AzureLibUtil.createInstanceCache(this);

    public AtlanteanBoatEntity(EntityType<? extends Boat> arg, Level arg2) {
        super(arg, arg2);
    }

    @Override
    public @NotNull Item getDropItem() {
        return ItemInit.ATLANTEAN_BOAT.get();
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
