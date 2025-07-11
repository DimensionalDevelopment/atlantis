package com.mystic.atlantis.blocks.pots;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CelenPotBlock extends PotBlock {
    public CelenPotBlock(Properties pProperties) {
        super(pProperties);
    }
    protected static final VoxelShape SHAPE_X = Block.box(2.0D, 0.0D, 5.0D, 14.0D, 16.0D, 11.0D);
    protected static final VoxelShape SHAPE_Z = Block.box(5.0D, 0.0D, 2.0D, 11.0D, 16.0D, 14.0D);
    private static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if(pState.getValue(HORIZONTAL_FACING).getAxis() == Direction.Axis.X) {
            return SHAPE_X;
        } else {
            return SHAPE_Z;
        }
    }
}
