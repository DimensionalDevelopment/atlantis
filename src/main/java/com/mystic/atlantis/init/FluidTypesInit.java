package com.mystic.atlantis.init;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.fluids.BaseFluidType;
import com.mystic.atlantis.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FastColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

public class FluidTypesInit {
    public static final ResourceLocation WATER_STILL = Atlantis.id("block/water_still");
    public static final ResourceLocation WATER_FLOWING = Atlantis.id("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY = Atlantis.id("block/water_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, Reference.MODID);

    public static final DeferredHolder<FluidType, FluidType> JETSTREAM_WATER_FLUID_TYPE = registerJetstreamWaterType("jetstream_water",
            FluidType.Properties.create().lightLevel(0).density(15).viscosity(1000).sound(SoundAction.get("drink"),
                    SoundEvents.GENERIC_DRINK));
    public static final DeferredHolder<FluidType, FluidType> SALTY_SEAWATER_FLUID_TYPE = registerSaltySeaWaterFluidType("salty_seawater",
            FluidType.Properties.create().lightLevel(0).density(0).viscosity(1000).sound(SoundAction.get("drink"),
                    SoundEvents.GENERIC_DRINK));

    private static DeferredHolder<FluidType, FluidType> registerJetstreamWaterType(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL, WATER_FLOWING, WATER_OVERLAY,
                FastColor.ARGB32.color(255, 169, 255, 208), new Vector3f(169f / 255f, 1f, 208f / 255f), properties));
    }

    private static DeferredHolder<FluidType, FluidType> registerSaltySeaWaterFluidType(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL, WATER_FLOWING, WATER_OVERLAY,
                FastColor.ARGB32.color(255, 10, 96, 208), new Vector3f(10f / 255f, 96f / 255f, 208f / 255f), properties));
    }

    public static void init(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}