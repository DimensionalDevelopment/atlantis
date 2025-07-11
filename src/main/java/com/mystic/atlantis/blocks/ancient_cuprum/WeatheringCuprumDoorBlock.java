package com.mystic.atlantis.blocks.ancient_cuprum;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class WeatheringCuprumDoorBlock extends DoorBlock implements WeatheringCuprum {
    private final WeatherState weatherState;

    public WeatheringCuprumDoorBlock(BlockSetType p_309051_, WeatherState p_308937_, Properties p_309122_) {
        super(p_309122_.randomTicks(), p_309051_);
        this.weatherState = p_308937_;
    }

    /**
     * Performs a random tick on a block.
     */
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
            this.applyChangeOverTime(pState, pLevel, pPos, pRandom);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return WeatheringCuprum.getNext(pState.getBlock()).isPresent();
    }

    public WeatherState getAge() {
        return this.weatherState;
    }
}
