package com.nosiphus.atlantis.world.biomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class BiomeATLANTIS extends Biome
{
    public BiomeATLANTIS()
    {
        super(new BiomeProperties("Alantis").setBaseHeight(-1.0F).setHeightVariation(0.1F).setWaterColor(40356));
        topBlock = Blocks.SAND.getStateFromMeta(0);
        fillerBlock = Blocks.SAND.getStateFromMeta(0);
        
        
        this.spawnableCreatureList.clear();
        
    }

    public Biome.TempCategory getTempCategory()
    {
        return Biome.TempCategory.OCEAN;
    }
}
