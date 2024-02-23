package com.mystic.atlantis.init;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.IPlantable;

public class AtlanteanFireMelonHead extends GrowingPlantHeadBlock implements LiquidBlockContainer, IPlantable {
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);
    private static final double GROW_PER_TICK_PROBABILITY = 0.14;

    protected AtlanteanFireMelonHead(Properties arg) {
        super(arg.noCollission().noOcclusion().randomTicks(), Direction.UP, SHAPE, true, 0.14);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 7));
    }

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return simpleCodec(AtlanteanFireMelonHead::new);
    }

    @Override
    public void randomTick(BlockState arg, ServerLevel arg2, BlockPos arg3, RandomSource random) {
        if (arg.getValue(AGE) < 8 && CommonHooks.onCropsGrowPre(arg2, arg3.relative(this.growthDirection), arg2.getBlockState(arg3.relative(this.growthDirection)), random.nextDouble() < GROW_PER_TICK_PROBABILITY)) {
            BlockPos blockpos = arg3.relative(this.growthDirection);
            if (this.canGrowInto(arg2.getBlockState(blockpos))) {
                arg2.setBlockAndUpdate(blockpos, this.getGrowIntoState(arg, arg2.random));
                CommonHooks.onCropsGrowPost(arg2, blockpos, arg2.getBlockState(blockpos));
            }
        }
    }

    @Override
    public @NotNull BlockState getMaxAgeState(BlockState arg) {
            return arg.setValue(AGE, 7);
    }

    @Override
    public boolean isMaxAge(BlockState arg) {
        return arg.getValue(AGE) == 7;
    }

    @Override
    protected boolean canGrowInto(BlockState arg) {
        return arg.is(Blocks.WATER);
    }

    @Override
    protected Block getBodyBlock() {
        return BlockInit.ATLANTEAN_FIRE_MELON_STEM.get();
    }

    @Override
    public boolean canAttachTo(BlockState arg) {
        return !arg.is(Blocks.MAGMA_BLOCK);
    }

    @Override
    public boolean canPlaceLiquid(@org.jetbrains.annotations.Nullable Player pPlayer, BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor arg, BlockPos arg2, BlockState arg3, FluidState arg4) {
        return false;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext arg) {
        FluidState fluidState = arg.getLevel().getFluidState(arg.getClickedPos());
        return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8 ? super.getStateForPlacement(arg) : null;
    }

    @Override
    public FluidState getFluidState(BlockState arg) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    public BlockState getPlant(BlockGetter arg, BlockPos arg2) {
        return this.defaultBlockState();
    }
}
