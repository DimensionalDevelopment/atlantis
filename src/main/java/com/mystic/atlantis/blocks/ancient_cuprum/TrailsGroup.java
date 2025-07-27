package com.mystic.atlantis.blocks.ancient_cuprum;

import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.registries.DeferredBlock;

public record TrailsGroup(
       DeferredBlock<WeatheringCuprumFullBlock> block,
       DeferredBlock<WeatheringCuprumFullBlock> cut,
       DeferredBlock<WeatheringCuprumFullBlock> chiseled,
       DeferredBlock<WeatheringCuprumStairsBlock> cut_stairs,
       DeferredBlock<WeatheringCuprumSlabBlock> cut_slab,
       DeferredBlock<WeatheringCuprumDoorBlock> door,
       DeferredBlock<WeatheringCuprumTrapdoorBlock> trapdoor,
       DeferredBlock<WeatheringCuprumGrateBlock> grate,
       DeferredBlock<WeatheringCuprumBulbBlock> bulb,
       DeferredBlock<Block> waxed_block,
       DeferredBlock<Block> waxed_cut,
       DeferredBlock<Block> waxed_chiseled,
       DeferredBlock<StairBlock> waxed_cut_stairs,
       DeferredBlock<SlabBlock> waxed_cut_slab,
       DeferredBlock<DoorBlock> waxed_door,
       DeferredBlock<TrapDoorBlock> waxed_trapdoor,
       DeferredBlock<Block> waxed_grate,
       DeferredBlock<CuprumBulbBlock> waxed_bulb) {
}
