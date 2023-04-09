package com.mystic.atlantis.init;

import com.mystic.atlantis.inventory.CrystalGeneratorMenu;
import com.mystic.atlantis.inventory.LinguisticMenu;
import com.mystic.atlantis.inventory.WritingMenu;
import com.mystic.atlantis.util.Reference;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeInit {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Reference.MODID);

    public static final RegistryObject<MenuType<CrystalGeneratorMenu>> CRYSTAL_GENERATOR_MENU =
            registerMenuType(CrystalGeneratorMenu::new, "crystal_generator_menu");
    public static final RegistryObject<MenuType<LinguisticMenu>> LINGUISTIC = CONTAINERS.register("linguistic", () -> new MenuType<>(LinguisticMenu::new));
    public static final RegistryObject<MenuType<WritingMenu>> WRITING = CONTAINERS.register("writing", () -> new MenuType<>(WritingMenu::new));

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return CONTAINERS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void init(IEventBus bus) {
        CONTAINERS.register(bus);
    }
}
