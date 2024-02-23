package com.mystic.atlantis.datagen;

import com.mystic.atlantis.blocks.BlockType;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

import java.util.function.Supplier;

//TODO Convert lang to pure data gen in the future. This will complain about duplicates if run without removing the existing en_us.json first
public class AtlantisEnglishLanguageProvider extends LanguageProvider {
    public AtlantisEnglishLanguageProvider(PackOutput generator) {
        super(generator, "atlantis", "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addBlocksFromType(BlockInit.SEA_GLASS, "Sea Glass");
        this.addBlocksFromType(BlockInit.RED_SEA_GLASS, "Red Sea Glass");
        this.addBlocksFromType(BlockInit.ORANGE_SEA_GLASS, "Orange Sea Glass");
        this.addBlocksFromType(BlockInit.YELLOW_SEA_GLASS, "Yellow Sea Glass");
        this.addBlocksFromType(BlockInit.GREEN_SEA_GLASS, "Green Sea Glass");
        this.addBlocksFromType(BlockInit.LIME_SEA_GLASS, "Lime Sea Glass");
        this.addBlocksFromType(BlockInit.LIGHT_BLUE_SEA_GLASS, "Light Blue Sea Glass");
        this.addBlocksFromType(BlockInit.LIGHT_GRAY_SEA_GLASS, "Light Gray Sea Glass");
        this.addBlocksFromType(BlockInit.MAGENTA_SEA_GLASS, "Magenta Sea Glass");
        this.addBlocksFromType(BlockInit.PURPLE_SEA_GLASS, "Purple Sea Glass");
        this.addBlocksFromType(BlockInit.PINK_SEA_GLASS, "Pink Sea Glass");
        this.addBlocksFromType(BlockInit.BLACK_SEA_GLASS, "Black Sea Glass");
        this.addBlocksFromType(BlockInit.GRAY_SEA_GLASS, "Gray Sea Glass");
        this.addBlocksFromType(BlockInit.WHITE_SEA_GLASS, "White Sea Glass");
        this.addBlocksFromType(BlockInit.BROWN_SEA_GLASS, "Brown Sea Glass");
        this.addBlocksFromType(BlockInit.CYAN_SEA_GLASS, "Cyan Sea Glass");
        this.addBlocksFromType(BlockInit.MONOCHROMATIC_SEA_GLASS, "Monochromatic Sea Glass");
        this.addBlocksFromType(BlockInit.MULTICOLOR_SEA_GLASS, "Multicolor Sea Glass");
    }

    private void addRecord(Supplier<Item> record, String s) {
        addItem(record, "Music Disc");
        add("item." + record.get() + "." + record.get() + ".desc", s);
    }

    private void addBlocksFromType(BlockType type, String base) {
        addBlock(type.block(), base);
        if(type.door() != null) addBlock(type.door(), base + " Door");
        if(type.button() != null) addBlock(type.button(), base + " Button");
        if(type.slab() != null) addBlock(type.slab(), base + " Slab");
        if(type.fence() != null) addBlock(type.fence(), base + " Fence");
        if(type.fenceGate() != null) addBlock(type.fenceGate(), base + "Fence Gate");
        if(type.pressurePlate() != null) addBlock(type.pressurePlate(), base + "Pressure Plate");
        if(type.trapDoor() != null) addBlock(type.trapDoor(), base + " Trapdoor");
        if(type.wall() != null) addBlock(type.wall(), base + " Wall");
    }

    private void addItem(Supplier<Item> registryObject) {
        addItem(registryObject, WordUtils.capitalizeFully(registryObject.get().toString().replace("_", " ")));
    }

    private void addBlock(Supplier<Block> registryObject) {
        addBlock(registryObject, WordUtils.capitalizeFully(registryObject.get().toString().replace("_", " ")));
    }


    private void add(Supplier<CreativeModeTab> tab, String entry) {
        this.add("itemGroup." + tab.get(), entry);
    }
}
