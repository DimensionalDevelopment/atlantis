package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.blockentities.AtlanteanPortalBlockEntity;
import com.mystic.atlantis.blocks.blockentities.plants.*;
import com.mystic.atlantis.blocks.blockentities.plants.AnemoneBlockEntity;
import com.mystic.atlantis.util.Reference;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class BlockEntityInit {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Reference.MODID);

	public static final RegistryObject<BlockEntityType<SeashroomBlockEntity>> SEASHROOM = BLOCK_ENTITY_TYPES.register("seashroom", () -> BlockEntityType.Builder.of(SeashroomBlockEntity::new, BlockInit.SEASHROOM.get()).build(null));
	public static final RegistryObject<BlockEntityType<TuberUpBlockEntity>> TUBER_UP = BLOCK_ENTITY_TYPES.register("tuber_up", () -> BlockEntityType.Builder.of(TuberUpBlockEntity::new, BlockInit.TUBER_UP.get()).build(null));
	public static final RegistryObject<BlockEntityType<BlueLilyBlockEntity>> BLUE_LILY = BLOCK_ENTITY_TYPES.register("blue_lily", () -> BlockEntityType.Builder.of(BlueLilyBlockEntity::new, BlockInit.BLUE_LILY.get()).build(null));
	public static final RegistryObject<BlockEntityType<BurntDeepBlockEntity>> BURNT_DEEP = BLOCK_ENTITY_TYPES.register("burnt_deep", () -> BlockEntityType.Builder.of(BurntDeepBlockEntity::new, BlockInit.BURNT_DEEP.get()).build(null));
	public static final RegistryObject<BlockEntityType<AnemoneBlockEntity>> ANEMONE = BLOCK_ENTITY_TYPES.register("anemone", () -> BlockEntityType.Builder.of(AnemoneBlockEntity::new, BlockInit.ANEMONE.get()).build(null));
	public static final RegistryObject<BlockEntityType<AtlanteanPortalBlockEntity>> ATLANTEAN_PORTAL = BLOCK_ENTITY_TYPES.register("atlantean_portal", () -> BlockEntityType.Builder.of(AtlanteanPortalBlockEntity::new, BlockInit.ATLANTEAN_PORTAL.get()).build(null));
	public static void init(IEventBus bus) {
		BLOCK_ENTITY_TYPES.register(bus);
	}

}
