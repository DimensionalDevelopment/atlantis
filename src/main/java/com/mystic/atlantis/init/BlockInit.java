package com.mystic.atlantis.init;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.mystic.atlantis.blocks.base.*;
import com.mystic.atlantis.blocks.blockentities.plants.*;
import com.mystic.atlantis.blocks.plants.*;
import com.mystic.atlantis.blocks.power.atlanteanstone.*;
import com.mystic.atlantis.blocks.shells.ColoredShellBlock;
import com.mystic.atlantis.blocks.shells.NautilusShellBlock;
import com.mystic.atlantis.blocks.shells.OysterShellBlock;
import com.mystic.atlantis.blocks.signs.AtlanteanSignBlock;
import com.mystic.atlantis.blocks.signs.AtlanteanWallSignBlock;
import com.mystic.atlantis.blocks.slabs.AncientWoodSlabBlock;
import com.mystic.atlantis.blocks.slabs.AtlanteanWoodSlabBlock;
import com.mystic.atlantis.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);
    
    public static final Map<LinguisticGlyph, Map<DyeColor, RegistryObject<Block>>> DYED_LINGUISTICS = new HashMap<>();
    public static final Map<LinguisticGlyph, RegistryObject<Block>> NON_LINGUISTICS = new HashMap<>();

    //Energy TODO fix Energy Blocks
    // public static final RegistryObject<Block> CRYSTAL_GENERATOR = registerBlock("crystal_generator", () -> new CrystalGeneratorBlock(BlockBehaviour.Properties.of(Material.AMETHYST)));
    // public static final RegistryObject<Block> CRYSTAL_STORAGE = registerBlock("crystal_storage", () -> new CrystalStorageBlock(BlockBehaviour.Properties.of(Material.AMETHYST)));

    //Fluid Blocks
    public static final RegistryObject<LiquidBlock> JETSTREAM_WATER_BLOCK = BLOCKS.register("jetstream_water",
            () -> new LiquidBlock(FluidInit.JETSTREAM_WATER, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> SALTY_SEA_WATER_BLOCK = BLOCKS.register("salty_sea_water",
            () -> new LiquidBlock(FluidInit.SALTY_SEA_WATER, BlockBehaviour.Properties.copy(Blocks.WATER)));

    //Atlantean Wood Type
    public static final WoodType ATLANTEAN = WoodType.register(new WoodType("atlantean", BlockSetType.OAK));

    //Atlantean Wood Variants
    public static final RegistryObject<AtlanteanButtonBlock> ATLANTEAN_BUTTON = registerBlock("atlantean_button", () -> new AtlanteanButtonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanWoodDoorBlock> ATLANTEAN_DOOR = registerBlock("atlantean_door", () -> new AtlanteanWoodDoorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanWoodFenceBlock> ATLANTEAN_FENCE = registerBlock("atlantean_fence", () -> new AtlanteanWoodFenceBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanFenceGateBlock> ATLANTEAN_FENCE_GATE = registerBlock("atlantean_fence_gate", () -> new AtlanteanFenceGateBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanWoodBlock> ATLANTEAN_PLANKS = registerBlock("atlantean_planks", () -> new AtlanteanWoodBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanPressurePlateBlock> ATLANTEAN_PRESSURE_PLATE = registerBlock("atlantean_pressure_plate", () -> new AtlanteanPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanSignBlock> ATLANTEAN_SIGNS = registerOnlyBlock("atlantean_sign", () -> new AtlanteanSignBlock(BlockBehaviour.Properties.of(), ATLANTEAN));
    public static final RegistryObject<AtlanteanWoodSlabBlock> ATLANTEAN_SLAB = registerBlock("atlantean_slab", () -> new AtlanteanWoodSlabBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanWoodStairBlock> ATLANTEAN_STAIRS = registerBlock("atlantean_stairs", () -> new AtlanteanWoodStairBlock(BlockInit.ATLANTEAN_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanWoodTrapdoorBlock> ATLANTEAN_TRAPDOOR = registerBlock("atlantean_trapdoor", () -> new AtlanteanWoodTrapdoorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanWallSignBlock> ATLANTEAN_WALL_SIGN = registerOnlyBlock("atlantean_wall_sign", () -> new AtlanteanWallSignBlock(BlockBehaviour.Properties.of(), ATLANTEAN));

    //Geckolib blocktypes
    public static final RegistryObject<UnderwaterShroomBlock> UNDERWATER_SHROOM_BLOCK = registerBlock("underwater_shroom", UnderwaterShroomBlock::new);
    public static final RegistryObject<TuberUpBlock> TUBER_UP_BLOCK = registerBlock("tuber_up", TuberUpBlock::new);
    public static final RegistryObject<BlueLilyBlock> BLUE_LILY_BLOCK = registerBlock("blue_lily", BlueLilyBlock::new);
    public static final RegistryObject<BurntDeepBlock> BURNT_DEEP_BLOCK = registerBlock("burnt_deep", BurntDeepBlock::new);
    public static final RegistryObject<EnenmomyBlock> ENENMOMY_BLOCK = registerBlock("enenmomy", EnenmomyBlock::new);

    //Trapdoors
    public static final RegistryObject<AncientWoodTrapdoorBlock> ANCIENT_DARK_OAK_WOOD_MOSS_TRAPDOOR = registerBlock("ancient_dark_oak_wood_moss_trapdoor",()-> new AncientWoodTrapdoorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodTrapdoorBlock> ANCIENT_BIRCH_WOOD_MOSS_TRAPDOOR = registerBlock("ancient_birch_wood_moss_trapdoor",()-> new AncientWoodTrapdoorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodTrapdoorBlock> ANCIENT_SPRUCE_WOOD_MOSS_TRAPDOOR = registerBlock("ancient_spruce_wood_moss_trapdoor",()-> new AncientWoodTrapdoorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodTrapdoorBlock> ANCIENT_JUNGLE_WOOD_MOSS_TRAPDOOR = registerBlock("ancient_jungle_wood_moss_trapdoor",()-> new AncientWoodTrapdoorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodTrapdoorBlock> ANCIENT_OAK_WOOD_MOSS_TRAPDOOR = registerBlock("ancient_oak_wood_moss_trapdoor",()-> new AncientWoodTrapdoorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodTrapdoorBlock> ANCIENT_ACACIA_WOOD_MOSS_TRAPDOOR = registerBlock("ancient_acacia_wood_moss_trapdoor",()-> new AncientWoodTrapdoorBlock(BlockBehaviour.Properties.of()));

    //Stairs
    public static final RegistryObject<AncientWoodStairBlock> ANCIENT_DARK_OAK_WOOD_MOSS_STAIRS = registerBlock("ancient_dark_oak_wood_moss_stairs",()-> new AncientWoodStairBlock(Blocks.DARK_OAK_STAIRS.defaultBlockState(), BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodStairBlock> ANCIENT_BIRCH_WOOD_MOSS_STAIRS = registerBlock("ancient_birch_wood_moss_stairs",()-> new AncientWoodStairBlock(Blocks.BIRCH_STAIRS.defaultBlockState(), BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodStairBlock> ANCIENT_SPRUCE_WOOD_MOSS_STAIRS = registerBlock("ancient_spruce_wood_moss_stairs",()-> new AncientWoodStairBlock(Blocks.SPRUCE_STAIRS.defaultBlockState(), BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodStairBlock> ANCIENT_JUNGLE_WOOD_MOSS_STAIRS = registerBlock("ancient_jungle_wood_moss_stairs",()-> new AncientWoodStairBlock(Blocks.JUNGLE_STAIRS.defaultBlockState(), BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodStairBlock> ANCIENT_OAK_WOOD_MOSS_STAIRS = registerBlock("ancient_oak_wood_moss_stairs",()-> new AncientWoodStairBlock(Blocks.OAK_STAIRS.defaultBlockState(), BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodStairBlock> ANCIENT_ACACIA_WOOD_MOSS_STAIRS = registerBlock("ancient_acacia_wood_moss_stairs",()-> new AncientWoodStairBlock(Blocks.ACACIA_STAIRS.defaultBlockState(), BlockBehaviour.Properties.of()));

    //Fences
    public static final RegistryObject<AncientWoodFenceBlock> ANCIENT_DARK_OAK_WOOD_MOSS_FENCE = registerBlock("ancient_dark_oak_wood_moss_fence",()-> new AncientWoodFenceBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodFenceBlock> ANCIENT_BIRCH_WOOD_MOSS_FENCE = registerBlock("ancient_birch_wood_moss_fence",()-> new AncientWoodFenceBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodFenceBlock> ANCIENT_SPRUCE_WOOD_MOSS_FENCE = registerBlock("ancient_spruce_wood_moss_fence",()-> new AncientWoodFenceBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodFenceBlock> ANCIENT_JUNGLE_WOOD_MOSS_FENCE = registerBlock("ancient_jungle_wood_moss_fence",()-> new AncientWoodFenceBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodFenceBlock> ANCIENT_OAK_WOOD_MOSS_FENCE = registerBlock("ancient_oak_wood_moss_fence",()-> new AncientWoodFenceBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodFenceBlock> ANCIENT_ACACIA_WOOD_MOSS_FENCE = registerBlock("ancient_acacia_wood_moss_fence",()-> new AncientWoodFenceBlock(BlockBehaviour.Properties.of()));

    //Doors
    public static final RegistryObject<AncientWoodDoorBlock> ANCIENT_DARK_OAK_WOOD_MOSS_DOOR = registerBlock("ancient_dark_oak_wood_moss_door",() -> new AncientWoodDoorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodDoorBlock> ANCIENT_BIRCH_WOOD_MOSS_DOOR = registerBlock("ancient_birch_wood_moss_door",() -> new AncientWoodDoorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodDoorBlock> ANCIENT_SPRUCE_WOOD_MOSS_DOOR = registerBlock("ancient_spruce_wood_moss_door",() -> new AncientWoodDoorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodDoorBlock> ANCIENT_JUNGLE_WOOD_MOSS_DOOR = registerBlock("ancient_jungle_wood_moss_door",() -> new AncientWoodDoorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodDoorBlock> ANCIENT_OAK_WOOD_MOSS_DOOR = registerBlock("ancient_oak_wood_moss_door",() -> new AncientWoodDoorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodDoorBlock> ANCIENT_ACACIA_WOOD_MOSS_DOOR = registerBlock("ancient_acacia_wood_moss_door",() -> new AncientWoodDoorBlock(BlockBehaviour.Properties.of()));

    //Shells
    public static final RegistryObject<ColoredShellBlock> BLACK_COLORED_SHELL_BLOCK = registerBlock("black_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> BLUE_COLORED_SHELL_BLOCK = registerBlock("blue_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> BROWN_COLORED_SHELL_BLOCK = registerBlock("brown_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> CYAN_COLORED_SHELL_BLOCK = registerBlock("cyan_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> GRAY_COLORED_SHELL_BLOCK = registerBlock("gray_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> GREEN_COLORED_SHELL_BLOCK = registerBlock("green_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> LIGHT_BLUE_COLORED_SHELL_BLOCK = registerBlock("light_blue_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> LIGHT_GRAY_COLORED_SHELL_BLOCK = registerBlock("light_gray_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> LIME_COLORED_SHELL_BLOCK = registerBlock("lime_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> MAGENTA_COLORED_SHELL_BLOCK = registerBlock("magenta_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> ORANGE_COLORED_SHELL_BLOCK = registerBlock("orange_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> PINK_COLORED_SHELL_BLOCK = registerBlock("pink_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> PURPLE_COLORED_SHELL_BLOCK = registerBlock("purple_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> RED_COLORED_SHELL_BLOCK = registerBlock("red_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> WHITE_COLORED_SHELL_BLOCK = registerBlock("white_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ColoredShellBlock> YELLOW_COLORED_SHELL_BLOCK = registerBlock("yellow_colored_shell_block",() -> new ColoredShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<OysterShellBlock> OYSTER_SHELL_BLOCK = registerBlock("oyster_shell_block",() -> new OysterShellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<NautilusShellBlock> NAUTILUS_SHELL_BLOCK = registerBlock("nautilus_shell_block",() -> new NautilusShellBlock(BlockBehaviour.Properties.of()));

    //Regular blocks
    public static final RegistryObject<SodiumBombBlock> SODIUM_BOMB = registerBlock("sodium_bomb",()-> new SodiumBombBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<SeaSaltChunkBlock> SEA_SALT_CHUNK = registerBlock("sea_salt_chunk",()-> new SeaSaltChunkBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<SunkenGravelBlock> SUNKEN_GRAVEL = registerBlock("sunken_gravel",()-> new SunkenGravelBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<CrackedGlowStoneBlock> CRACKED_GLOWSTONE = registerBlock("cracked_glowstone",()-> new CrackedGlowStoneBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<DeadGlowStoneBlock> DEAD_GLOWSTONE = registerBlock("dead_glowstone",()-> new DeadGlowStoneBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AlgaeDetritusStoneBlock> ALGAE_DETRITUS_STONE = registerBlock("algae_detritus_stone",()-> new AlgaeDetritusStoneBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<DetritusSandStoneBlock> DETRITUS_SANDSTONE = registerBlock("detritus_sandstone",()-> new DetritusSandStoneBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanPrismarineBlock> ATLANTEAN_PRISMARINE = registerBlock("atlantean_prismarine", () -> new AtlanteanPrismarineBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<BubbleMagmaBlock> BUBBLE_MAGMA = registerBlock("bubble_magma", () -> new BubbleMagmaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanLeavesBlock> ATLANTEAN_LEAVES = registerBlock("atlantean_leaf_block",()-> new AtlanteanLeavesBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanLogBlock> ATLANTEAN_LOGS = registerBlock("atlantean_log",()-> new AtlanteanLogBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodBlock> ANCIENT_ACACIA_WOOD_MOSS = registerBlock("ancient_acacia_wood_moss",()-> new AncientWoodBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodBlock> ANCIENT_OAK_WOOD_MOSS = registerBlock("ancient_oak_wood_moss",()-> new AncientWoodBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodBlock> ANCIENT_JUNGLE_WOOD_MOSS = registerBlock("ancient_jungle_wood_moss",()-> new AncientWoodBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodBlock> ANCIENT_SPRUCE_WOOD_MOSS = registerBlock("ancient_spruce_wood_moss",()-> new AncientWoodBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodBlock> ANCIENT_BIRCH_WOOD_MOSS = registerBlock("ancient_birch_wood_moss",()-> new AncientWoodBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodBlock> ANCIENT_DARK_OAK_WOOD_MOSS = registerBlock("ancient_dark_oak_wood_moss",()-> new AncientWoodBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<DropExperienceBlock> AQUAMARINE_ORE = registerBlock("aquamarine_ore",()-> new DropExperienceBlock(BlockBehaviour.Properties.of()
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 15.0F)
            .lightLevel((state) -> 2)));

    public static final RegistryObject<SeaBedBlock> SEABED = registerBlock("seabed", () -> new SeaBedBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<OceanLanternBlock> OCEAN_LANTERN = registerBlock("ocean_lantern",()-> new OceanLanternBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlantianSeaLanternBlock> ATLANTEAN_SEA_LANTERN = registerBlock("atlantean_sea_lantern",()-> new AtlantianSeaLanternBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanCoreBlock> ATLANTEAN_CORE = registerBlock("atlantean_core",()-> new AtlanteanCoreBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AquamarineBlock> BLOCK_OF_AQUAMARINE = registerBlock("block_of_aquamarine",()-> new AquamarineBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AquamarineBlock> CHISELED_GOLDEN_BLOCK = registerBlock("chiseled_golden_block",()-> new AquamarineBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AquamarineBlock> CHISELED_GOLDEN_AQUAMARINE = registerBlock("chiseled_golden_aquamarine",()-> new AquamarineBlock(BlockBehaviour.Properties.of()));;
    public static final RegistryObject<PearlBlock> BLACK_PEARL_BLOCK = registerBlock("black_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> BLUE_PEARL_BLOCK = registerBlock("blue_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> BROWN_PEARL_BLOCK = registerBlock("brown_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> CYAN_PEARL_BLOCK = registerBlock("cyan_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> GRAY_PEARL_BLOCK = registerBlock("gray_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> GREEN_PEARL_BLOCK = registerBlock("green_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> LIGHT_BLUE_PEARL_BLOCK = registerBlock("light_blue_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> LIGHT_GRAY_PEARL_BLOCK = registerBlock("light_gray_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> LIME_PEARL_BLOCK = registerBlock("lime_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> MAGENTA_PEARL_BLOCK = registerBlock("magenta_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> ORANGE_PEARL_BLOCK = registerBlock("orange_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> PINK_PEARL_BLOCK = registerBlock("pink_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> PURPLE_PEARL_BLOCK = registerBlock("purple_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> RED_PEARL_BLOCK = registerBlock("red_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> WHITE_PEARL_BLOCK = registerBlock("white_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PearlBlock> YELLOW_PEARL_BLOCK = registerBlock("yellow_pearl_block",()-> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlantisPortalBlock> ATLANTIS_PORTAL = registerBlock("atlantis_portal",()-> new AtlantisPortalBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<UnderwaterFlower> UNDERWATER_FLOWER = registerBlock("underwater_flower",()-> new UnderwaterFlower(BlockBehaviour.Properties.of()));
    public static final RegistryObject<UnderwaterFlower> RED_UNDERWATER_FLOWER = registerBlock("red_underwater_flower",()-> new UnderwaterFlower(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PurpleGlowingMushroom> PURPLE_GLOWING_MUSHROOM = registerBlock("purple_glowing_mushroom",()-> new PurpleGlowingMushroom(BlockBehaviour.Properties.of()));
    public static final RegistryObject<YellowGlowingMushroom> YELLOW_GLOWING_MUSHROOM = registerBlock("yellow_glowing_mushroom",()-> new YellowGlowingMushroom(BlockBehaviour.Properties.of()));
    public static final RegistryObject<UnderwaterFlower> YELLOW_UNDERWATER_FLOWER = registerBlock("yellow_underwater_flower",()-> new UnderwaterFlower(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AlgaePlantBlock> ALGAE = registerBlock("algae",()-> new AlgaePlantBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlantisClearPortalBlock> ATLANTIS_CLEAR_PORTAL = registerBlock("atlantis_clear_portal",()-> new AtlantisClearPortalBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanPowerStoneBlock> ATLANTEAN_POWER_STONE = registerBlock("atlantean_power_stone",()-> new AtlanteanPowerStoneBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanPowerLampBlock> ATLANTEAN_POWER_LAMP = registerBlock("atlantean_power_lamp",()-> new AtlanteanPowerLampBlock(BlockBehaviour.Properties.of().strength(0.3F)));
    public static final RegistryObject<AtlanteanPowerTorchBlock> ATLANTEAN_POWER_TORCH = registerOnlyBlock("atlantean_power_torch",()-> new AtlanteanPowerTorchBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<WallAtlanteanPowerTorchBlock> WALL_ATLANTEAN_POWER_TORCH = registerOnlyBlock("atlantean_power_wall_torch",()-> new WallAtlanteanPowerTorchBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanPowerDustBlock> ATLANTEAN_POWER_DUST_WIRE = registerOnlyBlock("atlantean_power_dust",()-> new AtlanteanPowerDustBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanPowerRepeaterBlock> ATLANTEAN_POWER_REPEATER = registerBlock("atlantean_power_repeater",()-> new AtlanteanPowerRepeaterBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanTripwireHook> ATLANTEAN_TRIPWIRE_HOOK = registerBlock("atlantean_tripwire_hook",()-> new AtlanteanTripwireHook(BlockBehaviour.Properties.of().noCollission()));
    public static final RegistryObject<AtlanteanTripwireBlock> ATLANTEAN_TRIPWIRE = registerOnlyBlock("atlantean_tripwire",()-> new AtlanteanTripwireBlock(ATLANTEAN_TRIPWIRE_HOOK.get(), BlockBehaviour.Properties.of().noCollission()));
    public static final RegistryObject<AtlanteanPowerLeverBlock> ATLANTEAN_POWER_LEVER = registerBlock("atlantean_power_lever",()-> new AtlanteanPowerLeverBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<AtlanteanPowerComparatorBlock> ATLANTEAN_POWER_COMPARATOR = registerBlock("atlantean_power_comparator",()-> new AtlanteanPowerComparatorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<BubbleMagmaBlock> CALCITE_BLOCK = registerBlock("calcite_block",()-> new BubbleMagmaBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<PushBubbleColumnBlock> PUSH_BUBBLE_COLUMN = registerOnlyBlock("push_bubble_column",()-> new PushBubbleColumnBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodSlabBlock> ANCIENT_ACACIA_WOOD_MOSS_SLAB = registerBlock("ancient_acacia_wood_moss_slab",()-> new AncientWoodSlabBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodSlabBlock> ANCIENT_OAK_WOOD_MOSS_SLAB = registerBlock("ancient_oak_wood_moss_slab",()-> new AncientWoodSlabBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodSlabBlock> ANCIENT_JUNGLE_WOOD_MOSS_SLAB = registerBlock("ancient_jungle_wood_moss_slab",()-> new AncientWoodSlabBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodSlabBlock> ANCIENT_SPRUCE_WOOD_MOSS_SLAB = registerBlock("ancient_spruce_wood_moss_slab",()-> new AncientWoodSlabBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodSlabBlock> ANCIENT_BIRCH_WOOD_MOSS_SLAB = registerBlock("ancient_birch_wood_moss_slab",()-> new AncientWoodSlabBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AncientWoodSlabBlock> ANCIENT_DARK_OAK_WOOD_MOSS_SLAB = registerBlock("ancient_dark_oak_wood_moss_slab",()-> new AncientWoodSlabBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AlgaeBlock> ALGAE_BLOCK = registerBlock("algae_block",()-> new AlgaeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<ChiseledAquamarineBlock> CHISELED_AQUAMARINE = registerBlock("chiseled_aquamarine",()-> new ChiseledAquamarineBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> ORICHALCUM_BLOCK = registerBlock("orichalcum_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0f, 6.0f).sound(SoundType.METAL)));

    public static final RegistryObject<LinguisticBlock> LINGUISTIC_BLOCK = registerLinguisticBlock("linguistic_block", () -> new LinguisticBlock(BlockBehaviour.Properties.of().strength(2.5F).sound(SoundType.WOOD)));

    public static final RegistryObject<WritingBlock> WRITING_BLOCK = registerLinguisticBlock("writing_block", () -> new WritingBlock(BlockBehaviour.Properties.of().strength(2.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<AtlanteanSaplingBlock> ATLANTEAN_SAPLING = registerBlock("atlantean_sapling", ()->
            new AtlanteanSaplingBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistryObject<AtlanteanFireMelonSpikedFruitBlock> ATLANTEAN_FIRE_MELON_FRUIT_SPIKED = registerOnlyBlock("atlantean_fire_melon_fruit_spiked", () -> new AtlanteanFireMelonSpikedFruitBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanFireMelonFruitBlock> ATLANTEAN_FIRE_MELON_FRUIT = registerOnlyBlock("atlantean_fire_melon_fruit", () -> new AtlanteanFireMelonFruitBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));

    public static final RegistryObject<AtlanteanFireMelonBody> ATLANTEAN_FIRE_MELON_STEM = registerOnlyBlock("atlantean_fire_melon_stem", () -> new AtlanteanFireMelonBody(BlockBehaviour.Properties.of()));
    public static final RegistryObject<AtlanteanFireMelonHead> ATLANTEAN_FIRE_MELON_TOP = registerOnlyBlock("atlantean_fire_melon_top", () -> new AtlanteanFireMelonHead(BlockBehaviour.Properties.of()));

    private static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<B> block) {
        return registerMainTabBlock(name, block, b -> () -> new BlockItem(b.get(),new Item.Properties()));
    }
    public static <B extends Block> RegistryObject<B> registerLinguisticBlock(String name, Supplier<B> block) {
        return registerGylphTabBlock(name, block, b -> () -> new BlockItem(b.get(),new Item.Properties()));
    }

    public static <B extends Block> RegistryObject<B> registerOnlyBlock(String name, Supplier<B> block) {
        return BLOCKS.register(name, block);
    }
    public static <L extends LiquidBlock> RegistryObject<L> registerFluidBlock(String name, Supplier<L> block) {
        var reg = BLOCKS.register(name, block);
        return reg;
    }

    private static <B extends Block, I extends BlockItem> RegistryObject<B> registerMainTabBlock(String name, Supplier<B> block, Function<RegistryObject<B>, Supplier<I>> item) {
        var reg = BLOCKS.register(name, block);
        AtlantisGroupInit.addToMainTab(ItemInit.ITEMS.register(name, () -> item.apply(reg).get()));
        return reg;
    }

    private static <B extends Block, I extends BlockItem> RegistryObject<B> registerGylphTabBlock(String name, Supplier<B> block, Function<RegistryObject<B>, Supplier<I>> item) {
        var reg = BLOCKS.register(name, block);
        AtlantisGroupInit.addToGylphTab(ItemInit.ITEMS.register(name, () -> item.apply(reg).get()));
        return reg;
    }

    public static RegistryObject<Block> getLinguisticBlock(LinguisticGlyph symbol, DyeColor color) {
        if(color != null) {
            if(symbol != null && symbol != LinguisticGlyph.BLANK) {
                return DYED_LINGUISTICS.get(symbol).get(color);
            } else {
                return DYED_LINGUISTICS.get(LinguisticGlyph.BLANK).get(color);
            }
        } else {
            if (symbol != null && symbol != LinguisticGlyph.BLANK) {
                return NON_LINGUISTICS.get(symbol);
            } else {
                return NON_LINGUISTICS.get(LinguisticGlyph.BLANK);
            }
        }
    }
    
    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
    }
    
    static {
        BiFunction<LinguisticGlyph, DyeColor, Supplier<Block>> blockSupplier = (glyph, dyeColor) -> () -> new GlyphBlock(glyph, dyeColor, BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5f, 6.0f
        ));

        for(LinguisticGlyph symbol : LinguisticGlyph.values()) {
            String name = "linguistic_glyph"  + symbol.toString();

            for (DyeColor color : DyeColor.values()) {
                DYED_LINGUISTICS.computeIfAbsent(symbol, c -> new HashMap<>()).put(color, registerLinguisticBlock(color.getSerializedName() + "_" + name, blockSupplier.apply(symbol, color)));
            }

            NON_LINGUISTICS.put(symbol, registerLinguisticBlock(name, blockSupplier.apply(symbol, null)));
        }
    }
}
