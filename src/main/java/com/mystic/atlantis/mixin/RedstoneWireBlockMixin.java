package com.mystic.atlantis.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.state.property.EnumProperty;

import com.mystic.atlantis.init.BlockInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

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
     * @author
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

//
//    @Inject(method = "getReceivedRedstonePower", at = @At(value = "FIELD", target = "Lnet/minecraft/block/RedstoneWireBlock;wiresGivePower:Z", ordinal = 0))
//    private void setPowerToWires1(World world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
//        if ((Object) this instanceof AtlanteanPowerDust){
//            ((RedstoneAccessor) Blocks.REDSTONE_WIRE).setWiresGivePower(false);
//        } else {
//            ((RedstoneAccessor) Blocks.REDSTONE_WIRE).setWiresGivePower(false);
//        }
//    }
//
//    @Inject(method = "getReceivedRedstonePower", at = @At(value = "FIELD", target = "Lnet/minecraft/block/RedstoneWireBlock;wiresGivePower:Z", ordinal = 1))
//    private void setPowerToWires2(World world, BlockPos pos, CallbackInfoReturnable<Integer> cir){
//        if ((Object) this instanceof AtlanteanPowerDust) {
//            ((RedstoneAccessor) Blocks.REDSTONE_WIRE).setWiresGivePower(true);
//        } else {
//            ((RedstoneAccessor) Blocks.REDSTONE_WIRE).setWiresGivePower(true);
//        }
//    }
//
//    @Inject(method = "getStrongRedstonePower", at = @At("HEAD"), cancellable = true)
//    private void getPowerToWires1(BlockState state, BlockView world, BlockPos pos, Direction direction, CallbackInfoReturnable<Integer> cir){
//        if (state.isOf(BlockInit.ATLANTEAN_POWER_DUST_WIRE)) {
//            if (((RedstoneAccessor) BlockInit.ATLANTEAN_POWER_DUST_WIRE).getWiresGivePower() && isFullyConnected(state))
//                cir.setReturnValue(0);
//            else cir.setReturnValue(state.getWeakRedstonePower(world, pos, direction));
//        }
//    }
//    @Redirect(method = "getWeakRedstonePower", at = @At(value = "FIELD", target = "Lnet/minecraft/block/RedstoneWireBlock;wiresGivePower:Z", opcode = Opcodes.GETFIELD))
//    public boolean getPowerToWires2(RedstoneWireBlock redstoneWireBlock) {
//        if ((Object) this instanceof AtlanteanPowerDust) {
//            return ((RedstoneAccessor) BlockInit.ATLANTEAN_POWER_DUST_WIRE).getWiresGivePower();
//        } else {
//            return ((RedstoneAccessor) Blocks.REDSTONE_WIRE).getWiresGivePower();
//        }
//    }
//
//    @Inject(method = "emitsRedstonePower", at = @At(value = "HEAD"), cancellable = true)
//    public void emitsRedstonePower(BlockState state, CallbackInfoReturnable<Boolean> cir) {
//        cir.cancel();
//        if ((Object) this instanceof AtlanteanPowerDust) {
//            cir.setReturnValue(((RedstoneAccessor) BlockInit.ATLANTEAN_POWER_DUST_WIRE).getWiresGivePower());
//        } else {
//            cir.setReturnValue(((RedstoneAccessor) Blocks.REDSTONE_WIRE).getWiresGivePower());
//        }
//    }
}
