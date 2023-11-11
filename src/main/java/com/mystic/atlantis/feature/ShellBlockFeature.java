package com.mystic.atlantis.feature;

import com.mojang.serialization.Codec;
import com.mystic.atlantis.init.BlockInit;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ShellBlockFeature extends Feature<NoneFeatureConfiguration> {

    public ShellBlockFeature(Codec<NoneFeatureConfiguration> FeatureSpreadConfig) {
        super(FeatureSpreadConfig);
    }
    
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> config) {
        WorldGenLevel reader = config.level();
        RandomSource rand = config.random();
        BlockPos pos = config.origin();

        int randomNum = rand.nextInt(DyeColor.values().length + 1);
        DyeColor color = DyeColor.byId(randomNum);
        BlockState blockstate = switch (rand.nextInt(5)) {
            case 0 -> BlockInit.COLORED_SHELL_BLOCKS.get(color).get().defaultBlockState();
            case 1 -> BlockInit.NAUTILUS_SHELL_BLOCK.get().defaultBlockState();
            case 2 -> BlockInit.NAUTILUS_SHELL_CRACKED.get().defaultBlockState();
            case 3 -> BlockInit.OYSTER_SHELL_CRACKED.get().defaultBlockState();
            case 4 -> BlockInit.CRACKED_SHELL_BLOCKS.get(color).get().defaultBlockState();
            default -> throw new IllegalStateException("Unexpected value: " + rand.nextInt(5));
        };
        if (reader.getBlockState(pos).is(Blocks.WATER) && blockstate.canSurvive(reader, pos)) {
            reader.setBlock(pos, blockstate, 2);
            return true;
        }
        return false;
    }
}
