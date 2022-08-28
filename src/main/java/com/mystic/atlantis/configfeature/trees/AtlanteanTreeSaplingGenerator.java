package com.mystic.atlantis.configfeature.trees;

import com.mystic.atlantis.configfeature.AtlantisFeature;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.Random;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AtlanteanTreeSaplingGenerator extends AbstractTreeGrower {

    @Override
    protected Holder<ConfiguredFeature<TreeConfiguration, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
        return null; //AtlantisFeature.ConfiguredFeaturesAtlantis.ATLANTEAN_TREE;
    }
}
