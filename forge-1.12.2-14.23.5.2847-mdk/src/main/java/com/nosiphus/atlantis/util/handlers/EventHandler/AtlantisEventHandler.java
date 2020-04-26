package com.nosiphus.atlantis.util.handlers.EventHandler;


import com.nosiphus.atlantis.Main;
import com.nosiphus.atlantis.blocks.base.AtlantisPortal;
import com.nosiphus.atlantis.items.ItemPortalAtlantis;
import com.nosiphus.atlantis.tabs.CreativeTab;
import com.nosiphus.atlantis.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class AtlantisEventHandler {
	
	
	
    public static Block withName(Block block, String name)
    {
        block.setCreativeTab(CreativeTab.AtlantisTab);
        block.setRegistryName(name);
        return block;
    }

    public static Item withName(Item item, String name)
    {
        item.setCreativeTab(CreativeTab.AtlantisTab);
        item.setRegistryName(name);
        return item;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        ModelLoader.setCustomModelResourceLocation(ItemPortalAtlantis.PORTAL, 0, new ModelResourceLocation(ItemPortalAtlantis.PORTAL.getRegistryName(), "normal"));

    }
    
    }
