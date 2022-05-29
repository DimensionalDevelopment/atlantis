package com.mystic.atlantis.mixin;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.Block.isShapeFullBlock;

@Mixin(Block.class)
public class BlockMixin{


    @Inject(method = "propagatesSkylightDown", at = @At("HEAD"), cancellable = true)
    public void waterSkylightPropagation(BlockState state, BlockGetter world, BlockPos pos , CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
        if (world instanceof Level level && DimensionAtlantis.isAtlantisDimension(level) && state == Blocks.WATER.defaultBlockState() && !isShapeFullBlock(state.getShape(world, pos))) {
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(!isShapeFullBlock(state.getShape(world, pos)) && state.getFluidState().isEmpty());
        }
    }
}
