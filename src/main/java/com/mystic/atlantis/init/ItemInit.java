package com.mystic.atlantis.init;

import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.entities.CrabEntity;
import com.mystic.atlantis.event.AtlantisSoundEvents;
import com.mystic.atlantis.items.armor.BasicArmorMaterial;
import com.mystic.atlantis.items.armor.ItemArmorAtlantis;
import com.mystic.atlantis.items.item.AtlanteanCrystal;
import com.mystic.atlantis.items.item.CrabEntityBucketItem;
import com.mystic.atlantis.items.item.CrabLegsItem;
import com.mystic.atlantis.items.item.DefaultItem;
import com.mystic.atlantis.items.musicdisc.AtlantisMusicDisc;
import com.mystic.atlantis.items.tools.*;
import com.mystic.atlantis.util.Reference;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ItemInit
{

    public static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Reference.MODID, name), item);
    }

    private static final Item.Settings ATLANTIS_SETTINGS = new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).maxCount(1);

    //SPAWN EGGS
    public static final Item ATLANTEAN_CRAB_EGG = register("atlantean_crab_egg", new SpawnEggItem(AtlantisEntities.CRAB, 0x800002, 0xff0f45, new Item.Settings().group(ItemGroup.MISC)));
    public static final Item ATLANTEAN_JELLYFISH_EGG = register("atlantean_jellyfish_egg", new SpawnEggItem(AtlantisEntities.JELLYFISH, 0x00458a, 0x0582ff, new Item.Settings().group(ItemGroup.MISC)));

    //MUSIC DISC
    public static final Item PANBEE = register("panbee", new AtlantisMusicDisc(15, AtlantisSoundEvents.PANBEE, ATLANTIS_SETTINGS));

    //ITEMS
    public static final Item AQUAMARINE_GEM = register("aquamarine_gem", new DefaultItem());
    public static final Item ORB_OF_ATLANTIS = register("orb_of_atlantis", new DefaultItem());
    public static final Item ATLANTEAN_CRYSTAL = register("atlantean_crystal", new AtlanteanCrystal());
    public static final Item OCEAN_STONE = register("ocean_stone", new DefaultItem());
    public static final Item DROP_OF_ATLANTIS = register("drop_of_atlantis", new DefaultItem());
    public static final Item BROWN_WROUGHT_PATCHES = register("brown_wrought_patches", new DefaultItem());
    public static final Item CRAB_LEGS = register("crab_legs", new CrabLegsItem(new Item.Settings()));

    //Entity Buckets
    public static final Item CRAB_BUCKET = register("crab_bucket", new CrabEntityBucketItem(AtlantisEntities.CRAB, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, (new Item.Settings()).maxCount(1)));

    //TOOLS
    public static final AxeItem AXE_AQUMARINE = (AxeItem) register("axe_aquamarine", new AquamarineAxe(ToolInit.AQUAMARINE, 4));
    public static final PickaxeItem PICKAXE_AQUMARINE = (PickaxeItem) register("pickaxe_aquamarine", new AquamarinePickaxe(ToolInit.AQUAMARINE, 3));
    public static final ShovelItem SHOVEL_AQUMARINE = (ShovelItem) register("shovel_aquamarine", new AquamarineShovel(ToolInit.AQUAMARINE, 1));
    public static final HoeItem HOE_AQUMARINE = (HoeItem) register("hoe_aquamarine", new AquamarineHoe(ToolInit.AQUAMARINE, 2));
    public static final SwordItem SWORD_AQUMARINE = (SwordItem) register("sword_aquamarine", new AquamarineSword(ToolInit.AQUAMARINE, 6));

    //ARMOR
    public static final ArmorItem AQUAMARINE_HELMET = (ArmorItem) register("aquamarine_helmet", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.HEAD, new Item.Settings()));
    public static final ArmorItem AQUAMARINE_CHESTPLATE = (ArmorItem) register("aquamarine_chestplate", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.CHEST, new Item.Settings()));
    public static final ArmorItem AQUAMARINE_LEGGINGS= (ArmorItem) register("aquamarine_leggings", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.LEGS, new Item.Settings()));
    public static final ArmorItem AQUAMARINE_BOOTS = (ArmorItem) register("aquamarine_boots", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.FEET, new Item.Settings()));
    public static final ArmorItem BROWN_WROUGHT_HELMET = (ArmorItem) register("brown_wrought_helmet", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.HEAD, new Item.Settings()));
    public static final ArmorItem BROWN_WROUGHT_CHESTPLATE = (ArmorItem) register("brown_wrought_chestplate", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.CHEST, new Item.Settings()));
    public static final ArmorItem BROWN_WROUGHT_LEGGINGS= (ArmorItem) register("brown_wrought_leggings", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.LEGS, new Item.Settings()));
    public static final ArmorItem BROWN_WROUGHT_BOOTS = (ArmorItem) register("brown_wrought_boots", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.FEET, new Item.Settings()));
}
