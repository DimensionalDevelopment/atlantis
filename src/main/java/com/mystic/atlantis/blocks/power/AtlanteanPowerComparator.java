package com.mystic.atlantis.blocks.power;

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

public class AtlanteanPowerComparator extends ComparatorBlock implements SimpleWaterloggedBlock {
    private static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;


    public AtlanteanPowerComparator(Properties settings) {
        super(settings.instabreak().sound(SoundType.WOOD));
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(MODE, ComparatorMode.COMPARE).setValue(WATERLOGGED, true));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if(world.getFluidState(pos).is(FluidTags.WATER)) {
            return canSupportRigidBlock(world, pos.below());
        } else {
           return false;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, MODE, POWERED);
    }
}
