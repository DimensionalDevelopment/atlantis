package com.mystic.atlantis.blocks;

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
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

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

    public static void update(ServerLevel level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            for (int i = 1; i < AtlantisConfig.INSTANCE.maxDistanceOfPushBubbleColumn.get();  i++) {
                BlockPos.MutableBlockPos bubblePos = pos.mutable();
                BlockState bubbleState = level.getBlockState(pos.relative(direction, i));
                BlockState previousBubbleState = level.getBlockState(pos.relative(direction,i - 1));
                bubbleState = getBubbleState(previousBubbleState, bubbleState, direction);

                if (bubbleState == null)
                    break;

                level.setBlock(bubblePos.move(direction.getNormal()), bubbleState, Block.UPDATE_ALL);
            }
        }
     }

    @NotNull
    @Override
    public FluidState getFluidState(@NotNull BlockState targetState) {
        return Fluids.WATER.getSource(false);
    }

    private static boolean isStillWater(BlockState state) {
        return state.is(Blocks.WATER) && state.getFluidState().getAmount() >= 8 && state.getFluidState().isSource();
    }

    private static BlockState getBubbleState(BlockState previousState, BlockState targetState, Direction curDir) {
        if (targetState.is(BlockInit.BUBBLE_MAGMA.get()))
            return null;

        if (previousState.is(BlockInit.BUBBLE_MAGMA.get())) {
            if (isStillWater(targetState) || targetState.is(BlockInit.PUSH_BUBBLE_COLUMN.get()))
                return BlockInit.PUSH_BUBBLE_COLUMN.get().defaultBlockState().setValue(PUSH, curDir).setValue(DECAY, AtlantisConfig.INSTANCE.maxDistanceOfPushBubbleColumn.get());
        } else if (previousState.is(BlockInit.PUSH_BUBBLE_COLUMN.get())) {
            if (previousState.getValue(DECAY) == 0 || !previousState.getValue(PUSH).equals(curDir))
                return Blocks.WATER.defaultBlockState();

            if (isStillWater(targetState) || targetState.is(BlockInit.PUSH_BUBBLE_COLUMN.get()))
                return BlockInit.PUSH_BUBBLE_COLUMN.get().defaultBlockState().setValue(PUSH, curDir).setValue(DECAY, previousState.getValue(DECAY) - 1);
        } else if (isStillWater(previousState)) {
            if (isStillWater(targetState) || targetState.is(BlockInit.PUSH_BUBBLE_COLUMN.get()))
                return Blocks.WATER.defaultBlockState();
        } else if (targetState.is(BlockInit.PUSH_BUBBLE_COLUMN.get())) {
            return Blocks.WATER.defaultBlockState();
        }

        return null;
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

