package com.mystic.atlantis.init;

import com.mystic.atlantis.inventory.LinguisticMenu;
import com.mystic.atlantis.inventory.WritingMenu;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MenuTypeInit {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, Reference.MODID);

  //  public static final Supplier<MenuType<CrystalGeneratorMenu>> CRYSTAL_GENERATOR_MENU =
  //          registerMenuType(CrystalGeneratorMenu::new, "crystal_generator_menu");
    public static final Supplier<MenuType<LinguisticMenu>> LINGUISTIC = CONTAINERS.register("linguistic", () -> new MenuType<>(LinguisticMenu::new, FeatureFlagSet.of()));
    public static final Supplier<MenuType<WritingMenu>> WRITING = CONTAINERS.register("writing", () -> new MenuType<>(WritingMenu::new, FeatureFlagSet.of()));

    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return CONTAINERS.register(name, () -> new MenuType<>(factory, FeatureFlagSet.of()));
    }

    public static void init(IEventBus bus) {
        CONTAINERS.register(bus);
    }
}
