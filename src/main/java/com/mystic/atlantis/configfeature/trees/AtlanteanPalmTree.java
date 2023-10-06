package com.mystic.atlantis.configfeature.trees;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import org.jetbrains.annotations.NotNull;

public class AtlanteanPalmTree extends TreeFeature {

    public AtlanteanPalmTree(Codec<TreeConfiguration> codec) {
        super(codec);
    }
}
