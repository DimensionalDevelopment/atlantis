package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.Main;
import com.mystic.atlantis.init.ModBlocks;
import com.mystic.atlantis.init.ModItems;
import com.mystic.atlantis.tabs.AtlantisTab;
import com.mystic.atlantis.util.IHasModel;
import com.mystic.atlantis.util.Reference;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class OysterShellBlock extends BlockRotatedPillar implements IHasModel {

	public OysterShellBlock(String name, Material material)
	{
		super(material);
		setTranslationKey(Reference.MODID + "." + name);
		setRegistryName(name);
		setCreativeTab(AtlantisTab.ATLANTIS_TAB);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setSoundType(SoundType.STONE);
		setHardness(2.0F);
		setResistance(6.0F);
		setHarvestLevel("pickaxe", 1);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerModel(Item.getItemFromBlock(this), 0);
	}
	
}
