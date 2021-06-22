package com.mystic.atlantis.blocks.power;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.ArrayList;
import java.util.List;

public class AtlanteanPowerTorch extends RedstoneTorchBlock implements Waterloggable {

    public static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;

    public AtlanteanPowerTorch(Settings settings) {
        super(settings.luminance(value -> value.get(LIT) ? 7 : 0).noCollision().sounds(BlockSoundGroup.WOOD).breakInstantly());
        this.getDefaultState().with(WATERLOGGED, Boolean.FALSE);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(LIT) && Direction.UP != direction ? 15 : 0;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected boolean shouldUnpower(World world, BlockPos pos, BlockState state) {
        return !state.get(WATERLOGGED) && world.isEmittingRedstonePower(pos.down(), Direction.DOWN);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos) ? Blocks.WATER.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (OnlyWater(world, pos, state)) {
            return sideCoversSmallSquare(world, pos.down(), Direction.UP);
        } else {
            return false;
        }
    }

    public Tag<Block> getAir(){
        Tag<Block> air = new Tag<Block>() {
            @Override
            public boolean contains(Block element) {
                return true;
            }

            @Override
            public List<Block> values() {
                List<Block> air2 = new ArrayList<Block>();
                air2.add(Blocks.AIR);
                return air2;
            }
        };
        return air;
    }

    public boolean OnlyWater(WorldView worldReader, BlockPos pos, BlockState state) {
        return !worldReader.getBlockState(pos).isIn(getAir()) || !this.canBlockStay(worldReader, pos, state);
    }

    public boolean canBlockStay(WorldView worldReader, BlockPos pos, BlockState state) {
        return canPlaceBlockAt(worldReader, pos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, LIT);
    }

    public boolean canPlaceBlockAt(WorldView worldReader, BlockPos pos) {

        if (worldReader.getBlockState(pos).getMaterial() != Material.WATER)
        {
            return true;
        }
        return worldReader.getBlockState(pos.down()) != worldReader.getBlockState(pos.down());
    }
}
