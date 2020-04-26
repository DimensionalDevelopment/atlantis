package com.nosiphus.atlantis.util.handlers.EventHandler;

import com.nosiphus.atlantis.config.AtlantisConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

@EventBusSubscriber
public class DimensionEffectTimedEvent 
{
	static Potion night_vision = Potion.getPotionById(16);
	static Potion water_breathing = Potion.getPotionById(13);
	static Potion haste = Potion.getPotionById(3);
	
	
	 
		@SuppressWarnings("unlikely-arg-type")
		@SubscribeEvent
		public static void  playerTick(PlayerTickEvent event){
			
	if(event.player instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.player;
			World world = player.world;
			
			if(world.provider.getDimension() == AtlantisConfig.dimensionId) 
			{
				
				if(player.getActivePotionEffects().contains(night_vision)) 
	        	{
	        	   
	        	}
				else
				{
					 PotionEffect potioneffect2 = new PotionEffect(night_vision, 9999, 3, false, true);
	                 player.addPotionEffect(potioneffect2);
				}	
				if(player.getActivePotionEffects().contains(water_breathing)) 
	        	{
	        	   
	        	}
				else
				{
					 PotionEffect potioneffect1 = new PotionEffect(water_breathing, 9999, 3, false, true);
					 player.addPotionEffect(potioneffect1);
				}		
				if(player.getActivePotionEffects().contains(haste)) 
	        	{
	        	   
	        	}
				else
				{
					PotionEffect potioneffect3 = new PotionEffect(haste, 9999, 3, false, true);
					player.addPotionEffect(potioneffect3);	
				}		
			}
		}
	}
}		