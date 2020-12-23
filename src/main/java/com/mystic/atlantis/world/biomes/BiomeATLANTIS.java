package com.mystic.atlantis.world.biomes;

import com.mystic.atlantis.world.dimension.atlantis.DecoratorBaseAtlantis;
import com.mystic.atlantis.world.dimension.atlantis.ModBiomeAtlantis;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;

import javax.annotation.Nonnull;

public class BiomeATLANTIS extends ModBiomeAtlantis {

    public BiomeATLANTIS() {
        super(new BiomeProperties("atlantis").setBaseHeight(-1.0F).setHeightVariation(0.1F).setTemperature(0.9F).setWaterColor(40356));
        this.setRegistryName("atlantis");
        this.topBlock = Blocks.SAND.getDefaultState();
        this.fillerBlock = Blocks.SANDSTONE.getDefaultState();
    }

    @Nonnull
    @Override
    public BiomeDecorator createBiomeDecorator() {
        return new BiomeATLANTIS.Decorator();
    }

    @Override
    public void initSpawnList() {
        this.spawnableCreatureList.clear();
    }

    @Override
    public int getSkyColorByTemp(float temp) {
        return 0x00b8e6;
    }

    private class Decorator extends DecoratorBaseAtlantis {}
}
