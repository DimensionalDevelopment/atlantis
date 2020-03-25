package com.mystic.dimensionatlantis.armour;


import com.mystic.dimensionatlantis.Main;
import com.mystic.dimensionatlantis.init.ModItems;
import com.mystic.dimensionatlantis.items.models.HelmetAquamarine;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmourBase1 extends ItemArmor 
{
	public ArmourBase1(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.ATLANTISTAB);
		setMaxStackSize(1);
		
		ModItems.ITEMS.add(this);
	}
	
	 
	    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default){
	        if(itemStack!=ItemStack.EMPTY){
	            if(itemStack.getItem() instanceof ItemArmor){
	                HelmetAquamarine model = new HelmetAquamarine();
	                model.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
	                
	                model.isChild = _default.isChild;
	                model.isRiding = _default.isRiding;
	                model.isSneak = _default.isSneak;
	                model.rightArmPose = _default.rightArmPose;
	                model.leftArmPose = _default.leftArmPose;
	                return model;
	            }
	        }
	        return null;
	    }
	}
