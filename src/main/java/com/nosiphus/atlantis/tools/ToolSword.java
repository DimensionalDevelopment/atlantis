package com.nosiphus.atlantis.tools;

import com.nosiphus.atlantis.Main;
import com.nosiphus.atlantis.init.ModItems;
import com.nosiphus.atlantis.tabs.AtlantisTab;
import com.nosiphus.atlantis.tabs.CreativeTab;
import com.nosiphus.atlantis.util.IHasModel;
import com.nosiphus.atlantis.util.reference;

import net.minecraft.item.ItemSword;

public class ToolSword extends ItemSword implements IHasModel
{

	public ToolSword(String name, ToolMaterial material) 
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
