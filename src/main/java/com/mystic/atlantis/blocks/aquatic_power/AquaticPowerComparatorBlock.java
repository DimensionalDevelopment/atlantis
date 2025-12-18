package com.mystic.atlantis.blocks.aquatic_power;

import com.mystic.atlantis.blocks.blockentities.AquaticComparatorEntity;
import com.mystic.atlantis.blocks.blockentities.AtlanteanPortalBlockEntity;
import com.mystic.atlantis.blocks.plants.Seabloom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class AquaticPowerComparatorBlock extends ComparatorBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public AquaticPowerComparatorBlock(Properties settings) {
        super(settings.instabreak().sound(SoundType.WOOD));
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(MODE, ComparatorMode.COMPARE).setValue(WATERLOGGED, true));
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153196_, BlockState p_153197_) {
        return new AquaticComparatorEntity(p_153196_, p_153197_);
    }

    @Override
    public FluidState getFluidState(BlockState targetState) {
        return targetState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(targetState);
    }

    @Override
    public boolean canSurvive(BlockState targetState, LevelReader reader, BlockPos targetPos) {
        if(reader.getFluidState(targetPos).is(FluidTags.WATER)) {
            return canSupportRigidBlock(reader, targetPos.below());
        } else {
            return false;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, MODE, POWERED);
    }
}
