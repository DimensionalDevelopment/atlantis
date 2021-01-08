package com.mystic.atlantis.blocks;

import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraftforge.common.ToolType;

public class AtlanteanCore extends Block
{

    public AtlanteanCore(Settings properties) {
        super(properties
                .sounds(BlockSoundGroup.METAL)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .requiresTool()
                .luminance((state) -> 7)
                .strength(4.0F, 25.0F));
    }
}
