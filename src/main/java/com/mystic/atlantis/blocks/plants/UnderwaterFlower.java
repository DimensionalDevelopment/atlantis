package com.mystic.atlantis.blocks.plants;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import org.jetbrains.annotations.Nullable;

public class UnderwaterFlower extends PlantBlock implements Waterloggable {

    public static final Property<Boolean> WATERLOGGED = Properties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public UnderwaterFlower(Settings properties) {
        super(properties
                .ticksRandomly()
                .strength(0.2F, 0.4F)
                .sounds(BlockSoundGroup.GRASS)
                .requiresTool()
                .noCollision()
                .nonOpaque());
        this.getDefaultState().with(WATERLOGGED, Boolean.TRUE);
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    protected boolean canPlantOnTop(BlockState state, BlockView worldIn, BlockPos pos) {
        return !state.getCollisionShape(worldIn, pos).getFace(Direction.UP).isEmpty() || state.isSideSolidFullSquare(worldIn, pos, Direction.UP);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        if(OnlyWater(worldIn, pos, state)){
            return this.canPlantOnTop(worldIn.getBlockState(blockpos), worldIn, blockpos);
        }else{
            return false;
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XZ;
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getBlockPos());
        if (blockstate.isOf(this)) {
            return blockstate;
        } else {
            FluidState fluidstate = context.getWorld().getFluidState(context.getBlockPos());
            boolean flag = fluidstate.getFluid() == Fluids.WATER;
            return super.getPlacementState(context).with(WATERLOGGED, Boolean.valueOf(flag));
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext useContext) {
       return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    public Tag<Block> getAir(){
        Tag<Block> air = new Tag<Block>() {
            @Override
            public boolean contains(Block element) {
                return true;
            }

            @Override
            public List<Block> values() {
                List<Block> air2 = new ArrayList<Block>();
                air2.add(Blocks.AIR);
                return air2;
            }
        };
        return air;
    }

    public boolean OnlyWater(WorldView worldReader, BlockPos pos, BlockState state) {
        return !worldReader.getBlockState(pos).isIn(getAir()) || !this.canBlockStay(worldReader, pos, state);
    }

    public boolean canBlockStay(WorldView worldReader, BlockPos pos, BlockState state) {
        return canPlaceBlockAt(worldReader, pos);
    }

    public boolean blockTypes(BlockState blockState){
        return blockState.getBlock() == Blocks.GRAVEL || blockState.getBlock() == Blocks.GRASS || blockState.getBlock() == Blocks.DIRT || blockState.getBlock() == Blocks.SAND;
    }

    public boolean canPlaceBlockAt(WorldView worldReader, BlockPos pos) {
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
