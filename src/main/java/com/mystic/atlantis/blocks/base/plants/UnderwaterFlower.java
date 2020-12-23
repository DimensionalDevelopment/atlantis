package com.mystic.atlantis.blocks.base.plants;

import com.mystic.atlantis.Main;
import com.mystic.atlantis.init.ModBlocks;
import com.mystic.atlantis.init.ModItems;
import com.mystic.atlantis.tabs.AtlantisTab;
import com.mystic.atlantis.util.IHasModel;
import com.mystic.atlantis.util.reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

import static net.minecraft.block.BlockLiquid.LEVEL;

public class UnderwaterFlower extends Block implements IPlantable, IHasModel {


    public static final EnumPlantType UNDERWATERFLOWER = EnumPlantType.getPlantType("UnderwaterFlower");
    protected static final AxisAlignedBB UNDERWATERFLOWER_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);


    public UnderwaterFlower(String name, Material material) {
        super(material);
        setTranslationKey(reference.MODID + "." + name);
        setRegistryName(name);
        setCreativeTab(AtlantisTab.ATLANTIS_TAB);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
        setTickRandomly(true);
        setCreativeTab(AtlantisTab.ATLANTIS_TAB);
        setHardness(0.0F);
        setSoundType(SoundType.PLANT);
        setDefaultState(getDefaultState().withProperty(BlockLiquid.LEVEL, 15));
    }

    public boolean blockTypes(IBlockState blockState){
        return blockState.getBlock() == Blocks.GRAVEL || blockState.getBlock() == Blocks.GRASS || blockState.getBlock() == Blocks.DIRT || blockState.getBlock() == Blocks.SAND;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos.down());

        if (worldIn.getBlockState(pos.up()).getMaterial() != Material.WATER)
        {
            return false;
        }
        if (blockTypes(state))
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LEVEL);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(LEVEL, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LEVEL);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return Block.NULL_AABB;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return UNDERWATERFLOWER_AABB;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        this.checkAndDropBlock(worldIn, pos, state);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        this.checkAndDropBlock(worldIn, pos, state);
    }

    public boolean checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (this.canBlockStay(worldIn, pos, state)) {
            return true;
        } else {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, Blocks.WATER.getDefaultState());
            return false;
        }
    }

    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return canPlaceBlockAt(world, pos);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }


    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }


   @SideOnly(Side.CLIENT)
   @Override
   public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return UNDERWATERFLOWER;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerModel(Item.getItemFromBlock(this), 0);
    }
}
