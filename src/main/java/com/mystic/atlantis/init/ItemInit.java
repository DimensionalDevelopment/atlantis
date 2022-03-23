package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.AtlanteanFireMelonFruit;
import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.event.AtlantisSoundEvents;
import com.mystic.atlantis.itemgroup.AtlantisGroup;
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
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, Reference.MODID);

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static RegistryObject<Item> register(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }

    static RegistryObject<Item> register(Supplier<Item> c, String id) {
        return register(id, c);
    }

    private static final Item.Properties ATLANTIS_SETTINGS = new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).stacksTo(1).tab(AtlantisGroup.INSTANCE);

    //SPAWN EGGS
    public static final RegistryObject<Item> ATLANTEAN_CRAB_EGG = register("atlantean_crab_egg",() -> new ForgeSpawnEggItem(AtlantisEntities.CRAB, 0x800002, 0xff0f45, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ATLANTEAN_JELLYFISH_EGG = register("atlantean_jellyfish_egg", () -> new ForgeSpawnEggItem(AtlantisEntities.JELLYFISH, 0x00458a, 0x0582ff, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ATLANTEAN_JELLYFISH_2_EGG = register("atlantean_jellyfish_2_egg", () -> new ForgeSpawnEggItem(AtlantisEntities.JELLYFISH2, 0x347BC2, 0x004080, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ATLANTEAN_SHRIMP_EGG = register("atlantean_shrimp_egg", () -> new ForgeSpawnEggItem(AtlantisEntities.SHRIMP, 0xff0000, 0xff8000, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //MUSIC DISC
    public static final RegistryObject<Item> PANBEE = register("panbee", () -> new AtlantisMusicDisc(15, AtlantisSoundEvents.PANBEE, ATLANTIS_SETTINGS));

    //ITEMS
    public static final RegistryObject<Item> AQUAMARINE_GEM = register("aquamarine_gem", DefaultItem::new);
    public static final RegistryObject<Item> ORB_OF_ATLANTIS = register("orb_of_atlantis", DefaultItem::new);
    public static final RegistryObject<Item> ATLANTEAN_CRYSTAL = register("atlantean_crystal", AtlanteanCrystal::new);
    public static final RegistryObject<Item> OCEAN_STONE = register("ocean_stone", DefaultItem::new);
    public static final RegistryObject<Item> DROP_OF_ATLANTIS = register("drop_of_atlantis", DefaultItem::new);
    public static final RegistryObject<Item> BROWN_WROUGHT_PATCHES = register("brown_wrought_patches", DefaultItem::new);
    public static final RegistryObject<Item> CRAB_LEGS = register("crab_legs", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(6).saturationMod(10.0f).build()).tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> SHRIMP = register("shrimp", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(5).saturationMod(2.0f).effect(new MobEffectInstance(MobEffects.CONFUSION, 100), 0.05f).build()).tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> COOKED_SHRIMP = register("cooked_shrimp", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(2).saturationMod(5.0f).build()).tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> ATLANTEAN_POWER_TORCH = register("atlantean_power_torch", () -> new StandingAndWallBlockItem(BlockInit.ATLANTEAN_POWER_TORCH.get(), BlockInit.WALL_ATLANTEAN_POWER_TORCH.get(), (new Item.Properties().tab(AtlantisGroup.INSTANCE))));
    public static final RegistryObject<Item> ATLANTEAN_POWER_DUST = register("atlantean_power_dust",  () -> new ItemNameBlockItem(BlockInit.ATLANTEAN_POWER_DUST_WIRE.get(), (new Item.Properties().tab(AtlantisGroup.INSTANCE))));
    public static final RegistryObject<Item> ATLANTEAN_STRING = register("atlantean_string",  () -> new ItemNameBlockItem(BlockInit.ATLANTEAN_TRIPWIRE.get(), (new Item.Properties().tab(AtlantisGroup.INSTANCE))));
    public static final RegistryObject<Item> SUBMARINE = register("submarine", () -> new SubmarineItem(new Item.Properties().tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> WATER_PILL = register("water_pill", WaterPill::new);
    public static final RegistryObject<Item> ATLANTEAN_SIGN = register("atlantean_sign", () -> new SignItem(new Item.Properties(), BlockInit.ATLANTEAN_SIGNS.get(), BlockInit.ATLANTEAN_WALL_SIGN.get()))

    public static final RegistryObject<Item> ATLANTEAN_FIRE_MELON_FRUIT = register("atlantean_fire_melon_fruit", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(1).build())));
    public static final RegistryObject<Item> ATLANTEAN_FIRE_MELON_FRUIT_SPIKED = register("atlantean_fire_melon_fruit_spiked", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(1).effect(() -> new MobEffectInstance(MobEffects.HARM, 60), 1.0f).build())));

    public static final RegistryObject<Item> ATLANTEAN_FIRE_MELON_SEEDS = register("atlantean_fire_melon_fruit_seeds",  () -> new ItemNameBlockItem(BlockInit.ATLANTEAN_FIRE_MELON_FRUIT.get(), new Item.Properties().tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> ATLANTEAN_FIRE_MELON_SPIKE = register("atlantean_fire_melon_spike",  DefaultItem::new);

    //Entity Buckets
    public static final RegistryObject<Item> CRAB_BUCKET = register("crab_bucket", ()->new CrabEntityBucketItem(AtlantisEntities.CRAB, ()->Fluids.WATER, ()->SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> JELLYFISH_BUCKET = register("jellyfish_bucket", ()->new JellyfishEntityBucketItem(AtlantisEntities.JELLYFISH, ()->Fluids.WATER, ()->SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> JELLYFISH_2_BUCKET = register("jellyfish_2_bucket", ()->new JellyfishEntityBucketItem(AtlantisEntities.JELLYFISH2, ()->Fluids.WATER, ()->SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> SHRIMP_BUCKET = register("shrimp_bucket", ()->new JellyfishEntityBucketItem(AtlantisEntities.SHRIMP, ()->Fluids.WATER, ()->SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).tab(AtlantisGroup.INSTANCE)));

    //TOOLS
    public static final RegistryObject<Item> AXE_AQUMARINE = register("axe_aquamarine", () -> new AquamarineAxe(ToolInit.AQUAMARINE, 4));
    public static final RegistryObject<Item> PICKAXE_AQUMARINE = register("pickaxe_aquamarine", () -> new AquamarinePickaxe(ToolInit.AQUAMARINE, 3));
    public static final RegistryObject<Item> SHOVEL_AQUMARINE = register("shovel_aquamarine", () -> new AquamarineShovel(ToolInit.AQUAMARINE, 1));
    public static final RegistryObject<Item> HOE_AQUMARINE = register("hoe_aquamarine", () -> new AquamarineHoe(ToolInit.AQUAMARINE, 2));
    public static final RegistryObject<Item> SWORD_AQUMARINE = register("sword_aquamarine", () -> new AquamarineSword(ToolInit.AQUAMARINE, 6));

    //ARMOR
    public static final RegistryObject<Item> AQUAMARINE_HELMET = register("aquamarine_helmet", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.HEAD, new Item.Properties().tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> AQUAMARINE_CHESTPLATE = register("aquamarine_chestplate", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.CHEST, new Item.Properties().tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> AQUAMARINE_LEGGINGS= register("aquamarine_leggings", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.LEGS, new Item.Properties().tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> AQUAMARINE_BOOTS = register("aquamarine_boots", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.FEET, new Item.Properties().tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> BROWN_WROUGHT_HELMET = register("brown_wrought_helmet", () -> new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.HEAD, new Item.Properties().tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> BROWN_WROUGHT_CHESTPLATE = register("brown_wrought_chestplate", () -> new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.CHEST, new Item.Properties().tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> BROWN_WROUGHT_LEGGINGS= register("brown_wrought_leggings", () -> new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.LEGS, new Item.Properties().tab(AtlantisGroup.INSTANCE)));
    public static final RegistryObject<Item> BROWN_WROUGHT_BOOTS = register("brown_wrought_boots", () -> new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.FEET, new Item.Properties().tab(AtlantisGroup.INSTANCE)));
}
