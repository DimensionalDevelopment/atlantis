package com.mystic.atlantis.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraftforge.common.ToolType;

public class BlockAquamarine extends Block
{
    public BlockAquamarine(Properties properties) {
        super(properties
                .sound(SoundType.METAL)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .hardnessAndResistance(3.0F, 15.0F));
    }
}
