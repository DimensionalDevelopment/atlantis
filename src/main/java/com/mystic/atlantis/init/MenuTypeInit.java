package com.mystic.atlantis.init;

import com.mystic.atlantis.inventory.LinguisticMenu;
import com.mystic.atlantis.inventory.WritingMenu;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuTypeInit {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, Reference.MODID);
    public static final DeferredHolder<MenuType<?>, MenuType<LinguisticMenu>> LINGUISTIC = CONTAINERS.register("linguistic", () -> new MenuType<>(LinguisticMenu::new, FeatureFlagSet.of()));
    public static final DeferredHolder<MenuType<?>, MenuType<WritingMenu>> WRITING = CONTAINERS.register("writing", () -> new MenuType<>(WritingMenu::new, FeatureFlagSet.of()));

//    private static <T extends AbstractContainerMenu> DeferredRegister<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
//        return CONTAINERS.register(name, () -> new MenuType<AbstractContainerMenu>());
//    }

    public static void init(IEventBus bus) {
        CONTAINERS.register(bus);
    }
}
