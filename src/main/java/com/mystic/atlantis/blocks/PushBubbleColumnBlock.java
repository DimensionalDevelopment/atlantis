package com.mystic.atlantis.blocks;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.particles.ModParticleTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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

public class PushBubbleColumnBlock extends Block implements BucketPickup {
    public static final DirectionProperty PUSH = BlockStateProperties.FACING;
    public static final IntegerProperty DECAY = IntegerProperty.create("decay", 0, 30);

    public PushBubbleColumnBlock(Properties settings) {
        super(settings.noCollission());
        this.registerDefaultState(this.stateDefinition.any().setValue(DECAY, 0));
    }

    @Override
    public void entityInside(BlockState targetState, Level level, BlockPos targetPos, Entity targetEntity) {
        BlockState aboveState = level.getBlockState(targetPos.above());
        
        if (aboveState.isAir()) {
            onBubbleColumnSurfaceCollision(targetEntity, targetState.getValue(PUSH));
            if (!level.isClientSide) {
                ServerLevel serverLevel = (ServerLevel) level;

                for (int i = 0; i < 2; ++i) {
                    serverLevel.sendParticles(ParticleTypes.SPLASH, (double) targetPos.getX() + level.random.nextDouble(), targetPos.getY() + 1, (double) targetPos.getZ() + level.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
                    serverLevel.sendParticles(ParticleTypes.BUBBLE, (double) targetPos.getX() + level.random.nextDouble(), targetPos.getY() + 1, (double) targetPos.getZ() + level.random.nextDouble(), 1, 0.0D, 0.01D, 0.0D, 0.2D);
                }
            }
        } else {
            onBubbleColumnCollision(targetEntity, targetState.getValue(PUSH));
        }
    }

    @Override
    public void tick(BlockState targetState, @NotNull ServerLevel level, @NotNull BlockPos targetPos, @NotNull RandomSource random) {
        update(level, targetPos, targetState.getValue(PUSH));
    }

    public static void update(ServerLevel level, BlockPos pos, Direction dir) {
        BlockState[] targetStates = new BlockState[30];
        BlockPos[] targetPoses = new BlockPos[30];

        targetPoses[0] = pos.relative(dir.getOpposite());
        targetStates[0] = level.getBlockState(targetPoses[0]);
        targetPoses[1] = pos;
        targetStates[1] = level.getBlockState(pos);

        for (int i = 2; i < targetStates.length; i++) {
            targetPoses[i] = pos.relative(dir, i-1);
            targetStates[i] = level.getBlockState(targetPoses[i]);
        }

        for (int i = 0; i < targetStates.length-1; i++) {
            Optional<BlockState> bubbleState = getBubbleState(targetStates[i], targetStates[i+1], dir);

            if(bubbleState.isPresent()) {
                targetStates[i+1] = bubbleState.get();
            }
        }

        for (int i = 1; i < targetStates.length; i++) {
            level.setBlock(targetPoses[i], targetStates[i], Block.UPDATE_CLIENTS);
        }
     }
    
    @NotNull
    @Override
    public FluidState getFluidState(@NotNull BlockState targetState) {
        return Fluids.WATER.getSource(false);
    }

    public static void update(LevelAccessor accessor, BlockPos targetPos, BlockState targetState, Direction curDir, int decay) {
    }

    private static boolean isStillWater(BlockState state) {
        return state.is(Blocks.WATER) && state.getFluidState().getAmount() >= 8 && state.getFluidState().isSource();
    }

    private static Optional<BlockState> getBubbleState(BlockState previousState, BlockState targetState, Direction curDir) {
        if(!targetState.is(BlockInit.BUBBLE_MAGMA.get())) {
            if(isStillWater(previousState)) {
                if(!isStillWater(targetState) && !targetState.is(BlockInit.PUSH_BUBBLE_COLUMN.get())) {
                    return Optional.empty();
                } else {
                    return Optional.of(Blocks.WATER.defaultBlockState());
                }
            } else if (previousState.is(BlockInit.BUBBLE_MAGMA.get())) {
                if (isStillWater(targetState) || targetState.is(BlockInit.PUSH_BUBBLE_COLUMN.get())) {
                    return Optional.ofNullable(BlockInit.PUSH_BUBBLE_COLUMN.get().defaultBlockState().setValue(PUSH, curDir).setValue(DECAY, 29));
                } else {
                    return Optional.empty();
                }
            } else if (previousState.is(BlockInit.PUSH_BUBBLE_COLUMN.get())) {
                if (previousState.getValue(DECAY) == 0) {
                    return Optional.of(Blocks.WATER.defaultBlockState());
                } else if (previousState.getValue(PUSH).equals(curDir)) {
                    if (isStillWater(targetState) || targetState.is(BlockInit.PUSH_BUBBLE_COLUMN.get())) {
                        return Optional.ofNullable(BlockInit.PUSH_BUBBLE_COLUMN.get().defaultBlockState().setValue(PUSH, curDir).setValue(DECAY, previousState.getValue(DECAY) - 1));
                    } else {
                        return Optional.empty();
                    }
                } else {
                    return Optional.of(Blocks.WATER.defaultBlockState());
                }
            } else {
                if (targetState.is(BlockInit.PUSH_BUBBLE_COLUMN.get())) {
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
    public void animateTick(BlockState targetState, Level level, BlockPos targetPos, RandomSource random) {
        double x = targetPos.getX();
        double y = targetPos.getY();
        double z = targetPos.getZ();

        level.addAlwaysVisibleParticle(ModParticleTypes.PUSH_BUBBLESTREAM.get(), x + 0.5D, y + 0.8D, z, targetState.getValue(PUSH).get3DDataValue(), 0.0D, 0.0D);

        if (random.nextInt(200) == 0) {
            level.playLocalSound(x, y, z, SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
        }

    }

    @Override
    public BlockState updateShape(BlockState targetState, Direction curDir, BlockState neighborState, LevelAccessor level, BlockPos targetPos, BlockPos neighborPos) {
        level.scheduleTick(targetPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        
        if (!this.canSurvive(targetState, level, targetPos) && !neighborState.is(BlockInit.PUSH_BUBBLE_COLUMN.get()) && isStillWater(neighborState)) {
            level.scheduleTick(targetPos, this, 1);
        }

        return super.updateShape(targetState, curDir, neighborState, level, targetPos, neighborPos);
    }

    public boolean canSurvive(BlockState targetState, LevelReader reader, BlockPos targetPos) {
        Direction pushDir = targetState.getValue(PUSH);

        BlockState relativeState = reader.getBlockState(targetPos.relative(pushDir.getOpposite()));

        if(relativeState.is(BlockInit.PUSH_BUBBLE_COLUMN.get()) && relativeState.getValue(PUSH).equals(pushDir)) {
            return relativeState.getValue(DECAY) >= 0;
        }

        return relativeState.is(BlockInit.BUBBLE_MAGMA.get());
    }

    public VoxelShape getShape(BlockState targetState, BlockGetter getter, BlockPos targetPos, CollisionContext context) {
        return Shapes.empty();
    }


    @Override
    public RenderShape getRenderShape(BlockState targetState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DECAY, PUSH);
    }

    @Override
    public ItemStack pickupBlock(LevelAccessor level, BlockPos targetPos, BlockState targetState) {
        level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);
        return new ItemStack(Items.WATER_BUCKET);
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Fluids.WATER.getPickupSound();
    }

    public void onBubbleColumnSurfaceCollision(Entity targetEntity, Direction dragDir) {
        adjustEntityMovement(targetEntity, dragDir, AtlantisConfig.INSTANCE.magmaAcceleration.get(), AtlantisConfig.INSTANCE.magmaThreshold.get());
    }

    public void adjustEntityMovement(Entity entity, Direction dragDir, double magnitude, double limit) {
        Vec3 dMovement = entity.getDeltaMovement();

        magnitude = magnitude * dragDir.getAxisDirection().getStep();
        limit = limit * dragDir.getAxisDirection().getStep();

        switch (dragDir.getAxis()) {
            case X -> {
                entity.setDeltaMovement(getAdjustedMagLimit(dragDir.getAxisDirection(), magnitude + dMovement.x, limit), dMovement.y, dMovement.z);
            }
            case Y -> {
                entity.setDeltaMovement(dMovement.x, getAdjustedMagLimit(dragDir.getAxisDirection(), magnitude + dMovement.y, limit), dMovement.z);
            }
            case Z -> {
                entity.setDeltaMovement(dMovement.x, dMovement.y, getAdjustedMagLimit(dragDir.getAxisDirection(), magnitude + dMovement.z, limit));
            }
        }
    }

    public double getAdjustedMagLimit(Direction.AxisDirection curAxisDir, double magnitude, double limit) {
        if(curAxisDir == Direction.AxisDirection.POSITIVE) {
            return Math.max(magnitude, limit);
        } else {
            return Math.min(magnitude, limit);
        }
    }

    public void onBubbleColumnCollision(Entity targetEntity, Direction dragDir) {
        adjustEntityMovement(targetEntity, dragDir, 0.06d, 0.7d);
        targetEntity.fallDistance = 0.0F;
    }
}

