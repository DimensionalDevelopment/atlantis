package com.mystic.dimensionatlantis.util.handlers;


import com.mystic.dimensionatlantis.init.ModBlocks;
import com.mystic.dimensionatlantis.util.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHandler 
{
	public static void registerCustomMeshesAndStates()
	{
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.DENSE_WATER_FLUID), new ItemMeshDefinition() 
		{	
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) 
			{
				return new ModelResourceLocation(Reference.MOD_ID + ":dense_water", "fluid");
			}
		});
		
		ModelLoader.setCustomStateMapper(ModBlocks.DENSE_WATER_FLUID, new StateMapperBase() 
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) 
			{
				return new ModelResourceLocation(Reference.MOD_ID + ":dense_water", "fluid");
			}
		});
	}

	
	}
