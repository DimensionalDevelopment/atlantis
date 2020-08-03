package com.nosiphus.atlantis.armor;

import com.nosiphus.atlantis.Main;
import com.nosiphus.atlantis.init.ModItems;
import com.nosiphus.atlantis.tabs.AtlantisTab;
import com.nosiphus.atlantis.tabs.CreativeTab;
import com.nosiphus.atlantis.util.IHasModel;
import com.nosiphus.atlantis.util.reference;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ArmorBase extends ItemArmor implements IHasModel
{

	public ArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
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
