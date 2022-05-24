package com.mystic.atlantis.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mystic.atlantis.blocks.LinguisticGlyph;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AtlantisLootTables extends LootTableProvider {

    List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = ImmutableList.of(
            Pair.of(BlockTables::new, LootContextParamSets.BLOCK)
    );

    @Override
    public List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return tables;
    }

    public AtlantisLootTables(DataGenerator arg) {
        super(arg);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((arg, arg2) -> LootTables.validate(validationtracker, arg, arg2));
    }

    public class BlockTables extends BlockLoot {
        private Set<Block> blockSet = new HashSet<>();

        @Override
        protected void addTables() {
            for (LinguisticGlyph glyph : LinguisticGlyph.values()) {
                for (DyeColor color : DyeColor.values()) {
                    dropSelf(BlockInit.getLinguisticBlock(glyph, color));
                }

                dropSelf(BlockInit.getLinguisticBlock(glyph, null));
            }


            dropSelf(BlockInit.LINGUISTIC_BLOCK);

            dropSelf(BlockInit.ORICHALCUM_BLOCK);
        }

        private void dropSelf(RegistryObject<Block> block) {
            dropSelf(block.get());
            blockSet.add(block.get());
        }



        @Override
        protected Iterable<Block> getKnownBlocks() {
            return blockSet;
        }
    }
}
