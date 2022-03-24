package com.mystic.atlantis.init;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AGE_7;

public class AtlanteanFireMelonHead extends GrowingPlantHeadBlock implements LiquidBlockContainer {
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);
    private static final double GROW_PER_TICK_PROBABILITY = 0.14;
    public static final IntegerProperty AGE = AGE_7;

    protected AtlanteanFireMelonHead(Properties arg) {
        super(arg.noCollission().noOcclusion(), Direction.UP, SHAPE, true, 0.14);
    }

    @Override
    public BlockState getMaxAgeState(BlockState arg) {
            return arg.setValue(AGE, 7);
    }

    @Override
    public boolean isMaxAge(BlockState arg) {
        return arg.getValue(AGE) == 7;
    }

    @Override
    protected boolean canGrowInto(BlockState arg) {
        return arg.is(Blocks.WATER);
    }

    @Override
    protected Block getBodyBlock() {
        return BlockInit.ATLANTEAN_FIRE_MELON_STEM.get();
    }

    @Override
    public boolean canAttachTo(BlockState arg) {
        return !arg.is(Blocks.MAGMA_BLOCK);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter arg, BlockPos arg2, BlockState arg3, Fluid arg4) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor arg, BlockPos arg2, BlockState arg3, FluidState arg4) {
        return false;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(Random random) {
        return 1;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext arg) {
        FluidState fluidState = arg.getLevel().getFluidState(arg.getClickedPos());
        return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8 ? super.getStateForPlacement(arg) : null;
    }

    @Override
    public FluidState getFluidState(BlockState arg) {
        return Fluids.WATER.getSource(false);
    }
}
