package com.mystic.dimensionatlantis.items;

import com.mystic.dimensionatlantis.Main;
import com.mystic.dimensionatlantis.init.ModItems;
import com.mystic.dimensionatlantis.util.IHasModel;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.ATLANTISTAB);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels()
    {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
