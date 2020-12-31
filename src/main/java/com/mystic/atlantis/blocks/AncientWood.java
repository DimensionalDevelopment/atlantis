package com.mystic.atlantis.blocks;

import com.mystic.atlantis.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class AncientWood extends Block{
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public AncientWood(Properties properties) {
        super(properties);
        properties
                .sound(SoundType.WOOD)
                .harvestLevel(2)
                .harvestTool(ToolType.AXE);

    }
}
