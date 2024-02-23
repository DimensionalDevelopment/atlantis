package com.mystic.atlantis.blocks.power.atlanteanstone;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;

import java.util.Iterator;
import java.util.List;

public class AtlanteanPressurePlateBlock extends PressurePlateBlock implements SimpleWaterloggedBlock {
    private static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;
    private static final BooleanProperty POWERED;

    public AtlanteanPressurePlateBlock(Properties settings) {
        super( BlockSetType.OAK, settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected int getSignalForState(BlockState targetState) {
        return targetState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected BlockState setSignalForState(BlockState targetState, int power) {
        return targetState.setValue(POWERED, power > 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, WATERLOGGED);
    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }
}
