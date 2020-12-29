package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.blocks.BlockBase;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

public class PearlBlock extends BlockBase {
	
	public PearlBlock(String name, Material material) 
	{
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(2.0F);
		setResistance(5.0F);
		setLightLevel(0.5F); //Light Value has to be 0 to 1!
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}
	
	@Override
	public boolean isTranslucent(IBlockState state) 
	{
		return true;
	}
	
	public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
	
	protected boolean canSilkHarvest()
    {
        return true;
    }
}
