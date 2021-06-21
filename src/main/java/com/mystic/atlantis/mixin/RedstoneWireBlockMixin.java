package com.mystic.atlantis.mixin;

import com.mystic.atlantis.blocks.power.AtlanteanPowerDust;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.state.property.EnumProperty;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneWireBlock.class)
public abstract class RedstoneWireBlockMixin{

    private static final EnumProperty<WireConnection> WIRE_CONNECTION_NORTH = RedstoneWireBlock.WIRE_CONNECTION_NORTH;
    private static final EnumProperty<WireConnection> WIRE_CONNECTION_SOUTH = RedstoneWireBlock.WIRE_CONNECTION_SOUTH;
    private static final EnumProperty<WireConnection> WIRE_CONNECTION_EAST = RedstoneWireBlock.WIRE_CONNECTION_EAST;
    private static final EnumProperty<WireConnection> WIRE_CONNECTION_WEST = RedstoneWireBlock.WIRE_CONNECTION_WEST;

    @Shadow
    private static boolean isFullyConnected(BlockState state) {
        return state.get(WIRE_CONNECTION_NORTH).isConnected() && state.get(WIRE_CONNECTION_SOUTH).isConnected() && state.get(WIRE_CONNECTION_EAST).isConnected() && state.get(WIRE_CONNECTION_WEST).isConnected();
    }

    @Redirect(method = "connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
    private static boolean isOf(BlockState state, Block block){
        return state.isOf(block) || state.isOf(BlockInit.ATLANTEAN_POWER_DUST_WIRE);
    }

    /**
     * @author WaterPicker
     * @reason valid reason here :P
     */
    @Overwrite
    private int increasePower(BlockState state) {
        if (state.isOf(Blocks.REDSTONE_WIRE)) {
            return state.get(RedstoneWireBlock.POWER);
        } else if (state.isOf(BlockInit.ATLANTEAN_POWER_DUST_WIRE)) {
            return state.get(RedstoneWireBlock.POWER);
        }
        return 0;
    }


    @Inject(method = "getReceivedRedstonePower", at = @At(value = "HEAD"), cancellable = true)
    private void setPowerToWires1(World world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
       cir.cancel();
        ((RedstoneAccessor) Blocks.REDSTONE_WIRE).setWiresGivePower(false);
        int receivedPower = world.getReceivedRedstonePower(pos);
        ((RedstoneAccessor) Blocks.REDSTONE_WIRE).setWiresGivePower(true);
        int calculatedPower = 0;
        if (receivedPower < 15 && receivedPower > 0) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos);
                calculatedPower = Math.max(calculatedPower, this.increasePower(blockState));
                BlockPos blockPos2 = pos.up();
                if (blockState.isSolidBlock(world, blockPos) && !world.getBlockState(blockPos2).isSolidBlock(world, blockPos2)) {
                    calculatedPower = Math.max(calculatedPower, this.increasePower(world.getBlockState(blockPos.up())));
                } else if (!blockState.isSolidBlock(world, blockPos)) {
                    calculatedPower = Math.max(calculatedPower, this.increasePower(world.getBlockState(blockPos.down())));
                }
            }

            cir.setReturnValue(Math.max(receivedPower - 1, calculatedPower - 1));
        } else if (receivedPower == 0) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos);
                calculatedPower = Math.max(calculatedPower, this.increasePower(blockState));
                BlockPos blockPos2 = pos.up();
                if (blockState.isSolidBlock(world, blockPos) && !world.getBlockState(blockPos2).isSolidBlock(world, blockPos2)) {
                    calculatedPower = Math.max(calculatedPower, this.increasePower(world.getBlockState(blockPos.up())));
                } else if (!blockState.isSolidBlock(world, blockPos)) {
                    calculatedPower = Math.max(calculatedPower, this.increasePower(world.getBlockState(blockPos.down())));
                }
            }

            cir.setReturnValue(Math.max(receivedPower, calculatedPower - 1));
        } else {
            cir.setReturnValue(Math.max(receivedPower - 1, calculatedPower - 1));
        }
    }


}
