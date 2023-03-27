package com.mystic.atlantis.blocks;

import static com.mystic.atlantis.blocks.plants.UnderwaterFlower.WATERLOGGED;

import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AtlantisClearPortalBlock extends CustomPortalBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction.Axis> AXIS = CustomPortalBlock.AXIS;
    protected static final VoxelShape X_SHAPE = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_SHAPE = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
    protected static final VoxelShape Y_SHAPE = Block.box (0.0D, 6.0D, 0.0D, 16.0D, 10.0D, 16.0D);

    public AtlantisClearPortalBlock(Properties settings) {
        super(settings
                .noCollission()
                .noOcclusion()
                .sound(SoundType.GLASS)
                .strength(0.2F, 0.4F)
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(AXIS)) {
            case Z:
                return Z_SHAPE;
            case Y:
                return Y_SHAPE;
            case X:
            default:
                return X_SHAPE;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, WATERLOGGED);
    }
}