package com.mystic.atlantis.init;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;

import java.util.Random;
import java.util.function.Supplier;

public class AtlanteanFireMelonBody extends GrowingPlantBodyBlock implements LiquidBlockContainer {
    public static final int MAX_AGE = 7;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    protected static final float AABB_OFFSET = 1.0F;
    protected static final VoxelShape BLOCK = Shapes.block();

    public AtlanteanFireMelonBody(BlockBehaviour.Properties arg2) {
        super(arg2);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos blockPos, Random random) {
        if (level.isAreaLoaded(blockPos, 1)) {
            if (level.getRawBrightness(blockPos, 0) >= 9) {
                float f = CropBlock.getGrowthSpeed(this, level, blockPos);
                if (ForgeHooks.onCropsGrowPre(level, blockPos, blockState, random.nextInt((int)(25.0F / f) + 1) == 0)) {
                    Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                    BlockPos blockpos = blockPos.relative(direction);
                    if (level.isEmptyBlock(blockpos) && level.isFluidAtPosition(blockpos, fluidState -> fluidState.is(Fluids.WATER))) {
                        level.setBlockAndUpdate(blockPos, BlockInit.ATLANTEAN_FIRE_MELON_FRUIT.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, direction));
                    }

                    ForgeHooks.onCropsGrowPost(level, blockPos, blockState);
                }
            }

        }
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return BlockInit.ATLANTEAN_FIRE_MELON_TOP.get();
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter arg, BlockPos arg2, BlockState arg3) {
        return new ItemStack(ItemInit.ATLANTEAN_FIRE_MELON_SEEDS.get());
    }

    @Override
    public FluidState getFluidState(BlockState arg) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    protected boolean canAttachTo(BlockState arg) {
        return this.getHeadBlock().canAttachTo(arg);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter arg, BlockPos arg2, BlockState arg3, Fluid arg4) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor arg, BlockPos arg2, BlockState arg3, FluidState arg4) {
        return false;
    }
}
