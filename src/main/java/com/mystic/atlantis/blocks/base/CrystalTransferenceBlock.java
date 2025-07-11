package com.mystic.atlantis.blocks.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;

public class CrystalTransferenceBlock extends Block {
    public static final IntegerProperty POWER = BlockStateProperties.POWER;

    public CrystalTransferenceBlock(Properties properties) {
        super(properties.strength(4.5F)
                .mapColor(MapColor.COLOR_LIGHT_BLUE)
                .lightLevel((state) -> 5)
                .sound(SoundType.AMETHYST)
                .randomTicks());
        this.registerDefaultState(this.stateDefinition.any().setValue(POWER, 0));
    }

    @Override
    public int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        if (isSubmergedInWater(pLevel, pPos)) {
            return Math.max(0, pState.getValue(POWER)) - 1;
        }
        return 0;
    }

    @Override
    public void neighborChanged(BlockState targetState, Level level, BlockPos targetPos, Block targetBlock, BlockPos fromPos, boolean notify) {
        super.neighborChanged(targetState, level, targetPos, targetBlock, fromPos, notify);
        this.updatePower(targetState, level, targetPos);
    }

    public boolean isSubmergedInWater(BlockGetter getter, BlockPos targetPos) {
        return getter.getBlockState(targetPos.east()).is(Blocks.WATER) || getter.getBlockState(targetPos.west()).is(Blocks.WATER) || getter.getBlockState(targetPos.north()).is(Blocks.WATER) ||
                getter.getBlockState(targetPos.south()).is(Blocks.WATER) || getter.getBlockState(targetPos.above()).is(Blocks.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(POWER));
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        this.updatePower(state, worldIn, pos);
    }

    public void updatePower(BlockState state, Level world, BlockPos pos) {
        if (!world.isClientSide) {
            int pow = world.getBestNeighborSignal(pos);
            world.setBlock(pos, state.setValue(POWER, Mth.clamp(pow, 0, 15)), 1 | 2 | 4);
        }
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }
}
