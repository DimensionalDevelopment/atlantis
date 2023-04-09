package com.mystic.atlantis.blocks.power.atlanteanstone;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class AtlanteanPowerComparatorBlock extends ComparatorBlock implements SimpleWaterloggedBlock {
    private static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;

    public AtlanteanPowerComparatorBlock(Properties settings) {
        super(settings.instabreak().sound(SoundType.WOOD));
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(MODE, ComparatorMode.COMPARE).setValue(WATERLOGGED, true));
    }

    @Override
    public FluidState getFluidState(BlockState targetState) {
        return targetState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(targetState);
    }

    @Override
    public boolean canSurvive(BlockState targetState, LevelReader reader, BlockPos targetPos) {
        if(reader.getFluidState(targetPos).is(FluidTags.WATER)) {
            return canSupportRigidBlock(reader, targetPos.below());
        } else {
           return false;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, MODE, POWERED);
    }
}
