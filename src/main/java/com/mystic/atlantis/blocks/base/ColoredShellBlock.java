package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.blocks.BlockBase;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ColoredShellBlock extends BlockBase {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	public ColoredShellBlock(String name, Material material) 
	{
		
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(2.0F);
		setResistance(6.0F);
		setHarvestLevel("pickaxe", 1);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(pos, placer)), 2);
	}

	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
		return EnumFacing.getFacingFromVector(
			 (float) (entity.posX - clickedBlock.getX()),
			 (float) (entity.posY - clickedBlock.getY()),
			 (float) (entity.posZ - clickedBlock.getZ())
		);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.values()[meta % EnumFacing.values().length];
		return getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}
}