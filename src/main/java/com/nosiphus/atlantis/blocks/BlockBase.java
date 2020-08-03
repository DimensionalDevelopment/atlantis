package com.nosiphus.atlantis.blocks;

import com.nosiphus.atlantis.Main;
import com.nosiphus.atlantis.init.ModBlocks;
import com.nosiphus.atlantis.init.ModItems;
import com.nosiphus.atlantis.tabs.AtlantisTab;
import com.nosiphus.atlantis.tabs.CreativeTab;
import com.nosiphus.atlantis.util.IHasModel;
import com.nosiphus.atlantis.util.reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel {

	public BlockBase(String name, Material material) {
		
		super(material);
		setTranslationKey(reference.MODID + "." + name);
		setRegistryName(name);
		setCreativeTab(AtlantisTab.ATLANTIS_TAB);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerModel(Item.getItemFromBlock(this), 0);
	}
	
}