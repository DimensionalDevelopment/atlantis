package com.mystic.atlantis.configfeature;

import com.mojang.serialization.Codec;
import com.mystic.atlantis.blocks.plants.Algae;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class AlgaeFeatureAtlantis extends Feature<NoFeatureConfig> {
    private static final Direction[] DIRECTIONS = Direction.values();

    public AlgaeFeatureAtlantis(Codec<NoFeatureConfig> p_i232002_1_) {
        super(p_i232002_1_);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos.Mutable blockpos$mutable = pos.toMutable();

        for(int i = 0; i < 75; ++i) {
            blockpos$mutable.setPos(pos);
            blockpos$mutable.move(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
            blockpos$mutable.setY(i);
            if (!reader.isAirBlock(blockpos$mutable)) {
                for(Direction direction : DIRECTIONS) {
                    if (direction != Direction.DOWN && Algae.canAttachTo(reader, blockpos$mutable, direction)) {
                        reader.setBlockState(blockpos$mutable, BlockInit.ALGAE.get().getDefaultState().with(Algae.getPropertyFor(direction), Boolean.TRUE), 2);
                        break;
                    }
                }
            }
        }

        return true;
    }
}

