package com.mystic.dimensionatlantis.commands.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class Teleport extends Teleporter
{
	private final WorldServer world;
	private double x,y,z;
	
	public Teleport(WorldServer world, double x, double y, double z)
	{
		super(world);
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw) 
	{
		
		this.world.getBlockState(new BlockPos((int)this.x, (int)this.y, (int)this.z));
		entityIn.setPosition(x,y,z);
		entityIn.motionX = 0f;
		entityIn.motionY = 0f;
		entityIn.motionZ = 0f;
	}
	
	public static void teleportToDimension(Entity entity, int dimension, double x, double y, double z)
	{
		int oldDimension = entity.getEntityWorld().provider.getDimension();
		EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entity;
		MinecraftServer server = entity.getEntityWorld().getMinecraftServer();
		WorldServer worldServer = server.getWorld(dimension);
		
		if (worldServer == null || server == null) throw new IllegalArgumentException("Dimension: " + dimension + " doesn't exist!");
		worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new Teleport(worldServer, x,y,z));
		entity.setPositionAndUpdate(x, y, z);
	}
}
