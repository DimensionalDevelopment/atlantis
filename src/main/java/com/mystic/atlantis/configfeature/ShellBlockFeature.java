package com.mystic.atlantis.configfeature;

import com.mojang.serialization.Codec;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class ShellBlockFeature extends Feature<DefaultFeatureConfig> {

    public ShellBlockFeature(Codec<DefaultFeatureConfig> FeatureSpreadConfig) {
        super(FeatureSpreadConfig);
    }
    
    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> config) {
        StructureWorldAccess reader = config.getWorld();
        Random rand = config.getRandom();
        BlockPos pos = config.getOrigin();

        BlockState blockstate = switch (rand.nextInt(16)) {
            case 0 -> BlockInit.BLACK_COLORED_SHELL_BLOCK.getDefaultState();
            case 1 -> BlockInit.BLUE_COLORED_SHELL_BLOCK.getDefaultState();
            case 2 -> BlockInit.LIGHT_BLUE_COLORED_SHELL_BLOCK.getDefaultState();
            case 3 -> BlockInit.LIGHT_GRAY_COLORED_SHELL_BLOCK.getDefaultState();
            case 4 -> BlockInit.RED_COLORED_SHELL_BLOCK.getDefaultState();
            case 5 -> BlockInit.YELLOW_COLORED_SHELL_BLOCK.getDefaultState();
            case 6 -> BlockInit.ORANGE_COLORED_SHELL_BLOCK.getDefaultState();
            case 7 -> BlockInit.PURPLE_COLORED_SHELL_BLOCK.getDefaultState();
            case 8 -> BlockInit.MAGENTA_COLORED_SHELL_BLOCK.getDefaultState();
            case 9 -> BlockInit.GREEN_COLORED_SHELL_BLOCK.getDefaultState();
            case 10 -> BlockInit.LIME_COLORED_SHELL_BLOCK.getDefaultState();
            case 11 -> BlockInit.BROWN_COLORED_SHELL_BLOCK.getDefaultState();
            case 12 -> BlockInit.PINK_COLORED_SHELL_BLOCK.getDefaultState();
            case 13 -> BlockInit.CYAN_COLORED_SHELL_BLOCK.getDefaultState();
            case 14 -> BlockInit.WHITE_COLORED_SHELL_BLOCK.getDefaultState();
            case 15 -> BlockInit.GRAY_COLORED_SHELL_BLOCK.getDefaultState();
            default -> throw new IllegalStateException("Unexpected value: " + rand.nextInt(16));
        };
        if (reader.getBlockState(pos).isOf(Blocks.WATER) && blockstate.canPlaceAt(reader, pos)) {
            reader.setBlockState(pos, blockstate, 2);
            return true;
        }
        return false;
    }
}
