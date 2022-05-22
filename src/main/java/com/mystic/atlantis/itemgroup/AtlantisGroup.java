package com.mystic.atlantis.itemgroup;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AtlantisGroup {
    public static CreativeModeTab MAIN = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), "atlantis.general") {
        @Override
        public ItemStack makeIcon() {
            return BlockInit.CHISELED_GOLDEN_AQUAMARINE.get().asItem().getDefaultInstance();
        }
    }.setBackgroundImage(new ResourceLocation("atlantis", "textures/gui/tab_atlantis.png"));


    public static CreativeModeTab GLYPH = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), "atlantis.glyph") {
            @Override
            public ItemStack makeIcon() {
                return BlockInit.LINGUISTIC_BLOCK.get().asItem().getDefaultInstance();
            }
    }.setBackgroundImage(new ResourceLocation("atlantis", "textures/gui/tab_atlantis.png"));


    public static void init(){
    }
}
