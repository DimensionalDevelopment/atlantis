package com.mystic.dimensionatlantis.api.player;



import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerAtlantis 
{
	public void onUpdate();

	public void setInPortal();

	public void saveNBTData(NBTTagCompound compound);

	public void loadNBTData(NBTTagCompound compound);

	public EntityPlayer getEntity();

	public void setJumping(boolean isJumping);

	public boolean isJumping();

	public void shouldPortalSound(boolean playSound);

	public boolean shouldPortalSound();
	
	public boolean inPortalBlock();
	
}