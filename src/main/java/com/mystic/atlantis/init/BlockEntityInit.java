package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.blockentities.AquaticComparatorEntity;
import com.mystic.atlantis.blocks.blockentities.AtlanteanPortalBlockEntity;
import com.mystic.atlantis.blocks.blockentities.plants.*;
import com.mystic.atlantis.util.Reference;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityInit {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Reference.MODID);

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SeashroomBlockEntity>> SEASHROOM = BLOCK_ENTITY_TYPES.register("seashroom", () -> BlockEntityType.Builder.of(SeashroomBlockEntity::new, BlockInit.SEASHROOM.get()).build(null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TuberUpBlockEntity>> TUBER_UP = BLOCK_ENTITY_TYPES.register("tuber_up", () -> BlockEntityType.Builder.of(TuberUpBlockEntity::new, BlockInit.TUBER_UP.get()).build(null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BlueLilyBlockEntity>> BLUE_LILY = BLOCK_ENTITY_TYPES.register("blue_lily", () -> BlockEntityType.Builder.of(BlueLilyBlockEntity::new, BlockInit.BLUE_LILY.get()).build(null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BurntDeepBlockEntity>> BURNT_DEEP = BLOCK_ENTITY_TYPES.register("burnt_deep", () -> BlockEntityType.Builder.of(BurntDeepBlockEntity::new, BlockInit.BURNT_DEEP.get()).build(null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AnemoneBlockEntity>> ANEMONE = BLOCK_ENTITY_TYPES.register("anemone", () -> BlockEntityType.Builder.of(AnemoneBlockEntity::new, BlockInit.ANEMONE.get()).build(null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AtlanteanPortalBlockEntity>> ATLANTEAN_PORTAL = BLOCK_ENTITY_TYPES.register("atlantean_portal", () -> BlockEntityType.Builder.of(AtlanteanPortalBlockEntity::new, BlockInit.ATLANTEAN_PORTAL.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AquaticComparatorEntity>> AQUATIC_COMPARATOR_ENTITY = BLOCK_ENTITY_TYPES.register("aquatic_power_comparator", () -> BlockEntityType.Builder.of(AquaticComparatorEntity::new, BlockInit.AQUATIC_POWER_COMPARATOR.get()).build(null));

    public static void init(IEventBus bus) {
		BLOCK_ENTITY_TYPES.register(bus);
	}

}
