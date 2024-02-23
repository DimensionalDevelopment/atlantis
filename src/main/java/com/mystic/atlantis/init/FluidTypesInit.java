package com.mystic.atlantis.init;

import com.mystic.atlantis.fluids.BaseFluidType;
import com.mystic.atlantis.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class FluidTypesInit {
    public static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, Reference.MODID);

    public static final Supplier<FluidType> JETSTREAM_WATER_FLUID_TYPE = registerJetstreamWaterType("jetstream_water",
            FluidType.Properties.create().lightLevel(0).density(15).viscosity(1000).sound(SoundAction.get("drink"),
                    SoundEvents.GENERIC_DRINK));
    public static final Supplier<FluidType> SALTY_SEA_WATER_FLUID_TYPE = registerSaltySeaWaterFluidType("salty_sea_water",
            FluidType.Properties.create().lightLevel(0).density(0).viscosity(1000).sound(SoundAction.get("drink"),
                    SoundEvents.GENERIC_DRINK));

    private static Supplier<FluidType> registerJetstreamWaterType(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL, WATER_FLOWING, WATER_OVERLAY,
                0x52A9FFD0, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), properties));
    }

    private static Supplier<FluidType> registerSaltySeaWaterFluidType(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL, WATER_FLOWING, WATER_OVERLAY,
                0x100A60D0, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), properties));
    }

    //public static final Supplier<FluidType> COCONUT_MILK_FLUID_TYPE = registerMilkType("coconut_milk", new FluidType(FluidType.Properties.create()
    //        .canSwim(false)
    //        .canDrown(true)
    //        .pathType(BlockPathTypes.WATER)
    //        .adjacentPathType(null)
    //        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
    //        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
    //        .lightLevel(0)
    //        .density(10)) {
    //    @Override
    //    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
    //        consumer.accept(new IClientFluidTypeExtensions() {
    //            @Override
    //            public ResourceLocation getStillTexture() {
    //                return WATER_STILL;
    //            }
//
    //            @Override
    //            public ResourceLocation getFlowingTexture() {
    //                return WATER_FLOWING;
    //            }
//
    //            @Override
    //            public @NotNull ResourceLocation getOverlayTexture() {
    //                return WATER_OVERLAY;
    //            }
    //        });
    //    }
    //});

    private static Supplier<FluidType> registerMilkType(String name, FluidType fluidType) {
        return FLUID_TYPES.register(name, () -> fluidType);
    }

    public static void init(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}