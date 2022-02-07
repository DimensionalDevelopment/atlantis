package com.mystic.atlantis.blocks.blockentities.registry;

import com.mystic.atlantis.blocks.blockentities.plants.*;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.util.Reference;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class TileRegistry {
        public static final BlockEntityType<UnderwaterShroomTileEntity> UNDERWATER_SHROOM_TILE = Registry.register(Registry.BLOCK_ENTITY_TYPE, Reference.MODID + ":underwater_shroom",
                FabricBlockEntityTypeBuilder.create(UnderwaterShroomTileEntity::new, BlockInit.UNDERWATER_SHROOM_BLOCK).build(null));
        public static final BlockEntityType<TuberUpTileEntity> TUBER_UP_TILE = Registry.register(Registry.BLOCK_ENTITY_TYPE, Reference.MODID + ":tuber_up",
                FabricBlockEntityTypeBuilder.create(TuberUpTileEntity::new, BlockInit.TUBER_UP_BLOCK).build(null));
        public static final BlockEntityType<BlueLilyTileEntity> BLUE_LILY_TILE = Registry.register(Registry.BLOCK_ENTITY_TYPE, Reference.MODID + ":blue_lily",
                FabricBlockEntityTypeBuilder.create(BlueLilyTileEntity::new, BlockInit.BLUE_LILY_BLOCK).build(null));
        public static final BlockEntityType<BurntDeepTileEntity> BURNT_DEEP_TILE = Registry.register(Registry.BLOCK_ENTITY_TYPE, Reference.MODID + ":burnt_deep",
                FabricBlockEntityTypeBuilder.create(BurntDeepTileEntity::new, BlockInit.BURNT_DEEP_BLOCK).build(null));
}
