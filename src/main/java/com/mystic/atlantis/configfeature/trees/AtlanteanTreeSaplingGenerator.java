package com.mystic.atlantis.configfeature.trees;

import com.mystic.atlantis.configfeature.AtlantisFeature;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AtlanteanTreeSaplingGenerator extends SaplingGenerator {

    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
        return AtlantisFeature.ConfiguredFeaturesAtlantis.ATLANTEAN_TREE;
    }
}
