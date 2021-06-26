package com.mystic.atlantis.configfeature;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import com.mojang.serialization.Codec;

import com.mystic.atlantis.blocks.plants.Algae;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class AlgaeFeatureAtlantis extends Feature<DefaultFeatureConfig> {
    private static final Direction[] DIRECTIONS = Direction.values();

    public AlgaeFeatureAtlantis(Codec<DefaultFeatureConfig> p_i232002_1_) {
        super(p_i232002_1_);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> config) {
        StructureWorldAccess reader = config.getWorld();
        BlockPos pos = config.getOrigin();
            if (!reader.isAir(pos)) {
                for(Direction direction : DIRECTIONS) {
                    if (direction != Direction.DOWN && Algae.canAttachTo(reader, pos, direction)) {
                        reader.setBlockState(pos, BlockInit.ALGAE.getDefaultState().with(Algae.getPropertyFor(direction), Boolean.TRUE), 2);
                        break;
                    }
                }
            }
        return true;
    }
}

