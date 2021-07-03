package com.mystic.atlantis.blocks.power;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.ComparatorMode;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

public class AtlanteanPowerComparator extends ComparatorBlock implements Waterloggable {
    private static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;


    public AtlanteanPowerComparator(Settings settings) {
        super(settings.breakInstantly().sounds(BlockSoundGroup.WOOD));
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(MODE, ComparatorMode.COMPARE).with(WATERLOGGED, true));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if(world.getFluidState(pos).isIn(FluidTags.WATER)) {
            return hasTopRim(world, pos.down());
        } else {
           return false;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, MODE, POWERED);
    }
}
