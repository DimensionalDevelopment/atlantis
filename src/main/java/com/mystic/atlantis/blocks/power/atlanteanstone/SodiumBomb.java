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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SodiumBomb extends Block {
    public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;

    public SodiumBomb(BlockBehaviour.Properties arg) {
        super(arg);
        this.registerDefaultState(this.defaultBlockState().setValue(UNSTABLE, false));
    }

    @Override
    public void onCaughtFire(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        SodiumBomb.explode(world, pos, igniter);
    }

    @Override
    public void onPlace(BlockState arg, @NotNull Level arg2, @NotNull BlockPos arg3, BlockState arg4, boolean bl) {
        if (!arg4.is(arg.getBlock()) && arg2.hasNeighborSignal(arg3)) {
            this.onCaughtFire(arg, arg2, arg3, null, null);
            arg2.removeBlock(arg3, false);
        }
    }

    @Override
    public void neighborChanged(@NotNull BlockState arg, Level arg2, @NotNull BlockPos arg3, @NotNull Block arg4, @NotNull BlockPos arg5, boolean bl) {
        if (arg2.hasNeighborSignal(arg3)) {
            this.onCaughtFire(arg, arg2, arg3, null, null);
            arg2.removeBlock(arg3, false);
        }
    }

    @Override
    public void playerWillDestroy(Level arg, @NotNull BlockPos arg2, @NotNull BlockState arg3, @NotNull Player arg4) {
        if (!arg.isClientSide() && !arg4.isCreative() && arg3.getValue(UNSTABLE)) {
            this.onCaughtFire(arg3, arg, arg2, null, null);
        }
        super.playerWillDestroy(arg, arg2, arg3, arg4);
    }

    @Override
    public void wasExploded(Level arg, BlockPos arg2, Explosion arg3) {
        if (!arg.isClientSide) {
            SodiumPrimedBomb primedtnt = new SodiumPrimedBomb(arg, (double)arg2.getX() + 0.5, arg2.getY(), (double)arg2.getZ() + 0.5, arg3.getSourceMob());
            int i = primedtnt.getFuse();
            primedtnt.setFuse((short)(arg.random.nextInt(i / 4) + i / 8));
            arg.addFreshEntity(primedtnt);
        }
    }

    @Deprecated
    public static void explode(Level arg, @NotNull BlockPos arg2) {
        SodiumBomb.explode(arg, arg2, null);
    }

    @Deprecated
    private static void explode(Level arg, BlockPos arg2, @Nullable LivingEntity arg3) {
        if (!arg.isClientSide) {
            SodiumPrimedBomb primedtnt = new SodiumPrimedBomb(arg, (double)arg2.getX() + 0.5, arg2.getY(), (double)arg2.getZ() + 0.5, arg3);
            arg.addFreshEntity(primedtnt);
            arg.playSound(null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0f, 1.0f);
            arg.gameEvent(arg3, GameEvent.PRIME_FUSE, arg2);
        }
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState arg, @NotNull Level arg22, @NotNull BlockPos arg3, Player arg4, @NotNull InteractionHand arg5, @NotNull BlockHitResult arg6) {
        ItemStack itemstack = arg4.getItemInHand(arg5);
        if (!itemstack.is(Items.FLINT_AND_STEEL) && !itemstack.is(Items.FIRE_CHARGE)) {
            return super.use(arg, arg22, arg3, arg4, arg5, arg6);
        }
        this.onCaughtFire(arg, arg22, arg3, arg6.getDirection(), arg4);
        BlockState air = Blocks.AIR.defaultBlockState();
        BlockState state2;
        if (arg22.getBlockState(arg3.below()) == air || arg22.getBlockState(arg3.above()) == air || arg22.getBlockState(arg3.north()) == air ||
                arg22.getBlockState(arg3.west()) == air || arg22.getBlockState(arg3.east()) == air || arg22.getBlockState(arg3.south()) == air) {
            state2 = air;
        } else {
            state2 = Blocks.WATER.defaultBlockState();
        }
        arg22.setBlock(arg3, state2, 11);
        Item item = itemstack.getItem();
        if (!arg4.isCreative()) {
            if (itemstack.is(Items.FLINT_AND_STEEL)) {
                itemstack.hurtAndBreak(1, arg4, arg2 -> arg2.broadcastBreakEvent(arg5));
            } else {
                itemstack.shrink(1);
            }
        }
        arg4.awardStat(Stats.ITEM_USED.get(item));
        return InteractionResult.sidedSuccess(arg22.isClientSide);
    }

    @Override
    public void onProjectileHit(Level arg, @NotNull BlockState arg2, @NotNull BlockHitResult arg3, @NotNull Projectile arg4) {
        if (!arg.isClientSide) {
            BlockPos blockpos = arg3.getBlockPos();
            Entity entity = arg4.getOwner();
            if (arg4.isOnFire() && arg4.mayInteract(arg, blockpos)) {
                this.onCaughtFire(arg2, arg, blockpos, null, entity instanceof LivingEntity ? (LivingEntity)entity : null);
                arg.removeBlock(blockpos, false);
            }
        }
    }

    @Override
    public boolean dropFromExplosion(@NotNull Explosion arg) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> arg) {
        arg.add(UNSTABLE);
    }
}
