package com.mystic.atlantis.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;

public class AtlanteanPrismarine extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;

    public AtlanteanPrismarine(AbstractBlock.Settings properties) {
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
                switch((Direction.Axis)state.get(AXIS)) {
                    case X:
                        return state.with(AXIS, Direction.Axis.Z);
                    case Z:
                        return state.with(AXIS, Direction.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    protected void appendProperties(StateManager.Builder< Block, BlockState> builder) {
        builder.add(AXIS);
    }

    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(AXIS, context.getSide().getAxis());
    }
}

