package com.mystic.dimensionatlantis.armour;


import com.mystic.dimensionatlantis.Main;
import com.mystic.dimensionatlantis.init.ModItems;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ArmourBase extends ItemArmor 
{
	public ArmourBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.ATLANTISTAB);
		setMaxStackSize(1);
		
		ModItems.ITEMS.add(this);
	}
}



	
