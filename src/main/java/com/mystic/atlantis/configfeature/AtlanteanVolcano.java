package com.mystic.atlantis.configfeature;

import com.mojang.serialization.Codec;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.util.FastNoiseLite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;

public class AtlanteanVolcano extends Feature<NoneFeatureConfiguration> {

    FastNoiseLite fnlPerlin = null;

    public AtlanteanVolcano(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        setSeed(context.level().getSeed());

        BlockPos pos = context.origin();
        SectionPos chunkPos = SectionPos.of(pos);

        if ((chunkPos.getX() & 1) == 1 || (chunkPos.getY() & 1) == context.random().nextInt(2) || (chunkPos.getZ() & 1) == 1) {
            return false;
        }

        if (context.level().getBlockState(context.origin().below()).getMaterial() == Material.AIR || context.level().getBlockState(context.origin().below()).getMaterial() == Material.WATER || context.level().getBlockState(context.origin().below()).getMaterial() == Material.LAVA || context.level().getHeight(Heightmap.Types.OCEAN_FLOOR_WG, context.origin().getX(), context.origin().getZ()) < 4)
            return false;

        if(AtlantisConfig.INSTANCE.volcanoesOn.get()) {
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            double baseRadius = 10;
            double waterLeakage = 0.7;
            double calciteBase = 0.6;
            int volcanoConeSize = 50;
            int volcanoStartHeight = volcanoConeSize - 5;
            double threshold = 0.5;

            for (double x = -volcanoConeSize; x <= volcanoConeSize; x++) {
                for (double y = -volcanoConeSize; y <= -15; y++) {
                    for (double z = -volcanoConeSize; z <= volcanoConeSize; z++) {
                        mutable.set(context.origin()).move((int) x, (int) y + volcanoStartHeight, (int) z);
                        float noise3 = FastNoiseLite.getSpongePerlinValue(fnlPerlin.GetNoise(mutable.getX(), mutable.getZ()));
                        double scaledNoise = (noise3 / 11) * (-(y * baseRadius) / ((x * x) + (z * z)));
                        if (scaledNoise - waterLeakage >= threshold) {
                            if (mutable.getY() <= context.origin().getY() + (volcanoStartHeight - 14)) {
                                context.level().setBlock(mutable, Blocks.WATER.defaultBlockState(), 2);
                                context.level().scheduleTick(mutable, Fluids.WATER, 0);
                            }
                        } else if (scaledNoise >= threshold) {
                            context.level().setBlock(mutable, Blocks.BASALT.defaultBlockState(), 2);
                        }
                        if (scaledNoise - calciteBase >= threshold) {
                            if (mutable.getY() <= 70 && mutable.getY() >= 40) {
                                context.level().setBlock(mutable, BlockInit.BUBBLE_MAGMA.get().defaultBlockState(), 2);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }



    public void setSeed(long seed) {
        if (fnlPerlin == null) {
            fnlPerlin = FastNoiseLite.createSpongePerlin((int) seed);
            fnlPerlin.SetFrequency(0.2F);
        }
    }
}
