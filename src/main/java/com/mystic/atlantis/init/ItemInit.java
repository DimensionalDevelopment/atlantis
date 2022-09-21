package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.LinguisticGlyph;
import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.event.AtlantisSoundEvents;
import com.mystic.atlantis.itemgroup.AtlantisGroup;
import com.mystic.atlantis.items.armor.BasicArmorMaterial;
import com.mystic.atlantis.items.armor.ItemArmorAtlantis;
import com.mystic.atlantis.items.armor.ItemArmorWrought;
import com.mystic.atlantis.items.item.*;
import com.mystic.atlantis.items.item.bottles.FireMelonJellyBottle;
import com.mystic.atlantis.items.item.bottles.JellyfishJellyBottle;
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
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ItemInit {

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);
    private static Map<LinguisticGlyph, RegistryObject<Item>> scrolls = new HashMap<>();

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    static <T extends Item> RegistryObject<T> register(Supplier<T> c, String id) {
        return register(id, c);
    }

    static RegistryObject<Item> registerGlyph(LinguisticGlyph symbol) {
        RegistryObject<Item> registryObject = register(() -> new LinguisticGlyphScrollItem(symbol), "linguistic_glyph_scroll" + symbol.toString());
        scrolls.put(symbol, registryObject);
        return registryObject;
    }

    private static final Item.Properties ATLANTIS_SETTINGS = new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).stacksTo(1).tab(AtlantisGroup.MAIN);

    private static final Item.Properties ATLANTIS_GLYPH = new Item.Properties().tab(AtlantisGroup.GLYPH);

    //BOATS
    public static final RegistryObject<Item> ATLANTEAN_BOAT = register("atlantean_boat", () -> new AtlanteanBoatItem(new Item.Properties().stacksTo(1).tab(AtlantisGroup.MAIN)));

    //SPAWN EGGS
    public static final RegistryObject<Item> ATLANTEAN_CRAB_EGG = register("atlantean_crab_egg",() -> new ForgeSpawnEggItem(AtlantisEntities.CRAB, 0x800002, 0xff0f45, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ATLANTEAN_JELLYFISH_EGG = register("atlantean_jellyfish_egg", () -> new ForgeSpawnEggItem(AtlantisEntities.JELLYFISH, 0x00458a, 0x0582ff, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ATLANTEAN_JELLYFISH_2_EGG = register("atlantean_jellyfish_2_egg", () -> new ForgeSpawnEggItem(AtlantisEntities.JELLYFISH2, 0x347BC2, 0x004080, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ATLANTEAN_SHRIMP_EGG = register("atlantean_shrimp_egg", () -> new ForgeSpawnEggItem(AtlantisEntities.SHRIMP, 0xff0000, 0xff8000, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> LEVIATHAN_EGG = register("leviathan_egg", () -> new ForgeSpawnEggItem(AtlantisEntities.LEVIATHAN, 0x01ddddd, 0xaddedb, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> COCONUT_CRAB_EGG = register("coconut_crab_egg", () -> new ForgeSpawnEggItem(AtlantisEntities.COCONUT_CRAB, 0x800002, 0xff0f45, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //MUSIC DISC
    public static final RegistryObject<Item> PANBEE = register("panbee", () -> new AtlantisMusicDisc(15, AtlantisSoundEvents.PANBEE, ATLANTIS_SETTINGS, 10));
    public static final RegistryObject<Item> COLUMN_CAVITATION = register("column_cavitation", () -> new AtlantisMusicDisc(15, AtlantisSoundEvents.COLUMN, ATLANTIS_SETTINGS, 10));

    //ITEMS
    public static final RegistryObject<Item> FIRE_MELON_JELLY_BOTTLE = register("fire_melon_jelly_bottle", () -> new FireMelonJellyBottle(new Item.Properties()));
    public static final RegistryObject<Item> JELLY_BOTTLE = register("jellyfish_jelly_bottle", () -> new JellyfishJellyBottle(new Item.Properties()));
    public static final RegistryObject<Item> AQUAMARINE_GEM = register("aquamarine_gem", DefaultItem::new);
    public static final RegistryObject<Item> ORICHALCUM_IGNOT = register("orichalcum_ignot", DefaultItem::new);
    public static final RegistryObject<Item> ORICHALCUM_BLEND = register("orichalcum_blend", DefaultItem::new);
    public static final RegistryObject<Item> ORB_OF_ATLANTIS = register("orb_of_atlantis", DefaultItem::new);
    public static final RegistryObject<Item> ATLANTEAN_CRYSTAL = register("atlantean_crystal", AtlanteanCrystal::new);
    public static final RegistryObject<Item> OCEAN_STONE = register("ocean_stone", DefaultItem::new);
    public static final RegistryObject<Item> DROP_OF_ATLANTIS = register("drop_of_atlantis", DefaultItem::new);
    public static final RegistryObject<Item> BROWN_WROUGHT_PATCHES = register("brown_wrought_patches", DefaultItem::new);
    public static final RegistryObject<Item> CRAB_LEGS = register("crab_legs", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(6).saturationMod(10.0f).build()).tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> SHRIMP = register("shrimp", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(5).saturationMod(2.0f).effect(new MobEffectInstance(MobEffects.CONFUSION, 100), 0.05f).build()).tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> COOKED_SHRIMP = register("cooked_shrimp", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(2).saturationMod(5.0f).build()).tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> ATLANTEAN_POWER_TORCH = register("atlantean_power_torch", () -> new StandingAndWallBlockItem(BlockInit.ATLANTEAN_POWER_TORCH.get(), BlockInit.WALL_ATLANTEAN_POWER_TORCH.get(), (new Item.Properties().tab(AtlantisGroup.MAIN))));
    public static final RegistryObject<Item> ATLANTEAN_POWER_DUST = register("atlantean_power_dust",  () -> new ItemNameBlockItem(BlockInit.ATLANTEAN_POWER_DUST_WIRE.get(), (new Item.Properties().tab(AtlantisGroup.MAIN))));
    public static final RegistryObject<Item> ATLANTEAN_STRING = register("atlantean_string",  () -> new ItemNameBlockItem(BlockInit.ATLANTEAN_TRIPWIRE.get(), (new Item.Properties().tab(AtlantisGroup.MAIN))));
    public static final RegistryObject<Item> SUBMARINE = register("submarine", () -> new SubmarineItem(new Item.Properties().tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> WATER_PILL = register("water_pill", WaterPill::new);
    public static final RegistryObject<Item> ATLANTEAN_SIGN = register("atlantean_sign", () -> new SignItem(new Item.Properties().tab(AtlantisGroup.MAIN), BlockInit.ATLANTEAN_SIGNS.get(), BlockInit.ATLANTEAN_WALL_SIGN.get()));

    public static final RegistryObject<Item> ATLANTEAN_FIRE_MELON_FRUIT = register("atlantean_fire_melon_fruit", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(1).build())));
    public static final RegistryObject<Item> ATLANTEAN_FIRE_MELON_FRUIT_SPIKED = register("atlantean_fire_melon_fruit_spiked", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(1).effect(() -> new MobEffectInstance(MobEffects.HARM, 60), 1.0f).build())));

    public static final RegistryObject<Item> ATLANTEAN_FIRE_MELON_SEEDS = register("atlantean_fire_melon_fruit_seeds",  () -> new ItemNameBlockItem(BlockInit.ATLANTEAN_FIRE_MELON_TOP.get(), new Item.Properties().tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> ATLANTEAN_FIRE_MELON_SPIKE = register("atlantean_fire_melon_spike",  DefaultItem::new);

    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL = registerGlyph(LinguisticGlyph.BLANK);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_A = registerGlyph(LinguisticGlyph.A);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_B = registerGlyph(LinguisticGlyph.B);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_C = registerGlyph(LinguisticGlyph.C);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_D = registerGlyph(LinguisticGlyph.D);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_E = registerGlyph(LinguisticGlyph.E);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_F = registerGlyph(LinguisticGlyph.F);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_G = registerGlyph(LinguisticGlyph.G);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_H = registerGlyph(LinguisticGlyph.H);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_I = registerGlyph(LinguisticGlyph.I);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_J = registerGlyph(LinguisticGlyph.J);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_K = registerGlyph(LinguisticGlyph.K);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_L = registerGlyph(LinguisticGlyph.L);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_M = registerGlyph(LinguisticGlyph.M);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_N = registerGlyph(LinguisticGlyph.N);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_O = registerGlyph(LinguisticGlyph.O);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_P = registerGlyph(LinguisticGlyph.P);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_Q = registerGlyph(LinguisticGlyph.Q);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_R = registerGlyph(LinguisticGlyph.R);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_S = registerGlyph(LinguisticGlyph.S);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_T = registerGlyph(LinguisticGlyph.T);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_U = registerGlyph(LinguisticGlyph.U);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_V = registerGlyph(LinguisticGlyph.V);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_W = registerGlyph(LinguisticGlyph.W);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_X = registerGlyph(LinguisticGlyph.X);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_Y = registerGlyph(LinguisticGlyph.Y);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_Z = registerGlyph(LinguisticGlyph.Z);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_0 = registerGlyph(LinguisticGlyph.ZERO);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_1 = registerGlyph(LinguisticGlyph.ONE);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_2 = registerGlyph(LinguisticGlyph.TWO);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_3 = registerGlyph(LinguisticGlyph.THREE);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_4 = registerGlyph(LinguisticGlyph.FOUR);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_5 = registerGlyph(LinguisticGlyph.FIVE);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_6 = registerGlyph(LinguisticGlyph.SIX);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_7 = registerGlyph(LinguisticGlyph.SEVEN);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_8 = registerGlyph(LinguisticGlyph.EIGHT);
    public static final RegistryObject<Item> LINGUISTIC_GLYPH_SCROLL_9 = registerGlyph(LinguisticGlyph.NINE);

    //Fluid Buckets
    public static final RegistryObject<Item> JETSTREAM_WATER_BUCKET = ITEMS.register("jetstream_water_bucket",
            () -> new BucketItem(FluidInit.JETSTREAM_WATER,
                    new Item.Properties().tab(AtlantisGroup.MAIN).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> SALTY_SEA_WATER_BUCKET = ITEMS.register("salty_sea_water_bucket",
            () -> new BucketItem(FluidInit.SALTY_SEA_WATER,
                    new Item.Properties().tab(AtlantisGroup.MAIN).craftRemainder(Items.BUCKET).stacksTo(1)));

    //Entity Buckets
    public static final RegistryObject<Item> CRAB_BUCKET = register("crab_bucket", ()->new CrabEntityBucketItem(AtlantisEntities.CRAB, ()->Fluids.WATER, ()->SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> JELLYFISH_BUCKET = register("jellyfish_bucket", ()->new JellyfishEntityBucketItem(AtlantisEntities.JELLYFISH, ()->Fluids.WATER, ()->SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> JELLYFISH_2_BUCKET = register("jellyfish_2_bucket", ()->new JellyfishEntityBucketItem(AtlantisEntities.JELLYFISH2, ()->Fluids.WATER, ()->SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> SHRIMP_BUCKET = register("shrimp_bucket", ()->new JellyfishEntityBucketItem(AtlantisEntities.SHRIMP, ()->Fluids.WATER, ()->SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).tab(AtlantisGroup.MAIN)));

    public static final RegistryObject<Item> COCONUT_CRAB_BUCKET = register("coconut_crab_bucket", ()->new CrabEntityBucketItem(AtlantisEntities.COCONUT_CRAB, ()->Fluids.WATER, ()->SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).tab(AtlantisGroup.MAIN)));

    //TOOLS
    public static final RegistryObject<Item> AXE_AQUAMARINE = register("axe_aquamarine", () -> new AquamarineAxe(ToolInit.AQUAMARINE, 4));
    public static final RegistryObject<Item> PICKAXE_AQUAMARINE = register("pickaxe_aquamarine", () -> new AquamarinePickaxe(ToolInit.AQUAMARINE, 3));
    public static final RegistryObject<Item> SHOVEL_AQUAMARINE = register("shovel_aquamarine", () -> new AquamarineShovel(ToolInit.AQUAMARINE, 1));
    public static final RegistryObject<Item> HOE_AQUAMARINE = register("hoe_aquamarine", () -> new AquamarineHoe(ToolInit.AQUAMARINE, 2));
    public static final RegistryObject<Item> SWORD_AQUAMARINE = register("sword_aquamarine", () -> new AquamarineSword(ToolInit.AQUAMARINE, 6));
    public static final RegistryObject<Item> ORICHALCUM_AXE = register("orichalcum_axe", () -> new AquamarineAxe(ToolInit.ORICHAClUM, 4));
    public static final RegistryObject<Item> ORICHALCUM_PICKAXE = register("orichalcum_pickaxe", () -> new AquamarinePickaxe(ToolInit.ORICHAClUM, 3));
    public static final RegistryObject<Item> ORICHALCUM_SHOVEL = register("orichalcum_shovel", () -> new AquamarineShovel(ToolInit.ORICHAClUM, 1));
    public static final RegistryObject<Item> ORICHALCUM_HOE = register("orichalcum_hoe", () -> new AquamarineHoe(ToolInit.ORICHAClUM, 2));
    public static final RegistryObject<Item> ORICHALCUM_SWORD = register("orichalcum_sword", () -> new AquamarineSword(ToolInit.ORICHAClUM, 6));

    //ARMOR
    public static final RegistryObject<Item> AQUAMARINE_HELMET = register("aquamarine_helmet", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.HEAD, new Item.Properties().tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> AQUAMARINE_CHESTPLATE = register("aquamarine_chestplate", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.CHEST, new Item.Properties().tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> AQUAMARINE_LEGGINGS= register("aquamarine_leggings", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.LEGS, new Item.Properties().tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> AQUAMARINE_BOOTS = register("aquamarine_boots", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, EquipmentSlot.FEET, new Item.Properties().tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> BROWN_WROUGHT_HELMET = register("brown_wrought_helmet", () -> new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.HEAD, new Item.Properties().tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> BROWN_WROUGHT_CHESTPLATE = register("brown_wrought_chestplate", () -> new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.CHEST, new Item.Properties().tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> BROWN_WROUGHT_LEGGINGS= register("brown_wrought_leggings", () -> new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.LEGS, new Item.Properties().tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> BROWN_WROUGHT_BOOTS = register("brown_wrought_boots", () -> new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, EquipmentSlot.FEET, new Item.Properties().tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> ORICHALCUM_HELMET = register("orichalcum_helmet", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_ORICHALCUM, EquipmentSlot.HEAD, new Item.Properties().tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> ORICHALCUM_CHESTPLATE = register("orichalcum_chestplate", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_ORICHALCUM, EquipmentSlot.CHEST, new Item.Properties().tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> ORICHALCUM_LEGGINGS= register("orichalcum_leggings", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_ORICHALCUM, EquipmentSlot.LEGS, new Item.Properties().tab(AtlantisGroup.MAIN)));
    public static final RegistryObject<Item> ORICHALCUM_BOOTS = register("orichalcum_boots", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_ORICHALCUM, EquipmentSlot.FEET, new Item.Properties().tab(AtlantisGroup.MAIN)));

    public static RegistryObject<Item> getScroll(LinguisticGlyph a) {
        return scrolls.get(a);
    }
}
