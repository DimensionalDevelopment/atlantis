package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.blocks.BlockBase;

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
		setLightLevel(0.7F); //Light Value has to be 0 to 1!
	}

	protected boolean canSilkHarvest()
	    {
	        return true;
	    }
}
