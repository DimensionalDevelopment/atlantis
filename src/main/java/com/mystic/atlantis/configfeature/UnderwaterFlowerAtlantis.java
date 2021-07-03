package com.mystic.atlantis.configfeature;

import com.mojang.serialization.Codec;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class UnderwaterFlowerAtlantis extends Feature<DefaultFeatureConfig> {

    public UnderwaterFlowerAtlantis(Codec<DefaultFeatureConfig> FeatureSpreadConfig) {
        super(FeatureSpreadConfig);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> config) {
        StructureWorldAccess reader = config.getWorld();
        Random rand = config.getRandom();
        BlockPos pos = config.getOrigin();
            BlockState blockstate = switch (rand.nextInt(3)) {
                case 1 -> BlockInit.RED_UNDERWATER_FLOWER.getDefaultState();
                case 2 -> BlockInit.YELLOW_UNDERWATER_FLOWER.getDefaultState();
                default -> BlockInit.UNDERWATER_FLOWER.getDefaultState();
            };
            if (reader.getBlockState(pos).isOf(Blocks.WATER) && blockstate.canPlaceAt(reader, pos)) {
                reader.setBlockState(pos, blockstate, 2);
                return true;
            }
        return false;
        }
    }
