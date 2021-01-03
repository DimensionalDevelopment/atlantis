package com.mystic.atlantis.blocks.plants;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class UnderwaterFlower extends BushBlock implements IWaterLoggable {

    public static final Property<Boolean> WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public UnderwaterFlower(Properties properties) {
        super(properties
                .tickRandomly()
                .hardnessAndResistance(0.2F, 0.4F)
                .sound(SoundType.PLANT)
                .notSolid());
        this.getDefaultState().with(WATERLOGGED, Boolean.TRUE);
    }

    @Override
    public boolean isTransparent(BlockState state) {
        return true;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return !state.getCollisionShape(worldIn, pos).project(Direction.UP).isEmpty() || state.isSolidSide(worldIn, pos, Direction.UP);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        if(OnlyWater(worldIn, pos, state)){
            return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
        }else{
            return false;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XZ;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());
        if (blockstate.isIn(this)) {
            return blockstate;
        } else {
            FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
            boolean flag = fluidstate.getFluid() == Fluids.WATER;
            return super.getStateForPlacement(context).with(WATERLOGGED, Boolean.valueOf(flag));
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
       return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    public ITag<Block> getAir(){
        ITag<Block> air = new ITag<Block>() {
            @Override
            public boolean contains(Block element) {
                return true;
            }

            @Override
            public List<Block> getAllElements() {
                List<Block> air2 = new ArrayList<Block>();
                air2.add(Blocks.AIR);
                return air2;
            }
        };
        return air;
    }

    public boolean OnlyWater(IWorldReader worldReader, BlockPos pos, BlockState state) {
        if (this.getBlock().isIn(getAir())) {
            if (this.canBlockStay(worldReader, pos, state)) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public boolean canBlockStay(IWorldReader worldReader, BlockPos pos, BlockState state) {
        return canPlaceBlockAt(worldReader, pos);
    }

    public boolean blockTypes(BlockState blockState){
        return blockState.getBlock() == Blocks.GRAVEL || blockState.getBlock() == Blocks.GRASS || blockState.getBlock() == Blocks.DIRT || blockState.getBlock() == Blocks.SAND;
    }

    public boolean canPlaceBlockAt(IWorldReader worldReader, BlockPos pos) {
        BlockState state = worldReader.getBlockState(pos.down());

        if (worldReader.getBlockState(pos.up()).getMaterial() != Material.WATER)
        {
            return true;
        }
        if(blockTypes(state)) {
            return false;
        }

        return true;
    }
}
