package com.mystic.atlantis.blocks.plants;

import com.mystic.atlantis.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class AtlanteanSaplingBlock extends SaplingBlock implements SimpleWaterloggedBlock {
    public static final Property<Boolean> WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public AtlanteanSaplingBlock(Properties settings) {
        super(new OakTreeGrower(), settings
                .randomTicks()
                .strength(0.2F, 0.4F)
                .sound(SoundType.GRASS)
                .noCollission()
                .noOcclusion());
        ComposterBlock.COMPOSTABLES.put(this, 0.3f);
        this.defaultBlockState().setValue(WATERLOGGED, Boolean.TRUE).setValue(STAGE, 0);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState targetState) {
        return true;
    }

    @Override
    protected boolean mayPlaceOn(BlockState targetState, BlockGetter worldIn, BlockPos targetPos) {
        return !targetState.getCollisionShape(worldIn, targetPos).getFaceShape(Direction.UP).isEmpty() || targetState.isFaceSturdy(worldIn, targetPos, Direction.UP);
    }

    @Override
    public boolean canSurvive(BlockState targetState, LevelReader world, BlockPos targetPos) {
        BlockPos below = targetPos.below();
        if (isWaterAt(world, targetPos)) {
            return this.mayPlaceOn(world.getBlockState(below), world, below);
        } else {
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

    private void growTree(ServerLevel level, ChunkGenerator generator, BlockPos targetPos, BlockState targetState, RandomSource random) {
        if (level.registryAccess().registry(Registries.CONFIGURED_FEATURE).isPresent()) {
            ConfiguredFeature<?, ?> configuredAtlanteanTreeFeature = level.registryAccess().registry(Registries.CONFIGURED_FEATURE).get().get(new ResourceLocation(Reference.MODID, "atlantean_tree_configured"));
            BlockState legacyTargetState = level.getFluidState(targetPos).createLegacyBlock();
            
            level.setBlock(targetPos, legacyTargetState, 4);
            if (configuredAtlanteanTreeFeature != null) {
                if (configuredAtlanteanTreeFeature.place(level, generator, random, targetPos)) {
                    if (level.getBlockState(targetPos) == legacyTargetState) {
                        level.sendBlockUpdated(targetPos, targetState, legacyTargetState, 2);
                    }
                } else {
                    level.setBlock(targetPos, targetState, 4);
                }
            }
        }
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos targetPos, BlockState targetState, RandomSource random) {
        if (targetState.getValue(STAGE) == 0) {
            level.setBlock(targetPos, targetState.cycle(STAGE), 4);
        } else {
            growTree(level, level.getChunkSource().getGenerator(), targetPos, targetState, random);
        }
    }

    @Override
    public boolean onTreeGrow(BlockState targetState, LevelReader reader, BiConsumer<BlockPos, BlockState> placeFunction, RandomSource random, BlockPos targetPos, TreeConfiguration config) {
        return true;
    }

    @Override
    public FluidState getFluidState(BlockState targetState) {
        return targetState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(targetState);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, STAGE);
    }

    public HolderSet<Block> getAir() {
        Holder<Block> airHolderSet = Holder.direct(Blocks.AIR);
        return HolderSet.direct(airHolderSet);
    }

    public boolean isWaterAt(LevelReader reader, BlockPos targetPos) {
        return !reader.getBlockState(targetPos).is(getAir()) || !this.canPlaceBlockAt(reader, targetPos);
    }

    public boolean canPlaceOn(BlockState targetState) {
        return targetState.getBlock() == Blocks.GRAVEL || targetState.getBlock() == Blocks.SANDSTONE || targetState.getBlock() == Blocks.GRASS || targetState.getBlock() == Blocks.DIRT || targetState.getBlock() == Blocks.SAND;
    }

    public boolean canPlaceBlockAt(LevelReader reader, BlockPos targetPos) {
        BlockState targetState = reader.getBlockState(targetPos.below());

        if (reader.getBlockState(targetPos.above()).is(Blocks.WATER)) {
            return true;
        }
        
        if (canPlaceOn(targetState)) {
            return false;
        }

        return true;
    }
}
