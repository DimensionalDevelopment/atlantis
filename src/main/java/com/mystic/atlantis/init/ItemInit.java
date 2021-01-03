package com.mystic.atlantis.init;

import com.mystic.atlantis.items.item.AtlanteanCrystal;
import com.mystic.atlantis.items.item.ItemBase;
import com.mystic.atlantis.items.item.OrbOfAtlantis;
import com.mystic.atlantis.items.tools.*;
import com.mystic.atlantis.util.Reference;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit
{
    public static final ItemGroup CREATIVE_TAB_ATLANTIS = new ItemGroup("atlantis") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlockInit.ANCIENT_ACACIA_WOOD_MOSS.get());
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }

        @Override
        public boolean hasScrollbar() {
            return true;
        }

        @Override
        public ResourceLocation getBackgroundImage() {
            return new ResourceLocation("minecraft", "textures/gui/container/creative_inventory/tab_item_search.png");
        }
    };

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);

    //ITEMS
    public static final RegistryObject<Item> AQUAMARINE_INGOT = ITEMS.register("aquamarine_ingot", ItemBase::new);
    public static final RegistryObject<Item> ORB_OF_ATLANTIS = ITEMS.register("orb_of_atlantis", OrbOfAtlantis::new);
    public static final RegistryObject<Item> ATLANTEAN_CRYSTAL = ITEMS.register("atlantean_crystal", AtlanteanCrystal::new);

    //TOOLS
    public static final RegistryObject<AxeItem> AXE_AQUMARINE = ITEMS.register("axe_aquamarine", () -> new AquamarineAxe(ToolInit.AQUAMARINE, 4));
    public static final RegistryObject<PickaxeItem> PICKAXE_AQUMARINE = ITEMS.register("pickaxe_aquamarine", () -> new AquamarinePickaxe(ToolInit.AQUAMARINE, 3));
    public static final RegistryObject<ShovelItem> SHOVEL_AQUMARINE = ITEMS.register("shovel_aquamarine", () -> new AquamarineShovel(ToolInit.AQUAMARINE, 1));
    public static final RegistryObject<HoeItem> HOE_AQUMARINE = ITEMS.register("hoe_aquamarine", () -> new AquamarineHoe(ToolInit.AQUAMARINE, 2));
    public static final RegistryObject<SwordItem> SWORD_AQUMARINE = ITEMS.register("sword_aquamarine", () -> new AquamarineSword(ToolInit.AQUAMARINE, 6));
}
