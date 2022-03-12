package com.mystic.atlantis.blocks.blockentities.registry;

import com.mystic.atlantis.blocks.blockentities.DummyDataStorage;
import com.mystic.atlantis.blocks.blockentities.plants.*;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileRegistry {
        private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister
                .create(ForgeRegistries.BLOCK_ENTITIES, Reference.MODID);

        public static void init(IEventBus bus) {
                TILES.register(bus);
        }

        public static final BlockEntityType<UnderwaterShroomTileEntity> UNDERWATER_SHROOM_TILE = register("underwater_shroom", BlockEntityType.Builder.create(UnderwaterShroomTileEntity::new, BlockInit.UNDERWATER_SHROOM_BLOCK).build(null));
        public static final BlockEntityType<TuberUpTileEntity> TUBER_UP_TILE = register("tuber_up", BlockEntityType.Builder.create(TuberUpTileEntity::new, BlockInit.TUBER_UP_BLOCK).build(null));
        public static final BlockEntityType<BlueLilyTileEntity> BLUE_LILY_TILE = register("blue_lily", BlockEntityType.Builder.create(BlueLilyTileEntity::new, BlockInit.BLUE_LILY_BLOCK).build(null));
        public static final BlockEntityType<BurntDeepTileEntity> BURNT_DEEP_TILE = register("burnt_deep", BlockEntityType.Builder.create(BurntDeepTileEntity::new, BlockInit.BURNT_DEEP_BLOCK).build(null));
        public static final BlockEntityType<EnenmomyTileEntity> ENENMOMY_TILE = register("enenmomy", BlockEntityType.Builder.create(EnenmomyTileEntity::new, BlockInit.ENENMOMY_BLOCK).build(null));

        public static final BlockEntityType<DummyDataStorage> DUMMY_DATA_STORAGE = register("dummydatastorage", BlockEntityType.Builder.create(DummyDataStorage::new, BlockInit.ATLANTIS_PORTAL).build(null));

        private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> tile) {
                TILES.register(name, () -> tile);
                return tile;
        }
}
