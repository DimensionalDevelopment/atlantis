package com.mystic.atlantis.blocks.blockentities.plants;

import mod.azure.azurelib.common.api.common.animatable.GeoBlockEntity;
import mod.azure.azurelib.common.internal.common.core.animatable.GeoAnimatable;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.core.animation.AnimatableManager;
import mod.azure.azurelib.common.internal.common.core.animation.AnimationController;
import mod.azure.azurelib.common.internal.common.core.animation.AnimationState;
import mod.azure.azurelib.common.internal.common.core.object.PlayState;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class GeneralPlantBlockEntity<T extends GeneralPlantBlockEntity<?>> extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache factory = AzureLibUtil.createInstanceCache(this);
    private final AnimationController<T> mainController;

    public GeneralPlantBlockEntity(Supplier<BlockEntityType<T>> registryObject, String name, BlockPos targetPos, BlockState targetState) {
        super(registryObject.get(), targetPos, targetState);
        mainController = new AnimationController<T>((T) this, name, 0, this::predicate);
    }

    private <E extends BlockEntity & GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        AnimationController<?> controller = event.getController();
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(mainController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
