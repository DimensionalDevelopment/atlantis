package com.mystic.atlantis.blocks.power.atlanteanstone;

import java.util.Map;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mystic.atlantis.init.BlockInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallAtlanteanPowerTorchBlock extends AtlanteanPowerTorchBlock {
	private static final Property<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final Property<Boolean> WATERLOGGED = AtlanteanPowerTorchBlock.WATERLOGGED;
	private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap((Map<Direction, VoxelShape>) ImmutableMap.of(
			Direction.NORTH, Block.box(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D),
			Direction.SOUTH, Block.box(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D),
			Direction.WEST, Block.box(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D),
			Direction.EAST, Block.box(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));

	public WallAtlanteanPowerTorchBlock(Properties settings) {
		super(settings
				.lootFrom(() -> BlockInit.ATLANTEAN_POWER_TORCH.get())
				.noCollission()
				.instabreak()
				.lightLevel(level -> 7)
				.sound(SoundType.WOOD));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, true).setValue(WATERLOGGED, true));
	}

	@Override
	public BlockState updateShape(BlockState targetState, Direction curDir, BlockState neighborState, LevelAccessor accessor, BlockPos targetPos, BlockPos neighborPos) {
		return curDir.getOpposite() == targetState.getValue(FACING) && !this.canSurvive(targetState, accessor, targetPos) ? Blocks.WATER.defaultBlockState() : targetState;
	}

	@Override
	public boolean canSurvive(BlockState targetState, LevelReader reader, BlockPos targetPos) {
		if (reader.getFluidState(targetPos).is(FluidTags.WATER)) {
			return Blocks.WALL_TORCH.canSurvive(targetState, reader, targetPos);
		} else {
			return false;
		}
	}

	@Override
	protected boolean hasNeighborSignal(Level level, BlockPos targetPos, BlockState targetState) {
		Direction facingDir = targetState.getValue(FACING).getOpposite();
		return level.hasSignal(targetPos.relative(facingDir), facingDir);
	}

	@Override
	public int getSignal(BlockState targetState, BlockGetter getter, BlockPos targetPos, Direction curDir) {
		return targetState.getValue(LIT) && targetState.getValue(FACING) != curDir ? 15 : 0;
	}

	@Override
	public BlockState rotate(BlockState targetState, Rotation currentRot) {
		return targetState.setValue(FACING, currentRot.rotate(targetState.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return getBoundingShape(state);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockState blockState = this.defaultBlockState();
		LevelReader worldView = ctx.getLevel();
		BlockPos blockPos = ctx.getClickedPos();
		Direction[] directions = ctx.getNearestLookingDirections();
		Direction[] var6 = directions;
		int var7 = directions.length;

		for (int var8 = 0; var8 < var7; ++var8) {
			Direction direction = var6[var8];
			if (direction.getAxis().isHorizontal()) {
				Direction direction2 = direction.getOpposite();
				blockState = blockState.setValue(FACING, direction2).setValue(WATERLOGGED, true);
				if (worldView.getFluidState(blockPos).is(FluidTags.WATER)) {
					if (this.canSurvive( blockState, worldView, blockPos )) {
						return blockState;
					}
				}
				return blockState;
			}
		}
		return null;
	}

	public static VoxelShape getBoundingShape(BlockState state) {
		return BOUNDING_SHAPES.get(state.getValue(FACING));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, LIT, WATERLOGGED);
	}
}
