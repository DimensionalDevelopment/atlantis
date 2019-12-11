package com.mystic.dimensionatlantis.blocks.fluids;

import com.mystic.dimensionatlantis.init.ModBlocks;
import com.mystic.dimensionatlantis.init.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockDenseWaterFluid extends BlockFluidClassic
{
	public BlockDenseWaterFluid(String name, Fluid fluid, Material material) 
	{
		super(fluid, material);
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) 
	{
		return EnumBlockRenderType.MODEL;
	}
	
	
	
}
