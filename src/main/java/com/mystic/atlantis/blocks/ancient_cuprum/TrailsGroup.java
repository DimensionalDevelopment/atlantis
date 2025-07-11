package com.mystic.atlantis.blocks.ancient_cuprum;

import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.RegistryObject;

public record TrailsGroup(
       RegistryObject<WeatheringCuprumFullBlock> block,
       RegistryObject<WeatheringCuprumFullBlock> cut,
       RegistryObject<WeatheringCuprumFullBlock> chiseled,
       RegistryObject<WeatheringCuprumStairsBlock> cut_stairs,
       RegistryObject<WeatheringCuprumSlabBlock> cut_slab,
       RegistryObject<WeatheringCuprumDoorBlock> door,
       RegistryObject<WeatheringCuprumTrapdoorBlock> trapdoor,
       RegistryObject<WeatheringCuprumGrateBlock> grate,
       RegistryObject<WeatheringCuprumBulbBlock> bulb,
       RegistryObject<Block> waxed_block,
       RegistryObject<Block> waxed_cut,
       RegistryObject<Block> waxed_chiseled,
       RegistryObject<StairBlock> waxed_cut_stairs,
       RegistryObject<SlabBlock> waxed_cut_slab,
       RegistryObject<DoorBlock> waxed_door,
       RegistryObject<TrapDoorBlock> waxed_trapdoor,
       RegistryObject<Block> waxed_grate,
       RegistryObject<CuprumBulbBlock> waxed_bulb) {
}
