package com.mystic.atlantis.blocks;

import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.particles.ModParticleTypes;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Optional;
import java.util.Random;

public class PushBubbleColumn extends Block implements FluidDrainable {
    public static final DirectionProperty PUSH = Properties.FACING;
    public static final IntProperty DECAY = IntProperty.of("decay", 0, 30);
    private static final int SCHEDULED_TICK_DELAY = 1;

    public PushBubbleColumn(AbstractBlock.Settings settings) {
        super(settings.noCollision().dropsNothing());
        this.setDefaultState(this.stateManager.getDefaultState().with(DECAY, 0));
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        BlockState blockState = world.getBlockState(pos.up());
        if (blockState.isAir()) {
            onBubbleColumnSurfaceCollision(entity, state.get(PUSH));
            if (!world.isClient) {
                ServerWorld serverWorld = (ServerWorld) world;

                for (int i = 0; i < 2; ++i) {
                    serverWorld.spawnParticles(ParticleTypes.SPLASH, (double) pos.getX() + world.random.nextDouble(), pos.getY() + 1, (double) pos.getZ() + world.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
                    serverWorld.spawnParticles(ParticleTypes.BUBBLE, (double) pos.getX() + world.random.nextDouble(), pos.getY() + 1, (double) pos.getZ() + world.random.nextDouble(), 1, 0.0D, 0.01D, 0.0D, 0.2D);
                }
            }
        } else {
            onBubbleColumnCollision(entity, state.get(PUSH));
        }

    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        update(world, pos, state.get(PUSH));
    }

    public static void update(ServerWorld world, BlockPos pos, Direction dir) {
        BlockState[] blockStates = new BlockState[30];
        BlockPos[] blockPoses = new BlockPos[30];

        blockPoses[0] = pos.offset(dir.getOpposite());
        blockStates[0] = world.getBlockState(blockPoses[0]);
        blockPoses[1] = pos;
        blockStates[1] = world.getBlockState(pos);

        for (int i = 2; i < blockStates.length; i++) {
            blockPoses[i] = pos.offset(dir, i-1);
            blockStates[i] = world.getBlockState(blockPoses[i]);
        }

        for (int i = 0; i < blockStates.length-1; i++) {
            Optional<BlockState> optionalBlockState = getBubbleState(blockStates[i], blockStates[i+1], dir);

            if(optionalBlockState.isPresent()) {
                blockStates[i+1] = optionalBlockState.get();
            }
        }

        for (int i = 1; i < blockStates.length; i++) {
            world.setBlockState(blockPoses[i], blockStates[i], Block.NOTIFY_LISTENERS);
        }
//
//        getBubbleState(previous, current, dir).ifPresent(state -> world.setBlockState(pos, state, Block.NOTIFY_LISTENERS));
//
//        getBubbleState(current, next, dir).ifPresent(state -> world.setBlockState(nextPos, state, Block.NOTIFY_LISTENERS));
     }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getStill(false);
    }

    public static void update(WorldAccess world, BlockPos pos, BlockState state, Direction direction, int decay) {
        update(world, pos, world.getBlockState(pos), state, direction, decay);
    }

    public static void update(WorldAccess world, BlockPos pos, BlockState water, BlockState bubbleSource, Direction direction, int decay) {
//        if (isStillWater(water) && decay >= 0) {
//            BlockState current = getBubbleState(bubbleSource, direction, decay);
//
//            world.setBlockState(pos, current, Block.NOTIFY_LISTENERS);
//
//            pos = pos.offset(direction);
//
//            update(world, pos, world.getBlockState(pos.offset(direction)), current, direction, decay -1);
//        }
    }

    private static boolean isStillWater(BlockState state) {
        return state.isOf(Blocks.WATER) && state.getFluidState().getLevel() >= 8 && state.getFluidState().isStill();
    }

    private static Optional<BlockState> getBubbleState(BlockState previous, BlockState current, Direction dir) {
        if(!current.isOf(BlockInit.CALCITE_BLOCK)) {
            if(isStillWater(previous)) {
                if(!isStillWater(current) && !current.isOf(BlockInit.PUSH_BUBBLE_COLUMN)) {
                    return Optional.empty();
                } else {
                    return Optional.of(Blocks.WATER.getDefaultState());
                }
            } else if (previous.isOf(BlockInit.CALCITE_BLOCK)) {
                if (isStillWater(current) || current.isOf(BlockInit.PUSH_BUBBLE_COLUMN)) {
                    return Optional.ofNullable(BlockInit.PUSH_BUBBLE_COLUMN.getDefaultState().with(PUSH, dir).with(DECAY, 29));
                } else {
                    return Optional.empty();
                }
            } else if (previous.isOf(BlockInit.PUSH_BUBBLE_COLUMN)) {
                if (previous.get(DECAY) == 0) {
                    return Optional.of(Blocks.WATER.getDefaultState());
                } else if (previous.get(PUSH).equals(dir)) {
                    if (isStillWater(current) || current.isOf(BlockInit.PUSH_BUBBLE_COLUMN)) {
                        return Optional.ofNullable(BlockInit.PUSH_BUBBLE_COLUMN.getDefaultState().with(PUSH, dir).with(DECAY, previous.get(DECAY) - 1));
                    } else {
                        return Optional.empty();
                    }
                } else {
                    return Optional.of(Blocks.WATER.getDefaultState());
                }
            } else {
                if (current.isOf(BlockInit.PUSH_BUBBLE_COLUMN)) {
                    return Optional.of(Blocks.WATER.getDefaultState());
                } else {
                    return Optional.empty();
                }
            }
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        world.addImportantParticle(ModParticleTypes.PUSH_BUBBLESTREAM, x + 0.5D, y + 0.8D, z, state.get(PUSH).getId(), 0.0D, 0.0D);

        if (random.nextInt(200) == 0) {
            world.playSound(x, y, z, SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
        }

    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        if (!this.canPlaceAt(state, world, pos) && !neighborState.isOf(BlockInit.PUSH_BUBBLE_COLUMN) && isStillWater(neighborState)) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction dir = state.get(PUSH);

        BlockState blockState = world.getBlockState(pos.offset(dir.getOpposite()));

        if(blockState.isOf(BlockInit.PUSH_BUBBLE_COLUMN) && blockState.get(PUSH).equals(dir)) {
            return blockState.get(DECAY) >= 0;
        }

        return blockState.isOf(BlockInit.CALCITE_BLOCK);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DECAY, PUSH);
    }

    @Override
    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
        return new ItemStack(Items.WATER_BUCKET);
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Fluids.WATER.getBucketFillSound();
    }

    public void onBubbleColumnSurfaceCollision(Entity entity, Direction drag) {
        adjust(entity, drag, AtlantisConfig.get().calciteAcceleration, AtlantisConfig.get().calciteThreshold);
    }

    public void adjust(Entity entity, Direction drag, double value, double limit) {
        Vec3d vec3d = entity.getVelocity();

        value = value * drag.getDirection().offset();
        limit = limit * drag.getDirection().offset();

        switch (drag.getAxis()) {
            case X -> {
                entity.setVelocity(get(drag.getDirection(),value + vec3d.x, limit), vec3d.y, vec3d.z);
            }
            case Y -> {
                entity.setVelocity(vec3d.x, get(drag.getDirection(),value + vec3d.y, limit), vec3d.z);
            }
            case Z -> {
                entity.setVelocity(vec3d.x, vec3d.y, get(drag.getDirection(),value + vec3d.z, limit));
            }
        }
    }

    public double get(Direction.AxisDirection direction, double val, double limit) {
        if(direction == Direction.AxisDirection.POSITIVE) {
            return Math.max(val, limit);
        } else {
            return Math.min(val, limit);
        }
    }

    public void onBubbleColumnCollision(Entity entity, Direction drag) {
        adjust(entity, drag, 0.06d, 0.7d);
        entity.fallDistance = 0.0F;
    }
}

