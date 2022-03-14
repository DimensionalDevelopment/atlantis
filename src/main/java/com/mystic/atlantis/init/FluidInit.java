package com.mystic.atlantis.init;

import com.mystic.atlantis.fluids.JetstreamWaterFluid;
import com.mystic.atlantis.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

public class FluidInit<T extends Block> {
    public static final HashMap<RegistryObject<? extends Block>, CustomRenderType> CUSTOM_RENDER_TYPES = new HashMap<RegistryObject<? extends Block>, CustomRenderType>();
    public static final ResourceLocation FLUID_STILL_TEXTURE = new ResourceLocation("minecraft", "block/" + "water" + "_still");
    public static final ResourceLocation FLUID_FLOWING_TEXTURE = new ResourceLocation("minecraft", "block/" + "water" + "_flow");
    public static final ResourceLocation FLUID_OVERLAY_TEXTURE = new ResourceLocation("minecraft", "block/" + "water" + "_overlay");
    private static DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Fluid.class, Reference.MODID);
    public static final RegistryObject<FlowingFluid> STILL_JETSTREAM_WATER = FLUIDS.register("jetstream_water", () -> new JetstreamWaterFluid.Still());
    public static final RegistryObject<Item> JETSTREAM_WATER_BUCKET = ItemInit.register("jetstream_water_bucket", ()->new BucketItem(STILL_JETSTREAM_WATER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<LiquidBlock> JETSTREAM_WATER = BlockInit.registerFluidBlock("jetstream_water", () -> new LiquidBlock(STILL_JETSTREAM_WATER, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<FlowingFluid> FLOWING_JETSTREAM_WATER = FLUIDS.register("flowing_jetstream_water", () -> new JetstreamWaterFluid.Flowing());
    public static final ForgeFlowingFluid.Properties JETSTREAM_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(STILL_JETSTREAM_WATER, FLOWING_JETSTREAM_WATER,
            FluidAttributes.Water.builder(FLUID_STILL_TEXTURE, FLUID_FLOWING_TEXTURE).overlay(FLUID_OVERLAY_TEXTURE).viscosity(1000))
            .bucket(JETSTREAM_WATER_BUCKET).canMultiply().block(JETSTREAM_WATER);

    public static void init(IEventBus bus) {
        FLUIDS.register(bus);
    }

    enum CustomRenderType {
        CUTOUT,
        CUTOUT_MIPPED,
        TRANSLUCENT
    }
}
