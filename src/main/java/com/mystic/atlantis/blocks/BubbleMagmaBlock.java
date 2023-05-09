package com.mystic.atlantis.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BubbleMagmaBlock extends Block {
	
    public BubbleMagmaBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void tick(BlockState targetState, ServerLevel level, BlockPos targetPos, RandomSource random) {
        PushBubbleColumnBlock.update(level, targetPos);
    }

    @Override
    public BlockState updateShape(BlockState targetState, Direction curDir, BlockState neighborState, LevelAccessor world, BlockPos targetPos, BlockPos neighborPos) {
        if (curDir == Direction.UP || curDir == Direction.DOWN || curDir == Direction.NORTH || curDir == Direction.SOUTH || curDir == Direction.EAST || curDir == Direction.WEST && neighborState.is(Blocks.WATER)) {
            world.scheduleTick(targetPos, this, 20);
        }

        return super.updateShape(targetState, curDir, neighborState, world, targetPos, neighborPos);
    }

    @Override
    public void onPlace(BlockState targetState, Level level, BlockPos targetPos, BlockState oldState, boolean notify) {
        level.scheduleTick(targetPos, this, 20);
    }
}
