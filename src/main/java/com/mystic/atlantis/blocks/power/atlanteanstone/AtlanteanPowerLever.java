package com.mystic.atlantis.blocks.power.atlanteanstone;

import com.mojang.math.Vector3f;
import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import java.util.Random;
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

public class AtlanteanPowerLever extends LeverBlock implements SimpleWaterloggedBlock {

    private static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;
    public static final Vector3f COLOR = new Vector3f(Vec3.fromRGB24(0x0000FF));
    public AtlanteanPowerLever(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(FACE, AttachFace.WALL).setValue(WATERLOGGED, true));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockState blockState;
        if (world.isClientSide) {
            blockState = state.cycle(POWERED);
            if (blockState.getValue(POWERED)) {
                makeParticle(blockState, world, pos, 1.0F);
            }

            return InteractionResult.SUCCESS;
        } else {
            blockState = this.pull(state, world, pos);
            float f = blockState.getValue(POWERED) ? 0.6F : 0.5F;
            world.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, f);
            world.gameEvent(player, blockState.getValue(POWERED).booleanValue() ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    private static void makeParticle(BlockState state, LevelAccessor world, BlockPos pos, float alpha) {
        Direction direction = state.getValue(FACING).getOpposite();
        Direction direction2 = getConnectedDirection(state).getOpposite();
        double d = (double)pos.getX() + 0.5D + 0.1D * (double)direction.getStepX() + 0.2D * (double)direction2.getStepX();
        double e = (double)pos.getY() + 0.5D + 0.1D * (double)direction.getStepY() + 0.2D * (double)direction2.getStepY();
        double f = (double)pos.getZ() + 0.5D + 0.1D * (double)direction.getStepZ() + 0.2D * (double)direction2.getStepZ();
        world.addParticle(new DustParticleOptions(COLOR, alpha), d, e, f, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED) && random.nextFloat() < 0.25F) {
            makeParticle(state, world, pos, 0.5F);
        }

    }

    public static boolean canAttach(LevelReader world, BlockPos pos, Direction direction) {
        if (world.getFluidState(pos).is(FluidTags.WATER)) {
            BlockPos blockPos = pos.relative(direction);
            return world.getBlockState(blockPos).isFaceSturdy(world, blockPos, direction.getOpposite());
        } else {
           return false;
        }
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING, POWERED, WATERLOGGED);
    }

}
