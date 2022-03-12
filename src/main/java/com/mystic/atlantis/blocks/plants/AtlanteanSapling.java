package com.mystic.atlantis.blocks.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AtlanteanSapling extends SaplingBlock implements SimpleWaterloggedBlock {

    public static final Property<Boolean> WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public AtlanteanSapling(AbstractTreeGrower generator, Properties settings) {
        super(generator, settings
                .randomTicks()
                .strength(0.2F, 0.4F)
                .sound(SoundType.GRASS)
                .noCollission()
                .noOcclusion());

        this.defaultBlockState().setValue(WATERLOGGED, Boolean.TRUE).setValue(STAGE, 0);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return !state.getCollisionShape(worldIn, pos).getFaceShape(Direction.UP).isEmpty() || state.isFaceSturdy(worldIn, pos, Direction.UP);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        if(OnlyWater(worldIn, pos, state)){
            return this.mayPlaceOn(worldIn.getBlockState(blockpos), worldIn, blockpos);
        }else{
            return false;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockBehaviour.OffsetType getOffsetType() {
        return BlockBehaviour.OffsetType.XZ;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        if (blockstate.is(this)) {
            return blockstate;
        } else {
            FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(flag));
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, STAGE);
    }

    public Tag<Block> getAir(){
        Tag<Block> air = new Tag<Block>() {
            @Override
            public boolean contains(Block element) {
                return true;
            }

            @Override
            public List<Block> getValues() {
                List<Block> air2 = new ArrayList<Block>();
                air2.add(Blocks.AIR);
                return air2;
            }
        };
        return air;
    }

    public boolean OnlyWater(LevelReader worldReader, BlockPos pos, BlockState state) {
        return !worldReader.getBlockState(pos).is(getAir()) || !this.canBlockStay(worldReader, pos, state);
    }

    public boolean canBlockStay(LevelReader worldReader, BlockPos pos, BlockState state) {
        return canPlaceBlockAt(worldReader, pos);
    }

    public boolean blockTypes(BlockState blockState){
        return blockState.getBlock() == Blocks.GRAVEL || blockState.getBlock() == Blocks.SANDSTONE || blockState.getBlock() == Blocks.GRASS || blockState.getBlock() == Blocks.DIRT || blockState.getBlock() == Blocks.SAND;
    }

    public boolean canPlaceBlockAt(LevelReader worldReader, BlockPos pos) {
        BlockState state = worldReader.getBlockState(pos.below());

        if (worldReader.getBlockState(pos.above()).getMaterial() != Material.WATER)
        {
            return true;
        }
        if(blockTypes(state)) {
            return false;
        }

        return true;
    }
}
