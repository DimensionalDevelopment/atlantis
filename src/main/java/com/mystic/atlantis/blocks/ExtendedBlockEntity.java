package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;

public interface ExtendedBlockEntity {
    void addAdditionalValidBlock(Block... blocks);
}
