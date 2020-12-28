package com.mystic.atlantis;

import com.mystic.atlantis.proxy.CommonProxy;
import com.mystic.atlantis.util.handlers.RegistryHandler;
import com.mystic.atlantis.util.Reference;

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
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main {

    @Mod.Instance
    public static Main instance;

    public static Logger logger;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	static 
	{
		FluidRegistry.enableUniversalBucket();
	}

	@SideOnly(Side.CLIENT)
	public static void OBJLoader(){
		OBJLoader.INSTANCE.addDomain(Reference.MODID);
	}

	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
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
