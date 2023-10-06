package com.mystic.atlantis.init;

import com.google.common.collect.ImmutableSet;
import com.mystic.atlantis.util.Reference;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class POITypesInit extends PoiTypes {
    public static final DeferredRegister<PoiType> POI = DeferredRegister.create(ForgeRegistries.POI_TYPES, Reference.MODID);

    public static final RegistryObject<PoiType> ATLANTEAN_PORTAL = POI.register("atlantean_portal" , () -> new PoiType(getBlockStates(BlockInit.ATLANTEAN_PORTAL_FRAME.get()), 0, 1));

    private static Set<BlockState> getBlockStates(Block pBlock) {
        return ImmutableSet.copyOf(pBlock.getStateDefinition().getPossibleStates());
    }

    public static void init(IEventBus bus) {
        POI.register(bus);
    }
}
