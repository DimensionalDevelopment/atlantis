package com.nosiphus.atlantis.items;

import com.nosiphus.atlantis.Main;
import com.nosiphus.atlantis.init.ModItems;
import com.nosiphus.atlantis.tabs.CreativeTab;
import com.nosiphus.atlantis.util.IHasModel;
import com.nosiphus.atlantis.util.Reference;

import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name) 
	{
		setUnlocalizedName(Reference.MODID + "." + name);
		setRegistryName(name);
		setCreativeTab(CreativeTab.AtlantisTab);
		this.maxStackSize = 16;
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerModel(this, 0);
	}
	
}
