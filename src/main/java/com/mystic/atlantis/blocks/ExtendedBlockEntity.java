package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;

public interface ExtendedBlockEntity {
    public void addAdditionalValidBlock(Block... blocks);
}
