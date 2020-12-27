package com.mystic.atlantis.items;

import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemAtlanteanCrystal extends ItemBase {

	public ItemAtlanteanCrystal(String name)
	{
		super(name);				
	}

	Random rand = new Random();

	 public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		if (!playerIn.capabilities.isCreativeMode)
		{
		   if(playerIn.getHealth() < playerIn.getMaxHealth()) {
			PotionEffect potioneffect = new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 1);
			playerIn.addPotionEffect(potioneffect);
			itemstack.shrink(1);
		   }
		}
		playerIn.addStat(StatList.getObjectUseStats(this));
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);

	}

}
