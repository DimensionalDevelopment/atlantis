package com.mystic.atlantis.fluids;

import com.mystic.atlantis.init.FluidInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class JetstreamWaterFluid extends FlowingFluid{

    /**
     * @return whether the given fluid an instance of this fluid
     */
    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == getSource() || fluid == getFlowing();
    }

    /**
     * @return whether the fluid infinite like water
     */
    @Override
    protected boolean canConvertToSource() {
        return true;
    }

    /**
     * Perform actions when fluid flows into a replaceable block. Water drops
     * the block's loot table. Lava plays the "block.lava.extinguish" sound.
     */
    @Override
    protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropResources(state, world, pos, blockEntity);
    }

    /**
     * Lava returns true if its FluidState is above a certain height and the
     * Fluid is Water.
     *
     * @return whether the given Fluid can flow into this FluidState
     */
    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockView, BlockPos blockPos, Fluid fluid, Direction direction) {
        return false;
    }

    /**
     * Possibly related to the distance checks for flowing into nearby holes?
     * Water returns 4. Lava returns 2 in the Overworld and 4 in the Nether.
     */
    @Override
    protected int getSlopeFindDistance(LevelReader worldView) {
        return 6;
    }

    /**
     * Water returns 1. Lava returns 2 in the Overworld and 1 in the Nether.
     */
    @Override
    protected int getDropOff(LevelReader worldView) {
        return 1;
    }

    @Override
    public int getAmount(FluidState state) {
        return state.getValue(LEVEL);
    }

    /**
     * Water returns 5. Lava returns 30 in the Overworld and 10 in the Nether.
     */
    @Override
    public int getTickDelay(LevelReader worldView) {
        return 1;
    }

    /**
     * Water and Lava both return 100.0F.
     */
    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public Fluid getSource() {
        return FluidInit.STILL_JETSTREAM_WATER.get();
    }

    @Override
    public Fluid getFlowing() {
        return FluidInit.FLOWING_JETSTREAM_WATER.get();
    }

    @Override
    public Item getBucket() {
        return FluidInit.JETSTREAM_WATER_BUCKET.get();
    }

    @Override
    protected BlockState createLegacyBlock(FluidState fluidState) {
        return FluidInit.JETSTREAM_WATER.get().defaultBlockState().setValue(BlockStateProperties.LEVEL, getLegacyLevel(fluidState));
    }

    @Override
    public boolean isSource(FluidState state) {
        return false;
    }

    public static class Flowing extends JetstreamWaterFluid {
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return fluidState.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends JetstreamWaterFluid {
        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }
    }
}
