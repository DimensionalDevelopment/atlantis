package com.mystic.atlantis.blocks.blockentities.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Supplier;

public class GeneralPlantBlockEntity<T extends GeneralPlantBlockEntity<?>> extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
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