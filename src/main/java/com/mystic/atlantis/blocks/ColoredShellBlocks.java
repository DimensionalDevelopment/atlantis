package com.mystic.atlantis.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraftforge.common.ToolType;

import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.FACING;

public class ColoredShellBlocks extends Block {

    private static final Direction[] GENERATE_DIRECTIONS = new Direction[]{Direction.WEST, Direction.EAST, Direction.SOUTH, Direction.NORTH};

    public ColoredShellBlocks(Properties properties) {
        super(properties
                .sound(SoundType.BONE)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .hardnessAndResistance(3.0F, 7.0F));

        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing());
    }


    public static Direction getGenerationDirection(Random rand) {
        return Util.getRandomObject(GENERATE_DIRECTIONS, rand);
    }

}
