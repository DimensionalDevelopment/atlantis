package com.mystic.atlantis.blocks.plants;

import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.Nullable;

import com.mystic.atlantis.init.BlockInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PurpleGlowingMushroom extends BushBlock implements SimpleWaterloggedBlock {
    public static final Property<Boolean> WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public PurpleGlowingMushroom(Properties settings) {
        super(settings
                .randomTicks()
                .lightLevel(light -> 8)
                .strength(0.2F, 0.4F)
                .sound(SoundType.GRASS)
                .requiresCorrectToolForDrops()
                .noCollission()
                .noOcclusion());
        ComposterBlock.COMPOSTABLES.put(this, 0.65f);
        this.defaultBlockState().setValue(WATERLOGGED, Boolean.TRUE);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return simpleCodec(PurpleGlowingMushroom::new);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState targetState) {
        return true;
    }

    @Override
    protected boolean mayPlaceOn(BlockState targetState, BlockGetter getter, BlockPos targetPos) {
        return !targetState.getCollisionShape(getter, targetPos).getFaceShape(Direction.UP).isEmpty() || targetState.isFaceSturdy(getter, targetPos, Direction.UP);
    }

    @Override
    public boolean canSurvive(BlockState targetState, LevelReader reader, BlockPos targetPos) {
        BlockPos below = targetPos.below();
        if(isWaterAt(reader, targetPos)){
            return this.mayPlaceOn(reader.getBlockState(below), reader, below);
        }else{
            return false;
        }
    }

    @Override
    public VoxelShape getShape(BlockState targetState, BlockGetter getter, BlockPos targetPos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState targetState = context.getLevel().getBlockState(context.getClickedPos());
        if (targetState.is(this)) {
            return targetState;
        } else {
            FluidState targetFluidState = context.getLevel().getFluidState(context.getClickedPos());
            boolean isWater = targetFluidState.getType() == Fluids.WATER;
            return super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(isWater));
        }
    }

    @Override
    public FluidState getFluidState(BlockState targetState) {
        return targetState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(targetState);
    }

    @Override
    public boolean canBeReplaced(BlockState targetState, BlockPlaceContext context) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    public HolderSet<Block> getAir(){
        Holder<Block> airHolderSet = Holder.direct(Blocks.AIR);
        return HolderSet.direct(airHolderSet);
    }

    public boolean isWaterAt(LevelReader reader, BlockPos targetPos) {
        return !reader.getBlockState(targetPos).is(getAir()) || !this.canPlaceBlockAt(reader, targetPos);
    }

    public boolean canPlaceOn(BlockState targetState){
        return targetState.getBlock() == BlockInit.SEABED.get() || targetState.getBlock() == Blocks.GRAVEL || targetState.getBlock() == Blocks.SANDSTONE || targetState.getBlock() == Blocks.GRASS_BLOCK || targetState.getBlock() == Blocks.DIRT || targetState.getBlock() == Blocks.SAND;
    }

    public boolean canPlaceBlockAt(LevelReader reader, BlockPos targetPos) {
        BlockState targetState = reader.getBlockState(targetPos.below());

        if (reader.getBlockState(targetPos.above()).is(Blocks.WATER)) {
            return true;
        }
        
        if(canPlaceOn(targetState)) {
            return false;
        }

        return true;
    }
}