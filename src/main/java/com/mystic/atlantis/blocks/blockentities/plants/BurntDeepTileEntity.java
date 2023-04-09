package com.mystic.atlantis.blocks.blockentities.plants;

import com.mystic.atlantis.init.TileEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class BurntDeepTileEntity extends BlockEntity implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private final AnimationController<BurntDeepTileEntity> mainController = new AnimationController<BurntDeepTileEntity>(this, "burntdeepcontroller", 0, this::predicate);

    public BurntDeepTileEntity(BlockPos targetPos, BlockState targetState) {
        super(TileEntityInit.BURNT_DEEP_TILE.get(), targetPos, targetState);
    }

    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationController<?> controller = event.getController();
        controller.transitionLengthTicks = 0;
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(mainController);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
