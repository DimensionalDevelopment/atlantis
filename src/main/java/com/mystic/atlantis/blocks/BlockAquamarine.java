package com.mystic.atlantis.blocks;


import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraftforge.common.ToolType;

public class BlockAquamarine extends Block
{
    public BlockAquamarine(Settings properties) {
        super(properties
                .sounds(BlockSoundGroup.METAL)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .requiresTool()
                .strength(3.0F, 15.0F));
    }
}
