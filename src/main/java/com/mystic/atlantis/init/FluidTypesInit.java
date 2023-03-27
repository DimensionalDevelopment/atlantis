package com.mystic.atlantis.init;

import com.mojang.math.Vector3f;
import com.mystic.atlantis.fluids.BaseFluidType;
import com.mystic.atlantis.util.Reference;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluidTypesInit {
    public static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Reference.MODID);

    public static final RegistryObject<FluidType> JETSTREAM_WATER_FLUID_TYPE = registerJetstreamWaterType("jetstream_water",
            FluidType.Properties.create().lightLevel(0).density(15).viscosity(1000).sound(SoundAction.get("drink"),
                    SoundEvents.GENERIC_DRINK));

    public static final RegistryObject<FluidType> SALTY_SEA_WATER_FLUID_TYPE = registerSaltySeaWaterFluidType("salty_sea_water",
            FluidType.Properties.create().lightLevel(0).density(0).viscosity(1000).sound(SoundAction.get("drink"),
                    SoundEvents.GENERIC_DRINK));

    private static RegistryObject<FluidType> registerJetstreamWaterType(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL, WATER_FLOWING, WATER_OVERLAY,
                0x52A9FFD0, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), properties));
    }

    private static RegistryObject<FluidType> registerSaltySeaWaterFluidType(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL, WATER_FLOWING, WATER_OVERLAY,
                0x100A60D0, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), properties));
    }

    public static void init(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}