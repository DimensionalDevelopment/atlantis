package com.mystic.atlantis.configfeature;

import com.mojang.serialization.Codec;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class GardenFoliagePlacerAtlantis extends Feature<DefaultFeatureConfig> {

    public GardenFoliagePlacerAtlantis(Codec<DefaultFeatureConfig> FeatureSpreadConfig) {
        super(FeatureSpreadConfig);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> config) {
        StructureWorldAccess reader = config.getWorld();
        Random rand = config.getRandom();
        BlockPos pos = config.getOrigin();
            BlockState blockstate = switch (rand.nextInt(5)) {
                case 1 -> BlockInit.TUBER_UP_BLOCK.getDefaultState();
                case 2 -> BlockInit.ENENMOMY_BLOCK.getDefaultState();
                case 3 -> BlockInit.BLUE_LILY_BLOCK.getDefaultState();
                case 4 -> BlockInit.BURNT_DEEP_BLOCK.getDefaultState();
                default -> BlockInit.UNDERWATER_SHROOM_BLOCK.getDefaultState();
            };
            if (reader.getBlockState(pos).isOf(Blocks.WATER) && blockstate.canPlaceAt(reader, pos)) {
                reader.setBlockState(pos, blockstate, 2);
                return true;
            }
        return false;
        }
    }
