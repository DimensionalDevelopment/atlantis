package com.mystic.atlantis.blocks.ancient_cuprum;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringCuprumStairsBlock extends StairBlock implements WeatheringCuprum {
    private final WeatherState weatherState;

    public WeatheringCuprumStairsBlock(WeatherState p_154951_, BlockState p_154952_, Properties p_154953_) {
        super(p_154952_, p_154953_.randomTicks());
        this.weatherState = p_154951_;
    }

    /**
     * Performs a random tick on a block.
     */
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        this.changeOverTime(pState, pLevel, pPos, pRandom);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return WeatheringCuprum.getNext(pState.getBlock()).isPresent();
    }

    public WeatherState getAge() {
        return this.weatherState;
    }
}