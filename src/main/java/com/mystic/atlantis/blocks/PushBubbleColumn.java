package com.mystic.atlantis.blocks;

import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.particles.ModParticleTypes;

public class PushBubbleColumn extends Block implements BucketPickup {
    public static final DirectionProperty PUSH = BlockStateProperties.FACING;
    public static final IntegerProperty DECAY = IntegerProperty.create("decay", 0, 30);
    private static final int SCHEDULED_TICK_DELAY = 1;

    public PushBubbleColumn(BlockBehaviour.Properties settings) {
        super(settings.noCollission().noDrops());
        this.registerDefaultState(this.stateDefinition.any().setValue(DECAY, 0));
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        BlockState blockState = world.getBlockState(pos.above());
        if (blockState.isAir()) {
            onBubbleColumnSurfaceCollision(entity, state.getValue(PUSH));
            if (!world.isClientSide) {
                ServerLevel serverWorld = (ServerLevel) world;

                for (int i = 0; i < 2; ++i) {
                    serverWorld.sendParticles(ParticleTypes.SPLASH, (double) pos.getX() + world.random.nextDouble(), pos.getY() + 1, (double) pos.getZ() + world.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
                    serverWorld.sendParticles(ParticleTypes.BUBBLE, (double) pos.getX() + world.random.nextDouble(), pos.getY() + 1, (double) pos.getZ() + world.random.nextDouble(), 1, 0.0D, 0.01D, 0.0D, 0.2D);
                }
            }
        } else {
            onBubbleColumnCollision(entity, state.getValue(PUSH));
        }

    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        update(world, pos, state.getValue(PUSH));
    }

    public static void update(ServerLevel world, BlockPos pos, Direction dir) {
        BlockState[] blockStates = new BlockState[30];
        BlockPos[] blockPoses = new BlockPos[30];

        blockPoses[0] = pos.relative(dir.getOpposite());
        blockStates[0] = world.getBlockState(blockPoses[0]);
        blockPoses[1] = pos;
        blockStates[1] = world.getBlockState(pos);

        for (int i = 2; i < blockStates.length; i++) {
            blockPoses[i] = pos.relative(dir, i-1);
            blockStates[i] = world.getBlockState(blockPoses[i]);
        }

        for (int i = 0; i < blockStates.length-1; i++) {
            Optional<BlockState> optionalBlockState = getBubbleState(blockStates[i], blockStates[i+1], dir);

            if(optionalBlockState.isPresent()) {
                blockStates[i+1] = optionalBlockState.get();
            }
        }

        for (int i = 1; i < blockStates.length; i++) {
            world.setBlock(blockPoses[i], blockStates[i], Block.UPDATE_CLIENTS);
        }
//
//        getBubbleState(previous, current, dir).ifPresent(state -> world.setBlockState(pos, state, Block.NOTIFY_LISTENERS));
//
//        getBubbleState(current, next, dir).ifPresent(state -> world.setBlockState(nextPos, state, Block.NOTIFY_LISTENERS));
     }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    public static void update(LevelAccessor world, BlockPos pos, BlockState state, Direction direction, int decay) {
        update(world, pos, world.getBlockState(pos), state, direction, decay);
    }

    public static void update(LevelAccessor world, BlockPos pos, BlockState water, BlockState bubbleSource, Direction direction, int decay) {
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
        return state.is(Blocks.WATER) && state.getFluidState().getAmount() >= 8 && state.getFluidState().isSource();
    }

    private static Optional<BlockState> getBubbleState(BlockState previous, BlockState current, Direction dir) {
        if(!current.is(BlockInit.CALCITE_BLOCK)) {
            if(isStillWater(previous)) {
                if(!isStillWater(current) && !current.is(BlockInit.PUSH_BUBBLE_COLUMN)) {
                    return Optional.empty();
                } else {
                    return Optional.of(Blocks.WATER.defaultBlockState());
                }
            } else if (previous.is(BlockInit.CALCITE_BLOCK)) {
                if (isStillWater(current) || current.is(BlockInit.PUSH_BUBBLE_COLUMN)) {
                    return Optional.ofNullable(BlockInit.PUSH_BUBBLE_COLUMN.defaultBlockState().setValue(PUSH, dir).setValue(DECAY, 29));
                } else {
                    return Optional.empty();
                }
            } else if (previous.is(BlockInit.PUSH_BUBBLE_COLUMN)) {
                if (previous.getValue(DECAY) == 0) {
                    return Optional.of(Blocks.WATER.defaultBlockState());
                } else if (previous.getValue(PUSH).equals(dir)) {
                    if (isStillWater(current) || current.is(BlockInit.PUSH_BUBBLE_COLUMN)) {
                        return Optional.ofNullable(BlockInit.PUSH_BUBBLE_COLUMN.defaultBlockState().setValue(PUSH, dir).setValue(DECAY, previous.getValue(DECAY) - 1));
                    } else {
                        return Optional.empty();
                    }
                } else {
                    return Optional.of(Blocks.WATER.defaultBlockState());
                }
            } else {
                if (current.is(BlockInit.PUSH_BUBBLE_COLUMN)) {
                    return Optional.of(Blocks.WATER.defaultBlockState());
                } else {
                    return Optional.empty();
                }
            }
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        world.addAlwaysVisibleParticle(ModParticleTypes.PUSH_BUBBLESTREAM, x + 0.5D, y + 0.8D, z, state.getValue(PUSH).get3DDataValue(), 0.0D, 0.0D);

        if (random.nextInt(200) == 0) {
            world.playLocalSound(x, y, z, SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
        }

    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        if (!this.canSurvive(state, world, pos) && !neighborState.is(BlockInit.PUSH_BUBBLE_COLUMN) && isStillWater(neighborState)) {
            world.scheduleTick(pos, this, 1);
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction dir = state.getValue(PUSH);

        BlockState blockState = world.getBlockState(pos.relative(dir.getOpposite()));

        if(blockState.is(BlockInit.PUSH_BUBBLE_COLUMN) && blockState.getValue(PUSH).equals(dir)) {
            return blockState.getValue(DECAY) >= 0;
        }

        return blockState.is(BlockInit.CALCITE_BLOCK);
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }


    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DECAY, PUSH);
    }

    @Override
    public ItemStack pickupBlock(LevelAccessor world, BlockPos pos, BlockState state) {
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);
        return new ItemStack(Items.WATER_BUCKET);
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Fluids.WATER.getPickupSound();
    }

    public void onBubbleColumnSurfaceCollision(Entity entity, Direction drag) {
        adjust(entity, drag, AtlantisConfig.get().calciteAcceleration, AtlantisConfig.get().calciteThreshold);
    }

    public void adjust(Entity entity, Direction drag, double value, double limit) {
        Vec3 vec3d = entity.getDeltaMovement();

        value = value * drag.getAxisDirection().getStep();
        limit = limit * drag.getAxisDirection().getStep();

        switch (drag.getAxis()) {
            case X -> {
                entity.setDeltaMovement(get(drag.getAxisDirection(),value + vec3d.x, limit), vec3d.y, vec3d.z);
            }
            case Y -> {
                entity.setDeltaMovement(vec3d.x, get(drag.getAxisDirection(),value + vec3d.y, limit), vec3d.z);
            }
            case Z -> {
                entity.setDeltaMovement(vec3d.x, vec3d.y, get(drag.getAxisDirection(),value + vec3d.z, limit));
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

