package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolActions;

import javax.annotation.Nullable;

import static com.mystic.atlantis.blocks.base.AtlanteanWoodDoorBlock.WATERLOGGED;

public class AtlanteanFireMelonSpikedFruitBlock extends HorizontalDirectionalBlock implements BonemealableBlock, SimpleWaterloggedBlock {
    public static final IntegerProperty AGE_4 = IntegerProperty.create("age", 0, 4);
    public static final IntegerProperty AGE = AGE_4;
    public static final BooleanProperty SPIKED = BooleanProperty.create("spiked");
    protected static final VoxelShape[] EAST_AABB = new VoxelShape[]{Block.box(11.0D, 7.0D, 6.0D, 15.0D, 12.0D, 10.0D), Block.box(9.0D, 5.0D, 5.0D, 15.0D, 12.0D, 11.0D), Block.box(7.0D, 3.0D, 4.0D, 15.0D, 12.0D, 12.0D)};
    protected static final VoxelShape[] WEST_AABB = new VoxelShape[]{Block.box(1.0D, 7.0D, 6.0D, 5.0D, 12.0D, 10.0D), Block.box(1.0D, 5.0D, 5.0D, 7.0D, 12.0D, 11.0D), Block.box(1.0D, 3.0D, 4.0D, 9.0D, 12.0D, 12.0D)};
    protected static final VoxelShape[] NORTH_AABB = new VoxelShape[]{Block.box(6.0D, 7.0D, 1.0D, 10.0D, 12.0D, 5.0D), Block.box(5.0D, 5.0D, 1.0D, 11.0D, 12.0D, 7.0D), Block.box(4.0D, 3.0D, 1.0D, 12.0D, 12.0D, 9.0D)};
    protected static final VoxelShape[] SOUTH_AABB = new VoxelShape[]{Block.box(6.0D, 7.0D, 11.0D, 10.0D, 12.0D, 15.0D), Block.box(5.0D, 5.0D, 9.0D, 11.0D, 12.0D, 15.0D), Block.box(4.0D, 3.0D, 7.0D, 12.0D, 12.0D, 15.0D)};

    public AtlanteanFireMelonSpikedFruitBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AGE, 0).setValue(SPIKED, true).setValue(WATERLOGGED, Boolean.TRUE));
    }

    @Override
    public boolean isRandomlyTicking(BlockState targetState) {
        return targetState.getValue(AGE) < 4;
    }

    @Override
    public void randomTick(BlockState targetState, ServerLevel level, BlockPos targetPos, RandomSource random) {
        int age = targetState.getValue(AGE);
        
        if (age < 4 && ForgeHooks.onCropsGrowPre(level, targetPos, targetState, level.random.nextInt(4) == 0)) {
            level.setBlock(targetPos, targetState.setValue(AGE, age + 1), 4);
            ForgeHooks.onCropsGrowPost(level, targetPos, targetState);
        }

    }

    public HolderSet<Block> getAir(){
        Holder<Block> airHolderSet = Holder.direct(Blocks.AIR);
        return HolderSet.direct(airHolderSet);
    }

    public boolean isWaterAt(LevelReader reader, BlockPos targetPos) {
        return !reader.getBlockState(targetPos).is(getAir());
    }

    @Override
    public FluidState getFluidState(BlockState targetState) {
        return targetState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(targetState);
    }

    public boolean canSurvive(BlockState targetState, LevelReader reader, BlockPos targetPos) {
        BlockState relativeState = reader.getBlockState(targetPos.relative(targetState.getValue(FACING)));
        
        if (isWaterAt(reader, targetPos)){
            return relativeState.is(BlockInit.ATLANTEAN_FIRE_MELON_STEM.get());
        } else {
            return false;
        }
    }

    @Override
    public VoxelShape getShape(BlockState targetState, BlockGetter getter, BlockPos targetPos, CollisionContext context) {
        switch(targetState.getValue(FACING)) {
            case SOUTH:
                return SOUTH_AABB[0];
            case NORTH:
            default:
                return NORTH_AABB[0];
            case WEST:
                return WEST_AABB[0];
            case EAST:
                return EAST_AABB[0];
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState targetState = this.defaultBlockState();
        LevelReader reader = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Direction[] lookingDirections = context.getNearestLookingDirections();
        int validLookingDirections = lookingDirections.length;

        for(int i = 0; i < validLookingDirections; ++i) {
            Direction curLookingDirection = lookingDirections[i];
            if (curLookingDirection.getAxis().isHorizontal()) {
                targetState = targetState.setValue(FACING, curLookingDirection);
                if (targetState.canSurvive(reader, clickedPos)) {
                    return targetState;
                }
            }
        }

        return null;
    }

    @Override
    public BlockState updateShape(BlockState targetState, Direction curDir, BlockState neighbourState, LevelAccessor accessor, BlockPos targetPos, BlockPos neighbourPos) {
        return curDir == targetState.getValue(FACING) && !targetState.canSurvive(accessor, targetPos) ? Blocks.WATER.defaultBlockState() : super.updateShape(targetState, curDir, neighbourState, accessor, targetPos, neighbourPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(AGE) < 4;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos targetPos, BlockState targetState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos targetPos, BlockState targetState) {
        level.setBlock(targetPos, targetState.setValue(AGE, targetState.getValue(AGE) + 1), 2);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AGE, SPIKED, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState targetState, BlockGetter getter, BlockPos targetPos, PathComputationType type) {
        return false;
    }

    @Override
    public InteractionResult use(BlockState targetState, Level level, BlockPos targetPos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack mainHandStack = player.getItemInHand(hand);
        
        if (mainHandStack.canPerformAction(ToolActions.SHEARS_CARVE) && targetState.getValue(SPIKED)) {
            if (!level.isClientSide) {
                Direction resultDir = result.getDirection();
                Direction oppositeDir = resultDir.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : resultDir;
                level.playSound(null, targetPos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(targetPos, targetState.setValue(SPIKED, false), 11);
                ItemEntity fireMelonSpikeItemEntity = new ItemEntity(
                        level,
                        (double)targetPos.getX() + 0.5 + (double)oppositeDir.getStepX() * 0.65,
                        (double)targetPos.getY() + 0.1,
                        (double)targetPos.getZ() + 0.5 + (double)oppositeDir.getStepZ() * 0.65,
                        new ItemStack(ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get(), 1)
                );
                fireMelonSpikeItemEntity.setDeltaMovement(0.05 * (double)oppositeDir.getStepX() + level.random.nextDouble() * 0.02, 0.05, 0.05 * (double)oppositeDir.getStepZ() + level.random.nextDouble() * 0.02);
                level.addFreshEntity(fireMelonSpikeItemEntity);
                mainHandStack.hurtAndBreak(1, player, arg2x -> arg2x.broadcastBreakEvent(hand));
                level.gameEvent(player, GameEvent.SHEAR, targetPos);
                player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use(targetState, level, targetPos, player, hand, result);
        }
    }

}
