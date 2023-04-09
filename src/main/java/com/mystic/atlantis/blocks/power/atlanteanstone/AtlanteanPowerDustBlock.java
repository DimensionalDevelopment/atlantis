package com.mystic.atlantis.blocks.power.atlanteanstone;

import java.util.Set;

import com.google.common.collect.Sets;
import com.mojang.math.Vector3f;
import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.mixin.RedstoneAccessor;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

public class AtlanteanPowerDustBlock extends RedStoneWireBlock implements SimpleWaterloggedBlock {
	public static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;
	private static final Property<Integer> POWER = RedStoneWireBlock.POWER;
	private static Vec3[] COLOR = Util.make(new Vec3[16], (vec3ds) -> {
		for (int i = 0; i <= 15; ++i) {
			float f = (float) i / 15.0F;
			float r = Mth.clamp(f * f * 0.7F - 0.5F, 0.0F, 1.0F);
			float g = Mth.clamp(f * f * 0.6F - 0.7F, 0.0F, 1.0F);
			float b = f * 0.6F + (f > 0.0F ? 0.4F : 0.3F);
			vec3ds[i] = new Vec3(r, g, b);
		}
	});

	public AtlanteanPowerDustBlock(Properties settings) {
		super(settings.noCollission().instabreak());
		this.registerDefaultState(defaultBlockState().setValue(NORTH, RedstoneSide.NONE).setValue(EAST, RedstoneSide.NONE).setValue(SOUTH, RedstoneSide.NONE).setValue(WEST, RedstoneSide.NONE).setValue(POWER, 0).setValue(WATERLOGGED, Boolean.TRUE));
		((RedstoneAccessor) this).setShouldSignal(true);
	}

	@Override
	public void animateTick(BlockState targetState, Level level, BlockPos targetPos, RandomSource random) {
		int power = targetState.getValue(POWER);

		if (power != 0) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				RedstoneSide wireConnection = targetState.getValue(PROPERTY_BY_DIRECTION.get(direction));
				switch (wireConnection) {
				case UP:
					this.spawnParticlesAlongLine(level, random, targetPos, COLOR[power], direction, Direction.UP, -0.5F, 0.5F);
				case SIDE:
					this.spawnParticlesAlongLine(level, random, targetPos, COLOR[power], Direction.DOWN, direction, 0.0F, 0.5F);
					break;
				case NONE:
				default:
					this.spawnParticlesAlongLine(level, random, targetPos, COLOR[power], Direction.DOWN, direction, 0.0F, 0.3F);
				}
			}
		}
	}

	private void spawnParticlesAlongLine(Level level, RandomSource random, BlockPos targetPos, Vec3 spawnVec, Direction curDir, Direction targetDir, float of1, float of2) {
		float offsetMod = of2 - of1;

		if (!(random.nextFloat() >= 0.2F * offsetMod)) {
			float offset = of1 + offsetMod * random.nextFloat();
			double targetX = 0.5D + (double)(0.4375F * (float)curDir.getStepX()) + (double)(offset * (float)targetDir.getStepX());
			double targetY = 0.5D + (double)(0.4375F * (float)curDir.getStepY()) + (double)(offset * (float)targetDir.getStepY());
			double targetZ = 0.5D + (double)(0.4375F * (float)curDir.getStepZ()) + (double)(offset * (float)targetDir.getStepZ());
			level.addParticle(new DustParticleOptions(new Vector3f(spawnVec), 1.0F), (double)targetPos.getX() + targetX, (double)targetPos.getY() + targetY, (double)targetPos.getZ() + targetZ, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public int getSignal(BlockState targetState, BlockGetter getter, BlockPos targetPos, Direction curDir) {
		if (((RedstoneAccessor) this).getShouldSignal() && curDir != Direction.DOWN) {
			int power = targetState.getValue(POWER);
			if (power == 0) {
				return 0;
			} else {
				return curDir != Direction.UP && !((RedstoneSide)this.getConnectionState(getter, targetState, targetPos).getValue((Property)PROPERTY_BY_DIRECTION.get(curDir.getOpposite()))).isConnected() ? 0 : power;
			}
		} else {
			return 0;
		}
	}

	@Override
	public int getDirectSignal(BlockState targetState, BlockGetter getter, BlockPos targetPos, Direction curDir) {
		return !((RedstoneAccessor) this).getShouldSignal() ? 0 : targetState.getSignal(getter, targetPos, curDir);
	}

	@Override
	public void updatePowerStrength(Level level, BlockPos targetPos, BlockState targetState) {
		int receivedPower = this.calculateTargetStrength(level, targetPos);

		if (targetState.getValue(POWER) != receivedPower) {
			if (level.getBlockState(targetPos) == targetState) {
				level.setBlock(targetPos, targetState.setValue(POWER, receivedPower), Block.UPDATE_CLIENTS);
			}

			Set<BlockPos> targetPositions = Sets.newHashSet();
			targetPositions.add(targetPos);
			Direction[] validDirections = Direction.values();

			for (Direction direction : validDirections) {
				targetPositions.add(targetPos.relative(direction));
			}

			for (BlockPos targetPosition : targetPositions) {
				level.updateNeighborsAt(targetPosition, this);
			}
		}
	}

	@Override
	public int calculateTargetStrength(Level level, BlockPos targetPos) {
		((RedstoneAccessor) this).setShouldSignal(false);
		int receivedPower = level.getBestNeighborSignal(targetPos);
		((RedstoneAccessor) this).setShouldSignal(true);
		int calculatedPower = 0;
		if(receivedPower == 15) {
			calculatedPower = 15;
			return calculatedPower;
		}
		else if (receivedPower < 15 && receivedPower > 0) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				BlockPos relativePos = targetPos.relative(direction);
				BlockState relativeState = level.getBlockState(relativePos);
				calculatedPower = Math.max(calculatedPower, this.getWireSignal(relativeState));
				BlockPos aboveTargetPos = targetPos.above();
				if (relativeState.isRedstoneConductor(level, relativePos) && !level.getBlockState(aboveTargetPos).isRedstoneConductor(level, aboveTargetPos)) {
					calculatedPower = Math.max(calculatedPower, this.getWireSignal(level.getBlockState(relativePos.above())));
				} else if (!relativeState.isRedstoneConductor(level, relativePos)) {
					calculatedPower = Math.max(calculatedPower, this.getWireSignal(level.getBlockState(relativePos.below())));
				}
			}

			return Math.max(receivedPower - 1, calculatedPower - 1);
		} else if (receivedPower == 0) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				BlockPos relativePos = targetPos.relative(direction);
				BlockState relativeState = level.getBlockState(relativePos);
				calculatedPower = Math.max(calculatedPower, this.getWireSignal(relativeState));
				BlockPos aboveTargetPos = targetPos.above();
				if (relativeState.isRedstoneConductor(level, relativePos) && !level.getBlockState(aboveTargetPos).isRedstoneConductor(level, aboveTargetPos)) {
					calculatedPower = Math.max(calculatedPower, this.getWireSignal(level.getBlockState(relativePos.above())));
				} else if (!relativeState.isRedstoneConductor(level, relativePos)) {
					calculatedPower = Math.max(calculatedPower, this.getWireSignal(level.getBlockState(relativePos.below())));
				}
			}

			return Math.max(receivedPower, calculatedPower - 1);
		} else {
			return Math.max(receivedPower - 1, calculatedPower - 1);
		}
	}

	@Override
	public boolean isSignalSource(BlockState targetState) {
		return ((RedstoneAccessor) this).getShouldSignal();
	}

	private int getWireSignal(BlockState targetState) {
		if (targetState.is(Blocks.REDSTONE_WIRE)) {
			return targetState.getValue(RedStoneWireBlock.POWER);
		} else if (targetState.is(BlockInit.ATLANTEAN_POWER_DUST_WIRE.get())) {
			return targetState.getValue(RedStoneWireBlock.POWER);
		}
		return 0;
	}

	@Override
	public FluidState getFluidState(BlockState targetState) {
		return targetState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(targetState);
	}

	@Override
	public boolean canSurvive(BlockState targetState, LevelReader reader, BlockPos targetPos) {
		if (reader.getFluidState(targetPos).is(FluidTags.WATER)) {
			BlockPos belowPos = targetPos.below();
			BlockState belowState = reader.getBlockState(belowPos);
			return this.canSurviveOn(reader, belowPos, belowState);
		} else {
			return false;
		}
	}

	private boolean canSurviveOn(BlockGetter getter, BlockPos targetPos, BlockState targetFloorState) {
		return targetFloorState.isFaceSturdy(getter, targetPos, Direction.UP) || targetFloorState.is(Blocks.HOPPER);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, EAST, NORTH, SOUTH, WEST, POWER);
	}
}
