package com.mystic.atlantis.blocks;

import com.mystic.atlantis.init.BlockInit;
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
    private static final int SCHEDULED_TICK_DELAY = 5;

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
        Direction dir = state.get(PUSH);

        update(world, pos, state, world.getBlockState(pos.offset(dir)), dir, state.get(DECAY));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getStill(false);
    }

    public static void update(WorldAccess world, BlockPos pos, BlockState state, Direction direction, int decay) {
        update(world, pos, world.getBlockState(pos), state, direction, decay);
    }

    public static void update(WorldAccess world, BlockPos pos, BlockState water, BlockState bubbleSource, Direction direction, int decay) {
        if (isStillWater(water)) {
            world.setBlockState(pos, getBubbleState(bubbleSource, direction, decay), Block.NOTIFY_LISTENERS);

            BlockPos.Mutable mutable = pos.mutableCopy().move(direction);
            BlockState bs = world.getBlockState(mutable);
            if(isStillWater(bs) && (decay-1) >= 0) {
                world.setBlockState(mutable, getBubbleState(bs, direction, decay-1), Block.NOTIFY_LISTENERS);
            }

        }
    }

    private static boolean isStillWater(BlockState state) {
        return state.isOf(BlockInit.PUSH_BUBBLE_COLUMN) || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() >= 8 && state.getFluidState().isStill();
    }

    private static BlockState getBubbleState(BlockState state, Direction direction, int decay) {
        if (state.isOf(BlockInit.PUSH_BUBBLE_COLUMN)) {
            return state;
        } else {
            return state.isOf(BlockInit.CALCITE_BLOCK) ? BlockInit.PUSH_BUBBLE_COLUMN.getDefaultState().with(PUSH, direction).with(DECAY, decay) : Blocks.WATER.getDefaultState();
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double d = pos.getX();
        double e = pos.getY();
        double f = pos.getZ();
        if (state.get(PUSH).equals(Direction.DOWN)) {
            world.addImportantParticle(ParticleTypes.CURRENT_DOWN, d + 0.5D, e + 0.8D, f, 0.0D, 0.0D, 0.0D);
            if (random.nextInt(200) == 0) {
                world.playSound(d, e, f, SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
            }
        } else {
            world.addImportantParticle(ParticleTypes.BUBBLE_COLUMN_UP, d + 0.5D, e, f + 0.5D, 0.0D, 0.04D, 0.0D);
            world.addImportantParticle(ParticleTypes.BUBBLE_COLUMN_UP, d + (double) random.nextFloat(), e + (double) random.nextFloat(), f + (double) random.nextFloat(), 0.0D, 0.04D, 0.0D);
            if (random.nextInt(200) == 0) {
                world.playSound(d, e, f, SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
            }
        }

    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        if (!this.canPlaceAt(state, world, pos) && !neighborState.isOf(BlockInit.PUSH_BUBBLE_COLUMN) && isStillWater(neighborState)) {
            world.getBlockTickScheduler().schedule(pos, this, 5);
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

    @Override
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
        adjust(entity, drag, 0.1d, 1.8d);
    }

    public void adjust(Entity entity, Direction drag, double value, double limit) {
        Vec3d vec3d = entity.getVelocity();

        value = value * drag.getDirection().offset();
        limit = limit * drag.getDirection().offset();
        switch (drag.getAxis()) {
            case X -> {
                value = Math.max(limit, vec3d.x + value);
                entity.setVelocity(value, vec3d.y, vec3d.z);
            }
            case Y -> {
                value = Math.max(limit, vec3d.y + value);
                entity.setVelocity(vec3d.x, value, vec3d.z);
            }
            case Z -> {
                value = Math.max(limit, vec3d.z + value);
                entity.setVelocity(vec3d.x, vec3d.y, value);
            }
        }
    }

    public void onBubbleColumnCollision(Entity entity, Direction drag) {
        adjust(entity, drag, 0.06d, 0.7d);
        entity.fallDistance = 0.0F;
    }
}

