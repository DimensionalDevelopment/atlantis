package com.mystic.atlantis.feature.trees;

import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class NymphTree extends Feature<TreeConfiguration> {

    public NymphTree(Codec<TreeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<TreeConfiguration> arg) {
        return true;
    }
}
