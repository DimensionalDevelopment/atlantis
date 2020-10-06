package com.nosiphus.atlantis.util.handlers;

import com.nosiphus.atlantis.init.ModBiome;
import com.nosiphus.atlantis.init.ModBlocks;
import com.nosiphus.atlantis.init.ModDimension;
import com.nosiphus.atlantis.init.ModItems;
import com.nosiphus.atlantis.util.IHasModel;
import com.nosiphus.atlantis.util.handlers.EventHandler.PositionEvent;
import com.nosiphus.atlantis.util.reference;
import com.nosiphus.atlantis.world.gen.WorldGenCustomStructures;
import com.nosiphus.atlantis.world.gen.WorldGenOres;

import com.nosiphus.atlantis.world.gen.WorldGenSubmarine;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RegistryHandler 
{
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event)
	{		
		
		for(Item item : ModItems.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : ModBlocks.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}
	}
	
	public static void preInitRegistries(FMLPreInitializationEvent event)
	{
		
		OBJLoader.INSTANCE.addDomain(reference.MODID);
		
		ModBiome.registerBiomes();
		GameRegistry.registerWorldGenerator(new WorldGenOres(), 0);
		ModDimension.registerDimensions();
		GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 1);
		GameRegistry.registerWorldGenerator(new WorldGenSubmarine(), 2);
		MinecraftForge.EVENT_BUS.register(new PositionEvent());
		
	}
	
	public static void initRegistries(FMLInitializationEvent event)
	{
	
	}
	
	public static void postInitRegistries(FMLPostInitializationEvent event)
	{

	}
	
}