package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.blocks.BlockBase;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockAquamarine extends BlockBase 
{
	public BlockAquamarine(String name, Material material) 
	{
		super(name, material);
		setSoundType(SoundType.METAL);
		setHardness(3.0F);
		setResistance(15.0F);
		setHarvestLevel("pickaxe", 2);
	}
}
