package com.mystic.atlantis.configfeature;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import com.mojang.serialization.Codec;

import com.mystic.atlantis.blocks.plants.Algae;
import com.mystic.atlantis.init.BlockInit;

public class AlgaeFeatureAtlantis extends Feature<DefaultFeatureConfig> {
    private static final Direction[] DIRECTIONS = Direction.values();

    public AlgaeFeatureAtlantis(Codec<DefaultFeatureConfig> p_i232002_1_) {
        super(p_i232002_1_);
    }

    public boolean generate(StructureWorldAccess reader, ChunkGenerator generator, Random rand, BlockPos pos, DefaultFeatureConfig config) {
        BlockPos.Mutable blockpos$mutable = pos.mutableCopy();

        for(int i = 0; i < 75; ++i) {
            blockpos$mutable.set(pos);
            blockpos$mutable.move(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
            blockpos$mutable.setY(i);
            if (!reader.isAir(blockpos$mutable)) {
                for(Direction direction : DIRECTIONS) {
                    if (direction != Direction.DOWN && Algae.canAttachTo(reader, blockpos$mutable, direction)) {
                        reader.setBlockState(blockpos$mutable, BlockInit.ALGAE.getDefaultState().with(Algae.getPropertyFor(direction), Boolean.TRUE), 2);
                        break;
                    }
                }
            }
        }

        return true;
    }
}

