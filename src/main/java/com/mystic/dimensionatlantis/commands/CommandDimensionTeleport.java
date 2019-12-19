package com.mystic.dimensionatlantis.commands;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.mystic.dimensionatlantis.commands.util.Teleport;
import com.mystic.dimensionatlantis.util.Reference;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import net.minecraft.world.WorldServer;

public class CommandDimensionTeleport extends CommandBase
{
	protected final WorldServer world;
	protected final Random random;
	private final List<String> aliases = Lists.newArrayList(Reference.MOD_ID, "tp", "tpmid", "tpdimension", "teleportdimension", "teleport");
	Potion night_vision = Potion.getPotionById(16);
	Potion water_breathing = Potion.getPotionById(13);
	Potion haste = Potion.getPotionById(3);
	
	
	public CommandDimensionTeleport(WorldServer worldIn)
    {
        this.world = worldIn;
        this.random = new Random(worldIn.getSeed());
    }
	
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] dim) throws CommandException 
	{
		if(dim.length < 1) return;
		
		
		
		String s = dim[0];
		String[] args = getPlayer(server, sender, world.getPlayerEntityByName(getName()));  
		
		int dimensionID;
		
		try
		{
			dimensionID = Integer.parseInt(s);
		} catch(NumberFormatException e)
		{
			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Dimension not found"));
			return;
		}
		
		if(sender instanceof EntityPlayer)
		{
			if(dimensionID == 1)
			{
				Teleport.teleportToDimension((EntityPlayer)sender, dimensionID, 0, 65, 0);
				EntityPlayer entityplayer = (EntityPlayer)getEntity(server, sender, args[0], EntityPlayer.class);

	                if (entityplayer.getActivePotionEffects().isEmpty())
	                {
	                    throw new CommandException("commands.effect.failure.notActive.all", new Object[] {entityplayer.getName()});
	                }
	                else
	                {
	                    entityplayer.clearActivePotions();
	                }
	
		    }     
			else
			{
				Teleport.teleportToDimension((EntityPlayer)sender, dimensionID, sender.getPosition().getX(), sender.getPosition().getY() + 10, sender.getPosition().getZ());
						if(dimensionID == 324987) 
						{
							
							EntityPlayer entityplayer = (EntityPlayer)getEntity(server, sender, args[0], EntityPlayer.class);	
							PotionEffect potioneffect1 = new PotionEffect(water_breathing, 9999, 3, false, true);
	                        entityplayer.addPotionEffect(potioneffect1);
	                        PotionEffect potioneffect2 = new PotionEffect(night_vision, 9999, 3, false, true);
	                        entityplayer.addPotionEffect(potioneffect2);
	                        PotionEffect potioneffect3 = new PotionEffect(haste, 9999, 3, false, true);
	                        entityplayer.addPotionEffect(potioneffect3);
						
						
						}
						else
						{
							EntityPlayer entityplayer1 = (EntityPlayer)getEntity(server, sender, args[0], EntityPlayer.class);

				                if (entityplayer1.getActivePotionEffects().isEmpty())
				                {
				                    throw new CommandException("commands.effect.failure.notActive.all", new Object[] {entityplayer1.getName()});
				                }
				                else
				                {
				                    entityplayer1.clearActivePotions();
				                }
				            }
						}
				}
		   }
	
	private String[] getPlayer(MinecraftServer server, ICommandSender sender, EntityPlayer playerEntityByName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() 
	{
		
		return "tpdimension";
	}
	
	@Override
	public String getUsage(ICommandSender sender) 
	{
		
		return "tpdimension <id>";
	}
	
	@Override
	public List<String> getAliases()
	{
		
		return aliases;
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) 
	{
		
		return true;
	}
}
	