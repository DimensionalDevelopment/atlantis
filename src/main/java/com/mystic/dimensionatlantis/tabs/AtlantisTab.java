package com.mystic.dimensionatlantis.tabs;


import com.mystic.dimensionatlantis.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class AtlantisTab extends CreativeTabs
{
	public AtlantisTab(String label) 
	{
		super("dimensionatlantistab");
		this.setBackgroundImageName("dimensionatlantis.png");
	}
	
	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(ModItems.OCEAN_STONE);
	}
	
	@Override
    public boolean hasSearchBar()
	{
        return false;
    }
}
