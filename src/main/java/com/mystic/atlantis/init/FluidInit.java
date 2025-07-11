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
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(BlockInit.JETSTREAM_WATER)
            .bucket(ItemInit.JETSTREAM_WATER_BUCKET);

    public static final RegistryObject<FlowingFluid> SALTY_SEAWATER = FLUIDS.register("salty_sea_water",
            () -> new ForgeFlowingFluid.Source(FluidInit.SALTY_SEAWATER_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_SALTY_SEAWATER = FLUIDS.register("flowing_salty_sea_water",
            () -> new ForgeFlowingFluid.Flowing(FluidInit.SALTY_SEAWATER_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties SALTY_SEAWATER_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            FluidTypesInit.SALTY_SEAWATER_FLUID_TYPE, SALTY_SEAWATER, FLOWING_SALTY_SEAWATER)
            .slopeFindDistance(2).levelDecreasePerBlock(3).block(BlockInit.SALTY_SEAWATER)
            .bucket(ItemInit.SALTY_SEAWATER_BUCKET);

  // public static final RegistryObject<FlowingFluid> COCONUT_MILK = FLUIDS.register("coconut_milk",
  //         () -> new ForgeFlowingFluid.Source(FluidInit.COCONUT_MILK_FLUID_PROPERTIES));
  // public static final RegistryObject<FlowingFluid> FLOWING_COCONUT_MILK = FLUIDS.register("flowing_coconut_milk",
  //         () -> new ForgeFlowingFluid.Flowing(FluidInit.COCONUT_MILK_FLUID_PROPERTIES));

  // public static final ForgeFlowingFluid.Properties COCONUT_MILK_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
  //         FluidTypesInit.COCONUT_MILK_FLUID_TYPE, COCONUT_MILK, FLOWING_COCONUT_MILK)
  //         .slopeFindDistance(2).levelDecreasePerBlock(1).block(BlockInit.COCONUT_MILK)
  //         .bucket(ItemInit.COCONUT_MILK_BUCKET);

    public static void init(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
