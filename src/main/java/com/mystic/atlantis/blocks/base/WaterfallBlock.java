package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.Main;
import com.mystic.atlantis.init.ModBlocks;
import com.mystic.atlantis.init.ModItems;
import com.mystic.atlantis.tabs.AtlantisTab;
import com.mystic.atlantis.util.IHasModel;
import com.mystic.atlantis.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class WaterfallBlock extends Block implements IHasModel {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public WaterfallBlock(String name) {
        super(Material.ROCK, MapColor.LIGHT_BLUE);
        setRegistryName(name);
        setTranslationKey(Reference.MODID + "." + name);
        setCreativeTab(AtlantisTab.ATLANTIS_TAB);
        setHardness(3.5F);
        setLightLevel(0.3125F); // Equivalent to lightLevel(5) / 16F
        setSoundType(SoundType.GLASS); // Closest to Amethyst
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        EnumFacing enumfacing = placer.getHorizontalFacing().getOpposite();
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }


    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerModel(Item.getItemFromBlock(this), 0);
    }
}