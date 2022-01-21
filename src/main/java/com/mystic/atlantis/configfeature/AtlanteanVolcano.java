package com.mystic.atlantis.configfeature;

import com.mojang.serialization.Codec;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.util.FastNoiseLite;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class AtlanteanVolcano extends Feature<DefaultFeatureConfig> {

    FastNoiseLite fnlPerlin = null;

    public AtlanteanVolcano(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        setSeed(context.getWorld().getSeed());

        BlockPos pos = context.getOrigin();
        ChunkSectionPos chunkPos = ChunkSectionPos.from(pos);
        ChunkRandom chunkRandom = new ChunkRandom();
        chunkRandom.setTerrainSeed(chunkPos.getX(), chunkPos.getZ());

        if ((chunkPos.getX() & 1) == 1 || (chunkPos.getY() & 1) == chunkRandom.nextInt(2) || (chunkPos.getZ() & 1) == 1) {
            return false;
        }

        if (context.getWorld().getBlockState(context.getOrigin().down()).getMaterial() == Material.AIR || context.getWorld().getBlockState(context.getOrigin().down()).getMaterial() == Material.WATER || context.getWorld().getBlockState(context.getOrigin().down()).getMaterial() == Material.LAVA || context.getWorld().getTopY(Heightmap.Type.OCEAN_FLOOR_WG, context.getOrigin().getX(), context.getOrigin().getZ()) < 4)
            return false;

        if(AtlantisConfig.get().volcanoesOn) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            double baseRadius = 10;
            double waterLeakage = 0.7;
            double calciteBase = 0.6;
            int volcanoConeSize = 50;
            int volcanoStartHeight = volcanoConeSize - 5;
            double threshold = 0.5;

            for (double x = -volcanoConeSize; x <= volcanoConeSize; x++) {
                for (double y = -volcanoConeSize; y <= -15; y++) {
                    for (double z = -volcanoConeSize; z <= volcanoConeSize; z++) {
                        mutable.set(context.getOrigin()).move((int) x, (int) y + volcanoStartHeight, (int) z);
                        float noise3 = FastNoiseLite.getSpongePerlinValue(fnlPerlin.GetNoise(mutable.getX(), mutable.getZ()));
                        double scaledNoise = (noise3 / 11) * (-(y * baseRadius) / ((x * x) + (z * z)));
                        if (scaledNoise - waterLeakage >= threshold) {
                            if (mutable.getY() <= context.getOrigin().getY() + (volcanoStartHeight - 14)) {
                                context.getWorld().setBlockState(mutable, Blocks.WATER.getDefaultState(), 2);
                                context.getWorld().getFluidTickScheduler().schedule(mutable, Fluids.WATER, 0);
                            }
                        } else if (scaledNoise >= threshold) {
                            context.getWorld().setBlockState(mutable, Blocks.TUFF.getDefaultState(), 2);
                        }
                        if (scaledNoise - calciteBase >= threshold) {
                            if (mutable.getY() <= 70 && mutable.getY() >= 40) {
                                context.getWorld().setBlockState(mutable, BlockInit.CALCITE_BLOCK.getDefaultState(), 2);
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
