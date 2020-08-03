package com.nosiphus.atlantis.blocks.base;

import com.nosiphus.atlantis.blocks.BlockBase;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class AtlanteanCore extends BlockBase
{
	public AtlanteanCore(String name, Material material) 
	{
		
		super(name, material);
		setSoundType(SoundType.METAL);
		setHardness(4.0F);
		setResistance(25.0F);
		setHarvestLevel("pickaxe", 2);
		setLightLevel(8.0F);
	}
}
