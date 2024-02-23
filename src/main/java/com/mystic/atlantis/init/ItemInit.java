package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.base.LinguisticGlyph;
import com.mystic.atlantis.items.*;
import com.mystic.atlantis.items.armor.BasicArmorMaterial;
import com.mystic.atlantis.items.armor.ItemArmorAtlantis;
import com.mystic.atlantis.items.armor.ItemArmorWrought;
import com.mystic.atlantis.items.item.energybased.AtlanteanCrystal;
import com.mystic.atlantis.items.musicdisc.AtlantisMusicDisc;
import com.mystic.atlantis.items.tools.*;
import com.mystic.atlantis.util.Reference;
import mod.azure.azurelib.neoforge.items.NeoForgeAzureSpawnEgg;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Reference.MODID);
    private static final Map<LinguisticGlyph, Supplier<Item>> scrolls = new HashMap<>();

    private static final Item.Properties ATLANTIS_SETTINGS = new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).stacksTo(1);

    //Energy
   // public static final Supplier<Item> ATLANTEAN_AMULET = register("atlantean_amulet", AtlanteanAmuletItem::new);
   // public static final Supplier<Item> ATLANTEAN_SPEAR = register("atlantean_spear", AtlanteanSpearItem::new);

    //BOATS
    public static final Supplier<Item> ATLANTEAN_BOAT = register("atlantean_boat", () -> new AtlanteanBoatItem(new Item.Properties().stacksTo(1)));

    //SPAWN EGGS
    public static final Supplier<Item> ATLANTEAN_CRAB_EGG = register("atlantean_crab_egg",() -> new NeoForgeAzureSpawnEgg(AtlantisEntityInit.CRAB, 0x800002, 0xff0f45));
    public static final Supplier<Item> ATLANTEAN_JELLYFISH_EGG = register("atlantean_jellyfish_egg", () -> new NeoForgeAzureSpawnEgg(AtlantisEntityInit.JELLYFISH, 0x00458a, 0x0582ff));
    public static final Supplier<Item> ATLANTEAN_SHRIMP_EGG = register("atlantean_shrimp_egg", () -> new NeoForgeAzureSpawnEgg(AtlantisEntityInit.SHRIMP, 0xff0000, 0xff8000));
    public static final Supplier<Item> LEVIATHAN_EGG = register("leviathan_egg", () -> new NeoForgeAzureSpawnEgg(AtlantisEntityInit.LEVIATHAN, 0x01ddddd, 0xaddedb));

    public static final Supplier<Item> ATLANTEAN_SEAHORSE_EGG = register("atlantean_seahorse_egg", () -> new NeoForgeAzureSpawnEgg(AtlantisEntityInit.SEAHORSE, 0xf6eb3e, 0xcfc85b));

    public static final Supplier<Item> COCONUT_CRAB_EGG = register("coconut_crab_egg", () -> new NeoForgeAzureSpawnEgg(AtlantisEntityInit.COCONUT_CRAB, 0x800002, 0xff0f45));
    public static final Supplier<Item> STARFISH_EGG = register("atlantean_starfish_egg", () -> new NeoForgeAzureSpawnEgg(AtlantisEntityInit.STARFISH, 0xFFA41D, 0xF6E25F));
    public static final Supplier<Item> STARFISH_ZOM_EGG = register("atlantean_starzomfish_egg", () -> new NeoForgeAzureSpawnEgg(AtlantisEntityInit.STARFISH_ZOM, 0xFE00F6, 0x00A170));
    //MUSIC DISC
    public static final Supplier<Item> PANBEE = register("panbee", () -> new AtlantisMusicDisc(15, AtlantisSoundEventInit.PANBEE, ATLANTIS_SETTINGS, 10));
    public static final Supplier<Item> COLUMN_CAVITATION = register("column_cavitation", () -> new AtlantisMusicDisc(15, AtlantisSoundEventInit.COLUMN, ATLANTIS_SETTINGS, 10));

    //ITEMS
    public static final Supplier<Item> BROKEN_SHELLS = register("broken_shells", DefaultItem::new);
    public static final Supplier<Item> SODIUM_NUGGET = register("sodium_nugget", () -> new SodiumItem(new Item.Properties()));
    public static final Supplier<Item> SEA_SALT = register("sea_salt", DefaultItem::new);
    public static final Supplier<Item> FIRE_MELON_JELLY_BOTTLE = register("fire_melon_jelly_bottle", () -> new FireMelonJellyBottle(new Item.Properties()));
    public static final Supplier<Item> JELLY_BOTTLE = register("jellyfish_jelly_bottle", () -> new JellyfishJellyBottle(new Item.Properties()));
    public static final Supplier<Item> AQUAMARINE_GEM = register("aquamarine_gem", DefaultItem::new);
    public static final Supplier<Item> ORICHALCUM_INGOT = register("orichalcum_ingot", DefaultItem::new);
    public static final Supplier<Item> ORICHALCUM_BLEND = register("orichalcum_blend", DefaultItem::new);
    public static final Supplier<Item> ORB_OF_ATLANTIS = register("orb_of_atlantis", OrbOfAtlantis::new);
    public static final Supplier<Item> ATLANTEAN_CRYSTAL = register("atlantean_crystal", AtlanteanCrystal::new);
    public static final Supplier<Item> OCEAN_STONE = register("ocean_stone", DefaultItem::new);
    public static final Supplier<Item> DROP_OF_ATLANTIS = register("drop_of_atlantis", DefaultItem::new);
    public static final Supplier<Item> BROWN_WROUGHT_PATCHES = register("brown_wrought_patches", DefaultItem::new);
    public static final Supplier<Item> CRAB_LEGS = register("crab_legs", () -> new CrabLegsItem(new Item.Properties()));
    public static final Supplier<Item> SHRIMP = register("shrimp", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(5).saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100), 0.05f).build())));
    public static final Supplier<Item> COOKED_SHRIMP = register("cooked_shrimp", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(2).saturationMod(0.4f).build())));
    public static final Supplier<Item> ATLANTEAN_POWER_TORCH = register("atlantean_power_torch", () -> new StandingAndWallBlockItem(BlockInit.ATLANTEAN_POWER_TORCH.get(), BlockInit.WALL_ATLANTEAN_POWER_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final Supplier<Item> ATLANTEAN_POWER_DUST = register("atlantean_power_dust",  () -> new ItemNameBlockItem(BlockInit.ATLANTEAN_POWER_DUST_WIRE.get(), new Item.Properties()));
    public static final Supplier<Item> ATLANTEAN_STRING = register("atlantean_string",  () -> new ItemNameBlockItem(BlockInit.ATLANTEAN_TRIPWIRE.get(), (new Item.Properties())));
    public static final Supplier<Item> SUBMARINE = register("submarine", () -> new SubmarineItem(new Item.Properties()));
    public static final Supplier<Item> WATER_PILL = register("water_pill", WaterPill::new);
    public static final Supplier<Item> ATLANTEAN_SIGN = register("atlantean_sign", () -> new SignItem(new Item.Properties(), BlockInit.ATLANTEAN_SIGNS.get(), BlockInit.ATLANTEAN_WALL_SIGN.get()));

    public static final Supplier<Item> ATLANTEAN_FIRE_MELON_FRUIT = register("atlantean_fire_melon_fruit", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(1).build())));
    public static final Supplier<Item> ATLANTEAN_FIRE_MELON_FRUIT_SPIKED = register("atlantean_fire_melon_fruit_spiked", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(1).effect(() -> new MobEffectInstance(MobEffects.HARM, 60), 1.0f).build())));

    public static final Supplier<Item> ATLANTEAN_FIRE_MELON_SEEDS = register("atlantean_fire_melon_fruit_seeds",  () -> new ItemNameBlockItem(BlockInit.ATLANTEAN_FIRE_MELON_TOP.get(), new Item.Properties()));
    public static final Supplier<Item> ATLANTEAN_FIRE_MELON_SPIKE = register("atlantean_fire_melon_spike",  DefaultItem::new);

    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL = registerGlyph(LinguisticGlyph.BLANK);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_A = registerGlyph(LinguisticGlyph.A);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_B = registerGlyph(LinguisticGlyph.B);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_C = registerGlyph(LinguisticGlyph.C);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_D = registerGlyph(LinguisticGlyph.D);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_E = registerGlyph(LinguisticGlyph.E);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_F = registerGlyph(LinguisticGlyph.F);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_G = registerGlyph(LinguisticGlyph.G);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_H = registerGlyph(LinguisticGlyph.H);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_I = registerGlyph(LinguisticGlyph.I);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_J = registerGlyph(LinguisticGlyph.J);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_K = registerGlyph(LinguisticGlyph.K);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_L = registerGlyph(LinguisticGlyph.L);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_M = registerGlyph(LinguisticGlyph.M);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_N = registerGlyph(LinguisticGlyph.N);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_O = registerGlyph(LinguisticGlyph.O);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_P = registerGlyph(LinguisticGlyph.P);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_Q = registerGlyph(LinguisticGlyph.Q);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_R = registerGlyph(LinguisticGlyph.R);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_S = registerGlyph(LinguisticGlyph.S);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_T = registerGlyph(LinguisticGlyph.T);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_U = registerGlyph(LinguisticGlyph.U);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_V = registerGlyph(LinguisticGlyph.V);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_W = registerGlyph(LinguisticGlyph.W);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_X = registerGlyph(LinguisticGlyph.X);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_Y = registerGlyph(LinguisticGlyph.Y);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_Z = registerGlyph(LinguisticGlyph.Z);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_0 = registerGlyph(LinguisticGlyph.ZERO);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_1 = registerGlyph(LinguisticGlyph.ONE);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_2 = registerGlyph(LinguisticGlyph.TWO);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_3 = registerGlyph(LinguisticGlyph.THREE);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_4 = registerGlyph(LinguisticGlyph.FOUR);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_5 = registerGlyph(LinguisticGlyph.FIVE);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_6 = registerGlyph(LinguisticGlyph.SIX);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_7 = registerGlyph(LinguisticGlyph.SEVEN);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_8 = registerGlyph(LinguisticGlyph.EIGHT);
    public static final Supplier<Item> LINGUISTIC_GLYPH_SCROLL_9 = registerGlyph(LinguisticGlyph.NINE);

    //Fluid Buckets
    public static final Supplier<Item> JETSTREAM_WATER_BUCKET = ITEMS.register("jetstream_water_bucket", () -> new BucketItem(FluidInit.JETSTREAM_WATER,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<Item> SALTY_SEA_WATER_BUCKET = ITEMS.register("salty_sea_water_bucket",
            () -> new BucketItem(FluidInit.SALTY_SEA_WATER,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    //public static final Supplier<Item> COCONUT_MILK_BUCKET = ITEMS.register("coconut_milk_bucket",
    //        () -> new BucketItem(FluidInit.COCONUT_MILK,
    //                new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    //Entity Buckets
    public static final Supplier<Item> CRAB_BUCKET = register("crab_bucket", ()->new CrabEntityBucketItem(AtlantisEntityInit.CRAB, ()->Fluids.WATER, ()->SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> JELLYFISH_BUCKET = register("jellyfish_bucket", ()->new AtlanteanEntityBucketItem(AtlantisEntityInit.JELLYFISH, ()->Fluids.WATER, ()->SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> SHRIMP_BUCKET = register("shrimp_bucket", ()->new AtlanteanEntityBucketItem(AtlantisEntityInit.SHRIMP, ()->Fluids.WATER, ()->SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1)));
    public static final Supplier<Item> SEAHORSE_BUCKET = register("seahorse_bucket", ()->new AtlanteanEntityBucketItem(AtlantisEntityInit.SEAHORSE, ()->Fluids.WATER, ()-> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1)));

    //TOOLS
    public static final Supplier<Item> AXE_AQUAMARINE = register("axe_aquamarine", () -> new AquamarineAxe(ToolInit.AQUAMARINE, 3));
    public static final Supplier<Item> PICKAXE_AQUAMARINE = register("pickaxe_aquamarine", () -> new AquamarinePickaxe(ToolInit.AQUAMARINE, 2));
    public static final Supplier<Item> SHOVEL_AQUAMARINE = register("shovel_aquamarine", () -> new AquamarineShovel(ToolInit.AQUAMARINE, 1));
    public static final Supplier<Item> HOE_AQUAMARINE = register("hoe_aquamarine", () -> new AquamarineHoe(ToolInit.AQUAMARINE, 2));
    public static final Supplier<Item> SWORD_AQUAMARINE = register("sword_aquamarine", () -> new AquamarineSword(ToolInit.AQUAMARINE, 4));
    public static final Supplier<Item> ORICHALCUM_AXE = register("orichalcum_axe", () -> new AquamarineAxe(ToolInit.ORICHAClUM, 3));
    public static final Supplier<Item> ORICHALCUM_PICKAXE = register("orichalcum_pickaxe", () -> new AquamarinePickaxe(ToolInit.ORICHAClUM, 2));
    public static final Supplier<Item> ORICHALCUM_SHOVEL = register("orichalcum_shovel", () -> new AquamarineShovel(ToolInit.ORICHAClUM, 1));
    public static final Supplier<Item> ORICHALCUM_HOE = register("orichalcum_hoe", () -> new AquamarineHoe(ToolInit.ORICHAClUM, 2));
    public static final Supplier<Item> ORICHALCUM_SWORD = register("orichalcum_sword", () -> new AquamarineSword(ToolInit.ORICHAClUM, 4));

    //ARMOR
    public static final Supplier<Item> AQUAMARINE_HELMET = register("aquamarine_helmet", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final Supplier<Item> AQUAMARINE_CHESTPLATE = register("aquamarine_chestplate", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final Supplier<Item> AQUAMARINE_LEGGINGS= register("aquamarine_leggings", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final Supplier<Item> AQUAMARINE_BOOTS = register("aquamarine_boots", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_AQUAMARINE, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final Supplier<Item> BROWN_WROUGHT_HELMET = register("brown_wrought_helmet", () -> new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final Supplier<Item> BROWN_WROUGHT_CHESTPLATE = register("brown_wrought_chestplate", () -> new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final Supplier<Item> BROWN_WROUGHT_LEGGINGS= register("brown_wrought_leggings", () -> new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final Supplier<Item> BROWN_WROUGHT_BOOTS = register("brown_wrought_boots", () -> new ItemArmorWrought(BasicArmorMaterial.ARMOR_BROWN_WROUGHT, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final Supplier<Item> ORICHALCUM_UPGRADE_SMITHING_TEMPLATE = register("orichalcum_upgrade_smithing_template", OrichalcumSmithingTemplateItem::new);

    public static final Supplier<Item> ORICHALCUM_HELMET = register("orichalcum_helmet", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_ORICHALCUM, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final Supplier<Item> ORICHALCUM_CHESTPLATE = register("orichalcum_chestplate", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_ORICHALCUM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final Supplier<Item> ORICHALCUM_LEGGINGS= register("orichalcum_leggings", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_ORICHALCUM, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final Supplier<Item> ORICHALCUM_BOOTS = register("orichalcum_boots", () -> new ItemArmorAtlantis(BasicArmorMaterial.ARMOR_ORICHALCUM, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static <T extends Item> Supplier<T> register(String name, Supplier<T> item) {
        var register = ITEMS.register(name, item);
        AtlantisGroupInit.addToMainTabItems(register);
        return register;
    }

    public static <T extends Item> Supplier<T> registerToGlyph(String name, Supplier<T> item) {
        var register = ITEMS.register(name, item);
        AtlantisGroupInit.addToGylphTabItems(register);
        return register;
    }

    static Supplier<Item> registerGlyph(LinguisticGlyph symbol) {
        Supplier<Item> Supplier = registerToGlyph("linguistic_glyph_scroll" + symbol.toString(), () -> new LinguisticGlyphScrollItem(symbol));
        scrolls.put(symbol, Supplier);
        return Supplier;
    }
    
    public static Supplier<Item> getScroll(LinguisticGlyph a) {
        return scrolls.get(a);
    }
    
    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }
}
