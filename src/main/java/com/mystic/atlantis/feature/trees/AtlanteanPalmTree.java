package com.mystic.atlantis.feature.trees;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class AtlanteanPalmTree extends TreeFeature {

    public AtlanteanPalmTree(Codec<TreeConfiguration> codec) {
        super(codec);
    }
}
