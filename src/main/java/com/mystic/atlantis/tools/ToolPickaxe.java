package com.mystic.atlantis.tools;

import com.mystic.atlantis.Main;
import com.mystic.atlantis.init.ModItems;
import com.mystic.atlantis.tabs.AtlantisTab;
import com.mystic.atlantis.util.IHasModel;
import com.mystic.atlantis.util.Reference;

import net.minecraft.item.ItemPickaxe;

public class ToolPickaxe extends ItemPickaxe implements IHasModel
{

	public ToolPickaxe(String name, ToolMaterial material)
	{
		super(material);
		setTranslationKey(Reference.MODID + "." + name);
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
