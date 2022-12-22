package com.mystic.atlantis.blocks.blockentities.registry;

import com.mystic.atlantis.blocks.blockentities.DummyDataStorage;
import com.mystic.atlantis.blocks.blockentities.energy.CrystalGenerator;
import com.mystic.atlantis.blocks.blockentities.energy.CrystalStorage;
import com.mystic.atlantis.blocks.blockentities.plants.*;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class TileRegistry {
        private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister
                .create(ForgeRegistries.BLOCK_ENTITY_TYPES, Reference.MODID);

        public static void init(IEventBus bus) {
                TILES.register(bus);
        }

        public static final RegistryObject<BlockEntityType<UnderwaterShroomTileEntity>> UNDERWATER_SHROOM_TILE = TILES.register("underwater_shroom", () -> BlockEntityType.Builder.of(UnderwaterShroomTileEntity::new, BlockInit.UNDERWATER_SHROOM_BLOCK.get()).build(null));
        public static final RegistryObject<BlockEntityType<TuberUpTileEntity>> TUBER_UP_TILE = TILES.register("tuber_up", () -> BlockEntityType.Builder.of(TuberUpTileEntity::new, BlockInit.TUBER_UP_BLOCK.get()).build(null));
        public static final RegistryObject<BlockEntityType<BlueLilyTileEntity>> BLUE_LILY_TILE = TILES.register("blue_lily", () -> BlockEntityType.Builder.of(BlueLilyTileEntity::new, BlockInit.BLUE_LILY_BLOCK.get()).build(null));
        public static final RegistryObject<BlockEntityType<BurntDeepTileEntity>> BURNT_DEEP_TILE = TILES.register("burnt_deep", () -> BlockEntityType.Builder.of(BurntDeepTileEntity::new, BlockInit.BURNT_DEEP_BLOCK.get()).build(null));
        public static final RegistryObject<BlockEntityType<EnenmomyTileEntity>> ENENMOMY_TILE = TILES.register("enenmomy", () -> BlockEntityType.Builder.of(EnenmomyTileEntity::new, BlockInit.ENENMOMY_BLOCK.get()).build(null));

        public static final RegistryObject<BlockEntityType<DummyDataStorage>> DUMMY_DATA_STORAGE = TILES.register("dummydatastorage", () -> BlockEntityType.Builder.of(DummyDataStorage::new, BlockInit.ATLANTIS_PORTAL.get()).build(null));
      //  public static final RegistryObject<BlockEntityType<CrystalGenerator>> CRYSTAL_GENERATOR = TILES.register("crystal_generator", () -> BlockEntityType.Builder.of(CrystalGenerator::new, BlockInit.CRYSTAL_GENERATOR.get()).build(null));
     //   public static final RegistryObject<BlockEntityType<CrystalStorage>> CRYSTAL_STORAGE = TILES.register("crystal_storage", () -> BlockEntityType.Builder.of(CrystalStorage::new, BlockInit.CRYSTAL_STORAGE.get()).build(null));
}
