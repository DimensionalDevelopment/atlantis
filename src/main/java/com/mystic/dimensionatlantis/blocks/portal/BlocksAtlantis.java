package com.mystic.dimensionatlantis.blocks.portal;

import com.mystic.dimensionatlantis.blocks.AtlantisPortal;
import com.mystic.dimensionatlantis.config.AtlantisConfig;
import com.mystic.dimensionatlantis.init.ModDimension;
import com.mystic.dimensionatlantis.world.TeleporterAtlantis;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class BlocksAtlantis extends Block {

    public BlocksAtlantis() {
        super(Material.PORTAL);
        setHardness(2F);
        setResistance(25.0F);
		setHarvestLevel("pickaxe", 2);
		
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
                            if (otherWorld.getBlockState(mutableBlockPos).getBlock() == AtlantisPortal.PORTAL) {
                                otherWorldPos = new BlockPos(x, y + 1, z);
                                foundBlock = true;
                                break;
                            }
                        }
                    }
                }
                if (foundBlock){
                    TeleporterAtlantis.of(otherWorldPos.getX() + 0.5, otherWorldPos.getY(), otherWorldPos.getZ() + 0.5, AtlantisConfig.dimensionId).teleport(playerIn);
                }
                if (!foundBlock){
                    otherWorld.setBlockState(otherWorldPos.down(), AtlantisPortal.PORTAL.getDefaultState());
                    TeleporterAtlantis.of(otherWorldPos.getX() + 0.5, otherWorldPos.getY(), otherWorldPos.getZ() + 0.5, AtlantisConfig.dimensionId).teleport(playerIn);
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
                            if (overWorld.getBlockState(mutableBlockPos).getBlock() == AtlantisPortal.PORTAL) {
                                overWorldPos = new BlockPos(x, y + 1, z);
                                foundBlock = true;
                                break;
                            }
                        }
                    }
                }
                if (foundBlock){
                    TeleporterAtlantis.of(overWorldPos.getX() + 0.5, overWorldPos.getY(), overWorldPos.getZ() + 0.5, AtlantisConfig.overworldId).teleport(playerIn);
                }
                if (!foundBlock){
                    overWorld.setBlockState(overWorldPos.down(), AtlantisPortal.PORTAL.getDefaultState());
                    TeleporterAtlantis.of(overWorldPos.getX() + 0.5, overWorldPos.getY(), overWorldPos.getZ() + 0.5, AtlantisConfig.overworldId).teleport(playerIn);
                }
            }
        }
        return true;
    }
}