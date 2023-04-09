package com.mystic.atlantis.blocks.power.atlanteanstone;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class AtlanteanPowerTorchBlock extends RedstoneTorchBlock implements SimpleWaterloggedBlock {
	public static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;

	public AtlanteanPowerTorchBlock(Properties settings) {
		super(settings
				.lightLevel(value -> value.getValue(LIT) ? 7 : 0)
				.noCollission()
				.sound(SoundType.WOOD)
				.instabreak());
		this.defaultBlockState().setValue(WATERLOGGED, Boolean.FALSE);
	}

	@Override
	public FluidState getFluidState(BlockState targetState) {
		return targetState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(targetState);
	}

	@Override
	public BlockState updateShape(BlockState targetState, Direction curDir, BlockState neighborState, LevelAccessor world, BlockPos targetPos, BlockPos neighborPos) {
		return curDir == Direction.DOWN && !this.canSurvive(targetState, world, targetPos) ? Blocks.WATER.defaultBlockState() : super.updateShape(targetState, curDir, neighborState, world, targetPos, neighborPos);
	}

	@Override
	public void animateTick(BlockState targetState, Level level, BlockPos targetPos, RandomSource random) {
		if (targetState.getValue(LIT)) {
			double targetX = (double)targetPos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
			double targetY = (double)targetPos.getY() + 0.7D + (random.nextDouble() - 0.5D) * 0.2D;
			double targetZ = (double)targetPos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
			level.addParticle(new DustParticleOptions(AtlanteanPowerLeverBlock.COLOR, 1.0F), targetX, targetY, targetZ, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public boolean canSurvive(BlockState targetState, LevelReader reader, BlockPos targetPos) {
		if (reader.getFluidState(targetPos).is(FluidTags.WATER)) {
			return canSupportCenter(reader, targetPos.below(), Direction.UP);
		} else {
			return false;
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, LIT);
	}
}
