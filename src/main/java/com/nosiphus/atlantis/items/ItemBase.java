package com.nosiphus.atlantis.items;

import com.nosiphus.atlantis.Main;
import com.nosiphus.atlantis.init.ModItems;
import com.nosiphus.atlantis.tabs.AtlantisTab;
import com.nosiphus.atlantis.tabs.CreativeTab;
import com.nosiphus.atlantis.util.IHasModel;
import com.nosiphus.atlantis.util.reference;

import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name) 
	{
		setTranslationKey(reference.MODID + "." + name);
		setRegistryName(name);
		setCreativeTab(AtlantisTab.ATLANTIS_TAB);
		this.maxStackSize = 16;
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerModel(this, 0);
	}
	
}
