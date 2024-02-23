package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class AtlantisBlockModelProvider extends BlockModelProvider {

    public AtlantisBlockModelProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, "atlantis", existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.cubeAll(BlockInit.ORICHALCUM_BLOCK);
        this.cubeBottomTop("writing_block", Atlantis.id("block/writing_table_side"), Atlantis.id("block/atlantean_planks"), Atlantis.id("block/writing_table_top"));
    }

    private void cubeAll(Supplier<Block> block) {
        this.cubeAll(String.valueOf(block.get().getName()), blockTexture(block.get().getLootTable()));
    }

    private ResourceLocation blockTexture(ResourceLocation loc) {
        return new ResourceLocation(loc.getNamespace(), "block/" + loc.getPath());
    }
}
