package com.nosiphus.atlantis.init;

import java.util.ArrayList;
import java.util.List;

import com.nosiphus.atlantis.Main;
import com.nosiphus.atlantis.armor.ArmorBase;
import com.nosiphus.atlantis.items.ItemAquamarineIngot;
import com.nosiphus.atlantis.items.ItemAtlanteanCrystal;
import com.nosiphus.atlantis.items.ItemBase;
import com.nosiphus.atlantis.tools.ToolAxe;
import com.nosiphus.atlantis.tools.ToolHoe;
import com.nosiphus.atlantis.tools.ToolPickaxe;
import com.nosiphus.atlantis.tools.ToolShovel;
import com.nosiphus.atlantis.tools.ToolSword;
import com.nosiphus.atlantis.util.Reference;

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
	public static final ArmorMaterial ARMOR_AQUAMARINE = EnumHelper.addArmorMaterial("armor_aquamarine", Reference.MODID + ":aquamarine", 24, new int[] {2, 6, 7, 3} , 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);
	
	//Items
	public static final Item ATLANTEAN_CRYSTAL = new ItemAtlanteanCrystal("atlantean_crystal");
	public static final Item OCEAN_STONE = new ItemBase("ocean_stone");
	public static final Item INGOT_AQUAMARINE = new ItemAquamarineIngot("ingot_aquamarine");
	public static final Item DROP_OF_ATLANTIS = new ItemBase("drop_of_atlantis");
	public static final Item ORB_OF_ATLANTIS = new ItemBase("orb_of_atlantis");
	
	//Tools
	public static final Item AXE_AQUAMARINE = new ToolAxe("axe_aquamarine", TOOL_AQUAMARINE, 2, 4);
	public static final Item HOE_AQUAMARINE = new ToolHoe("hoe_aquamarine", TOOL_AQUAMARINE);
	public static final Item PICKAXE_AQUAMARINE = new ToolPickaxe("pickaxe_aquamarine", TOOL_AQUAMARINE);
	public static final Item SHOVEL_AQUAMARINE = new ToolShovel("shovel_aquamarine", TOOL_AQUAMARINE);
	
	//Armor
	public static final Item HELMET_AQUAMARINE = new ArmorBase("helmet_aquamarine", ARMOR_AQUAMARINE, 1, EntityEquipmentSlot.HEAD);
	public static final Item CHESTPLATE_AQUAMARINE = new ArmorBase("chestplate_aquamarine", ARMOR_AQUAMARINE, 1, EntityEquipmentSlot.CHEST);
	public static final Item LEGGINGS_AQUAMARINE = new ArmorBase("leggings_aquamarine", ARMOR_AQUAMARINE, 2, EntityEquipmentSlot.LEGS);
	public static final Item BOOTS_AQUAMARINE = new ArmorBase("boots_aquamarine", ARMOR_AQUAMARINE, 1, EntityEquipmentSlot.FEET);
	
	//Weapons
	public static final Item SWORD_AQUAMARINE = new ToolSword("sword_aquamarine", TOOL_AQUAMARINE);
	
	
}
