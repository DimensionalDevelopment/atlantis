package com.mystic.dimensionatlantis.items;

import com.mystic.dimensionatlantis.Main;
import com.mystic.dimensionatlantis.init.ModItems;
import net.minecraft.item.Item;

public class ItemBase extends Item {

	public ItemBase(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.ATLANTISTAB);
		this.maxStackSize = 16;
		
		ModItems.ITEMS.add(this);
	}
}
