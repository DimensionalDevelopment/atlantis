package com.mystic.atlantis.init;

import static com.mystic.atlantis.blocks.power.atlanteanstone.AtlanteanPowerTorchBlock.WATERLOGGED;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.IPlantable;
import org.jetbrains.annotations.Nullable;

public class AtlanteanFireMelonBody extends GrowingPlantBodyBlock implements LiquidBlockContainer, IPlantable {
    public static final int MAX_AGE = 7;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    protected static final float AABB_OFFSET = 1.0F;
    protected static final VoxelShape BLOCK = Shapes.block();

    public AtlanteanFireMelonBody(Properties arg) {
        super(arg.noCollission().noOcclusion().randomTicks(), Direction.UP, BLOCK, true);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource random) {
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos blockpos = blockPos.relative(direction);
        if(level.getBlockState(blockpos.offset(direction.getOpposite().getNormal().multiply(2))) == Blocks.WATER.defaultBlockState()) {
            if (level.isAreaLoaded(blockPos, 1)) {
                float f = CropBlock.getGrowthSpeed(this, level, blockPos);
                if (CommonHooks.onCropsGrowPre(level, blockPos, blockState, random.nextInt((int) (5.0F / f) + 1) == 5 || random.nextInt((int) (5.0F / f) + 1) == 0)) {
                    if (level.isFluidAtPosition(blockpos, fluidState -> fluidState.is(Fluids.WATER))) {
                        level.setBlockAndUpdate(blockpos.offset(direction.getOpposite().getNormal().multiply(2)), BlockInit.ATLANTEAN_FIRE_MELON_FRUIT_SPIKED.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, direction));
                    }

                    CommonHooks.onCropsGrowPost(level, blockPos, blockState);
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> arg) {
        arg.add(AGE, WATERLOGGED);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return BlockInit.ATLANTEAN_FIRE_MELON_TOP.get();
    }

    @Override
    protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
        return simpleCodec(AtlanteanFireMelonBody::new);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return new ItemStack(ItemInit.ATLANTEAN_FIRE_MELON_SEEDS.get());
    }

    @Override
    public FluidState getFluidState(BlockState arg) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    public boolean canAttachTo(@NotNull BlockState arg) {
        return this.getHeadBlock().canAttachTo(arg);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player pPlayer, BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor arg, BlockPos arg2, BlockState arg3, FluidState arg4) {
        return false;
    }

    @Override
    public BlockState getPlant(BlockGetter arg, BlockPos arg2) {
        return this.defaultBlockState();
    }
}
