package com.mystic.dimensionatlantis.util.handlers;

import com.mystic.dimensionatlantis.Main;
import com.mystic.dimensionatlantis.commands.CommandDimensionTeleport;
import com.mystic.dimensionatlantis.init.ModBiome;
import com.mystic.dimensionatlantis.init.ModBlocks;
import com.mystic.dimensionatlantis.init.ModDimension;
import com.mystic.dimensionatlantis.init.ModFluids;
import com.mystic.dimensionatlantis.init.ModItems;
import com.mystic.dimensionatlantis.proxy.ClientProxy;
import com.mystic.dimensionatlantis.world.gen.WorldGenCustomStructures;
import com.mystic.dimensionatlantis.world.gen.WorldGenOres;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
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
		
		ModFluids.registerFluids();
		ClientProxy.registerCustomMeshesAndStates();
		ModBiome.registerBiomes();
		GameRegistry.registerWorldGenerator(new WorldGenOres(), 0);
		ModDimension.registerDimensions();
		GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 1);
		
		
	}
	
	
	
	
	public static void initRegistries(FMLInitializationEvent event)
	{
		
		
			
	}
	
	public static void postInitRegistries(FMLPostInitializationEvent event)
	{
		
	}
	
	public static void serverRegistries(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandDimensionTeleport());
	}
	
}
