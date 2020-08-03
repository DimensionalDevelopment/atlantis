package com.nosiphus.atlantis.tabs;

import com.nosiphus.atlantis.init.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/*public class CreativeTab extends CreativeTabs{



	public static CreativeTabs AtlantisTab = new CreativeTabs("Atlantis")
	{

		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(Item.getItemFromBlock(ModBlocks.OCEAN_LANTERN));
		}

	}.setBackgroundImageName("atlantis.png");

	@Override
	public ItemStack createIcon() {
		return null;
	}
}
 */

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

