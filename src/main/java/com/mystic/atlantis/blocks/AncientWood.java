package com.mystic.atlantis.blocks;

import com.mystic.atlantis.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class AncientWood extends Block {

    public AncientWood(Properties properties) {
        super(properties
                .sound(SoundType.WOOD)
                .harvestLevel(2)
                .harvestTool(ToolType.AXE)
                .setRequiresTool()
                .hardnessAndResistance(3.0F, 6.0F));

    }
}
