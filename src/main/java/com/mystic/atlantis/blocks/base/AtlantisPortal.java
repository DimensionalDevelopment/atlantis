package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.blocks.BlockBase;
import com.mystic.atlantis.init.ModBlocks;
import com.mystic.atlantis.world.TeleporterAtlantis;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AtlantisPortal extends BlockBase 
{
	
	Potion night_vision = Potion.getPotionById(16);
	Potion water_breathing = Potion.getPotionById(13);
	Potion haste = Potion.getPotionById(3);
	
	public AtlantisPortal(String name, Material material) 
	{
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(2.0F);
		setResistance(25.0F);
		setHarvestLevel("pickaxe", 5);
	}

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos){
            if (worldIn.provider.getDimension() == AtlantisConfig.overworldId){
                return super.canPlaceBlockAt(worldIn, pos);
            } else if (worldIn.provider.getDimension() == AtlantisConfig.dimensionId) {
                return super.canPlaceBlockAt(worldIn, pos);
            } else {
                return false;
            }
    }
	
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            //from Overworld to Atlantis
            if (worldIn.provider.getDimension() == AtlantisConfig.overworldId) {
                World otherWorld = worldIn.getMinecraftServer().getWorld(AtlantisConfig.dimensionId);
                otherWorld.getBlockState(pos);
                BlockPos otherWorldPos = otherWorld.getHeight(pos);
                boolean foundBlock = false;
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(0, 0, 0);

                for (int y = 0; y < 256; y++) {
                    for (int x = pos.getX() - 6; x < pos.getX() + 6; x++) {
                        for (int z = pos.getZ() - 6; z < pos.getZ() + 6; z++) {
                            mutableBlockPos.setPos(x,y,z);
                            if (otherWorld.getBlockState(mutableBlockPos).getBlock() == ModBlocks.PORTAL) {
                                otherWorldPos = new BlockPos(x, y + 1, z);
                                foundBlock = true;
                                break;
                            }
                        }
                    }
                }
                if (foundBlock){
                    TeleporterAtlantis.of(otherWorldPos.getX() + 0.5, otherWorldPos.getY(), otherWorldPos.getZ() + 0.5, AtlantisConfig.dimensionId).teleport(playerIn);
                    PotionEffect potioneffect1 = new PotionEffect(water_breathing, 9999, 3, false, true);
                    playerIn.addPotionEffect(potioneffect1);
                    PotionEffect potioneffect2 = new PotionEffect(night_vision, 9999, 3, false, true);
                    playerIn.addPotionEffect(potioneffect2);
                    PotionEffect potioneffect3 = new PotionEffect(haste, 9999, 3, false, true);
                    playerIn.addPotionEffect(potioneffect3);
                    	
                }
                if (!foundBlock){
                    otherWorld.setBlockState(otherWorldPos.down(), ModBlocks.PORTAL.getDefaultState());
                    TeleporterAtlantis.of(otherWorldPos.getX() + 0.5, otherWorldPos.getY(), otherWorldPos.getZ() + 0.5, AtlantisConfig.dimensionId).teleport(playerIn);
                    PotionEffect potioneffect1 = new PotionEffect(water_breathing, 9999, 3, false, true);
                    playerIn.addPotionEffect(potioneffect1);
                    PotionEffect potioneffect2 = new PotionEffect(night_vision, 9999, 3, false, true);
                    playerIn.addPotionEffect(potioneffect2);
                    PotionEffect potioneffect3 = new PotionEffect(haste, 9999, 3, false, true);
                    playerIn.addPotionEffect(potioneffect3);
                }
            }

            //FROM Atlantis to Overworld
            if (worldIn.provider.getDimension() == 324987) {
                World overWorld = worldIn.getMinecraftServer().getWorld(AtlantisConfig.overworldId);
                overWorld.getBlockState(pos);
                BlockPos overWorldPos = overWorld.getHeight(pos);
                boolean foundBlock = false;
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(0, 0, 0);
                
                for (int y = 0; y < 256; y++) {
                    for (int x = pos.getX() - 6; x < pos.getX() + 6; x++) {
                        for (int z = pos.getZ() - 6; z < pos.getZ() + 6; z++) {
                            mutableBlockPos.setPos(x, y, z);
                            if (overWorld.getBlockState(mutableBlockPos).getBlock() == ModBlocks.PORTAL) {
                                overWorldPos = new BlockPos(x, y + 1, z);
                                foundBlock = true;
                                break;
                            }
                        }
                    }
                }
                if (foundBlock){
                    TeleporterAtlantis.of(overWorldPos.getX() + 0.5, overWorldPos.getY(), overWorldPos.getZ() + 0.5, AtlantisConfig.overworldId).teleport(playerIn);
                    if (!worldIn.isRemote) 
                    {
                    	if(playerIn.getActivePotionEffects().isEmpty()) 
                    	{
                    	   
                    	}
                    	else
                    	{
                    		playerIn.clearActivePotions();
                    	}
                    }
                }
                if (!foundBlock){
                    overWorld.setBlockState(overWorldPos.down(), ModBlocks.PORTAL.getDefaultState());
                    TeleporterAtlantis.of(overWorldPos.getX() + 0.5, overWorldPos.getY(), overWorldPos.getZ() + 0.5, AtlantisConfig.overworldId).teleport(playerIn);
                    if (!worldIn.isRemote) 
                    {
                    	if(playerIn.getActivePotionEffects().isEmpty()) 
                    	{
                    	   
                    	}
                    	else
                    	{
                    		playerIn.clearActivePotions();
                    	}
                    }
                }
            }
        }
        return true;
    }
}
