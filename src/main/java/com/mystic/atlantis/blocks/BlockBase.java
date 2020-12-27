package com.mystic.atlantis.blocks;

import com.mystic.atlantis.Main;
import com.mystic.atlantis.init.ModBlocks;
import com.mystic.atlantis.init.ModItems;
import com.mystic.atlantis.tabs.AtlantisTab;
import com.mystic.atlantis.util.IHasModel;
import com.mystic.atlantis.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import java.util.Objects;

public abstract class BlockBase extends Block implements IHasModel {

	public BlockBase(String name, Material material) {
		
		super(material);
		setTranslationKey(Reference.MODID + "." + name);
		setRegistryName(name);
		setCreativeTab(AtlantisTab.ATLANTIS_TAB);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
		
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerModel(Item.getItemFromBlock(this), 0);
	}
}