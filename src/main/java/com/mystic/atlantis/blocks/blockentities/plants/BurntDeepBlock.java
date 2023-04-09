package com.mystic.atlantis.blocks.blockentities.plants;

import static com.mystic.atlantis.blocks.plants.UnderwaterFlower.WATERLOGGED;

import org.jetbrains.annotations.Nullable;

import com.mystic.atlantis.init.TileEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;

public class BurntDeepBlock extends BushBlock implements EntityBlock, SimpleWaterloggedBlock {

	public BurntDeepBlock() {
		super(Properties.of(Material.PLANT)
				.noOcclusion()
				.noCollission()
				.sound(SoundType.GRASS)
				.strength(0.2f, 0.3f)
				.randomTicks());
		this.defaultBlockState().setValue(WATERLOGGED, Boolean.TRUE);
	}

	public boolean canPlaceOn(BlockState targetState){
		return targetState.getBlock() == Blocks.GRAVEL || targetState.getBlock() == Blocks.SANDSTONE || targetState.getBlock() == Blocks.GRASS || targetState.getBlock() == Blocks.DIRT || targetState.getBlock() == Blocks.SAND;
	}

	public boolean canPlaceBlockAt(LevelReader reader, BlockPos targetPos) {
		BlockState belowState = reader.getBlockState(targetPos.below());

		if (reader.getBlockState(targetPos.above()).getMaterial() != Material.WATER) {
			return true;
		}

		if (canPlaceOn(belowState)) {
			return false;
		}

		return true;
	}

	@Override
	public FluidState getFluidState(BlockState targetState) {
		return targetState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(targetState);
	}

	@Override
	public RenderShape getRenderShape(BlockState targetState) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	protected boolean mayPlaceOn(BlockState targetState, BlockGetter getter, BlockPos targetPos) {
		return !targetState.getCollisionShape(getter, targetPos).getFaceShape(Direction.UP).isEmpty() || targetState.isFaceSturdy(getter, targetPos, Direction.UP);
	}

	@Override
	public boolean canSurvive(BlockState targetState, LevelReader reader, BlockPos targetPos) {
		BlockPos below = targetPos.below();

		if(isWaterAt(reader, targetPos)){
			return this.mayPlaceOn(reader.getBlockState(below), reader, below);
		}else{
			return false;
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState targetPos = context.getLevel().getBlockState(context.getClickedPos());
		
		if (targetPos.is(this)) {
			return targetPos;
		} else {
			FluidState targetFluidState = context.getLevel().getFluidState(context.getClickedPos());
			boolean isWater = targetFluidState.getType() == Fluids.WATER;
			return super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(isWater));
		}
	}

	public boolean isWaterAt(LevelReader reader, BlockPos targetPos) {
		return !reader.getBlockState(targetPos).is(getAir()) || !this.canPlaceBlockAt(reader, targetPos);
	}

	public HolderSet<Block> getAir(){
		Holder<Block> airHolderSet = Holder.direct(Blocks.AIR);
		return HolderSet.direct(airHolderSet);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos targetPos, BlockState targetState) {
		return TileEntityInit.BURNT_DEEP_TILE.get().create(targetPos, targetState);
	}
}
