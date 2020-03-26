package com.mystic.dimensionatlantis.init;

import java.util.ArrayList;
import java.util.List;

import com.mystic.dimensionatlantis.Main;
import com.mystic.dimensionatlantis.armour.ArmourBase;
import com.mystic.dimensionatlantis.items.ItemAquamarineIngot;
import com.mystic.dimensionatlantis.items.ItemAtlanteanCrystal;
import com.mystic.dimensionatlantis.items.ItemBase;
import com.mystic.dimensionatlantis.tools.ToolAxe;
import com.mystic.dimensionatlantis.tools.ToolHoe;
import com.mystic.dimensionatlantis.tools.ToolPickaxe;
import com.mystic.dimensionatlantis.tools.ToolShield;
import com.mystic.dimensionatlantis.tools.ToolShovel;
import com.mystic.dimensionatlantis.tools.ToolSword;
import com.mystic.dimensionatlantis.util.Reference;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems
{

	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Material
	public static final ToolMaterial TOOL_AQUAMARINE = EnumHelper.addToolMaterial("tool_aquamarine", 2, 700, 7F, 2.5F, 12);
	public static final ArmorMaterial ARMOUR_AQUAMARINE = EnumHelper.addArmorMaterial("armour_aquamarine", Reference.MOD_ID + ":aquamarine", 24, new int[] {2, 6, 7, 3} , 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);
	
	//Items
	public static final Item ATLANTEAN_CRYSTAL = new ItemAtlanteanCrystal("atlantean_crystal");
	public static final Item OCEAN_STONE = new ItemBase("ocean_stone");
	public static final Item INGOT_AQUAMARINE = new ItemAquamarineIngot("ingot_aquamarine");
	public static final Item DROP_OF_ATLANTIS = new ItemBase("drop_of_atlantis");
	public static final Item ORB_OF_ATLANTIS = new ItemBase("orb_of_atlantis");
	
	//Tools
	public static final Item AXE_AQUAMARINE = new ToolAxe("axe_aquamarine", TOOL_AQUAMARINE);
	public static final Item HOE_AQUAMARINE = new ToolHoe("hoe_aquamarine", TOOL_AQUAMARINE);
	public static final Item PICKAXE_AQUAMARINE = new ToolPickaxe("pickaxe_aquamarine", TOOL_AQUAMARINE);
	public static final Item SHOVEL_AQUAMARINE = new ToolShovel("shovel_aquamarine", TOOL_AQUAMARINE);
	
	//Armour
	public static final Item HELMET_AQUAMARINE = new ArmourBase("helmet_aquamarine", ARMOUR_AQUAMARINE, 1, EntityEquipmentSlot.HEAD);
	public static final Item CHESTPLATE_AQUAMARINE = new ArmourBase("chestplate_aquamarine", ARMOUR_AQUAMARINE,  1, EntityEquipmentSlot.CHEST);
	public static final Item LEGGINGS_AQUAMARINE = new ArmourBase("leggings_aquamarine", ARMOUR_AQUAMARINE, 2, EntityEquipmentSlot.LEGS);
	public static final Item BOOTS_AQUAMARINE = new ArmourBase("boots_aquamarine", ARMOUR_AQUAMARINE, 1, EntityEquipmentSlot.FEET);
	
	//Weapons
	public static final Item SWORD_AQUAMARINE = new ToolSword("sword_aquamarine", TOOL_AQUAMARINE);
	public static final Item ATLANTEAN_SHIELD = new ToolShield("atlantean_shield", Main.ATLANTISTAB, 450);
	
	
}
