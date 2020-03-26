package com.mystic.dimensionatlantis.util.handlers;

import com.mystic.dimensionatlantis.Main;
import com.mystic.dimensionatlantis.init.ModBiome;
import com.mystic.dimensionatlantis.init.ModBlocks;
import com.mystic.dimensionatlantis.init.ModDimension;
import com.mystic.dimensionatlantis.init.ModItems;
import com.mystic.dimensionatlantis.init.ModOreDictionary;
import com.mystic.dimensionatlantis.util.Reference;
import com.mystic.dimensionatlantis.world.gen.WorldGenOres;
import com.mystic.dimensionatlantis.world.gen.generators.WorldGenerateCustomStructures;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class RegisteryHandler 
{
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : ModItems.ITEMS)
		{
			
			Main.proxy.registerItemRenderer(item, 0, "inventory");
		}
		
		for(Block block : ModBlocks.BLOCKS)
		{
			Main.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
		}
	}
	
	public static void preInitRegistries(FMLPreInitializationEvent event)
	{
		OBJLoader.INSTANCE.addDomain(Reference.MOD_ID);
		

	
		registerModel(ModItems.HOE_AQUAMARINE);
		registerModel(ModItems.PICKAXE_AQUAMARINE);
		registerModel(ModItems.AXE_AQUAMARINE);
		registerModel(ModItems.SHOVEL_AQUAMARINE);
		ModBiome.registerBiomes();
		GameRegistry.registerWorldGenerator(new WorldGenOres(), 0);
		ModDimension.registerDimensions();
		GameRegistry.registerWorldGenerator(new WorldGenerateCustomStructures(), 1);
		
	}
	
	
	
	
	public static void initRegistries(FMLInitializationEvent event)
	{
		
		ModOreDictionary.registerOres();
			
	}
	
	public static void postInitRegistries(FMLPostInitializationEvent event)
	{
		
	}
	
	public static void serverRegistries(FMLServerStartingEvent event)
	{
		
	}
	
	public static void registerModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
