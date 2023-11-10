package com.mystic.atlantis.init;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AtlantisGroupInit {
    public static DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MODID);

    public static final List<Supplier<? extends ItemLike>> MAIN_BLOCKS = new ArrayList<>();
    public static final List<Supplier<? extends ItemLike>> GLYPH_BLOCKS = new ArrayList<>();
    public static final List<Supplier<? extends ItemLike>> MAIN_ITEMS = new ArrayList<>();
    public static final List<Supplier<? extends ItemLike>> GLYPH_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.atlantis.general"))
            .icon(BlockInit.CHISELED_GOLDEN_AQUAMARINE.get().asItem()::getDefaultInstance)
            .withSearchBar(58)
            .hideTitle()
            .displayItems((pParameters, pOutput) -> {
                    MAIN_BLOCKS.forEach(itemLike -> pOutput.accept(itemLike.get()));
                    MAIN_ITEMS.forEach(itemLike -> pOutput.accept(itemLike.get()));
            })
            .withBackgroundLocation(new ResourceLocation("atlantis", "textures/gui/atlantis_tab.png"))
            .build());

    public static final RegistryObject<CreativeModeTab> GLYPH = CREATIVE_TABS.register("glyph", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.atlantis.glyph"))
            .icon(BlockInit.LINGUISTIC_BLOCK.get().asItem()::getDefaultInstance)
            .withSearchBar(58)
            .hideTitle()
            .displayItems((pParameters, pOutput) -> {
                GLYPH_BLOCKS.forEach(itemLike -> pOutput.accept(itemLike.get()));
                GLYPH_ITEMS.forEach(itemLike -> pOutput.accept(itemLike.get()));
            })
            .withBackgroundLocation(new ResourceLocation("atlantis", "textures/gui/glyph_tab.png"))
            .build());


    public static <T extends Item> RegistryObject<T> addToMainTab (RegistryObject<T> itemLike) {
        MAIN_BLOCKS.add(itemLike);
        return itemLike;
    }

    public static <T extends Item> RegistryObject<T> addToGylphTab (RegistryObject<T> itemLike) {
        GLYPH_BLOCKS.add(itemLike);
        return itemLike;
    }

    public static <T extends Item> RegistryObject<T> addToMainTabItems (RegistryObject<T> itemLike) {
        MAIN_ITEMS.add(itemLike);
        return itemLike;
    }

    public static <T extends Item> RegistryObject<T> addToGylphTabItems (RegistryObject<T> itemLike) {
        GLYPH_ITEMS.add(itemLike);
        return itemLike;
    }

    public static void init(IEventBus bus){
        CREATIVE_TABS.register(bus);
    }
}
