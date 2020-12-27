package com.mystic.atlantis.blocks.base.plants;

import com.mystic.atlantis.Main;
import com.mystic.atlantis.init.ModBlocks;
import com.mystic.atlantis.init.ModItems;
import com.mystic.atlantis.tabs.AtlantisTab;
import com.mystic.atlantis.util.IHasModel;
import com.mystic.atlantis.util.Reference;
import io.github.opencubicchunks.cubicchunks.core.CubicChunks;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class Algae extends Block implements IShearable, IHasModel {

    // States
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool[] ALL_FACES = new PropertyBool[] {UP, NORTH, SOUTH, WEST, EAST};
    private static final IProperty<Integer> LEVEL = BlockLiquid.LEVEL;

    // Bounding Boxes
    protected static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0625D, 1.0D, 1.0D);
    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.9375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0625D);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.9375D, 1.0D, 1.0D, 1.0D);


    public Algae(String name, Material material)
    {
        super(material);
        setTranslationKey(Reference.MODID + "." + name);
        setRegistryName(name);
        setCreativeTab(AtlantisTab.ATLANTIS_TAB);

        setTickRandomly(true);
        setCreativeTab(AtlantisTab.ATLANTIS_TAB);
        setHardness(0.0F);
        setSoundType(SoundType.PLANT);
        setDefaultState(getBlockState().getBaseState().withProperty(BlockLiquid.LEVEL, 15).withProperty(UP, Boolean.FALSE).withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.FALSE).withProperty(SOUTH, Boolean.FALSE).withProperty(WEST, Boolean.FALSE));

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = state.getActualState(source, pos);
        int i = 0;
        AxisAlignedBB axisalignedbb = FULL_BLOCK_AABB;

        if ((Boolean) state.getValue(UP))
        {
            axisalignedbb = UP_AABB;
            ++i;
        }

        if ((Boolean) state.getValue(NORTH))
        {
            axisalignedbb = NORTH_AABB;
            ++i;
        }

        if ((Boolean) state.getValue(EAST))
        {
            axisalignedbb = EAST_AABB;
            ++i;
        }

        if ((Boolean) state.getValue(SOUTH))
        {
            axisalignedbb = SOUTH_AABB;
            ++i;
        }

        if ((Boolean) state.getValue(WEST))
        {
            axisalignedbb = WEST_AABB;
            ++i;
        }

        return i == 1 ? axisalignedbb : FULL_BLOCK_AABB;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        BlockPos blockpos = pos.up();
        return state.withProperty(UP, worldIn.getBlockState(blockpos).getBlockFaceShape(worldIn, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return side != EnumFacing.DOWN && side != EnumFacing.UP && this.canAttachTo(worldIn, pos, side);
    }

    public boolean canAttachTo(World world, BlockPos blockPos, EnumFacing enumFacing)
    {
        Block block = world.getBlockState(blockPos.up()).getBlock();
        return this.isAcceptableNeighbor(world, blockPos.offset(enumFacing.getOpposite()), enumFacing) && (block == Blocks.WATER || block == ModBlocks.ALGAE || this.isAcceptableNeighbor(world, blockPos.up(), EnumFacing.UP));
    }

    private boolean isAcceptableNeighbor(World world, BlockPos blockPos, EnumFacing enumFacing)
    {
        IBlockState iblockstate = world.getBlockState(blockPos);
        return iblockstate.getBlockFaceShape(world, blockPos, enumFacing) == BlockFaceShape.SOLID && !isExceptBlockForAttaching(iblockstate.getBlock());
    }

    protected static boolean isExceptBlockForAttaching(Block block)
    {
        return block instanceof BlockShulkerBox || block == Blocks.BEACON || block == Blocks.CAULDRON || block == Blocks.GLASS || block == Blocks.STAINED_GLASS || block == Blocks.PISTON || block == Blocks.STICKY_PISTON || block == Blocks.PISTON_HEAD || block == Blocks.TRAPDOOR;
    }

    private boolean recheckGrownSides(World worldIn, BlockPos pos, IBlockState state)
    {
        IBlockState iblockstate = state;

        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            PropertyBool propertybool = getPropertyFor(enumfacing);

            if ((Boolean) state.getValue(propertybool) && !this.canAttachTo(worldIn, pos, enumfacing.getOpposite()))
            {
                IBlockState iblockstate1 = worldIn.getBlockState(pos.up());

                if (iblockstate1.getBlock() != this || !(Boolean) iblockstate1.getValue(propertybool))
                {
                    state = state.withProperty(propertybool, Boolean.FALSE);
                }
            }
        }

        if (getNumGrownFaces(state) == 0)
        {
            return false;
        }
        else
        {
            if (iblockstate != state)
            {
                worldIn.setBlockState(pos, state, 2);
            }

            return true;
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!worldIn.isRemote && !this.recheckGrownSides(worldIn, pos, state))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            setBlockToWater(worldIn, pos);
        }

    }

    public boolean setBlockToWater(World world, BlockPos blockPos)
    {
        return world.setBlockState(blockPos, Blocks.WATER.getDefaultState(), 3);
    }

    public boolean isWaterBlock(World worldIn, BlockPos pos)
    {
        return isWater(worldIn.getBlockState(pos), worldIn, pos);
    }

    public boolean isWater(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.getMaterial() == Material.WATER;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (worldIn.rand.nextInt(4) == 0 && worldIn.isAreaLoaded(pos, 4))
            {
                int i = 4;
                int j = 5;
                boolean flag = false;
                label181:

                for (int k = -4; k <= 4; ++k)
                {
                    for (int l = -4; l <= 4; ++l)
                    {
                        for (int i1 = -1; i1 <= 1; ++i1)
                        {
                            if (worldIn.getBlockState(pos.add(k, i1, l)).getBlock() == this)
                            {
                                --j;

                                if (j <= 0)
                                {
                                    flag = true;
                                    break label181;
                                }
                            }
                        }
                    }
                }

                EnumFacing enumfacing1 = EnumFacing.random(rand);
                BlockPos blockpos2 = pos.up();

                if (enumfacing1 == EnumFacing.UP && pos.getY() < CubicChunks.MAX_SUPPORTED_BLOCK_Y && isWaterBlock(worldIn, blockpos2))
                {
                    IBlockState iblockstate2 = state;

                    for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL)
                    {
                        if (rand.nextBoolean() && this.canAttachTo(worldIn, blockpos2, enumfacing2.getOpposite()))
                        {
                            iblockstate2 = iblockstate2.withProperty(getPropertyFor(enumfacing2), Boolean.TRUE);
                        }
                        else
                        {
                            iblockstate2 = iblockstate2.withProperty(getPropertyFor(enumfacing2), Boolean.FALSE);
                        }
                    }

                    if ((Boolean) iblockstate2.getValue(NORTH) || (Boolean) iblockstate2.getValue(EAST) || (Boolean) iblockstate2.getValue(SOUTH) || (Boolean) iblockstate2.getValue(WEST))
                    {
                        worldIn.setBlockState(blockpos2, iblockstate2, 2);
                    }
                }
                else if (enumfacing1.getAxis().isHorizontal() && !(Boolean) state.getValue(getPropertyFor(enumfacing1)))
                {
                    if (!flag)
                    {
                        BlockPos blockpos4 = pos.offset(enumfacing1);
                        IBlockState iblockstate3 = worldIn.getBlockState(blockpos4);
                        Block block1 = iblockstate3.getBlock();

                        if (isWater(iblockstate3, worldIn, blockpos4))
                        {
                            EnumFacing enumfacing3 = enumfacing1.rotateY();
                            EnumFacing enumfacing4 = enumfacing1.rotateYCCW();
                            boolean flag1 = (Boolean) state.getValue(getPropertyFor(enumfacing3));
                            boolean flag2 = (Boolean) state.getValue(getPropertyFor(enumfacing4));
                            BlockPos blockpos = blockpos4.offset(enumfacing3);
                            BlockPos blockpos1 = blockpos4.offset(enumfacing4);

                            if (flag1 && this.canAttachTo(worldIn, blockpos.offset(enumfacing3), enumfacing3))
                            {
                                worldIn.setBlockState(blockpos4, this.getDefaultState().withProperty(getPropertyFor(enumfacing3), Boolean.TRUE), 2);
                            }
                            else if (flag2 && this.canAttachTo(worldIn, blockpos1.offset(enumfacing4), enumfacing4))
                            {
                                worldIn.setBlockState(blockpos4, this.getDefaultState().withProperty(getPropertyFor(enumfacing4), Boolean.TRUE), 2);
                            }
                            else if (flag1 && isWaterBlock(worldIn, blockpos) && this.canAttachTo(worldIn, blockpos, enumfacing1))
                            {
                                worldIn.setBlockState(blockpos, this.getDefaultState().withProperty(getPropertyFor(enumfacing1.getOpposite()), Boolean.TRUE), 2);
                            }
                            else if (flag2 && isWaterBlock(worldIn, blockpos1) && this.canAttachTo(worldIn, blockpos1, enumfacing1))
                            {
                                worldIn.setBlockState(blockpos1, this.getDefaultState().withProperty(getPropertyFor(enumfacing1.getOpposite()), Boolean.TRUE), 2);
                            }
                        }
                        else if (iblockstate3.getBlockFaceShape(worldIn, blockpos4, enumfacing1) == BlockFaceShape.SOLID)
                        {
                            worldIn.setBlockState(pos, state.withProperty(getPropertyFor(enumfacing1), Boolean.TRUE), 2);
                        }
                    }
                }
                else
                {
                    if (pos.getY() > 1)
                    {
                        BlockPos blockpos3 = pos.down();
                        IBlockState iblockstate = worldIn.getBlockState(blockpos3);
                        Block block = iblockstate.getBlock();

                        if (block.getMaterial(state) == Material.WATER)
                        {
                            IBlockState iblockstate1 = state;

                            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                            {
                                if (rand.nextBoolean())
                                {
                                    iblockstate1 = iblockstate1.withProperty(getPropertyFor(enumfacing), Boolean.FALSE);
                                }
                            }

                            if ((Boolean) iblockstate1.getValue(NORTH) || (Boolean) iblockstate1.getValue(EAST) || (Boolean) iblockstate1.getValue(SOUTH) || (Boolean) iblockstate1.getValue(WEST))
                            {
                                worldIn.setBlockState(blockpos3, iblockstate1, 2);
                            }
                        }
                        else if (block == this)
                        {
                            IBlockState iblockstate4 = iblockstate;

                            for (EnumFacing enumfacing5 : EnumFacing.Plane.HORIZONTAL)
                            {
                                PropertyBool propertybool = getPropertyFor(enumfacing5);

                                if (rand.nextBoolean() && (Boolean) state.getValue(propertybool))
                                {
                                    iblockstate4 = iblockstate4.withProperty(propertybool, Boolean.TRUE);
                                }
                            }

                            if ((Boolean) iblockstate4.getValue(NORTH) || (Boolean) iblockstate4.getValue(EAST) || (Boolean) iblockstate4.getValue(SOUTH) || (Boolean) iblockstate4.getValue(WEST))
                            {
                                worldIn.setBlockState(blockpos3, iblockstate4, 2);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(BlockLiquid.LEVEL, 15).withProperty(UP, Boolean.FALSE).withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.FALSE).withProperty(SOUTH, Boolean.FALSE).withProperty(WEST, Boolean.FALSE);
        return facing.getAxis().isHorizontal() ? iblockstate.withProperty(getPropertyFor(facing.getOpposite()), Boolean.TRUE) : iblockstate;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.AIR;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
        {
            player.addStat(Objects.requireNonNull(StatList.getBlockStats(this)));
            spawnAsEntity(worldIn, pos, new ItemStack(ModBlocks.ALGAE, 1, 0));
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(BlockLiquid.LEVEL, meta).withProperty(SOUTH, (meta & 1) > 0).withProperty(WEST, (meta & 2) > 0).withProperty(NORTH, (meta & 4) > 0).withProperty(EAST, (meta & 8) > 0);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }


    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if ((Boolean) state.getValue(SOUTH))
        {
            i |= 1;
            return i;
        }

        else if ((Boolean) state.getValue(WEST))
        {
            i |= 2;
            return i;
        }

        else if ((Boolean) state.getValue(NORTH))
        {
            i |= 4;
            return i;
        }

        else if ((Boolean) state.getValue(EAST))
        {
            i |= 8;
            return i;
        }

        else
        {
            return state.getValue(LEVEL);
        }
    }

    @Override
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {BlockLiquid.LEVEL, UP, NORTH, EAST, SOUTH, WEST});
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case CLOCKWISE_180:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        switch (mirrorIn)
        {
            case LEFT_RIGHT:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
            case FRONT_BACK:
                return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
            default:
                return super.withMirror(state, mirrorIn);
        }
    }

    public static PropertyBool getPropertyFor(EnumFacing side)
    {
        switch (side)
        {
            case UP:
                return UP;
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
            case WEST:
                return WEST;
            case EAST:
                return EAST;
            default:
                throw new IllegalArgumentException(side + " is an invalid choice");
        }
    }

    public static int getNumGrownFaces(IBlockState state)
    {
        int i = 0;

        for (PropertyBool propertybool : ALL_FACES)
        {
            if ((Boolean) state.getValue(propertybool))
            {
                ++i;
            }
        }

        return i;
    }

    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
    {
        return true;
    }
    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
    {
        return true;
    }
    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        return java.util.Arrays.asList(new ItemStack(this, 1));
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerModel(Item.getItemFromBlock(this), 0);
    }
}
