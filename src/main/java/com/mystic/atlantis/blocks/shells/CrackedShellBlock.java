package com.mystic.atlantis.blocks.shells;

import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class CrackedShellBlock extends Block {
    private static final Direction[] GENERATE_DIRECTIONS = new Direction[]{Direction.WEST, Direction.EAST, Direction.SOUTH, Direction.NORTH, Direction.UP, Direction.DOWN};

    public CrackedShellBlock(Properties properties) {
        super(properties
                .sound(SoundType.BONE_BLOCK)
                .requiresCorrectToolForDrops()
                .strength(2.5F, 5.0F));

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    public static Direction getGenerationDirection(RandomSource rand) {
        return Util.getRandom(GENERATE_DIRECTIONS, rand);
    }

}
