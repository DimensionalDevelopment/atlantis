package com.mystic.atlantis.mixin;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RedstoneWireBlock.class)
public abstract class RedstoneWireBlockMixin {



    @Redirect(method = "connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
    private static boolean isOf(BlockState state, Block block){
        return state.isOf(block) || state.isOf(BlockInit.ATLANTEAN_POWER_DUST_WIRE);
    }
}
