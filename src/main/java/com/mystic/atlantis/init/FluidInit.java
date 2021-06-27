package com.mystic.atlantis.init;

import com.mystic.atlantis.fluids.JetstreamWaterFluid;
import com.mystic.atlantis.util.Reference;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FluidInit{
        public static final FlowableFluid STILL_JETSTREAM_WATER = Registry.register(Registry.FLUID, new Identifier(Reference.MODID, "jetstream_water"), new JetstreamWaterFluid.Still());
        public static final FlowableFluid FLOWING_JETSTREAM_WATER = Registry.register(Registry.FLUID, new Identifier(Reference.MODID, "flowing_jetstream_water"), new JetstreamWaterFluid.Flowing());
        public static final Item JETSTREAM_WATER_BUCKET = Registry.register(Registry.ITEM, new Identifier(Reference.MODID, "jetstream_water_bucket"), new BucketItem(STILL_JETSTREAM_WATER, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        public static final Block JETSTREAM_WATER = Registry.register(Registry.BLOCK, new Identifier(Reference.MODID, "jetstream_water"), new FluidBlock(STILL_JETSTREAM_WATER, FabricBlockSettings.copy(Blocks.WATER)){});
}
