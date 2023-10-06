package com.mystic.atlantis.blocks.plants;

import com.mystic.atlantis.init.BlockInit;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class AtlanteanPalmSaplingBlock extends SaplingBlock {

    public AtlanteanPalmSaplingBlock(Properties settings) {
        super(new OakTreeGrower(), settings
                .randomTicks()
                .strength(0.2F, 0.4F)
                .sound(SoundType.GRASS)
                .noCollission()
                .noOcclusion());
        ComposterBlock.COMPOSTABLES.put(this, 0.3f);
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
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        if(this.canPlaceBlockAt(worldIn, blockpos)){
            return this.mayPlaceOn(worldIn.getBlockState(blockpos), worldIn, blockpos);
        }else{
            return false;
        }
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState targetState, BlockGetter getter, BlockPos targetPos, CollisionContext context) {
        return SHAPE;
    }

    private void growTree(ServerLevel level, ChunkGenerator generator, BlockPos targetPos, BlockState targetState, RandomSource random) {
        if (level.registryAccess().registry(Registries.CONFIGURED_FEATURE).isPresent()) {
            ConfiguredFeature<?, ?> configuredAtlanteanTreeFeature = level.registryAccess().registry(Registries.CONFIGURED_FEATURE).get().get(new ResourceLocation(Reference.MODID, "atlantean_palm_tree_configured"));
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
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }

    public boolean canPlaceOn(BlockState targetState){
        return targetState.getBlock() == Blocks.SAND;
    }

    public boolean canPlaceBlockAt(LevelReader reader, BlockPos targetPos) {
        BlockState targetState = reader.getBlockState(targetPos);
        return canPlaceOn(targetState);
    }
}
