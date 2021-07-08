package com.mystic.atlantis.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;

import java.util.Random;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import static net.minecraft.state.property.Properties.FACING;

public class ColoredShellBlocks extends Block {

    private static final Direction[] GENERATE_DIRECTIONS = new Direction[]{Direction.WEST, Direction.EAST, Direction.SOUTH, Direction.NORTH, Direction.UP, Direction.DOWN};

    public ColoredShellBlocks(FabricBlockSettings properties) {
        super(properties
                .sounds(BlockSoundGroup.BONE)
                .breakByTool(FabricToolTags.PICKAXES, 2)
                .requiresTool()
                .strength(3.0F, 7.0F));

        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(FACING, context.getSide());
    }


    public static Direction getGenerationDirection(Random rand) {
        return Util.getRandom(GENERATE_DIRECTIONS, rand);
    }

}
