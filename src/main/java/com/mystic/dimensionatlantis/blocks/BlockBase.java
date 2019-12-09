package com.mystic.dimensionatlantis.blocks;

import com.mystic.dimensionatlantis.Main;
import com.mystic.dimensionatlantis.init.ModBlocks;
import com.mystic.dimensionatlantis.init.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;

public class BlockBase extends Block
{
	public BlockBase(String name, Material material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.ATLANTISTAB);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
}
