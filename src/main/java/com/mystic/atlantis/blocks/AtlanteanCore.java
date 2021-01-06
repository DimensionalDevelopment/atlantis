package com.mystic.atlantis.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraftforge.common.ToolType;

public class AtlanteanCore extends Block
{

    public AtlanteanCore(Properties properties) {
        super(properties
                .sound(SoundType.METAL)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .setLightLevel((state) -> 7)
                .hardnessAndResistance(4.0F, 25.0F));
    }
}
