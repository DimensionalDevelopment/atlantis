package com.mystic.atlantis.items;

import com.mystic.atlantis.Main;
import com.mystic.atlantis.init.ModItems;
import com.mystic.atlantis.tabs.AtlantisTab;
import com.mystic.atlantis.util.IHasModel;
import com.mystic.atlantis.util.reference;

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
