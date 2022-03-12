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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class ItemInit {
    private static DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, Reference.MODID);

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static Item register(String name, Item item) {
        ITEMS.register(name, () -> item);
        return item;
    }

    static <T extends Item> T register(T c, String id) {
        register(id, c);
        return c;
    }

    private static final Item.Properties ATLANTIS_SETTINGS = new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).stacksTo(1);

    public static final BlockItem UNDERWATER_SHROOM = register(new BlockItem(BlockInit.UNDERWATER_SHROOM_BLOCK, new Item.Properties()), "underwater_shroom");
    public static final BlockItem TUBER_UP = register(new BlockItem(BlockInit.TUBER_UP_BLOCK, new Item.Properties()), "tuber_up");
    public static final BlockItem BLUE_LILY = register(new BlockItem(BlockInit.BLUE_LILY_BLOCK, new Item.Properties()), "blue_lily");
    public static final BlockItem BURNT_DEEP = register(new BlockItem(BlockInit.BURNT_DEEP_BLOCK, new Item.Properties()), "burnt_deep");
    public static final BlockItem ENENMOMY = register(new BlockItem(BlockInit.ENENMOMY_BLOCK, new Item.Properties()), "enenmomy");

    //SPAWN EGGS
    public static final Item ATLANTEAN_CRAB_EGG = register("atlantean_crab_egg", new SpawnEggItem(AtlantisEntities.CRAB, 0x800002, 0xff0f45, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final Item ATLANTEAN_JELLYFISH_EGG = register("atlantean_jellyfish_egg", new SpawnEggItem(AtlantisEntities.JELLYFISH, 0x00458a, 0x0582ff, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final Item ATLANTEAN_JELLYFISH_2_EGG = register("atlantean_jellyfish_2_egg", new SpawnEggItem(AtlantisEntities.JELLYFISH2, 0x347BC2, 0x004080, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final Item ATLANTEAN_SHRIMP_EGG = register("atlantean_shrimp_egg", new SpawnEggItem(AtlantisEntities.SHRIMP, 0xff0000, 0xff8000, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //MUSIC DISC
    public static final Item PANBEE = register("panbee", new AtlantisMusicDisc(15, AtlantisSoundEvents.PANBEE, ATLANTIS_SETTINGS));

    //ITEMS
    public static final Item AQUAMARINE_GEM = register("aquamarine_gem", new DefaultItem());
    public static final Item ORB_OF_ATLANTIS = register("orb_of_atlantis", new DefaultItem());
    public static final Item ATLANTEAN_CRYSTAL = register("atlantean_crystal", new AtlanteanCrystal());
    public static final Item OCEAN_STONE = register("ocean_stone", new DefaultItem());
    public static final Item DROP_OF_ATLANTIS = register("drop_of_atlantis", new DefaultItem());
    public static final Item BROWN_WROUGHT_PATCHES = register("brown_wrought_patches", new DefaultItem());
    public static final Item CRAB_LEGS = register("crab_legs", new Item(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(6).saturationMod(10.0f).build())));
    public static final Item SHRIMP = register("shrimp", new Item(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(5).saturationMod(2.0f).effect(new MobEffectInstance(MobEffects.CONFUSION, 100), 0.05f).build())));
    public static final Item COOKED_SHRIMP = register("cooked_shrimp", new Item(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(2).saturationMod(5.0f).build())));
    public static final Item ATLANTEAN_POWER_TORCH = register("atlantean_power_torch", new StandingAndWallBlockItem(BlockInit.ATLANTEAN_POWER_TORCH, BlockInit.WALL_ATLANTEAN_POWER_TORCH, (new Item.Properties())));
    public static final Item ATLANTEAN_POWER_DUST = register("atlantean_power_dust", new ItemNameBlockItem(BlockInit.ATLANTEAN_POWER_DUST_WIRE, (new Item.Properties())));
    public static final Item ATLANTEAN_STRING = register("atlantean_string", new ItemNameBlockItem(BlockInit.ATLANTEAN_TRIPWIRE, (new Item.Properties())));
    public static final Item SUBMARINE = register("submarine", new SubmarineItem(new Item.Properties()));
    public static final Item WATER_PILL = register("water_pill", new WaterPill());

    //Entity Buckets
    public static final Item CRAB_BUCKET = register("crab_bucket", new CrabEntityBucketItem(AtlantisEntities.CRAB, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1)));
    public static final Item JELLYFISH_BUCKET = register("jellyfish_bucket", new JellyfishEntityBucketItem(AtlantisEntities.JELLYFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1)));
    public static final Item JELLYFISH_2_BUCKET = register("jellyfish_2_bucket", new JellyfishEntityBucketItem(AtlantisEntities.JELLYFISH2, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1)));
    public static final Item SHRIMP_BUCKET = register("shrimp_bucket", new JellyfishEntityBucketItem(AtlantisEntities.SHRIMP, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1)));

    //TOOLS
    public static final AxeItem AXE_AQUMARINE = (AxeItem) register("axe_aquamarine", new AquamarineAxe(ToolInit.AQUAMARINE, 4));
    public static final PickaxeItem PICKAXE_AQUMARINE = (PickaxeItem) register("pickaxe_aquamarine", new AquamarinePickaxe(ToolInit.AQUAMARINE, 3));
    public static final ShovelItem SHOVEL_AQUMARINE = (ShovelItem) register("shovel_aquamarine", new AquamarineShovel(ToolInit.AQUAMARINE, 1));
    public static final HoeItem HOE_AQUMARINE = (HoeItem) register("hoe_aquamarine", new AquamarineHoe(ToolInit.AQUAMARINE, 2));
    public static final SwordItem SWORD_AQUMARINE = (SwordItem) register("sword_aquamarine", new AquamarineSword(ToolInit.AQUAMARINE, 6));

    //ARMOR
    public static final ArmorItem AQUAMARINE_HELMET = (ArmorItem) register("aquamarine_helmet", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.HEAD, new Item.Properties()));
    public static final ArmorItem AQUAMARINE_CHESTPLATE = (ArmorItem) register("aquamarine_chestplate", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.CHEST, new Item.Properties()));
    public static final ArmorItem AQUAMARINE_LEGGINGS= (ArmorItem) register("aquamarine_leggings", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.LEGS, new Item.Properties()));
    public static final ArmorItem AQUAMARINE_BOOTS = (ArmorItem) register("aquamarine_boots", new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.FEET, new Item.Properties()));
    public static final ArmorItem BROWN_WROUGHT_HELMET = (ArmorItem) register("brown_wrought_helmet", new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.HEAD, new Item.Properties()));
    public static final ArmorItem BROWN_WROUGHT_CHESTPLATE = (ArmorItem) register("brown_wrought_chestplate", new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.CHEST, new Item.Properties()));
    public static final ArmorItem BROWN_WROUGHT_LEGGINGS= (ArmorItem) register("brown_wrought_leggings", new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.LEGS, new Item.Properties()));
    public static final ArmorItem BROWN_WROUGHT_BOOTS = (ArmorItem) register("brown_wrought_boots", new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.FEET, new Item.Properties()));
}
