package com.mystic.atlantis.init;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class AtlantisGroupInit {
    public static DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.literal("atlantis.general"))
            .icon(() -> BlockInit.CHISELED_GOLDEN_AQUAMARINE.get().asItem().getDefaultInstance())
            .displayItems((pParameters, pOutput) -> pOutput.acceptAll(BlockInit.tabMap.get(Atlantis.id("main")).stream().map(RegistryObject::get).map(Item::getDefaultInstance).toList()))
            .withBackgroundLocation(new ResourceLocation("atlantis", "textures/gui/tab_atlantis.png"))
            .build());

    public static final RegistryObject<CreativeModeTab> GLYPH = CREATIVE_TABS.register("glyph", () -> CreativeModeTab.builder()
            .title(Component.literal("atlantis.glyph"))
            .icon(() -> BlockInit.LINGUISTIC_BLOCK.get().asItem().getDefaultInstance())
            .displayItems((pParameters, pOutput) -> pOutput.acceptAll(BlockInit.tabMap.get(Atlantis.id("glyph")).stream().map(RegistryObject::get).map(Item::getDefaultInstance).toList()))
            .withBackgroundLocation(new ResourceLocation("atlantis", "textures/gui/tab_atlantis.png"))
            .build());


    public static void init(IEventBus bus){
        CREATIVE_TABS.register(bus);
        bus.addListener((Consumer<BuildCreativeModeTabContentsEvent>) event -> {
            if(BlockInit.tabMap.containsKey(event.getTabKey().location())) {
                BlockInit.tabMap.get(event.getTabKey().location()).forEach(event::accept);
            }
        });
    }
}
