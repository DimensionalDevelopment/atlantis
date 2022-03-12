package com.mystic.atlantis.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;

public class OysterShellBlock extends PillarBlock
{
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;

    public OysterShellBlock(AbstractBlock.Settings properties) {
        super(properties
                .strength(2.0F, 6.0F)
//                .breakByTool(FabricToolTags.PICKAXES, 1) //TODO: Update
                .requiresTool()
                .sounds(BlockSoundGroup.STONE));
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    public BlockState rotate(BlockState state, BlockRotation rot) {
        switch(rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                return switch (state.get(AXIS)) {
                    case X -> state.with(AXIS, Direction.Axis.Z);
                    case Z -> state.with(AXIS, Direction.Axis.X);
                    default -> state;
                };
            default:
                return state;
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(AXIS, context.getSide().getAxis());
    }
}
