package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AtlantisBlockStateProvider extends BlockStateProvider {
    public AtlantisBlockStateProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, "atlantis", existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.horizontalBlock(BlockInit.WRITING_BLOCK.get(), new ModelFile.ExistingModelFile(Atlantis.id("block/writing_block"), itemModels().existingFileHelper));
        this.simpleBlock(BlockInit.ORICHALCUM_BLOCK.get());

        this.simpleBlock(BlockInit.BLACK_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.BLUE_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.BROWN_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.CYAN_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.GRAY_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.GREEN_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.LIGHT_BLUE_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.LIGHT_GRAY_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.LIME_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.MAGENTA_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.ORANGE_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.PINK_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.PURPLE_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.RED_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.WHITE_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.YELLOW_PEARL_BLOCK.get());
    }
}
