package com.mystic.atlantis.init;

import com.mystic.atlantis.items.armor.BasicArmorMaterial;
import com.mystic.atlantis.items.armor.ItemArmorAtlantis;
import com.mystic.atlantis.items.item.AtlanteanCrystal;
import com.mystic.atlantis.items.item.DefaultItem;
import com.mystic.atlantis.items.tools.*;
import com.mystic.atlantis.util.Reference;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemInit
{
    public static final ItemGroup CREATIVE_TAB_ATLANTIS = FabricItemGroupBuilder.create(new Identifier("atlantis")).icon(() -> new ItemStack(BlockInit.CHISELED_GOLDEN_AQUAMARINE)).build().setName("atlantis:textures/gui/tab_atlantis.png");


    public static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Reference.MODID, name), item);
    }

    //ITEMS
    public static final Item AQUAMARINE_GEM = register("aquamarine_gem", new DefaultItem());
    public static final Item ORB_OF_ATLANTIS = register("orb_of_atlantis", new DefaultItem());
    public static final Item ATLANTEAN_CRYSTAL = register("atlantean_crystal", new AtlanteanCrystal());
    public static final Item OCEAN_STONE = register("ocean_stone", new DefaultItem());
    public static final Item DROP_OF_ATLANTIS = register("drop_of_atlantis", new DefaultItem());
    public static final Item BROWN_WROUGHT_PATCHES = register("brown_wrought_patches", new DefaultItem());

    //TOOLS
    public static final AxeItem AXE_AQUMARINE = (AxeItem) register("axe_aquamarine", new AquamarineAxe(ToolInit.AQUAMARINE, 4));
    public static final PickaxeItem PICKAXE_AQUMARINE = (PickaxeItem) register("pickaxe_aquamarine", new AquamarinePickaxe(ToolInit.AQUAMARINE, 3));
    public static final ShovelItem SHOVEL_AQUMARINE = (ShovelItem) register("shovel_aquamarine", new AquamarineShovel(ToolInit.AQUAMARINE, 1));
    public static final HoeItem HOE_AQUMARINE = (HoeItem) register("hoe_aquamarine", new AquamarineHoe(ToolInit.AQUAMARINE, 2));
    public static final SwordItem SWORD_AQUMARINE = (SwordItem) register("sword_aquamarine", new AquamarineSword(ToolInit.AQUAMARINE, 6));

    //ARMOR
    public static final ArmorItem AQUAMARINE_HELMET = (ArmorItem) register("aquamarine_helmet", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.HEAD, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final ArmorItem AQUAMARINE_CHESTPLATE = (ArmorItem) register("aquamarine_chestplate", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.CHEST, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final ArmorItem AQUAMARINE_LEGGINGS= (ArmorItem) register("aquamarine_leggings", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.LEGS, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final ArmorItem AQUAMARINE_BOOTS = (ArmorItem) register("aquamarine_boots", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.FEET, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final ArmorItem BROWN_WROUGHT_HELMET = (ArmorItem) register("brown_wrought_helmet", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.HEAD, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final ArmorItem BROWN_WROUGHT_CHESTPLATE = (ArmorItem) register("brown_wrought_chestplate", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.CHEST, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final ArmorItem BROWN_WROUGHT_LEGGINGS= (ArmorItem) register("brown_wrought_leggings", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.LEGS, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
    public static final ArmorItem BROWN_WROUGHT_BOOTS = (ArmorItem) register("brown_wrought_boots", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.FEET, new Item.Settings().group(CREATIVE_TAB_ATLANTIS)));
}
