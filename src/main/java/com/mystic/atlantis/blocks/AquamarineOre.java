package com.mystic.atlantis.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraftforge.common.ToolType;

public class AquamarineOre extends Block
{
    public AquamarineOre(Properties properties)
    {
        super(properties
            .sound(SoundType.STONE)
            .harvestLevel(2)
            .harvestTool(ToolType.PICKAXE)
            .setRequiresTool()
            .hardnessAndResistance(3.0F, 15.0F)
            .setLightLevel((state) -> 2));
    }
}
