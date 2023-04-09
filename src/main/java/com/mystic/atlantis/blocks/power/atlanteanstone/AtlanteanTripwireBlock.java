package com.mystic.atlantis.blocks.power.atlanteanstone;

import java.util.Map;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.TripWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class AtlanteanTripwireBlock extends TripWireBlock implements SimpleWaterloggedBlock {
	private static final Map<Direction, BooleanProperty> FACING_PROPERTIES = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((entry) -> entry.getKey().getAxis().isHorizontal()).collect(Util.toMap());
	private final AtlanteanTripwireHook hookBlock;
	private static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;

	public AtlanteanTripwireBlock(AtlanteanTripwireHook hookBlock, Properties settings) {
		super(hookBlock, settings.instabreak());
		this.hookBlock = hookBlock;
		this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false).setValue(ATTACHED, false).setValue(DISARMED, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, true));
	}

	@Override
	public BlockState updateShape(BlockState targetState, Direction curDir, BlockState neighborState, LevelAccessor accessor, BlockPos targetPos, BlockPos neighborPos) {
		return curDir.getAxis().isHorizontal() ? targetState.setValue(FACING_PROPERTIES.get(curDir), this.shouldConnectTo(neighborState, curDir)) : super.updateShape(targetState, curDir, neighborState, accessor, targetPos, neighborPos);
	}

	@Override
	public boolean shouldConnectTo(BlockState targetState, Direction facingDir) {
		if (targetState.getFluidState().is(FluidTags.WATER)) {
			if (targetState.is(this.hookBlock)) {
				return targetState.getValue(AtlanteanTripwireHook.FACING) == facingDir.getOpposite();
			} else {
				return targetState.is(this);
			}
		}
		return false;
	}

	@Override
	public FluidState getFluidState(BlockState targetState) {
		return targetState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(targetState);
	}


	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(POWERED, ATTACHED, DISARMED, NORTH, EAST, WEST, SOUTH, WATERLOGGED);
	}
}
