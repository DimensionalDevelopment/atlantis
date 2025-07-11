package com.mystic.atlantis.blocks.ancient_cuprum;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringCuprumGrateBlock extends WaterloggedTransparentBlock implements WeatheringCuprum {
    private final WeatherState weatherState;

    public WeatheringCuprumGrateBlock(WeatherState p_309130_, BlockBehaviour.Properties p_309077_) {
        super(p_309077_.randomTicks());
        this.weatherState = p_309130_;
    }

    /**
     * Performs a random tick on a block.
     */
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        this.applyChangeOverTime(pState, pLevel, pPos, pRandom);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return WeatheringCuprum.getNext(pState.getBlock()).isPresent();
    }

    public WeatherState getAge() {
        return this.weatherState;
    }
}
