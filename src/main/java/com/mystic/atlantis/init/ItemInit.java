package com.mystic.atlantis.init;

import com.mystic.atlantis.items.armor.BasicArmorMaterial;
import com.mystic.atlantis.items.armor.ItemArmorAtlantis;
import com.mystic.atlantis.items.item.AtlanteanCrystal;
import com.mystic.atlantis.items.item.ItemBase;
import com.mystic.atlantis.items.item.OrbOfAtlantis;
import com.mystic.atlantis.items.tools.*;
import com.mystic.atlantis.util.Reference;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit
{
    public static final ItemGroup CREATIVE_TAB_ATLANTIS = new ItemGroup("atlantis") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlockInit.CHISELED_GOLDEN_AQUAMARINE.get());
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
        public Identifier getBackgroundImage() {
            return new Identifier("minecraft", "textures/gui/container/creative_inventory/tab_item_search.png");
        }
    };

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);

    //ITEMS
    public static final RegistryObject<Item> AQUAMARINE_INGOT = ITEMS.register("aquamarine_ingot", ItemBase::new);
    public static final RegistryObject<Item> ORB_OF_ATLANTIS = ITEMS.register("orb_of_atlantis", OrbOfAtlantis::new);
    public static final RegistryObject<Item> ATLANTEAN_CRYSTAL = ITEMS.register("atlantean_crystal", AtlanteanCrystal::new);
    public static final RegistryObject<Item> OCEAN_STONE = ITEMS.register("ocean_stone", ItemBase::new);
    public static final RegistryObject<Item> DROP_OF_ATLANTIS = ITEMS.register("drop_of_atlantis", ItemBase::new);
    public static final RegistryObject<Item> BROWN_WROUGHT_PATCHES = ITEMS.register("brown_wrought_patches", ItemBase::new);

    //TOOLS
    public static final RegistryObject<AxeItem> AXE_AQUMARINE = ITEMS.register("axe_aquamarine", () -> new AquamarineAxe(ToolInit.AQUAMARINE, 4));
    public static final RegistryObject<PickaxeItem> PICKAXE_AQUMARINE = ITEMS.register("pickaxe_aquamarine", () -> new AquamarinePickaxe(ToolInit.AQUAMARINE, 3));
    public static final RegistryObject<ShovelItem> SHOVEL_AQUMARINE = ITEMS.register("shovel_aquamarine", () -> new AquamarineShovel(ToolInit.AQUAMARINE, 1));
    public static final RegistryObject<HoeItem> HOE_AQUMARINE = ITEMS.register("hoe_aquamarine", () -> new AquamarineHoe(ToolInit.AQUAMARINE, 2));
    public static final RegistryObject<SwordItem> SWORD_AQUMARINE = ITEMS.register("sword_aquamarine", () -> new AquamarineSword(ToolInit.AQUAMARINE, 6));

    //ARMOR
    public static final RegistryObject<ArmorItem> AQUAMARINE_HELMET = ITEMS.register("aquamarine_helmet", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.HEAD, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final RegistryObject<ArmorItem> AQUAMARINE_CHESTPLATE = ITEMS.register("aquamarine_chestplate", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.CHEST, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final RegistryObject<ArmorItem> AQUAMARINE_LEGGINGS= ITEMS.register("aquamarine_leggings", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.LEGS, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final RegistryObject<ArmorItem> AQUAMARINE_BOOTS = ITEMS.register("aquamarine_boots", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.FEET, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final RegistryObject<ArmorItem> BROWN_WROUGHT_HELMET = ITEMS.register("brown_wrought_helmet", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.HEAD, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final RegistryObject<ArmorItem> BROWN_WROUGHT_CHESTPLATE = ITEMS.register("brown_wrought_chestplate", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.CHEST, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final RegistryObject<ArmorItem> BROWN_WROUGHT_LEGGINGS= ITEMS.register("brown_wrought_leggings", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.LEGS, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final RegistryObject<ArmorItem> BROWN_WROUGHT_BOOTS = ITEMS.register("brown_wrought_boots", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.FEET, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
}
