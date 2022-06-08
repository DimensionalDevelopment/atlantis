package com.mystic.atlantis.configfeature.trees;

import com.mystic.atlantis.configfeature.AtlantisFeature;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.Random;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class AtlanteanTreeSaplingGenerator extends AbstractTreeGrower {

    @Override
    protected Holder<ConfiguredFeature<TreeConfiguration, ?>> getConfiguredFeature(Random random, boolean bees) {
        return AtlantisFeature.ConfiguredFeaturesAtlantis.ATLANTEAN_TREE;
    }
}
