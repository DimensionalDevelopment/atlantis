package com.mystic.atlantis.blocks.base;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class LuminescentPrismarine extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public LuminescentPrismarine(Properties settings) {
        super(settings
                .strength(2.0F, 6.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE));
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
    }

    public BlockState rotate(BlockState targetState, Rotation currentRot) {
        return switch (currentRot) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (targetState.getValue(AXIS)) {
                case X -> targetState.setValue(AXIS, Direction.Axis.Z);
                case Z -> targetState.setValue(AXIS, Direction.Axis.X);
                default -> targetState;
            };
            default -> targetState;
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder< Block, BlockState> builder) {
        builder.add(AXIS);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
    }
}

