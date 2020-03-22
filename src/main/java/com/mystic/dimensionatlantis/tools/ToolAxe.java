package com.mystic.dimensionatlantis.tools;

import java.util.Set;

import com.google.common.collect.Sets;
import com.mystic.dimensionatlantis.Main;
import com.mystic.dimensionatlantis.init.ModBlocks;
import com.mystic.dimensionatlantis.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ToolAxe extends ItemTool
{
		private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE, ModBlocks.ANCIENT_ACACIA_WOOD_MOSS, ModBlocks.ANCIENT_BIRCH_WOOD_MOSS, ModBlocks.ANCIENT_DARK_OAK_WOOD_MOSS, ModBlocks.ANCIENT_JUNGLE_WOOD_MOSS, ModBlocks.ANCIENT_OAK_WOOD_MOSS, ModBlocks.ANCIENT_SPRUCE_WOOD_MOSS);

	    public ToolAxe(String name, ToolMaterial material)
	    {
	        super(material, EFFECTIVE_ON);
	        setUnlocalizedName(name);
			setRegistryName(name);
			setCreativeTab(Main.ATLANTISTAB);
	        
	        ModItems.ITEMS.add(this);
	    }

	    public float getStrVsBlock(ItemStack stack, IBlockState state)
	    {
	        Material material = state.getMaterial();
	        return material != Material.WOOD && material != Material.PLANTS && material != Material.VINE ? 1 : this.toolMaterial.getEfficiency();
	    }
}
