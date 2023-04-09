package com.mystic.atlantis.blocks.power.atlanteanstone;

import org.jetbrains.annotations.Nullable;

import com.google.common.base.MoreObjects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.TripWireBlock;
import net.minecraft.world.level.block.TripWireHookBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class AtlanteanTripwireHook extends TripWireHookBlock implements SimpleWaterloggedBlock {
	private static final Property<Boolean> WATERLOGGED = AtlanteanPowerTorchBlock.WATERLOGGED;

	public AtlanteanTripwireHook(Properties settings) {
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
	public void calculateState(Level level, BlockPos targetPos, BlockState hookState, boolean beingRemoved, boolean notifyNeighbours, int searchRange, @Nullable BlockState targetState) {
		Direction direction = hookState.getValue(TripWireHookBlock.FACING);
		boolean isAttached = hookState.getValue(ATTACHED);
		boolean isPowered = hookState.getValue(POWERED);
		boolean stillExists = !beingRemoved;
		boolean poweredAndArmed = false;
		int facingChecks = 0;
		BlockState[] targetStates = new BlockState[42];

		BlockPos relativePos;
		for(int k = 1; k < 42; ++k) {
			relativePos = targetPos.relative(direction, k);
			BlockState relativeState = level.getBlockState(relativePos);
			if (relativeState.is(this)) {
				if (relativeState.getValue(TripWireHookBlock.FACING) == direction.getOpposite()) {
					facingChecks = k;
				}
				break;
			}

			if (!relativeState.is(this) && k != searchRange) {
				targetStates[k] = null;
				stillExists = false;
			} else {
				if (k == searchRange) {
					relativeState = MoreObjects.firstNonNull(targetState, relativeState);
				}

				boolean isRelativeStateArmed = !(Boolean)relativeState.getValue(TripWireBlock.DISARMED);
				boolean isRelativeStatePowered = relativeState.getValue(TripWireBlock.POWERED);
				poweredAndArmed |= isRelativeStateArmed && isRelativeStatePowered;
				targetStates[k] = relativeState;
				if (k == searchRange) {
					level.scheduleTick(targetPos, this, 10);
					stillExists &= isRelativeStateArmed;
				}
			}
		}

		stillExists &= facingChecks > 1;
		poweredAndArmed &= stillExists;
		BlockState curState = this.defaultBlockState().setValue(ATTACHED, stillExists).setValue(POWERED, poweredAndArmed);
		if (facingChecks > 0) {
			relativePos = targetPos.relative(direction, facingChecks);
			Direction oppositeDir = direction.getOpposite();
			level.setBlock(relativePos, curState.setValue(TripWireHookBlock.FACING, oppositeDir), Block.UPDATE_ALL);
			this.notifyNeighbors(level, relativePos, oppositeDir);
			this.playSound(level, relativePos, stillExists, poweredAndArmed, isAttached, isPowered);
		}

		this.playSound(level, targetPos, stillExists, poweredAndArmed, isAttached, isPowered);
		if (!beingRemoved) {
			level.setBlock(targetPos, curState.setValue(TripWireHookBlock.FACING, direction), Block.UPDATE_ALL);
			if (notifyNeighbours) {
				this.notifyNeighbors(level, targetPos, direction);
			}
		}

		if (isAttached != stillExists) {
			for(int l = 1; l < facingChecks; ++l) {
				BlockPos relativeTargetPos = targetPos.relative(direction, l);
				BlockState curTargetState = targetStates[l];
				if (curTargetState != null) {
					level.setBlock(relativeTargetPos, curTargetState.setValue(ATTACHED, stillExists), Block.UPDATE_ALL);
					if (!level.getBlockState(relativeTargetPos).isAir()) {
					}
				}
			}
		}
	}

	private void playSound(Level level, BlockPos targetPos, boolean attached, boolean on, boolean detached, boolean off) {
		if (on && !off) {
			level.playSound(null, targetPos, SoundEvents.TRIPWIRE_CLICK_ON, SoundSource.BLOCKS, 0.4F, 0.6F);
			level.gameEvent(null, GameEvent.BLOCK_ACTIVATE, targetPos);
		} else if (!on && off) {
			level.playSound(null, targetPos, SoundEvents.TRIPWIRE_CLICK_OFF, SoundSource.BLOCKS, 0.4F, 0.5F);
			level.gameEvent(null, GameEvent.BLOCK_DEACTIVATE, targetPos);
		} else if (attached && !detached) {
			level.playSound(null, targetPos, SoundEvents.TRIPWIRE_ATTACH, SoundSource.BLOCKS, 0.4F, 0.7F);
			level.gameEvent(null, GameEvent.BLOCK_ATTACH, targetPos);
		} else if (!attached && detached) {
			level.playSound(null, targetPos, SoundEvents.TRIPWIRE_DETACH, SoundSource.BLOCKS, 0.4F, 1.2F / (level.random.nextFloat() * 0.2F + 0.9F));
			level.gameEvent(null, GameEvent.BLOCK_DETACH, targetPos);
		}
	}

	private void notifyNeighbors(Level level, BlockPos targetPos, Direction curDir) {
		level.updateNeighborsAt(targetPos, this);
		level.updateNeighborsAt(targetPos.relative(curDir.getOpposite()), this);
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
