package com.mystic.atlantis.init;

import com.google.common.collect.ImmutableSet;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public class POITypesInit extends PoiTypes {
    public static final DeferredRegister<PoiType> POI = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, Reference.MODID);

    public static final DeferredHolder<PoiType, PoiType> ATLANTEAN_PORTAL = POI.register("atlantean_portal" , () -> new PoiType(getBlockStates(BlockInit.ATLANTEAN_PORTAL.get()), 0, 1));

    private static Set<BlockState> getBlockStates(Block pBlock) {
        return ImmutableSet.copyOf(pBlock.getStateDefinition().getPossibleStates());
    }

    public static void init(IEventBus bus) {
        POI.register(bus);
    }
}
