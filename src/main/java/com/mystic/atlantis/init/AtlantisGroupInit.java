package com.mystic.atlantis.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AtlantisGroupInit {
    public static final CreativeModeTab MAIN = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), "atlantis.general") {
        @Override
        public ItemStack makeIcon() {
            return BlockInit.CHISELED_GOLDEN_AQUAMARINE.get().asItem().getDefaultInstance();
        }
    }.setBackgroundImage(new ResourceLocation("atlantis", "textures/gui/tab_atlantis.png"));

    public static final CreativeModeTab GLYPH = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), "atlantis.glyph") {
            @Override
            public ItemStack makeIcon() {
                return BlockInit.LINGUISTIC_BLOCK.get().asItem().getDefaultInstance();
            }
    }.setBackgroundImage(new ResourceLocation("atlantis", "textures/gui/tab_atlantis.png"));


    public static void init(){
    }
}
