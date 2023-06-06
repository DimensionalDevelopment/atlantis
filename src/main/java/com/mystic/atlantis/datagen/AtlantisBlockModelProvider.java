package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class AtlantisBlockModelProvider extends BlockModelProvider {

    public AtlantisBlockModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, "atlantis", existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.cubeAll(BlockInit.ORICHALCUM_BLOCK);
        this.cubeBottomTop("writing_block", Atlantis.id("block/writing_table_side"), Atlantis.id("block/atlantean_planks"), Atlantis.id("block/writing_table_top"));
    }

    private void cubeAll(RegistryObject<Block> block) {
        this.cubeAll(block.getId().getPath(), blockTexture(block.getId()));
    }

    private ResourceLocation blockTexture(ResourceLocation loc) {
        return new ResourceLocation(loc.getNamespace(), "block/" + loc.getPath());
    }
}
