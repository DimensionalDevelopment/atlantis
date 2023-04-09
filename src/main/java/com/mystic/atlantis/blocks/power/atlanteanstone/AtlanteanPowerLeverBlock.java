package com.mystic.atlantis.blocks.power.atlanteanstone;

import com.mojang.math.Vector3f;
import com.mystic.atlantis.blocks.plants.UnderwaterFlower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class AtlanteanPowerLeverBlock extends LeverBlock implements SimpleWaterloggedBlock {
	private static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;
	public static final Vector3f COLOR = new Vector3f(Vec3.fromRGB24(0x0000FF));

	public AtlanteanPowerLeverBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(FACE, AttachFace.WALL).setValue(WATERLOGGED, true));
	}

	@Override
	public InteractionResult use(BlockState targetState, Level level, BlockPos targetPos, Player player, InteractionHand hand, BlockHitResult result) {
		BlockState cycledState;
		if (level.isClientSide) {
			cycledState = targetState.cycle(POWERED);
			if (cycledState.getValue(POWERED)) {
				makeParticle(cycledState, level, targetPos, 1.0F);
			}

			return InteractionResult.SUCCESS;
		} else {
			cycledState = this.pull(targetState, level, targetPos);
			float powerMod = cycledState.getValue(POWERED) ? 0.6F : 0.5F;
			level.playSound(null, targetPos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, powerMod);
			level.gameEvent(player, cycledState.getValue(POWERED).booleanValue() ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, targetPos);
			return InteractionResult.CONSUME;
		}
	}

	@Override
	public FluidState getFluidState(BlockState targetState) {
		return targetState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(targetState);
	}

	private static void makeParticle(BlockState targetState, LevelAccessor accessor, BlockPos targetPos, float alpha) {
		Direction oppositeDir = targetState.getValue(FACING).getOpposite();
		Direction connectedDir = getConnectedDirection(targetState).getOpposite();
		double targetX = (double)targetPos.getX() + 0.5D + 0.1D * (double)oppositeDir.getStepX() + 0.2D * (double)connectedDir.getStepX();
		double targetY = (double)targetPos.getY() + 0.5D + 0.1D * (double)oppositeDir.getStepY() + 0.2D * (double)connectedDir.getStepY();
		double targetZ = (double)targetPos.getZ() + 0.5D + 0.1D * (double)oppositeDir.getStepZ() + 0.2D * (double)connectedDir.getStepZ();
		accessor.addParticle(new DustParticleOptions(COLOR, alpha), targetX, targetY, targetZ, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void animateTick(BlockState targetState, Level level, BlockPos targetPos, RandomSource random) {
		if (targetState.getValue(POWERED) && random.nextFloat() < 0.25F) {
			makeParticle(targetState, level, targetPos, 0.5F);
		}
	}

	public static boolean canAttach(LevelReader reader, BlockPos targetPos, Direction curDir) {
		if (reader.getFluidState(targetPos).is(FluidTags.WATER)) {
			BlockPos relativePos = targetPos.relative(curDir);
			return reader.getBlockState(relativePos).isFaceSturdy(reader, relativePos, curDir.getOpposite());
		} else {
			return false;
		}
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACE, FACING, POWERED, WATERLOGGED);
	}

}
