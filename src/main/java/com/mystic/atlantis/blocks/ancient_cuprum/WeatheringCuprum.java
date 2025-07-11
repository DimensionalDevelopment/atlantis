package com.mystic.atlantis.blocks.ancient_cuprum;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.serialization.Codec;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

import static com.mystic.atlantis.blocks.ancient_cuprum.WeatheringCuprum.WeatherState.*;

public interface WeatheringCuprum extends ChangeOverTimeBlock<WeatheringCuprum.WeatherState> {
    Supplier<ImmutableBiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() -> {
        var builder = ImmutableBiMap.<Block, Block>builder();

        link(builder, UNAFFECTED, EXPOSED);
        link(builder, EXPOSED, WEATHERED);
        link(builder, WEATHERED, OXIDIZED);

        return builder.build();
    });

    static void link(ImmutableBiMap.Builder<Block, Block> builder, WeatherState before, WeatherState after) {
        builder.put(BlockInit.ANCIENT_CUPRUM.get(before).block().get(), BlockInit.ANCIENT_CUPRUM.get(after).block().get());
        builder.put(BlockInit.ANCIENT_CUPRUM.get(before).cut().get(), BlockInit.ANCIENT_CUPRUM.get(after).cut().get());
        builder.put(BlockInit.ANCIENT_CUPRUM.get(before).chiseled().get(), BlockInit.ANCIENT_CUPRUM.get(after).chiseled().get());
        builder.put(BlockInit.ANCIENT_CUPRUM.get(before).cut_stairs().get(), BlockInit.ANCIENT_CUPRUM.get(after).cut_stairs().get());
        builder.put(BlockInit.ANCIENT_CUPRUM.get(before).cut_slab().get(), BlockInit.ANCIENT_CUPRUM.get(after).cut_slab().get());
        builder.put(BlockInit.ANCIENT_CUPRUM.get(before).door().get(), BlockInit.ANCIENT_CUPRUM.get(after).door().get());
        builder.put(BlockInit.ANCIENT_CUPRUM.get(before).trapdoor().get(), BlockInit.ANCIENT_CUPRUM.get(after).trapdoor().get());
        builder.put(BlockInit.ANCIENT_CUPRUM.get(before).grate().get(), BlockInit.ANCIENT_CUPRUM.get(after).grate().get());
        builder.put(BlockInit.ANCIENT_CUPRUM.get(before).bulb().get(), BlockInit.ANCIENT_CUPRUM.get(after).bulb().get());
    }

    Supplier<ImmutableBiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> NEXT_BY_BLOCK.get().inverse());

    static Optional<Block> getPrevious(Block pBlock) {
        return Optional.ofNullable(PREVIOUS_BY_BLOCK.get().get(pBlock));
    }

    static Optional<BlockState> getPrevious(BlockState pState) {
        return getPrevious(pState.getBlock()).map(p_154903_ -> p_154903_.withPropertiesOf(pState));
    }

    static Optional<Block> getNext(Block pBlock) {
        return Optional.ofNullable(NEXT_BY_BLOCK.get().get(pBlock));
    }

    @Override
    default Optional<BlockState> getNext(BlockState pState) {
        return getNext(pState.getBlock()).map(p_154896_ -> p_154896_.withPropertiesOf(pState));
    }

    @Override
    default float getChanceModifier() {
        return this.getAge() == UNAFFECTED ? 0.75F : 1.0F;
    }

    enum WeatherState implements StringRepresentable {
        UNAFFECTED("unaffected", 15),
        EXPOSED("exposed", 12),
        WEATHERED("weathered", 8),
        OXIDIZED("oxidized", 4);

        public static final Codec<WeatherState> CODEC = StringRepresentable.fromEnum(WeatherState::values);
        private final String name;
        private final int lightLevel;

        WeatherState(String pName, int lightLevel) {
            this.name = pName;
            this.lightLevel = lightLevel;
        }

        public int lightLevel() {
            return lightLevel;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}

