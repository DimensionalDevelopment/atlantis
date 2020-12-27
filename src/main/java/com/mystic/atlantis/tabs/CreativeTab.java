package com.mystic.atlantis.tabs;

import com.mystic.atlantis.init.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs {

	public CreativeTab(String label){
		super(label);
		this.setBackgroundImageName("atlantis.png");
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(Item.getItemFromBlock(ModBlocks.OCEAN_LANTERN));
	}
}

