package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.blockentities.AtlantisPortalBlockEntity;
import com.mystic.atlantis.blocks.blockentities.DummyDataStorage;
import com.mystic.atlantis.blocks.blockentities.plants.*;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class TileEntityInit {
	public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister
			.create(Registries.BLOCK_ENTITY_TYPE, Reference.MODID);

	public static final Supplier<BlockEntityType<UnderwaterShroomTileEntity>> UNDERWATER_SHROOM_TILE = TILE_ENTITIES.register("underwater_shroom", () -> BlockEntityType.Builder.of(UnderwaterShroomTileEntity::new, BlockInit.UNDERWATER_SHROOM_BLOCK.get()).build(null));
	public static final Supplier<BlockEntityType<TuberUpTileEntity>> TUBER_UP_TILE = TILE_ENTITIES.register("tuber_up", () -> BlockEntityType.Builder.of(TuberUpTileEntity::new, BlockInit.TUBER_UP_BLOCK.get()).build(null));
	public static final Supplier<BlockEntityType<BlueLilyTileEntity>> BLUE_LILY_TILE = TILE_ENTITIES.register("blue_lily", () -> BlockEntityType.Builder.of(BlueLilyTileEntity::new, BlockInit.BLUE_LILY_BLOCK.get()).build(null));
	public static final Supplier<BlockEntityType<BurntDeepTileEntity>> BURNT_DEEP_TILE = TILE_ENTITIES.register("burnt_deep", () -> BlockEntityType.Builder.of(BurntDeepTileEntity::new, BlockInit.BURNT_DEEP_BLOCK.get()).build(null));
	public static final Supplier<BlockEntityType<EnenmomyTileEntity>> ENENMOMY_TILE = TILE_ENTITIES.register("enenmomy", () -> BlockEntityType.Builder.of(EnenmomyTileEntity::new, BlockInit.ENENMOMY_BLOCK.get()).build(null));
	public static final Supplier<BlockEntityType<AtlantisPortalBlockEntity>> ATLANTIS_PORTAL = TILE_ENTITIES.register("atlantis_portal", () -> BlockEntityType.Builder.of(AtlantisPortalBlockEntity::new, BlockInit.ATLANTIS_CLEAR_PORTAL.get()).build(null));
	public static final Supplier<BlockEntityType<DummyDataStorage>> DUMMY_DATA_STORAGE = TILE_ENTITIES.register("dummydatastorage", () -> BlockEntityType.Builder.of(DummyDataStorage::new, BlockInit.ATLANTIS_PORTAL.get()).build(null));
	//public static final Supplier<BlockEntityType<CrystalGenerator>> CRYSTAL_GENERATOR = TILE_ENTITIES.register("crystal_generator", () -> BlockEntityType.Builder.of(CrystalGenerator::new, BlockInit.CRYSTAL_GENERATOR.get()).build(null));
	//public static final Supplier<BlockEntityType<CrystalStorage>> CRYSTAL_STORAGE = TILE_ENTITIES.register("crystal_storage", () -> BlockEntityType.Builder.of(CrystalStorage::new, BlockInit.CRYSTAL_STORAGE.get()).build(null));
	//public static final Supplier<BlockEntityType<CrystalTransference>> CRYSTAL_TRANSFERENCE = TILE_ENTITIES.register("crystal_transference", () -> BlockEntityType.Builder.of(CrystalTransference::new, BlockInit.CRYSTAL_TRANSFERENCE.get()).build(null));
	public static void init(IEventBus bus) {
		TILE_ENTITIES.register(bus);
	}

}
