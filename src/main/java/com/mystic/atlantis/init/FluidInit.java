package com.mystic.atlantis.init;

import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FluidInit {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, Reference.MODID);

    public static final Supplier<FlowingFluid> JETSTREAM_WATER = FLUIDS.register("jetstream_water",
            () -> new BaseFlowingFluid.Source(FluidInit.JETSTREAM_WATER_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_JETSTREAM_WATER = FLUIDS.register("flowing_jetstream_water",
            () -> new BaseFlowingFluid.Flowing(FluidInit.JETSTREAM_WATER_FLUID_PROPERTIES));


    public static final BaseFlowingFluid.Properties JETSTREAM_WATER_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
            FluidTypesInit.JETSTREAM_WATER_FLUID_TYPE, JETSTREAM_WATER, FLOWING_JETSTREAM_WATER)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(BlockInit.JETSTREAM_WATER_BLOCK)
            .bucket(ItemInit.JETSTREAM_WATER_BUCKET);

    public static final Supplier<FlowingFluid> SALTY_SEA_WATER = FLUIDS.register("salty_sea_water",
            () -> new BaseFlowingFluid.Source(FluidInit.SALTY_SEA_WATER_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_SALTY_SEA_WATER = FLUIDS.register("flowing_salty_sea_water",
            () -> new BaseFlowingFluid.Flowing(FluidInit.SALTY_SEA_WATER_FLUID_PROPERTIES));

    public static final BaseFlowingFluid.Properties SALTY_SEA_WATER_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
            FluidTypesInit.SALTY_SEA_WATER_FLUID_TYPE, SALTY_SEA_WATER, FLOWING_SALTY_SEA_WATER)
            .slopeFindDistance(2).levelDecreasePerBlock(3).block(BlockInit.SALTY_SEA_WATER_BLOCK)
            .bucket(ItemInit.SALTY_SEA_WATER_BUCKET);

  // public static final Supplier<FlowingFluid> COCONUT_MILK = FLUIDS.register("coconut_milk",
  //         () -> new ForgeFlowingFluid.Source(FluidInit.COCONUT_MILK_FLUID_PROPERTIES));
  // public static final Supplier<FlowingFluid> FLOWING_COCONUT_MILK = FLUIDS.register("flowing_coconut_milk",
  //         () -> new ForgeFlowingFluid.Flowing(FluidInit.COCONUT_MILK_FLUID_PROPERTIES));

  // public static final ForgeFlowingFluid.Properties COCONUT_MILK_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
  //         FluidTypesInit.COCONUT_MILK_FLUID_TYPE, COCONUT_MILK, FLOWING_COCONUT_MILK)
  //         .slopeFindDistance(2).levelDecreasePerBlock(1).block(BlockInit.COCONUT_MILK)
  //         .bucket(ItemInit.COCONUT_MILK_BUCKET);

    public static void init(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
