package com.mystic.atlantis.blocks.base;

import com.google.common.base.Predicates;
import com.mystic.atlantis.blocks.plants.Seabloom;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

public class AtlanteanPortalFrame extends EndPortalFrameBlock implements SimpleWaterloggedBlock {
    private static BlockPattern portalShape;

    public static final Property<Boolean> WATERLOGGED = Seabloom.WATERLOGGED;

    public AtlanteanPortalFrame() {
        super(Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.GLASS)
                .lightLevel((p_50847_) -> 1).strength(3.0F, 100000f));
        this.defaultBlockState().setValue(WATERLOGGED, Boolean.FALSE);
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


    public static BlockPattern getOrCreatePortalShape() {
        if (portalShape == null) {
            portalShape = BlockPatternBuilder.start().aisle("?vvv?", ">???<", ">???<", ">???<", "?^^^?")
                    .where('?', BlockInWorld.hasState(BlockStatePredicate.ANY)).where('^',
                            BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockInit.ATLANTEAN_PORTAL_FRAME.get())
                    .where(HAS_EYE, Predicates.equalTo(true)).where(FACING, Predicates.equalTo(Direction.SOUTH))))
                    .where('>', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockInit.ATLANTEAN_PORTAL_FRAME.get())
                            .where(HAS_EYE, Predicates.equalTo(true)).where(FACING, Predicates.equalTo(Direction.WEST))))
                    .where('v', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockInit.ATLANTEAN_PORTAL_FRAME.get())
                            .where(HAS_EYE, Predicates.equalTo(true)).where(FACING, Predicates.equalTo(Direction.NORTH))))
                    .where('<', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockInit.ATLANTEAN_PORTAL_FRAME.get())
                            .where(HAS_EYE, Predicates.equalTo(true)).where(FACING, Predicates.equalTo(Direction.EAST)))).build();
        }

        return portalShape;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HAS_EYE, WATERLOGGED);
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, tool, dropExperience);

        if (state.getValue(HAS_EYE)) {
            popResource(level, pos, new ItemStack(ItemInit.ORB_OF_ATLANTIS.get()));
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            if (!level.isClientSide) {

                BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos();

                for (int x = -3; x <= 3; x++) {
                    for (int z = -3; z <= 3; z++) {

                        checkPos.set(pos.getX() + x, pos.getY(), pos.getZ() + z);

                        if (level.getBlockState(checkPos).is(BlockInit.ATLANTEAN_PORTAL.get())) {
                            level.setBlock(checkPos, Blocks.AIR.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }
}