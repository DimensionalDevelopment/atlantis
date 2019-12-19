package com.mystic.dimensionatlantis.init;

import java.util.ArrayList;
import java.util.List;

import com.mystic.dimensionatlantis.items.ItemAquamarineIngot;
import com.mystic.dimensionatlantis.items.ItemAtlanteanCrystal;
import com.mystic.dimensionatlantis.items.ItemBase;

import net.minecraft.item.Item;

public class ModItems
{

	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item ATLANTEAN_CRYSTAL = new ItemAtlanteanCrystal("atlantean_crystal");
	public static final Item OCEAN_STONE = new ItemBase("ocean_stone");
	public static final Item INGOT_AQUAMARINE = new ItemAquamarineIngot("ingot_aquamarine");
	public static final Item DROP_OF_ATLANTIS = new ItemBase("drop_of_atlantis");
	public static final Item ORB_OF_ATLANTIS = new ItemBase("orb_of_atlantis");
}
