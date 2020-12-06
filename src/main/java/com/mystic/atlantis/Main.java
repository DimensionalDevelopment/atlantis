package com.mystic.atlantis;

import java.util.logging.Logger;

import com.mystic.atlantis.proxy.CommonProxy;
import com.mystic.atlantis.util.handlers.RegistryHandler;
import com.mystic.atlantis.util.reference;

import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = reference.MODID, name = reference.NAME, version = reference.VERSION)
public class Main {

    @Mod.Instance
    public static Main instance;

    public static Logger logger;
	
	@SidedProxy(clientSide = reference.CLIENT_PROXY_CLASS, serverSide = reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	static 
	{
		FluidRegistry.enableUniversalBucket();
	}

	@SideOnly(Side.CLIENT)
	public static void OBJLoader(){
		OBJLoader.INSTANCE.addDomain(reference.MODID);
	}

	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		if(event.getSide() == Side.CLIENT){
			OBJLoader();
		}
			RegistryHandler.preInitRegistries(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		RegistryHandler.initRegistries(event);
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event)
	{
		RegistryHandler.postInitRegistries(event);
	}

}
