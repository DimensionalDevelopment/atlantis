package com.mystic.dimensionatlantis.world.biomes;

import net.minecraft.world.biome.Biome;

public class BiomeATLANTIS extends Biome
{
    public BiomeATLANTIS()
    {
        super(new BiomeProperties("Alantis").setBaseHeight(-1.0F).setHeightVariation(0.1F).setWaterColor(40356));
        this.spawnableCreatureList.clear();
    }

    public Biome.TempCategory getTempCategory()
    {
        return Biome.TempCategory.OCEAN;
    }
}
