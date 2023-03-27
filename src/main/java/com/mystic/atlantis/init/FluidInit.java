package com.mystic.atlantis.init;

import com.mystic.atlantis.util.Reference;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluidInit {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, Reference.MODID);

    public static final RegistryObject<FlowingFluid> JETSTREAM_WATER = FLUIDS.register("jetstream_water",
            () -> new ForgeFlowingFluid.Source(FluidInit.JETSTREAM_WATER_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_JETSTREAM_WATER = FLUIDS.register("flowing_jetstream_water",
            () -> new ForgeFlowingFluid.Flowing(FluidInit.JETSTREAM_WATER_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties JETSTREAM_WATER_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            FluidTypesInit.JETSTREAM_WATER_FLUID_TYPE, JETSTREAM_WATER, FLOWING_JETSTREAM_WATER)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(BlockInit.JETSTREAM_WATER_BLOCK)
            .bucket(ItemInit.JETSTREAM_WATER_BUCKET);

    public static final RegistryObject<FlowingFluid> SALTY_SEA_WATER = FLUIDS.register("salty_sea_water",
            () -> new ForgeFlowingFluid.Source(FluidInit.JETSTREAM_WATER_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_SALTY_SEA_WATER = FLUIDS.register("flowing_salty_sea_water",
            () -> new ForgeFlowingFluid.Flowing(FluidInit.SALTY_SEA_WATER_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties SALTY_SEA_WATER_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            FluidTypesInit.SALTY_SEA_WATER_FLUID_TYPE, SALTY_SEA_WATER, FLOWING_SALTY_SEA_WATER)
            .slopeFindDistance(2).levelDecreasePerBlock(3).block(BlockInit.SALTY_SEA_WATER_BLOCK)
            .bucket(ItemInit.SALTY_SEA_WATER_BUCKET);


    public static void init(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
