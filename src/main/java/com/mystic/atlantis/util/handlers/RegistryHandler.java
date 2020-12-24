package com.mystic.atlantis.util.handlers;

import com.mystic.atlantis.init.ModBiomes;
import com.mystic.atlantis.init.ModBlocks;
import com.mystic.atlantis.init.ModItems;
import com.mystic.atlantis.util.IHasModel;
import com.mystic.atlantis.util.handlers.EventHandler.PositionEvent;
import com.mystic.atlantis.world.biomes.WorldTypeAtlantis;
import com.mystic.atlantis.world.dimension.atlantis.Dimension;
import com.mystic.atlantis.world.gen.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class RegistryHandler 
{
	public static final WorldType WORLD_TYPE_ATLANTIS = new WorldTypeAtlantis();

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
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		ModBiomes.registerBiomes(event);
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
		GameRegistry.registerWorldGenerator(new WorldGenOres(), 0);
		Dimension.registerDimensions();
		MinecraftForge.TERRAIN_GEN_BUS.register(WorldTypeAtlantis.WORLD_TYPES);
		GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 1);
		GameRegistry.registerWorldGenerator(new WorldGenSubmarine(), 2);
		GameRegistry.registerWorldGenerator(new WorldGenUnderwaterFlower(), 3);
		MinecraftForge.EVENT_BUS.register(new PositionEvent());
	}


	
	public static void initRegistries(FMLInitializationEvent event)
	{
		for (ModBiomes.ModBiomeEntry biomeEntry : ModBiomes.biomeEntryList) {
			if (biomeEntry.getWeight() > 0) {
				BiomeManager.addBiome(biomeEntry.getType(), biomeEntry.getEntry());
				BiomeManager.addStrongholdBiome(biomeEntry.getBiome());
			}
		}
	}
	
	public static void postInitRegistries(FMLPostInitializationEvent event)
	{

	}
}