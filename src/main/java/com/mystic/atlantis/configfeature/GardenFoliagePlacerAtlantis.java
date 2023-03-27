package com.mystic.atlantis.configfeature;

import com.mojang.serialization.Codec;
import com.mystic.atlantis.init.BlockInit;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class GardenFoliagePlacerAtlantis extends Feature<NoneFeatureConfiguration> {

    public GardenFoliagePlacerAtlantis(Codec<NoneFeatureConfiguration> FeatureSpreadConfig) {
        super(FeatureSpreadConfig);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> config) {
        WorldGenLevel reader = config.level();
        RandomSource rand = config.random();
        BlockPos pos = config.origin();
            BlockState blockstate = switch (rand.nextInt(5)) {
                case 1 -> BlockInit.TUBER_UP_BLOCK.get().defaultBlockState();
                case 2 -> BlockInit.ENENMOMY_BLOCK.get().defaultBlockState();
                case 3 -> BlockInit.BLUE_LILY_BLOCK.get().defaultBlockState();
                case 4 -> BlockInit.BURNT_DEEP_BLOCK.get().defaultBlockState();
                default -> BlockInit.UNDERWATER_SHROOM_BLOCK.get().defaultBlockState();
            };
            if (reader.getBlockState(pos).is(Blocks.WATER) && blockstate.canSurvive(reader, pos)) {
                reader.setBlock(pos, blockstate, 2);
                return true;
            }
        return false;
        }
    }
