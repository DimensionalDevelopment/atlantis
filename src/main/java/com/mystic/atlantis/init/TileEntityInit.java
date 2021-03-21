package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.blockentity.DummyDataStorage;
import com.mystic.atlantis.util.Reference;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class TileEntityInit {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister
            .create(ForgeRegistries.TILE_ENTITIES, Reference.MODID);

    public static final RegistryObject<TileEntityType<DummyDataStorage>> DUMMY_DATA_STORAGE_TILE_ENTITY_TYPE = TILE_ENTITY_TYPE
            .register("dummydatastorage",
                    () -> TileEntityType.Builder.create(() -> new DummyDataStorage(), BlockInit.ATLANTIS_PORTAL.get()).build(null));

}
