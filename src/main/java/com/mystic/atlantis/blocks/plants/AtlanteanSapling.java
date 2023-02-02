package com.mystic.atlantis.blocks.plants;

import com.mystic.atlantis.configfeature.trees.AtlanteanTree;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;

public class AtlanteanSapling extends SaplingBlock implements SimpleWaterloggedBlock {

    public static final Property<Boolean> WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public AtlanteanSapling(Properties settings) {
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
        if (OnlyWater(worldIn, pos, state)) {
            return this.mayPlaceOn(worldIn.getBlockState(blockpos), worldIn, blockpos);
        } else {
            return false;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
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

    private void growTree(ServerLevel serverLevel, ChunkGenerator generator, BlockPos blockPos, BlockState blockState, RandomSource randomSource) {
        if (serverLevel.registryAccess().registry(Registry.CONFIGURED_FEATURE_REGISTRY).isPresent()) {
            ConfiguredFeature<?, ?> configuredfeature = serverLevel.registryAccess().registry(Registry.CONFIGURED_FEATURE_REGISTRY).get().get(new ResourceLocation(Reference.MODID, "atlantean_tree_configured"));
            BlockState blockstate = serverLevel.getFluidState(blockPos).createLegacyBlock();
            serverLevel.setBlock(blockPos, blockstate, 4);
            if (configuredfeature != null) {
                if (configuredfeature.place(serverLevel, generator, randomSource, blockPos)) {
                    if (serverLevel.getBlockState(blockPos) == blockstate) {
                        serverLevel.sendBlockUpdated(blockPos, blockState, blockstate, 2);
                    }
                } else {
                    serverLevel.setBlock(blockPos, blockState, 4);
                }
            }
        }
    }

    @Override
    public void advanceTree(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, RandomSource randomSource) {
        if (blockState.getValue(STAGE) == 0) {
            serverLevel.setBlock(blockPos, blockState.cycle(STAGE), 4);
        } else {
            growTree(serverLevel, serverLevel.getChunkSource().getGenerator(), blockPos, blockState, randomSource);
        }
    }

    @Override
    public boolean onTreeGrow(BlockState state, LevelReader levelReader, BiConsumer<BlockPos, BlockState> placeFunction, RandomSource randomSource, BlockPos pos, TreeConfiguration config) {
        return true;
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

    public HolderSet<Block> getAir() {
        Holder<Block> airHolderSet = Holder.direct(Blocks.AIR);
        return HolderSet.direct(airHolderSet);
    }

    public boolean OnlyWater(LevelReader worldReader, BlockPos pos, BlockState state) {
        return !worldReader.getBlockState(pos).is(getAir()) || !this.canBlockStay(worldReader, pos, state);
    }

    public boolean canBlockStay(LevelReader worldReader, BlockPos pos, BlockState state) {
        return canPlaceBlockAt(worldReader, pos);
    }

    public boolean blockTypes(BlockState blockState) {
        return blockState.getBlock() == Blocks.GRAVEL || blockState.getBlock() == Blocks.SANDSTONE || blockState.getBlock() == Blocks.GRASS || blockState.getBlock() == Blocks.DIRT || blockState.getBlock() == Blocks.SAND;
    }

    public boolean canPlaceBlockAt(LevelReader worldReader, BlockPos pos) {
        BlockState state = worldReader.getBlockState(pos.below());

        if (worldReader.getBlockState(pos.above()).getMaterial() != Material.WATER) {
            return true;
        }
        if (blockTypes(state)) {
            return false;
        }

        return true;
    }
}
