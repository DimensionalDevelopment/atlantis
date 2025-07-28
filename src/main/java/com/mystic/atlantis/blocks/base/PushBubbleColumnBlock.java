package com.mystic.atlantis.blocks.base;

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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
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
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PushBubbleColumnBlock extends Block implements BucketPickup {
    public static final DirectionProperty PUSH = BlockStateProperties.FACING;
    public static final IntegerProperty DECAY = IntegerProperty.create("decay", 0, 30);

    public PushBubbleColumnBlock(Properties settings) {
        super(settings.replaceable());
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
                    serverLevel.sendParticles(ParticleTypes.BUBBLE, (double) targetPos.getX() + level.random.nextDouble(), targetPos.getY() + 1, (double) targetPos.getZ() + level.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 0.02D);
                }
            }
        } else {
            onBubbleColumnCollision(targetEntity, targetState.getValue(PUSH));
        }
    }

    public static void update(ServerLevel level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos.MutableBlockPos bubblePos = pos.mutable();

            for (int i = 1; i < AtlantisConfig.CONFIG.maxDistanceOfPushBubbleColumn.get(); i++) {
                bubblePos.move(direction);
                BlockState previousState = level.getBlockState(bubblePos.relative(direction.getOpposite()));
                var previousFluidState = level.getFluidState(bubblePos.relative(direction.getOpposite()));
                BlockState targetState = level.getBlockState(bubblePos);

                BlockState newState = getBubbleState(previousState, previousFluidState, targetState, direction);

                if (newState == null) {
                    //System.out.println("Stopping bubble column decay at " + bubblePos + " due to null state.");
                    break;
                }

                level.setBlock(bubblePos, newState, Block.UPDATE_ALL);
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

    private static BlockState getBubbleState(BlockState previousState, FluidState fluidState, BlockState targetState, Direction curDir) {
        //System.out.println("Debugging getBubbleState method:");
        //System.out.println("Previous State: " + previousState.toString());
        //System.out.println("Target State: " + targetState.toString());
        //System.out.println("Current Direction: " + curDir.toString());
        //System.out.println("Previous Fluid State: " + fluidState.toString());

        if (targetState.is(BlockInit.BUBBLE_MAGMA.get())) {
            //System.out.println("Target is BUBBLE_MAGMA, returning null.");
            return null;
        }

        if (previousState.is(BlockInit.BUBBLE_MAGMA.get())) {
            if (isStillWater(targetState) || targetState.is(BlockInit.PUSH_BUBBLE_COLUMN.get())) {
                //System.out.println("Setting PUSH_BUBBLE_COLUMN state");
                return BlockInit.PUSH_BUBBLE_COLUMN.get().defaultBlockState().setValue(PUSH, curDir).setValue(DECAY, AtlantisConfig.CONFIG.maxDistanceOfPushBubbleColumn.get());
            }
        } else if (previousState.is(BlockInit.PUSH_BUBBLE_COLUMN.get())) {
            if (previousState.getValue(DECAY) == 0) {
                //System.out.println("Returning default WATER state");
                return Blocks.WATER.defaultBlockState();
            } else if(!previousState.getValue(PUSH).equals(curDir)) {
                return null;
            } else if (isStillWater(targetState) || targetState.is(BlockInit.PUSH_BUBBLE_COLUMN.get())) {
                //System.out.println("Setting PUSH_BUBBLE_COLUMN state with reduced decay");
                //System.out.println("Previous decay value: " + previousState.getValue(DECAY));
                return BlockInit.PUSH_BUBBLE_COLUMN.get().defaultBlockState().setValue(PUSH, curDir).setValue(DECAY, previousState.getValue(DECAY) - 1);
            }
        } else if (fluidState.isEmpty() || isStillWater(previousState)) {
            if (isStillWater(targetState) || targetState.is(BlockInit.PUSH_BUBBLE_COLUMN.get())) {
                //System.out.println("Returning default WATER state");
                return Blocks.WATER.defaultBlockState();
            }
        }

        //System.out.println("unknown state detected, returning null!");
        return null;
    }

    @Override
    public void animateTick(BlockState targetState, Level level, BlockPos targetPos, RandomSource random) {
        double x = targetPos.getX();
        double y = targetPos.getY();
        double z = targetPos.getZ();

        switch(targetState.getValue(PUSH)) {
            case UP -> level.addAlwaysVisibleParticle(ModParticleTypes.PUSH_BUBBLESTREAM_UP.get(), x + (double) random.nextFloat(), y + (double) random.nextFloat(), z + (double) random.nextFloat(), 0.0D, 0.0D, 0.0D);
            case DOWN -> level.addAlwaysVisibleParticle(ModParticleTypes.PUSH_BUBBLESTREAM_DOWN.get(), x + (double) random.nextFloat(), y + (double) random.nextFloat(), z + (double) random.nextFloat(), 0.0D, 0.0D, 0.0D);
            case NORTH -> level.addAlwaysVisibleParticle(ModParticleTypes.PUSH_BUBBLESTREAM_NORTH.get(), x + (double) random.nextFloat(), y + (double) random.nextFloat(), z + (double) random.nextFloat(), 0.0D, 0.0D, 0.0D);
            case SOUTH -> level.addAlwaysVisibleParticle(ModParticleTypes.PUSH_BUBBLESTREAM_SOUTH.get(), x + (double) random.nextFloat(), y + (double) random.nextFloat(), z + (double) random.nextFloat(), 0.0D, 0.0D, 0.0D);
            case EAST -> level.addAlwaysVisibleParticle(ModParticleTypes.PUSH_BUBBLESTREAM_EAST.get(), x + (double) random.nextFloat(), y + (double) random.nextFloat(), z + (double) random.nextFloat(), 0.0D, 0.0D, 0.0D);
            case WEST -> level.addAlwaysVisibleParticle(ModParticleTypes.PUSH_BUBBLESTREAM_WEST.get(), x + (double) random.nextFloat(), y + (double) random.nextFloat(), z + (double) random.nextFloat(), 0.0D, 0.0D, 0.0D);
        }

        if (random.nextInt(200) == 0) {
            level.playLocalSound(x, y, z, SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!level.isClientSide()) {
            update((ServerLevel) (level), pos);
        }
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion pExplosion) {
        if (!level.isClientSide()) {
            update((ServerLevel) (level), pos);
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

        if (relativeState.is(BlockInit.PUSH_BUBBLE_COLUMN.get()) && relativeState.getValue(PUSH).equals(pushDir)) {
            ;
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
    public ItemStack pickupBlock(@Nullable Player player, LevelAccessor level, BlockPos targetPos, BlockState targetState) {
        level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);
        return new ItemStack(Items.WATER_BUCKET);
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Fluids.WATER.getPickupSound();
    }

    public void onBubbleColumnSurfaceCollision(Entity targetEntity, Direction dragDir) {
        adjustEntityMovement(targetEntity, dragDir, AtlantisConfig.CONFIG.magmaAcceleration.get(), AtlantisConfig.CONFIG.magmaThreshold.get());
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
        if (curAxisDir == Direction.AxisDirection.POSITIVE) {
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

