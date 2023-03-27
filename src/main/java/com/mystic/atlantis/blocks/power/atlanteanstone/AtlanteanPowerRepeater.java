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

public class AtlanteanPowerRepeater extends RepeaterBlock implements SimpleWaterloggedBlock {

    private static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;

    public AtlanteanPowerRepeater(Properties settings) {
        super(settings.instabreak().sound(SoundType.WOOD));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (world.getFluidState(pos).is(FluidTags.WATER)) {
            BlockPos blockPos = pos.below();
            BlockState blockState = world.getBlockState(blockPos);
            return this.canRunOnTop(world, blockPos, blockState);
        } else {
            return false;
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED)) {
            Direction direction = state.getValue(FACING);
            double d = (double)pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            double e = (double)pos.getY() + 0.4D + (random.nextDouble() - 0.5D) * 0.2D;
            double f = (double)pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            float g = -5.0F;
            if (random.nextBoolean()) {
                g = (float)(state.getValue(DELAY) * 2 - 1);
            }

            g /= 16.0F;
            double h = g * (float)direction.getStepX();
            double i = g * (float)direction.getStepZ();
            world.addParticle(new DustParticleOptions(AtlanteanPowerLever.COLOR, 1.0F), d + h, e, f + i, 0.0D, 0.0D, 0.0D);
        }
    }

    private boolean canRunOnTop(BlockGetter world, BlockPos pos, BlockState floor) {
        return floor.isFaceSturdy(world, pos, Direction.UP) || floor.is(Blocks.HOPPER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, DELAY, LOCKED, POWERED);
    }
}
