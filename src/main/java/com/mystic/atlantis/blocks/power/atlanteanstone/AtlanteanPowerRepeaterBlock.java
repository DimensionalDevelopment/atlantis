package com.mystic.atlantis.blocks.power.atlanteanstone;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class AtlanteanPowerRepeaterBlock extends RepeaterBlock implements SimpleWaterloggedBlock {
	private static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;

	public AtlanteanPowerRepeaterBlock(Properties settings) {
		super(settings
				.instabreak()
				.sound(SoundType.WOOD));
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
			return this.canRunOnTop(reader, belowPos, belowState);
		} else {
			return false;
		}
	}

	@Override
	public void animateTick(BlockState targetState, Level level, BlockPos targetPos, RandomSource random) {
		if (targetState.getValue(POWERED)) {
			Direction facingDir = targetState.getValue(FACING);
			double targetX = (double)targetPos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
			double targetY = (double)targetPos.getY() + 0.4D + (random.nextDouble() - 0.5D) * 0.2D;
			double targetZ = (double)targetPos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
			float potentialDelay = -5.0F;
			if (random.nextBoolean()) {
				potentialDelay = (float)(targetState.getValue(DELAY) * 2 - 1);
			}

			potentialDelay /= 16.0F;
			double dX = potentialDelay * (float)facingDir.getStepX();
			double dZ = potentialDelay * (float)facingDir.getStepZ();
			level.addParticle(new DustParticleOptions(AtlanteanPowerLeverBlock.COLOR, 1.0F), targetX + dX, targetY, targetZ + dZ, 0.0D, 0.0D, 0.0D);
		}
	}

	private boolean canRunOnTop(BlockGetter getter, BlockPos targetPos, BlockState targetFloorState) {
		return targetFloorState.isFaceSturdy(getter, targetPos, Direction.UP) || targetFloorState.is(Blocks.HOPPER);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING, DELAY, LOCKED, POWERED);
	}
}
