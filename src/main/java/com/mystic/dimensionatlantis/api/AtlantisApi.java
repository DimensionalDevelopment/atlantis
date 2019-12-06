package com.mystic.dimensionatlantis.api;






import com.mystic.dimensionatlantis.api.player.IPlayerAtlantis;
import com.mystic.dimensionatlantis.util.Reference;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class AtlantisApi
{

	
	
	

	@CapabilityInject(IPlayerAtlantis.class)
	public static Capability<IPlayerAtlantis> ATLANTIS_PLAYER = null;

	private static final AtlantisApi instance = new AtlantisApi();

	public AtlantisApi()
	{
	}




public static AtlantisApi getInstance()
{
	return instance;
}




public IPlayerAtlantis get(EntityPlayer player)
{
	return player.getCapability(ATLANTIS_PLAYER, null);
}
}