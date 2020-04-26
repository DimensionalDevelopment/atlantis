package com.nosiphus.atlantis.blocks.base;



import com.nosiphus.atlantis.Main;
import com.nosiphus.atlantis.init.ModBlocks;
import com.nosiphus.atlantis.init.ModItems;
import com.nosiphus.atlantis.tabs.CreativeTab;
import com.nosiphus.atlantis.util.IHasModel;
import com.nosiphus.atlantis.util.Reference;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;


public class OysterShellBlock extends BlockRotatedPillar implements IHasModel
{

	public OysterShellBlock(String name, Material material) 
	{
		
		super(material);
		setUnlocalizedName(Reference.MODID + "." + name);
		setRegistryName(name);
		setCreativeTab(CreativeTab.AtlantisTab);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setSoundType(SoundType.STONE);
		setHardness(2.0F);
		setResistance(6.0F);
		setHarvestLevel("pickaxe", 1);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerModel(Item.getItemFromBlock(this), 0);
	}
	
}
