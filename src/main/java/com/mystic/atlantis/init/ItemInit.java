package com.mystic.atlantis.init;

import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.event.AtlantisSoundEvents;
import com.mystic.atlantis.items.armor.BasicArmorMaterial;
import com.mystic.atlantis.items.armor.ItemArmorAtlantis;
import com.mystic.atlantis.items.armor.ItemArmorWrought;
import com.mystic.atlantis.items.item.*;
import com.mystic.atlantis.items.musicdisc.AtlantisMusicDisc;
import com.mystic.atlantis.items.tools.*;
import com.mystic.atlantis.util.Reference;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ItemInit
{

    public static void init(){}

    public static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Reference.MODID, name), item);
    }

    static <T extends Item> T register(T c, String id) {
        Registry.register(Registry.ITEM, new Identifier(Reference.MODID, id), c);
        return c;
    }

    private static final Item.Settings ATLANTIS_SETTINGS = new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).maxCount(1);

    public static final BlockItem UNDERWATER_SHROOM = register(new BlockItem(BlockInit.UNDERWATER_SHROOM_BLOCK, new Item.Settings()), "underwater_shroom");
    public static final BlockItem TUBER_UP = register(new BlockItem(BlockInit.TUBER_UP_BLOCK, new Item.Settings()), "tuber_up");
    public static final BlockItem BLUE_LILY = register(new BlockItem(BlockInit.BLUE_LILY_BLOCK, new Item.Settings()), "blue_lily");
    public static final BlockItem BURNT_DEEP = register(new BlockItem(BlockInit.BURNT_DEEP_BLOCK, new Item.Settings()), "burnt_deep");
    public static final BlockItem ENENMOMY = register(new BlockItem(BlockInit.ENENMOMY_BLOCK, new Item.Settings()), "enenmomy");

    //SPAWN EGGS
    public static final Item ATLANTEAN_CRAB_EGG = register("atlantean_crab_egg", new SpawnEggItem(AtlantisEntities.CRAB, 0x800002, 0xff0f45, new Item.Settings().group(ItemGroup.MISC)));
    public static final Item ATLANTEAN_JELLYFISH_EGG = register("atlantean_jellyfish_egg", new SpawnEggItem(AtlantisEntities.JELLYFISH, 0x00458a, 0x0582ff, new Item.Settings().group(ItemGroup.MISC)));
    public static final Item ATLANTEAN_JELLYFISH_2_EGG = register("atlantean_jellyfish_2_egg", new SpawnEggItem(AtlantisEntities.JELLYFISH2, 0x347BC2, 0x004080, new Item.Settings().group(ItemGroup.MISC)));
    public static final Item ATLANTEAN_SHRIMP_EGG = register("atlantean_shrimp_egg", new SpawnEggItem(AtlantisEntities.SHRIMP, 0xff0000, 0xff8000, new Item.Settings().group(ItemGroup.MISC)));

    //MUSIC DISC
    public static final Item PANBEE = register("panbee", new AtlantisMusicDisc(15, AtlantisSoundEvents.PANBEE, ATLANTIS_SETTINGS));

    //ITEMS
    public static final Item AQUAMARINE_GEM = register("aquamarine_gem", new DefaultItem());
    public static final Item ORB_OF_ATLANTIS = register("orb_of_atlantis", new DefaultItem());
    public static final Item ATLANTEAN_CRYSTAL = register("atlantean_crystal", new AtlanteanCrystal());
    public static final Item OCEAN_STONE = register("ocean_stone", new DefaultItem());
    public static final Item DROP_OF_ATLANTIS = register("drop_of_atlantis", new DefaultItem());
    public static final Item BROWN_WROUGHT_PATCHES = register("brown_wrought_patches", new DefaultItem());
    public static final Item CRAB_LEGS = register("crab_legs", new Item(new Item.Settings().food(new FoodComponent.Builder().meat().hunger(6).saturationModifier(10.0f).build())));
    public static final Item SHRIMP = register("shrimp", new Item(new Item.Settings().food(new FoodComponent.Builder().meat().hunger(5).saturationModifier(2.0f).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 100), 0.05f).build())));
    public static final Item COOKED_SHRIMP = register("cooked_shrimp", new Item(new Item.Settings().food(new FoodComponent.Builder().meat().hunger(2).saturationModifier(5.0f).build())));
    public static final Item ATLANTEAN_POWER_TORCH = register("atlantean_power_torch", new WallStandingBlockItem(BlockInit.ATLANTEAN_POWER_TORCH, BlockInit.WALL_ATLANTEAN_POWER_TORCH, (new Item.Settings())));
    public static final Item ATLANTEAN_POWER_DUST = register("atlantean_power_dust", new AliasedBlockItem(BlockInit.ATLANTEAN_POWER_DUST_WIRE, (new Item.Settings())));
    public static final Item ATLANTEAN_STRING = register("atlantean_string", new AliasedBlockItem(BlockInit.ATLANTEAN_TRIPWIRE, (new Item.Settings())));
    public static final Item SUBMARINE = register("submarine", new SubmarineItem(new FabricItemSettings()));
    public static final Item WATER_PILL = register("water_pill", new WaterPill());

    //Entity Buckets
    public static final Item CRAB_BUCKET = register("crab_bucket", new CrabEntityBucketItem(AtlantisEntities.CRAB, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, (new Item.Settings()).maxCount(1)));
    public static final Item JELLYFISH_BUCKET = register("jellyfish_bucket", new JellyfishEntityBucketItem(AtlantisEntities.JELLYFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, (new Item.Settings()).maxCount(1)));
    public static final Item JELLYFISH_2_BUCKET = register("jellyfish_2_bucket", new JellyfishEntityBucketItem(AtlantisEntities.JELLYFISH2, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, (new Item.Settings()).maxCount(1)));
    public static final Item SHRIMP_BUCKET = register("shrimp_bucket", new JellyfishEntityBucketItem(AtlantisEntities.SHRIMP, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, (new Item.Settings()).maxCount(1)));

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
    public static final ArmorItem BROWN_WROUGHT_HELMET = (ArmorItem) register("brown_wrought_helmet", new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.HEAD, new Item.Settings()));
    public static final ArmorItem BROWN_WROUGHT_CHESTPLATE = (ArmorItem) register("brown_wrought_chestplate", new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.CHEST, new Item.Settings()));
    public static final ArmorItem BROWN_WROUGHT_LEGGINGS= (ArmorItem) register("brown_wrought_leggings", new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.LEGS, new Item.Settings()));
    public static final ArmorItem BROWN_WROUGHT_BOOTS = (ArmorItem) register("brown_wrought_boots", new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.FEET, new Item.Settings()));
}
