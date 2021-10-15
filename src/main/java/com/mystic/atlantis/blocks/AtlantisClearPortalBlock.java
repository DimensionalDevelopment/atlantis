package com.mystic.atlantis.blocks;

import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import static com.mystic.atlantis.blocks.plants.UnderwaterFlower.WATERLOGGED;

public class AtlantisClearPortalBlock extends CustomPortalBlock implements Waterloggable {
    public static final EnumProperty<Direction.Axis> AXIS = CustomPortalBlock.AXIS;
    protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public AtlantisClearPortalBlock(Settings settings) {
        super(settings
                .noCollision()
                .nonOpaque()
                .sounds(BlockSoundGroup.GLASS)
                .strength(0.2F, 0.4F)
        );
        this.setDefaultState(this.stateManager.getDefaultState().with(AXIS, Direction.Axis.X).with(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(AXIS)) {
            case Z:
                return Z_SHAPE;
            case X:
            default:
                return X_SHAPE;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS, WATERLOGGED);
    }
}