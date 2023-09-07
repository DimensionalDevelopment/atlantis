package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.Block;

public interface ExtendedBlockEntity {
    void addAdditionalValidBlock(Block... blocks);
}
