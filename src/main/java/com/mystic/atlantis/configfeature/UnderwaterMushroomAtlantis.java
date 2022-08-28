package com.mystic.atlantis.configfeature;

import com.mojang.serialization.Codec;
import com.mystic.atlantis.init.BlockInit;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class UnderwaterMushroomAtlantis extends Feature<NoneFeatureConfiguration> {

    public UnderwaterMushroomAtlantis(Codec<NoneFeatureConfiguration> FeatureSpreadConfig) {
        super(FeatureSpreadConfig);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> config) {
        WorldGenLevel reader = config.level();
        RandomSource rand = config.random();
        BlockPos pos = config.origin();
        BlockState blockstate;
        if (rand.nextInt(2) == 1) {
            blockstate = BlockInit.PURPLE_GLOWING_MUSHROOM.get().defaultBlockState();
        } else {
            blockstate = BlockInit.YELLOW_GLOWING_MUSHROOM.get().defaultBlockState();
        }
        if (reader.getBlockState(pos).is(Blocks.WATER) && blockstate.canSurvive(reader, pos)) {
                reader.setBlock(pos, blockstate, 2);
                return true;
            }
        return false;
        }
    }
