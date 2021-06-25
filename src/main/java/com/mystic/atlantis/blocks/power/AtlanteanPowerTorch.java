package com.mystic.atlantis.blocks.power;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AtlanteanPowerTorch extends RedstoneTorchBlock implements Waterloggable {

    public static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;

    public AtlanteanPowerTorch(Settings settings) {
        super(settings.luminance(value -> value.get(LIT) ? 7 : 0).noCollision().sounds(BlockSoundGroup.WOOD).breakInstantly());
        this.getDefaultState().with(WATERLOGGED, Boolean.FALSE);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos) ? Blocks.WATER.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            double d = (double)pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            double e = (double)pos.getY() + 0.7D + (random.nextDouble() - 0.5D) * 0.2D;
            double f = (double)pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            world.addParticle(new DustParticleEffect(AtlanteanPowerLever.COLOR, 1.0F), d, e, f, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
            return sideCoversSmallSquare(world, pos.down(), Direction.UP);
        } else {
            return false;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, LIT);
    }
}
