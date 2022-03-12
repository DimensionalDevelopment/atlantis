package com.mystic.atlantis.configfeature;

import com.mojang.serialization.Codec;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.util.FastNoiseLite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class AtlanteanIslands extends Feature<NoneFeatureConfiguration> {

    FastNoiseLite perlin = null;


    public AtlanteanIslands(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        setSeed(context.level().getSeed());

        double radius = 15; /*rand.nextInt(config.getMaxPossibleRadius()) + config.getMinRadius() - 5;*/

        BlockPos pos = context.origin();
        SectionPos chunkPos = SectionPos.of(pos);

        if (context.level().getHeight() < 60) {
            return false;
        }

        if ((chunkPos.getX() & 1) == 1 || (chunkPos.getY() & 1) == context.random().nextInt(2) || (chunkPos.getZ() & 1) == 1) {
            return false;
        }

        if(AtlantisConfig.get().islandsOn) {
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            for (double x = -radius; x <= radius; x++) {
                for (double y = 1; y <= radius; y++) {
                    for (double z = -radius; z <= radius; z++) {
                        mutable.set(context.origin()).move((int) x, (int) (y - radius), (int) z);
                        double noise = FastNoiseLite.getSpongePerlinValue(perlin.GetNoise(mutable.getX(), mutable.getY(), mutable.getZ()));
                        double scaledNoise = (noise) * ((y * 3) / ((x * x) + (z * z)));
                        if (scaledNoise >= 0.5) {
                            if (y == radius) {
                                context.level().setBlock(mutable, Blocks.SAND.defaultBlockState(), 2);
                            } else {
                                context.level().setBlock(mutable, Blocks.SANDSTONE.defaultBlockState(), 2);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public void setSeed(long seed) {
        if (perlin == null) {
            perlin = FastNoiseLite.createSpongePerlin((int) seed);
            perlin.SetFrequency(0.2F);
        }
    }
}
