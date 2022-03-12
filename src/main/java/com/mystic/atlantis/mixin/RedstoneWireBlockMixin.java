package com.mystic.atlantis.mixin;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedStoneWireBlock.class)
public abstract class RedstoneWireBlockMixin{

    @Redirect(method = "shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0))
    private static boolean is(BlockState state, Block block){
        return state.is(block) || state.is(BlockInit.ATLANTEAN_POWER_DUST_WIRE);
    }

    /**
     * @author WaterPicker
     * @reason valid reason here :P
     */
    @Overwrite
    private int getWireSignal(BlockState state) {
        if (state.is(Blocks.REDSTONE_WIRE)) {
            return state.getValue(RedStoneWireBlock.POWER);
        } else if (state.is(BlockInit.ATLANTEAN_POWER_DUST_WIRE)) {
            return state.getValue(RedStoneWireBlock.POWER);
        }
        return 0;
    }


    @Inject(method = "calculateTargetStrength", at = @At(value = "HEAD"), cancellable = true)
    public void setPowerToWires1(Level world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
       cir.cancel();
        ((RedstoneAccessor) Blocks.REDSTONE_WIRE).setShouldSignal(false);
        int receivedPower = world.getBestNeighborSignal(pos);
        ((RedstoneAccessor) Blocks.REDSTONE_WIRE).setShouldSignal(true);
        int calculatedPower = 0;
        if (receivedPower < 15 && receivedPower > 0) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos blockPos = pos.relative(direction);
                BlockState blockState = world.getBlockState(blockPos);
                calculatedPower = Math.max(calculatedPower, this.getWireSignal(blockState));
                BlockPos blockPos2 = pos.above();
                if (blockState.isRedstoneConductor(world, blockPos) && !world.getBlockState(blockPos2).isRedstoneConductor(world, blockPos2)) {
                    calculatedPower = Math.max(calculatedPower, this.getWireSignal(world.getBlockState(blockPos.above())));
                } else if (!blockState.isRedstoneConductor(world, blockPos)) {
                    calculatedPower = Math.max(calculatedPower, this.getWireSignal(world.getBlockState(blockPos.below())));
                }
            }

            cir.setReturnValue(Math.max(receivedPower - 1, calculatedPower - 1));
        } else if (receivedPower == 0) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos blockPos = pos.relative(direction);
                BlockState blockState = world.getBlockState(blockPos);
                calculatedPower = Math.max(calculatedPower, this.getWireSignal(blockState));
                BlockPos blockPos2 = pos.above();
                if (blockState.isRedstoneConductor(world, blockPos) && !world.getBlockState(blockPos2).isRedstoneConductor(world, blockPos2)) {
                    calculatedPower = Math.max(calculatedPower, this.getWireSignal(world.getBlockState(blockPos.above())));
                } else if (!blockState.isRedstoneConductor(world, blockPos)) {
                    calculatedPower = Math.max(calculatedPower, this.getWireSignal(world.getBlockState(blockPos.below())));
                }
            }

            cir.setReturnValue(Math.max(receivedPower, calculatedPower - 1));
        } else {
            cir.setReturnValue(Math.max(receivedPower - 1, calculatedPower - 1));
        }
    }


}
