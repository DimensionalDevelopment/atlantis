package com.mystic.atlantis.init;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.util.Reference;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AtlantisGroupInit {
    public static DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MODID);

    public static final List<Supplier<? extends ItemLike>> MAIN_BLOCKS = new ArrayList<>();
    public static final List<Supplier<? extends ItemLike>> GLYPH_BLOCKS = new ArrayList<>();
    public static final List<Supplier<? extends ItemLike>> MAIN_ITEMS = new ArrayList<>();
    public static final List<Supplier<? extends ItemLike>> GLYPH_ITEMS = new ArrayList<>();

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.atlantis.general"))
            .icon(BlockInit.CHISELED_GOLDEN_AQUAMARINE.get().asItem()::getDefaultInstance)
            .hideTitle()
            .displayItems((pParameters, pOutput) -> {
                    MAIN_BLOCKS.forEach(itemLike -> pOutput.accept(itemLike.get()));
                    MAIN_ITEMS.forEach(itemLike -> pOutput.accept(itemLike.get()));
            })
            .backgroundTexture(Atlantis.id("textures/gui/atlantis_tab.png"))
            .withSearchBar(58)
            .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GLYPH = CREATIVE_TABS.register("glyph", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.atlantis.glyph"))
            .icon(BlockInit.LINGUISTIC_TABLE.get().asItem()::getDefaultInstance)
            .hideTitle()
            .displayItems((pParameters, pOutput) -> {
                GLYPH_BLOCKS.forEach(itemLike -> pOutput.accept(itemLike.get()));
                GLYPH_ITEMS.forEach(itemLike -> pOutput.accept(itemLike.get()));
            })
            .backgroundTexture(Atlantis.id("textures/gui/glyph_tab.png"))
            .withSearchBar(58)
            .build());


    public static <T extends Item> DeferredItem<T> addToMainTab (DeferredItem<T> itemLike) {
        MAIN_BLOCKS.add(itemLike);
        return itemLike;
    }

    public static <T extends Item> DeferredItem<T> addToGylphTab (DeferredItem<T> itemLike) {
        GLYPH_BLOCKS.add(itemLike);
        return itemLike;
    }

    public static <T extends Item> DeferredItem<T> addToMainTabItems (DeferredItem<T> itemLike) {
        MAIN_ITEMS.add(itemLike);
        return itemLike;
    }

    public static <T extends Item> DeferredItem<T> addToGylphTabItems (DeferredItem<T> itemLike) {
        GLYPH_ITEMS.add(itemLike);
        return itemLike;
    }

    public static void init(IEventBus bus){
        CREATIVE_TABS.register(bus);
    }
}