package com.mystic.atlantis.blocks.power;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.*;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.mixin.RedstoneAccessor;

public class AtlanteanPowerDust extends RedstoneWireBlock implements Waterloggable {

    public static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;
    private static final EnumProperty<WireConnection> WIRE_CONNECTION_NORTH = Properties.NORTH_WIRE_CONNECTION;
    private static final EnumProperty<WireConnection> WIRE_CONNECTION_EAST = Properties.EAST_WIRE_CONNECTION;
    private static final EnumProperty<WireConnection>WIRE_CONNECTION_SOUTH = Properties.SOUTH_WIRE_CONNECTION;
    private static final EnumProperty<WireConnection> WIRE_CONNECTION_WEST = Properties.WEST_WIRE_CONNECTION;
    private static final Map<Direction, EnumProperty<WireConnection>> DIRECTION_TO_WIRE_CONNECTION_PROPERTY = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, WIRE_CONNECTION_NORTH, Direction.EAST, WIRE_CONNECTION_EAST, Direction.SOUTH, WIRE_CONNECTION_SOUTH, Direction.WEST, WIRE_CONNECTION_WEST));
    private static final Property<Integer> POWER = RedstoneWireBlock.POWER;
    private static Vec3d[] COLOR = Util.make(new Vec3d[16], (vec3ds) -> {
        for (int i = 0; i <= 15; ++i) {
            float f = (float) i / 15.0F;
            float r = MathHelper.clamp(f * f * 0.7F - 0.5F, 0.0F, 1.0F);
            float g = MathHelper.clamp(f * f * 0.6F - 0.7F, 0.0F, 1.0F);
            float b = f * 0.6F + (f > 0.0F ? 0.4F : 0.3F);
            vec3ds[i] = new Vec3d((double) r, (double) g, (double) b);
        }
    });

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int i = state.get(POWER);
        if (i != 0) {

            for (Direction direction : Direction.Type.HORIZONTAL) {
                WireConnection wireConnection = state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
                switch (wireConnection) {
                    case UP:
                        this.addPoweredParticles(world, random, pos, COLOR[i], direction, Direction.UP, -0.5F, 0.5F);
                    case SIDE:
                        this.addPoweredParticles(world, random, pos, COLOR[i], Direction.DOWN, direction, 0.0F, 0.5F);
                        break;
                    case NONE:
                    default:
                        this.addPoweredParticles(world, random, pos, COLOR[i], Direction.DOWN, direction, 0.0F, 0.3F);
                }
            }

        }
    }

    private void addPoweredParticles(World world, Random random, BlockPos pos, Vec3d color, Direction direction, Direction direction2, float f, float g) {
        float h = g - f;
        if (!(random.nextFloat() >= 0.2F * h)) {
            float i = 0.4375F;
            float j = f + h * random.nextFloat();
            double d = 0.5D + (double)(0.4375F * (float)direction.getOffsetX()) + (double)(j * (float)direction2.getOffsetX());
            double e = 0.5D + (double)(0.4375F * (float)direction.getOffsetY()) + (double)(j * (float)direction2.getOffsetY());
            double k = 0.5D + (double)(0.4375F * (float)direction.getOffsetZ()) + (double)(j * (float)direction2.getOffsetZ());
            world.addParticle(new DustParticleEffect(new Vec3f(color), 1.0F), (double)pos.getX() + d, (double)pos.getY() + e, (double)pos.getZ() + k, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (((RedstoneAccessor) this).getWiresGivePower() && direction != Direction.DOWN) {
            int i = state.get(POWER);
            if (i == 0) {
                return 0;
            } else {
                return direction != Direction.UP && !((WireConnection)this.getPlacementState(world, state, pos).get((Property)DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction.getOpposite()))).isConnected() ? 0 : i;
            }
        } else {
            return 0;
        }
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return !((RedstoneAccessor) this).getWiresGivePower() ? 0 : state.getWeakRedstonePower(world, pos, direction);
    }

    @Override
    protected void update(World world, BlockPos pos, BlockState state) {
        int receivedPower = this.getReceivedRedstonePower(world, pos);
        if (state.get(POWER) != receivedPower) {
            if (world.getBlockState(pos) == state) {
                world.setBlockState(pos, state.with(POWER, receivedPower), Block.NOTIFY_LISTENERS);
            }

            Set<BlockPos> set = Sets.newHashSet();
            set.add(pos);
            Direction[] var6 = Direction.values();

            for (Direction direction : var6) {
                set.add(pos.offset(direction));
            }

            for (BlockPos blockPos : set) {
                world.updateNeighborsAlways(blockPos, this);
            }
        }
    }

    @Override
    public int getReceivedRedstonePower(World world, BlockPos pos) {
        ((RedstoneAccessor) this).setWiresGivePower(false);
        int receivedPower = world.getReceivedRedstonePower(pos);
        ((RedstoneAccessor) this).setWiresGivePower(true);
        int calculatedPower = 0;
        if (receivedPower < 15 && receivedPower > 0) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos);
                calculatedPower = Math.max(calculatedPower, this.increasePower(blockState));
                BlockPos blockPos2 = pos.up();
                if (blockState.isSolidBlock(world, blockPos) && !world.getBlockState(blockPos2).isSolidBlock(world, blockPos2)) {
                    calculatedPower = Math.max(calculatedPower, this.increasePower(world.getBlockState(blockPos.up())));
                } else if (!blockState.isSolidBlock(world, blockPos)) {
                    calculatedPower = Math.max(calculatedPower, this.increasePower(world.getBlockState(blockPos.down())));
                }
            }

            return Math.max(receivedPower - 1, calculatedPower - 1);
        } else if (receivedPower == 0) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos);
                calculatedPower = Math.max(calculatedPower, this.increasePower(blockState));
                BlockPos blockPos2 = pos.up();
                if (blockState.isSolidBlock(world, blockPos) && !world.getBlockState(blockPos2).isSolidBlock(world, blockPos2)) {
                    calculatedPower = Math.max(calculatedPower, this.increasePower(world.getBlockState(blockPos.up())));
                } else if (!blockState.isSolidBlock(world, blockPos)) {
                    calculatedPower = Math.max(calculatedPower, this.increasePower(world.getBlockState(blockPos.down())));
                }
            }

            return Math.max(receivedPower, calculatedPower - 1);
        } else {
            return Math.max(receivedPower - 1, calculatedPower - 1);
        }
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return ((RedstoneAccessor) this).getWiresGivePower();
    }

    private int increasePower(BlockState state) {
        if (state.isOf(Blocks.REDSTONE_WIRE)) {
            return state.get(RedstoneWireBlock.POWER);
        } else if (state.isOf(BlockInit.ATLANTEAN_POWER_DUST_WIRE)) {
            return state.get(RedstoneWireBlock.POWER);
        }
        return 0;
    }

    public AtlanteanPowerDust(Settings settings) {
        super(settings.noCollision().breakInstantly());
        this.setDefaultState(getDefaultState().with(WIRE_CONNECTION_NORTH, WireConnection.NONE).with(WIRE_CONNECTION_EAST, WireConnection.NONE).with(WIRE_CONNECTION_SOUTH, WireConnection.NONE).with(WIRE_CONNECTION_WEST, WireConnection.NONE).with(POWER, 0).with(WATERLOGGED, Boolean.TRUE));
        ((RedstoneAccessor) this).setWiresGivePower(true);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
            BlockPos blockPos = pos.down();
            BlockState blockState = world.getBlockState(blockPos);
            return this.canRunOnTop(world, blockPos, blockState);
        } else {
            return false;
        }
    }

    private boolean canRunOnTop(BlockView world, BlockPos pos, BlockState floor) {
        return floor.isSideSolidFullSquare(world, pos, Direction.UP) || floor.isOf(Blocks.HOPPER);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, WIRE_CONNECTION_EAST, WIRE_CONNECTION_NORTH, WIRE_CONNECTION_SOUTH, WIRE_CONNECTION_WEST, POWER);
    }
}
