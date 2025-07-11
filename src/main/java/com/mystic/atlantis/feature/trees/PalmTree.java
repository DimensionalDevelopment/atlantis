package com.mystic.atlantis.feature.trees;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class PalmTree extends TreeFeature {

    public PalmTree(Codec<TreeConfiguration> codec) {
        super(codec);
    }
}
