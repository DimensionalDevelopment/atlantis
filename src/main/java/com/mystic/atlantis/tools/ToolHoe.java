package com.mystic.atlantis.tools;

import com.mystic.atlantis.Main;
import com.mystic.atlantis.init.ModItems;
import com.mystic.atlantis.tabs.AtlantisTab;
import com.mystic.atlantis.util.IHasModel;
import com.mystic.atlantis.util.reference;

import net.minecraft.item.ItemHoe;

public class ToolHoe extends ItemHoe implements IHasModel
{

	public ToolHoe(String name, ToolMaterial material) 
	{
		super(material);
		setTranslationKey(reference.MODID + "." + name);
		setRegistryName(name);
		setCreativeTab(AtlantisTab.ATLANTIS_TAB);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerModel(this, 0);
	}

}
