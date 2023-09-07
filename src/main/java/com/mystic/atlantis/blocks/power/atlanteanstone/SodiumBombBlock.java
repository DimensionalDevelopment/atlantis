package com.mystic.atlantis.blocks.power.atlanteanstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SodiumBombBlock extends Block {
	public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;

	public SodiumBombBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(UNSTABLE, false));
	}

	@Override
	public void onCaughtFire(@NotNull BlockState targetState, @NotNull Level level, @NotNull BlockPos targetPos, @Nullable Direction facingDir, @Nullable LivingEntity igniter) {
		SodiumBombBlock.explode(level, targetPos, igniter);
	}

	@Override
	public void onPlace(BlockState targetState, @NotNull Level level, @NotNull BlockPos targetPos, BlockState oldState, boolean moving) {
		if (!oldState.is(targetState.getBlock()) && level.hasNeighborSignal(targetPos)) {
			this.onCaughtFire(targetState, level, targetPos, null, null);
			level.removeBlock(targetPos, false);
		}
	}

	@Override
	public void neighborChanged(@NotNull BlockState targetState, Level level, @NotNull BlockPos targetPos, @NotNull Block targetBlock, @NotNull BlockPos fromPos, boolean moving) {
		if (level.hasNeighborSignal(targetPos)) {
			this.onCaughtFire(targetState, level, targetPos, null, null);
			level.removeBlock(targetPos, false);
		}
	}

	@Override
	public void playerWillDestroy(Level level, @NotNull BlockPos targetPos, @NotNull BlockState targetState, @NotNull Player player) {
		if (!level.isClientSide() && !player.isCreative() && targetState.getValue(UNSTABLE)) {
			this.onCaughtFire(targetState, level, targetPos, null, null);
		}
		super.playerWillDestroy(level, targetPos, targetState, player);
	}

	@Override
	public void wasExploded(Level level, BlockPos targetPos, Explosion explosion) {
		if (!level.isClientSide) {
			SodiumPrimedBombBlock primedtnt = new SodiumPrimedBombBlock(level, (double)targetPos.getX() + 0.5, targetPos.getY(), (double)targetPos.getZ() + 0.5, explosion.getIndirectSourceEntity());
			int fuse = primedtnt.getFuse();
			primedtnt.setFuse((short)(level.random.nextInt(fuse / 4) + fuse / 8));
			level.addFreshEntity(primedtnt);
		}
	}

	@Deprecated
	public static void explode(Level level, @NotNull BlockPos targetPos) {
		SodiumBombBlock.explode(level, targetPos, null);
	}

	@Deprecated
	private static void explode(Level level, BlockPos targetPos, @Nullable LivingEntity igniter) {
		if (!level.isClientSide) {
			SodiumPrimedBombBlock primedtnt = new SodiumPrimedBombBlock(level, (double)targetPos.getX() + 0.5, targetPos.getY(), (double)targetPos.getZ() + 0.5, igniter);
			level.addFreshEntity(primedtnt);
			level.playSound(null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0f, 1.0f);
			level.gameEvent(igniter, GameEvent.PRIME_FUSE, targetPos);
		}
	}

	@NotNull
	@Override
	public InteractionResult use(@NotNull BlockState targetState, @NotNull Level level, @NotNull BlockPos targetPos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
		ItemStack heldStack = player.getItemInHand(hand);
		if (!heldStack.is(Items.FLINT_AND_STEEL) && !heldStack.is(Items.FIRE_CHARGE)) {
			return super.use(targetState, level, targetPos, player, hand, result);
		}
		this.onCaughtFire(targetState, level, targetPos, result.getDirection(), player);
		BlockState airState = Blocks.AIR.defaultBlockState();
		BlockState waterOrAirState;
		if (level.getBlockState(targetPos.below()) == airState || level.getBlockState(targetPos.above()) == airState || level.getBlockState(targetPos.north()) == airState ||
				level.getBlockState(targetPos.west()) == airState || level.getBlockState(targetPos.east()) == airState || level.getBlockState(targetPos.south()) == airState) {
			waterOrAirState = airState;
		} else {
			waterOrAirState = Blocks.WATER.defaultBlockState();
		}
		level.setBlock(targetPos, waterOrAirState, 11);
		Item heldItem = heldStack.getItem();
		if (!player.isCreative()) {
			if (heldStack.is(Items.FLINT_AND_STEEL)) {
				heldStack.hurtAndBreak(1, player, arg2 -> arg2.broadcastBreakEvent(hand));
			} else {
				heldStack.shrink(1);
			}
		}
		player.awardStat(Stats.ITEM_USED.get(heldItem));
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	@Override
	public void onProjectileHit(Level level, @NotNull BlockState targetState, @NotNull BlockHitResult result, @NotNull Projectile projectile) {
		if (!level.isClientSide) {
			BlockPos targetPos = result.getBlockPos();
			Entity projectileOwner = projectile.getOwner();
			if (projectile.isOnFire() && projectile.mayInteract(level, targetPos)) {
				this.onCaughtFire(targetState, level, targetPos, null, projectileOwner instanceof LivingEntity ? (LivingEntity)projectileOwner : null);
				level.removeBlock(targetPos, false);
			}
		}
	}

	@Override
	public boolean dropFromExplosion(@NotNull Explosion explosion) {
		return false;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(UNSTABLE);
	}
}
