package com.mystic.atlantis.blocks;

import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraftforge.common.ToolType;

public class AncientWood extends Block {

    public AncientWood(Settings properties) {
        super(properties
                .sounds(BlockSoundGroup.WOOD)
                .harvestLevel(2)
                .harvestTool(ToolType.AXE)
                .requiresTool()
                .strength(3.0F, 6.0F));

    }
}
