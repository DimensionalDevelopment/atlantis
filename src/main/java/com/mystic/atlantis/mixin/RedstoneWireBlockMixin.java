package com.mystic.atlantis.mixin;

import com.mystic.atlantis.init.BlockInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(value = RedStoneWireBlock.class, priority = 99999)
public abstract class RedstoneWireBlockMixin {

    @Redirect(method = "shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0))
    private static boolean is(BlockState state, Block block) {
        return state.is(block) || state.is(BlockInit.AQUATIC_POWER_DUST_WIRE.get());
    }

    @Unique
    private int getWireSignal(BlockState state) {
        if (state.is(Blocks.REDSTONE_WIRE)) {
            return state.getValue(RedStoneWireBlock.POWER);
        } else if (state.is(BlockInit.AQUATIC_POWER_DUST_WIRE.get())) {
            return state.getValue(RedStoneWireBlock.POWER);
        }
        return 0;
    }

    @Inject(method = "calculateTargetStrength", at = @At(value = "HEAD"), cancellable = true, require = 1)
    public void setPowerToWires1(Level level, BlockPos targetPos, CallbackInfoReturnable<Integer> cir) {
        cir.cancel();
        ((RedstoneAccessor) ((RedStoneWireBlock) (Object) this)).setShouldSignal(false);
        int receivedPower = level.getBestNeighborSignal(targetPos);
        ((RedstoneAccessor) ((RedStoneWireBlock) (Object) this)).setShouldSignal(true);
        int calculatedPower = 0;
        if(receivedPower >= 15) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (level.getBlockState(targetPos.relative(direction)).getBlockHolder().get() == BlockInit.AQUATIC_POWER_DUST_WIRE.get()
                        || level.getBlockState(targetPos.relative(direction).below()).getBlockHolder().get() == BlockInit.AQUATIC_POWER_DUST_WIRE.get()
                || level.getBlockState(targetPos.relative(direction).above()).getBlockHolder().get() == BlockInit.AQUATIC_POWER_DUST_WIRE.get()) {
                    cir.setReturnValue(Math.max(receivedPower - 1, calculatedPower - 1));
                }
            }
            cir.setReturnValue(receivedPower);
        } else if (receivedPower >= 0) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos relativePos = targetPos.relative(direction);
                BlockState relativeState = level.getBlockState(relativePos);
                calculatedPower = Math.max(calculatedPower, this.getWireSignal(relativeState));
                BlockPos aboveTargetPos = targetPos.above();
                if (relativeState.isRedstoneConductor(level, relativePos) && !level.getBlockState(aboveTargetPos).isRedstoneConductor(level, aboveTargetPos)) {
                    calculatedPower = Math.max(calculatedPower, this.getWireSignal(level.getBlockState(relativePos.above())));
                } else if (!relativeState.isRedstoneConductor(level, relativePos)) {
                    calculatedPower = Math.max(calculatedPower, this.getWireSignal(level.getBlockState(relativePos.below())));
                }
            }
            if (receivedPower == 0) {
                cir.setReturnValue(Math.max(receivedPower, calculatedPower -1));
            } else {
                cir.setReturnValue(Math.max(receivedPower - 1, calculatedPower - 1));
            }
        } else {
            cir.setReturnValue(0);
        }
    }
}
