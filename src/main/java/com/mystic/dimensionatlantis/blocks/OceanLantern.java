package com.mystic.dimensionatlantis.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class OceanLantern extends BlockBase 
{

	public OceanLantern(String name, Material material) 
	{
		super(name, material);
		setSoundType(SoundType.GLASS);
		setHardness(2.0F);
		setResistance(10.0F);
		setLightLevel(5.0F);
	}

	 protected boolean canSilkHarvest()
	    {
	        return true;
	    }
}
