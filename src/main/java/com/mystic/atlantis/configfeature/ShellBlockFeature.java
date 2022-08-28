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

import java.util.Random;

public class ShellBlockFeature extends Feature<NoneFeatureConfiguration> {

    public ShellBlockFeature(Codec<NoneFeatureConfiguration> FeatureSpreadConfig) {
        super(FeatureSpreadConfig);
    }
    
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> config) {
        WorldGenLevel reader = config.level();
        RandomSource rand = config.random();
        BlockPos pos = config.origin();

        BlockState blockstate = switch (rand.nextInt(16)) {
            case 0 -> BlockInit.BLACK_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 1 -> BlockInit.BLUE_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 2 -> BlockInit.LIGHT_BLUE_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 3 -> BlockInit.LIGHT_GRAY_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 4 -> BlockInit.RED_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 5 -> BlockInit.YELLOW_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 6 -> BlockInit.ORANGE_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 7 -> BlockInit.PURPLE_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 8 -> BlockInit.MAGENTA_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 9 -> BlockInit.GREEN_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 10 -> BlockInit.LIME_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 11 -> BlockInit.BROWN_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 12 -> BlockInit.PINK_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 13 -> BlockInit.CYAN_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 14 -> BlockInit.WHITE_COLORED_SHELL_BLOCK.get().defaultBlockState();
            case 15 -> BlockInit.GRAY_COLORED_SHELL_BLOCK.get().defaultBlockState();
            default -> throw new IllegalStateException("Unexpected value: " + rand.nextInt(16));
        };
        if (reader.getBlockState(pos).is(Blocks.WATER) && blockstate.canSurvive(reader, pos)) {
            reader.setBlock(pos, blockstate, 2);
            return true;
        }
        return false;
    }
}
