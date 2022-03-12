package com.mystic.atlantis.configfeature.trees;

import com.mystic.atlantis.configfeature.AtlantisFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class AtlanteanTreeSaplingGenerator extends AbstractTreeGrower {

    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getConfiguredFeature(Random random, boolean bees) {
        return AtlantisFeature.ConfiguredFeaturesAtlantis.ATLANTEAN_TREE;
    }
}
