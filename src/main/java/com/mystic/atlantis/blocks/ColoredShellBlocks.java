package com.mystic.atlantis.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ColoredShellBlocks extends Block {
    public ColoredShellBlocks(Properties properties) {
        super(properties);
        properties
                .sound(SoundType.BONE)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
                .hardnessAndResistance(2.0F, 6.0F);
    }

    @Override
    public boolean isIn(ITag<Block> tagIn) {
        return super.isIn(tagIn);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
    }

    @Override
    public boolean matchesBlock(Block block) {
        return super.matchesBlock(block);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context);
    }

    @Override
    public StateContainer<Block, BlockState> getStateContainer() {
        return super.getStateContainer();
    }
}
