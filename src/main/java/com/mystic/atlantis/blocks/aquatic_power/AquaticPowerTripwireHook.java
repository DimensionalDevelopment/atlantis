package com.mystic.atlantis.blocks.aquatic_power;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.TripWireHookBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class AquaticPowerTripwireHook extends TripWireHookBlock implements SimpleWaterloggedBlock {
	private static final Property<Boolean> WATERLOGGED = AquaticPowerTorchBlock.WATERLOGGED;

	public AquaticPowerTripwireHook(Properties settings) {
		super(settings.instabreak());
		this.defaultBlockState().setValue(WATERLOGGED, Boolean.TRUE);
	}

	@Override
	public BlockState updateShape(BlockState targetState, Direction curDir, BlockState neighborState, LevelAccessor accessor, BlockPos targetPos, BlockPos neighborPos) {
		return curDir.getOpposite() == targetState.getValue(FACING) && !targetState.canSurvive(accessor, targetPos) ? Blocks.WATER.defaultBlockState() : super.updateShape(targetState, curDir, neighborState, accessor, targetPos, neighborPos);
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, ATTACHED, WATERLOGGED);
	}

	@Override
	public FluidState getFluidState(BlockState targetState) {
		return targetState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(targetState);
	}

	@Override
	public boolean canSurvive(BlockState targetState, LevelReader reader, BlockPos targetPos) {
		if (reader.getFluidState(targetPos).is(FluidTags.WATER)) {
			Direction facingDir = targetState.getValue(FACING);
			BlockPos relativePos = targetPos.relative(facingDir.getOpposite());
			BlockState relativeState = reader.getBlockState(relativePos);
			return facingDir.getAxis().isHorizontal() && relativeState.isFaceSturdy(reader, relativePos, facingDir);
		} else {
			return false;
		}
	}
}
