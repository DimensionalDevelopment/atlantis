package com.mystic.atlantis.init;

import com.mystic.atlantis.fluids.JetstreamWaterFluid;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.Registry;
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
import net.minecraftforge.registries.DeferredRegister;

public class FluidInit {
        private static DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Fluid.class, Reference.MODID);

        public static void init(IEventBus bus) {
                FLUIDS.register(bus);
        }

        public static final FlowingFluid STILL_JETSTREAM_WATER = Registry.register(Registry.FLUID, new ResourceLocation(Reference.MODID, "jetstream_water"), new JetstreamWaterFluid.Still());
        public static final FlowingFluid FLOWING_JETSTREAM_WATER = Registry.register(Registry.FLUID, new ResourceLocation(Reference.MODID, "flowing_jetstream_water"), new JetstreamWaterFluid.Flowing());
        public static final Item JETSTREAM_WATER_BUCKET = ItemInit.register("jetstream_water_bucket", new BucketItem(STILL_JETSTREAM_WATER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        public static final Block JETSTREAM_WATER = BlockInit.blockOnlyRegistry("jetstream_water", new LiquidBlock(STILL_JETSTREAM_WATER, BlockBehaviour.Properties.copy(Blocks.WATER)){});
}
