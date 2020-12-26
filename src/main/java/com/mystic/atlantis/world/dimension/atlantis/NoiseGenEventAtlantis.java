package com.mystic.atlantis.world.dimension.atlantis;

import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;

import java.util.Random;

public class NoiseGenEventAtlantis extends InitNoiseGensEvent {
    public NoiseGenEventAtlantis(World world, Random rand, Context original) {
        super(world, rand, original);
    }

    public static class ContextAtlantis extends Context {

        public ContextAtlantis(NoiseGeneratorOctaves lperlin1, NoiseGeneratorOctaves lperlin2, NoiseGeneratorOctaves perlin, NoiseGeneratorOctaves scale, NoiseGeneratorOctaves depth) {
            super(lperlin1, lperlin2, perlin, scale, depth);
        }

        @Override
        public ContextAtlantis clone() {
            return new ContextAtlantis(getLPerlin1(), getLPerlin2(), getPerlin(), getScale(), getDepth());
        }
    }
}
