package com.mystic.atlantis.inventory;

import com.mystic.atlantis.util.Reference;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeInit {
    public static DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Reference.MODID);

    public static RegistryObject<MenuType<LinguisticMenu>> LINGUISTIC = CONTAINERS.register("linguistic", () -> new MenuType<>(LinguisticMenu::new));

    public static RegistryObject<MenuType<WritingMenu>> WRITING = CONTAINERS.register("writing", () -> new MenuType<>(WritingMenu::new));

    public static void init(IEventBus bus) {
        CONTAINERS.register(bus);
    }
}
