package com.mystic.atlantis.blocks.power;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.math.Vector3f;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.mixin.RedstoneAccessor;

public class AtlanteanPowerDust extends RedStoneWireBlock implements SimpleWaterloggedBlock {

    public static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;
    private static final EnumProperty<RedstoneSide> WIRE_CONNECTION_NORTH = BlockStateProperties.NORTH_REDSTONE;
    private static final EnumProperty<RedstoneSide> WIRE_CONNECTION_EAST = BlockStateProperties.EAST_REDSTONE;
    private static final EnumProperty<RedstoneSide>WIRE_CONNECTION_SOUTH = BlockStateProperties.SOUTH_REDSTONE;
    private static final EnumProperty<RedstoneSide> WIRE_CONNECTION_WEST = BlockStateProperties.WEST_REDSTONE;
    private static final Map<Direction, EnumProperty<RedstoneSide>> DIRECTION_TO_WIRE_CONNECTION_PROPERTY = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, WIRE_CONNECTION_NORTH, Direction.EAST, WIRE_CONNECTION_EAST, Direction.SOUTH, WIRE_CONNECTION_SOUTH, Direction.WEST, WIRE_CONNECTION_WEST));
    private static final Property<Integer> POWER = RedStoneWireBlock.POWER;
    private static Vec3[] COLOR = Util.make(new Vec3[16], (vec3ds) -> {
        for (int i = 0; i <= 15; ++i) {
            float f = (float) i / 15.0F;
            float r = Mth.clamp(f * f * 0.7F - 0.5F, 0.0F, 1.0F);
            float g = Mth.clamp(f * f * 0.6F - 0.7F, 0.0F, 1.0F);
            float b = f * 0.6F + (f > 0.0F ? 0.4F : 0.3F);
            vec3ds[i] = new Vec3((double) r, (double) g, (double) b);
        }
    });

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        int i = state.getValue(POWER);
        if (i != 0) {

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                RedstoneSide wireConnection = state.getValue(PROPERTY_BY_DIRECTION.get(direction));
                switch (wireConnection) {
                    case UP:
                        this.spawnParticlesAlongLine(world, (Random) random, pos, COLOR[i], direction, Direction.UP, -0.5F, 0.5F);
                    case SIDE:
                        this.spawnParticlesAlongLine(world, (Random) random, pos, COLOR[i], Direction.DOWN, direction, 0.0F, 0.5F);
                        break;
                    case NONE:
                    default:
                        this.spawnParticlesAlongLine(world, (Random) random, pos, COLOR[i], Direction.DOWN, direction, 0.0F, 0.3F);
                }
            }

        }
    }

    private void spawnParticlesAlongLine(Level world, Random random, BlockPos pos, Vec3 color, Direction direction, Direction direction2, float f, float g) {
        float h = g - f;
        if (!(random.nextFloat() >= 0.2F * h)) {
            float i = 0.4375F;
            float j = f + h * random.nextFloat();
            double d = 0.5D + (double)(0.4375F * (float)direction.getStepX()) + (double)(j * (float)direction2.getStepX());
            double e = 0.5D + (double)(0.4375F * (float)direction.getStepY()) + (double)(j * (float)direction2.getStepY());
            double k = 0.5D + (double)(0.4375F * (float)direction.getStepZ()) + (double)(j * (float)direction2.getStepZ());
            world.addParticle(new DustParticleOptions(new Vector3f(color), 1.0F), (double)pos.getX() + d, (double)pos.getY() + e, (double)pos.getZ() + k, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        if (((RedstoneAccessor) this).getShouldSignal() && direction != Direction.DOWN) {
            int i = state.getValue(POWER);
            if (i == 0) {
                return 0;
            } else {
                return direction != Direction.UP && !((RedstoneSide)this.getConnectionState(world, state, pos).getValue((Property)PROPERTY_BY_DIRECTION.get(direction.getOpposite()))).isConnected() ? 0 : i;
            }
        } else {
            return 0;
        }
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return !((RedstoneAccessor) this).getShouldSignal() ? 0 : state.getSignal(world, pos, direction);
    }

    @Override
    public void updatePowerStrength(Level world, BlockPos pos, BlockState state) {
        int receivedPower = this.calculateTargetStrength(world, pos);
        if (state.getValue(POWER) != receivedPower) {
            if (world.getBlockState(pos) == state) {
                world.setBlock(pos, state.setValue(POWER, receivedPower), Block.UPDATE_CLIENTS);
            }

            Set<BlockPos> set = Sets.newHashSet();
            set.add(pos);
            Direction[] var6 = Direction.values();

            for (Direction direction : var6) {
                set.add(pos.relative(direction));
            }

            for (BlockPos blockPos : set) {
                world.updateNeighborsAt(blockPos, this);
            }
        }
    }

    @Override
    public int calculateTargetStrength(Level world, BlockPos pos) {
        ((RedstoneAccessor) this).setShouldSignal(false);
        int receivedPower = world.getBestNeighborSignal(pos);
        ((RedstoneAccessor) this).setShouldSignal(true);
        int calculatedPower = 0;
        if (receivedPower < 15 && receivedPower > 0) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos blockPos = pos.relative(direction);
                BlockState blockState = world.getBlockState(blockPos);
                calculatedPower = Math.max(calculatedPower, this.getWireSignal(blockState));
                BlockPos blockPos2 = pos.above();
                if (blockState.isRedstoneConductor(world, blockPos) && !world.getBlockState(blockPos2).isRedstoneConductor(world, blockPos2)) {
                    calculatedPower = Math.max(calculatedPower, this.getWireSignal(world.getBlockState(blockPos.above())));
                } else if (!blockState.isRedstoneConductor(world, blockPos)) {
                    calculatedPower = Math.max(calculatedPower, this.getWireSignal(world.getBlockState(blockPos.below())));
                }
            }

            return Math.max(receivedPower - 1, calculatedPower - 1);
        } else if (receivedPower == 0) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos blockPos = pos.relative(direction);
                BlockState blockState = world.getBlockState(blockPos);
                calculatedPower = Math.max(calculatedPower, this.getWireSignal(blockState));
                BlockPos blockPos2 = pos.above();
                if (blockState.isRedstoneConductor(world, blockPos) && !world.getBlockState(blockPos2).isRedstoneConductor(world, blockPos2)) {
                    calculatedPower = Math.max(calculatedPower, this.getWireSignal(world.getBlockState(blockPos.above())));
                } else if (!blockState.isRedstoneConductor(world, blockPos)) {
                    calculatedPower = Math.max(calculatedPower, this.getWireSignal(world.getBlockState(blockPos.below())));
                }
            }

            return Math.max(receivedPower, calculatedPower - 1);
        } else {
            return Math.max(receivedPower - 1, calculatedPower - 1);
        }
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return ((RedstoneAccessor) this).getShouldSignal();
    }

    private int getWireSignal(BlockState state) {
        if (state.is(Blocks.REDSTONE_WIRE)) {
            return state.getValue(RedStoneWireBlock.POWER);
        } else if (state.is(BlockInit.ATLANTEAN_POWER_DUST_WIRE.get())) {
            return state.getValue(RedStoneWireBlock.POWER);
        }
        return 0;
    }

    public AtlanteanPowerDust(Properties settings) {
        super(settings.noCollission().instabreak());
        this.registerDefaultState(defaultBlockState().setValue(NORTH, RedstoneSide.NONE).setValue(EAST, RedstoneSide.NONE).setValue(SOUTH, RedstoneSide.NONE).setValue(WEST, RedstoneSide.NONE).setValue(POWER, 0).setValue(WATERLOGGED, Boolean.TRUE));
        ((RedstoneAccessor) this).setShouldSignal(true);
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
            return this.canSurviveOn(world, blockPos, blockState);
        } else {
            return false;
        }
    }

    private boolean canSurviveOn(BlockGetter world, BlockPos pos, BlockState floor) {
        return floor.isFaceSturdy(world, pos, Direction.UP) || floor.is(Blocks.HOPPER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, EAST, NORTH, SOUTH, WEST, POWER);
    }
}
