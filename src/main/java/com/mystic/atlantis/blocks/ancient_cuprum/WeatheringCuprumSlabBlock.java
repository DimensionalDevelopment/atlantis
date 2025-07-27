package com.mystic.atlantis.blocks.ancient_cuprum;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringCuprumSlabBlock extends SlabBlock implements WeatheringCuprum {
    private final WeatherState weatherState;

    public WeatheringCuprumSlabBlock(WeatherState p_154938_, Properties p_154939_) {
        super(p_154939_.randomTicks());
        this.weatherState = p_154938_;
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
