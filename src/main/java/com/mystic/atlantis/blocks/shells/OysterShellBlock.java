package com.mystic.atlantis.blocks.shells;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class OysterShellBlock extends RotatedPillarBlock {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public OysterShellBlock(BlockBehaviour.Properties properties) {
        super(properties
                .strength(2.0F, 6.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.BONE_BLOCK));
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
    }

    public BlockState rotate(BlockState targetState, Rotation currentRot) {
        switch(currentRot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                return switch (targetState.getValue(AXIS)) {
                    case X -> targetState.setValue(AXIS, Direction.Axis.Z);
                    case Z -> targetState.setValue(AXIS, Direction.Axis.X);
                    default -> targetState;
                };
            default:
                return targetState;
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
    }
}
