package com.mystic.atlantis.datagen;

import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AtlantisMainProvider extends BlockStateProvider {
    private final PackOutput packOutput;
    private final ExistingFileHelper existingFileHelper;
    List<Proxied> providers;
    @SafeVarargs
    public AtlantisMainProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper, Function<AtlantisMainProvider, Proxied>... providers) {
        super(packOutput, Reference.MODID, existingFileHelper);
        this.packOutput = packOutput;
        this.existingFileHelper = existingFileHelper;
        this.providers = Stream.of(providers).map(provider -> provider.apply(this)).collect(Collectors.toList());
    }

    @Override
    protected void registerStatesAndModels() {
        for (Proxied provider : providers) {
            provider.registerStatesAndModels();
        }
    }

    public PackOutput getPackOutput() {
        return packOutput;
    }

    public ExistingFileHelper getExistingFileHelper() {
        return existingFileHelper;
    }

    public abstract static class Proxied extends BlockStateProvider {
        private final AtlantisMainProvider provider;

        public Proxied(AtlantisMainProvider provider) {
            super(provider.getPackOutput(), Reference.MODID, provider.getExistingFileHelper());
            this.provider = provider;
        }

        @Override
        public BlockModelProvider models() {
            return provider.models();
        }

        @Override
        public ItemModelProvider itemModels() {
            return provider.itemModels();
        }

        public VariantBlockStateBuilder getVariantBuilder(Block b) {
            return provider.getVariantBuilder(b);
        }

        public MultiPartBlockStateBuilder getMultipartBuilder(Block b) {
            return provider.getMultipartBuilder(b);
        }

        public ResourceLocation key(Block block) {
            return BuiltInRegistries.BLOCK.getKey(block);
        }

        public boolean registered(Block block) {
            return provider.registeredBlocks.containsKey(block);
        }

        public abstract void registerStatesAndModels();
    }

}
