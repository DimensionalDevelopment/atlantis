package com.mystic.atlantis.datagen;

import com.mystic.atlantis.init.FluidInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class AtlantisFluidTagsProvider extends FluidTagsProvider {
    public AtlantisFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, String modid, ExistingFileHelper efh) {
        super(output, lookup, modid, efh);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(FluidTags.WATER).replace(false).add(
                FluidInit.JETSTREAM_WATER.get(),
                FluidInit.SALTY_SEAWATER.get(),
                FluidInit.FLOWING_SALTY_SEAWATER.get(),
                FluidInit.FLOWING_JETSTREAM_WATER.get()
        );
    }
}
