package com.mystic.atlantis.datagen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.Atlantis;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WaterAttachedToLeavesDecorator extends TreeDecorator {
    public static final MapCodec<WaterAttachedToLeavesDecorator> CODEC =
            RecordCodecBuilder.mapCodec((p_225996_) -> p_225996_.group(
                    Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((p_226012_) -> p_226012_.probability),
                    Codec.intRange(0, 16).fieldOf("exclusion_radius_xz").forGetter((p_226012_) -> p_226012_.exclusionRadiusXZ),
                    Codec.intRange(0, 16).fieldOf("exclusion_radius_y").forGetter((p_226010_) -> p_226010_.exclusionRadiusY),
                    BlockStateProvider.CODEC.fieldOf("block_provider").forGetter((p_226008_) -> p_226008_.blockProvider),
                    ExtraCodecs.nonEmptyList(Direction.CODEC.listOf()).fieldOf("directions").forGetter((p_225998_) -> p_225998_.directions)).apply(p_225996_,
                    WaterAttachedToLeavesDecorator::new)
            );
    protected final float probability;
    protected final int exclusionRadiusXZ;
    protected final int exclusionRadiusY;
    protected final BlockStateProvider blockProvider;
    protected final List<Direction> directions;

    public WaterAttachedToLeavesDecorator(float p_225988_, int p_225989_, int p_225990_, BlockStateProvider p_225991_, List<Direction> p_225993_) {
        this.probability = p_225988_;
        this.exclusionRadiusXZ = p_225989_;
        this.exclusionRadiusY = p_225990_;
        this.blockProvider = p_225991_;
        this.directions = p_225993_;
    }

    public void place(TreeDecorator.Context pContext) {
        Set<BlockPos> $$1 = new HashSet();
        RandomSource $$2 = pContext.random();

        for(BlockPos $$3 : Util.shuffledCopy(pContext.leaves(), $$2)) {
            Direction $$4 = Util.getRandom(this.directions, $$2);
            BlockPos $$5 = $$3.relative($$4);
            if (!$$1.contains($$5) && $$2.nextFloat() < this.probability) {
                BlockPos $$6 = $$5.offset(-this.exclusionRadiusXZ, -this.exclusionRadiusY, -this.exclusionRadiusXZ);
                BlockPos $$7 = $$5.offset(this.exclusionRadiusXZ, this.exclusionRadiusY, this.exclusionRadiusXZ);

                for(BlockPos $$8 : BlockPos.betweenClosed($$6, $$7)) {
                    $$1.add($$8.immutable());
                }

                pContext.setBlock($$5, this.blockProvider.getState($$2, $$5));
            }
        }

    }

    protected TreeDecoratorType<?> type() {
        return Atlantis.WATER_ATTACH_TO_LEAVES.get();
    }
}
