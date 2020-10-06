package com.nosiphus.atlantis.world.biomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class BiomeATLANTIS extends Biome
{
    public BiomeATLANTIS()
    {
        super(new BiomeProperties("Alantis").setBaseHeight(-1.0F).setHeightVariation(0.1F).setWaterColor(40356));
        topBlock = Blocks.SAND.getDefaultState();
        fillerBlock = Blocks.SAND.getDefaultState();
        
        
        this.spawnableCreatureList.clear();
        
    }

    public Biome.TempCategory getTempCategory()
    {
        return Biome.TempCategory.OCEAN;
    }
}
