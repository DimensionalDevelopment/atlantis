package com.mystic.dimensionatlantis.items;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemAtlanteanCrystal extends ItemBase
{
	
	 
	static Potion instance_health = Potion.getPotionById(6);
	public ItemAtlanteanCrystal(String name)
	{
		super(name);				
	}

	public Random random;
	 public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	    {
	        ItemStack itemstack = playerIn.getHeldItem(handIn);

	        if (!playerIn.capabilities.isCreativeMode)
	        {
	            
	        	PotionEffect potioneffect = new PotionEffect(instance_health, 1, 1, false, false);
	        	int p = 0;
	        	do {
	        		
					worldIn.spawnParticle(EnumParticleTypes.TOTEM, playerIn.posX + random.nextDouble(), playerIn.posY, playerIn.posZ + random.nextDouble(), 1.0D, 1.0D, 1.0D);
	        		System.out.println("particle " + p );
	        		p++;
	        	} while(p<=200);
	        	playerIn.addPotionEffect(potioneffect);
	        	itemstack.shrink(1);
	        }
	    
	        playerIn.addStat(StatList.getObjectUseStats(this));
	        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	    
	    }     
	
}
