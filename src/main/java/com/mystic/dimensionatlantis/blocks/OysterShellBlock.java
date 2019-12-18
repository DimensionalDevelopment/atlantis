package com.mystic.dimensionatlantis.blocks;



import com.mystic.dimensionatlantis.Main;
import com.mystic.dimensionatlantis.init.ModBlocks;
import com.mystic.dimensionatlantis.init.ModItems;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;


public class OysterShellBlock extends BlockRotatedPillar
{

	public OysterShellBlock(String name, Material material) 
	{
		
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.ATLANTISTAB);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setSoundType(SoundType.STONE);
		setHardness(2.0F);
		setResistance(6.0F);
		setHarvestLevel("pickaxe", 1);
	}
}
