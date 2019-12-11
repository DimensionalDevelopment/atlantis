package com.mystic.dimensionatlantis;

import java.util.logging.Logger;

import com.mystic.dimensionatlantis.proxy.CommonProxy;
import com.mystic.dimensionatlantis.tabs.AtlantisTab;
import com.mystic.dimensionatlantis.util.Reference;
import com.mystic.dimensionatlantis.util.handlers.RegisteryHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {

    @Mod.Instance
    public static Main instance;

    public static Logger logger;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static final CreativeTabs ATLANTISTAB = new AtlantisTab("dimensionatlantistab");
	
	static 
	{
		FluidRegistry.enableUniversalBucket();
	}

	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		RegisteryHandler.preInitRegistries(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		RegisteryHandler.initRegistries(event);
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event)
	{
		RegisteryHandler.postInitRegistries(event);
	}
	
	@EventHandler
	public void serverInit(FMLServerStartingEvent event)
	{
		RegisteryHandler.serverRegistries(event);
	}
	
}
